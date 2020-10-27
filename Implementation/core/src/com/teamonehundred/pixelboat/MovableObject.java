package com.teamonehundred.pixelboat;

public abstract class MovableObject extends GameObject {
    // drag is amount speed is reduced by every frame naturally
    float terminal_speed = 10;
    float speed = 0;
    float drag = .05f;
    float acceleration = .2f;

    MovableObject(int x, int y, int w, int h, String texture_path) {
        super(x, y,  w,  h, texture_path);
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
}
