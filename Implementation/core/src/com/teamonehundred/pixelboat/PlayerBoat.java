package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    Texture stamina_texture;
    Texture durability_texture;

    Sprite stamina_bar;
    Sprite durability_bar;

    int ui_bar_width = 500;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    PlayerBoat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);

        initialise();
    }

    PlayerBoat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, name, durability_per_hit, stamina_usage, stamina_regen);

        initialise();
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
    }

    public List<Sprite> getUISprites() {
        updateUISprites();  // todo probably move this to only when they change rather than every frame

        List<Sprite> ret = new ArrayList<Sprite>();
        ret.add(stamina_bar);
        ret.add(durability_bar);
        return ret;
    }

    private void updateUISprites() {
        stamina_bar.setPosition(-ui_bar_width / 2, -50 + sprite.getY());
        durability_bar.setPosition(-ui_bar_width / 2, -35 + sprite.getY());

        stamina_bar.setSize((int) (ui_bar_width * stamina), 10);
        durability_bar.setSize((int) (ui_bar_width * durability), 10);
    }
}
