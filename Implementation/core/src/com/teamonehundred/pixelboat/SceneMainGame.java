package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    protected int boats_per_race = 7;
    protected int groups_per_game = 9;

    protected PlayerBoat player;
    protected List<Boat> all_boats;

    //RaceThread[] threads;

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
    SceneMainGame() {
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

        results = new SceneResultsScreen(all_boats);

        //todo check if this syncs with list
        boat_selection = new SceneBoatSelection(player);

        race = new BoatRace(all_boats.subList(0, boats_per_race));
        leg_number++;

        //startBackgroundRaces();
    }


    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    protected void finalize() {
        bg.dispose();
    }


    /**
     * Draws SpriteBatch on display along with updating player camera and player overlay Using BoatRace.
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
     * Calls main runStep method for BoatRace which is repeatedly called for updating the game state.
     * <p>
     * The BoatRace runStep method checks for started or finished boats in a leg, calls update methods for
     * the movements for player boat and AI boats obstacles as well as checking for collisions.
     *
     * @author William Walton
     */
    public int update() {
        if (player.hasFinishedLeg()) {
            while (!race.isFinished()) race.runStep();
        }
        if (!race.isFinished()) race.runStep();
            // only run 3 guaranteed legs
        else if (leg_number < 3) {
            race = new BoatRace(all_boats.subList(0, boats_per_race));

            leg_number++;


            // generate some "realistic" times for all boats not shown
            for (int i = boats_per_race; i < all_boats.size(); i++) {
                all_boats.get(i).setStartTime(0);
                all_boats.get(i).setEndTime((long) (50000 + 10000 * Math.random()));
                all_boats.get(i).setLegTime();
            }

            return 4;

//                for (int i = 0; i < groups_per_game - 1; i++) {
//                    try {
//                        threads[i].join();
//                    } catch (InterruptedException e) {
//                        System.out.println("Main thread Interrupted");
//                    }
//                }
        } else {
            // sort boats based on best time
            Collections.sort(all_boats, new Comparator<Boat>() {
                @Override
                public int compare(Boat b1, Boat b2) {
                    return (int) (b1.getBestTime() - b2.getBestTime());
                }
            });

            race = new BoatRace(all_boats.subList(0, boats_per_race));
            last_run = true;
        }


        return scene_id;
    }

//    private void startBackgroundRaces(){
//        // run all boats in the background
//        threads = new RaceThread[groups_per_game - 1];
//        for (int i = 1; i < groups_per_game; i++) {
//            threads[i - 1] = new RaceThread(all_boats.subList(boats_per_race * i, (boats_per_race * (i + 1))));
//            threads[i - 1].start();
//        }
//    }

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

    public List<Boat> getAllBoats() {
        return all_boats;
    }

    public PlayerBoat getPlayer() {
        for (Boat b : all_boats)
            if (b instanceof PlayerBoat)
                return (PlayerBoat) b;

        return null;
    }

    private class RaceThread extends Thread {
        List<Boat> boats;
        BoatRace race;

        RaceThread(List<Boat> boats) {
            this.boats = new ArrayList<>();
            this.boats.addAll(boats);
            race = new BoatRace(this.boats);
        }

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
