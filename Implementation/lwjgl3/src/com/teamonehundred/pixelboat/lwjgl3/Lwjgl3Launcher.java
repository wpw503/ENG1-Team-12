package com.teamonehundred.pixelboat.lwjgl3	;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.teamonehundred.pixelboat.PixelBoat;

public class Lwjgl3Launcher {
    public static void main(String[] arg) {
		//custom window settings
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //config.title = "PixelBoat";
		config.setWindowedMode(1280, 720);
		//config.foregroundFPS = 30;
		//config.fullscreen = false;
		//config.resizable = false;
		//start the app
        new Lwjgl3Application(new PixelBoat(), config);
    }
}
