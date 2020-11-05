package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BoatRace {
    List<Boat> boats;

    BitmapFont font; //TimingTest

    List<CollisionObject> obstacles;

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
        for (Boat b : boats) {
            if (b instanceof PlayerBoat)
                ((PlayerBoat) b).setStartTime(System.currentTimeMillis());
        }

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void runStep() {
        for (Boat b : boats) {
            // update boat (handles inputs if player, etc)
            if (b instanceof AIBoat) {
                ((AIBoat) b).updatePosition(obstacles);
            } else if (b instanceof PlayerBoat) {
                b.updatePosition();
                // check for collisions
                for (CollisionObject obstacle : obstacles)
                    ((PlayerBoat) b).checkCollisions(obstacle);
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
    }


}
