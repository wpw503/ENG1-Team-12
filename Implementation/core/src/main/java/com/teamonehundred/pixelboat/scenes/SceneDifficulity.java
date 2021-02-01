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

public class SceneDifficulity implements Scene {
    protected int scene_id = 6;

    protected boolean is_new_click = false;

    protected int num_buttons = 4;

    protected Texture bg;
    protected Sprite bg_sprite;

    protected Texture[] diff_options;
    protected Sprite[] diff_option_sprites;

    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

    public int diffFactor; 

    public SceneDifficulity() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
        fill_viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bg = new Texture("difficulty_options_screen.png");
        bg_sprite = new Sprite(bg);
        bg_sprite.setPosition(0, 0);
        bg_sprite.setSize(1280, 720);

        diff_options = new Texture[num_buttons];
        diff_option_sprites = new Sprite[num_buttons];

        diff_options[0] = new Texture("easyButton.png");
        diff_options[1] = new Texture("mediumButton.png");
        diff_options[2] = new Texture("hardButton.png");
        diff_options[3] = new Texture("readyButton.png");

        for (int i = 0; i < num_specs; i++) {
            diff_option_sprites[i] = new Sprite(diff_options[i]);
            diff_option_sprites[i].setSize(512 / 2, 256 / 2);
            diff_option_sprites[i].setPosition(
                    (fill_camera.viewportWidth / 2) - (diff_option_sprites[i].getWidth() / 2),
                    (fill_camera.viewportHeight / 2) + (diff_option_sprites[i].getHeight() / 2) - i * (diff_option_sprites[i].getHeight()));
        }
    }
    public int update() {
        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            is_new_click = true;

        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        for (int i = 0; i < num_buttons; i++)
            if (boat_option_sprites[i].getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && is_new_click) {
                    if (i == 0){
                        System.out.println("easy");
                        diffFactor = 1;
                        //implement easy
                        //spawn same amount of obs
                        //smae collision damage
                    }
                    if (i ==1){
                        System.out.println("easy");
                        diffFactor =1.5;
                        //med
                        //spawn 1.5 the amount of obs
                        //collosion increase 1.5 amount
                    }
                    if (i ==2){
                        System.out.println("hard");
                        diffFactor =2;
                        //hard
                        //spawn 2 the amount of obs
                        //collision increase 2 amount
                    }
                    if (i ==3){
                        //exit to the tutorial 
                        return 3;  
                    }
                }
            }
            return diff_id;
        }
    }
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
        bg_sprite.draw(batch);
        for (int i = 0; i < 0; i++) {
            diff_option_sprites[i].draw(batch);
        }
        batch.end();
    }
    public int getDiffFactor() {
        return diffFactor;
    }

}
