package com.android.projet.projetandroid.markerAugReality;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.projet.projetandroid.R;
import com.android.projet.projetandroid.markerAugReality.markers.AbstractMarker;
import com.android.projet.projetandroid.markerAugReality.markers.CoinMarker;
import com.android.projet.projetandroid.markerAugReality.markers.EndMarker;
import com.android.projet.projetandroid.markerAugReality.markers.EnemyMarker;
import com.android.projet.projetandroid.markerAugReality.markers.MarkerType;
import com.android.projet.projetandroid.markerAugReality.markers.MovingMarker;
import com.android.projet.projetandroid.markerAugReality.markers.SpringMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StandardMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StartMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StyleMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;

public class MarkerActivity extends AndARActivity {

    private ARObject someObject;
    private ARToolkit artoolkit;
    private List<AbstractMarker> detectedMarkers;
    private HashMap markers;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CustomRenderer renderer = new CustomRenderer();
        setNonARRenderer(renderer);
        detectedMarkers = new ArrayList<>();
        markers = new HashMap();
        try {

            artoolkit = getArtoolkit();

            someObject = new CoinMarker(new float[]{0.97f, 0.84f, 0.12f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.COIN, someObject);
            someObject = new EndMarker(new float[]{1.0f, 0.0f, 0.0f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.END, someObject);
            someObject = new EnemyMarker(new float[]{1.0f, 0.27f, 0.27f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.ENEMY, someObject);
            someObject = new MovingMarker(new float[]{0.125f, 0.44f, 0.97f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.MOVING, someObject);
            someObject = new SpringMarker(new float[]{0.36f, 0.36f, 0.36f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.TRAMPOLINE, someObject);
            someObject = new StandardMarker(new float[]{0.67f, 0.25f, 0.04f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.STANDARD, someObject);
            someObject = new StartMarker(new float[]{0.0f, 0.85f, 0.219f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.START, someObject);
            someObject = new StyleMarker(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            markers.put(MarkerType.STYLE, someObject);

        } catch (AndARException ex){
            System.out.println("");
        }
        createInfoLayer();
        startPreview();
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndAR EXCEPTION", ex.getMessage());
        finish();
    }

    private View infoLayer;
    private FrameLayout frameLayout;

    private void createInfoLayer(){
        canBeLaunched = false;
        frameLayout = (FrameLayout)this.findViewById(android.R.id.content);
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        infoLayer = vi.inflate(R.layout.marker_info, null);
        requiredTypes = new ArrayList<>();
        requiredTypes.add(MarkerType.END);
        requiredTypes.add(MarkerType.START);
        requiredTypes.add(MarkerType.STANDARD);
        images = new ArrayList<>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLayout.addView(infoLayer);
                Typeface type = Typeface.createFromAsset(getAssets(), "RetrovilleNC.ttf");
                TextView tv = (TextView) findViewById(R.id.textViewWaitingMarker);
                tv.setTypeface(type);
                tv.setTextSize(18);
                TextView tv2 = (TextView) findViewById(R.id.textViewRequired);
                tv2.setTypeface(type);
                tv2.setTextSize(16);
                images.add((ImageView) findViewById(R.id.imageView));
                images.add((ImageView) findViewById(R.id.imageView2));
                images.add((ImageView) findViewById(R.id.imageView3));
            }
        });
    }

    private boolean canBeLaunched;
    private List<MarkerType> requiredTypes;
    private List<ImageView> images;

    private boolean checkLaunched(){
        List<AbstractMarker> whatWeWant = new ArrayList<>();
        ImageView imageView = null;
        int i = 0;
        for (MarkerType type : requiredTypes){
            whatWeWant.add((AbstractMarker)markers.get(type));
            imageView = images.get(i);
            if(detectedMarkers.contains((AbstractMarker)markers.get(type))){
                imageView.setAlpha(0.1f);
            }
            i++;
        }
        return detectedMarkers.containsAll(whatWeWant);
    }

    public void detected(AbstractMarker marker, final Point xy){
        detectedMarkers.add(marker);
        canBeLaunched = checkLaunched();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.textViewWaitingMarker);
                tv.setText((canBeLaunched ? "Ready to launch :" : "Deetected markers :") + " " + detectedMarkers.size());

            }
        });
    }
}