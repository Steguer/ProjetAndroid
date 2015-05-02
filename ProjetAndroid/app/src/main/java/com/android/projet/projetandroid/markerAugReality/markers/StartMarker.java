package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class StartMarker extends AbstractMarker {
    public StartMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.START);
    }

    public StartMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.START);
    }

    public StartMarker(float[] customColor, MarkerActivity markerActivity) {
        this("StartMarker", "arrow_down.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public StartMarker(MarkerActivity markerActivity) {
        this("StartMarker", "arrow_down.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
