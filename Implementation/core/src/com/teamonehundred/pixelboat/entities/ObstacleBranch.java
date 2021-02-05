package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the simple branch obstacle.
 *
 * @author James Frost
 * @author William Walton
   JavaDoc by Umer Fakher
 */
public class ObstacleBranch extends Obstacle {

  /**
  * A constructor for an Obstacle taking its position (x and y).
  * Branch image is taken by default from C:\...\ENG1-Team-12\Implementation\core\assets.
  * Random rotation is set.
  *
  * @author James Frost
  * @author William Walton
  */
  public ObstacleBranch(int x, int y) {
    super(x, y, 60, 60, "obstacle_branch.png");
    sprite.setRotation(-90 + (float) Math.random() * 180);
  }

  /**
  * Returns a new collision bounds object for the branch obstacle.
  * Creates a new collision bounds object representing the current position of this branch.
  * See the collision bounds visualisation folder in assets for a visual representation.
  *
  * @return CollisionBounds of branch obstacle
  * @author James Frost
  * @author William Walton
  */
  @Override
  public CollisionBounds getBounds() {
    CollisionBounds myBounds = new CollisionBounds();
    Rectangle mainRect = new Rectangle(
        sprite.getX() + (0.31f * sprite.getWidth()),
        sprite.getY() + (0.06f * sprite.getHeight()),
        0.31f * sprite.getWidth(),
        0.88f * sprite.getHeight());
    myBounds.addBound(mainRect);
    myBounds.setOrigin(new Vector2(
        sprite.getX() + (sprite.getWidth() / 2),
        sprite.getY() + (sprite.getHeight() / 2)));
    myBounds.setRotation(sprite.getRotation());
    return myBounds;
  }
  
}
