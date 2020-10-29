package com.teamonehundred.pixelboat;

public class ObstacleFloatingBranch extends ObstacleBranch{
    ObstacleFloatingBranch(int x, int y){
        super(x, y);
        // have the branch move at a constant speed
        drag = 0;
        speed = 0.3f;
        sprite.setRotation(-180);
    }
}
