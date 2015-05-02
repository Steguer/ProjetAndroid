package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class MovingMarker extends AbstractMarker {
    public MovingMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.MOVING);
    }

    public MovingMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.MOVING);
    }

    public MovingMarker(float[] customColor, MarkerActivity markerActivity) {
        this("MovingMarker", "moving.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public MovingMarker(MarkerActivity markerActivity) {
        this("MovingMarker", "moving.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
