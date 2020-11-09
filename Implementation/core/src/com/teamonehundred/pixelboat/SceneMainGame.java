package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * Represents the Main Game Scene for when the boat race starts.
 *
 * @author William Walton
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
class SceneMainGame implements Scene {

    protected int scene_id = 1;

    protected int leg_number = 0;

    protected PlayerBoat player;
    protected List<Boat> all_boats;

    protected Texture bg;

    protected BoatRace race;

    /**
     * Main constructor for a SceneMainGame.
     *
     * Initialises a BoatRace, player's boat, AI boats and scene textures.
     * @author William Walton
     *
     */
    SceneMainGame() {
        player = new PlayerBoat(-15, 0);
        player.setName("Player");
        all_boats = new ArrayList<>();

        all_boats.add(player);
        for (int i = 0; i < 6; i++) {all_boats.add(new AIBoat(50 * i, 40));all_boats.get(all_boats.size()-1).setName("AI Boat " + Integer.toString(i));}
        Collections.swap(all_boats, 0, 3); // move player to middle of group

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


    /** Destructor disposes of this texture once it is no longer referenced. */
    protected void finalize() {
        bg.dispose();
    }


    /**
     * Draws SpriteBatch on display along with updating player camera and player overlay Using BoatRace.
     *
     * @param batch Spritebatch passed for drawing graphic objects onto screen.
     * @author William Walton
     *
     */
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

    /**
     * Calls main runStep method for BoatRace which is repeatedly called for updating the game state.
     *
     * The BoatRace runStep method checks for started or finished boats in a leg, calls update methods for
     * the movements for player boat and AI boats obstacles as well as checking for collisions.
     * @author William Walton
     */
    public int update() {
        if (player.hasFinishedLeg()) {
            while (!race.isFinished()) race.runStep();
        }
        if (!race.isFinished()) race.runStep();
            // only run 3 guaranteed legs
        else if (leg_number < 3) {
            race = new BoatRace(all_boats);
            leg_number++;
        } else {
            //todo add final leg checks and running here
        }
      
        return scene_id;
    }

    /**
     * TODO Finish DocString if needed
     *
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        player.getCamera().viewportHeight = height;
        player.getCamera().viewportWidth = width;
    }
}
