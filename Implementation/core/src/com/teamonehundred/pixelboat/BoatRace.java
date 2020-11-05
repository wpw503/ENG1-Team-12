package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BoatRace {
    List<Boat> boats;

    List<CollisionObject> obstacles;

    BoatRace(List<Boat> race_boats) {
        boats = new ArrayList<>();
        boats.addAll(race_boats);

        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(40, 400, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(40, 600, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(40, 100, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(100, 400, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(-100, 400, 40, 40, "obstacle.png"));
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
            if( b instanceof PlayerBoat)
                all_sprites.addAll(((PlayerBoat)b).getUISprites());
        }

        for (CollisionObject obs : obstacles) {
            // check if can be cast back up
            if (obs instanceof Obstacle && obs.isShown())
                all_sprites.add(((Obstacle) obs).getSprite());
        }

        return all_sprites;
    }
}
