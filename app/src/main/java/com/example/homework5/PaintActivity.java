package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class PaintActivity extends AppCompatActivity {

    MyCanvas myCanvas;
    TouchListener touchListener;

    Random rd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchListener = new TouchListener(this);
        myCanvas.setOnTouchListener(touchListener);

        String imagePath = getIntent().getStringExtra("image_path");
        Drawable drawable = Drawable.createFromPath(imagePath);
        myCanvas.setBackground(drawable);
    }

    public void addPath(int id, float x, float y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        myCanvas.updatePath(id, x, y);
    }

    public void removePath(int id) {
        myCanvas.removePath(id);
    }

    public void onDoubleTap() {
        myCanvas.setBackgroundColor(Color.rgb(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255)));
    }

    public void changePaintColor(View view) {
        switch (view.getId()) {
            case R.id.bRed:
                myCanvas.changeColor(Color.RED);
                break;
            case R.id.bBlue:
                myCanvas.changeColor(Color.BLUE);
                break;
            case R.id.bGreen:
                myCanvas.changeColor(Color.GREEN);
                break;
        }
    }

}