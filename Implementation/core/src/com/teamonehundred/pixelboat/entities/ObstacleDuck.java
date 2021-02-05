package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the simple duck obstacle.
 *
 * @author James Frost
 * @author William Walton
   JavaDoc by Umer Fakher
 */
public class ObstacleDuck extends Obstacle {

  /**
   * A constructor for an Obstacle taking its position (x and y).
   * Duck image is taken by default from C:\...\ENG1-Team-12\Implementation\core\assets.
   * Random rotation is set.
   *
   * @author James Frost
   * @author William Walton
   */
  public ObstacleDuck(int x, int y) {
    super(x, y, 30, 30, "obstacle_duck.png");
    // give each duck a random rotation
    sprite.setOriginCenter();
    sprite.setRotation((float) Math.random() * 360);
    // have the duck move at a constant speed
    drag = 0;
    speed = .2f;
    rotation_speed = .2f;
  }

  /**
   * Updates position of duck obstacle and turns by 1 point.
   */
  @Override
  public void updatePosition() {
    turn(1);
    super.updatePosition();
  }
  
  /**
  * Returns a new collision bounds object for the duck obstacle.
  * Creates a new collision bounds object representing the current position of this duck.
  * See the collision bounds visualisation folder in assets for a visual representation.
  *
  * @return CollisionBounds of duck obstacle
  * @author James Frost
  * @author William Walton
  */
  @Override
  public CollisionBounds getBounds() {
    CollisionBounds myBounds = new CollisionBounds();
    Rectangle r1 = new Rectangle(
        sprite.getX() + (0.09f * sprite.getWidth()),
        sprite.getY() + (0.13f * sprite.getHeight()),
        0.41f * sprite.getWidth(),
        0.4f * sprite.getHeight());
    Rectangle r2 = new Rectangle(
        sprite.getX() + (0.5f * sprite.getWidth()),
        sprite.getY() + (0.13f * sprite.getHeight()),
        0.31f * sprite.getWidth(),
        0.75f * sprite.getHeight());
    myBounds.addBound(r1);
    myBounds.addBound(r2);
    myBounds.setOrigin(new Vector2(
        sprite.getX() + (sprite.getWidth() / 2),
        sprite.getY() + (sprite.getHeight() / 2)));
    myBounds.setRotation(sprite.getRotation());
    return myBounds;
  }
}
