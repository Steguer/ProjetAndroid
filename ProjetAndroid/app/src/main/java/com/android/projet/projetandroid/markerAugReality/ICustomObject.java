package com.android.projet.projetandroid.markerAugReality;

import android.graphics.Point;

import edu.dhbw.andar.ARObject;

/**
 * Created by Adrien on 29/04/2015.
 */
public abstract class ICustomObject extends ARObject {
    private MarkerActivity markerActivity;
    private boolean hasDetected;

    public ICustomObject(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter);
        this.markerActivity = markerActivity;
        this.hasDetected = false;
    }

    protected void detected(Point xy){
        //if(!this.hasDetected) {
            markerActivity.detected(this, xy);
            this.hasDetected = true;
        //}
    }
}
