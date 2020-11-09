package com.teamonehundred.pixelboat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.teamonehundred.pixelboat.PixelBoat;

public class DesktopLauncher {
    public static void main(String[] arg) {
		//custom window settings
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "PixelBoat";
		config.width = 1280;
		config.height = 720;
		//config.foregroundFPS = 30;
		//config.fullscreen = false;
		//config.resizable = false;
		//start the app
        new LwjglApplication(new PixelBoat(), config);
    }
}
