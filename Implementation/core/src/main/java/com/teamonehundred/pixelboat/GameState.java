package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.teamonehundred.pixelboat.entities.AiBoat;
import com.teamonehundred.pixelboat.entities.Boat;
import com.teamonehundred.pixelboat.entities.CollisionObject;
import com.teamonehundred.pixelboat.entities.Obstacle;
import com.teamonehundred.pixelboat.entities.ObstacleBranch;
import com.teamonehundred.pixelboat.entities.ObstacleDuck;
import com.teamonehundred.pixelboat.entities.ObstacleFloatingBranch;
import com.teamonehundred.pixelboat.entities.ObstacleLaneWall;
import com.teamonehundred.pixelboat.entities.PlayerBoat;
import com.teamonehundred.pixelboat.entities.PowerUp;
import com.teamonehundred.pixelboat.entities.PowerUpDrag;
import com.teamonehundred.pixelboat.entities.PowerUpEnergy;
import com.teamonehundred.pixelboat.entities.PowerUpHealth;
import com.teamonehundred.pixelboat.entities.PowerUpRotation;
import com.teamonehundred.pixelboat.entities.PowerUpSpeed;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GameState used to save the game.
 */
public class GameState implements Serializable {

  /**
   * serialVersionUID for the GameState object.
   */
  private static final long serialVersionUID = -6916029576714596394L;

  /**
   * Private enum to store type of object to be saved.
   */
  private enum ObjectType {
    BOAT,
    DUCK,
    BRANCH,
    FLOATING_BRANCH,
    LANE_WALL,
    POWERUP_SPEED,
    POWERUP_ENERGY,
    POWERUP_HEALTH, POWERUP_DRAG, POWERUP_ROTATION,
  }

  private class SerializableGameObject implements Serializable {
    
    /**
     *  serialVersionUID for the SerializableGameObject object.
     */
    private static final long serialVersionUID = 304000522049061736L;
    float posX;
    float posY;
    float rotation;
    ObjectType type;
    float speed;
    Boolean isShown;

    // Boat specific attributes
    protected String name = "default boat name";

    protected float durability = 1.f; // from 0 to 1
    protected float durabilityPerHit = .1f;
    protected float stamina = 1.f; // from 0 to 1, percentage of stamina max
    protected float staminaUsage = 0.005f; // todo change this after testing
    protected float staminaRegen = .002f;

    protected List<Long> legTimes = new ArrayList<>(); // times for every previous leg
    protected long startTime = -1;
    protected long endTime = -1; // ms since epoch when starting and finishing current leg
    protected long framesRaced = 0; // number of frames taken to do current leg
    protected long timeToAdd = 0; // Penalty in ms to add to the end time for this leg.
    protected long timeSinceStart = 0; // time elapsed from start time
    protected long timeLeg = 0;
    protected int framesToAnimate = 0;
    protected int currentAnimationFrame = 0;
    protected int framesElapsed = 0;

    public boolean hasFinishedLeg = false;
    public boolean hasStartedLeg = false;

    /**
     * Create a SerializableGameObject.
     *
     * @param x        the x position of the object
     * @param y        the y position of the object
     * @param width    the width of the object
     * @param height   the height of the object
     * @param rotation the rotation angle of the object
     * @param type     the type of the object
     * @param speed    the current speed of the object
     * @param isShown the visibility of the object
     * 
     * @author Adam Blanchet
     */
    SerializableGameObject(float x, float y, float width, float height,
                           float rotation, ObjectType type, float speed,
            Boolean isShown) {
      this.posX = x;
      this.posY = y;
      this.rotation = rotation;
      this.type = type;
      this.speed = speed;
      this.isShown = isShown;

    }

  }

  
  List<SerializableGameObject> gameObjects;
  int playerBoatIndex = 0;
  public int legNumber;
  public boolean lastRun;
  public boolean isFinished;
  public long totalFrames;

