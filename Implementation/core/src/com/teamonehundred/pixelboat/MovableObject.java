package com.teamonehundred.pixelboat;

abstract class MovableObject extends GameObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected float max_speed = 10;
    protected float speed = 0;
    protected float drag = .04f;  // amount speed is reduced by every frame naturally
    protected float acceleration = .2f;
    protected float rotation_speed = 2.f;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    MovableObject(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
    }

    MovableObject(int x, int y, int w, int h, String texture_path, int frame_count) {
        super(x, y, w, h, texture_path, frame_count);
    }

    /* ################################### //
                    METHODS
    // ################################### */

    // turn left (1) or right (-1)
    public void turn(int amount) {
        sprite.rotate(amount * rotation_speed);
    }

    // move forwards x in whatever direction currently facing
    // return amount moved in y for camera scrolling
    private void move(float distance) {
        double dy = Math.cos((Math.toRadians(sprite.getRotation()))) * distance;
        double dx = Math.sin((Math.toRadians(sprite.getRotation()))) * distance;

        sprite.translate((float) (-dx), (float) dy);
    }

    public void updatePosition() {
        move(speed);
        speed -= speed - drag < 0 ? speed : drag;
    }

    public void accelerate() {
        speed += speed >= max_speed ? 0 : acceleration;
    }

    public void reset_motion() {
        speed = 0;
        sprite.setRotation(0);
    }
}
