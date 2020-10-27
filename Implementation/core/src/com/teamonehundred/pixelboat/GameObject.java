package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import jdk.nashorn.internal.objects.annotations.Getter;

public abstract class GameObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    // coordinates are world coordinates relative to the bottom left of object
    // rotation is in degrees
    // width, height, x, y, and rotation are stored in sprite

    Texture texture;
    Sprite sprite;
    Boolean is_shown;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    GameObject(int x, int y, int w, int h, String texture_path) {
        texture = new Texture(texture_path);
        sprite = new Sprite(texture);
        is_shown = true;

        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
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

    public Rectangle getBounds(){return sprite.getBoundingRectangle();}
}
