package de.brainstormsoftworks.taloonerrl.html;

import de.brainstormsoftworks.taloonerrl.core.TaloonerRl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class TaloonerRlHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new TaloonerRl();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
