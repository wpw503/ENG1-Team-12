package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class BoatSelection {
    PlayerBoat boat;

    public BoatSelection(PlayerBoat boat){
        this.boat = boat;
    }

    // return false to exit
    public boolean update(){
        return false;
    }

    public void draw(SpriteBatch batch) {

    }
}
