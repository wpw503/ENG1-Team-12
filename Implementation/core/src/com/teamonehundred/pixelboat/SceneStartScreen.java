package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class SceneStartScreen implements Scene {
    protected int scene_id = 0;

    protected Texture bg;

    SceneStartScreen() {
        bg = new Texture("start_screen.png");
    }

    // destructor
    protected void finalize() {
        bg.dispose();
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            scene_id = 1;
    }

    public int getCurrentSceneID() {
        return scene_id;
    }
}
