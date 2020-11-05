package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

interface Scene {
    public void draw(SpriteBatch batch);
    public void update();
    public int getCurrentSceneID();
}