  /**
   * Create a GameState.
   *
   * @param allBoats the list of all boats from SceneMainGame
   * @param playerBoat the PlayerBoat object
   * @param obstacles the list of all obstacles in the race
   * @param powerups the list of all powerups in the race
   * @param legNumber the current leg number
   * @param lastRun true if it is the final leg, false otherwise
   * @param isFinsihed true if the race is finished, false otherwise
   * @param totalFrames number of frames elapsed since the start of the race
   */
  public GameState(List<Boat> allBoats, PlayerBoat playerBoat, List<CollisionObject> obstacles,
                    List<CollisionObject> powerups, int legNumber, boolean lastRun,
                    boolean isFinsihed, long totalFrames) {
    
    this.legNumber = legNumber;
    this.lastRun = lastRun;
    this.isFinished = isFinsihed;
    this.totalFrames = totalFrames;
    this.gameObjects = new ArrayList<SerializableGameObject>();

    for (Boat boat : allBoats) {
      if (boat instanceof PlayerBoat) {
        this.playerBoatIndex = allBoats.indexOf(boat);
      }
      float x = boat.getSprite().getX();
      float y = boat.getSprite().getY();
      float width = boat.getSprite().getWidth();
      float height = boat.getSprite().getHeight();
      float rotation = boat.getSprite().getRotation();
      ObjectType type = ObjectType.BOAT;
      float speed = boat.speed;
      Boolean isShown = boat.isShown();
      
      SerializableGameObject obj = new SerializableGameObject(x, y, width, height,
                                                              rotation, type, speed, isShown);

      // Set boat specific stats
      obj.name = boat.getName();
      obj.durability = boat.durability;
      obj.durabilityPerHit = boat.durabilityPerHit;
      obj.stamina = boat.stamina;
      obj.staminaUsage = boat.staminaUsage;
      obj.staminaUsage = boat.staminaRegen;

      obj.legTimes.addAll(boat.getLegTimes());
      obj.startTime = boat.getStartTime(false);
      obj.endTime = boat.getEndTime(false);

      // Deal with delta times
      obj.timeSinceStart = System.currentTimeMillis() - boat.getStartTime(false);
      if (boat.hasFinishedLeg) {
        obj.timeLeg = boat.getCalcTime();
      }

      obj.framesRaced = boat.getFramesRaced();
      obj.timeToAdd = boat.getTimeToAdd();
      
      obj.framesToAnimate = boat.framesToAnimate;
      obj.currentAnimationFrame = boat.currentAnimationFrame;
      obj.framesElapsed = boat.framesElapsed;

      obj.hasFinishedLeg = boat.hasFinishedLeg;
      obj.hasStartedLeg = boat.hasStartedLeg;


      this.gameObjects.add(obj);

      
    }

    for (CollisionObject obstacle : obstacles) {

      ObjectType type = null;

      if (obstacle instanceof ObstacleFloatingBranch) {
        type = ObjectType.FLOATING_BRANCH;
      } else if (obstacle instanceof ObstacleBranch) {
        type = ObjectType.BRANCH;
      } else if (obstacle instanceof ObstacleDuck) {
        type = ObjectType.DUCK;
      } else if (obstacle instanceof ObstacleLaneWall) {
        type = ObjectType.LANE_WALL;
      }


      if (type == null) {
        continue; // We skip this object because we don't know what it is
      } else {
        // We know it's a type of obstacle

        Obstacle obstacleCast = (Obstacle) obstacle; 
        
        float x = obstacleCast.getSprite().getX();
        float y = obstacleCast.getSprite().getY();
        float width = obstacleCast.getSprite().getWidth();
        float height = obstacleCast.getSprite().getHeight();
        float rotation = obstacleCast.getSprite().getRotation();
        float speed = obstacleCast.speed;
        Boolean isShown = obstacle.isShown();

        SerializableGameObject obj = new SerializableGameObject(x, y, width, height,
                                                                rotation, type, speed, isShown);
        
        gameObjects.add(obj);
      }
        
    }


    for (CollisionObject powerup : powerups) {

      ObjectType type = null;

      if (powerup instanceof PowerUpEnergy) {
        type = ObjectType.POWERUP_ENERGY;
      } else if (powerup instanceof PowerUpHealth) {
        type = ObjectType.POWERUP_HEALTH;
      } else if (powerup instanceof PowerUpSpeed) {
        type = ObjectType.POWERUP_SPEED;
      } else if (powerup instanceof PowerUpDrag) {
        type = ObjectType.POWERUP_DRAG;
      } else if (powerup instanceof PowerUpRotation) {
        type = ObjectType.POWERUP_ROTATION;
      }
      
      if (type == null) {
        continue; // We skip this powerup because we don't know what it is
      } else {

        PowerUp powerupCast = (PowerUp) powerup;

        float x = powerupCast.getSprite().getX();
        float y = powerupCast.getSprite().getY();
        float width = powerupCast.getSprite().getWidth();
        float height = powerupCast.getSprite().getHeight();
        float rotation = powerupCast.getSprite().getRotation();
        float speed = powerupCast.speed;
        Boolean isShown = powerupCast.isShown;

        SerializableGameObject obj = new SerializableGameObject(x, y, width, height, rotation,
                                                               type, speed, isShown);

        gameObjects.add(obj);

      }

    }

  }

