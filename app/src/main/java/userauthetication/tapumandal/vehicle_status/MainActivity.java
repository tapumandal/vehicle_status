package userauthetication.tapumandal.vehicle_status;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button vehicleStatusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vehicleStatusBtn = (Button) findViewById(R.id.go_vehicle_status);
    }

    public void vehicleStatus(View view) {

        Toast.makeText(getApplicationContext(), "GO Vehicle Status Activity", Toast.LENGTH_LONG).show();
    }
}