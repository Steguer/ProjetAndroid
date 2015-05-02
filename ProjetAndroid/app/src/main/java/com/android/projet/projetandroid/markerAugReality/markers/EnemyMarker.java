package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class EnemyMarker extends AbstractMarker {
    public EnemyMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.ENEMY);
    }

    public EnemyMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.ENEMY);
    }

    public EnemyMarker(float[] customColor, MarkerActivity markerActivity) {
        this("EnemyMarker", "ghost.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public EnemyMarker(MarkerActivity markerActivity) {
        this("EnemyMarker", "ghost.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
