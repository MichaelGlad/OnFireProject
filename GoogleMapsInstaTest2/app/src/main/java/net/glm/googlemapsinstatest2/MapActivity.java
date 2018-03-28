package net.glm.googlemapsinstatest2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.PointF;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

import net.glm.googlemapsinstatest2.Data.ModelUser1;

import java.util.ArrayList;

import static net.glm.googlemapsinstatest2.Utility.Utility.*;


public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMapClickListener{

    static final int MY_PERMISSION_LOCATION = 102;
    static final String LOG_TAG = "MapActivity";

    private GoogleMap mMap;
    private boolean permissionIsGranted = false;

    Double myLatitude = null;
    Double myLongitude = null;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private RecyclerView usersRecyclerView;
    private LinearLayoutManager horizontalLinearLayoutManager;
    private RecyclerviewUsersOnMapAdapter mapRecyclerAdapter;
    ArrayList<ModelUser1> users;


    ZoomControls zoom;
    Marker mMarker;
    Marker circleMarker;

    ArrayList<Marker> imageMarkers;
    ArrayList<Marker> circleMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        users = ModelUser1.getFakeUsers1();
        usersRecyclerView = findViewById(R.id.recyclerview_users);
//        MyCustomLayoutManager myCustomLayoutManager = new MyCustomLayoutManager(this);
//        myCustomLayoutManager.setOrientation(MyCustomLayoutManager.HORIZONTAL);
        horizontalLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        usersRecyclerView.setLayoutManager(horizontalLinearLayoutManager);
//        usersRecyclerView.setLayoutManager(myCustomLayoutManager);
        mapRecyclerAdapter = new RecyclerviewUsersOnMapAdapter();
        usersRecyclerView.setAdapter(mapRecyclerAdapter);
        mapRecyclerAdapter.addAll(users);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(usersRecyclerView);

        usersRecyclerView.setVisibility(View.GONE);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Log.d(LOG_TAG, " - In onCreate the result is : " + googleApiClient.toString());


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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng neriya = new LatLng(31.9558506, 35.1263328);

        imageMarkers = initImageMarkers(neriya);
        circleMarkers = initCircleMarkers(neriya);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(neriya, 14));
        if (checkPermission()) {
            mMap.setMyLocationEnabled(false);
        }
        mMap.setOnMarkerClickListener(new MyMarkerClickListener());
        mMap.setOnMapClickListener(this);
    }


    private void requestLocationUpdate() {
        if (checkPermission()) {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(2 * 1000);
            locationRequest.setFastestInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
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
        Double latitude;
        Double longitude;
        LatLng currentLocation;

        for (int i = 0; i < imageMarkers.size(); i++) {
            latitude = location.getLatitude() + users.get(i).getShiftLat();
            longitude = location.getLongitude() + users.get(i).getShiftLong();
            currentLocation = new LatLng(latitude, longitude);
            imageMarkers.get(i).setPosition(currentLocation);
            circleMarkers.get(i).setPosition(currentLocation);

        }

        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
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

    private ArrayList<Marker> initImageMarkers(LatLng location) {
        ArrayList<Marker> markerArrayList = new ArrayList<>();
        Marker marker;
        Double latitude;
        Double longitude;
        LatLng currentLocation;

        for (int i = 0; i < users.size(); i++) {

            Bitmap circleBitmap = getResizebleCircleBitmap(BitmapFactory.decodeResource(getResources(), users.get(i).getImgId()),
                    (int) 30 );
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(circleBitmap);
            latitude = location.latitude + users.get(i).getShiftLat();
            longitude = location.longitude + users.get(i).getShiftLong();
            currentLocation = new LatLng(latitude, longitude);

            marker = mMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title(users.get(i).getName())
                    .icon(icon)
                    .zIndex(1.0f)
            );

            marker.setAnchor(0.5f, 0.5f);
            marker.setTag((Integer) i);
            markerArrayList.add(marker);
        }
        return markerArrayList;
    }

    private ArrayList<Marker> initCircleMarkers(LatLng location) {
        ArrayList<Marker> markerArrayList = new ArrayList<>();
        Marker marker;
        Double latitude;
        Double longitude;
        LatLng currentLocation;

        for (ModelUser1 user : users) {

            latitude = location.latitude + user.getShiftLat();
            longitude = location.longitude + user.getShiftLong();
            currentLocation = new LatLng(latitude, longitude);

            marker = mMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .icon(BitmapDescriptorFactory.fromBitmap(getCircle(user.getRadius()))));
            marker.setAnchor(0.5f, 0.5f);
            markerArrayList.add(marker);
        }
        return markerArrayList;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(usersRecyclerView.getVisibility() == View.VISIBLE){
            usersRecyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {

        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.getTag() != null) {
                Integer position;
                usersRecyclerView.setVisibility(View.VISIBLE);
                position = (Integer) marker.getTag();
                usersRecyclerView.smoothScrollToPosition(position);

            }
            return false;
        }
    }

    public class MyCustomLayoutManager extends LinearLayoutManager {
        //We need mContext to create our LinearSmoothScroller
        private static final float MILLISECONDS_PER_INCH = 40f;
        private Context mContext;

        public MyCustomLayoutManager(Context context) {
            super(context);
            mContext = context;
        }

        //Override this method? Check.
        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView,
                                           RecyclerView.State state, int position) {

            //Create your RecyclerView.SmoothScroller instance? Check.
            LinearSmoothScroller smoothScroller =
                    new LinearSmoothScroller(mContext) {

                        //Automatically implements this method on instantiation.
                        @Override
                        public PointF computeScrollVectorForPosition
                        (int targetPosition) {
                            return MyCustomLayoutManager.this.computeScrollVectorForPosition
                                    (targetPosition);
                        }

                        @Override
                        protected float calculateSpeedPerPixel
                                (DisplayMetrics displayMetrics) {
                            return MILLISECONDS_PER_INCH/displayMetrics.densityDpi;
                        }
                    };

            //Docs do not tell us anything about this,
            //but we need to set the position we want to scroll to.
            smoothScroller.setTargetPosition(position);

            //Call startSmoothScroll(SmoothScroller)? Check.
            startSmoothScroll(smoothScroller);
        }
    }



}