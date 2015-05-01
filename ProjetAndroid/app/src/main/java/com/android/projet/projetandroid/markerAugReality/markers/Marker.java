package com.android.projet.projetandroid.markerAugReality.markers;

import android.graphics.Point;

/**
 * Created by Adrien on 23/04/2015.
 */
public class Marker {
    private Point position;
    private float width;
    private MarkerType type;

    private static final float DEFAULT_WIDTH = 80f;

    public Marker(Point position, MarkerType type) {
        this(position, DEFAULT_WIDTH, type);
    }

    public Marker(Point position, float width, MarkerType type) {
        this.position = position;
        this.width = width;
        this.type = type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public MarkerType getType() {
        return type;
    }

    public void setType(MarkerType type) {
        this.type = type;
    }
}
