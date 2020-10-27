package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class PixelBoat extends ApplicationAdapter {
    /* ################################################ //
                    Start Screen Attributes
    // ################################################ */

    Texture start_screen;

    PlayerBoat player;
    List<Obstacle> obstacles;
    Texture bg;

    SpriteBatch batch;
    OrthographicCamera camera;

    // id of current game state
    // 0 = start menu
    // 1 = game
    // 2 = ...
    int game_state = 0;

    // ran when the game starts
    @Override
    public void create() {
        player = new PlayerBoat(-15, 0, 30, 100, "object_placeholder.png");
        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(40, 400, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(40, 600, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(40, 100, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(100, 400, 40, 40, "obstacle.png"));
        obstacles.add(new Obstacle(-100, 400, 40, 40, "obstacle.png"));
        bg = new Texture("temp_background.png");
        start_screen = new Texture("start_screen.png");

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, Gdx.graphics.getHeight() / 3, 0);
        camera.update();
    }

    // ran every frame
    @Override
    public void render() {
        switch(game_state){
            case 0: menuLoop();
                    break;
            case 1: gameLoop();
                    break;
        }
    }

    // ran when the game closes
    @Override
    public void dispose() {
        batch.dispose();
    }

    // the actual boat game bit
    private void gameLoop(){
        // user input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.accelerate();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            // boat cannot go back, there is only moving forwards, just like life...
            // in that sense, the game is a very good analogy for the struggles of life
            // constantly having ot move forward, unable to return to what was
            // constantly dodging obstacles, trying to keep moving forwards
            // maybe programming at 3am isn't such a good idea after all...

            //obj.move(-5);
            // but it can for testing
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.turn(5);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.turn(-5);
        }

        // update motion
        camera.translate(0, (float) player.updatePosition(), 0);

        // check for collisions
        for (Obstacle obs : obstacles) {
            if (obs.getBounds().overlaps(player.getBounds()) && obs.isShown()) {
                player.hasCollided();
                obs.hasCollided();
            }
        }

        // drawing
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bg, -500, -70, 1000, 1000);
        if (player.isShown()) {
            player.getSprite().draw(batch);
            for(Sprite ui_element : player.getUISprites())
                ui_element.draw(batch);
        }
        for (Obstacle obs : obstacles)
            if (obs.isShown()) obs.getSprite().draw(batch);
        batch.end();
    }

    // the initial menu where you press start
    private void menuLoop(){
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            game_state = 1;

        batch.begin();
        batch.draw(start_screen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
}
