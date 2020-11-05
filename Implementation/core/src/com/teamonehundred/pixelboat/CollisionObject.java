package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Rectangle;

interface CollisionObject {
    // called when this object collides with something
    void hasCollided();
    // returns a collision bounds object for intersection checking
    CollisionBounds getBounds();
    // if the object should be considered for collision checking
    boolean isShown();
}
