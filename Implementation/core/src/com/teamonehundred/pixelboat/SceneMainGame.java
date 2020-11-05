package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneMainGame implements Scene {

    int scene_id = 1;

    PlayerBoat player;
    Texture bg;

    BoatRace race;

    SceneMainGame() {
        player = new PlayerBoat(-15, 0);
        bg = new Texture("water_background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        race = new BoatRace(new ArrayList<Boat>(Arrays.asList(player, new AIBoat(30, 40))));
    }

    // destructor
    protected void finalize() {
        bg.dispose();
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.getCamera().update();
        batch.setProjectionMatrix(player.getCamera().combined);

        batch.begin();
        batch.draw(bg, -10000, -200, 0, 0, 1000000, 10000000);
        race.draw(batch);
        batch.end();
    }

    public void update() {
        race.runStep();
    }

    public int getCurrentSceneID() {
        return scene_id;
    }
}
