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
    String DEBUG_TAG = "Maraca";
    private MediaPlayer mediaPlayer;

    //Parameters of Dragging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multitouch);
        audioFileSetUp();
    }
    private boolean audioFileSetUp() {
        mediaPlayer = new MediaPlayer();
        String filePath = "drumSound.mp3";
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
                mediaPlayer.start();
        return true;

            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                //mediaPlayer.start();
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                mediaPlayer.stop();
                mediaPlayer.release();
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                mediaPlayer.stop();
                mediaPlayer.release();
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



