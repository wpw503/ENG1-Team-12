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

public class SceneOptionsMenu implements Scene {
    protected int scene_id = 2;

    protected boolean is_fullscreen = false;

    protected Texture bg;
    protected Sprite bg_sprite;

    protected Texture full;
    protected Sprite full_sprite;
    protected Texture full_check_yes;
    protected Texture full_check_no;
    protected Sprite full_check_sprite;

    protected Texture accel;
    protected Sprite accel_sprite;

    protected Texture left;
    protected Sprite left_sprite;

    protected Texture right;
    protected Sprite right_sprite;

    protected Texture back;
    protected Texture back_hovered;
    protected Sprite back_sprite;

    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

    public SceneOptionsMenu() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
        fill_viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bg = new Texture("start_screen.png");
        bg_sprite = new Sprite(bg);
        bg_sprite.setPosition(0, 0);
        bg_sprite.setSize(1280, 720);

        full = new Texture("options_menu_fullscreen.png");
        full_check_yes = new Texture("options_menu_checkbox_yes.png");
        full_check_no = new Texture("options_menu_checkbox_no.png");
        full = new Texture("options_menu_fullscreen.png");
        full_sprite = new Sprite(full);
        full_check_sprite = new Sprite(full_check_no);
        full_sprite.setSize(512 / 2, 128 / 2);
        full_sprite.setPosition((fill_camera.viewportWidth / 2) - (full_sprite.getWidth()), (Gdx.graphics.getHeight() / 2) + (full_sprite.getHeight() * 1.5f));
        full_check_sprite.setSize(128 / 2, 128 / 2);
        full_check_sprite.setPosition((fill_camera.viewportWidth / 2) + (full_sprite.getWidth() / 2), (Gdx.graphics.getHeight() / 2) + (full_sprite.getHeight() * 1.5f));

        accel = new Texture("options_menu_fullscreen.png");
        accel_sprite = new Sprite(accel);
        accel_sprite.setSize(512 / 2, 128 / 2);
        accel_sprite.setPosition((fill_camera.viewportWidth / 2) - (full_sprite.getWidth()), (Gdx.graphics.getHeight() / 2) + (full_sprite.getHeight() * .5f));

        left = new Texture("options_menu_fullscreen.png");
        left_sprite = new Sprite(left);
        left_sprite.setSize(512 / 2, 128 / 2);
        left_sprite.setPosition((fill_camera.viewportWidth / 2) - (full_sprite.getWidth()), (Gdx.graphics.getHeight() / 2) - (full_sprite.getHeight() * .5f));

        right = new Texture("options_menu_fullscreen.png");
        right_sprite = new Sprite(right);
        right_sprite.setSize(512 / 2, 128 / 2);
        right_sprite.setPosition((fill_camera.viewportWidth / 2) - (full_sprite.getWidth()), (Gdx.graphics.getHeight() / 2) - (full_sprite.getHeight() * 1.5f));

        back = new Texture("options_menu_back.png");
        back_hovered = new Texture("options_menu_back_hovered.png");
        back_sprite = new Sprite(back);
        back_sprite.setSize(512 / 2, 128 / 2);
        back_sprite.setPosition((fill_camera.viewportWidth / 2) - (full_sprite.getWidth()), 70);
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
        bg_sprite.draw(batch);
        full_sprite.draw(batch);
        accel_sprite.draw(batch);
        left_sprite.draw(batch);
        right_sprite.draw(batch);
        back_sprite.draw(batch);
        full_check_sprite.draw(batch);
        batch.end();
    }

    public int update() {
        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (back_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            back_sprite.setTexture(back_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                return 0;
            }
        } else
            back_sprite.setTexture(back);

        // todo add single click detection to stop this changing every frame
        if (full_check_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            //full_check_sprite.setTexture(full_check_);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (is_fullscreen) {
                    full_check_sprite.setTexture(full_check_no);
                    is_fullscreen = !is_fullscreen;
                } else {
                    full_check_sprite.setTexture(full_check_yes);
                    is_fullscreen = !is_fullscreen;
                }
            }
        }

        return scene_id;
    }

    public void resize(int width, int height) {
        fill_viewport.update(width, height);
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
    }
}
