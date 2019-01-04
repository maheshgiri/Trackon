package pnpl.admin.trackon;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat = 18.9256,longitude=72.8242;
    private Timer timer;
    private Marker mumbaimarker;
    private Handler handler;
    private TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    lat = lat + 0.002;
                    longitude = longitude + 0.002;

                    mumbaimarker.setRotation(180);
                    LatLng newmum = new LatLng(lat, longitude);
                    mumbaimarker.setPosition(newmum);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(newmum));
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        handler=new Handler();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (timer==null)
        timer=new Timer();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer!=null){
            timer.purge();
            timer.cancel();
            timer=null;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng mumbai = new LatLng(lat  , longitude);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.taxi);
        mumbaimarker = mMap.addMarker(new MarkerOptions()
                .position(mumbai)
                .icon(icon)
                .title("Mumbai"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(mumbai));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (timer!=null)timer.schedule(timerTask,6000,6000);
    }
}
