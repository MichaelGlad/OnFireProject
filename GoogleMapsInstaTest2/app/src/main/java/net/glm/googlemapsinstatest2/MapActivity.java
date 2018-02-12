package net.glm.googlemapsinstatest2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    static final int MY_PERMISSION_LOCATION = 102;
    static final String LOG_TAG = "MapActivity";

    private GoogleMap mMap;
    private boolean permissionIsGranted = false;

    Double myLatitude = null;
    Double myLongitude = null;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;


    ZoomControls zoom;
    Marker mMarker;
    Marker circleMarker;

    Button btnMark;
    Button btnSatellite;
    Button btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MyButtonOnClickListener myButtonOnClickListener = new MyButtonOnClickListener();
        btnMark = (Button) findViewById(R.id.btn_mark);
        btnMark.setOnClickListener(myButtonOnClickListener);
        btnSatellite = (Button) findViewById(R.id.btn_satellite);
        btnSatellite.setOnClickListener(myButtonOnClickListener);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(myButtonOnClickListener);


        zoom = (ZoomControls) findViewById(R.id.zc_zoom);

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Log.d(LOG_TAG, " - In onCreate the result is : " + googleApiClient.toString());

        locationRequest = new LocationRequest();
        locationRequest.setInterval(2 * 1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
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
        //LatLng sydney = new LatLng(-34, 151);
        LatLng neriya = new LatLng(31.9558506, 35.1263328);

        circleMarker = mMap.addMarker(new MarkerOptions()
                .position(neriya)
                .icon(BitmapDescriptorFactory.fromBitmap(getCircle(100))));
        circleMarker.setAnchor(0.5f, 0.5f);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.n1);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.3), (int) (bitmap.getHeight() * 0.3), true);
        Bitmap circleBitmap = getCroppedBitmap(resizedBitmap);


        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(circleBitmap);

        mMarker = mMap.addMarker(new MarkerOptions()
                .position(neriya)
                .title("Marker in Neriya")
                .icon(icon));
        mMarker.setAnchor(0.5f, 0.5f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(neriya));
        if (checkPermission()) {

            mMap.setMyLocationEnabled(false);
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public Bitmap getCircle(int radius) {

        Bitmap output = Bitmap.createBitmap(radius*2,
                radius*2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = 0x40EF5350;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawCircle(radius,radius,radius, paint);
        return output;

    }





    private boolean checkPermission() {
        if (permissionIsGranted) {
            return permissionIsGranted;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_LOCATION);
            }
        } else permissionIsGranted = true;

        return permissionIsGranted;

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                permissionIsGranted = true;
            } else {
                permissionIsGranted = false;
                Toast.makeText(this, " This App request location Permission to be granted ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestLocationUpdate() {
        if (checkPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,  this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, " Connection Suspended ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, " Connection Failed : ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        LatLng currentLocation = new LatLng(myLatitude, myLongitude);
//        mMap.addMarker(new MarkerOptions()
//                .position(currentLocation)
//                .title("Current Location")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        circleMarker.setPosition(currentLocation);
        mMarker.setPosition(currentLocation);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }



    class MyButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_mark:
                    LatLng myLocation = new LatLng(myLatitude, myLongitude);
                    mMap.addMarker(new MarkerOptions().position(myLocation).title("Friend is here"));
                    break;

                case R.id.btn_satellite:
                    if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        btnSatellite.setText("Normal");
                    } else {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        btnSatellite.setText("Satellite");

                    }
                    break;
                case R.id.btn_clear:
                    mMap.clear();
                    break;

            }

        }
    }

    void testPutLocation(Location location, String locationName) {
        Marker locationMarker;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(locationName + " Location");
        //Put the Icon and color of Icon on the MAP
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        locationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));


    }
}