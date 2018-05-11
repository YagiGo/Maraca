package com.example.zhaoxinwu.maraca;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class OrientationActivity extends AppCompatActivity implements SensorEventListener {
    private CheckBox mCheckBoxOrientation;
    private TextView mAzimuthText, mPitchText, mRollText;
    private float[] mAccelerationValue = new float[3];
    private float[] mGeoMagneticValue = new float[3];
    private float[] mOrientationValue = new float[3];
    private float[] mInRotationMatrix = new float[9];
    private float[] mOutRotationMatrix = new float[9];
    private float[] mInclinationMatrix = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magenticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magenticSensor, sensorManager.SENSOR_DELAY_UI);

        mAzimuthText = (TextView)findViewById(R.id.text_view_azimuth);
        mRollText = (TextView)findViewById(R.id.text_view_roll);
        mPitchText = (TextView)findViewById(R.id.text_view_pitch);

        Button buttonOrientation = (Button) findViewById(R.id.button_orientation);
        buttonOrientation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!mCheckBoxOrientation.isChecked()) {
                    SensorManager.getRotationMatrix(mInRotationMatrix, mInclinationMatrix, mAccelerationValue, mGeoMagneticValue);
                    SensorManager.remapCoordinateSystem(mInRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, mOutRotationMatrix);
                    SensorManager.getOrientation(mOutRotationMatrix, mOrientationValue);

                    String azimuthText = String.valueOf(Math.floor(Math.toDegrees((double) mOrientationValue[0])));
                    String pitchText = String.valueOf(Math.floor(Math.toDegrees((double) mOrientationValue[1])));
                    String rollText = String.valueOf(Math.floor(Math.toDegrees((double) mOrientationValue[2])));

                    mAzimuthText.setText(azimuthText);
                    mPitchText.setText(pitchText);
                    mRollText.setText(rollText);
                }

            }
        });
        mCheckBoxOrientation = (CheckBox) findViewById(R.id.checkbox_orientation);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeoMagneticValue = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerationValue = event.values.clone();
                break;
        }
        if (mCheckBoxOrientation.isChecked()) {
            SensorManager.getRotationMatrix(mInRotationMatrix, mInclinationMatrix, mAccelerationValue, mGeoMagneticValue);
            SensorManager.remapCoordinateSystem(mInRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, mOutRotationMatrix);
            SensorManager.getOrientation(mOutRotationMatrix, mOrientationValue);
            String azimuthText = String.valueOf(Math.floor(Math.toDegrees((double) mOrientationValue[0])));
            String pitchText = String.valueOf(Math.floor(Math.toDegrees((double) mOrientationValue[1])));
            String rollText = String.valueOf(Math.floor(Math.toDegrees((double) mOrientationValue[2])));
            float gX = mOrientationValue[0] / SensorManager.GRAVITY_EARTH;
            float gY = mOrientationValue[1] / SensorManager.GRAVITY_EARTH;
            float gZ = mOrientationValue[2] / SensorManager.GRAVITY_EARTH;
            //Calculate G force

            mAzimuthText.setText(String.valueOf(gX));
            mPitchText.setText(String.valueOf(gY));
            mRollText.setText(String.valueOf(gZ));
            /*
            mAzimuthText.setText(azimuthText);
            mPitchText.setText(pitchText);
            mRollText.setText(rollText);
            */
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}