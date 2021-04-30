package com.example.homework5;

import android.graphics.Path;

public class Stroke {

    private int color;
    private Path path;

    public Stroke(int color, Path path) {
        this.color = color;
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public Path getPath() {
        return path;
    }
}
