package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teamonehundred.pixelboat.PixelBoat;
import java.io.IOException;

/**
 * SceneSaveScreen.
 */
public class SceneSaveScreen implements Scene {

  Stage stage;
  Preferences prefs;
  Texture bg;
  Sprite bgSprite;
  TextArea saveNameInput;
  Table layout;

  boolean shouldSave = false;
  boolean shouldCancel = false;
  boolean shouldInitialize = true;

  PixelBoat parent;

  public SceneSaveScreen(PixelBoat parent) {
    this.parent = parent;
  }

  private void initialize() {

    stage = new Stage(new ScreenViewport());

    // Get the saves
    prefs = Gdx.app.getPreferences("Saves");

    Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

    saveNameInput = new TextArea("Default Save Name", skin);

    TextButton saveButton = new TextButton("Save Game", skin);
    saveButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        shouldSave = true;
      }

    });

    TextButton cancelButton = new TextButton("Cancel", skin);
    cancelButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        shouldCancel = true;
      }

    });

    layout = new Table();
    layout.setFillParent(true);

    layout.add(saveNameInput).colspan(4);
    layout.row().pad(20, 50, 0, 50);
    layout.add(saveButton).colspan(4);
    layout.row().pad(20, 50, 0, 50);
    layout.add(cancelButton).colspan(4);

    stage.addActor(layout);
    Gdx.input.setInputProcessor(stage);

    bg = new Texture("start_screen.png");
    bgSprite = new Sprite(bg);
    bgSprite.setPosition(0, 0);
    bgSprite.setSize(1280, 720);

  }

  @Override
  public void draw(SpriteBatch batch) {
    Gdx.gl.glClearColor(1f, 1f, 1f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    bgSprite.draw(batch);
    batch.end();

    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();

  }

  @Override
  public int update() {

    if (shouldCancel) {
      shouldCancel = false;
      shouldInitialize = true;

      // Reload the save
      Preferences tempPrefs = Gdx.app.getPreferences("tempSaves");
      try {
        ((SceneMainGame) parent.allScenes[1]).restoreGame("save", tempPrefs);
      } catch (IOException e) {
        // If it fails then don't do anything
        e.printStackTrace();
      }

      // Return ID of SceneMainGame and stop getting inputs
      Gdx.input.setInputProcessor(null);
      return 1;
    } else if (shouldSave) {
      shouldSave = false;

      // Check if there is actually a save name
      if (saveNameInput.getText().equals("")) {
        return 8;
      }

      // Save the game to the specified name

      Preferences tempPrefs = Gdx.app.getPreferences("tempSaves");
      prefs.putString(saveNameInput.getText(), tempPrefs.getString("save"));
      prefs.flush();

      shouldInitialize = true;

      // Return ID of start screen and stop receiving inputs
      Gdx.input.setInputProcessor(null);
      return 0;
      
    } else if (shouldInitialize) {
      shouldInitialize = false;

      initialize();

      return 8;

    }



    return 8;
  }

  @Override
  public void resize(int width, int height) {
    Viewport viewport = stage.getViewport();
    viewport.setScreenSize(width, height);
    stage.setViewport(viewport);

  }

}
