package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class SceneBoatSelection implements Scene {
    protected int scene_id = 5;

    protected int spec_id = 0;


    public SceneBoatSelection() {
    }

    // return false to exit
    public int update() {

        return 3;
    }

    public void draw(SpriteBatch batch) {

    }

    public void resize(int width, int height) {
    }

    public int getSpecID() {
        return spec_id;
    }
}
