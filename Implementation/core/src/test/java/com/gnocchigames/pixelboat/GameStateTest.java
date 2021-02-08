package com.gnocchigames.pixelboat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.teamonehundred.pixelboat.BoatRace;
import com.teamonehundred.pixelboat.GameState;
import com.teamonehundred.pixelboat.PixelBoat;
import com.teamonehundred.pixelboat.entities.AiBoat;
import com.teamonehundred.pixelboat.entities.Boat;
import com.teamonehundred.pixelboat.entities.CollisionObject;
import com.teamonehundred.pixelboat.entities.Obstacle;
import com.teamonehundred.pixelboat.entities.PowerUp;
import com.teamonehundred.pixelboat.scenes.SceneMainGame;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for the GameState class.
 */
public class GameStateTest {

  private static Application application;
  private SceneMainGame game;
  private BoatRace race;

  /**
   * .
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
   * Test getPlayerIndex.
   */
  @Test
  public void testPlayerIndex() {

    SceneMainGame game = new SceneMainGame();

    // Create GameState object

    GameState testState = new GameState(
        game.getAllBoats(),
        game.getPlayer(),
        game.getRace().obstacles,
        game.getRace().powerups,
        game.getLegNumber(),
        game.isLastRun(),
        game.getRace().isFinished(),
        game.getRace().totalFrames
    );

    // Test getPlayerIndex
    int expectedPlayerIndex = game.getAllBoats().indexOf(game.getPlayer());

    assertEquals(
        expectedPlayerIndex,
        testState.getPlayerIndex(),
        "GameState player index does not match.");
  }

  /**
   * Test getBoatList().
   */
  @Test
  public void testBoatList() {

    SceneMainGame game = new SceneMainGame();

    // Create GameState object

    GameState testState = new GameState(
        game.getAllBoats(),
        game.getPlayer(),
        game.getRace().obstacles,
        game.getRace().powerups,
        game.getLegNumber(),
        game.isLastRun(),
        game.getRace().isFinished(),
        game.getRace().totalFrames
    );

    // Test getAllBoats.
    List<Boat> expectedAllBoats = game.getAllBoats();

    List<Boat> actualAllBoats = testState.getBoatList();

    assertEquals(
        expectedAllBoats.size(),
        actualAllBoats.size()
    );

    for (int i = 0; i < expectedAllBoats.size(); i++) {
      Boat expected = expectedAllBoats.get(i);
      Boat actual = actualAllBoats.get(i);

      // Check position
      assertEquals(expected.getSprite().getX(), actual.getSprite().getX(), "X does not match");
      assertEquals(expected.getSprite().getY(), actual.getSprite().getY(), "Y does not match");

      // Check size
      assertEquals(
          expected.getSprite().getHeight(),
          actual.getSprite().getHeight(),
          "Height does not match"
      );
      assertEquals(
          expected.getSprite().getWidth(),
          actual.getSprite().getWidth(),
          "Width does not match"
      );
      
      // Check both boats are of same class
      assertEquals(
          expected.getClass(),
          actual.getClass(),
          "Class does not match"    
      );

    }



  }

  @Test
  public void testPowerUps() {
    SceneMainGame game = new SceneMainGame();

    // Create GameState object

    GameState testState = new GameState(
        game.getAllBoats(),
        game.getPlayer(),
        game.getRace().obstacles,
        game.getRace().powerups,
        game.getLegNumber(),
        game.isLastRun(),
        game.getRace().isFinished(),
        game.getRace().totalFrames
    );

    // Test getPowerupsList.
    List<CollisionObject> expectedPowerups = game.getRace().powerups;

    List<CollisionObject> actualPowerups = testState.getPowerupsList();

    assertEquals(
        expectedPowerups.size(),
        actualPowerups.size(),
        "List size does not match"
    );

    for (int i = 0; i < expectedPowerups.size(); i++) {
      PowerUp expected = (PowerUp) expectedPowerups.get(i);
      PowerUp actual = (PowerUp) actualPowerups.get(i);

      // Check position
      assertEquals(expected.getSprite().getX(), actual.getSprite().getX(), "X does not match");
      assertEquals(expected.getSprite().getY(), actual.getSprite().getY(), "Y does not match");

      // Check size
      assertEquals(
          expected.getSprite().getHeight(),
          actual.getSprite().getHeight(),
          "Height does not match"
      );
      assertEquals(
          expected.getSprite().getWidth(),
          actual.getSprite().getWidth(),
          "Width does not match"
      );
      
      // Check both powerups are of same class
      assertEquals(
          expected.getClass(),
          actual.getClass(),
          "Class does not match"    
      );
    }
  }

  @Test
  public void testCollisionObstacles() {
    SceneMainGame game = new SceneMainGame();
    
    // Create GameState object

    GameState testState = new GameState(
        game.getAllBoats(),
        game.getPlayer(),
        game.getRace().obstacles,
        game.getRace().powerups,
        game.getLegNumber(),
        game.isLastRun(),
        game.getRace().isFinished(),
        game.getRace().totalFrames
    );

    // Test getCollionObjects.
    List<CollisionObject> expectedObjects = game.getRace().obstacles;

    List<CollisionObject> actualObjects = testState.getCollisionObjects();

    assertEquals(
        expectedObjects.size(),
        actualObjects.size(),
        "List size does not match"
    );

    for (int i = 0; i < expectedObjects.size(); i++) {
      Obstacle expected = (Obstacle) expectedObjects.get(i);
      Obstacle actual = (Obstacle) actualObjects.get(i);

      // Check position
      assertEquals(expected.getSprite().getX(), actual.getSprite().getX(), "X does not match");
      assertEquals(expected.getSprite().getY(), actual.getSprite().getY(), "Y does not match");

      // Check size
      assertEquals(
          expected.getSprite().getHeight(),
          actual.getSprite().getHeight(),
          "Height does not match"
      );
      assertEquals(
          expected.getSprite().getWidth(),
          actual.getSprite().getWidth(),
          "Width does not match"
      );
      
      // Check both obstacles are of same class
      assertEquals(
          expected.getClass(),
          actual.getClass(),
          "Class does not match"    
      );
    }
  }


}
