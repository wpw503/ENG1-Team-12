package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;
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

    protected PlayerBoat player;
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
        bg = new Texture("water_background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        race = new BoatRace(new ArrayList<Boat>(Arrays.asList(player, new AIBoat(30, 40))));
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
        race.runStep();
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
