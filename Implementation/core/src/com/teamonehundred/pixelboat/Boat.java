package com.teamonehundred.pixelboat;

import java.util.List;

// generic boat class, never instantiated
public abstract class Boat extends GameObject implements CollisionObject{
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    String name;
    float durability = 1.f;  // from 0 to 1
    List<Float>[] leg_times;  // times for every previous leg
    int stamina_max;
    float stamina = 1.f;  // from 0 to 1, percentage of stamina max

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
}
