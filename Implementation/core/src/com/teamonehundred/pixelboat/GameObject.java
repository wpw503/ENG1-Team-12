package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    // coordinates are world coordinates relative to the bottom left of object
    // rotation is in degrees
    // width, height, x, y, and rotation are stored in sprite
    // drag is amount speed is reduced by every frame naturally
    float terminal_speed = 10;
    float speed = 0;
    float drag = .05f;
    float acceleration = .2f;

    Texture texture;
    Sprite sprite;
    Boolean is_shown;

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

    public void turn(float amount) {
        sprite.rotate(amount);
    }

    // move forwards x in whatever direction currently facing
    // return amount moved in y for camera scrolling
    private double move(float distance) {
        double dy = Math.cos((Math.toRadians(sprite.getRotation()))) * distance;
        double dx = Math.sin((Math.toRadians(sprite.getRotation()))) * distance;

        sprite.translate((float) (-dx), (float) dy);

        return dy;
    }

    public double updatePosition(){
        double ret = move(speed);
        speed -= speed - drag < 0 ? speed : drag;
        return ret;
    }

    public void accelerate(){
        speed += speed >= terminal_speed ? 0 : acceleration;
    }

    public boolean isShown() {
        return is_shown;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds(){return sprite.getBoundingRectangle();}
}
