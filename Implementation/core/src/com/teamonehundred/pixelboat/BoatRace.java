package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

class BoatRace {
    PlayerBoat player;
    //todo add ai boats

    List<CollisionObject> obstacles;

    BoatRace(PlayerBoat player) {
        this.player = player;

        obstacles = new ArrayList<>();

        // add some random obstacles
        for (int i = 0; i < 5; i++)
            obstacles.add(new ObstacleBranch((int)(-600 + Math.random() * 1200), (int) (60 + Math.random() * 400)));

        for (int i = 0; i < 5; i++)
            obstacles.add(new ObstacleFloatingBranch((int)(-600 + Math.random() * 1200), (int) (60 + Math.random() * 400)));

        for (int i = 0; i < 5; i++)
            obstacles.add(new ObstacleDuck((int)(-600 + Math.random() * 1200), (int) (60 + Math.random() * 400)));
    }

    public void runStep() {
        // update player's boat (handles inputs, etc)
        player.updatePosition();
        //update all obstacle positions
        for(CollisionObject obs : obstacles){
            if (obs instanceof Obstacle)
                ((Obstacle)obs).updatePosition();
        }

        // check for collisions
        for(CollisionObject obstacle : obstacles)
            player.checkCollisions(obstacle);
    }

    public List<Sprite> getSprites() {
        List<Sprite> all_sprites = new ArrayList<>();

        all_sprites.add(player.getSprite());
        all_sprites.addAll(player.getUISprites());

        for (CollisionObject obs : obstacles) {
            // check if can be cast back up
            if(obs instanceof Obstacle && obs.isShown())
                all_sprites.add(((Obstacle)obs).getSprite());
        }

        return all_sprites;
    }
}
