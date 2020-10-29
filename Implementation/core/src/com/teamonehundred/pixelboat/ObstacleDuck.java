package com.teamonehundred.pixelboat;

public class ObstacleDuck extends Obstacle{
    ObstacleDuck(int x, int y) {
        super(x, y, 30, 30, "obstacle_duck.png");
        // give each duck a random rotation
        sprite.setOriginCenter();
        sprite.setRotation((float)Math.random()*360);
        // have the duck move at a constant speed
        drag = 0;
        speed = .2f;
        rotation_speed = .2f;
    }

    @Override
    public void updatePosition() {
        turn(1);
        super.updatePosition();
    }
}
