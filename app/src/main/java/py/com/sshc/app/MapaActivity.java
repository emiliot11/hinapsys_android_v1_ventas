package py.com.sshc.app;

import android.Manifest;
import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

@RuntimePermissions
public class MapaActivity extends AppCompatActivity implements py.com.sshc.app.NumeroDocumentoDialogFragment.Host {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    Location mCurrentLocation;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */
    Menu menuMapa;

    private final static String KEY_LOCATION = "location";

    StringUtil stringUtil = new StringUtil();

    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_demo_activity);

        if (TextUtils.isEmpty(getResources().getString(R.string.google_maps_api_key))) {
            throw new IllegalStateException("You forgot to supply a Google Maps API key");
        }

        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {



            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(this, "Error - Map Fragment es nulo!!", Toast.LENGTH_SHORT).show();
        }

    }

    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
/*            LatLngBounds australiaBounds = new LatLngBounds(
                    new LatLng(-44, 113), // SW bounds
                    new LatLng(-10, 154)  // NE bounds
            );
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(australiaBounds, 0));*/

            Toast.makeText(this, "Mapa cargado exitosamente!", Toast.LENGTH_SHORT).show();
            MapaActivityPermissionsDispatcher.getMyLocationWithPermissionCheck(this);
            MapaActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
        } else {
            Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapaActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @SuppressWarnings({"MissingPermission"})
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    /*
     * Called when the Activity becomes visible.
    */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /*
     * Called when the Activity is no longer visible.
	 */
    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }

            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Display the connection status

        if (mCurrentLocation != null) {
            Toast.makeText(this, "Ubicación del GPS localizada!", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
        /*} else {
            Toast.makeText(this, "Ubicación desconocida, favor habilite el GPS del equipo!", Toast.LENGTH_SHORT).show();*/
        }
        MapaActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // GPS may be turned off
        if (location == null) {
            return;
        }

        // Report to the UI that the location was updated

        mCurrentLocation = location;
        String msg = "Ubicación actual: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());

        if (menuMapa != null) {
            MenuItem menuItem = menuMapa.findItem(R.id.action_registrar);

            menuItem.setEnabled(true);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(),
                        location.getLongitude()), 16));

        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onOptionOne(String numeroDocumento) {

        registrar(numeroDocumento);

    }

    @Override
    public void onOptionTwo() {

    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_geotag, menu);
        menuMapa =  menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_registrar) {


            /*Intent intent = new Intent(this,NumeroDocumentoDialogFragment.class);
            startActivity(intent);*/

            showEditDialog();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        NumeroDocumentoDialogFragment numeroDocumentoDialogFragment = py.com.sshc.app.NumeroDocumentoDialogFragment.newInstance();
        numeroDocumentoDialogFragment.show(fm, "fragment_edit_name");
    }

    public void registrar(String numeroDocumento) {

        GeoTagDoc geoTagDoc = new GeoTagDoc();

        String msg = "Ubicación actual: " +
                Double.toString(mCurrentLocation.getLatitude()) + "," +
                Double.toString(mCurrentLocation.getLongitude());

        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        geoTagDoc.setUid(numeroDocumento);
        geoTagDoc.setLatitud(mCurrentLocation.getLatitude());
        geoTagDoc.setLongitud(mCurrentLocation.getLongitude());

        guardarGeoTagDoc(geoTagDoc);

        //Toast.makeText(this, numeroDocumento, Toast.LENGTH_SHORT).show();

    }

    public void guardarGeoTagDoc(GeoTagDoc geoTagDoc){


        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(this) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(this).getServidorConServicio())
                .build();


        GeoTagDocAPI api = adapter.create(GeoTagDocAPI.class);

        GeoTagDocParametros geoTagDocParametros = new GeoTagDocParametros();
        geoTagDocParametros.setEmpresa("01");
        geoTagDocParametros.setGeoTagDoc(geoTagDoc);
        geoTagDocParametros.setCuenta(new PreferencesManager(this).getCuenta(this));


        //pb.setVisibility(View.VISIBLE);
        api.guardarGeoTagDoc(geoTagDocParametros, new Callback<List<GeoTagDocRESP>>() {


            @Override
            public void success(List<GeoTagDocRESP> geoTagDocRESPS, Response response) {
                if (this == null) {
                    return;
                }
                if (geoTagDocRESPS != null) {
                    Boolean guardado = geoTagDocRESPS.get(0).getEstado();
                    String numeroCRM = geoTagDocRESPS.get(0).getMensaje().trim();
                    //pb.setVisibility(View.INVISIBLE);
                    if (guardado) {

                        Toast mensajeAlUsuario = Toast.makeText(MapaActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT);
                        mensajeAlUsuario.setGravity(Gravity.CENTER, 0, 0);
                        mensajeAlUsuario.show();;

                    }
                    else
                    {
                        Toast.makeText(MapaActivity.this, "ERROR = " + geoTagDocRESPS.get(0).getMensaje(), Toast.LENGTH_LONG).show();
                    }


                } else {
                    //pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (MapaActivity.this == null) {
                    return;
                }

                String errorDescripcion = "";

                //pb.setVisibility(View.INVISIBLE);
                error.printStackTrace();

                if (error.getResponse() != null) {
                    try {
                        errorDescripcion = stringUtil.convertStreamToString(error.getResponse().getBody().in());
                        if (errorDescripcion != null && error.getResponse().getStatus() == 500) {
                            Toast.makeText(MapaActivity.this, errorDescripcion, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MapaActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
