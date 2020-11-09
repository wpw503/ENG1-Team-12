package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;

public class lane_wall extends Obstacle {
    public static int texture_height = 64;
    lane_wall(int x, int y){
        super(x, y, 32, texture_height, "lane_buoy.png", 2);
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
