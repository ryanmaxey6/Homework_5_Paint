package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    MyCanvas myCanvas;
    TouchListener touchListener;

    Random rd = new Random();

    static final int REQUEST_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchListener = new TouchListener(this);
        myCanvas.setOnTouchListener(touchListener);
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

    public void onLongPress() {
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicIntent, REQUEST_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnail = (Bitmap) extras.get("data");
            myCanvas.setBackground(new BitmapDrawable(getResources(), thumbnail));
        }
    }
}