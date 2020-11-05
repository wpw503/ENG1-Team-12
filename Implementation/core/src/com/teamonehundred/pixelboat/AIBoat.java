package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Shape2D;

import java.util.ArrayList;
import java.util.List;

public class AIBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */
    float number_of_rays;
    float ray_angle_range;
    float ray_range;
    float ray_step_size;
    boolean regen;


    /* ################################### //
              CONSTRUCTORS
    // ################################### */
    AIBoat(int x, int y) {
        super(x, y);

        initialise();
    }

    AIBoat(int x, int y, int w, int h, String texture_path) { // So this section will just initialise the AI boat, it doesn't need the intialise method of playerboat due to the fact it doesn't have any textures for durability / stamina
        super(x, y, w, h, texture_path);

        initialise();
    }

    AIBoat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, name, durability_per_hit, stamina_usage, stamina_regen); // This should be the init that is used mostly (but the other one is needed incase someone messes up)

        initialise();

    }

    public void initialise() {
        number_of_rays = 9; // how many rays are fired from the boat
        ray_angle_range = 145; // the range of the angles that the boat will fire rays out at
        ray_range = 100; // the range of each ray
        ray_step_size = (float) 10;
        regen = false;
    }

    public void updatePosition(List<CollisionObject> collidables) {
        // TODO: Make this a method, and neaten it up
        // TODO: Link Acc w/ turning for better AI (that one may take a bit of time tho)
        // TODO: Visible stamina for AI (maybe as debug option)
        if (!regen) {
            this.accelerate();
            if (stamina <= 0.1) {
                regen = true;
            }
        } else {
            if (stamina >= 0.3) {
                regen = false;
            }
        }
        this.check_turn(collidables);
        super.updatePosition();

    }

    @Override
    public boolean isShown() {
        return super.isShown();
    }

    private List<Float> get_ray_fire_point() {
        List<Float> coordinates = new ArrayList<>();
        float o_x = sprite.getX() + (sprite.getWidth() / 2);
        float o_y = sprite.getY() + (sprite.getHeight());
        float mod_a = (float) Math.sqrt(Math.pow(sprite.getX() - o_x, 2) + Math.pow(sprite.getY() - o_y, 2));
        float dx = mod_a * (float) Math.sin(Math.abs(Math.toRadians(sprite.getRotation())));
        float dy = mod_a * (float) Math.cos(Math.abs(Math.toRadians(sprite.getRotation())));
        if (sprite.getRotation() > 0) {
            dx = -dx;
        }
        coordinates.add(o_x + dx);
        coordinates.add(o_y);
        return coordinates;
    }


    public void check_turn(List<CollisionObject> collidables) {
         /* Fire a number of rays with limited distance out the front of the boat,
         select a ray that isn't obstructed by an object,
         preference the middle (maybe put a preference to side as well)
         if every ray is obstructed either (keep turning [left or right] on the spot until one is, or choose the one that is obstructed furthest away
         I am pretty sure the second option (choose the one that is obstructed furthest away) is better
         */

        //Firing rays

        //select an area of 180 degrees (pi radians)
        boolean cheeky_bit_of_coding = true; // this is a very cheeky way of solving the problem, but has a few benefits
        //TODO: Explain the cheeky_bit_of_coding better
        for (int ray = 0; ray <= number_of_rays; ray++) {
            if (cheeky_bit_of_coding) {
                ray--;
                float ray_angle = sprite.getRotation() + ((ray_angle_range / (number_of_rays / 2)) * ray);
                cheeky_bit_of_coding = false;
            } else {
                float ray_angle = sprite.getRotation() - ((ray_angle_range / (number_of_rays / 2)) * ray);
                cheeky_bit_of_coding = true;
            }

            float ray_angle = ((ray_angle_range / number_of_rays) * ray) + sprite.getRotation();
            for (float dist = 0; dist <= ray_range; dist += ray_step_size) {
                List<Float> start_point = get_ray_fire_point();
                double tempx = (Math.cos(Math.toRadians(ray_angle)) * dist) + (start_point.get(0));
                double tempy = (Math.sin(Math.toRadians(ray_angle)) * dist) + (start_point.get(1));
                //check if there is a collision hull (other than self) at (tempx, tempy)
                for (CollisionObject collideable : collidables) {
                    for (Shape2D bound : collideable.getBounds().getShapes()) {
                        if (bound.contains((float) tempx, (float) tempy)) {
                            if (cheeky_bit_of_coding) {
                                turn(-1);
                                return;
                            } else {
                                turn(1);
                                return;
                            }
                        }
                    }

                }
            }
        }

    }

}
