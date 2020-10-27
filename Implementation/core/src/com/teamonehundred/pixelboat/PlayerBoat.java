package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoat extends Boat{
    Texture stamina_texture;
    Texture durability_texture;

    Sprite stamina_bar;
    Sprite durability_bar;

    int ui_bar_width = 500;

    PlayerBoat(int x, int y, int w, int h, String texture_path){
        super(x, y,  w,  h, texture_path);

        stamina_texture = new Texture("stamina_texture.png");
        durability_texture = new Texture("durability_texture.png");

        stamina_bar = new Sprite(stamina_texture);
        durability_bar = new Sprite(durability_texture);

        stamina_bar.setSize(ui_bar_width, 10);
        durability_bar.setSize(ui_bar_width, 10);

        stamina_bar.setPosition(-ui_bar_width/2, 5);
        durability_bar.setPosition(-ui_bar_width/2, 20);
    }

    public List<Sprite> getUISprites(){
        updateUISprites();

        List<Sprite> ret = new ArrayList<Sprite>();
        ret.add(stamina_bar);
        ret.add(durability_bar);
        return ret;
    }

    private void updateUISprites(){
        stamina_bar.setPosition(-ui_bar_width/2, -50 + sprite.getY());
        durability_bar.setPosition(-ui_bar_width/2, -35 + sprite.getY());

        stamina_bar.setSize((int)(ui_bar_width * stamina), 10);
        durability_bar.setSize((int)(ui_bar_width * durability), 10);
    }
}
