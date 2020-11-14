package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class SceneBoatSelection implements Scene {
    protected int scene_id = 5;

    PlayerBoat boat;

    public SceneBoatSelection(PlayerBoat boat) {
        this.boat = boat;
    }

    // return false to exit
    public int update() {
        return 1;
    }

    public void draw(SpriteBatch batch) {

    }

    public void resize(int width, int height) {
    }
}
