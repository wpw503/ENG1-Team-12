package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamonehundred.pixelboat.scenes.*;

/**
 * Main class for the PixelBoat game.
 * 
 * <p>extends Libgdx ApplicationAdapter.
 *
 * @author William Walton
 * @author James Frost
 * @author Umer Fakher
   JavaDoc by Umer Fakher
 */
public class PixelBoat extends ApplicationAdapter {
  protected Scene[] allScenes;  // stores all game scenes and their data
  protected SpriteBatch batch;  // thing that draws the sprites

  // id of current game state
  // 0 = start menu
  // 1 = game
  // 2 = options
  // 3 = tutorial
  // 4 = results
  // 5 =boat selection
  protected int sceneID = 0;

  /**
   * Create method runs when the game starts.
   * 
   * <p>Runs every scene in Game.
   */
  @Override
  public void create() {
    allScenes = new Scene[6];
    allScenes[0] = new SceneStartScreen();
    allScenes[1] = new SceneMainGame();
    allScenes[2] = new SceneOptionsMenu();
    allScenes[3] = new SceneTutorial();
    allScenes[4] = new SceneResultsScreen();
    allScenes[5] = new SceneBoatSelection();

    batch = new SpriteBatch();
  }

  /**
   * Render function runs every frame.
   * 
   * <p>Controls functionality of frame switching.
   */
  @Override
  public void render() {
    // run the current scene
    int newSceneID = allScenes[sceneID].update();
    allScenes[sceneID].draw(batch);

    if (sceneID != newSceneID) {
      // special case updates
      if (newSceneID == 4) {
        ((SceneResultsScreen) allScenes[4]).setBoats(((SceneMainGame) allScenes[1]).getAllBoats());
      } else if (newSceneID == 3 && sceneID == 5) {
        ((SceneMainGame) allScenes[1]).setPlayerSpec(((SceneBoatSelection)
            allScenes[5]).getSpecID());
      }
      // check if we need to change scene
      sceneID = newSceneID;
    }
  }

  /**
   * Disposes unneeded SpriteBatch and exits application.
   * 
   * <p>Runs when the game needs to close.
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
    allScenes[sceneID].resize(width, height);
  }
}
