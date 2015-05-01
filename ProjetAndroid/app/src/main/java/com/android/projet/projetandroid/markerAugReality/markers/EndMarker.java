package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class EndMarker extends AbstractMarker {
    public EndMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.COIN);
    }

    public EndMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.COIN);
    }

    public EndMarker(float[] customColor, MarkerActivity markerActivity) {
        this("EndMarker", "arrow_right.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public EndMarker(MarkerActivity markerActivity) {
        this("EndMarker", "arrow_right.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
