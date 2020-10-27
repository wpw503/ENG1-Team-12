package com.teamonehundred.pixelboat;

import java.util.List;

// generic boat class, never instantiated
public abstract class Boat extends MovableObject implements CollisionObject{
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    String name;
    float durability = 1.f;  // from 0 to 1
    List<Float>[] leg_times;  // times for every previous leg
    int stamina_max;
    float stamina = 1.f;  // from 0 to 1, percentage of stamina max
    float stamina_usage = .01f;
    float stamina_regen = .002f;

    int start_time, end_time;  // Seconds since epoch when starting and finishing current leg

    /* ################################### //
                    METHODS
    // ################################### */

    //constructor
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y,  w,  h, texture_path);
    }

    public void hasCollided(){
        durability -= 0.2f;
    }

    @Override
    public void accelerate(){
        stamina = stamina - stamina_usage <= 0 ? 0 : stamina - stamina_usage;
        if (stamina > 0)
            super.accelerate();
    }

    @Override
    public double updatePosition(){
        stamina = stamina + stamina_regen >= 1 ? 1.f : stamina + stamina_regen;
        return super.updatePosition();
    }
}
