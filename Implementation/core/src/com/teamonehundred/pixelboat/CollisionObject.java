package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public interface CollisionObject {
    // called when this object collides with something
    public void hasCollided();
    public Rectangle getBounds();
}
