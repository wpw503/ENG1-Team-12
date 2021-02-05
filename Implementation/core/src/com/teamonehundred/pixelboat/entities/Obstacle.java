package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the obstacle as an abstract class that extends from movable object
 * and implements the CollisionObject interface.
 *
 * @author James Frost
 * @author William Walton
   JavaDoc by Umer Fakher
 */
public abstract class Obstacle extends MovableObject implements CollisionObject {

  /* ################################### //
                CONSTRUCTORS
  // ################################### */
  /**
   * A constructor for an Obstacle taking its position (x and y) and width and height.
   * Accepts Texture indirectly through file path. Integer for frame count not needed.
   *
   * @author James Frost
   * @author William Walton
   */
  Obstacle(int x, int y, int w, int h, String texturePath) {
    super(x, y, w, h, texturePath);
    //todo implement this
  }

  /**
  * A constructor for an Obstacle taking its position (x and y) and width and height.
  * Accepts Texture indirectly through file path. Integer for frame count needed.
  *
  * @author James Frost
  * @author William Walton
  */
  Obstacle(int x, int y, int w, int h, String texturePath, int frameCount) {
    super(x, y, w, h, texturePath, frameCount);
  }

  /**
  * A constructor for an Obstacle taking its position (x and y) and width and height.
  * Accepts Texture directly. Integer for frame count needed.
  *
  * @author James Frost
  * @author William Walton
  */
  Obstacle(int x, int y, int w, int h, Texture t, int frameCount) {
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
    is_shown = false;
  }
  
}
