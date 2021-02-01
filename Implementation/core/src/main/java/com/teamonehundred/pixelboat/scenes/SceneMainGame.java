package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamonehundred.pixelboat.entities.AIBoat;
import com.teamonehundred.pixelboat.entities.Boat;
import com.teamonehundred.pixelboat.entities.CollisionObject;
import com.teamonehundred.pixelboat.BoatRace;
import com.teamonehundred.pixelboat.GameState;
import com.teamonehundred.pixelboat.entities.PlayerBoat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents the Main Game Scene for when the boat race starts.
 *
 * @author William Walton
 * @author Umer Fakher JavaDoc by Umer Fakher
 */
public class SceneMainGame implements Scene {

    protected int scene_id = 1;

    protected int leg_number = 0;

    protected int boats_per_race = 7;
    protected int groups_per_game = 3;

    protected PlayerBoat player;
    protected List<Boat> all_boats;

    protected Texture bg;

    protected BoatRace race;
    protected SceneResultsScreen results;
    protected SceneBoatSelection boat_selection;

    protected boolean last_run = false;

    /**
     * Main constructor for a SceneMainGame.
     * <p>
     * Initialises a BoatRace, player's boat, AI boats and scene textures.
     *
     * @author William Walton
     */
    public SceneMainGame() {
        player = new PlayerBoat(-15, 0);
        player.setName("Player");
        all_boats = new ArrayList<>();

        all_boats.add(player);
        for (int i = 0; i < (boats_per_race * groups_per_game) - 1; i++) {
            all_boats.add(new AIBoat(0, 40));
            all_boats.get(all_boats.size() - 1).setName("AI Boat " + Integer.toString(i));
        }
        Collections.swap(all_boats, 0, 3); // move player to middle of first group

        bg = new Texture("water_background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        race = new BoatRace(all_boats.subList(0, boats_per_race));
        leg_number++;
    }

    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    // protected void finalize() {
    //     bg.dispose();
    // }

    /**
     * Draws SpriteBatch on display along with updating player camera and player
     * overlay Using BoatRace.
     *
     * @param batch Spritebatch passed for drawing graphic objects onto screen.
     * @author William Walton
     */
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.getCamera().update();
        batch.setProjectionMatrix(player.getCamera().combined);

        batch.begin();

        batch.draw(bg, -10000, -2000, 0, 0, 1000000, 10000000);
        race.draw(batch);

        batch.end();
    }

    /**
     * Calls main runStep method for BoatRace which is repeatedly called for
     * updating the game state.
     * <p>
     * The BoatRace runStep method checks for started or finished boats in a leg,
     * calls update methods for the movements for player boat and AI boats obstacles
     * as well as checking for collisions.
     *
     * @author William Walton
     */
    public int update() {

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            try {
                saveGame("testSave");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            try {
                restoreGame("testSave");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (player.hasFinishedLeg()) {
            // while (!race.isFinished()) race.runStep();
            race.estimateEndTimes();
        }
        if (!race.isFinished())
            race.runStep();
        // only run 3 guaranteed legs
        else if (leg_number < 3) {
            race = new BoatRace(all_boats.subList(0, boats_per_race));

            leg_number++;

            // generate some "realistic" times for all boats not shown
            for (int i = boats_per_race; i < all_boats.size(); i++) {
                all_boats.get(i).setStartTime(0);
                all_boats.get(i).setEndTime((long) (65000 + 10000 * Math.random()));
                all_boats.get(i).setLegTime();
            }

            return 4;

        } else if (leg_number == 3) {
            // sort boats based on best time
            Collections.sort(all_boats, new Comparator<Boat>() {
                @Override
                public int compare(Boat b1, Boat b2) {
                    return (int) (b1.getBestTime() - b2.getBestTime());
                }
            });

            race = new BoatRace(all_boats.subList(0, boats_per_race));
            last_run = true;
            leg_number++;

            return 4;
        }

        // stay in results after all legs done
        if (race.isFinished() && leg_number > 3)
            return 4;

        return scene_id;
    }

    /**
     * Resize method if for camera extension.
     *
     * @param width  Integer width to be resized to
     * @param height Integer height to be resized to
     * @author Umer Fakher
     */
    public void resize(int width, int height) {
        player.getCamera().viewportHeight = height;
        player.getCamera().viewportWidth = width;
    }

    /**
     * Getter method for returning list of boats which contain all boats in scene.
     *
     * @return list of boats
     * @author Umer Fakher
     */
    public List<Boat> getAllBoats() {
        return all_boats;
    }

    /**
     * Setter method for player boat spec in the scene.
     *
     * @param spec Integer for player spec.
     * @author Umer Fakher
     */
    public void setPlayerStats(int spec, int diffDecrease) {
        player.setSpec(spec);
        player.setDiff(diffDecrease);
    }


    private void saveGame(String saveName) throws IOException {
        // Create GameState object
        List<CollisionObject> objects = race.obstacles;
        GameState gameState = new GameState(all_boats, player, objects, race.powerups, leg_number, last_run, race.is_finished, race.total_frames);

        // Serialize GameState object to a String
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream objStream = new ObjectOutputStream(baos);

        objStream.writeObject(gameState);

        objStream.close();

        String serializedGameState = Base64.getEncoder().encodeToString(baos.toByteArray());

        // Add save to preferences for storage
        Preferences prefs = Gdx.app.getPreferences("Saves");

        prefs.putString(saveName, serializedGameState);
        // Save preferences
        prefs.flush();

    }

    private void restoreGame(String saveName) throws IOException {

        // Get serialized object from preferences
        Preferences prefs = Gdx.app.getPreferences("Saves");

        String serializedGameState = prefs.getString(saveName);

        
        // Decode serialize GameState
        byte[] data = Base64.getDecoder().decode(serializedGameState);

        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        ObjectInputStream objStream = new ObjectInputStream(bais);

        GameState gameState = null;

        try {
            gameState = (GameState) objStream.readObject();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<Boat> boat_list = gameState.getBoatList();
        PlayerBoat playerBoat = (PlayerBoat) boat_list.get(gameState.getPlayerIndex());
        List<CollisionObject> obstacle_list = gameState.getCollisionObjects();
        List<CollisionObject> powerup_list = gameState.getPowerupsList();

        this.all_boats = boat_list;
        this.player = playerBoat;
        
        this.leg_number = gameState.legNumber;
        this.last_run = gameState.lastRun;

        
        this.race.boats = boat_list.subList(0, boats_per_race);
        this.race.obstacles = obstacle_list;
        this.race.powerups = powerup_list;
        this.race.is_finished = gameState.isFinished;
        this.race.total_frames = gameState.totalFrames;
    }


    /**
     * RaceThread class for Multi-threading.
     *
     * @author William Walton
     * JavaDoc by Umer Fakher
     */
    private class RaceThread extends Thread {
        List<Boat> boats;
        BoatRace race;

        RaceThread(List<Boat> boats) {
            this.boats = new ArrayList<>();
            this.boats.addAll(boats);
            race = new BoatRace(this.boats);
        }

        /**
         * Main run method for RaceThread class.
         * <p>
         * Runs race until it has finished.
         *
         * @author William Walton
         */
        public void run() {
            while (!race.isFinished()) race.runStep();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
