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
    protected List<ObstacleLaneWall> lane_objects;

    protected BitmapFont font; //TimingTest
    protected Texture lane_sep;

    protected List<CollisionObject> obstacles;

    protected int start_y = 200;
    protected int end_y = 10000;

    protected int lane_width = 400;
    protected int penalty_per_frame = 1; // ms to add per frame when over the lane

    protected boolean is_finished = false;

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
        lane_sep = new Texture("lane_buoy.png");

        boats = new ArrayList<>();
        boats.addAll(race_boats);

        for (int i = 0; i < boats.size(); i++) {
            boats.get(i).has_started_leg = false;
            boats.get(i).has_finished_leg = false;

            boats.get(i).reset_motion();
            boats.get(i).sprite.setPosition(getLaneCentre(i), 40);  // reset boats y and place in lane
            boats.get(i).setFramesRaced(0);
            boats.get(i).reset();

            if(boats.get(i) instanceof PlayerBoat)
                ((PlayerBoat)boats.get(i)).resetCameraPos();
        }

        obstacles = new ArrayList<>();

        // add some random obstacles
        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleBranch(
                    (int) (-(lane_width*boats.size()/2) + Math.random() * (lane_width*boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y-start_y-50))));

        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleFloatingBranch((int) (-(lane_width*boats.size()/2) + Math.random() * (lane_width*boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y-start_y-50))));

        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleDuck((int) (-(lane_width*boats.size()/2) + Math.random() * (lane_width*boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y-start_y-50))));

        // add the lane separators
        for (int lane = 0; lane <= boats.size(); lane ++){
            for (int height = 0; height <= end_y; height += ObstacleLaneWall.texture_height){
                obstacles.add(new ObstacleLaneWall(getLaneCentre(lane) - lane_width/2, height, lane_sep));
            }
        }

        // Initialise colour of Time Elapsed Overlay
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    private int getLaneCentre(int boat_index){
        int race_width = boats.size() * lane_width;
        return (-race_width/2)+(lane_width*(boat_index+1))-(lane_width/2);
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
            if (c instanceof ObstacleLaneWall){
                ((ObstacleLaneWall) c).setAnimationFrame(0);
            }
        }
        for (int i = 0; i < boats.size(); i++) {
            // check if any boats have finished
            if (!boats.get(i).hasFinishedLeg() && boats.get(i).getSprite().getY() > end_y) {
                // store the leg time in the object
                boats.get(i).setStartTime(0);
                boats.get(i).setEndTime((long)(boats.get(i).getStartTime(false) + ((1000.0/60.0)*boats.get(i).getFramesRaced())));
                boats.get(i).setLegTime();

                boats.get(i).setHasFinishedLeg(true);
            }
            // check if any boats have started
            else if (!boats.get(i).hasStartedLeg() && boats.get(i).getSprite().getY() > start_y) {
                boats.get(i).setStartTime(System.currentTimeMillis());
                boats.get(i).setHasStartedLeg(true);
                boats.get(i).setFramesRaced(0);
            }else{
                // if not start or end, must be racing
                boats.get(i).addFrameRaced();
            }
        }

        boolean not_finished = false;

        for (int i = 0; i < boats.size(); i++) {
            // all boats
            if (!boats.get(i).hasFinishedLeg()) not_finished = true;

            // update boat (handles inputs if player, etc)
            if (boats.get(i) instanceof AIBoat) {
                ((AIBoat) boats.get(i)).updatePosition(obstacles);
            } else if (boats.get(i) instanceof PlayerBoat) {
                boats.get(i).updatePosition();
            }
            // check for collisions
            for (CollisionObject obstacle : obstacles) {
                if(obstacle.isShown())
                    boats.get(i).checkCollisions(obstacle);
            }

            // check if out of lane
            if(boats.get(i).getSprite().getX() > getLaneCentre(i) + lane_width/2 ||
                    boats.get(i).getSprite().getX() < getLaneCentre(i) - lane_width/2)
                boats.get(i).setTimeToAdd(boats.get(i).getTimeToAdd() + penalty_per_frame);
        }
        is_finished = !not_finished;

        for (CollisionObject c : obstacles) {
            if (c instanceof Obstacle)
                ((Obstacle) c).updatePosition();
        }
    }

    public boolean isFinished() {
        return is_finished;
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
