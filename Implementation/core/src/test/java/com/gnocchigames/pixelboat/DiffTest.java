package com.gnocchigames.pixelboat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.teamonehundred.pixelboat.entities.PlayerBoat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for Difficulty settings.
 */
public class DiffTest {
  private static Application application;

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
   * .
   */
  @AfterAll
  public static void cleanUp() {
    // Exit the application first
    application.exit();
    application = null;
  }
   
  /**
   * tests set diff for easy difficulty.
   */

  @Test
  public void testEasy() {
    int diffLevel = 1;
    PlayerBoat testBoat = new PlayerBoat(40, 40);

    testBoat.setDiff(diffLevel);
    float expectedStaminaRegain = .002f;
    assertEquals(expectedStaminaRegain, testBoat.getStaminaRegain(), 
        "Easy: Stamina regain does not match");

    float expectedStaminaUsage = 0.005f;
    assertEquals(expectedStaminaUsage, testBoat.getStaminaUsage(),
        "Easy: Stamina usage does not match");

    float expectedDurabilityPerHit = .1f;
    assertEquals(expectedDurabilityPerHit, testBoat.getDurabilityPerHit(), 
        "Easy: durabilityPerHit does not match");
  }
  /**
   *  tests setDiff for medium difficulty.
   */

  @Test
  public void testMedium() {
    int diffLevel = 2;
    PlayerBoat testBoat = new PlayerBoat(40, 40);
    testBoat.setDiff(diffLevel);

    float expectedStaminaRegain = .002f * 0.7f;
    assertEquals(expectedStaminaRegain, testBoat.getStaminaRegain(), 
        "Med: Stamina regain does not match");

    float expectedStaminaUsage = 0.005f * 1.3f;
    assertEquals(expectedStaminaUsage, testBoat.getStaminaUsage(), 
        "Med: Stamina usage does not match");

    float expectedDurabilityPerHit = .1f * 2.0f;
    assertEquals(expectedDurabilityPerHit, testBoat.getDurabilityPerHit(),
        "Med: durabilityPerHit does not match");

  }

  /**
   * test setDiff for hard difficulty.
   */
  @Test
  public void testHard() {
    int diffLevel = 3;
    PlayerBoat testBoat = new PlayerBoat(40, 40);
    testBoat.setDiff(diffLevel);
        
    float expectedStaminaRegain = .002f * 0.4f;
    assertEquals(expectedStaminaRegain, testBoat.getStaminaRegain(), 
        "Hard: Stamina regain does not match");

    float expectedStaminaUsage = 0.005f * 1.7f;
    assertEquals(expectedStaminaUsage, testBoat.getStaminaUsage(), 
        "Hard: Stamina usage does not match");

    float expectedDurabilityPerHit = .1f * 2.5f;
    assertEquals(expectedDurabilityPerHit, testBoat.getDurabilityPerHit(),
        "Hard: durabilityPerHit does not match");
  }
}