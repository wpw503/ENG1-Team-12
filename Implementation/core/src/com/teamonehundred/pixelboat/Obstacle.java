package com.teamonehundred.pixelboat;

public class Obstacle extends GameObject{
    Obstacle(int x, int y, int w, int h, String texture_path){
        super(x, y,  w,  h, texture_path);
        //todo implement this
    }

    public void hasCollided(){
        is_shown = false;
    }
}
