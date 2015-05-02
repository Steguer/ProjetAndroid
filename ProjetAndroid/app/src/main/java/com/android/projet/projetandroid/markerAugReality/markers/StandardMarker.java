package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class StandardMarker extends AbstractMarker {
    public StandardMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.STANDARD);
    }

    public StandardMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.STANDARD);
    }

    public StandardMarker(float[] customColor, MarkerActivity markerActivity) {
        this("StandardMarker", "platform.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public StandardMarker(MarkerActivity markerActivity) {
        this("StandardMarker", "platform.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
