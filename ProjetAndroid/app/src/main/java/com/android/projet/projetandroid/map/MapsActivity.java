package com.android.projet.projetandroid.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.projet.projetandroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    private HashMap<Marker, MyMarker> mMarkersHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();

        mMyMarkersArray.add(new MyMarker("UQAC", "icon1", Double.parseDouble("-48.420067"), Double.parseDouble("-71.052506")));
        mMyMarkersArray.add(new MyMarker("Cegep de Chicoutimi", "icon2", Double.parseDouble("48.424268"), Double.parseDouble("-71.052007")));
        mMyMarkersArray.add(new MyMarker("UQAC : Centre Social", "icon3", Double.parseDouble("48.419900"), Double.parseDouble("-71.052700")));
        mMyMarkersArray.add(new MyMarker("UQAC : Porte OUEST", "icon4", Double.parseDouble("48.420357"), Double.parseDouble("-71.053006")));
        mMyMarkersArray.add(new MyMarker("UQAC : Porte EST","icon1",Double.parseDouble("48.419957"), Double.parseDouble("-71.052006")));

        setUpMap();

        plotMarkers(mMyMarkersArray);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarker myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.castle));

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    private int manageMarkerIcon(String markerIcon)
    {
        if (markerIcon.equals("icon1"))
            return R.drawable.castle;
        else if(markerIcon.equals("icon2"))
            return R.drawable.castle;
        else if(markerIcon.equals("icon3"))
            return R.drawable.castle;
        else if(markerIcon.equals("icon4"))
            return R.drawable.castle;
        else if(markerIcon.equals("icon5"))
            return R.drawable.castle;
        else if(markerIcon.equals("icon6"))
            return R.drawable.castle;
        else if(markerIcon.equals("icon7"))
            return R.drawable.castle;
        else
            return R.drawable.castle;
    }


    private void setUpMap()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.

            if (mMap != null)
            {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                    {
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

            MyMarker myMarker = mMarkersHashMap.get(marker);

            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);

            TextView anotherLabel = (TextView)v.findViewById(R.id.another_label);

            markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));

            markerLabel.setText(myMarker.getmLabel());
            anotherLabel.setText("Mettre du texte");

            return v;
        }
    }

}
