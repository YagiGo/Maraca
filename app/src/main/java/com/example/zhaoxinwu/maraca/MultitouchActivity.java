package com.example.zhaoxinwu.maraca;

import android.content.res.AssetFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.util.Log;
import android.support.v4.view.MotionEventCompat;
import android.media.MediaPlayer;
import android.media.AudioManager;

import java.io.IOException;

public class MultitouchActivity extends AppCompatActivity {
    private TextView mTouchTypeText;
    private TextView mTouchPointText1;
    private TextView mTouchPointText2;
    private TextView mTouchLengthText;
    String DEBUG_TAG = "Maraca";
    // For touch process
    private static final int NONE = 0;
    private static final int TOUCH = 1;
    private static final int DRAG = 2;
    private static final int PINCH = 3;
    private MediaPlayer mediaPlayer;

    //Parameters of Dragging
    private float mDragStartX = 0.0f;
    private float mDragStartY = 0.0f;
    private double mPinchStartDistance = 0.0f;
    private int mTouchMode;
    private String mTouchTypeString = "";
    private String mTouchPoint1String = "";
    private String mTouchPoint2String = "";
    private String mTouchLengthString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multitouch);
        mTouchTypeText = (TextView) findViewById(R.id.text_view_touch_type);
        mTouchPointText1 = (TextView) findViewById(R.id.text_view_touch_point_1);
        mTouchPointText2 = (TextView) findViewById(R.id.text_view_touch_point_2);
        mTouchLengthText = (TextView) findViewById(R.id.text_view_touch_length);
        audioFileSetUp();
    }
    private boolean audioFileSetUp() {
        mediaPlayer = new MediaPlayer();
        String filePath = "testMusic.wav";
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

    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        audioFileSetUp();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                mediaPlayer.reset();
        return true;

            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                mediaPlayer.start();
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                mediaPlayer.stop();
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                mediaPlayer.stop();
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                mediaPlayer.stop();
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

}



