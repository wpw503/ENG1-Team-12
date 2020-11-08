package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BoatRace. Call functionality for sprite objects such as boats and obstacles.
 *
 * @author William Walton
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
class BoatRace {
    protected List<Boat> boats;
    protected List<lane_wall> lane_objects;

    protected BitmapFont font; //TimingTest

    protected List<CollisionObject> obstacles;

    protected int start_y = 200;
    protected int end_y = 2000;

    protected int lane_width = 400;
    protected int number_of_competitors = 6;
    protected int penalty_per_frame = 1; // ms to add per frame when over the lane

    /**
     * Main constructor for a BoatRace.
     *
     * Initialises lists of boats and obstacles as well as the colour of the Time Elapsed Overlay.
     *
     * @param race_boats List<Boat> A list of ai boats and the player boat.
     * @author William Walton
     * @author Umer Fakher
     * JavaDoc by Umer Fakher
     */
    BoatRace(List<Boat> race_boats) {
        boats = new ArrayList<>();
        boats.addAll(race_boats);

        obstacles = new ArrayList<>();

        // add some random obstacles
        for (int i = 0; i < 5; i++)
            obstacles.add(new ObstacleBranch((int) (-600 + Math.random() * 1200), (int) (60 + Math.random() * 400)));

        for (int i = 0; i < 5; i++)
            obstacles.add(new ObstacleFloatingBranch((int) (-600 + Math.random() * 1200), (int) (60 + Math.random() * 400)));

        for (int i = 0; i < 5; i++)
            obstacles.add(new ObstacleDuck((int) (-600 + Math.random() * 1200), (int) (60 + Math.random() * 400)));

        createLanes(obstacles);

        // Initialise colour of Time Elapsed Overlay
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    private void createLanes(List<CollisionObject> collidables){
        for (int lane = 0; lane <= number_of_competitors * lane_width; lane += lane_width){
            for (int height = 0; height <= end_y; height += lane_wall.texture_height){
                collidables.add(new lane_wall(lane, height));
            }
        }

    }


    /**
     * Main method called for BoatRace.
     *
     * This method is the main game loop that checks if any boats have started or finished a leg and
     * calls the update methods for the movements for player boat and AI boats obstacles.
     * Also this method checks for collisions.
     *
     * @author William Walton
     * @author Umer Fakher
     */
    public void runStep() {
        for (CollisionObject c : obstacles) {
            if (c instanceof Obstacle)
                ((Obstacle) c).updatePosition();
            if (c instanceof lane_wall){
                ((lane_wall) c).setAnimationFrame(0);
            }
        }
        for (Boat b : boats) {
            // check if any boats have finished
            if (b.getEndTime(false) == -1 && b.getSprite().getY() > end_y) {
                // store the leg time in the object
                b.setEndTime(System.currentTimeMillis());
                b.setLegTime();

                System.out.print("a boat ended race with time (ms) ");
                System.out.println(b.getCalcTime());
            }
            // check if any boats have started
            else if (b.getStartTime(false) == -1 && b.getSprite().getY() > start_y) {
                b.setStartTime(System.currentTimeMillis());
            }

            // update boat (handles inputs if player, etc)
            if (b instanceof AIBoat) {
                ((AIBoat) b).updatePosition(obstacles);
            } else if (b instanceof PlayerBoat) {
                b.updatePosition();
            }
            // check for collisions
            for (CollisionObject obstacle : obstacles) {
                b.checkCollisions(obstacle);
            }
        }

    }


    /**
     * Returns a list of all sprites in the PixelBoat game including boats and obstacles.
     *
     * @return List<Sprites> A list of all sprites in the PixelBoat game.
     * @author William Walton
     * @author Umer Fakher
     */
    public List<Sprite> getSprites() {
        List<Sprite> all_sprites = new ArrayList<>();

        for (Boat b : boats) {
            all_sprites.add(b.getSprite());
            if (b instanceof PlayerBoat)
                all_sprites.addAll(((PlayerBoat) b).getUISprites());
        }

        for (CollisionObject obs : obstacles) {
            // check if can be cast back up
            if (obs instanceof Obstacle && obs.isShown())
                all_sprites.add(((Obstacle) obs).getSprite());
        }

        return all_sprites;
    }

    /**
     * Calculates and displays the Time Elapsed Overlay for player boat from the start of a leg.
     *
     * The displayed time is updated in real-time and the position is consistent with the player hud (i.e. stamina
     * and durability bar positions).
     *
     * @param batch
     * @author Umer Fakher
     */
    public void draw(SpriteBatch batch) {

        // Retrieves sprites and calls function recursively.
        for (Sprite sp : getSprites())
            sp.draw(batch);

        for (Boat b : boats) {
            //If current boat b is the player's boat then can display hud for this boat
            if (b instanceof PlayerBoat) {
                //Calculate time elapsed from the start in milliseconds
                long i = (System.currentTimeMillis() - ((PlayerBoat) b).getStartTime(false));

                //Displays and updates the time elapsed overlay and keeps position consistent with player's boat
                drawTimeDisplay(batch, b, "", i, -((PlayerBoat) b).ui_bar_width / 2,
                        500 + ((PlayerBoat) b).getSprite().getY());

                //Draws a leg time display on the screen when the given boat has completed a leg of the race.
                drawLegTimeDisplay(batch, b);
            }
        }

        Texture temp = new Texture("object_placeholder.png");

        batch.draw(temp, -400, start_y, 800, 5);
        batch.draw(temp, -400, end_y, 800, 5);

        temp.dispose();
    }

    /**
     * Draws the a time display on the screen.
     *
     * @param batch SpriteBatch instance
     * @param b Boat instance
     * @param label_text label for text. If "" empty string passed in then default time display shown.
     * @param time time to be shown in milliseconds
     * @param x horizontal position of display
     * @param y vertical position of display
     * @author Umer Fakher
     */
    public void drawTimeDisplay(SpriteBatch batch, Boat b, String label_text, long time, float x, float y){
        if (label_text.equals("")){
            label_text = "Time (min:sec) = %02d:%02d";
        }
        font.draw(batch, String.format(label_text, time / 60000, time / 1000 % 60), x, y);
    }

    /**
     *  Draws a leg time display on the screen when the given boat has completed a leg of the race.
     *
     *  This function gets the leg times list for the given boat instance, gets the last updated leg time
     *  and formats a leg time display string which shows which leg was completed and in what time.
     *  The function then passes on the drawing of this formatted leg time display to drawTimeDisplay.
     *
     * @param batch SpriteBatch instance
     * @param b Boat instance
     * @author Umer Fakher
     */
    public void drawLegTimeDisplay(SpriteBatch batch, Boat b){
        if(b.getEndTime(false) != -1){
            List<Long> legtimes = b.getLegTimes();
            long i = legtimes.get(legtimes.size()-1); // get last in leg times list
            String label = String.format("Leg Time %d (min:sec) = ", legtimes.size())+"%02d:%02d";
            drawTimeDisplay(batch, b, label, i, -((PlayerBoat) b).ui_bar_width / 2,
                    500-(legtimes.size()*20) + ((PlayerBoat) b).getSprite().getY());
        }
    }

}
