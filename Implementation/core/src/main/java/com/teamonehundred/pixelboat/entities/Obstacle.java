package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the obstacle as an abstract class that extends from movable object
 * and implements the CollisionObject interface.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public abstract class Obstacle extends MovableObject implements CollisionObject {
    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    /**
     * A constructor for an Obstacle taking its position (x and y) and width and height.
     * <p>
     * <p>
     * Accepts Texture indirectly through file path. Integer for frame count not needed.
     *
     * @author James Frost
     * @author William Walton
     */
    Obstacle(float x, float y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
        //todo implement this
    }

    /**
     * A constructor for an Obstacle taking its position (x and y) and width and height.
     * <p>
     * <p>
     * Accepts Texture indirectly through file path. Integer for frame count needed.
     *
     * @author James Frost
     * @author William Walton
     */
    Obstacle(float x, float y, int w, int h, String texture_path, int frame_count) {
        super(x, y, w, h, texture_path, frame_count);
    }

    /**
     * A constructor for an Obstacle taking its position (x and y) and width and height.
     * <p>
     * <p>
     * Accepts Texture directly. Integer for frame count needed.
     *
     * @author James Frost
     * @author William Walton
     */
    Obstacle(float x, float y, int w, int h, Texture t, int frame_count) {
        super(x, y, w, h, t, frame_count);
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
