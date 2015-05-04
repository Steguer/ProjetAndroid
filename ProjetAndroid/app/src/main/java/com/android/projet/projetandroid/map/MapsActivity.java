package com.android.projet.projetandroid.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.projet.projetandroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;



public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;

    static final LatLng UQAC = new LatLng(48.420067, -71.052506);
    static final LatLng UQACEST = new LatLng(48.419957, -71.052006);
    static final LatLng UQACOUEST = new LatLng(48.420357, -71.053006);
    static final LatLng CEGEP = new LatLng(48.424268, -71.052007);
    static final LatLng UQACCentreSocial = new LatLng(48.419900,-71.052700);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Marker uqac = mMap.addMarker(new MarkerOptions()
                .position(UQAC)
                .title("UQAC")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        Marker uqacest = mMap.addMarker(new MarkerOptions()
                .position(UQACEST)
                .title("UQAC Porte Est")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        Marker uqacouest = mMap.addMarker(new MarkerOptions()
                .position(UQACOUEST)
                .title("UQAC Porte Ouest")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        Marker uqaccs = mMap.addMarker(new MarkerOptions()
                .position(UQACCentreSocial)
                .title("UQAC Centre Social")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        Marker cegep = mMap.addMarker(new MarkerOptions()
                .position(CEGEP)
                .title("Cegep de Chicoutimi")
                .snippet("Informations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));

        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

}
