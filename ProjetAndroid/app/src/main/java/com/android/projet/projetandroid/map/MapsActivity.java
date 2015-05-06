package com.android.projet.projetandroid.map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.projet.projetandroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


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

        getAllLevels();
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

    }

    private void getAllLevels() {
        String stringUrl = "http://progmobileuqac.olympe.in/ville.php?mode=get";
        requete(stringUrl);
    }

    private void requete(String url) {
        // Gets the URL from the UI's text field.
        String stringUrl = url;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            Toast.makeText(getApplicationContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            ArrayList<String[]> niveaux = jsonParsing(result);
            addMarkers(niveaux);

            //textView.setText(p);
        }
    }

    private void addMarkers(ArrayList<String[]> niveaux) {
        for(String[] niv : niveaux) {
            LatLng pos = new LatLng(Double.valueOf(niv[0]), Double.valueOf(niv[1]));
            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(niv[2])
                    .snippet(niv[3]+"&&"+niv[4]+"&&f")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.castle)));
        }
        //p += "lat "+niv[0]+" lon "+niv[1]+" nom "+niv[2]+" joueur "+niv[3]+" date "+niv[4]+"\n";
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        //
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"), 8);
        String line = null, result = "";
        while ((line = reader.readLine()) != null) {
            result += line + "\n";
        }
        return result;
    }

    private ArrayList<String[]> jsonParsing(String toParse) {
        // Parse les données JSON
        ArrayList<String[]> niveaux = new ArrayList<String[]>();
        try{
            JSONArray jArray = new JSONArray(toParse);
            for(int i=0;i<jArray.length();i++){
                String[] niv = new String[5];
                JSONObject json_data = jArray.getJSONObject(i);
                // Affichage ID_ville et Nom_ville dans le LogCat
                String lat = ((Double)json_data.getDouble("lat")).toString();
                String lon = ((Double)json_data.getDouble("lon")).toString();
                String nom = json_data.getString("nom");
                String pseudo = json_data.getString("joueur");
                String date = json_data.getString("date");
                Log.i("log_tag", "lat: " + json_data.getDouble("lat") +
                                ", lon: " + json_data.getDouble("lon")
                );
                niv[0] = lat;
                niv[1] = lon;
                niv[2] = nom;
                niv[3] = pseudo;
                niv[4] = date;
                niveaux.add(niv);
            }
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return niveaux;
    }
}
