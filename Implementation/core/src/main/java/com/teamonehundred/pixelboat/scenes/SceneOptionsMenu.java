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
 * Represents the Options Menu Scene for when the player wants
 * to select/edit the options before the race starts.
 *
 * @author William Walton, JavaDoc by Umer Fakher
 */
public class SceneOptionsMenu implements Scene {
  protected int sceneId = 2;

  protected boolean isFullscreen = false;

  protected Texture bg;
  protected Sprite bgSprite;

  protected Texture full;
  protected Sprite fullSprite;
  protected Texture fullCheckYes;
  protected Texture fullCheckNo;
  protected Sprite fullCheckSprite;

  protected Texture accel;
  protected Sprite accelSprite;

  protected Texture left;
  protected Sprite leftSprite;

  protected Texture right;
  protected Sprite rightSprite;

  protected Texture back;
  protected Texture backHovered;
  protected Sprite backSprite;

  protected Viewport fillViewPort;
  protected OrthographicCamera fillCamera;

  /**
   * Main constructor for a SceneOptionsMenu.
   *
   * <p>Initialises a Scene textures for Options Menu and camera.
   * When options are hovered over they will change texture indicating
   * what the user is about to select.
   *
   * @author William Walton
   */
  public SceneOptionsMenu() {
    fillCamera = new OrthographicCamera();
    fillViewPort = new FillViewport(1280, 720, fillCamera);
    fillViewPort.apply();
    fillCamera.position.set(fillCamera.viewportWidth / 2, fillCamera.viewportHeight / 2, 0);
    fillViewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    bg = new Texture("start_screen.png");
    bgSprite = new Sprite(bg);
    bgSprite.setPosition(0, 0);
    bgSprite.setSize(1280, 720);

    full = new Texture("options_menu_fullscreen.png");
    fullCheckYes = new Texture("options_menu_checkbox_yes.png");
    fullCheckNo = new Texture("options_menu_checkbox_no.png");
    full = new Texture("options_menu_fullscreen.png");
    fullSprite = new Sprite(full);
    fullCheckSprite = new Sprite(fullCheckNo);
    fullSprite.setSize(512 / 2, 128 / 2);
    fullSprite.setPosition((fillCamera.viewportWidth / 2) - (fullSprite.getWidth()),
                           (Gdx.graphics.getHeight() / 2) + (fullSprite.getHeight() * 1.5f));
    fullCheckSprite.setSize(128 / 2, 128 / 2);
    fullCheckSprite.setPosition((fillCamera.viewportWidth / 2) + (fullSprite.getWidth() / 2),
                                (Gdx.graphics.getHeight() / 2) + (fullSprite.getHeight() * 1.5f));

    accel = new Texture("options_menu_fullscreen.png");
    accelSprite = new Sprite(accel);
    accelSprite.setSize(512 / 2, 128 / 2);
    accelSprite.setPosition((fillCamera.viewportWidth / 2) - (fullSprite.getWidth()),
                            (Gdx.graphics.getHeight() / 2) + (fullSprite.getHeight() * .5f));

    left = new Texture("options_menu_fullscreen.png");
    leftSprite = new Sprite(left);
    leftSprite.setSize(512 / 2, 128 / 2);
    leftSprite.setPosition((fillCamera.viewportWidth / 2) - (fullSprite.getWidth()),
                           (Gdx.graphics.getHeight() / 2) - (fullSprite.getHeight() * .5f));

    right = new Texture("options_menu_fullscreen.png");
    rightSprite = new Sprite(right);
    rightSprite.setSize(512 / 2, 128 / 2);
    rightSprite.setPosition((fillCamera.viewportWidth / 2) - (fullSprite.getWidth()),
                            (Gdx.graphics.getHeight() / 2) - (fullSprite.getHeight() * 1.5f));

    back = new Texture("options_menu_back.png");
    backHovered = new Texture("options_menu_back_hovered.png");
    backSprite = new Sprite(back);
    backSprite.setSize(512 / 2, 128 / 2);
    backSprite.setPosition((fillCamera.viewportWidth / 2) - (fullSprite.getWidth()), 70);
  }

  /**
   * Draw function for SceneOptionsMenu.
   *
   * <p>Draws Options Menu for the PixelBoat game.
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
    fullSprite.draw(batch);
    accelSprite.draw(batch);
    leftSprite.draw(batch);
    rightSprite.draw(batch);
    backSprite.draw(batch);
    fullCheckSprite.draw(batch);
    batch.end();
  }

  /**
   * Update function for SceneOptionsMenu.
   * Ends SceneOptionsMenu based on user input otherwise stays in scene.
   *
   * <p>Returns an specified integer when you want to exit the screen else return scene_id
   * if you want to stay in scene.
   * In this case left clicking with the mouse on  the back button will stop the Options Menu Scene
   * and continue with the Main Menu Scene.
   *
   * @return the scene_id of which screen is next (either this screen still or another)
   * @author William Walton
   */
  public int update() {
    Vector3 mousePos = fillCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

    if (backSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
      backSprite.setTexture(backHovered);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        return 0;
      }
    } else {
      backSprite.setTexture(back);
    }

    // todo add single click detection to stop this changing every frame
    if (fullCheckSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
      //full_check_sprite.setTexture(full_check_);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        if (isFullscreen) {
          fullCheckSprite.setTexture(fullCheckNo);
          isFullscreen = !isFullscreen;
        } else {
          fullCheckSprite.setTexture(fullCheckYes);
          isFullscreen = !isFullscreen;
        }
      }
    }

    return sceneId;
  }

  /**
   * Temp resize method if needed for camera extension.
   *
   * @param width  Integer width to be resized to
   * @param height Integer height to be resized to
   * @author William Walton
   */
  public void resize(int width, int height) {
    fillViewPort.update(width, height);
    fillCamera.position.set(fillCamera.viewportWidth / 2, fillCamera.viewportHeight / 2, 0);
  }
}
