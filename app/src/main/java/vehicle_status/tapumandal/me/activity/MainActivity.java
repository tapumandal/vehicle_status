package vehicle_status.tapumandal.me.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vehicle_status.tapumandal.me.R;

public class MainActivity extends AppCompatActivity {

    private Button vehicleStatusBtn, mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        vehicleStatusBtn = (Button) findViewById(R.id.go_vehicle_status);
        mapView = (Button) findViewById(R.id.go_map_view);
    }

    public void vehicleStatus(View view) {


        startActivity(new Intent(getApplicationContext(), VehicleStatusActivity.class));
    }

    public void mapView(View view) {
        startActivity(new Intent(getApplicationContext(), MapMyLocationActivity.class));
    }

    public void searchLocation(View view) {
    }

    public void mapDirection(View view) {
    }
}