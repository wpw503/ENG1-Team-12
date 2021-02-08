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
import java.util.ArrayList;

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
  protected Texture hardHovered;
  protected Sprite hardSprite;

  protected Texture med;
  protected Texture medHovered;
  protected Sprite medSprite;

  protected Texture easy;
  protected Texture easyHovered;
  protected Sprite easySprite;

  protected Texture ready;
  protected Texture readyHovered;
  protected Sprite readySprite;

  protected Texture arrow;
  protected Sprite arrowSprite;


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

    bg = new Texture("start_screen.png");
    bgSprite = new Sprite(bg);
    bgSprite.setPosition(0, 0);
    bgSprite.setSize(1280, 720);

    hard = new Texture("hard.png");
    hardHovered = new Texture("hard_hovered.png");
    hardSprite = new Sprite(hard);
    //hardSprite.setSize(512 / 4, 128 / 4);
    hardSprite.setPosition((fillCamera.viewportWidth / 4) - (hardSprite.getWidth() / 4),
                           (fillCamera.viewportHeight / 4) + (hardSprite.getHeight() * 4));

    med = new Texture("medium.png");
    medHovered = new Texture("medium_hovered.png");
    medSprite = new Sprite(med);
    //medSprite.setSize(512 / 4, 128 / 4);
    medSprite.setPosition((fillCamera.viewportWidth / 4) - (medSprite.getWidth() / 4),
                          (fillCamera.viewportHeight / 4) + (medSprite.getHeight() * 2));

    easy = new Texture("easy.png");
    easyHovered = new Texture("easy_hovered.png");
    easySprite = new Sprite(easy);
    //easySprite.setSize(512 / 4, 128 / 4);
    easySprite.setPosition((fillCamera.viewportWidth / 4) - (easySprite.getWidth() / 4),
                           (fillCamera.viewportHeight / 4) + (easySprite.getHeight()));

    ready = new Texture("ready.png");
    readyHovered = new Texture("ready_hovered.png");
    readySprite = new Sprite(ready);
    //readySprite.setSize(512 / 4, 128 / 4);
    readySprite.setPosition((fillCamera.viewportWidth / 4) - (readySprite.getWidth() / 4),
                            (fillCamera.viewportHeight / 4) + (readySprite.getHeight() / 4));


    // Set the positions of the buttons
    Sprite[] buttonSprites = {hardSprite, medSprite, easySprite, readySprite};
    float buttonHeight = 64;
    float buttonWidth = 256; 
    float spaceBetween = 30;
    float startheight = (buttonSprites.length * (buttonHeight + spaceBetween));
    for (int i = 0; i < buttonSprites.length; i++) {
      buttonSprites[i].setSize(buttonWidth, buttonHeight);
      buttonSprites[i].setCenter(
          fillCamera.viewportWidth / 2,
          startheight - i * (buttonHeight + spaceBetween)
      );

    }

    arrow = new Texture("select_arrow.png");
    arrowSprite = new Sprite(arrow);
    arrowSprite.setSize(buttonHeight, buttonHeight);
    arrowSprite.setX(easySprite.getX() + easySprite.getWidth() + 20);
    arrowSprite.setY(easySprite.getY());

  }

  /**
   * Update the scene.
   */
  public int update() {

    // Update the position of the arrow
    switch (diffDecrease) {
      case 1:
        arrowSprite.setY(easySprite.getY());
        break;
      case 2:
        arrowSprite.setY(medSprite.getY());
        break;
      case 3:
        arrowSprite.setY(hardSprite.getY());
        break;
      default:
        break;
    }

    Vector3 mousePos = fillCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

    if (hardSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {

      hardSprite.setTexture(hardHovered);

      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        System.out.println("hard");
        diffDecrease = 3;
      }

    } else {
      hardSprite.setTexture(hard);
    }
    
    if (medSprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {

      medSprite.setTexture(medHovered);

      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        System.out.println("med");
        diffDecrease = 2;
      }

    } else {
      medSprite.setTexture(med);
    }

    if (easySprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {

      easySprite.setTexture(easyHovered);

      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        System.out.println("esay");
        diffDecrease = 1;
      }
    } else {
      easySprite.setTexture(easy);
    }   

    if (readySprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {

      readySprite.setTexture(readyHovered);

      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        return 3;
      }
    } else {
      readySprite.setTexture(ready);
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
    arrowSprite.draw(batch);
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
