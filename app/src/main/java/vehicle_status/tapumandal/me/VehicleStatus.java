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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);

//        this.onLocationChanged(null);
    }

//    speed is meter per second
    private void updateVehicleSpeed(float speed) {

        speed = (speed*3600)/1000;
        speed = Float.parseFloat(new DecimalFormat("##.##").format(speed));
        tv_speed.setText(Float.toString(speed)+" KM");
    }


    @Override
    public void onLocationChanged(Location location) {


//        if(location == null){
//            this.updateVehicleSpeed((float) 0.0);
//        }else{
//            this.updateVehicleSpeed(location.getSpeed());
//        }

        this.updateVehicleSpeed(location.getSpeed());
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
