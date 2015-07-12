package de.brainstormsoftworks.taloonerrl.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.brainstormsoftworks.taloonerrl.core.TaloonerRl;

public class TaloonerRlDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TaloonerRl(), config);
	}
}
