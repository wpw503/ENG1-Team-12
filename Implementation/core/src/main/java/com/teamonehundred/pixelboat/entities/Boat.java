package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

// generic boat class, never instantiated

/**
 * Base class for all boat types. Contains all functionality for moving, taking damage and collision
 *
 * @author William Walton
 * @author Umer Fakher
 */
public abstract class Boat extends MovableObject implements CollisionObject {
  /* ################################### //
               ATTRIBUTES
  // ################################### */

  public String name = "default boat name";

  public float durability = 1.f;  // from 0 to 1
  public float durabilityPerHit = .1f;
  public float stamina = 1.f;  // from 0 to 1, percentage of stamina max
  public float staminaUsage = 0.005f;  //todo change this after testing
  public float staminaRegen = .002f;

  public List<Long> legTimes = new ArrayList<>();  // times for every previous leg
  public long startTime = -1;
  public long endTime = -1;  // ms since epoch when starting and finishing current leg
  public long framesRaced = 0;  // number of frames taken to do current leg
  public long timeToAdd = 0;  // penalty in ms to add to the end time for this leg. 
          

  public int framesToAnimate = 0;
  public int currentAnimationFrame = 0;
  public int framesElapsed = 0;

  public boolean hasFinishedLeg = false;
  public boolean hasStartedLeg = false;

  /* ################################### //
          CONSTRUCTORS
  // ################################### */

  //default specs

  /**
   * Construct a Boat object at point (x,y) with default size, texture and animation.
   *
   * @param x float coordinate for the bottom left point of the boat
   * @param y float coordinate for the bottom left point of the boat
   * @author William Walton
   */
  Boat(float x, float y) {
    super(x, y, 80, 100, "boat.png", 4);
  }

  /**
   * Construct a Boat object with at point (x,y) with width and height and texture path
   * with default stats (stamina usage, durability, etc).
   *
   * @param x      int coordinate for the bottom left point of the boat
   * @param y      int coordinate for the bottom left point of the boat
   * @param w      int width of the new boat
   * @param h      int height of the new boat
   * @param texturePath String relative path from the core/assets folder of the boats texture image
   * @author William Walton
   */
  Boat(int x, int y, int w, int h, String texturePath) {
    super(x, y, w, h, texturePath, 4);
  }

  //specify specs

  /**
   * Construct a Boat object with all parameters specified.
   *
   * @param x          int coordinate for the bottom left point of the boat
   * @param y          int coordinate for the bottom left point of the boat
   * @param w          int width of the new boat
   * @param h          int height of the new boat
   * @param texturePath    relative path from the core/assets folder of the boats texture image
   * @param durabilityPerHit float percentage (0-1) of the max durability taken each hit
   * @param name         String of the boat seen when the game ends
   * @param staminaRegen    float percentage of stamina regenerated each frame (0-1)
   * @param staminaUsage    float percentage of stamina used each frame when accelerating (0-1)
   * @author William Walton
   */
  Boat(int x, int y, int w, int h, String texturePath, String name,
      float durabilityPerHit, float staminaUsage, float staminaRegen) {
    super(x, y, w, h, texturePath, 4);

    this.name = name;
    this.durabilityPerHit = durabilityPerHit;
    this.staminaUsage = staminaUsage;
    this.staminaRegen = staminaRegen;
  }

  /* ################################### //
          METHODS
  // ################################### */

  /**
   * Function called when this boat collides with another object.
   *
   * @author William Walton
   */
  public void hasCollided() {
    durability -= durability - durabilityPerHit <= 0 ? 0 : durabilityPerHit;
    if (maxSpeed >= 10) {
      maxSpeed -= 0.5;
    }
    
  }

  /**
   * Applies collision with speed bonus.
   * 
   * @author Ben Dunbar
   */
  public void hasCollidedSpeed() {
    if (maxSpeed <= 20) {
      maxSpeed += 3;
    }
  }

