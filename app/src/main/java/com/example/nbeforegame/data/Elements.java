package com.example.nbeforegame.data;

public class Elements {
    private int number;
    private int figure;
    private int color;

    public Elements(int number, int figure, int color) {
        this.number = number;
        this.figure = figure;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public int getFigure() {
        return figure;
    }

    public int getColor() {
        return color;
    }

}
