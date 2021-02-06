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
 * SceneDifficulty.
 */
public class SceneDifficulty implements Scene {
  protected int sceneId = 6;

  protected boolean isNewClick = false;

  protected int numButtons = 4;

  protected Texture bg;
  protected Sprite bgSprite;

  protected Texture hard;
  protected Sprite hardSprite;

  protected Texture med;
  protected Sprite medSprite;

  protected Texture easy;
  protected Sprite easySprite;

  protected Texture ready;
  protected Sprite readySprite;


  protected Viewport fillViewport;
  protected OrthographicCamera fillCamera;

   
  public int diffDecrease = 1;

  /**
   * Create a SceneDifficulty.
   */
  public SceneDifficulty() {
    fillCamera = new OrthographicCamera();
    fillViewport = new FillViewport(1280, 720, fillCamera);
    fillViewport.apply();
    fillCamera.position.set(fillCamera.viewportWidth / 2, fillCamera.viewportHeight / 2, 0);
    fillViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    bg = new Texture("difficulty_options_screen.png");
    bgSprite = new Sprite(bg);
    bgSprite.setPosition(0, 0);
    bgSprite.setSize(1280, 720);

    hard = new Texture("hardButton.png");
    hardSprite = new Sprite(hard);
    hardSprite.setSize(512 / 4, 128 / 4);
    hardSprite.setPosition((fillCamera.viewportWidth / 4) - (hardSprite.getWidth() / 4),
                           (fillCamera.viewportHeight / 4) + (hardSprite.getHeight() * 4));

    med = new Texture("mediumButton.png");
    medSprite = new Sprite(med);
    medSprite.setSize(512 / 4, 128 / 4);
    medSprite.setPosition((fillCamera.viewportWidth / 4) - (medSprite.getWidth() / 4),
                          (fillCamera.viewportHeight / 4) + (medSprite.getHeight() * 2));

    easy = new Texture("easyButton.png");
    easySprite = new Sprite(easy);
    easySprite.setSize(512 / 4, 128 / 4);
    easySprite.setPosition((fillCamera.viewportWidth / 4) - (easySprite.getWidth() / 4),
                           (fillCamera.viewportHeight / 4) + (easySprite.getHeight()));

    ready = new Texture("readyButton.png");
    readySprite = new Sprite(ready);
    readySprite.setSize(512 / 4, 128 / 4);
    readySprite.setPosition((fillCamera.viewportWidth / 4) - (readySprite.getWidth() / 4),
                            (fillCamera.viewportHeight / 4) + (readySprite.getHeight() / 4));

  }

  /**
   * Update the scene.
   */
  public int update() {

    Vector3 mousePos = fillCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

    if (hardSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)
        && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      System.out.println("hard");
      diffDecrease = 3;
    }
    
    if (medSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)
        && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      System.out.println("med");
      diffDecrease = 2;
    }

    if (easySprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)
        && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      System.out.println("esay");
      diffDecrease = 1;
    }   

    if (readySprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)
        && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      return 3;
    }
    return sceneId;
  }
  
  /**
   * Draw the scene.
   */
  public void draw(SpriteBatch batch) {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.setProjectionMatrix(fillCamera.combined);
    batch.begin();
    bgSprite.draw(batch);
    hardSprite.draw(batch);
    medSprite.draw(batch);
    easySprite.draw(batch);
    readySprite.draw(batch);
    batch.end();
  }
 
  public int getDiffDecrease() {
    return diffDecrease;
  }

  public void resize(int width, int height) {
    fillViewport.update(width, height);
    fillCamera.position.set(fillCamera.viewportWidth / 4, fillCamera.viewportHeight / 4, 0);
  }
}
