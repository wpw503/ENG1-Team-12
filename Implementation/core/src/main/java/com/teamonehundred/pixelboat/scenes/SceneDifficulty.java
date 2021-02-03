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

public class SceneDifficulty implements Scene {
    protected int scene_id = 6;

    protected boolean is_new_click = false;

    protected int num_buttons = 4;

    protected Texture bg;
    protected Sprite bgSprite;

    protected Texture hard;
    protected Sprite hardSprite;

    protected Texture med;
    protected Sprite medSprite;

    protected Texture easy;
    protected Sprite easySprite;

    protected Texture ready;
    protected Sprite readySprite;


    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

   
    public float diffDecrease = 1f;

    public SceneDifficulty() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
        fill_viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bg = new Texture("difficulty_options_screen.png");
        bgSprite = new Sprite(bg);
        bgSprite.setPosition(0, 0);
        bgSprite.setSize(1280, 720);

        hard = new Texture("hardButton.png");
        hardSprite = new Sprite(hard);
        hardSprite.setSize(512 / 4, 128 / 4);
        hardSprite.setPosition((fill_camera.viewportWidth / 4) - (hardSprite.getWidth() / 4), (fill_camera.viewportHeight / 4) + (hardSprite.getHeight()*4));

        med = new Texture("mediumButton.png");
        medSprite = new Sprite(med);
        medSprite.setSize(512 / 4, 128 / 4);
        medSprite.setPosition((fill_camera.viewportWidth / 4) - (medSprite.getWidth() / 4), (fill_camera.viewportHeight / 4) + (medSprite.getHeight() * 2));

        easy = new Texture("easyButton.png");
        easySprite = new Sprite(easy);
        easySprite.setSize(512 / 4, 128 / 4);
        easySprite.setPosition((fill_camera.viewportWidth / 4) - (easySprite.getWidth() / 4), (fill_camera.viewportHeight / 4) + (easySprite.getHeight()));

        ready = new Texture("readyButton.png");
        readySprite = new Sprite(ready);
        readySprite.setSize(512 / 4, 128 / 4);
        readySprite.setPosition((fill_camera.viewportWidth / 4) - (readySprite.getWidth() / 4), (fill_camera.viewportHeight / 4) + (readySprite.getHeight() / 4));

    }
    public int update() {

        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (hardSprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("hard");
            double diffFactor = 2;
            float diffDecrease = 0.7f;
            }
        
        if (medSprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("med");
            double diffFactor = 1.5;
            float diffDecrease = 0.85f;
            }

        if (easySprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("esay");
            double diffFactor = 1;
            float diffDecrease = 1.0f;
            }   

        if (readySprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            return 3;
            }
        
        return scene_id;
    }
    
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
        bgSprite.draw(batch);
        hardSprite.draw(batch);
        medSprite.draw(batch);
        easySprite.draw(batch);
        readySprite.draw(batch);
        batch.end();
    }
 
    public float getDiffDecrease(){
        return diffDecrease;
    }

    public void resize(int width, int height) {
        fill_viewport.update(width, height);
        fill_camera.position.set(fill_camera.viewportWidth / 4, fill_camera.viewportHeight / 4, 0);
    }
}
