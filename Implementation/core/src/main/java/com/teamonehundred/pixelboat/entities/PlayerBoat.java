package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user player's boat.
 *
 * @author James Frost
 * @author William Walton, JavaDoc by Umer Fakher
 */
public class PlayerBoat extends Boat {
  /* ################################### //
           ATTRIBUTES
  // ################################### */

  protected OrthographicCamera camera;

  protected Texture staminaTexture;
  protected Texture durabilityTexture;

  protected Sprite staminaBar;
  protected Sprite durabilityBar;

  public int uiBarWidth = 500;

  /* ################################### //
          CONSTRUCTORS
  // ################################### */

  /**
   * Construct a PlayerBoat object at point (x,y) with default size, texture and animation.
   *
   * @param x float coordinate for the bottom left point of the boat
   * @param y float coordinate for the bottom left point of the boat
   * @author William Walton
   */
  public PlayerBoat(float x, float y) {
    super(x, y);

    initialise();
  }

  /**
   * Construct a PlayerBoat object with at point (x,y) with width and height and texture path
   * with default stats (stamina usage, durability, etc).
   *
   * @param x      int coordinate for the bottom left point of the boat
   * @param y      int coordinate for the bottom left point of the boat
   * @param w      int width of the new boat
   * @param h      int height of the new boat
   * @param texturePath String relative path from the core/assets folder of the boats texture image
   * @author William Walton
   */
  PlayerBoat(int x, int y, int w, int h, String texturePath) {
    super(x, y, w, h, texturePath);

    initialise();
  }

  /**
   * Construct a PlayerBoat object with all parameters specified.
   *
   * @param x          int coordinate for the bottom left point of the boat
   * @param y          int coordinate for the bottom left point of the boat
   * @param w          int width of the new boat
   * @param h          int height of the new boat
   * @param texturePath   relative path from the core/assets folder of the boats texture image
   * @param durabilityPerHit float percentage (0-1) of the max durability taken each hit
   * @param name         String of the boat seen when the game ends
   * @param staminaRegen    float percentage of stamina regenerated each frame (0-1)
   * @param staminaUsage    float percentage of stamina used each frame when accelerating (0-1)
   * @author William Walton
   */
  PlayerBoat(int x, int y, int w, int h, String texturePath, String name,
             float durabilityPerHit, float staminaUsage, float staminaRegen) {
    super(x, y, w, h, texturePath, name, durabilityPerHit, staminaUsage, staminaRegen);

    initialise();
  }

  /*
   * Destructor disposes of this texture once it is no longer referenced.
   */
  // protected void finalize() {
  //   stamina_texture.dispose();
  //   durability_texture.dispose();
  // }

  /* ################################### //
          METHODS
  // ################################### */

  /**
   * Shared initialisation functionality among all constructors.
   *
   * <p>Sets stamina bar and durability bar textures and sprites.
   * Initialises the bars' size and position.
   * Initialises camera position.
   */
  public void initialise() {
    staminaTexture = new Texture("stamina_texture.png");
    durabilityTexture = new Texture("durability_texture.png");

    staminaBar = new Sprite(staminaTexture);
    durabilityBar = new Sprite(durabilityTexture);

    staminaBar.setSize(uiBarWidth, 10);
    durabilityBar.setSize(uiBarWidth, 10);


    staminaBar.setPosition(-uiBarWidth / 2, 5);
    durabilityBar.setPosition(-uiBarWidth / 2, 20);

    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(sprite.getX(), Gdx.graphics.getHeight() / 3 + sprite.getY(), 0);
    camera.update();
  }

  /**
   * Sets the spec type of boat.
   *
   * <p>Can be in these states:
   * - debug
   * - default
   * - fast low durability
   *
   * @param specId int for boat spec
   */
  public void setSpec(int specId) {
    switch (specId) {
      case 0:
        // debug
        staminaUsage = 0f;
        durabilityPerHit = 0f;
        break;
      case 1:
        // default
        break;
      case 2:
        // fast low durability
        maxSpeed = 20;
        durabilityPerHit = .2f;
        break;
      default:
        break;
    }
  }

  /**
   * Set the difficulty level.
   *
   * @param diffDecrease difficulty level
   */
  public void setDiff(int diffDecrease) {
    if (diffDecrease == 2) {
      staminaRegen *= 0.7f; //decrease 0-1
      staminaUsage *= 1.3f; //increase 0-1
      durabilityPerHit *= 2.0f; //increase 0-1
    }
    if (diffDecrease == 3) {
      staminaRegen *= 0.4f;
      staminaUsage *= 1.7f;
      durabilityPerHit *= 3.0f;
    }
  }

  /**
   * Updates the position based on the user's input.
   *
   * <p>'W' key accelerates the boat.
   * 'A' Turns the boat to the left
   * 'D' Turns the boat to the right
   *
   * <p>Updates the x and y position of the sprite with
   * new x and y according to which input has been requested.
   * The camera will follow the player's boat.
   *
   * @author William Walton
   */
  @Override
  public void updatePosition() {
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      this.accelerate();
    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      // nothing atm
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      this.turn(1);
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      this.turn(-1);
    }

    float oldX = sprite.getX();
    float oldY = sprite.getY();

    super.updatePosition();

    // only follow player in x axis if they go off screen
    float dx = Math.abs(sprite.getX()) > Gdx.graphics.getWidth() / 3 ? sprite.getX() - oldX : 0;
    float dy = sprite.getY() - oldY;

    // move camera to follow player
    camera.translate(dx, dy, 0);
  }

  /**
   * Returns the all sprites for PlayerBoat UI.
   *
   * <p>This includes the stamina bar and durability bar.
   *
   * @return List of Sprites
   */
  public List<Sprite> getUiSprites() {
    updateUiSprites();  // TODO: probably move this to only when they change rather than every frame

    List<Sprite> ret = new ArrayList<Sprite>();
    ret.add(staminaBar);
    ret.add(durabilityBar);
    return ret;
  }

  /**
   * Getter for PlayerBoat Camera.
   *
   * @return OrthographicCamera
   */
  public OrthographicCamera getCamera() {
    return camera;
  }

  /**
   * Resets PlayerBoat Camera position.
   */
  public void resetCameraPos() {
    camera.position.set(sprite.getX(), Gdx.graphics.getHeight() / 3, 0);
    camera.update();
  }

  /**
   * Update the position and size of the UI elements
   * (e.g. stamina bar and durability bar) according to their values.
   *
   * <p>The stamina decreases as player requests the boat to row and move.
   * It increases when this is not the case.
   * Durability decreases according to the collisions with other obstacles.
   * Dynamically updates the size of the stamina bar and durability bar
   * based on the PlayerBoat attributes as they change.
   */
  private void updateUiSprites() {
    staminaBar.setPosition(-uiBarWidth / 2 + sprite.getX() + sprite.getWidth() / 2,
                           -50 + sprite.getY());
    durabilityBar.setPosition(-uiBarWidth / 2 + sprite.getX() + sprite.getWidth() / 2,
                              -35 + sprite.getY());

    staminaBar.setSize((int) (uiBarWidth * stamina), 10);
    durabilityBar.setSize((int) (uiBarWidth * durability), 10);
  }

}
