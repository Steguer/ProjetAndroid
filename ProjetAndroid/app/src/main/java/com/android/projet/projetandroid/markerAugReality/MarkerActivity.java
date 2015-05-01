package com.android.projet.projetandroid.markerAugReality;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.projet.projetandroid.R;
import com.android.projet.projetandroid.markerAugReality.markers.AbstractMarker;
import com.android.projet.projetandroid.markerAugReality.markers.CoinMarker;
import com.android.projet.projetandroid.markerAugReality.markers.EndMarker;
import com.android.projet.projetandroid.markerAugReality.markers.EnemyMarker;
import com.android.projet.projetandroid.markerAugReality.markers.MovingMarker;
import com.android.projet.projetandroid.markerAugReality.markers.SpringMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StandardMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StartMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StyleMarker;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;

public class MarkerActivity extends AndARActivity {

    private ARObject someObject;
    private ARToolkit artoolkit;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CustomRenderer renderer = new CustomRenderer();
        setNonARRenderer(renderer);
        try {

            artoolkit = getArtoolkit();

            someObject = new CoinMarker(new float[]{0.97f, 0.84f, 0.12f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new EndMarker(new float[]{1.0f, 0.0f, 0.0f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new EnemyMarker(new float[]{1.0f, 0.27f, 0.27f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new MovingMarker(new float[]{0.125f, 0.44f, 0.97f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new SpringMarker(new float[]{0.36f, 0.36f, 0.36f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new StandardMarker(new float[]{0.67f, 0.25f, 0.04f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new StartMarker(new float[]{0.0f, 0.85f, 0.219f, 1.0f}, this);
            artoolkit.registerARObject(someObject);
            someObject = new StyleMarker(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, this);
            artoolkit.registerARObject(someObject);

        } catch (AndARException ex){
            System.out.println("");
        }
        startPreview();
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndAR EXCEPTION", ex.getMessage());
        finish();
    }

    private View textView;
    public void detected(AbstractMarker customObject, final Point xy){
        //System.out.println(xy);
        final FrameLayout frame = (FrameLayout)this.findViewById(android.R.id.content);
if(textView == null){
    LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    textView = vi.inflate(R.layout.marker_info, null);
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            frame.addView(textView);
            Typeface type = Typeface.createFromAsset(getAssets(),"RetrovilleNC.ttf");
            TextView tv = (TextView) findViewById(R.id.textViewMarker);
            tv.setTypeface(type);
            tv.setTextSize(20);
        }
    });

}

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.textViewMarker);
                tv.setText(xy.toString());
                textView.setX(xy.x);
                textView.setX(xy.y);

            }
        });
    }
}