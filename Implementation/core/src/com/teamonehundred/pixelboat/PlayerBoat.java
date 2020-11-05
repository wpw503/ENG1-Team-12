package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

class PlayerBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    OrthographicCamera camera;

    Texture stamina_texture;
    Texture durability_texture;

    Sprite stamina_bar;
    Sprite durability_bar;

    int ui_bar_width = 500;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    PlayerBoat(int x, int y){
        super(x, y);

        initialise();
    }

    PlayerBoat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);

        initialise();
    }

    PlayerBoat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, name, durability_per_hit, stamina_usage, stamina_regen);

        initialise();
    }

    // destructor
    protected void finalize() {
        stamina_texture.dispose();
        durability_texture.dispose();
    }

    /* ################################### //
                    METHODS
    // ################################### */

    // shared initialisation functionality among all constructors
    public void initialise() {
        stamina_texture = new Texture("stamina_texture.png");
        durability_texture = new Texture("durability_texture.png");

        stamina_bar = new Sprite(stamina_texture);
        durability_bar = new Sprite(durability_texture);

        stamina_bar.setSize(ui_bar_width, 10);
        durability_bar.setSize(ui_bar_width, 10);


        stamina_bar.setPosition(-ui_bar_width / 2, 5);
        durability_bar.setPosition(-ui_bar_width / 2, 20);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, Gdx.graphics.getHeight() / 3, 0);
        camera.update();
    }

    @Override
    public void updatePosition() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.accelerate();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            // nothing atm
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.turn(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.turn(-1);
        }

        float old_x = sprite.getX();
        float old_y = sprite.getY();

        super.updatePosition();

        //only follow player in x axis if they go off screen
        float dx = Math.abs(sprite.getX()) > Gdx.graphics.getWidth() / 3 ? sprite.getX() - old_x : 0;
        float dy = sprite.getY() - old_y;

        // move camera to follow player
        camera.translate(dx, dy, 0);
    }


    public List<Sprite> getUISprites() {
        updateUISprites();  // todo probably move this to only when they change rather than every frame

        List<Sprite> ret = new ArrayList<Sprite>();
        ret.add(stamina_bar);
        ret.add(durability_bar);
        return ret;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    private void updateUISprites() {
        stamina_bar.setPosition(-ui_bar_width / 2, -50 + sprite.getY());
        durability_bar.setPosition(-ui_bar_width / 2, -35 + sprite.getY());

        stamina_bar.setSize((int) (ui_bar_width * stamina), 10);
        durability_bar.setSize((int) (ui_bar_width * durability), 10);
    }

}
