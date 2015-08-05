package de.brainstormsoftworks.taloonerrl.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.brainstormsoftworks.taloonerrl.core.TaloonerRl;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * wrapper class to start a new desktop game
 *
 * @author David Becker
 *
 */
public class TaloonerRlDesktop {

	/**
	 * starts a new game
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Renderer.VIRTUAL_HEIGHT;
		config.width = Renderer.VIRTUAL_WIDTH;
		new LwjglApplication(new TaloonerRl(), config);
	}
}
