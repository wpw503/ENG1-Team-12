package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

class SceneMainGame implements Scene {

    protected int scene_id = 1;

    protected int leg_number = 0;

    protected PlayerBoat player;
    protected List<Boat> all_boats;

    protected Texture bg;

    protected BoatRace race;

    SceneMainGame() {
        player = new PlayerBoat(-15, 0);
        all_boats = new ArrayList<>();

        all_boats.add(player);
        for (int i = 0; i < 6; i++) all_boats.add(new AIBoat(50 * i, 40));

        bg = new Texture("water_background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        race = new BoatRace(all_boats);
        leg_number++;
//        while(!race.isFinished())race.runBackgroundStep();
//        race = new BoatRace(all_boats);
//        while(!race.isFinished())race.runBackgroundStep();
//        race = new BoatRace(all_boats);
//        while(!race.isFinished())race.runBackgroundStep();
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
        if (player.hasFinishedLeg()) {
            while (!race.isFinished()) race.runBackgroundStep();
        }
        if (!race.isFinished()) race.runStep();
            // only run 3 guaranteed legs
        else if (leg_number < 3) {
            race = new BoatRace(all_boats);
            leg_number++;
        } else {
            //todo add final leg checks and running here
        }
    }

    public int getCurrentSceneID() {
        return scene_id;
    }
}
