package com.example.zhaoxinwu.maraca;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeveloperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        Button orientationSwitchButton = (Button) findViewById(R.id.button_orientation);
        orientationSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrientationActivity.class);
                startActivity(intent);
            }
        });

        Button locationActivitySwitchButton = (Button) findViewById(R.id.button_location);
        locationActivitySwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(intent);
            }
        });

        Button multitouchActivitySwitchButton = (Button) findViewById(R.id.button_multitouch);
        multitouchActivitySwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), MultitouchActivity.class);
                startActivity(intent);
            }
        });
    }
}
