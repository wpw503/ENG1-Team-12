package com.teamonehundred.pixelboat;

/**
 * Represents the CollisionObject as an interface.
 *
 * @author James Frost
 * @author William Walton
 * @author Umer Fakher
 */
interface CollisionObject {
    /**
     * Called when this object collides with something
     */
    void hasCollided();

    /**
     * Returns a collision bounds object for intersection checking
     */
    CollisionBounds getBounds();

    /**
     * Returns true if the object should be considered for collision checking
     */
    boolean isShown();
}
