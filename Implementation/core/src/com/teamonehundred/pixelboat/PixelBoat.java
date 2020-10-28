package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PixelBoat extends ApplicationAdapter {
    Scene[] all_scenes;  // stores all game scenes and their data
    SpriteBatch batch;  // thing that draws the sprites

    // id of current game state
    // 0 = start menu
    // 1 = game
    // 2 = ...
    int scene_id = 0;

    // ran when the game starts
    @Override
    public void create() {
        all_scenes = new Scene[2];
        all_scenes[0] = new SceneStartScreen();
        all_scenes[1] = new SceneMainGame();

        batch = new SpriteBatch();
    }

    // ran every frame
    @Override
    public void render() {
        // run the current scene
        all_scenes[scene_id].update();
        all_scenes[scene_id].draw(batch);

        // check if we need to change scene
        scene_id = all_scenes[scene_id].getCurrentSceneID();
    }

    // ran when the game closes
    @Override
    public void dispose() {
        batch.dispose();
    }
}
