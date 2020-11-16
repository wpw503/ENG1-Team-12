package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the movable object as an abstract class that extends from game object.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
abstract class MovableObject extends GameObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected float max_speed = 15;
    protected float speed = 0;
    protected float drag = .04f;  // amount speed is reduced by every frame naturally
    protected float acceleration = .2f;
    protected float rotation_speed = 2.f;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    /**
     * A constructor for MovableObject.
     *
     * @param x            int for horizontal position of object
     * @param y            int for vertical position of object
     * @param w            int for width of object
     * @param h            int for height of object
     * @param texture_path String of object's file path
     */
    MovableObject(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
    }


    /**
     * A constructor for MovableObject.
     *
     * @param x            int for horizontal position of object
     * @param y            int for vertical position of object
     * @param w            int for width of object
     * @param h            int for height of object
     * @param texture_path String of object's file path
     * @param frame_count  int frame count
     */
    MovableObject(int x, int y, int w, int h, String texture_path, int frame_count) {
        super(x, y, w, h, texture_path, frame_count);
    }

    /**
     * A constructor for MovableObject.
     *
     * @param x           int for horizontal position of object
     * @param y           int for vertical position of object
     * @param w           int for width of object
     * @param h           int for height of object
     * @param t           Direct Texture
     * @param frame_count int frame count
     */
    MovableObject(int x, int y, int w, int h, Texture t, int frame_count) {
        super(x, y, w, h, t, frame_count);
    }

    /* ################################### //
                    METHODS
    // ################################### */

    /**
     * Rotates the Movable object by some given value.
     * <p>
     * Note: turn left (1) or right (-1)
     *
     * @param amount Integer value that dictates how much the movable object will be rotated
     * @author James Frost
     * @author William Walton
     */
    public void turn(int amount) {
        sprite.rotate(amount * rotation_speed);
    }


    /**
     * Move forwards x, y in whatever direction currently facing.
     *
     * @param distance to be moved
     * @author James Frost
     * @author William Walton
     */
    private void move(float distance) {
        double dy = Math.cos((Math.toRadians(sprite.getRotation()))) * distance;
        double dx = Math.sin((Math.toRadians(sprite.getRotation()))) * distance;

        sprite.translate((float) (-dx), (float) dy);
    }

    /**
     * Updates position of movable object based on speed and decreases speed according to drag calculation.
     *
     * @author James Frost
     * @author William Walton
     */
    public void updatePosition() {
        move(speed);
        speed -= speed - drag < 0 ? speed : drag;
    }

    /**
     * Increase speed based on current acceleration attribute.
     * <p>
     * If max_speed (terminal velocity) is reached for the movable object then don't increase speed.
     *
     * @author James Frost
     * @author William Walton
     */
    public void accelerate() {
        speed += speed >= max_speed ? 0 : acceleration;
    }

    /**
     * Resets speed to 0 and rotation to 0.
     */
    public void reset_motion() {
        speed = 0;
        sprite.setRotation(0);
    }
}
