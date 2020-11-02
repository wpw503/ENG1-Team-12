package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

class BoatRace {
    PlayerBoat player;
    //todo add ai boats

    BitmapFont font; //TimingTest

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

        //Timing Test
        player.setStartTime(System.currentTimeMillis());
        font = new BitmapFont();
        font.setColor(Color.RED);
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

    public void draw(SpriteBatch batch){
        // Timing Testing
        long i = (System.currentTimeMillis() - player.getStartTime(false));
        for (Sprite sp : getSprites())
            sp.draw(batch);
        font.draw(batch,  String.format("Time (min:sec) = %02d:%02d", i/60000, i/1000%60),-player.ui_bar_width / 2, -50 + player.getSprite().getY());//TimingTest

    }


}
