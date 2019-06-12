package vehicle_status.tapumandal.me;

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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class VehicleStatus extends AppCompatActivity implements LocationListener {

    private int speed;
    private Location currentLocation;
    private Location startLocation;
    private float tradeledDistance = 0;

    private LocationManager locationManager;
    private TextView tv_speed;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vehicle_status);

        tv_speed = (TextView) findViewById(R.id.vehicle_speed);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

//    speed is meter per second
    private void updateVehicleSpeed(float speed) {

        speed = (speed*3600)/1000;
        speed = Float.parseFloat(new DecimalFormat("##.##").format(speed));
        tv_speed.setText(Float.toString(speed)+" KM");
    }

    private void vehiclePosition(Location location) {
        if(startLocation == null){
//            vechicle starting position
            startLocation.setLatitude(location.getLatitude());
            startLocation.setLongitude(location.getLongitude());
        }else{
//            vehicle current position
            currentLocation.setLatitude(location.getLatitude());
            currentLocation.setLongitude(location.getLongitude());
        }
    }
    private void travelledDistance() {
        tradeledDistance = startLocation.distanceTo(currentLocation);
        Toast.makeText(getApplicationContext(), Float.toString(tradeledDistance), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLocationChanged(Location location) {

        this.updateVehicleSpeed(location.getSpeed());
        if(location != null) {
            this.vehiclePosition(location);
            this.travelledDistance();
        }
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
