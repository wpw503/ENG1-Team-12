package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamonehundred.pixelboat.scenes.Scene;
import com.teamonehundred.pixelboat.scenes.SceneBoatSelection;
import com.teamonehundred.pixelboat.scenes.SceneDifficulty;
import com.teamonehundred.pixelboat.scenes.SceneLoadScreen;
import com.teamonehundred.pixelboat.scenes.SceneMainGame;
import com.teamonehundred.pixelboat.scenes.SceneOptionsMenu;
import com.teamonehundred.pixelboat.scenes.SceneResultsScreen;
import com.teamonehundred.pixelboat.scenes.SceneSaveScreen;
import com.teamonehundred.pixelboat.scenes.SceneStartScreen;
import com.teamonehundred.pixelboat.scenes.SceneTutorial;


/**
 * Main class for the PixelBoat game.
 *
 * <p>Extends Libgdx ApplicationAdapter.
 *
 * @author William Walton
 * @author James Frost
 * @author Umer Fakher JavaDoc by Umer Fakher
 */
public class PixelBoat extends ApplicationAdapter {


  public Scene[] allScenes;  // stores all game scenes and their data
  protected SpriteBatch batch;  // thing that draws the sprites

  // id of current game state
  // 0 = start menu
  // 1 = game
  // 2 = options
  // 3 = tutorial
  // 4 = results
  // 5 =boat selection
  // 6 = difficulty options
  protected int sceneId = 0;

  public static int MAIN_MENU = 0;
  public static int GAME_SCENE = 1;
  public static int OPTIONS_SCENE = 2;
  public static int TUTORIAL_SCENE = 3;
  public static int RESULTS_SCENE = 4;
  public static int BOAT_SELECT = 5;
  public static int DIFFICULTY_SELECT = 6;
  public static int LOAD_SCENE = 7;
  public static int SAVE_SCENE = 8;

  /**
   * Create method runs when the game starts.
   *
   * <p>Runs every scene in Game.
   */
  @Override
  public void create() {
    allScenes = new Scene[9];
    allScenes[MAIN_MENU] = new SceneStartScreen();
    allScenes[GAME_SCENE] = new SceneMainGame();
    allScenes[OPTIONS_SCENE] = new SceneOptionsMenu();
    allScenes[TUTORIAL_SCENE] = new SceneTutorial();
    allScenes[RESULTS_SCENE] = new SceneResultsScreen();
    allScenes[BOAT_SELECT] = new SceneBoatSelection();
    allScenes[DIFFICULTY_SELECT] = new SceneDifficulty();
    allScenes[LOAD_SCENE] = new SceneLoadScreen(this);
    allScenes[SAVE_SCENE] = new SceneSaveScreen(this);
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
    int newSceneId = allScenes[sceneId].update();
    allScenes[sceneId].draw(batch);

    if (sceneId != newSceneId) {
      // special case updates
      if (newSceneId == RESULTS_SCENE) {
        ((SceneResultsScreen) allScenes[RESULTS_SCENE]).setBoats(
            ((SceneMainGame) allScenes[GAME_SCENE]).getAllBoats());
      } else if (newSceneId == TUTORIAL_SCENE && sceneId == DIFFICULTY_SELECT) {
        ((SceneMainGame) allScenes[GAME_SCENE]).setPlayerStats(
            ((SceneBoatSelection) allScenes[BOAT_SELECT]).getSpecId(),
            ((SceneDifficulty) allScenes[DIFFICULTY_SELECT]).getDiffDecrease());
        ((SceneMainGame) allScenes[GAME_SCENE]).initialize();
      }


      // check if we need to change scene
      sceneId = newSceneId;
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
    allScenes[sceneId].resize(width, height);
  }
}
