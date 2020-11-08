package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// generic boat class, never instantiated
/**
 * Base class for all boat types. Contains all functionality for moving, taking damage and collision
 *
 * @author William Walton
 * @author Umer Fakher
 */
abstract class Boat extends MovableObject implements CollisionObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected String name;

    protected float durability = 1.f;  // from 0 to 1
    protected float durability_per_hit = .2f;
    protected float stamina = 1.f;  // from 0 to 1, percentage of stamina max
    protected float stamina_usage = .0f;  //todo change this after testing
    protected float stamina_regen = .002f;

    protected List<Long> leg_times = new ArrayList<>();  // times for every previous leg
    protected long start_time = -1;
    protected long end_time = -1;  // ms since epoch when starting and finishing current leg
    protected long time_to_add = 0;  // ms to add to the end time for this leg. Accumulated by crossing the lines

    protected int frames_to_animate = 0;
    protected int current_animation_frame = 0;
    protected int frames_elapsed = 0;

    protected Animation<Texture> rowing_animation;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    //default specs
    /**
     * Construct a Boat object with default size, texture and animation at point (x,y)
     *
     * @param x x coordinate for the bottom left point of the boat
     * @param y y coordinate for the bottom left point of the boat
     * @author William Walton
     */
    Boat(int x, int y){
        super(x, y, 80, 100, "boat.png", 4);
    }

    /**
     * Construct a Boat object with default stats (stamina usage, durability, etc)
     *
     * @param x x coordinate for the bottom left point of the boat
     * @param y y coordinate for the bottom left point of the boat
     * @param w the width of the new boat
     * @param h the height of the new boat
     * @param texture_path the relative path from the core/assets folder of the boats texture image
     * @author William Walton
     */
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path, 4);
    }

    //specify specs
    /**
     * Construct a Boat object with all parameters specified
     *
     * @param x x coordinate for the bottom left point of the boat
     * @param y y coordinate for the bottom left point of the boat
     * @param w the width of the new boat
     * @param h the height of the new boat
     * @param texture_path the relative path from the core/assets folder of the boats texture image
     * @param durability_per_hit the percentage (0-1) of the max durability taken each hit
     * @param name the name of the boat seen when the game ends
     * @param stamina_regen the percentage of stamina regenerated each frame (0-1)
     * @param stamina_usage the percentage of stamina used each frame when accelerating (0-1)
     * @author William Walton
     */
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

    /**
     * Function called when this boat collides with another object
     *
     * @author William Walton
     */
    public void hasCollided() {
        durability -= durability - durability_per_hit <= 0 ? 0 : durability_per_hit;
    }

    /**
     * Function called when the boat accelerates
     *
     * @author William Walton
     */
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

    /**
     * Function called every frame when the game updates all objects positions
     *
     * @author William Walton
     */
    @Override
    public void updatePosition() {
        super.updatePosition();
        stamina = stamina + stamina_regen >= 1 ? 1.f : stamina + stamina_regen;
    }

    /**
     * Sets the start time of a boat in milliseconds.
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

    /**
     * Returns recorded leg times of this boat.
     *
     * @return List<Long> Returns a list of long types in milliseconds.
     * @author Umer Fakher
     */
    public List<Long> getLegTimes() {
        return leg_times;
    }

    /**
     * Returns the time penalties to be added this boat accumulated by crossing the lines.
     *
     * @return Returns a long time in milliseconds.
     */
    public long getTimeToAdd() {
        return time_to_add;
    }

    /**
     * Sets the time penalties to be added by this boat accumulated by crossing the lines.
     *
     * @param time_to_add Recorded long time in milliseconds.
     */
    public void setTimeToAdd(long time_to_add) {
        this.time_to_add = time_to_add;
    }

    /**
     * Checks to see if the this boat has collided with the other CollisionObject object passed.
     *
     * @param object The CollisionObject that will be checked to see if it has hit this boat.
     * @author Umer Fakher
     */
    public void checkCollisions(CollisionObject object) {
        if (this.getBounds().isColliding(object.getBounds())) {
            hasCollided();
            object.hasCollided();
        }
    }

    /**
     * Used to return the CollisionBounds object representing this boat. Used for collision detection
     *
     * @author William Walton
     */
    @Override
    public CollisionBounds getBounds() {
        // create a new collision bounds object representing my current position
        // see the collision bounds visualisation folder in assets for a visual representation
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = new Rectangle(
                sprite.getX()+(0.32f*sprite.getWidth()),
                sprite.getY()+(0.117f*sprite.getHeight()),
                0.32f*sprite.getWidth(),
                0.77f* sprite.getHeight());
        my_bounds.addBound(main_rect);

        my_bounds.setOrigin(new Vector2(
                sprite.getX()+(sprite.getWidth()/2),
                sprite.getY()+(sprite.getHeight()/2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }
}
