package com.teamonehundred.pixelboat.entities;

/**
 * Represents a floating branch obstacle.
 *
 * @author James Frost
 * @author William Walton, JavaDoc by Umer Fakher
 */
public class ObstacleFloatingBranch extends ObstacleBranch {
  /**
   * A constructor for a floating branch obstacle taking its position (x and y).
   *
   * <p>Branch image is taken by default from C:\...\ENG1-Team-12\Implementation\core\assets.
   * Random rotation is set.
   *
   * @author James Frost
   * @author William Walton
   */
  public ObstacleFloatingBranch(float x, float y) {
    super(x, y);
    // have the branch move at a constant speed
    drag = 0;
    speed = 0.3f;
    sprite.setRotation(-180);
  }
}
