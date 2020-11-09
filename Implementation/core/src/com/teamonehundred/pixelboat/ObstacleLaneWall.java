package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;

class ObstacleLaneWall extends Obstacle {
    public static int texture_height = 64;

    ObstacleLaneWall(int x, int y, Texture t){
        super(x, y, 32, texture_height, t, 2);
        setAnimationFrame(0);
    }

    public void setAnimationFrame(int i){
        super.setAnimationFrame(i);
    }

    @Override
    public void hasCollided(){
        setAnimationFrame(1);
    }
}
