package de.brainstormsoftworks.taloonerrl.android;

import de.brainstormsoftworks.taloonerrl.core.TaloonerRl;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class TaloonerRlActivity extends AndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
			initialize(new TaloonerRl(), config);
	}
}
