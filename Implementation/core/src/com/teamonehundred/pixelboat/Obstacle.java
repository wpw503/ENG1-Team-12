package com.teamonehundred.pixelboat;

import com.teamonehundred.pixelboat.CollisionObject;
import com.teamonehundred.pixelboat.MovableObject;

public abstract class Obstacle extends MovableObject implements CollisionObject {
    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    Obstacle(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
        //todo implement this
    }

    /* ################################### //
                    METHODS
    // ################################### */

    public void hasCollided() {
        is_shown = false;
    }
}
