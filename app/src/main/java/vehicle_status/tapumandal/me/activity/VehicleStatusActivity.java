package vehicle_status.tapumandal.me.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

import vehicle_status.tapumandal.me.R;
import vehicle_status.tapumandal.me.model.VehicleStatusModel;

public class VehicleStatusActivity extends AppCompatActivity implements LocationListener {

    private int speed;
    private Location currentLocation, startLocation;
    private float tradeledDistance = 0;

    private LocationManager locationManager;
    private TextView tv_speed, tv_traveled_distance;

    public VehicleStatusModel vehicleStatusModel;
    FirebaseDatabase database;
    DatabaseReference vehicleStatusRef;

    private String uid="dQjKuLeF45bfdYgvmFxx36Yuxqw2";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vehicle_status);

        tv_speed = (TextView) findViewById(R.id.vehicle_speed);
        tv_traveled_distance = (TextView) findViewById(R.id.traveled_distance);

        vehicleStatusModel = new VehicleStatusModel();
        database = FirebaseDatabase.getInstance();
        vehicleStatusRef = database.getReference("vehicle_status");
        currentLocation = new Location("");
        startLocation = new Location("");


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
//            Toast.makeText(getApplicationContext(), "else", Toast.LENGTH_SHORT).show();
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, this);
        }

    }

//    speed is meter per second
    private void updateVehicleSpeed(float speed) {

        speed = (speed*3600)/1000;
        speed = Float.parseFloat(new DecimalFormat("##.##").format(speed));
        tv_speed.setText(Float.toString(speed)+" KM");

        vehicleStatusModel.setSpeed(speed);
    }

    private void vehiclePosition(Location location) {
        if(vehicleStatusModel.getStartLatitude() > 0 || vehicleStatusModel.getStartLongitude() > 0){                    //  vehicle current position

            currentLocation.setLatitude(location.getLatitude());
            currentLocation.setLongitude(location.getLongitude());

            vehicleStatusModel.setCurrentLatitude(location.getLongitude());
            vehicleStatusModel.setCurrentLongitude(location.getLatitude());
        }else{                                                                                      //  vechicle starting position

            startLocation.setLatitude(location.getLatitude());
            startLocation.setLongitude(location.getLongitude());
            currentLocation.setLatitude(location.getLatitude());
            currentLocation.setLongitude(location.getLongitude());

            vehicleStatusModel.setStartLatitude(location.getLatitude());
            vehicleStatusModel.setStartLongitude(location.getLongitude());
            vehicleStatusModel.setCurrentLatitude(location.getLatitude());
            vehicleStatusModel.setCurrentLongitude(location.getLongitude());

        }
    }
    private void travelledDistance() {
        tradeledDistance = Float.parseFloat(new DecimalFormat("##.####").format((startLocation.distanceTo(currentLocation))/1000));
        tv_traveled_distance.setText(Float.toString(tradeledDistance)+" KM");
    }


    private void saveVehicleStatus() {
        Toast.makeText(getApplicationContext(), "saveVehicleStatus", Toast.LENGTH_LONG).show();
        vehicleStatusRef.child(uid).setValue(vehicleStatusModel);
    }

    @Override
    public void onLocationChanged(Location location) {

        this.updateVehicleSpeed(location.getSpeed());

        this.vehiclePosition(location);
        this.travelledDistance();
        this.saveVehicleStatus();
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
