package com.example.nbeforegame.data;

import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nbeforegame.R;

import java.util.ArrayList;
import java.util.Random;

public class MainModel {

    private ArrayList<Elements> elementsArrayList = new ArrayList<>();
    private int stepBack = 1;
    Application application;

    public MainModel(Application application) {
        this.application = application;
    }

    //neeed to rise chance of 3x match of elements
    private Elements createElements() {
        return new Elements(randNumber(), randFigure(), randColor());
    }

    private int randNumber() {
        return new Random().nextInt(3) + 1;
    }

    private int randFigure() {
        Random random = new Random();
        int i = random.nextInt(3) + 1;
        int figure;
        if (i == 1)
            figure = R.drawable.triangle_tight;
        else if (i == 2)
            figure = R.drawable.circle;
        else
            figure = R.drawable.square_tight2;
        return figure;
    }

    private int randColor() {
        Random rand = new Random();
        int color;
        int colorRand = rand.nextInt(3) + 1;
        if (colorRand == 1)
            color = application.getResources().getColor(R.color.colorPrimaryDark);
        else if (colorRand == 2)
            color = application.getResources().getColor(R.color.colorYellow);
        else
            color = application.getResources().getColor(R.color.colorGreen);
        return color;
    }

    private boolean compareElements(Elements elementsToCompare, int answer, int stepBack) {
        int i = elementsArrayList.size() - 1 - stepBack;
        int matches = 0;
        if (i < 0) {
            return answer == 0;
        }
        Elements elements = elementsArrayList.get(i);
        if (elementsToCompare.getColor() == elements.getColor()) matches++;
        if (elementsToCompare.getFigure() == elements.getFigure()) matches++;
        if (elementsToCompare.getNumber() == elements.getNumber()) matches++;
        return answer == matches;
    }

}
