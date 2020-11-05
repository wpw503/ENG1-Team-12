package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class ObstacleDuck extends Obstacle{
    ObstacleDuck(int x, int y) {
        super(x, y, 30, 30, "obstacle_duck.png");
        // give each duck a random rotation
        sprite.setOriginCenter();
        sprite.setRotation((float)Math.random()*360);
        // have the duck move at a constant speed
        drag = 0;
        speed = .2f;
        rotation_speed = .2f;
    }

    @Override
    public void updatePosition() {
        turn(1);
        super.updatePosition();
    }

    @Override
    public CollisionBounds getBounds() {
        // create a new collision bounds object representing my current position
        // see the collision bounds visualisation folder in assets for a visual representation
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle r1 = new Rectangle(
                sprite.getX()+(0.09f*sprite.getWidth()),
                sprite.getY()+(0.13f*sprite.getHeight()),
                0.41f*sprite.getWidth(),
                0.4f* sprite.getHeight());
        Rectangle r2 = new Rectangle(
                sprite.getX()+(0.5f*sprite.getWidth()),
                sprite.getY()+(0.13f*sprite.getHeight()),
                0.31f*sprite.getWidth(),
                0.75f* sprite.getHeight());

        my_bounds.addBound(r1);
        my_bounds.addBound(r2);

        my_bounds.setOrigin(new Vector2(
                sprite.getX()+(sprite.getWidth()/2),
                sprite.getY()+(sprite.getHeight()/2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }
}