  /**
   * Applies collision with health bonus.
   * 
   * @author Ben Dunbar
   */
  public void hasCollidedHealth() {
    if (durability < 0.9) {
      durability += 0.1;
    } else {
      durability = 1;
    }
  }

  /**
   * Applies collision with energy bonus.
   * 
   * @author Ben Dunbar
   */
  public void hasCollidedEnergy() {
    if (stamina < 0.75) {
      stamina += 0.25;
    } else {
      stamina = 1;
    }
  }

  /**
   * Applies collision with rotation bonus.
   * 
   * @author Ben Dunbar
   */
  public void hasCollidedRotation() {
    if (rotationSpeed < 2.75) {
      rotationSpeed += 0.2;
    } else {
      rotationSpeed = 3;
    }
  }

  /**
   * Applies collision with rotation bonus.
   * 
   * @author Ben Dunbar
   */
  public void hasCollidedDrag() {
    if (drag > 0.22f) {
      drag -= 0.02f;
    } else {
      drag = 0.2f;
    }
  }


  /**
   * Function called when the boat accelerates.
   *
   * @author William Walton
   */
  @Override
  public void accelerate() {
    stamina = stamina - staminaUsage <= 0 ? 0 : stamina - staminaUsage;
    if (stamina > 0) {
      super.accelerate();
      framesToAnimate += 1;
    }

    if (framesToAnimate > 0) {
      setAnimationFrame(currentAnimationFrame);
      framesElapsed++;
      if (framesElapsed % 15 == 0) {
        currentAnimationFrame++;
      }
      framesToAnimate--;
    } else {
      // reset everything
      setAnimationFrame(0);
      currentAnimationFrame = 0;
      framesElapsed = 0;
      framesToAnimate = 0;
    }
  }

  /**
   * Function called every frame when the game updates all objects positions.
   *
   * @author William Walton
   */
  @Override
  public void updatePosition() {
    super.updatePosition();
    stamina = stamina + staminaRegen >= 1 ? 1.f : stamina + staminaRegen;
  }

  // Getter and Setter methods for attributes

  public long getFramesRaced() {
    return framesRaced;
  }

  public void setFramesRaced(long framesRaced) {
    this.framesRaced = framesRaced;
  }

