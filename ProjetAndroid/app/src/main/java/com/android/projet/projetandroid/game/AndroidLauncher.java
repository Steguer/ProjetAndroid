package com.android.projet.projetandroid.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.android.projet.projetandroid.game.superjumper.SuperJumper;

public class AndroidLauncher extends AndroidApplication {
    ActionResolverAndroid actionResolver;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        actionResolver = new ActionResolverAndroid(this);
		initialize(new SuperJumper(actionResolver), config);
	}
}
