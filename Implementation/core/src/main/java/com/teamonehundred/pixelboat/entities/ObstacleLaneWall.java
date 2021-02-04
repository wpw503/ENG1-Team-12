package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a lane wall obstacle.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public class ObstacleLaneWall extends Obstacle {
    // Class attributes shared by all instances
    public static int texture_height = 64;

    /**
     * A constructor for an lane wall obstacle taking its position (x and y).
     * <p>
     * <p>
     * Image is taken by default from C:\...\ENG1-Team-12\Implementation\core\assets.
     *
     * @author James Frost
     * @author William Walton
     */
    public ObstacleLaneWall(float x, float y, Texture texture) {
        super(x, y, 32, texture_height, texture, 2);
        setAnimationFrame(0);
    }

    public ObstacleLaneWall(float x, float y, String texture_path) {
        super(x, y, 32, texture_height, texture_path, 2);
        setAnimationFrame(0);
    }

    public void setAnimationFrame(int i) {
        super.setAnimationFrame(i);
    }

    @Override
    public void hasCollided() {
        setAnimationFrame(1);
    }
}
