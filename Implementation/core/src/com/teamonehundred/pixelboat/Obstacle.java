package com.teamonehundred.pixelboat;

import com.teamonehundred.pixelboat.CollisionObject;
import com.teamonehundred.pixelboat.MovableObject;

abstract class Obstacle extends MovableObject implements CollisionObject {
    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    Obstacle(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
        //todo implement this
    }

    Obstacle(int x, int y, int w, int h, String texture_path, int frame_count){
        super(x, y, w, h, texture_path,frame_count);
    }

    /* ################################### //
                    METHODS
    // ################################### */

    public void hasCollided() {
        is_shown = false;
    }
}
