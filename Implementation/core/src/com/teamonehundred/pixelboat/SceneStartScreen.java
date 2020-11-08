package com.teamonehundred.pixelboat;

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

class SceneStartScreen implements Scene {
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

    SceneStartScreen() {
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
        play_sprite.setPosition((Gdx.graphics.getWidth() / 2) - (play_sprite.getWidth() / 2), (Gdx.graphics.getHeight() / 2) + (play_sprite.getHeight() / 2));

        options = new Texture("start_menu_options.png");
        options_hovered = new Texture("start_menu_options_hovered.png");
        options_sprite = new Sprite(options);
        options_sprite.setSize(512 / 2, 128 / 2);
        options_sprite.setPosition((Gdx.graphics.getWidth() / 2) - (options_sprite.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (options_sprite.getHeight() / 2));
    }

    // destructor
    protected void finalize() {
        bg.dispose();
    }

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

    public void update() {
        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (play_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            play_sprite.setTexture(play_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                scene_id = 1;
            }
        } else
            play_sprite.setTexture(play);

        if (options_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            options_sprite.setTexture(options_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                //todo options
            }
        } else
            options_sprite.setTexture(options);
    }

    public int getCurrentSceneID() {
        return scene_id;
    }

    public void resize(int width, int height) {
        fill_viewport.update(width, height);
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
    }
}
