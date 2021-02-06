package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

/**
 * Represents the AI's boat.
 *
 * @author James Frost
 *     JavaDoc by Umer Fakher
 */
public class AiBoat extends Boat {
  /* ################################### //
                  ATTRIBUTES
  // ################################### */

  protected float numberOfRays;
  protected float rayAngleRange;
  protected float rayRage;
  protected float rayStepSize;
  protected boolean regen;

  /* ################################### //
            CONSTRUCTORS
  // ################################### */

  /**
   * Construct a AIBoat object at point (x,y) with default size, texture and animation.
   *
   * @param x float coordinate for the bottom left point of the boat
   * @param y float coordinate for the bottom left point of the boat
   * @author James Frost
   */
  public AiBoat(float x, float y) {
    super(x, y);

    initialise();
  }

  /**
   * Construct a AIBoat object with at point (x,y) with width and height and texture path
   * with default stats (stamina usage, durability, etc).
   *
   * @param x            int coordinate for the bottom left point of the boat
   * @param y            int coordinate for the bottom left point of the boat
   * @param w            int width of the new boat
   * @param h            int height of the new boat
   * @param texturePath String relative path from the core/assets folder of the boats texture image
   * @author James Frost
   */
  AiBoat(int x, int y, int w, int h, String texturePath) { 
    // So this section will just initialise the AI boat, it doesn't need the intialise method of
    // playerboat due to the fact it doesn't have any textures for durability / stamina
    super(x, y, w, h, texturePath);

    initialise();
  }

  /**
   * Construct a AIBoat object with all parameters specified.
   *
   * @param x                  int coordinate for the bottom left point of the boat
   * @param y                  int coordinate for the bottom left point of the boat
   * @param w                  int width of the new boat
   * @param h                  int height of the new boat
   * @param texturePath       String relative path from the core/assets folder of
   *                           the boats texture image
   * @param durabilityPerHit float percentage (0-1) of the max durability taken each hit
   * @param name               String of the boat seen when the game ends
   * @param staminaRegen      float percentage of stamina regenerated each frame (0-1)
   * @param staminaUsage      float percentage of stamina used each frame when accelerating (0-1)
   * @author James Frost
   */
  AiBoat(int x, int y, int w, int h, String texturePath, String name,
      float durabilityPerHit, float staminaUsage, float staminaRegen) {
    super(x, y, w, h, texturePath, name,
        durabilityPerHit, staminaUsage, staminaRegen); 
    // This should be the init that is used mostly 
    // (but the other one is needed incase someone messes up)

    initialise();

  }

  /**
   * Shared initialisation functionality among all constructors.
   * 
   * <p>Initialises the ray properties. Rays are used to help the AI control
   * the boat based on visual feedback of its environment i.e. obstacles such
   *  as movable obstacles and static lane wall obstacles.
   *
   * @author James Frost
   */
  public void initialise() {
    numberOfRays = 4; // how many rays are fired from the boat
    rayAngleRange = 145; // the range of the angles that the boat will fire rays out at
    rayRage = 30; // the range of each ray
    rayStepSize = (float) 10;
    regen = false;
  }

  /**
   * Updates position of objects AIBoat based on acceleration and stamina.
   * 
   * <p>Checks if AIBoat can turn and updates position accordingly based on any
   * collision objects that may overlap.
   *
   * @param collidables List of Collision Objects
   * @author James Frost
   */
  public void updatePosition(List<CollisionObject> collidables) {
    // TODO: Make this a method, and neaten it up
    // TODO: Link Acc w/ turning for better AI (that one may take a bit of time though)
    // TODO: Visible stamina for AI (maybe as debug option)
    if (!regen) {
      this.accelerate();
      if (stamina <= 0.1) {
        regen = true;
      }
    } else {
      if (stamina >= 0.5) {
        regen = false;
      }
    }
    // todo fix this, it takes too long
    this.check_turn(collidables);
    super.updatePosition();

  }

  /**
   * Returns true if AIBoat should exist on the screen.
   *
   * @return boolean parent isShown
   * @author James Frost
   */
  @Override
  public boolean isShown() {
    return super.isShown();
  }

  /**
   * Return centre coordinates of point where ray is fired.
   *
   * @return Vector2 of coordinates
   * @author James Frost
   */
  protected Vector2 get_ray_fire_point() {
    Vector2 p = new Vector2(
            sprite.getX() + (sprite.getWidth() / 2),
            sprite.getY() + (sprite.getHeight()));

    Vector2 p1 = p.rotateAround(new Vector2(
                    sprite.getX() + (sprite.getWidth() / 2),
                    sprite.getY() + (sprite.getHeight() / 2)),
            sprite.getRotation());

    return p1;
  }

  /**
   * Fire a number of rays with limited distance out the front of the boat, select a ray that
   * isn't obstructed by an object, preference the middle (maybe put a preference to side as well)
   * if every ray is obstructed either (keep turning [left or right] on the spot until one is,
   * or choose the one that is obstructed furthest away the second option
   * (choose the one that is obstructed furthest away) is better.
   *
   * @param collidables List of Collision Objects
   * @author James Frost
   */
  protected void check_turn(List<CollisionObject> collidables) {
    //Firing rays

    //select an area of 180 degrees (pi radians)
    // this is a very cheeky way of solving the problem, but has a few benefits
    boolean cheekyBitOfCoding = true;
    //TODO: Explain the cheeky_bit_of_coding better
    Vector2 startPoint = get_ray_fire_point();
    for (int ray = 0; ray <= numberOfRays; ray++) {
      if (cheekyBitOfCoding) {
        ray--;
        float rayAngle = sprite.getRotation() + ((rayAngleRange / (numberOfRays / 2)) * ray);
        cheekyBitOfCoding = false;
      } else {
        float rayAngle = sprite.getRotation() - ((rayAngleRange / (numberOfRays / 2)) * ray);
        cheekyBitOfCoding = true;
      }

      float rayAngle = ((rayAngleRange / numberOfRays) * ray) + sprite.getRotation();

      for (float dist = 0; dist <= rayRage; dist += rayStepSize) {

        double tempx = (Math.cos(Math.toRadians(rayAngle)) * dist) + (startPoint.x);
        double tempy = (Math.sin(Math.toRadians(rayAngle)) * dist) + (startPoint.y);
        //check if there is a collision hull (other than self) at (tempx, tempy)
        for (CollisionObject collideable : collidables) {
          // very lazy way of optimising this code. will break if the collidable isn't an obstacle
          if (collideable.isShown()
                  && ((Obstacle) collideable).getSprite().getY() > sprite.getY() - 200 
                  && ((Obstacle) collideable).getSprite().getY() < sprite.getY() + 200 
                  && ((Obstacle) collideable).getSprite().getX() > sprite.getX() - 200 
                  && ((Obstacle) collideable).getSprite().getX() < sprite.getX() + 200) {
            for (Shape2D bound : collideable.getBounds().getShapes()) {
              if (bound.contains((float) tempx, (float) tempy)) {
                if (cheekyBitOfCoding) {
                  turn(-1);
                  return;
                } else {
                  turn(1);
                  return;
                }

              }
            }
          }

        }
      }
    }
  }
}
