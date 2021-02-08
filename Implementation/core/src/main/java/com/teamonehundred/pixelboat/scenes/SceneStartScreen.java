package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents the Main Game Scene for when the boat race starts.
 *
 * @author William Walton, JavaDoc by Umer Fakher
 */
public class SceneStartScreen implements Scene {
  protected int sceneId = 0;

  protected Texture bg;
  protected Sprite bgSprite;

  protected Texture play;
  protected Texture playHovered;
  protected Sprite playSprite;

  protected Texture options;
  protected Texture optionsHovered;
  protected Sprite optionsSprite;

  protected Texture load;
  protected Texture loadHovered;
  protected Sprite loadSprite;

  protected Viewport fillViewport;
  protected OrthographicCamera fillCamera;

  /**
   * Main constructor for a SceneStartGame.
   *
   * <p>Initialises a Scene textures for StartScreen. 
   * Including start menu for playing the game and options.
   * When options are hovered over they will change texture
   * indicating what the user is about to select.
   *
   * @author William Walton
   */
  public SceneStartScreen() {
    fillCamera = new OrthographicCamera();
    fillViewport = new FillViewport(1280, 720, fillCamera);
    fillViewport.apply();
    fillCamera.position.set(fillCamera.viewportWidth / 2, fillCamera.viewportHeight / 2, 0);

    bg = new Texture("start_screen.png");
    bgSprite = new Sprite(bg);
    bgSprite.setPosition(0, 0);
    bgSprite.setSize(1280, 720);

    play = new Texture("start_menu_play.png");
    playHovered = new Texture("start_menu_play_hovered.png");
    playSprite = new Sprite(play);
    playSprite.setSize(512 / 2, 128 / 2);
    playSprite.setPosition((fillCamera.viewportWidth / 2) - (playSprite.getWidth() / 2),
                           (fillCamera.viewportHeight / 2) + (playSprite.getHeight() / 2));

    load = new Texture("load_game.png");
    loadHovered = new Texture("load_game_hovered.png");
    loadSprite = new Sprite(load);
    loadSprite.setSize(512 / 2, 128 / 2);
    loadSprite.setPosition((fillCamera.viewportWidth / 2) - (loadSprite.getWidth() / 2),
                          (fillCamera.viewportHeight / 2) * 0.75f - (loadSprite.getHeight() / 2));

    options = new Texture("start_menu_options.png");
    optionsHovered = new Texture("start_menu_options_hovered.png");
    optionsSprite = new Sprite(options);
    optionsSprite.setSize(512 / 2, 128 / 2);
    optionsSprite.setPosition((fillCamera.viewportWidth / 2) - (optionsSprite.getWidth() / 2),
                              (fillCamera.viewportHeight / 2) - (optionsSprite.getHeight() / 2));
  }

  /*
   * Destructor disposes of this texture once it is no longer referenced.
   */
  // protected void finalize() {
  //   bg.dispose();
  // }

  /**
   * Draw function for SceneStartScreen.
   *
   * <p>Draws StartScreen for the PixelBoat game.
   *
   * @param batch SpriteBatch used for drawing to screen.
   * @author William Walton
   */
  public void draw(SpriteBatch batch) {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.setProjectionMatrix(fillCamera.combined);
    batch.begin();
    bgSprite.draw(batch);
    playSprite.draw(batch);
    optionsSprite.draw(batch);
    loadSprite.draw(batch);
    batch.end();
  }

  /**
   * Update function for SceneStartScreen. Ends SceneStartScreen based
   * on user input otherwise stays in scene.
   *
   * <p>Returns an specified integer when you want to exit
   * the screen else return scene_id if you want to stay in scene.
   *
   * @return the scene_id of which screen is next (either this screen still or another)
   * @author William Walton
   */
  public int update() {
    Vector3 mousePos = fillCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

    if (playSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
      playSprite.setTexture(playHovered);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        return 5;
      }
    } else {
      playSprite.setTexture(play);
    }

    if (loadSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
      loadSprite.setTexture(loadHovered);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        return 7;
      }
    } else {
      loadSprite.setTexture(load);
    }

    if (optionsSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
      optionsSprite.setTexture(optionsHovered);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        // todo enable options when implemented
        //return 2;
      }
    } else {
      optionsSprite.setTexture(options);
    }

    // Stay in SceneStartScreen
    return sceneId;
  }


  /**
   * Resize method if for camera extension.
   *
   * @param width  Integer width to be resized to
   * @param height Integer height to be resized to
   * @author William Walton
   */
  public void resize(int width, int height) {
    fillViewport.update(width, height);
    fillCamera.position.set(fillCamera.viewportWidth / 2, fillCamera.viewportHeight / 2, 0);
  }
}
