package com.android.projet.projetandroid.game;

import android.graphics.Bitmap;

import com.android.projet.projetandroid.markerAugReality.markers.Marker;

import java.util.List;

/**
 * Created by Adrien on 23/04/2015.
 */
public class GameController {
    private static GameController instance;

    private  List<Marker> markersList;
    private  Bitmap background;

    private GameController() {
    }

    public static GameController getIsntance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Create a game with the detected markers
     * @param markers
     * @param backgroundImage
     * @return true if the game has correctly been created
     */
    public boolean createGame(List<Marker> markers, Bitmap backgroundImage) {
        this.markersList = markers;
        this.background = backgroundImage;

        return true;
    }

    public List<Marker> getMarkersList() {
        return markersList;
    }

    public Bitmap getBackground() {
        return background;
    }
}
