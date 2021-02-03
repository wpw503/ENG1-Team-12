package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamonehundred.pixelboat.scenes.*;

/**
 * Main class for the PixelBoat game.
 * <p>
 * Extends Libgdx ApplicationAdapter.
 *
 * @author William Walton
 * @author James Frost
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
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
    // 6 = difficulty options
    protected int scene_id = 0;

    /**
     * Create method runs when the game starts.
     * <p>
     * Runs every scene in Game.
     */
    @Override
    public void create() {
        all_scenes = new Scene[7];
        all_scenes[0] = new SceneStartScreen();
        all_scenes[1] = new SceneMainGame();
        all_scenes[2] = new SceneOptionsMenu();
        all_scenes[3] = new SceneTutorial();
        all_scenes[4] = new SceneResultsScreen();
        all_scenes[5] = new SceneBoatSelection();
        all_scenes[6] = new SceneDifficulty();
        System.out.println(((SceneBoatSelection) all_scenes[5]).getSpecID());
        System.out.println(((SceneDifficulty) all_scenes[6]).getDiffDecrease());

        batch = new SpriteBatch();
    }

    /**
     * Render function runs every frame.
     * <p>
     * Controls functionality of frame switching.
     */
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
                ((SceneMainGame) all_scenes[1]).setPlayerSpec(((SceneBoatSelection) all_scenes[5]).getSpecID(),((SceneDifficulty) all_scenes[6]).getDiffDecrease());


            // check if we need to change scene
            scene_id = new_scene_id;
        }
    }

    /**
     * Disposes unneeded SpriteBatch and exits application.
     * <p>
     * Runs when the game needs to close.
     */
    @Override
    public void dispose() {
        batch.dispose();

        Gdx.app.exit();
        System.exit(0);
    }

    /**
     * Resize used and passed to resize method of each scene based on width and height attributes.
     *
     * @param width  int for scene
     * @param height int for scene
     */
    @Override
    public void resize(int width, int height) {
        all_scenes[scene_id].resize(width, height);
    }
}
