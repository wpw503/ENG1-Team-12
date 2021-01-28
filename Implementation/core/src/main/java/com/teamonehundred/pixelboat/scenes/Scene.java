package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents the interface for all Scenes.
 *
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public interface Scene {
    /**
     * Draw method for displaying text and objects onto the screen.
     *
     * @param batch SpriteBatch used for drawing to screen.
     */
    void draw(SpriteBatch batch);

    /**
     * Update function for Scene. Ends Scene based on user input otherwise stays in scene.
     *
     * @return returns an integer which is the scene_id of which screen is next (either this screen still or another)
     */
    int update();

    /**
     * Resize method if needed for camera extension.
     *
     * @param width  Integer width to be resized to
     * @param height Integer height to be resized to
     */
    void resize(int width, int height);
}
