package com.android.projet.projetandroid.markerAugReality.markers;

import android.graphics.Point;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adrien on 23/04/2015.
 */
public class Marker {
    private Vector2 position;
    private float width;
    private MarkerType type;

    private static final float DEFAULT_WIDTH = 80f;

    public Marker(Point position, MarkerType type) {
        this(position, DEFAULT_WIDTH, type);
    }

    public Marker(Point position, float width, MarkerType type) {
        setPosition(position);
        this.width = width;
        this.type = type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = new Vector2(0,0);
        this.position.set(map(position.x, 0, 1920, 0, 10), 15 - map(position.y, 0, 1080, 0, 15));
    }

    public float map(float x, float in_min, float in_max, float out_min, float out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
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
