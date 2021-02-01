package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the simple branch obstacle.
 *
 * @author Ben Dunbar
 * JavaDoc by 
 */
public class PowerUpHealth extends PowerUp {

    /**
     * A constructor for an PowerUp taking its position (x and y).
     * <p>
     * <p>
     * A power up image is taken by default from C:\...\ENG1-Team-12\Implementation\core\assets.
     * Rotation is set.
     *
     * @author Ben Dunbar
     */
    public PowerUpHealth(float x, float y) {
        super(x, y, 60, 60, "power_up_health.png");
        sprite.setRotation(0);
    }

    /**
     * Returns a new collision bounds object for the branch obstacle.
     * <p>
     * <p>
     * Creates a new collision bounds object representing the current position of this branch.
     * See the collision bounds visualisation folder in assets for a visual representation.
     *
     * @return CollisionBounds of branch obstacle
     * @author James Frost
     * @author William Walton
     */
    @Override
    public CollisionBounds getBounds() {
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = new Rectangle(
                sprite.getX() + (0.31f * sprite.getWidth()),
                sprite.getY() + (0.06f * sprite.getHeight()),
                0.31f * sprite.getWidth(),
                0.88f * sprite.getHeight());
        my_bounds.addBound(main_rect);

        my_bounds.setOrigin(new Vector2(
                sprite.getX() + (sprite.getWidth() / 2),
                sprite.getY() + (sprite.getHeight() / 2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }
}
