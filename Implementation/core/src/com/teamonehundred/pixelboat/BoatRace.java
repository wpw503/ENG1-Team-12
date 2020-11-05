package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BoatRace {
    PlayerBoat player;
    //todo add ai boats
    List<AIBoat> AIboats;

    List<CollisionObject> obstacles;

    BoatRace(PlayerBoat player) {
        this.player = player;

        obstacles = new ArrayList<>();
        obstacles.add( new Obstacle(40, 400, 40, 40, "obstacle.png"));
        obstacles.add( new Obstacle(40, 600, 40, 40, "obstacle.png"));
        obstacles.add( new Obstacle(40, 100, 40, 40, "obstacle.png"));
        obstacles.add( new Obstacle(100, 400, 40, 40, "obstacle.png"));
        obstacles.add( new Obstacle(-100, 400, 40, 40, "obstacle.png"));

        AIboats = new ArrayList<>();
        AIboats.add(new AIBoat(10, 20,  30, 100, "object_placeholder.png"));

    }

    public void runStep() {
        // update player's boat (handles inputs, etc)
        player.updatePosition();

        for (AIBoat AI : AIboats){
            AI.updatePosition(obstacles);

        }
        // check for collisions
        for(CollisionObject obstacle : obstacles)
            player.checkCollisions(obstacle);
    }

    public List<Sprite> getSprites() {
        List<Sprite> all_sprites = new ArrayList<>();

        all_sprites.add(player.getSprite());
        all_sprites.addAll(player.getUISprites());

        for (AIBoat AI : AIboats){
            all_sprites.add((AI.getSprite()));
            Texture texture;
            Sprite sprite;
            texture = new Texture("point.png");
            sprite = new Sprite(texture);
            sprite.setPosition(AI.sprite.getOriginX(), AI.sprite.getOriginY());
            sprite.setSize(5, 5);
            all_sprites.add(sprite);
        }


        for (CollisionObject obs : obstacles) {
            // check if can be cast back up
            if(obs instanceof Obstacle && obs.isShown())
                all_sprites.add(((Obstacle)obs).getSprite());
        }

        return all_sprites;
    }
}
