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
 * Represents the Boat Selection Scene for when the player wants to select which boat to play with 
 * before the race starts.
 *
 * @author William Walton
   JavaDoc by Umer Fakher
 */
public class SceneBoatSelection implements Scene {
  protected int sceneID = 5;
  protected boolean isNewClick = false;
  protected int specID = 0;
  protected int numSpecs = 3;
  protected Texture bg;
  protected Sprite bgSprite;
  protected Texture[] boatOptions;
  protected Sprite[] boatOptionSprites;
  protected Viewport fillViewport;
  protected OrthographicCamera fillCamera;
  /**
  * Main constructor for a SceneBoatSelection.
  * 
  * <p>Initialises a Scene textures for Boat Selection and camera.
  *
  * @author William Walton
  */

  public SceneBoatSelection() {
    fillCamera = new OrthographicCamera();
    fillViewport = new FillViewport(1280, 720, fillCamera);
    fillViewport.apply();
    fillCamera.position.set(fillCamera.viewportWidth / 2, fillCamera.viewportHeight / 2, 0);
    fillViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    bg = new Texture("boat_selection_screen.png");
    bgSprite = new Sprite(bg);
    bgSprite.setPosition(0, 0);
    bgSprite.setSize(1280, 720);
    boatOptions = new Texture[numSpecs];
    boatOptionSprites = new Sprite[numSpecs];
    boatOptions[0] = new Texture("boat_selection_debug.png");
    boatOptions[1] = new Texture("boat_selection_default.png");
    boatOptions[2] = new Texture("boat_selection_fastlowdurability.png");
    for (int i = 0; i < numSpecs; i++) {
      boatOptionSprites[i] = new Sprite(boatOptions[i]);
      boatOptionSprites[i].setSize(512 / 2, 256 / 2);
      boatOptionSprites[i].setPosition(
          (fillCamera.viewportWidth / 2) - (boatOptionSprites[i].getWidth() / 2),
          (fillCamera.viewportHeight / 2) + (boatOptionSprites[i].getHeight() / 2)
           - i * (boatOptionSprites[i].getHeight()));
    }
  }

  /**
  * Update function for SceneBoatSelection. Ends SceneBoatSelection based on user input 
    otherwise stays in scene.
  * 
  * <p>Returns an specified integer when you want to exit the screen else return sceneID
    if you want to stay in scene.
  *
  * @return returns an integer which is the sceneID of which screen is next 
    (either this screen still or another)
  * @author William Walton
  */
  public int update() {
    if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      isNewClick = true;
    }
    Vector3 mousePos = fillCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    for (int i = 0; i < numSpecs; i++) {
      if (boatOptionSprites[i].getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && isNewClick) {
          specID = i;
          return 3;  // return 3 to exit
        }
      }    
    }
    return sceneID;
  }

  /**
  * Draw function for SceneBoatSelection.
  * 
  * <p>Draws BoatSelection for the PixelBoat game.
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
    for (int i = 0; i < 3; i++) {
      boatOptionSprites[i].draw(batch);
    }
    batch.end();
  }

  /**
   * Temp resize method if needed for camera extension.
   *
   * @param width  Integer width to be resized to
   * @param height Integer height to be resized to
   * @author Umer Fakher
   */
  public void resize(int width, int height) {
  }

  /**
   * Getter method for the specified boat's specID.
   *
   * @return boat's spec id
   * @author William Walton
   */
  public int getSpecID() {
    return specID;
  }

}
