package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

interface Scene {
    void draw(SpriteBatch batch);
    int update();
    void resize(int width, int height);
}
