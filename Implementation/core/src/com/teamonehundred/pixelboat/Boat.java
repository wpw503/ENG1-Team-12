package com.teamonehundred.pixelboat;

import java.util.List;

// generic boat class, never instantiated
public abstract class Boat extends MovableObject implements CollisionObject{
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    String name;

    float durability = 1.f;  // from 0 to 1
    float durability_per_hit = -.2f;
    float stamina = 1.f;  // from 0 to 1, percentage of stamina max
    float stamina_usage = .01f;
    float stamina_regen = .002f;

    List<Float>[] leg_times;  // times for every previous leg
    int start_time, end_time;  // Seconds since epoch when starting and finishing current leg

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    //default specs
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y,  w,  h, texture_path);
    }

    //specify specs
    Boat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y,  w,  h, texture_path);

        this.name = name;
        this.durability_per_hit = durability_per_hit;
        this.stamina_usage = stamina_usage;
        this.stamina_regen = stamina_regen;
    }

    /* ################################### //
                    METHODS
    // ################################### */

    public void hasCollided(){
        durability -= durability_per_hit;
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
