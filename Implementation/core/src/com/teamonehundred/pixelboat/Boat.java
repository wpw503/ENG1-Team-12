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
    float stamina_usage = .005f;
    float stamina_regen = .002f;

    List<Float> leg_times;  // times for every previous leg
    int start_time, end_time;  // Seconds since epoch when starting and finishing current leg

    Texture rowing_animation_frame_1;
    Texture rowing_animation_frame_2;
    Texture rowing_animation_frame_3;

    int frames_to_animate = 0;
    int current_animation_frame = 0;

    Animation rowing_animation;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    //default specs
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);

        initialise();
    }

    //specify specs
    Boat(int x, int y, int w, int h, String texture_path, String name,
         float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path);

        this.name = name;
        this.durability_per_hit = durability_per_hit;
        this.stamina_usage = stamina_usage;
        this.stamina_regen = stamina_regen;

        initialise();
    }

    private void initialise() {
        rowing_animation_frame_1 = new Texture("boat_animation_1.png");
        rowing_animation_frame_2 = new Texture("boat_animation_2.png");
        rowing_animation_frame_3 = new Texture("boat_animation_3.png");

        rowing_animation = new Animation(1f, rowing_animation_frame_1,
                rowing_animation_frame_2,
                rowing_animation_frame_3);
    }

    /* ################################### //
                    METHODS
    // ################################### */

    @Override
    public Sprite getSprite() {
        if (frames_to_animate > 0) {
            // change sprite to current animation frame
            sprite.setTexture((Texture) rowing_animation.getKeyFrame((current_animation_frame++) / 30,
                    true));
            frames_to_animate--;
        } else {
            sprite.setTexture(texture);  // reset texture
            current_animation_frame = 0;
        }
        return super.getSprite();
    }

    public void hasCollided() {
        durability -= durability - durability_per_hit <= 0 ? 0 : durability_per_hit;
    }

    @Override
    public void accelerate() {
        stamina = stamina - stamina_usage <= 0 ? 0 : stamina - stamina_usage;
        if (stamina > 0)
            super.accelerate();
        frames_to_animate += 1;
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        stamina = stamina + stamina_regen >= 1 ? 1.f : stamina + stamina_regen;
    }


    /**
     * Returns System Time in Milliseconds.
     */
    public long getTime() {
        return System.currentTimeMillis();
    }

    public void setStartTime() {
        this.start_time = (int) getTime() / 1000; // Milliseconds to Seconds conversion 1000:1
    }

    public void setEndTime() {
        this.start_time = (int) getTime() / 1000;
    }

    public int getCalcTime() {
        return this.end_time - this.start_time;
    }

    public void setLegTime() {
        this.leg_times.add((float) this.getCalcTime());
    }

}