  /**
   * Get the index of the player boat in the boat list.
   *
   * @return the index of the player in the boat list
   */
  public int getPlayerIndex() {
    return playerBoatIndex;
  }

  /**
   * Get the list of boats from the GameState.
   *
   * @return the list of boats from the GameState
   */
  public List<Boat> getBoatList() {
    
    List<Boat> output = new ArrayList<Boat>();
    int i = 0;
    for (SerializableGameObject obj : gameObjects) {

      if (obj.type.equals(ObjectType.BOAT)) {
        
        if (i == playerBoatIndex) { // Create a PlayerBoat object instead
          PlayerBoat player = new PlayerBoat(obj.posX, obj.posY);
          player.speed = obj.speed;
          player.isShown = obj.isShown;
          player.getSprite().setRotation(obj.rotation);

          player.name = obj.name;
          player.durability = obj.durability;
          player.durabilityPerHit = obj.durabilityPerHit;
          player.stamina = obj.stamina;
          player.staminaUsage = obj.staminaUsage;
          player.staminaUsage = obj.staminaRegen;

          player.legTimes = obj.legTimes;


          // Deal with time deltas
          player.startTime = System.currentTimeMillis() - obj.timeSinceStart;
          if (obj.hasFinishedLeg) {
            player.endTime =  player.startTime + obj.timeLeg;
          }
          
          player.framesRaced = obj.framesRaced;
          player.timeToAdd = obj.timeToAdd;
          
          player.framesToAnimate = obj.framesToAnimate;
          player.currentAnimationFrame = obj.currentAnimationFrame;
          player.framesElapsed = obj.framesElapsed;

          player.hasFinishedLeg = obj.hasFinishedLeg;
          player.hasStartedLeg = obj.hasStartedLeg;

          output.add(player);

        } else {
          // Create an AIBoat
          AiBoat ai = new AiBoat(obj.posX, obj.posY);
          ai.speed = obj.speed;
          ai.isShown = obj.isShown;
          ai.getSprite().setRotation(obj.rotation);

          ai.name = obj.name;
          ai.durability = obj.durability;
          ai.durabilityPerHit = obj.durabilityPerHit;
          ai.stamina = obj.stamina;
          ai.staminaUsage = obj.staminaUsage;
          ai.staminaUsage = obj.staminaRegen;

          ai.legTimes = obj.legTimes;
          ai.startTime = obj.startTime;
          ai.endTime = obj.endTime;
          ai.framesRaced = obj.framesRaced;
          ai.timeToAdd = obj.timeToAdd;
          
          ai.framesToAnimate = obj.framesToAnimate;
          ai.currentAnimationFrame = obj.currentAnimationFrame;
          ai.framesElapsed = obj.framesElapsed;

          ai.hasFinishedLeg = obj.hasFinishedLeg;
          ai.hasStartedLeg = obj.hasStartedLeg;

          output.add(ai);

        }
      }
      i++;

    }


    return output;


  }

