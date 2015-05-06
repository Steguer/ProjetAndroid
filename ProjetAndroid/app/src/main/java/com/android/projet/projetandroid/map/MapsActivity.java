package com.android.projet.projetandroid.map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.projet.projetandroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.android.projet.projetandroid.map.SphericalUtil.computeDistanceBetween;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    NotificationHandler nHandler;

    static final LatLng UQAC = new LatLng(48.420067, -71.052506);
    static final LatLng UQACEST = new LatLng(48.419957, -71.052006);
    static final LatLng UQACOUEST = new LatLng(48.420357, -71.053006);
    static final LatLng CEGEP = new LatLng(48.424268, -71.052007);
    static final LatLng UQACCentreSocial = new LatLng(48.419900,-71.052700);
    static final LatLng UQACSalleCours = new LatLng(48.420067,-71.052506);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap object from the fragment
        mMap = mapFragment.getMap();

        // Setting a custom info window adapter for the google map
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                String[] s = arg0.getSnippet().split("&&");
                TextView tvUser = (TextView) v.findViewById(R.id.textViewUsername);
                tvUser.setText(s[0]);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.textViewRealDate);
                tvLng.setText(s[1]);

                ImageView iv = (ImageView) v.findViewById(R.id.imageViewOk);
                iv.setVisibility(s[2].equals("t") ? View.VISIBLE : View.INVISIBLE);

                TextView tvG = (TextView) v.findViewById(R.id.textViewGameName);
                Typeface type = Typeface.createFromAsset(getAssets(), "RetrovilleNC.ttf");
                tvG.setTextColor(Color.RED);
                tvG.setTypeface(type);
                tvG.setTextSize(20);
                tvG.setText(arg0.getTitle());
                TextView tvH = (TextView) v.findViewById(R.id.textViewHighScores);
                tvH.setTextColor(Color.RED);
                tvH.setTypeface(type);
                tvH.setTextSize(18);

                // Returning the view containing InfoWindow contents
                return v;

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {

        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
            //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            //mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);


        mMap.addMarker(new MarkerOptions()
                .position(UQAC)
                .title("UQAC")
                .snippet("Jean&&02/04/2015&&f")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACEST)
                .title("UQAC Porte Est")
                .snippet("Madeleine&&15/04/2015&&t")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.heart_bit2)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACOUEST)
                .title("UQAC Porte Ouest")
                .snippet("Guy&&10/04/2015&&t")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.heart_bit2)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACCentreSocial)
                .title("UQAC Centre Social")
                .snippet("Michel&&11/04/2015&&f")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(CEGEP)
                .title("Cegep de Chicoutimi")
                .snippet("Patrick&&08/04/2015&&t")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.heart_bit2)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location cur = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        LatLng curPosition = new LatLng(cur.getLatitude(),cur.getLongitude());


        double diff = computeDistanceBetween(UQAC,curPosition);
        double diff1 = computeDistanceBetween(UQACEST,curPosition);
        double diff2 = computeDistanceBetween(UQACOUEST,curPosition);
        double diff3 = computeDistanceBetween(UQACCentreSocial,curPosition);
        double diff4 = computeDistanceBetween(CEGEP,curPosition);

        if(diff < 3 || diff1 < 3 || diff2 < 3 || diff3 < 3 || diff4 < 3 ){
            nHandler.createSimpleNotification(this);
        }

    }

}
