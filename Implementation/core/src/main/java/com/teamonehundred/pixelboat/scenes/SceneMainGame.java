package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamonehundred.pixelboat.BoatRace;
import com.teamonehundred.pixelboat.GameState;
import com.teamonehundred.pixelboat.PixelBoat;
import com.teamonehundred.pixelboat.entities.AiBoat;
import com.teamonehundred.pixelboat.entities.Boat;
import com.teamonehundred.pixelboat.entities.CollisionObject;
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

  protected int sceneId = 1;

  protected int legNumber = 0;

  protected int boatsPerRace = 7;
  protected int groupsPerGame = 3;

  protected PlayerBoat player;
  protected List<Boat> allBoats;

  protected Texture bg;

  protected BoatRace race;
  protected SceneResultsScreen results;
  protected SceneBoatSelection boatSelection;

  protected boolean lastRun = false;

  protected PixelBoat parent;

  /**
   * Main constructor for a SceneMainGame.
   * 
   * <p>Initialises a BoatRace, player's boat, AI boats and scene textures.
   *
   * @author William Walton
   */
  public SceneMainGame() {
    initialize();
  }

  /**
   * Initialize SceneMainGame and all its elements.
   */
  public void initialize() {
    player = new PlayerBoat(-15, 0);
    player.setName("Player");
    allBoats = new ArrayList<>();

    allBoats.add(player);
    for (int i = 0; i < (boatsPerRace * groupsPerGame) - 1; i++) {
      allBoats.add(new AiBoat(0, 40));
      allBoats.get(allBoats.size() - 1).setName("AI Boat " + Integer.toString(i));
    }
    Collections.swap(allBoats, 0, 3); // move player to middle of first group

    bg = new Texture("water_background.png");
    bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

    race = new BoatRace(allBoats.subList(0, boatsPerRace));
    legNumber++;
  }

  /*
   * Destructor disposes of this texture once it is no longer referenced.
   */
  // protected void finalize() {
  //   bg.dispose();
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
   * 
   * <p>The BoatRace runStep method checks for started or finished boats in a leg,
   * calls update methods for the movements for player boat and AI boats obstacles
   * as well as checking for collisions.
   *
   * @author William Walton
   */
  public int update() {

    if (Gdx.input.isKeyPressed(Input.Keys.P)) {
      try {
        saveGame("save");
        return PixelBoat.SAVE_SCENE;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }


    if (player.hasFinishedLeg()) {
      // while (!race.isFinished()) race.runStep();
      race.estimateEndTimes();
    }
    if (!race.isFinished()) {
      race.runStep();
    } else if (legNumber < 3) { // only run 3 guaranteed legs
      race = new BoatRace(allBoats.subList(0, boatsPerRace));

      legNumber++;

      // generate some "realistic" times for all boats not shown
      for (int i = boatsPerRace; i < allBoats.size(); i++) {
        allBoats.get(i).setStartTime(0);
        allBoats.get(i).setEndTime((long) (65000 + 10000 * Math.random()));
        allBoats.get(i).setLegTime();
      }

      return 4;

    } else if (legNumber == 3) {
      // sort boats based on best time
      Collections.sort(allBoats, new Comparator<Boat>() {
        @Override
        public int compare(Boat b1, Boat b2) {
          return (int) (b1.getBestTime() - b2.getBestTime());
        }
      });

      race = new BoatRace(allBoats.subList(0, boatsPerRace));
      lastRun = true;
      legNumber++;

      return 4;
    }

    // stay in results after all legs done
    if (race.isFinished() && legNumber > 3) {
      return 4;
    }

    return sceneId;
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
    return allBoats;
  }

  /**
   * Setter method for player boat stats in the scene.
   *
   * @param spec Integer for player spec.
   * @param diffLevel Integer for player difficulty level
   * @author Umer Fakher 
   * @author Henry Overton
   */
  public void setPlayerStats(int spec, int diffLevel) {
    player.setSpec(spec);
    player.setDiff(diffLevel);
  }

  /**
   * Saves the current state of the game to the filesystem.
   *
   * @param saveName the name of the save state
   * @throws IOException if an ObjectOutputStream or a ByteArrayOutputStream cannot be created
   */
  private void saveGame(String saveName) throws IOException {
    // Create GameState object
    List<CollisionObject> objects = race.obstacles;
    GameState gameState = new GameState(allBoats, player, objects,
                                        race.powerups, legNumber,
                                        lastRun, race.isFinished,
                                        race.totalFrames);

    // Serialize GameState object to a String
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    ObjectOutputStream objStream = new ObjectOutputStream(baos);

    objStream.writeObject(gameState);

    objStream.close();

    String serializedGameState = Base64.getEncoder().encodeToString(baos.toByteArray());

    // Add save to preferences for storage
    Preferences prefs = Gdx.app.getPreferences("tempSaves");

    prefs.putString(saveName, serializedGameState);
    // Save preferences
    prefs.flush();

    // reset self

  
  }
  
  /**
   * Restore the state of the game from the filesystem.
   *
   * @param saveName the name of the save state
   * @throws IOException if an ByteArrayInputStream or ObjectInputStream cannot be created
   */
  public void restoreGame(String saveName, Preferences prefs) throws IOException {

    // Get serialized object from preferences

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

    List<Boat> boatList = gameState.getBoatList();
    PlayerBoat playerBoat = (PlayerBoat) boatList.get(gameState.getPlayerIndex());
    
    this.allBoats = boatList;
    this.player = playerBoat;
    
    this.legNumber = gameState.legNumber;
    this.lastRun = gameState.lastRun;
    
    List<CollisionObject> obstacleList = gameState.getCollisionObjects();
    List<CollisionObject> powerupList = gameState.getPowerupsList();
    
    this.race.boats = boatList.subList(0, boatsPerRace);
    this.race.obstacles = obstacleList;
    this.race.powerups = powerupList;
    this.race.isFinished = gameState.isFinished;
    this.race.totalFrames = gameState.totalFrames;
  }

  public void restoreState(GameState game) {

  }


  /**
   * RaceThread class for Multi-threading.
   *
   * @author William Walton JavaDoc by Umer Fakher
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
     *
     * <p>Runs race until it has finished.
     *
     * @author William Walton
     */
    public void run() {
      while (!race.isFinished()) {
        race.runStep();
      }

      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * Getter for sceneId.
   *
   * @return the sceneId.
   */
  public int getSceneId() {
    return sceneId;
  }

  /**
   * Setter for sceneId.
   *
   * @param sceneId the sceneId to set.
   */
  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

  /**
   * Getter for legNumber.
   *
   * @return the legNumber.
   */
  public int getLegNumber() {
    return legNumber;
  }

  /**
   * Setter for legNumber.
   *
   * @param legNumber the legNumber to set.
   */
  public void setLegNumber(int legNumber) {
    this.legNumber = legNumber;
  }

  /**
   * Getter for boatsPerRace.
   *
   * @return the boatsPerRace.
   */
  public int getBoatsPerRace() {
    return boatsPerRace;
  }

  /**
   * Setter for boatsPerRace.
   *
   * @param boatsPerRace the boatsPerRace to set.
   */
  public void setBoatsPerRace(int boatsPerRace) {
    this.boatsPerRace = boatsPerRace;
  }

  /**
   * Getter for groupsPerGame.
   *
   * @return the groupsPerGame.
   */
  public int getGroupsPerGame() {
    return groupsPerGame;
  }

  /**
   * Setter for groupsPerGame.
   *
   * @param groupsPerGame the groupsPerGame to set.
   */
  public void setGroupsPerGame(int groupsPerGame) {
    this.groupsPerGame = groupsPerGame;
  }

  /**
   * Getter for player.
   *
   * @return the player.
   */
  public PlayerBoat getPlayer() {
    return player;
  }

  /**
   * Setter for Player.
   *
   * @param player the player to set.
   */
  public void setPlayer(PlayerBoat player) {
    this.player = player;
  }

  /**
   * Setter for allBoats.
   *
   * @param allBoats the allBoats to set.
   */
  public void setAllBoats(List<Boat> allBoats) {
    this.allBoats = allBoats;
  }


  /**
   * Getter for race.
   *
   * @return the race.
   */
  public BoatRace getRace() {
    return race;
  }

  /**
   * Setter for race.
   *
   * @param race the race to set.
   */
  public void setRace(BoatRace race) {
    this.race = race;
  }


  /**
   * Getter for lastRun.
   *
   * @return the lastRun.
   */
  public boolean isLastRun() {
    return lastRun;
  }

  /**
   * Setter for lastRun.
   *
   * @param lastRun the lastRun to set.
   */
  public void setLastRun(boolean lastRun) {
    this.lastRun = lastRun;
  }

  /**
   * Getter for parent.
   *
   * @return the parent.
   */
  public PixelBoat getParent() {
    return parent;
  }

  /**
   * Setter for parent.
   *
   * @param parent the parent to set.
   */
  public void setParent(PixelBoat parent) {
    this.parent = parent;
  }
}
