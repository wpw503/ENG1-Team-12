package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BoatRace {
    List<Boat> boats;

    BitmapFont font; //TimingTest

    List<CollisionObject> obstacles;

    int start_y = 200;
    int end_y = 2000;

    int lane_width = 400;
    int penalty_per_frame = 1; // ms to add per frame when over the lane

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

        //Timing Test
//        for (Boat b : boats) {
//            if (b instanceof PlayerBoat) {
//                ((PlayerBoat) b).setStartTime(System.currentTimeMillis());
//            }
//        }

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void runStep() {
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
            for (CollisionObject obstacle : obstacles){
                b.checkCollisions(obstacle);
            }
        }


    }


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

    public void draw(SpriteBatch batch) {
        // Timing Testing

        for (Sprite sp : getSprites())
            sp.draw(batch);

        for (Boat b : boats) {
            if (b instanceof PlayerBoat) {
                long i = (System.currentTimeMillis() - ((PlayerBoat) b).getStartTime(false));
                font.draw(batch, String.format("Time (min:sec) = %02d:%02d", i / 60000, i / 1000 % 60), -((PlayerBoat) b).ui_bar_width / 2, -50 + ((PlayerBoat) b).getSprite().getY());//TimingTest
            }
        }

        Texture temp = new Texture("object_placeholder.png");

        batch.draw(temp, -400, start_y, 800, 5);
        batch.draw(temp, -400, end_y, 800, 5);

        temp.dispose();
    }


}