  /**
   * Get the list of collision objects in from the GameState (non powerups).
   *
   * @return the list of collision objects from the GameState (non powerups)
   */
  public List<CollisionObject> getCollisionObjects() {

    List<CollisionObject> output = new ArrayList<CollisionObject>();
    Texture laneWallTexture = new Texture("lane_buoy.png");

    for (SerializableGameObject obj : gameObjects) {
      switch (obj.type) {
        case DUCK:
          ObstacleDuck duck = new ObstacleDuck(obj.posX, obj.posY);
          duck.speed = obj.speed;
          duck.isShown = obj.isShown;
          duck.getSprite().setRotation(obj.rotation);
          output.add(duck);
          break;
      
        case BRANCH:
          ObstacleBranch branch = new ObstacleBranch(obj.posX, obj.posY);
          branch.speed = obj.speed;
          branch.isShown = obj.isShown;
          branch.getSprite().setRotation(obj.rotation);

          output.add(branch);
          break;

        case FLOATING_BRANCH:
          ObstacleFloatingBranch floatingBranch = new ObstacleFloatingBranch(obj.posX, obj.posY);
          floatingBranch.speed = obj.speed;
          floatingBranch.isShown = obj.isShown;
          floatingBranch.getSprite().setRotation(obj.rotation);

          output.add(floatingBranch);
          break;

        case LANE_WALL:
          ObstacleLaneWall laneWall = new ObstacleLaneWall(obj.posX, obj.posY, laneWallTexture);
          laneWall.isShown = obj.isShown;

          output.add(laneWall);
          break;
        default:
          break;
      }
    }


    return output;

  }

  /**
   * Get the list of powerup objects from the GameState.
   *
   * @return the list of powerup objects from the GameState
   */
  public List<CollisionObject> getPowerupsList() {
    
    List<CollisionObject> output = new ArrayList<CollisionObject>();


    for (SerializableGameObject obj : gameObjects) {
      switch (obj.type) {
        case POWERUP_ENERGY:
          
          PowerUpEnergy powerupEnergy = new PowerUpEnergy(obj.posX, obj.posY);
          powerupEnergy.speed = obj.speed;
          powerupEnergy.isShown = obj.isShown;
          powerupEnergy.getSprite().setRotation(obj.rotation);

          output.add(powerupEnergy);

          break;

        case POWERUP_SPEED:
          PowerUpSpeed powerUpSpeed = new PowerUpSpeed(obj.posX, obj.posY);
          powerUpSpeed.speed = obj.speed;
          powerUpSpeed.isShown = obj.isShown;
          powerUpSpeed.getSprite().setRotation(obj.rotation);

          output.add(powerUpSpeed);
          break;

        case POWERUP_HEALTH:
          PowerUpHealth powerUpHealth = new PowerUpHealth(obj.posX, obj.posY);
          powerUpHealth.speed = obj.speed;
          powerUpHealth.isShown = obj.isShown;
          powerUpHealth.getSprite().setRotation(obj.rotation);

          output.add(powerUpHealth);
          break;

        case POWERUP_DRAG:
          PowerUpDrag powerUpDrag = new PowerUpDrag(obj.posX, obj.posY);
          powerUpDrag.speed = obj.speed;
          powerUpDrag.isShown = obj.isShown;
          powerUpDrag.getSprite().setRotation(obj.rotation);

          output.add(powerUpDrag);
          break;

        case POWERUP_ROTATION:
          PowerUpRotation powerUpRotation = new PowerUpRotation(obj.posX, obj.posY);
          powerUpRotation.speed = obj.speed;
          powerUpRotation.isShown = obj.isShown;
          powerUpRotation.getSprite().setRotation(obj.rotation);

          output.add(powerUpRotation);
          break;
          
        default:
          break;
      }
    }


    return output;
    
  }


}

