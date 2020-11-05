package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * The class that everything visible in the game is derived from.
 * Contains texture and positional information.
 */
abstract class GameObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    // coordinates are world coordinates relative to the bottom left of object
    // rotation is in degrees
    // width, height, x, y, and rotation are stored in sprite

    /**
     * Stores the image rendered when the object is shown.
     */
    Texture texture;
    /**
     * Stores the texture of the object and positional information (x and y coordinates, width and height, rotation)
     */
    Sprite sprite;
    /**
     * Used to determine if the object should be rendered or not. Also used in collision detection
     */
    Boolean is_shown;

    // set to null if not animated
    TextureRegion[] animation_regions;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    // static texture
    GameObject(int x, int y, int w, int h, final String texture_path) {
        initialise(x, y, w, h, texture_path);

        animation_regions = null;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    // animation
    GameObject(int x, int y, int w, int h, final String texture_path, int frame_count) {
        initialise(x, y, w, h, texture_path);

        animation_regions = new TextureRegion[frame_count];
        float texture_width = 1f/(frame_count);
        for(int i = 0; i < frame_count; i++){
            animation_regions[i] = new TextureRegion(texture, i*texture_width, 0f, (i+1)*texture_width, 1f);
        }

        sprite = new Sprite(animation_regions[0]);
        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    void initialise(int x, int y, int w, int h, final String texture_path) {
        texture = new Texture(texture_path);
        is_shown = true;
    }

    // destructor
    protected void finalize() {
        texture.dispose();
    }

    /* ################################### //
                    METHODS
    // ################################### */

    public boolean isShown() {
        return is_shown;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public void setAnimationFrame(int i){
        if(animation_regions != null)
            sprite.setRegion(animation_regions[i%animation_regions.length]);
    }
}
