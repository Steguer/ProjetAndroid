package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class SpringMarker extends AbstractMarker {
    public SpringMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.TRAMPOLINE);
    }

    public SpringMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.TRAMPOLINE);
    }

    public SpringMarker(float[] customColor, MarkerActivity markerActivity) {
        this("SpringMarker", "spring.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public SpringMarker(MarkerActivity markerActivity) {
        this("SpringMarker", "spring.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
