package com.android.projet.projetandroid.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Steven GERARD on 28/04/2015.
 */
public class ActionResolverAndroid implements ActionResolver {
    private Context context;

    public ActionResolverAndroid(Context context) {
        this.context = context;
    }

    @Override
    public void launchActivity(Class activityClass) {
        launchAndroidActivity(activityClass);
    }

    private void launchAndroidActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(context, activityClass);
        context.startActivity(newActivity);
    }
}
