package com.android.projet.projetandroid.markerAugReality;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.projet.projetandroid.R;
import com.android.projet.projetandroid.game.GameController;
import com.android.projet.projetandroid.markerAugReality.markers.AbstractMarker;
import com.android.projet.projetandroid.markerAugReality.markers.CoinMarker;
import com.android.projet.projetandroid.markerAugReality.markers.EndMarker;
import com.android.projet.projetandroid.markerAugReality.markers.EnemyMarker;
import com.android.projet.projetandroid.markerAugReality.markers.Marker;
import com.android.projet.projetandroid.markerAugReality.markers.MarkerType;
import com.android.projet.projetandroid.markerAugReality.markers.MovingMarker;
import com.android.projet.projetandroid.markerAugReality.markers.SoundPoolPlayer;
import com.android.projet.projetandroid.markerAugReality.markers.SpringMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StandardMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StartMarker;
import com.android.projet.projetandroid.markerAugReality.markers.StyleMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;

public class MarkerActivity extends AndARActivity {

    private ARObject someObject;
    private ARToolkit artoolkit;
    private List<AbstractMarker> detectedMarkers;
    private HashMap markers;
    private SoundPoolPlayer sound;
    private long lastSoundPlayed;
    private AnimationSet animSet;

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

        } catch (AndARException ex) {
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

    private void createInfoLayer() {
        canBeLaunched = false;
        frameLayout = (FrameLayout) this.findViewById(android.R.id.content);
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
                TextView tv3 = (TextView) findViewById(R.id.textViewTakingPicture);
                tv3.setTypeface(type);
                tv3.setTextSize(16);
                tv3.setVisibility(View.GONE);
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                pb.setVisibility(View.GONE);
                images.add((ImageView) findViewById(R.id.imageView));
                images.add((ImageView) findViewById(R.id.imageView2));
                images.add((ImageView) findViewById(R.id.imageView3));
                Button button = (Button) findViewById(R.id.launchButton);
                button.setVisibility(View.INVISIBLE);
                button.setOnClickListener(launchButtonHandler);
                button.setTypeface(type);
                button.setTextSize(20);
                Animation scale = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scale.setDuration(1000);
                animSet = new AnimationSet(true);
                animSet.setFillEnabled(true);
                animSet.addAnimation(scale);
                animSet.setRepeatMode(Animation.REVERSE);
                animSet.setRepeatCount(-1);

            }
        });
        sound = new SoundPoolPlayer(this);
        lastSoundPlayed = System.currentTimeMillis();
        takingPicture = false;
        new Timer().scheduleAtFixedRate(new CheckUndetectionTask(), 0, 800);
    }

    private boolean canBeLaunched;
    private List<MarkerType> requiredTypes;
    private List<ImageView> images;

    private boolean checkLaunched() {
        List<AbstractMarker> whatWeWant = new ArrayList<>();
        ImageView imageView = null;
        int i = 0;
        for (MarkerType type : requiredTypes) {
            whatWeWant.add((AbstractMarker) markers.get(type));
            imageView = images.get(i);
            if (detectedMarkers.contains((AbstractMarker) markers.get(type))) {
                imageView.setAlpha(0.1f);
            } else {
                imageView.setAlpha(1.0f);
            }
            i++;
        }
        return detectedMarkers.containsAll(whatWeWant);
    }

    public void detected(AbstractMarker marker, boolean detected) {
        if (takingPicture) {
            return;
        }
        if (detected) {
            detectedMarkers.add(marker);
        } else {
            detectedMarkers.remove(marker);
        }
        canBeLaunched = checkLaunched();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.textViewWaitingMarker);
                ImageView iv = (ImageView) findViewById(R.id.imageViewLogo);
                Button button = (Button) findViewById(R.id.launchButton);

                if (detectedMarkers.size() > 0) {
                    tv.setText((canBeLaunched ? "Ready to launch with " + detectedMarkers.size() + " markers" : "Detected markers : " + detectedMarkers.size()));
                    iv.setAlpha(canBeLaunched ? 1.0f : 0.5f);
                    if (canBeLaunched) {
                        button.setVisibility(View.VISIBLE);

                        long t = System.currentTimeMillis();
                        if (t - lastSoundPlayed >= 2000) {
                            sound.playShortResource(R.raw.coin);
                            lastSoundPlayed = t;
                            button.startAnimation(animSet);
                        }
                    } else {
                        button.setVisibility(View.INVISIBLE);
                    }
                } else {
                    tv.setText("Waiting for markers...");
                    iv.setAlpha(0.1f);
                    button.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    public class CheckUndetectionTask extends TimerTask {
        @Override
        public void run() {
            long t = System.currentTimeMillis();
            List<AbstractMarker> toRemove = new ArrayList<>();
            for (AbstractMarker marker : detectedMarkers) {
                if (marker.isHasDetected() && t - marker.getLastDrew() >= AbstractMarker.TIME_UNDETECTED) {
                    toRemove.add(marker);
                }
            }
            for (AbstractMarker marker : toRemove) {
                marker.setHasDetected(false);
                detected(marker, false);
            }
        }
    }

    private List<Marker> temporaryMarkers;

    View.OnClickListener launchButtonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if (checkLaunched() && !takingPicture) {
                temporaryMarkers = new ArrayList<>();
                for (AbstractMarker marker : detectedMarkers) {
                    temporaryMarkers.add(new Marker(marker.getPosition(), marker.getType()));
                }
                for (AbstractMarker marker : detectedMarkers) {
                    marker.setStopDraw(true);
                }
                takingPicture = true;
                new TakeAsyncScreenshot().execute();
            }
        }
    };

    private boolean takingPicture;

    @Override
    public void onBackPressed() {
        if(!checkLaunched()){
            return;
        }
        Iterator entries = markers.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            ((AbstractMarker) thisEntry.getValue()).setStopDraw(false);
        }
        sound.playShortResource(R.raw.highjump);
        GameController.getIsntance().createGame(temporaryMarkers, bm);
        Animation scale = new ScaleAnimation(1.0f, 10.0f, 1.0f, 10.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        final AnimationSet animSet = new AnimationSet(true);
        animSet.setFillEnabled(true);
        animSet.addAnimation(scale);
        animSet.setRepeatCount(1);

        animSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                customOnBackKeyPressed();
            }
        });

        Button button = (Button) findViewById(R.id.launchButton);
        button.startAnimation(animSet);

    }

    private Bitmap bm;

    class TakeAsyncScreenshot extends AsyncTask<Void, Void, Void> {

        private String errorMsg = null;

        @Override
        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv = (TextView) findViewById(R.id.textViewTakingPicture);
                    tv.setVisibility(View.VISIBLE);
                    ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                    pb.setVisibility(View.VISIBLE);
                }
            });

            bm = takeScreenshot();

            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  TextView tv = (TextView) findViewById(R.id.textViewTakingPicture);
                                  tv.setVisibility(View.GONE);
                                  ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                                  pb.setVisibility(View.GONE);
                                  onBackPressed();
                              }
                          }

            );
            return null;
        }

        protected void onPostExecute(Void result) {
        }

        ;

    }

    private void customOnBackKeyPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sound.release();
    }
}