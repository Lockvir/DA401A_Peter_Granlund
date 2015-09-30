package petergranlund.assignment_4_2;

import android.location.Location;
import android.media.MediaPlayer;
//import android.support.v7.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener/*, MarkerDialogFragment.OnFragmentInteractionListener*/, LocationListener , GoogleApiClient{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng home = new LatLng(47, 106);
    private MapLocationObject mapLocationObject;
    MediaPlayer mMPlayer;
    Vibrator mVibrator;
    long mVibrationLenth = 500;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    DateFormat mLastUpdateTime;
    double mDistanceToPoint = 0.0000f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MapsActivity", "onCreate");
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMPlayer = MediaPlayer.create(this,R.raw.sound_file);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        //mMap.setMyLocationEnabled(true);
        createLocationRequest();
        //Location here = mMap.getMyLocation();
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addMarker(new MarkerOptions().position(home).title("Marker"));
        //Log.i("MapActivity", String.valueOf(here.getLatitude())+ "  "+String.valueOf(here.getLongitude()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 1));
        mapLocationObject = new MapLocationObject(55.611404, 12.995255,"Sweden", "Malmo", "Spellabbet");
        mMap.setOnMarkerClickListener(this);
        mapLocationObject.MakeMaker(mMap);
        mMap.clear();
        //mapLocationObject.MakeMaker(mMap);
        startLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MapsActivity", "onResume()");
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
        // Do a null check to confirm that we have not already instantiated the map.
        Log.i("MapsActivity", "setUpMapIfNeeded()");
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("MapsActivity", "onMarkerClick()");
        MarkerDialogFragment mDialog = new MarkerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("location", mapLocationObject);
        mDialog.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        mDialog.show(fm, "fragment_marker_dialog");
        PlaySound();
        VibratePhone();
        return true;
    }

    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }*/

    private void PlaySound()
    {
        mMPlayer.start();
    }

    private void VibratePhone()
    {
        mVibrator.vibrate(500);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        //updateUI();
        if (IsCloseTo(mapLocationObject))
        {
            mapLocationObject.MakeMaker(mMap);
        }
        else
        {
            mMap.clear();
        }
    }

    private boolean IsCloseTo(MapLocationObject locationObject)
    {
        return mDistanceToPoint > Math.sqrt(Math.pow(( mCurrentLocation.getLatitude()- locationObject.getmLatLng().latitude),2)+ Math.pow((mCurrentLocation.getLongitude()-locationObject.getmLatLng().longitude),2));
    }

    @Override
    public boolean hasConnectedApi(Api<?> api) {
        return false;
    }

    @Override
    public ConnectionResult getConnectionResult(Api<?> api) {
        return null;
    }

    @Override
    public void connect() {

    }

    @Override
    public ConnectionResult blockingConnect() {
        return null;
    }

    @Override
    public ConnectionResult blockingConnect(long l, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void reconnect() {

    }

    @Override
    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        return null;
    }

    @Override
    public void stopAutoManage(FragmentActivity fragmentActivity) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isConnecting() {
        return false;
    }

    @Override
    public void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {

    }

    @Override
    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
        return false;
    }

    @Override
    public void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {

    }

    @Override
    public void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {

    }

    @Override
    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
        return false;
    }

    @Override
    public void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {

    }
}
