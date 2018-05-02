package com.example.zhaoxinwu.maraca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class MultitouchActivity extends AppCompatActivity {
    private TextView mTouchTypeText;
    private TextView mTouchPointText1;
    private TextView mTouchPointText2;
    private TextView mTouchLengthText;
    // For touch process
    private static final int NONE = 0;
    private static final int TOUCH = 1;
    private static final int DRAG = 2;
    private static final int PINCH = 3;

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
    }

    public boolean onTouchEvent(MotionEvent event) {
        //Pitch Detected
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if(event.getPointerCount() >=2) {
                    mPinchStartDistance = getPinchDistance(event);
                    if (mPinchStartDistance > 50) {
                        mTouchMode = PINCH;
                        mTouchTypeString = "PINCH";
                        mTouchPoint1String = "x:" + event.getX(0) + "y:" + event.getY(0);
                        mTouchPoint2String = "x:" + event.getX(1) + "y:" + event.getY(1);
                        mTouchLengthString = "length:" + getPinchDistance(event);
                    }
                    break;
                    

                }
        }

}


