package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the power ups as an abstract class that extends from movable object
 * and implements the CollisionObject interface.
 *
 * @author Ben Dunbar, JavaDoc by Ben Dunbar
 */
public abstract class PowerUp extends MovableObject implements CollisionObject {
  /* ################################### //
          CONSTRUCTORS
  // ################################### */

  /**
   * A constructor for a Power-Up taking its position (x and y) and width and height.
   *
   * <p>Accepts Texture indirectly through file path. Integer for frame count not needed.
   *
   * @author Ben Dunbar
   */
  PowerUp(float x, float y, int w, int h, String texturePath) {
    super(x, y, w, h, texturePath);
    //todo implement this
  }

  /**
   * A constructor for a Power-Up taking its position (x and y) and width and height.
   *
   * <p>Accepts Texture indirectly through file path.
   * Integer for frame count needed.
   *
   * @author Ben Dunbar
   */
  PowerUp(int x, int y, int w, int h, String texturePath, int frameCount) {
    super(x, y, w, h, texturePath, frameCount);
  }

  /**
   * A constructor for a Power-Up taking its position (x and y) and width and height.
   *
   * <p>Accepts Texture directly. Integer for frame count needed.
   *
   * @author Ben Dunbar
   */
  PowerUp(int x, int y, int w, int h, Texture t, int frameCount) {
    super(x, y, w, h, t, frameCount);
  }

  /* ################################### //
          METHODS
  // ################################### */

  /**
   * Removes object from game screen once a boat has collided with it.
   *
   * @author James Frost
   * @author William Walton
   */
  public void hasCollided() {
    isShown = false;
  }
}
