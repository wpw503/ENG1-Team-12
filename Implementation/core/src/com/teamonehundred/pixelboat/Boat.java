package com.teamonehundred.pixelboat;

import java.util.List;

// generic boat class, never instantiated
public abstract class Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    String name;
    float durability;  // from 0 to 1
    List<Float>[] leg_times;  // times for every previous leg
    int stamina_max;
    float stamina;  // from 0 to 1, percentage of stamina max

    int start_time, end_time;  // Seconds since epoch when starting and finishing current leg

    /* ################################### //
                    METHODS
    // ################################### */

    //constructor
    Boat() {
        //todo implement this
    }

    public void draw(){
        //todo work out how to do this
    }


}
