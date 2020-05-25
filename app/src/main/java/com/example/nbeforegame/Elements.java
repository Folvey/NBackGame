package com.example.nbeforegame;

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

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
