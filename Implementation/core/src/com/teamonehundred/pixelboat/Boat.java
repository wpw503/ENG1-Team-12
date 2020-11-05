package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

// generic boat class, never instantiated
abstract class Boat extends MovableObject implements CollisionObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    String name;

    float durability = 1.f;  // from 0 to 1
    float durability_per_hit = .2f;
    float stamina = 1.f;  // from 0 to 1, percentage of stamina max
    float stamina_usage = .01f;
    float stamina_regen = .002f;

    List<Long> leg_times;  // times for every previous leg
    long start_time, end_time;  // Seconds since epoch when starting and finishing current leg

    int frames_to_animate = 0;
    int current_animation_frame = 0;
    int frames_elapsed = 0;

    Animation<Texture> rowing_animation;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    //default specs
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path, 4);
    }

    //specify specs
    Boat(int x, int y, int w, int h, String texture_path, String name,
         float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, 4);

        this.name = name;
        this.durability_per_hit = durability_per_hit;
        this.stamina_usage = stamina_usage;
        this.stamina_regen = stamina_regen;
    }

    /* ################################### //
                    METHODS
    // ################################### */

    public void hasCollided() {
        durability -= durability - durability_per_hit <= 0 ? 0 : durability_per_hit;
    }

    @Override
    public void accelerate() {
        stamina = stamina - stamina_usage <= 0 ? 0 : stamina - stamina_usage;
        if (stamina > 0) {
            super.accelerate();
            frames_to_animate += 1;
        }

        if (frames_to_animate > 0) {
            setAnimationFrame(current_animation_frame);
            frames_elapsed++;
            if (frames_elapsed % 15 == 0)
                current_animation_frame++;
            frames_to_animate--;
        } else {
            // reset everything
            setAnimationFrame(0);
            current_animation_frame = 0;
            frames_elapsed = 0;
            frames_to_animate = 0;
        }
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        stamina = stamina + stamina_regen >= 1 ? 1.f : stamina + stamina_regen;
    }

    public void setStartTime(long start_time){
        this.start_time = start_time;
    }

    public long getStartTime(boolean inSeconds){
        if (inSeconds){
            return this.start_time/1000; // Milliseconds to Seconds conversion 1000:1
        }
        return this.start_time;
    }

    public void setEndTime(long end_time){
        this.start_time = end_time;
    }

    public long getEndTime(boolean inSeconds){
        if (inSeconds){
            return this.end_time/1000; // Milliseconds to Seconds conversion 1000:1
        }
        return this.end_time;
    }

    public long getCalcTime(){
        return this.end_time - this.start_time;
    }

    public void setLegTime(){
        this.leg_times.add(this.getCalcTime());
    }

    public void checkCollisions(CollisionObject obstacle){ }

}
