package com.greyjan.twelfthnight2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greyjan.twelfthnight2.TwelfthNightGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;
		config.height = 800;
		config.fullscreen = true;
		new LwjglApplication(new TwelfthNightGame(), config);
	}
}
