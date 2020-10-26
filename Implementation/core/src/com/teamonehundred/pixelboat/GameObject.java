package com.teamonehundred.pixelboat;

import java.lang.Math;

public abstract class GameObject {
    // coordinates are world coordinates relative to the object's lane
    // rotation is in radians, 0 being up, -n being some amount left, n being some amount right
    int x, y, terminal_speed, speed;
    float rotation;

    public void turn(float amount){
        rotation += amount;
    }

    // move forwards x in whatever direction currently facing
    public void move(int distance){
        double dx = Math.cos((Math.PI/2) - Math.abs(rotation)) * distance;
        double dy = Math.sin((Math.PI/2) - Math.abs(rotation)) * distance;

        x += rotation < 0 ? -dx : dx;
        y += dy;
    }
}
