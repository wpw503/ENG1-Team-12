package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Rectangle;

public interface CollisionObject {
    // called when this object collides with something
    void hasCollided();
    // returns a libgdx rectangle for intersection checking
    Rectangle getBounds();
    // if the object should be considered for collision checking
    boolean isShown();
}
