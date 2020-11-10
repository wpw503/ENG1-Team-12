package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.*;

import java.util.ArrayList;
import java.util.List;

class CollisionBounds {
    protected List<Shape2D> bounds;  // shapes that represent the area of the object
    protected float rotation; // the rotation of the sprite
    protected Vector2 origin; // the centre coordinate of the sprite

    CollisionBounds() {
        this.bounds = new ArrayList<>();
        rotation = 0;
        origin = new Vector2();
    }

    public List<Shape2D> getShapes() {
        return bounds;
    }

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

    public List<Polygon> getPolygons(){
        List<Polygon> ret = new ArrayList<>();

        for (Shape2D my_bound : bounds)
            ret.add(getPolygon((Rectangle) my_bound, rotation, origin));

        return ret;
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
        Polygon pr1 = getPolygon(r1, rot1, o1);
        Polygon pr2 = getPolygon(r2, rot2, o2);

        return Intersector.overlapConvexPolygons(pr1, pr2);
    }
}
