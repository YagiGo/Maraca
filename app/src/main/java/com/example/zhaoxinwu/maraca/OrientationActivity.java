package com.example.zhaoxinwu.maraca;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.IDNA;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.IOException;


public class OrientationActivity extends AppCompatActivity implements SensorEventListener {
    /*
    private CheckBox mCheckBoxOrientation;
    private TextView mAzimuthText, mPitchText, mRollText;
    private float[] mAccelerationValue = new float[3];
    private float[] mGeoMagneticValue = new float[3];
    private float[] mOrientationValue = new float[3];
    private float[] mInRotationMatrix = new float[9];
    private float[] mOutRotationMatrix = new float[9];
    private float[] mInclinationMatrix = new float[9];
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    private static final String GForceTag = "OrientationActivity";
    private long lastUpdate = System.currentTimeMillis();
    private final int SHAKE_THRESHOLD = 800;
    */
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private MediaPlayer mediaPlayerMaraca = new MediaPlayer();
    private String DEBUG_TAG = "Maraca";
    private static final int maxVol = 100;
    protected boolean audioFileSetUp(MediaPlayer mediaPlayer, String fileName) {
        String filePath = fileName;
        boolean fileCheck = false;
        // Read File
        try {

            AssetFileDescriptor audioScripter = getAssets().openFd(filePath);
            mediaPlayer.setDataSource(audioScripter.getFileDescriptor(),
                    audioScripter.getStartOffset(),
                    audioScripter.getLength());
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return fileCheck;
    }
    protected boolean audioFileReleased(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
        return true;
    }
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if(mAccel > 12) {
                float currVol = mAccel;
                Log.d("Shake Detector", "Shaking Detected");
                // while(!audioFileSetUp()) {}
                float log1=(float)(Math.log(maxVol-currVol)/Math.log(maxVol));
                mediaPlayerMaraca.start();
                mediaPlayerMaraca.setVolume(1-log1,1-log1);
            }
            // Log.d("DEBUG", Float.toString(mAccel));
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        audioFileSetUp(mediaPlayerMaraca, "testMusic.wav");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        /*
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magenticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magenticSensor, sensorManager.SENSOR_DELAY_UI);


        mAzimuthText = (TextView) findViewById(R.id.text_view_azimuth);
        mRollText = (TextView) findViewById(R.id.text_view_roll);
        mPitchText = (TextView) findViewById(R.id.text_view_pitch);

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
        */
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*
    }
        float x=0, y=0, z=0, last_x=0, last_y=0, last_z=0;
        long diffTime = 100;
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
            long curTime = System.currentTimeMillis();
            float[] values = new float[3];
            //float x, y, z, last_x, last_y, last_z;
            if ((curTime - lastUpdate) > 100) {
                diffTime = curTime - lastUpdate;
                lastUpdate = curTime;
                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];
            }
            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
            */
            /*
            if (speed > SHAKE_THRESHOLD) {
                Log.d("sensor", "shake detected w/ speed: " + speed);
            }
            */
            /*
            Log.d("sensor", "shake detected w/ speed: " + speed);
            last_x = x;
            last_y = y;
            last_z = z;
            */
        // }

        //alpha = t / t+dT
            /*
            final float alpha = 0.8f;
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];
            */
            /*
            float gX = mOrientationValue[0] / SensorManager.GRAVITY_EARTH;
            float gY = mOrientationValue[1] / SensorManager.GRAVITY_EARTH;
            float gZ = mOrientationValue[2] / SensorManager.GRAVITY_EARTH;
            */
        //Calculate G force
        //Log.d(GForceTag, Float.toString(linear_acceleration[0]));
        //Log.d(GForceTag, Float.toString(linear_acceleration[1]));
        //Log.d(GForceTag,Float.toString(linear_acceleration[2]));
            /*
            mAzimuthText.setText(String.valueOf(linear_acceleration[0]));
            mPitchText.setText(String.valueOf(linear_acceleration[1]));
            mRollText.setText(String.valueOf(linear_acceleration[2]));
            */
            /*
            mAzimuthText.setText(azimuthText);
            mPitchText.setText(pitchText);
            mRollText.setText(rollText);
            */
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

}