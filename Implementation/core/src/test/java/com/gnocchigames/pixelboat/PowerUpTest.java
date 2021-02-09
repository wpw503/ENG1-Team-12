package com.gnocchigames.pixelboat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.teamonehundred.pixelboat.entities.Boat;
import com.teamonehundred.pixelboat.entities.PlayerBoat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for the power-ups.
 */
public class PowerUpTest {
    private static Application application;   
}

/**
   * Setup the test environment.
   */
  @BeforeAll
  public static void setupTest() {
    application = new HeadlessApplication(new ApplicationListener() {

      @Override public void create() {}

      @Override public void resize(int width, int height) {}

      @Override public void render() {}

      @Override public void pause() {}

      @Override public void resume() {}
      
      @Override public void dispose() {}
    });
    Gdx.gl20 = Mockito.mock(GL20.class);
    Gdx.gl = Gdx.gl20;
  }
  
  /**
   * end test enviroment.
   */
  @AfterAll
  public static void cleanUp() {
    // Exit the application first
    application.exit();
    application = null;
  }

  /**
   * tests the speed power-up
   */
  @Test
  public void testPowerUpSpeed() {
      PlayerBoat testBoat = new PlayerBoat(40, 40);

      testBoat.hasCollidedSpeed();
      float expectedDruability = 1;
      assertEquals(expectedDruability, testBoat.getDurability(), 
        "Speed: Durability does not match.");

      float expectedSpeed = 18;
      assertEquals(expectedSpeed, testBoat.getSpeed(), 
        "Speed: Speed does not match.");
  }

  @Test
  public void testPowerUpHealth() {
      PlayerBoat testBoat = new PlayerBoat(40, 40);
      
      testBoat.hasCollidedSpeed();
      float expectedDruability = 1;
      assertEquals(expectedDruability, testBoat.getDurability(), 
        "Health: Durability does not match.");

      float expectedSpeed = 15;
      assertEquals(expectedSpeed, testBoat.getSpeed(), 
        "Health: Speed does not match.");
  }

  @Test
  public void testPowerUpStamina() {
      PlayerBoat testBoat = new PlayerBoat(40, 40);
      
      testBoat.hasCollidedStamina();
      float expectedDruability = 1;
      assertEquals(expectedDruability, testBoat.getDurability(), 
        "Stamina: Durability does not match.");

      float expectedSpeed = 15;
      assertEquals(expectedSpeed, testBoat.getSpeed(), 
        "Stamina: Speed does not match.");

      float expectedStamina = 1;
      assertEquals(expectedStamina, testBoat.getStamina(), 
        "Stamina: Stamina does not match.");
  }

  @Test
  public void testPowerUpDrag() {
      PlayerBoat testBoat = new PlayerBoat(40, 40);
      
      testBoat.hasCollidedDrag();
      float expectedDruability = 1;
      assertEquals(expectedDruability, testBoat.getDurability(), 
        "Drag: Durability does not match.");

      float expectedSpeed = 15;
      assertEquals(expectedSpeed, testBoat.getSpeed(), 
        "Drag: Speed does not match.");

      float expectedDrag = 0.038f;
      assertEquals(expectedDrag, testBoat.getDrag(), 
        "Drag: Drag does not match.");
  }

  @Test
  public void testPowerUpRotation() {
      PlayerBoat testBoat = new PlayerBoat(40, 40);
      
      testBoat.hasCollidedRotation();
      float expectedDruability = 1;
      assertEquals(expectedDruability, testBoat.getDurability(), 
        "Rotation: Durability does not match.");

      float expectedSpeed = 15;
      assertEquals(expectedSpeed, testBoat.getSpeed(), 
        "Rotation: Speed does not match.");

      float expectedRotation = 2.2f;
      assertEquals(expectedRotation, testBoat.getRotation(), 
        "Rotation: Rotation speed does not match.");
  }
