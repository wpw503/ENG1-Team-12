package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PixelBoat extends ApplicationAdapter {
    protected Scene[] all_scenes;  // stores all game scenes and their data
    protected SpriteBatch batch;  // thing that draws the sprites

    // id of current game state
    // 0 = start menu
    // 1 = game
    // 2 = options
    // 3 = tutorial
    // 4 = results
    // 5 =boat selection
    protected int scene_id = 0;

    // ran when the game starts
    @Override
    public void create() {
        all_scenes = new Scene[6];
        all_scenes[0] = new SceneStartScreen();
        all_scenes[1] = new SceneMainGame();
        all_scenes[2] = new SceneOptionsMenu();
        all_scenes[3] = new SceneTutorial();
        all_scenes[4] = new SceneResultsScreen();
        all_scenes[5] = new SceneBoatSelection();

        batch = new SpriteBatch();
    }

    // ran every frame
    @Override
    public void render() {
        // run the current scene
        int new_scene_id = all_scenes[scene_id].update();
        all_scenes[scene_id].draw(batch);

        if (scene_id != new_scene_id) {
            // special case updates
            if (new_scene_id == 4)
                ((SceneResultsScreen) all_scenes[4]).setBoats(((SceneMainGame) all_scenes[1]).getAllBoats());
            else if (new_scene_id == 3 && scene_id == 5)
                ((SceneMainGame) all_scenes[1]).setPlayerSpec(((SceneBoatSelection) all_scenes[5]).getSpecID());


            // check if we need to change scene
            scene_id = new_scene_id;
        }
    }

    // ran when the game closes
    @Override
    public void dispose() {
        batch.dispose();

        Gdx.app.exit();
        System.exit(0);
    }

    @Override
    public void resize(int width, int height) {
        all_scenes[scene_id].resize(width, height);
    }
}
