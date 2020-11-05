package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

import java.util.List;

class CollisionBounds {
    private List<Shape2D> bounds;  // shapes that represent the area of the object

    CollisionBounds(List<Shape2D> bounds){
        this.bounds = bounds;
    }

    public List<Shape2D> getBounds(){return bounds;}

    public boolean isColliding(CollisionBounds collider){
        // only works for rectangles, but could be expanded to check for other shape types
        for(Shape2D my_bound : bounds) {
            for (Shape2D their_bound : collider.getBounds()){
                if (my_bound instanceof Rectangle){
                    if (their_bound instanceof Rectangle){
                        if(((Rectangle) my_bound).overlaps((Rectangle)their_bound))
                            return false;
                    }
                }
            }
        }

        return true;
    }
}
