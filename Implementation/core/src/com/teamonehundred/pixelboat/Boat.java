package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

import java.util.ArrayList;
import java.util.Arrays;
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
    float stamina_usage = .005f;
    float stamina_regen = .002f;

    List<Long> leg_times = new ArrayList<>();  // times for every previous leg
    long start_time = -1;
    long end_time = -1;  // ms since epoch when starting and finishing current leg
    long time_to_add = 0;  // ms to add to the end time for this leg. Accumulated by crossing the lines

    int frames_to_animate = 0;
    int current_animation_frame = 0;
    int frames_elapsed = 0;

    Animation<Texture> rowing_animation;

    CollisionBounds bounds;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    //default specs
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path, 4);

        bounds = new CollisionBounds(new ArrayList<Shape2D>(Arrays.asList(new Rectangle(0, 0, 1, 2))));
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

    /**
     * Sets the start time of a boat in milliseconds.
     * <p>
     * E.g. Pass use System.currentTimeMillis() to get current system time and pass this long into this method.
     *
     * @param start_time long value which is start time of the boat.
     * @author Umer Fakher
     */
    public void setStartTime(long start_time) {
        this.start_time = start_time;
    }

    /**
     * Returns the long value start time of the boat.
     *
     * @param inSeconds boolean to decide if the time should be returned in seconds or in milliseconds.
     * @return the long value start time
     * @author Umer Fakher
     */
    public long getStartTime(boolean inSeconds) {
        if (inSeconds) {
            return this.start_time / 1000; // Milliseconds to Seconds conversion 1000:1
        }
        return this.start_time;
    }


    /**
     * Sets the end time of a boat in milliseconds.
     * <p>
     * E.g. Pass use System.currentTimeMillis() to get current system time and pass this long into this method.
     *
     * @param end_time long value which is end time of the boat.
     * @author Umer Fakher
     */
    public void setEndTime(long end_time) {
        this.end_time = end_time;
    }

    /**
     * Returns the long value end time of the boat.
     *
     * @param inSeconds boolean to decide if the time should be returned in seconds or in milliseconds.
     * @return the long value end time
     * @author Umer Fakher
     */
    public long getEndTime(boolean inSeconds) {
        if (inSeconds) {
            return this.end_time / 1000; // Milliseconds to Seconds conversion 1000:1
        }
        return this.end_time;
    }

    /**
     * Returns the difference between the end time and start time in milliseconds.
     *
     * @return long value time difference
     * @author Umer Fakher
     */
    public long getCalcTime() {
        return time_to_add + (this.end_time - this.start_time);
    }

    /**
     * Adds the difference between end time and start time into the leg times list as a long value.
     *
     * @author Umer Fakher
     */
    public void setLegTime() {
        this.leg_times.add(this.getCalcTime());
        ;
    }

    public List<Long> getLegTimes() {
        return leg_times;
    }

    public long getTimeToAdd() {
        return time_to_add;
    }

    public void setTimeToAdd(long time_to_add) {
        this.time_to_add = time_to_add;
    }

    /**
     * Checks to see if the this boat has collided with the other CollisionObject object passed.
     *
     * @param object The CollisionObject that will be checked to see if it has hit this boat.
     */
    public void checkCollisions(CollisionObject object) {
        if (bounds.isColliding(object.getBounds())) {
            hasCollided();
            object.hasCollided();
        }
    }
}
