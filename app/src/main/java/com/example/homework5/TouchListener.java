package com.example.homework5;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class TouchListener implements View.OnTouchListener {
    MainActivity mainActivity;
    GestureDetectorCompat gestureDetectorCompat;

    public TouchListener(MainActivity ma) {
        this.mainActivity = ma;
        gestureDetectorCompat = new GestureDetectorCompat(this.mainActivity, new MyGestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        int maskedAction = event.getActionMasked();
        switch(maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int i = 0, size = event.getPointerCount(); i < size; i++)
                {
                    int id = event.getPointerId(i);
                    mainActivity.addPath(id, event.getX(i), event.getY(i));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0, size = event.getPointerCount(); i < size; i++)
                {
                    int id = event.getPointerId(i);
                    mainActivity.updatePath(id, event.getX(i), event.getY(i));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                for (int i = 0, size = event.getPointerCount(); i < size; i++)
                {
                    int id = event.getPointerId(i);
                    mainActivity.removePath(id);
                }
                break;
        }
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mainActivity.onDoubleTap();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mainActivity.onLongPress();
            super.onLongPress(e);
        }
    }
}
