package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class StyleMarker extends AbstractMarker {
    public StyleMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.STYLE);
    }

    public StyleMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.STYLE);
    }

    public StyleMarker(float[] customColor, MarkerActivity markerActivity) {
        this("StyleMarker", "moving.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public StyleMarker(MarkerActivity markerActivity) {
        this("StyleMarker", "moving.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}