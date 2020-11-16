package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a CollisionBounds object.
 *
 * Helps to control functionality for objects colliding by defining bounds that are used for identifying collisions.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
class CollisionBounds {

    // Class attributes
    protected List<Shape2D> bounds;  // shapes that represent the area of the object
    protected float rotation; // the rotation of the sprite
    protected Vector2 origin; // the centre coordinate of the sprite

    /**
     * Main constructor for CollisionBounds.
     *
     * Initialises bounds list, rotation and coordinates.
     */
    CollisionBounds() {
        this.bounds = new ArrayList<>();
        rotation = 0;
        origin = new Vector2();
    }

    /**
     * Getter for the bounds for the object that represent its area.
     *
     * @return List of Shape2D which represent bounds for the object.
     */
    public List<Shape2D> getShapes() {
        return bounds;
    }


    /**
     * Returns polygon for CollisionBounds.
     *
     * @param r1 Rectangle representing shape of object
     * @param rot1 float for rotation of object
     * @param o1 Vector2 position vector
     * @return polygon for CollisionBounds of object.
     */
    private Polygon getPolygon(Rectangle r1, float rot1, Vector2 o1) {
        //get all points from rectangle 1
        Vector2[] r1_prime = new Vector2[4];
        r1_prime[0] = (new Vector2(r1.x, r1.y));
        r1_prime[1] = (new Vector2(r1.x, r1.y + r1.height));
        r1_prime[2] = (new Vector2(r1.x + r1.width, r1.y));
        r1_prime[3] = (new Vector2(r1.x + r1.width, r1.y + r1.height));

        // rotate all points about their sprite's origin
        for (Vector2 p : r1_prime)
            p.rotateAround(o1, rot1);

        Polygon pr1 = new Polygon(new float[]{
                r1_prime[0].x, r1_prime[0].y,
                r1_prime[1].x, r1_prime[1].y,
                r1_prime[2].x, r1_prime[2].y,
                r1_prime[3].x, r1_prime[3].y,});

        return pr1;
    }

    /** Returns the list of polygons */
    public List<Polygon> getPolygons(){
        List<Polygon> ret = new ArrayList<>();

        for (Shape2D my_bound : bounds)
            ret.add(getPolygon((Rectangle) my_bound, rotation, origin));

        return ret;
    }


    /**
     * Gets the rotation attribute of the sprite.
     *
     * @return float
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation attribute of the sprite to the new rotation passed in.
     *
     * @param new_rotation float that is set to be the new rotation of the sprite.
     */
    public void setRotation(float new_rotation) {
        this.rotation = new_rotation;
    }

    /**
     * Gets the centre coordinate of the sprite
     * @return Vector2
     */
    public Vector2 getOrigin() {
        return origin;
    }

    /**
     * Sets the centre coordinate of the sprite with the vector passed in.
     *
     * @param o Vector2 that is set to be the new centre coordinate of the sprite.
     */
    public void setOrigin(Vector2 o) {
        origin = o;
    }

    /**
     * Adds new bound to the list of bounds
     *
     * @param new_bound Shape2D that will be used to represent the area of the object.
     */
    public void addBound(Shape2D new_bound) {
        bounds.add(new_bound);
    }

    /**
     * Returns true if objects have collided according to their collision boundaries.
     *
     * Note: only works for rectangles, but could be expanded to check for other shape types
     *
     * @param collider CollisionBounds to be checked to see if there is a collision.
     * @return boolean if objects colliding
     */
    public boolean isColliding(CollisionBounds collider) {

        for (Shape2D my_bound : bounds) {
            for (Shape2D their_bound : collider.getShapes()) {
                if (my_bound instanceof Rectangle && their_bound instanceof Rectangle) {
                    if (rectOnRectCollides((Rectangle) my_bound, rotation, origin, (Rectangle) their_bound, collider.getRotation(), collider.getOrigin()))
                        return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if objects have collided according to their
     * collision boundaries overlapping rectangle upon rectangle.
     *
     * @param r1 Rectangle area of the sprite
     * @param rot1 float rotation of the sprite
     * @param o1 Vector2 centre coordinate of the sprite
     * @param r2 Rectangle area of the second sprite
     * @param rot2 float rotation of the second sprite
     * @param o2 Vector2 centre coordinate of the second sprite
     * @return boolean
     */
    public boolean rectOnRectCollides(Rectangle r1, float rot1, Vector2 o1, Rectangle r2, float rot2, Vector2 o2) {
        Polygon pr1 = getPolygon(r1, rot1, o1);
        Polygon pr2 = getPolygon(r2, rot2, o2);

        return Intersector.overlapConvexPolygons(pr1, pr2);
    }
}