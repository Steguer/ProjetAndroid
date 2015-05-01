package com.android.projet.projetandroid.markerAugReality.markers;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;

/**
 * Created by Adrien on 01/05/2015.
 */
public class CoinMarker extends AbstractMarker {
    public CoinMarker(String name, String patternName, double markerWidth, double[] markerCenter, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, markerActivity, MarkerType.COIN);
    }

    public CoinMarker(String name, String patternName, double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity) {
        super(name, patternName, markerWidth, markerCenter, customColor, markerActivity, MarkerType.COIN);
    }

    public CoinMarker(float[] customColor, MarkerActivity markerActivity) {
        this("CoinMarker", "coin.patt", 80.0, new double[]{0, 0}, customColor, markerActivity);
    }

    public CoinMarker(MarkerActivity markerActivity) {
        this("CoinMarker", "coin.patt", 80.0, new double[]{0, 0}, markerActivity);
    }
}
