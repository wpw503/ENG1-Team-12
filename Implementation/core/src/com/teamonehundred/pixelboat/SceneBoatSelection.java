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

class SceneBoatSelection implements Scene {
    protected int scene_id = 5;

    protected boolean is_new_click = false;

    protected int spec_id = 0;
    protected int num_specs = 3;

    protected Texture bg;
    protected Sprite bg_sprite;

    protected Texture[] boat_options;
    protected Sprite[] boat_option_sprites;

    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

    public SceneBoatSelection() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
        fill_viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bg = new Texture("boat_selection_screen.png");
        bg_sprite = new Sprite(bg);
        bg_sprite.setPosition(0, 0);
        bg_sprite.setSize(1280, 720);

        boat_options = new Texture[num_specs];
        boat_option_sprites = new Sprite[num_specs];

        boat_options[0] = new Texture("boat_selection_debug.png");
        boat_options[1] = new Texture("boat_selection_default.png");
        boat_options[2] = new Texture("boat_selection_fastlowdurability.png");

        for (int i = 0; i < num_specs; i++) {
            boat_option_sprites[i] = new Sprite(boat_options[i]);
            boat_option_sprites[i].setSize(512 / 2, 256 / 2);
            boat_option_sprites[i].setPosition(
                    (fill_camera.viewportWidth / 2) - (boat_option_sprites[i].getWidth() / 2),
                    (fill_camera.viewportHeight / 2) + (boat_option_sprites[i].getHeight() / 2) - i * (boat_option_sprites[i].getHeight()));
        }
    }

    // return 3 to exit
    public int update() {
        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            is_new_click = true;

        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        for(int i = 0; i < num_specs; i++)
        if (boat_option_sprites[i].getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && is_new_click) {
                spec_id = i;
                return 3;
            }
        }

        return scene_id;
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
        bg_sprite.draw(batch);
        for (int i = 0; i < 3; i++) {
            boat_option_sprites[i].draw(batch);
        }
        batch.end();
    }

    public void resize(int width, int height) {
    }

    public int getSpecID() {
        return spec_id;
    }
}
