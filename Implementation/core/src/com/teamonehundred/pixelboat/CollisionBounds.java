package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

class CollisionBounds {
    private List<Shape2D> bounds;  // shapes that represent the area of the object
    private float rotation; // the rotation of the sprite
    private Vector2 origin; // the centre coordinate of the sprite

    CollisionBounds() {
        this.bounds = new ArrayList<>();
        rotation = 0;
        origin = new Vector2();
    }

    public List<Shape2D> getShapes() {
        return bounds;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float new_rotation) {
        this.rotation = new_rotation;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2 o) {
        origin = o;
    }

    public void addBound(Shape2D new_bound) {
        bounds.add(new_bound);
    }

    public boolean isColliding(CollisionBounds collider) {
        // only works for rectangles, but could be expanded to check for other shape types
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

    public boolean rectOnRectCollides(Rectangle r1, float rot1, Vector2 o1, Rectangle r2, float rot2, Vector2 o2) {
        //get all points from rectangle 1
        Vector2[] r1_prime = new Vector2[4];
        r1_prime[0] = (new Vector2(r1.x, r1.y));
        r1_prime[1] = (new Vector2(r1.x, r1.y + r1.height));
        r1_prime[2] = (new Vector2(r1.x + r1.width, r1.y));
        r1_prime[3] = (new Vector2(r1.x + r1.width, r1.y + r1.height));

        //get all points from rectangle 2
        Vector2[] r2_prime = new Vector2[4];
        r2_prime[0] = (new Vector2(r2.x, r2.y));
        r2_prime[1] = (new Vector2(r2.x, r2.y + r2.height));
        r2_prime[2] = (new Vector2(r2.x + r2.width, r2.y));
        r2_prime[3] = (new Vector2(r2.x + r2.width, r2.y + r2.height));

        // rotate all points about their sprite's origin
        for (Vector2 p : r1_prime)
            p.rotateAround(o1, rot1);
        for (Vector2 p : r2_prime)
            p.rotateAround(o2, rot2);

        Polygon pr1 = new Polygon(new float[]{
                r1_prime[0].x, r1_prime[0].y,
                r1_prime[1].x, r1_prime[1].y,
                r1_prime[2].x, r1_prime[2].y,
                r1_prime[3].x, r1_prime[3].y,});

        Polygon pr2 = new Polygon(new float[]{
                r2_prime[0].x, r2_prime[0].y,
                r2_prime[1].x, r2_prime[1].y,
                r2_prime[2].x, r2_prime[2].y,
                r2_prime[3].x, r2_prime[3].y,});

        // check if any points from r1 is in r2
        for (Vector2 p : r1_prime) {
            if (pr2.contains(p))
                return true;
        }

        // check if any points from r2 is in r1
        for (Vector2 p : r2_prime) {
            if (pr1.contains(p))
                return true;
        }

        // if nothing has collided
        return false;
    }
}
