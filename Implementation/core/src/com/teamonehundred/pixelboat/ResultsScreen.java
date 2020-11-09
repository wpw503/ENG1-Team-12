package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

class ResultsScreen {
    protected List<Boat> boats;

    ResultsScreen(List<Boat> race_boats) {
        boats = new ArrayList<>();
        boats.addAll(race_boats);
    }

    // return false when you want to exit the results screen
    public boolean update() {
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

        return false;
    }

    public void draw(SpriteBatch batch) {
    }
}
