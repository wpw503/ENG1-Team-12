package com.teamonehundred.pixelboat.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents the Main Game Scene for when the boat race starts.
 *
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public class SceneStartScreen implements Scene {
    protected int scene_id = 0;

    protected Texture bg;
    protected Sprite bg_sprite;

    protected Texture play;
    protected Texture play_hovered;
    protected Sprite play_sprite;

    protected Texture options;
    protected Texture options_hovered;
    protected Sprite options_sprite;

    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

    /**
     * Main constructor for a SceneStartGame.
     * <p>
     * Initialises a Scene textures for StartScreen. Including start menu for playing the game and options.
     * When options are hovered over they will change texture indicating what the user is about to select.
     *
     * @author William Walton
     */
    public SceneStartScreen() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);

        bg = new Texture("start_screen.png");
        bg_sprite = new Sprite(bg);
        bg_sprite.setPosition(0, 0);
        bg_sprite.setSize(1280, 720);

        play = new Texture("start_menu_play.png");
        play_hovered = new Texture("start_menu_play_hovered.png");
        play_sprite = new Sprite(play);
        play_sprite.setSize(512 / 2, 128 / 2);
        play_sprite.setPosition((fill_camera.viewportWidth / 2) - (play_sprite.getWidth() / 2), (fill_camera.viewportHeight / 2) + (play_sprite.getHeight() / 2));

        options = new Texture("start_menu_options.png");
        options_hovered = new Texture("start_menu_options_hovered.png");
        options_sprite = new Sprite(options);
        options_sprite.setSize(512 / 2, 128 / 2);
        options_sprite.setPosition((fill_camera.viewportWidth / 2) - (options_sprite.getWidth() / 2), (fill_camera.viewportHeight / 2) - (options_sprite.getHeight() / 2));
    }

    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    // protected void finalize() {
    //     bg.dispose();
    // }

    /**
     * Draw function for SceneStartScreen.
     * <p>
     * Draws StartScreen for the PixelBoat game.
     *
     * @param batch SpriteBatch used for drawing to screen.
     * @author William Walton
     */
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
        bg_sprite.draw(batch);
        play_sprite.draw(batch);
        options_sprite.draw(batch);
        batch.end();
    }

    /**
     * Update function for SceneStartScreen. Ends SceneStartScreen based on user input otherwise stays in scene.
     * <p>
     * Returns an specified integer when you want to exit the screen else return scene_id if you want to stay in scene.
     *
     * @return returns an integer which is the scene_id of which screen is next (either this screen still or another)
     * @author William Walton
     */
    public int update() {
        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (play_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            play_sprite.setTexture(play_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                return 5;
            }
        } else
            play_sprite.setTexture(play);

        if (options_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            options_sprite.setTexture(options_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                // todo enable options when implemented
                //return 2;
            }
        } else
            options_sprite.setTexture(options);

        // Stay in SceneStartScreen
        return scene_id;
    }


    /**
     * Resize method if for camera extension.
     *
     * @param width  Integer width to be resized to
     * @param height Integer height to be resized to
     * @author William Walton
     */
    public void resize(int width, int height) {
        fill_viewport.update(width, height);
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
    }
}
