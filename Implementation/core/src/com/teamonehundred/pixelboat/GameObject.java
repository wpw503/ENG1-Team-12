package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represents a game object.
 * <p>
 * The GameObject class that everything visible in the game is derived from.
 * Contains texture and positional information.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
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
    protected Texture texture;
    /**
     * Stores the texture of the object and positional information (x and y coordinates, width and height, rotation)
     */
    protected Sprite sprite;
    /**
     * Used to determine if the object should be rendered or not. Also used in collision detection
     */
    protected Boolean is_shown;

    // set to null if not animated
    /**
     * The array of frames used for animations, stored as TextureRegion s
     */
    protected TextureRegion[] animation_regions;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    /**
     * A constructor for GameObject for static textures.
     *
     * @param x            int for horizontal position of object
     * @param y            int for vertical position of object
     * @param w            int for width of object
     * @param h            int for height of object
     * @param texture_path String of object's file path
     */
    GameObject(int x, int y, int w, int h, final String texture_path) {
        initialise(x, y, w, h, texture_path);

        animation_regions = null;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    // animation

    /**
     * A constructor for GameObject for animation textures.
     *
     * @param x            int for horizontal position of object
     * @param y            int for vertical position of object
     * @param w            int for width of object
     * @param h            int for height of object
     * @param texture_path String of object's file path
     * @param frame_count  int frame count
     */
    GameObject(int x, int y, int w, int h, final String texture_path, int frame_count) {
        initialise(x, y, w, h, texture_path);

        animation_regions = new TextureRegion[frame_count];
        float texture_width = 1f / (frame_count);
        for (int i = 0; i < frame_count; i++) {
            animation_regions[i] = new TextureRegion(texture, i * texture_width, 0f, (i + 1) * texture_width, 1f);
        }

        sprite = new Sprite(animation_regions[0]);
        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    /**
     * A constructor for GameObject.
     *
     * @param x           int for horizontal position of object
     * @param y           int for vertical position of object
     * @param w           int for width of object
     * @param h           int for height of object
     * @param texture     Direct Texture
     * @param frame_count int frame count
     */
    GameObject(int x, int y, int w, int h, Texture texture, int frame_count) {
        this.texture = texture;
        is_shown = true;

        animation_regions = new TextureRegion[frame_count];
        float texture_width = 1f / (frame_count);
        for (int i = 0; i < frame_count; i++) {
            animation_regions[i] = new TextureRegion(texture, i * texture_width, 0f, (i + 1) * texture_width, 1f);
        }

        sprite = new Sprite(animation_regions[0]);
        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    /**
     * A constructor for GameObject.
     *
     * @param x            int for horizontal position of object
     * @param y            int for vertical position of object
     * @param w            int for width of object
     * @param h            int for height of object
     * @param texture_path String of object's file path
     */
    void initialise(int x, int y, int w, int h, final String texture_path) {
        texture = new Texture(texture_path);
        is_shown = true;
    }

    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    protected void finalize() {
        texture.dispose();
    }

    /* ################################### //
                    METHODS
    // ################################### */

    /**
     * Returns true if GameObject should be shown otherwise false.
     *
     * @return is_shown boolean
     */
    public boolean isShown() {
        return is_shown;
    }

    /**
     * Getter for GameObject sprite.
     *
     * @return Sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Returns a new collision bounds object for the GameObject.
     * <p>
     * <p>
     * Creates a new collision bounds object representing the current position of this GameObject.
     * See the collision bounds visualisation folder in assets for a visual representation.
     *
     * @return CollisionBounds object of GameObject
     * @author James Frost
     * @author William Walton
     */
    public CollisionBounds getBounds() {
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = sprite.getBoundingRectangle();  // default is to use whole sprite
        my_bounds.addBound(main_rect);
        return my_bounds;
    }

    /**
     * Sets GameObject sprite's animation region according to integer passed in as long as the animation
     * regions is not null.
     *
     * @param i int
     */
    public void setAnimationFrame(int i) {
        if (animation_regions != null)
            sprite.setRegion(animation_regions[i % animation_regions.length]);
    }
}
