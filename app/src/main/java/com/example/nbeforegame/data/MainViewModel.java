package com.example.nbeforegame.data;

import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nbeforegame.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainViewModel extends AndroidViewModel {

    private Application application;
    private MutableLiveData<Elements> liveDataElements = new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataScore = new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataProgress = new MutableLiveData<>();
    private ArrayList<Elements> elementsArrayList = new ArrayList<>();
    private int stepBack = 1;
    private int score = 0;
    private double scoreMultiplier = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        liveDataScore.setValue(0);
        liveDataProgress.setValue(100);
        addElements();
    }

    public MutableLiveData<Elements> getElements() {
        return liveDataElements;
    }

    public MutableLiveData<Integer> getScore() {
        return liveDataScore;
    }

    public void setLiveDataScore(int score) {
        liveDataScore.setValue(score);
    }

    public MutableLiveData<Integer> getProgress() {
        return liveDataProgress;
    }

    public void setLiveDataProgress(MutableLiveData<Integer> liveDataProgress) {
        this.liveDataProgress = liveDataProgress;
    }

    public void addElements() {
        Elements elements = createElements();
        elementsArrayList.add(elements);
        liveDataElements.setValue(elements);

        //shuffleContainers(elements); ПОКА ЧТО обработать внутри мейн активити
    }

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

    public boolean compareElements(int answer) {
        int i = elementsArrayList.size() - 1 - stepBack;
        int matches = 0;
        if (i < 0) {
            return answer == 0;
        }
        Elements elements = elementsArrayList.get(i);
        Elements elementsToCompare = liveDataElements.getValue();
        if (elementsToCompare.getColor() == elements.getColor()) matches++;
        if (elementsToCompare.getFigure() == elements.getFigure()) matches++;
        if (elementsToCompare.getNumber() == elements.getNumber()) matches++;
        if (answer == matches) {
            scoreMultiplier += 0.5;
            score += 1 * scoreMultiplier;
            liveDataScore.setValue(score);
        } else
            scoreMultiplier = 1;
        return answer == matches;
    }
}
