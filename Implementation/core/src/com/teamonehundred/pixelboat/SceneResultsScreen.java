package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

class SceneResultsScreen implements Scene {
    protected int scene_id = 4;

    protected List<Boat> boats;
    protected BitmapFont font; // For text display - UF
//    protected OrthographicCamera fill_camera;

    SceneResultsScreen() {
        boats = null;

        // Initialise colour of Text Display Overlay - UF
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    // return 1 when you want to exit the results screen
    // else return scene_id if you want to stay
    public int update() {
        // placeholder text output
        for (Boat b : boats) {
            System.out.print("a boat (");
            System.out.print(b.getName());
            System.out.print(") ended race with time (ms) ");
            System.out.print(b.getLegTimes().get(b.getLegTimes().size() - 1));
            System.out.print(" (");
            System.out.print(b.getTimeToAdd());
            System.out.println(" ms was penalty)");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            return 1;
        }
        return scene_id;
//        return 1;
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.setProjectionMatrix(fill_camera.combined);

//
        PlayerBoat thePlayerBoat = null;
        //         your draw code
        for (Boat b : boats){
            if (b instanceof  PlayerBoat){
                thePlayerBoat = (PlayerBoat) b;

            }
        }

        batch.begin();

        font.setColor(Color.ORANGE);
        font.draw(batch, "Results Screen! Click on the screen to skip and start the next leg!",
                -thePlayerBoat.ui_bar_width / 2, 540 + thePlayerBoat.getSprite().getY());

        font.setColor(Color.YELLOW);
        font.draw(batch, "BoatName | Race Time in ms | Race penalty in ms",
                -thePlayerBoat.ui_bar_width / 2, 520 + thePlayerBoat.getSprite().getY());

        String label_template = "%s | %d ms | %d ms";//"A boat (%s) ended race with time (ms) %d (%d ms was penalty)";
        int column_num = -1;
        int column_idx = -1;
        for (Boat b : boats) {
            if (b instanceof PlayerBoat){
                font.setColor(Color.RED);
            }
            else{
                font.setColor(Color.WHITE);
            }

            if (boats.indexOf(b) % 20 == 0) {
                column_num++;
                column_idx = 0;
            }
            column_idx++;

            String label_text = String.format(label_template, b.getName(),
                    b.getLegTimes().get(b.getLegTimes().size() - 1), b.getTimeToAdd());
            font.draw(batch, label_text, -thePlayerBoat.ui_bar_width / 2 + column_num * 210,
                    500 - (column_idx * 20) + thePlayerBoat.getSprite().getY());
        }

        batch.end();

    }

    public void resize(int width, int height) {
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}
