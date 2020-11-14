package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
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
        font.setColor(Color.BLUE);
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

        //return scene_id;
        return 1;
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
//
        PlayerBoat thePlayerBoat = null;
        //         your draw code
        for (Boat b : boats){
            if (b instanceof  PlayerBoat){
                thePlayerBoat = (PlayerBoat) b;

            }
        }
//
        String label_text = thePlayerBoat.getName();
        font.draw(batch, label_text, -thePlayerBoat.ui_bar_width / 2, 470 + thePlayerBoat.getSprite().getY());
        batch.end();

//        for (Boat b : boats) {
//            if (b instanceof  PlayerBoat) {
//                List<Long> legTimes = b.getLegTimes();
//                float boatTime = legTimes.get(legTimes.size() - 1);
//                String label_text = b.getName();//String.format("A boat (%s) ended race with time (ms) %d (%d ms was penalty)", b.getName(), boatTime, b.getTimeToAdd());
//                //String.format(label_text, boatTime / 60000, boatTime / 1000 % 60)
//
//                // font.draw(batch, label_text,-thePlayerBoat.ui_bar_width /2,500 - (boats.indexOf(b)*10) + thePlayerBoat.getSprite().getY());
////            drawTimeDisplay(batch, b, label, i, -((PlayerBoat) b).ui_bar_width / 2,
////                    500 - (legtimes.size() * 20) + ((PlayerBoat) b).getSprite().getY());
//                font.draw(batch, label_text, -thePlayerBoat.ui_bar_width / 2, 470 + thePlayerBoat.getSprite().getY());
//            }
//        }
    }

    public void resize(int width, int height) {
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}
