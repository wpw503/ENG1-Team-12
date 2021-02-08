package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teamonehundred.pixelboat.PixelBoat;
import java.io.IOException;

/**
 * SceneLoadScreen.
 */
public class SceneLoadScreen implements Scene {

  Stage stage;
  boolean shouldGoToMenu = false;
  boolean shouldInitialize = true;
  boolean shouldLoadGame = false;
  boolean shouldDelete = false;
  PixelBoat parent;
  SelectBox<String> choose;
  Preferences prefs;
  Table layout;
  protected Texture bg;
  protected Sprite bgSprite;

  /**
   * Create a SceneLoadScreen.
   */
  public SceneLoadScreen(PixelBoat parent) {
    this.parent = parent;

  }

  private void initialize() {
    
    
    // Load the preferences containing the saves
    prefs = Gdx.app.getPreferences("Saves");
    
    // Generate all the UI items (save selector, load, delete and return buttons)
    stage = new Stage(new ScreenViewport());

    Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));
    choose = new SelectBox<String>(skin);
    choose.setItems((String[]) prefs.get().keySet().toArray(new String[prefs.get().size()]));
    
    final TextButton returnButton = new TextButton("Back to menu", skin);
    returnButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        shouldGoToMenu = true;
      }
    });

    final TextButton loadButton = new TextButton("Load Selected", skin);
    loadButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        shouldLoadGame = true;
      }
    });

    final TextButton deleteButton = new TextButton("Delete Selected", skin);
    deleteButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        shouldDelete = true;
      }

    });

    // Set up screen with items
    layout = new Table();
    layout.setFillParent(true);

    layout.add(choose).colspan(4);
    layout.row().pad(20, 50, 0, 50);
    layout.add(returnButton).colspan(4);
    layout.row().pad(20, 50, 0, 50);
    layout.add(loadButton).colspan(4);
    layout.row().pad(20, 50, 0, 50);
    layout.add(deleteButton).colspan(4);

    stage.addActor(layout);
    Gdx.input.setInputProcessor(stage);

    // Load background
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

    if (shouldGoToMenu) {

      shouldInitialize = true;
      shouldGoToMenu = false;

      // reset the race in case something failed to load
      // eg. IOException during loading a save
      ((SceneMainGame) parent.allScenes[PixelBoat.GAME_SCENE]).initialize();
      return PixelBoat.MAIN_MENU;

    } else if (shouldInitialize) {

      shouldInitialize = false;
      initialize();
      return PixelBoat.LOAD_SCENE;

    } else if (shouldLoadGame) {
      shouldLoadGame = false;
      shouldInitialize = true;
      
      try {
        String saveName = choose.getSelected();
        if (saveName == null) {
          shouldInitialize = false;
          return PixelBoat.LOAD_SCENE;
        }
        ((SceneMainGame) parent.allScenes[1]).restoreGame(saveName, prefs);
      } catch (IOException e) {
        // If it fails then do not initialize
        shouldInitialize = false;
        return PixelBoat.LOAD_SCENE;
      }

      // Exit to the game, stop listening for inputs
      shouldLoadGame = false;
      Gdx.input.setInputProcessor(null);
      return PixelBoat.GAME_SCENE;

    } else if (shouldDelete) {
      shouldDelete = false;

      // Don't do anything if the selection is empty
      if (choose.getSelected() == null) {
        return PixelBoat.LOAD_SCENE;
      }

      // Delete selected save from the preferenes
      prefs.remove(choose.getSelected());
      prefs.flush();

      initialize();

      return PixelBoat.LOAD_SCENE;


    }

    return PixelBoat.LOAD_SCENE;

  }

  @Override
  public void resize(int width, int height) {
     
    Viewport viewport = stage.getViewport();
    viewport.setScreenSize(width, height);
    stage.setViewport(viewport);
  }



}
