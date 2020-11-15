package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

class SceneResultsScreen implements Scene {
    protected int scene_id = 4;

    protected List<Boat> boats;

    SceneResultsScreen() {
        boats = null;
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
    }

    public void resize(int width, int height) {
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}
