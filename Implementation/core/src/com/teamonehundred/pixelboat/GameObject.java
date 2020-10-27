package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject {
    // coordinates are world coordinates relative to the bottom left of object
    // rotation is in degrees
    // width, height, x, y, and rotation are stored in sprite
    int terminal_speed, speed;

    Texture texture;
    Sprite sprite;

    GameObject(int x, int y, int w, int h, String texture_path) {
        texture = new Texture(texture_path);
        sprite = new Sprite(texture);

        sprite.setPosition(x, y);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    // destructor
    protected void finalize() {
        texture.dispose();
    }

    public void turn(float amount) {
        sprite.rotate(amount);
    }

    // move forwards x in whatever direction currently facing
    // return amount moved in y for camera scrolling
    public double move(int distance) {
        double dy = Math.cos( (Math.toRadians(sprite.getRotation()))) * distance;
        double dx = Math.sin( (Math.toRadians(sprite.getRotation()))) * distance;

        sprite.translate((float)(-dx), (float)dy);

        return dy;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
