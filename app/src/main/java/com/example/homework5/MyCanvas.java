package com.example.homework5;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCanvas extends View {
    HashMap <Integer, Stroke> activePaths;
    ArrayList<Stroke> completedPaths = new ArrayList<>();

    // ArrayLists for x and y coordinates of icons
    ArrayList<Float> xCoordinates = new ArrayList<>();
    ArrayList<Float> yCoordinates = new ArrayList<>();
    ArrayList<Bitmap> icons = new ArrayList<>();

    ArrayList<AddedItemType> addedItems = new ArrayList<>();

    Paint pathPaint;
    int currColor;

    enum AddedItemType {
        PATH,
        ICON
    }

    public MyCanvas(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        activePaths = new HashMap<>();

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.RED);
        currColor = Color.RED;
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Stroke completedStroke: completedPaths) {
            pathPaint.setColor(completedStroke.getColor());
            canvas.drawPath(completedStroke.getPath(), pathPaint);
        }

        for (Stroke stroke: activePaths.values()) {
            pathPaint.setColor(stroke.getColor());
            canvas.drawPath(stroke.getPath(), pathPaint);
        }

        // loop through x and y ArrayLists
        for (int i = 0; i < icons.size(); i++)
        {
            float x = xCoordinates.get(i).floatValue() - (icons.get(i).getWidth() / 2);
            float y = yCoordinates.get(i).floatValue() - (icons.get(i).getHeight() / 2);
            canvas.drawBitmap(icons.get(i), x, y, pathPaint);
        }

        super.onDraw(canvas);
    }

    public void addPath(int id, float x, float y) {
        Path path = new Path();
        Stroke stroke = new Stroke(currColor, path);
        path.moveTo(x, y);
        Log.d("MyCanvas", "x: " + x + " y: " + y);
        activePaths.put(id, stroke);
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        Stroke stroke = activePaths.get(id);
        if (stroke != null) {
            stroke.getPath().lineTo(x, y);
        }
        invalidate();
    }

    public void removePath(int id) {
        if (activePaths.containsKey(id)) {
            Stroke stroke = activePaths.remove(id);
            completedPaths.add(stroke);
            addedItems.add(AddedItemType.PATH);
        }
        invalidate();
    }

    public void changeColor(int color) {
        pathPaint.setColor(color);
        currColor = color;
    }

    public void clearCanvas() {
        completedPaths.clear();
        activePaths.clear();
        xCoordinates.clear();
        yCoordinates.clear();
        icons.clear();
        addedItems.clear();
        invalidate();
    }

    public void undoPaint() {
        if (addedItems.size() > 0)
        {
            AddedItemType addedItem = addedItems.remove(addedItems.size() - 1);

            if (completedPaths.size() != 0 && addedItem == AddedItemType.PATH) {
                completedPaths.remove(completedPaths.size() - 1);
                invalidate();
            }
            else if (icons.size() != 0 && addedItem == AddedItemType.ICON) {
                icons.remove(icons.size() - 1);
                xCoordinates.remove(xCoordinates.size() - 1);
                yCoordinates.remove(yCoordinates.size() - 1);
                invalidate();
            }
        }
    }

    public void addIcon(float x, float y) {
        // store x and y in separate ArrayLists
        // add point
        xCoordinates.add(x);
        yCoordinates.add(y);
        Resources res = getResources();
        Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.hokie_pic);
        icon = Bitmap.createScaledBitmap(icon,  150, 150, true);
        icons.add(icon);
        addedItems.add(AddedItemType.ICON);
        invalidate();
    }

    public void addIcon2(float x, float y) {
        xCoordinates.add(x);
        yCoordinates.add(y);
        Resources res = getResources();
        Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.hokie_feet);
        icon = Bitmap.createScaledBitmap(icon, 175, 175, true);
        icons.add(icon);
        addedItems.add(AddedItemType.ICON);
        invalidate();
    }
}
