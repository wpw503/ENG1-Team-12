package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

// simple obstacle that doesn't move
class ObstacleBranch extends Obstacle {
    ObstacleBranch(int x, int y) {
        super(x, y, 60, 60, "obstacle_branch.png");
        sprite.setRotation(-90 + (float)Math.random() * 180);
    }

    @Override
    public CollisionBounds getBounds() {
        // create a new collision bounds object representing my current position
        // see the collision bounds visualisation folder in assets for a visual representation
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = new Rectangle(
                sprite.getX()+(0.31f*sprite.getWidth()),
                sprite.getY()+(0.06f*sprite.getHeight()),
                0.31f*sprite.getWidth(),
                0.88f* sprite.getHeight());
        my_bounds.addBound(main_rect);

        my_bounds.setOrigin(new Vector2(
                sprite.getX()+(sprite.getWidth()/2),
                sprite.getY()+(sprite.getHeight()/2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }
}
