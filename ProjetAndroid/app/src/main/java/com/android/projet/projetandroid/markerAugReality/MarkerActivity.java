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

            someObject = new CustomObject1("test", "1.patt", 80.0, new double[]{0,0});
            artoolkit.registerARObject(someObject);

            someObject = new CustomObject2("test", "2.patt", 80.0, new double[]{0,0});
            artoolkit.registerARObject(someObject);

            someObject = new CustomObject3("test", "3.patt", 80.0, new double[]{0,0}, this);
            artoolkit.registerARObject(someObject);

            someObject = new CustomObject4("test", "4.patt", 80.0, new double[]{0,0});
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
    public void detected(ICustomObject customObject, final Point xy){
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