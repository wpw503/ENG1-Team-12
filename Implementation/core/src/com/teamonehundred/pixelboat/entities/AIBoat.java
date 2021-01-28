package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Represents the AI's boat.
 *
 * @author James Frost
 * JavaDoc by Umer Fakher
 */
public class AIBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected float number_of_rays;
    protected float ray_angle_range;
    protected float ray_range;
    protected float ray_step_size;
    protected boolean regen;

    /* ################################### //
              CONSTRUCTORS
    // ################################### */

    /**
     * Construct a AIBoat object at point (x,y) with default size, texture and animation.
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @author James Frost
     */
    public AIBoat(int x, int y) {
        super(x, y);

        initialise();
    }

    /**
     * Construct a AIBoat object with at point (x,y) with width and height and texture path
     * with default stats (stamina usage, durability, etc).
     *
     * @param x            int coordinate for the bottom left point of the boat
     * @param y            int coordinate for the bottom left point of the boat
     * @param w            int width of the new boat
     * @param h            int height of the new boat
     * @param texture_path String relative path from the core/assets folder of the boats texture image
     * @author James Frost
     */
    AIBoat(int x, int y, int w, int h, String texture_path) { // So this section will just initialise the AI boat, it doesn't need the intialise method of playerboat due to the fact it doesn't have any textures for durability / stamina
        super(x, y, w, h, texture_path);

        initialise();
    }

    /**
     * Construct a AIBoat object with all parameters specified.
     *
     * @param x                  int coordinate for the bottom left point of the boat
     * @param y                  int coordinate for the bottom left point of the boat
     * @param w                  int width of the new boat
     * @param h                  int height of the new boat
     * @param texture_path       String relative path from the core/assets folder of the boats texture image
     * @param durability_per_hit float percentage (0-1) of the max durability taken each hit
     * @param name               String of the boat seen when the game ends
     * @param stamina_regen      float percentage of stamina regenerated each frame (0-1)
     * @param stamina_usage      float percentage of stamina used each frame when accelerating (0-1)
     * @author James Frost
     */
    AIBoat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, name, durability_per_hit, stamina_usage, stamina_regen); // This should be the init that is used mostly (but the other one is needed incase someone messes up)

        initialise();

    }

    /**
     * Shared initialisation functionality among all constructors.
     * <p>
     * Initialises the ray properties. Rays are used to help the AI control the boat based on visual feedback
     * of its environment i.e. obstacles such as movable obstacles and static lane wall obstacles.
     *
     * @author James Frost
     */
    public void initialise() {
        number_of_rays = 4; // how many rays are fired from the boat
        ray_angle_range = 145; // the range of the angles that the boat will fire rays out at
        ray_range = 30; // the range of each ray
        ray_step_size = (float) 10;
        regen = false;
    }

    /**
     * Updates position of objects AIBoat based on acceleration and stamina.
     * <p>
     * Checks if AIBoat can turn and updates position accordingly based on any collision objects that may overlap.
     *
     * @param collidables List of Collision Objects
     * @author James Frost
     */
    public void updatePosition(List<CollisionObject> collidables) {
        // TODO: Make this a method, and neaten it up
        // TODO: Link Acc w/ turning for better AI (that one may take a bit of time though)
        // TODO: Visible stamina for AI (maybe as debug option)
        if (!regen) {
            this.accelerate();
            if (stamina <= 0.1) {
                regen = true;
            }
        } else {
            if (stamina >= 0.5) {
                regen = false;
            }
        }
        // todo fix this, it takes too long
        this.check_turn(collidables);
        super.updatePosition();

    }

    /**
     * Returns true if AIBoat should exist on the screen.
     *
     * @return boolean parent isShown
     * @author James Frost
     */
    @Override
    public boolean isShown() {
        return super.isShown();
    }

    /**
     * Return centre coordinates of point where ray is fired.
     *
     * @return Vector2 of coordinates
     * @author James Frost
     */
    protected Vector2 get_ray_fire_point() {
        Vector2 p = new Vector2(
                sprite.getX() + (sprite.getWidth() / 2),
                sprite.getY() + (sprite.getHeight()));

        Vector2 p1 = p.rotateAround(new Vector2(
                        sprite.getX() + (sprite.getWidth() / 2),
                        sprite.getY() + (sprite.getHeight() / 2)),
                sprite.getRotation());

        return p1;
    }

    /**
     * Fire a number of rays with limited distance out the front of the boat, select a ray that
     * isn't obstructed by an object, preference the middle (maybe put a preference to side as well)
     * if every ray is obstructed either (keep turning [left or right] on the spot until one is,
     * or choose the one that is obstructed furthest away the second option
     * (choose the one that is obstructed furthest away) is better
     *
     * @param collidables List of Collision Objects
     * @author James Frost
     */
    protected void check_turn(List<CollisionObject> collidables) {
        //Firing rays

        //select an area of 180 degrees (pi radians)
        boolean cheeky_bit_of_coding = true; // this is a very cheeky way of solving the problem, but has a few benefits
        //TODO: Explain the cheeky_bit_of_coding better
        Vector2 start_point = get_ray_fire_point();
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

                double tempx = (Math.cos(Math.toRadians(ray_angle)) * dist) + (start_point.x);
                double tempy = (Math.sin(Math.toRadians(ray_angle)) * dist) + (start_point.y);
                //check if there is a collision hull (other than self) at (tempx, tempy)
                for (CollisionObject collideable : collidables) {
                    // very lazy way of optimising this code. will break if the collidable isn't an obstacle
                    if (collideable.isShown() &&
                            ((Obstacle) collideable).getSprite().getY() > sprite.getY() - 200 &&
                            ((Obstacle) collideable).getSprite().getY() < sprite.getY() + 200 &&
                            ((Obstacle) collideable).getSprite().getX() > sprite.getX() - 200 &&
                            ((Obstacle) collideable).getSprite().getX() < sprite.getX() + 200)
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
