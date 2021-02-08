package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BoatRace. Call functionality for sprite objects such as boats and obstacles.
 *
 * @author William Walton
 * @author Umer Fakher JavaDoc by Umer Fakher
 */
public class BoatRace {
  public List<Boat> boats;

  protected BitmapFont font; //TimingTest
  protected Texture laneSep;
  protected Texture startBanner;
  protected Texture bleachersL;
  protected Texture bleacherR;

  public List<CollisionObject> obstacles;
  public List<CollisionObject> powerups;

  protected int startY = 200;
  protected int endY = 40000;

  protected int laneWidth = 400;
  protected int penaltyPerFrame = 1; // ms to add per frame when over the lane

  public boolean isFinished = false;
  public long totalFrames = 0;

  public int legNo = 0;

  /**
   * Main constructor for a BoatRace.
   * 
   * <p>Initialises lists of boats and obstacles as well as the colour of the Time Elapsed Overlay.
   *
   * @param raceBoats List of Boat A list of ai boats and the player boat.
   * @author William Walton
   * @author Umer Fakher JavaDoc by Umer Fakher
   */
  public BoatRace(List<Boat> raceBoats) {
    laneSep = new Texture("lane_buoy.png");
    startBanner = new Texture("start_banner.png");
    bleachersL = new Texture("bleachers_l.png");
    bleacherR = new Texture("bleachers_r.png");

    boats = new ArrayList<>();
    boats.addAll(raceBoats);

    for (int i = 0; i < boats.size(); i++) {
      boats.get(i).hasStartedLeg = false;
      boats.get(i).hasFinishedLeg = false;

      boats.get(i).reset_motion();
      boats.get(i).sprite.setPosition(getLaneCentre(i), 40);  // reset boats y and place in lane
      boats.get(i).setFramesRaced(0);
      boats.get(i).reset();

      if (boats.get(i) instanceof PlayerBoat) {
        ((PlayerBoat) boats.get(i)).resetCameraPos();
      }
    }

    obstacles = new ArrayList<>();

    // add some random obstacles
    for (int i = 0; i < 100; i++) {
      obstacles.add(new ObstacleBranch(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    for (int i = 0; i < 100; i++) {
      obstacles.add(new ObstacleFloatingBranch(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    for (int i = 0; i < 100; i++) {
      obstacles.add(new ObstacleDuck(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    // add the lane separators
    for (int lane = 0; lane <= boats.size(); lane++) {
      for (int height = 0; height <= endY; height += ObstacleLaneWall.texture_height) {
        obstacles.add(new ObstacleLaneWall(getLaneCentre(lane) - laneWidth / 2, height, laneSep));
      }
    }

    powerups = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      powerups.add(new PowerUpSpeed(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    for (int i = 0; i < 100; i++) {
      powerups.add(new PowerUpHealth(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    for (int i = 0; i < 100; i++) {
      powerups.add(new PowerUpEnergy(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    for (int i = 0; i < 100; i++) {
      powerups.add(new PowerUpRotation(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    for (int i = 0; i < 100; i++) {
      powerups.add(new PowerUpDrag(
              (int) (-(laneWidth * boats.size() / 2)
                    + Math.random() * (laneWidth * boats.size())),
              (int) (startY + 50 + Math.random() * (endY - startY - 50))));
    }

    // Initialise colour of Time Elapsed Overlay
    font = new BitmapFont();
    font.setColor(Color.RED);
  }

  private int getLaneCentre(int boatIndex) {
    int raceWidth = boats.size() * laneWidth;
    return (-raceWidth / 2) + (laneWidth * (boatIndex + 1)) - (laneWidth / 2);
  }

  /**
   * Main method called for BoatRace.
   * 
   * <p>This method is the main game loop that checks if any boats have started or
   * finished a leg and calls the update methods for the movements for player boat
   * and AI boats obstacles. Also this method checks for collisions.
   *
   * @author William Walton
   * @author Umer Fakher
   */
  public void runStep() {
    // dnf after 5 mins
    if (totalFrames++ > 60 * 60 * 5) {
      isFinished = true;
      for (Boat b : boats) {
        if (!b.hasFinishedLeg()) {
          b.setStartTime(0);
          b.setEndTime((long) (b.getStartTime(false) + ((1000.0 / 60.0) * b.getFramesRaced())));
          b.setLegTime();
        }
      }
    }

    for (CollisionObject c : obstacles) {
      if (c instanceof Obstacle) {
        ((Obstacle) c).updatePosition();
      }
      if (c instanceof ObstacleLaneWall) {
        ((ObstacleLaneWall) c).setAnimationFrame(0);
      }
    }

    for (CollisionObject c : powerups) {
      if (c instanceof PowerUp) {
        ((PowerUp) c).updatePosition();
      }
    }

    for (Boat boat : boats) {
      // check if any boats have finished
      if (!boat.hasFinishedLeg() && boat.getSprite().getY() > endY) {
        // store the leg time in the object
        boat.setStartTime(0);
        boat.setEndTime(
            (long) (boat.getStartTime(false) + ((1000.0 / 60.0) * boat.getFramesRaced())));
        boat.setLegTime();
        

        boat.setHasFinishedLeg(true);
      } else if (!boat.hasStartedLeg() && boat.getSprite().getY() > startY) {
        // if any boats have started
        boat.setStartTime(System.currentTimeMillis());
        boat.setHasStartedLeg(true);
        boat.setFramesRaced(0);
      } else {
        // if not start or end, must be racing
        boat.addFrameRaced();
      }
    }

    boolean notFinished = false;

    for (int i = 0; i < boats.size(); i++) {
      // all boats
      if (!boats.get(i).hasFinishedLeg()) {
        notFinished = true;
      }

      // update boat (handles inputs if player, etc)
      if (boats.get(i) instanceof AiBoat) {
        ((AiBoat) boats.get(i)).updatePosition(obstacles);
      } else if (boats.get(i) instanceof PlayerBoat) {
        boats.get(i).updatePosition();
      }

      // check for collisions
      for (CollisionObject obstacle : obstacles) {
        if (obstacle.isShown()) {
          boats.get(i).checkCollisions(obstacle);
        }
      }

      for (CollisionObject powerup : powerups) {
        if (powerup.isShown()) {
          boats.get(i).checkCollisions(powerup);
        }
      }

      // check if out of lane
      if (boats.get(i).getSprite().getX() > getLaneCentre(i) + laneWidth / 2
              || boats.get(i).getSprite().getX() < getLaneCentre(i) - laneWidth / 2) {
        boats.get(i).setTimeToAdd(boats.get(i).getTimeToAdd() + penaltyPerFrame);
      }
    }
    isFinished = !notFinished;
  }

  public boolean isFinished() {
    return isFinished;
  }

  /**
   * Returns a list of all sprites in the PixelBoat game including boats and
   * obstacles.
   *
   * @return List of Sprites A list of all sprites in the PixelBoat game.
   * @author William Walton
   * @author Umer Fakher
   */
  public List<Sprite> getSprites() {
    List<Sprite> allSprites = new ArrayList<>();

    for (CollisionObject obs : obstacles) {
      // check if can be cast back up
      if (obs instanceof Obstacle && obs.isShown()) {
        allSprites.add(((Obstacle) obs).getSprite());
      }
    }

    for (CollisionObject pus : powerups) {
      // check if can be cast back up
      if (pus instanceof PowerUp && pus.isShown()) {
        allSprites.add(((PowerUp) pus).getSprite());
      }
    }

    for (Boat b : boats) {
      allSprites.add(b.getSprite());
      if (b instanceof PlayerBoat) {
        allSprites.addAll(((PlayerBoat) b).getUiSprites());
      }
    }

    return allSprites;
  }

  /**
   * Calculates and displays the Time Elapsed Overlay for player boat from the
   * start of a leg.
   * 
   * <p>The displayed time is updated in real-time and the position is consistent
   * with the player hud (i.e. stamina and durability bar positions).
   *
   * @param batch batch to draw to
   * @author Umer Fakher
   */
  public void draw(SpriteBatch batch) {

    // Retrieves sprites and calls function recursively.
    for (Sprite sp : getSprites()) {
      sp.draw(batch);
    }

    for (Boat b : boats) {
      // If current boat b is the player's boat then can display hud for this boat
      if (b instanceof PlayerBoat) {
        if (((PlayerBoat) b).hasStartedLeg()) {
          // Calculate time elapsed from the start in milliseconds
          long i = (System.currentTimeMillis() - ((PlayerBoat) b).getStartTime(false));

          // Displays and updates the time elapsed overlay and keeps position consistent
          // with player's boat
          drawTimeDisplay(batch, b, "", i, -((PlayerBoat) b).uiBarWidth / 2,
                  500 + ((PlayerBoat) b).getSprite().getY());

          // Draws a leg time display on the screen when the given boat has completed a
          // leg of the race.
          drawLegTimeDisplay(batch, b);
        }
      }
    }

    int raceWidth = boats.size() * laneWidth;
    
    for (int i = -1000; i < endY + 1000; i += 800) {
      batch.draw(bleacherR, raceWidth / 2 + 400, i, 400, 800);
    }
    for (int i = -1000; i < endY + 1000; i += 800) {
      batch.draw(bleachersL, -raceWidth / 2 - 800, i, 400, 800);
    }
    for (int i = 0; i < boats.size(); i++) {
      batch.draw(startBanner, 
                (getLaneCentre(i)) - (laneWidth / 2),
                startY, laneWidth, laneWidth / 2);
    }
    Texture temp = new Texture("object_placeholder.png");
    batch.draw(temp, -raceWidth / 2, endY, raceWidth, 5);
    
    temp.dispose();
  }

  /**
   * Draws the a time display on the screen.
   *
   * @param batch      SpriteBatch instance
   * @param b          Boat instance
   * @param labelText label for text. If "" empty string passed in then default time display shown.
   * @param time       time to be shown in milliseconds
   * @param x          horizontal position of display
   * @param y          vertical position of display
   * @author Umer Fakher
   */
  public void drawTimeDisplay(SpriteBatch batch, Boat b, String labelText,
                              long time, float x, float y) {
    if (labelText.equals("")) {
      labelText = "Time (min:sec) = %02d:%02d";
    }
    font.draw(batch, String.format(labelText, time / 60000, time / 1000 % 60), x, y);
  }

  /**
   * Draws a leg time display on the screen when the given boat has completed a leg of the race.
   * 
   * <p>This function gets the leg times list for the given boat instance, gets the last updated 
   * leg time and formats a leg time display string which shows which leg was completed and 
   * in what time.
   * The function then passes on the drawing of this formatted leg time display to drawTimeDisplay.
   *
   * @param batch SpriteBatch instance
   * @param b     Boat instance
   * @author Umer Fakher
   */
  public void drawLegTimeDisplay(SpriteBatch batch, Boat b) {
    if (b.getEndTime(false) != -1) {
      for (long l : b.getLegTimes()) {
        String label = String.format(
            "Leg Time %d (min:sec) = ",
            b.getLegTimes().indexOf(l) + 1) + "%02d:%02d";
        drawTimeDisplay(batch, b, label, l, -((PlayerBoat) b).uiBarWidth / 2,
                500 - ((b.getLegTimes().indexOf(l) + 1) * 20) + ((PlayerBoat) b)
                .getSprite().getY());
      }

    }
  }
  
  /**
   * Estimates the times for all boats at the end of a race. 
   * It also sets the boats to the finished state. The race is also set to finished.
   *
   * @author Adam Blanchet
   */
  public void estimateEndTimes() {

    // Loop through all the boats that haven't finished the leg

    for (Boat boat : boats) {

      if (!boat.hasFinishedLeg()) {

        boat.setStartTime(0);
        
        // TODO: make sure this is accurate enough
        // Estimate the remaining time: using 50% of max speed as an estimate
        float distanceToFinish = endY - boat.getSprite().getY();

        double cruiseSpeed = boat.maxSpeed * 0.5;

        float remainingTime = (float) (distanceToFinish / cruiseSpeed);

        // Set the time for the boat and then set the boat as finished
        boat.setEndTime(
            (long) (boat.getStartTime(false)
            + ((1000.0 / 60.0) * boat.getFramesRaced())
            + remainingTime));
        boat.setLegTime();

        boat.setHasFinishedLeg(true);

      }

    }
    // Set the race as finished

    this.isFinished = true;

  }

}