  public void addFrameRaced() {
    framesRaced++;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the start time of a boat in milliseconds. E.g. Pass use
   * System.currentTimeMillis() to get current system time and pass this long into
   * this method.
   *
   * @param startTime long value which is start time of the boat.
   * @author Umer Fakher
   */
  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  /**s
   * Returns the long value start time of the boat.
   *
   * @param inSeconds boolean to decide if the time should be returned in seconds
   *                  or in milliseconds.
   * @return the long value start time
   * @author Umer Fakher
   */
  public long getStartTime(boolean inSeconds) {
    if (inSeconds) {
      return this.startTime / 1000; // Milliseconds to Seconds conversion 1000:1
    }
    return this.startTime;
  }

  /**
   * Sets the end time of a boat in milliseconds. E.g. Pass use
   * System.currentTimeMillis() to get current system time and pass this long into
   * this method.
   *
   * @param endTime long value which is end time of the boat.
   * @author Umer Fakher
   */
  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  /**
   * Returns the long value end time of the boat.
   *
   * @param inSeconds boolean to decide if the time should be returned in seconds
   *                  or in milliseconds.
   * @return the long value end time
   * @author Umer Fakher
   */
  public long getEndTime(boolean inSeconds) {
    if (inSeconds) {
      // Milliseconds to Seconds conversion 1000:1
      return this.endTime / 1000;
    }
    return this.endTime;
  }

  /**
   * Returns the difference between the end time and start time in milliseconds.
   *
   * @return long value time difference
   * @author Umer Fakher
   */
  public long getCalcTime() {
    return timeToAdd + (this.endTime - this.startTime);
  }

  /**
   * Adds the difference between end time and start time into the leg times list
   * as a long value.
   *
   * @author Umer Fakher
   */
  public void setLegTime() {
    this.legTimes.add(this.getCalcTime());
  }

  /**
   * Returns recorded leg times of this boat.
   *
   * @return List of Long Returns a list of long types in milliseconds.
   * @author Umer Fakher
   */
  public List<Long> getLegTimes() {
    return legTimes;
  }

  /**
   * Returns the time penalties to be added this boat accumulated by crossing the
   * lines.
   *
   * @return Returns a long time in milliseconds.
   */
  public long getTimeToAdd() {
    return timeToAdd;
  }

  /**
   * Sets the time penalties to be added by this boat accumulated by crossing the
   * lines.
   *
   * @param timeToAdd Recorded long time in milliseconds.
   */
  public void setTimeToAdd(long timeToAdd) {
    this.timeToAdd = timeToAdd;
  }

  /**
   * Checks to see if the this boat has collided with the other CollisionObject
   * object passed.
   *
   * @param object The CollisionObject that will be checked to see if it has hit
   *               this boat.
   * @author Umer Fakher
   */
  public void checkCollisions(CollisionObject object) {
    if (object instanceof Obstacle && !(((Obstacle) object).getSprite().getY() > sprite.getY() - 200
            && ((Obstacle) object).getSprite().getY() < sprite.getY() + 200
            && ((Obstacle) object).getSprite().getX() > sprite.getX() - 200
            && ((Obstacle) object).getSprite().getX() < sprite.getX() + 200)) {
      return;
    }
    if (this.getBounds().isColliding(object.getBounds())) {
      if (object instanceof PowerUpSpeed) {
        hasCollidedSpeed();
      } else if (object instanceof PowerUpHealth) {
        hasCollidedHealth();
      } else if (object instanceof PowerUpEnergy) {
        hasCollidedEnergy();
      } else {
        hasCollided();
      }
      // if (object instanceof PowerUpSpeed)
      // hasCollidedSpeed();
      object.hasCollided();
    }
  }

  /**
   * Used to return the CollisionBounds object representing this boat. Used for
   * collision detection
   *
   * @author William Walton
   */
  @Override
  public CollisionBounds getBounds() {
    // create a new collision bounds object representing my current position
    // see the collision bounds visualisation folder in assets for a visual
    // representation
    CollisionBounds myBounds = new CollisionBounds();
    Rectangle mainRect = new Rectangle(sprite.getX() + (0.32f * sprite.getWidth()),
            sprite.getY() + (0.117f * sprite.getHeight()), 0.32f * sprite.getWidth(),
            0.77f * sprite.getHeight());
    myBounds.addBound(mainRect);

    myBounds.setOrigin(new Vector2(sprite.getX() + (sprite.getWidth() / 2),
                                   sprite.getY() + (sprite.getHeight() / 2)));
    myBounds.setRotation(sprite.getRotation());

    return myBounds;
  }

  // Getters and Setters for has_started_leg and has_finished_leg

  public boolean hasFinishedLeg() {
    return hasFinishedLeg;
  }

  public void setHasFinishedLeg(boolean hasFinishedLeg) {
    this.hasFinishedLeg = hasFinishedLeg;
  }

  public boolean hasStartedLeg() {
    return hasStartedLeg;
  }

  public void setHasStartedLeg(boolean hasStartedLeg) {
    this.hasStartedLeg = hasStartedLeg;
  }

  /**
   * Reset max_speed, durability and stamina to defaults.
   */
  public void reset() {
    this.maxSpeed = 15;
    this.durability = 1;
    this.stamina = 1;
  }

  /**
   * Gets current best time for boat from its list of leg_times.
   *
   * @return long time in milliseconds.
   */
  public long getBestTime() {
    long currentBest = -1;

    for (long time : legTimes) {
      if (time > currentBest) {
        currentBest = time;
      }
    }

    return currentBest;
  }
}

