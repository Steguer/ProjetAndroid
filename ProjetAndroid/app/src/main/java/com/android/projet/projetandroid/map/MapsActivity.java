package com.android.projet.projetandroid.map;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.android.projet.projetandroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;

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

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.textViewUsername);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.textViewRealDate);

                // Setting the latitude
                tvLat.setText("Patrick");

                // Setting the longitude
                tvLng.setText("18/04/2015");

                TextView tvG = (TextView) v.findViewById(R.id.textViewGameName);
                Typeface type = Typeface.createFromAsset(getAssets(), "RetrovilleNC.ttf");
                tvG.setTextColor(Color.RED);
                tvG.setTypeface(type);
                tvG.setTextSize(20);
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
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACEST)
                .title("UQAC Porte Est")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACOUEST)
                .title("UQAC Porte Ouest")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACCentreSocial)
                .title("UQAC Centre Social")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(UQACSalleCours)
                .title("UQAC Salle cours")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.addMarker(new MarkerOptions()
                .position(CEGEP)
                .title("Cegep de Chicoutimi")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

    }

}
