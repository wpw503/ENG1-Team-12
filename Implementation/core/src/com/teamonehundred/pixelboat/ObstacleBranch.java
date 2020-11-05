package com.teamonehundred.pixelboat;

// simple obstacle that doesn't move
class ObstacleBranch extends Obstacle {
    ObstacleBranch(int x, int y) {
        super(x, y, 60, 60, "obstacle_branch.png");
        sprite.setRotation(-90 + (float)Math.random() * 180);
    }
}
