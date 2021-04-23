package com.example.homework5;

import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class TouchListener implements View.OnTouchListener {
    PaintActivity paintActivity;
    GestureDetectorCompat gestureDetectorCompat;

    public TouchListener(PaintActivity pa) {
        this.paintActivity = pa;
        gestureDetectorCompat = new GestureDetectorCompat(this.paintActivity, new MyGestureListener());
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
                    paintActivity.addPath(id, event.getX(i), event.getY(i));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0, size = event.getPointerCount(); i < size; i++)
                {
                    int id = event.getPointerId(i);
                    paintActivity.updatePath(id, event.getX(i), event.getY(i));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                for (int i = 0, size = event.getPointerCount(); i < size; i++)
                {
                    int id = event.getPointerId(i);
                    paintActivity.removePath(id);
                }
                break;
        }
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            paintActivity.onDoubleTap();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
//            paintActivity.onLongPress();
            super.onLongPress(e);
        }
    }
}
