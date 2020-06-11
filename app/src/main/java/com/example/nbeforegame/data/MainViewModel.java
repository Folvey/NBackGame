package com.example.nbeforegame.data;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.nbeforegame.R;
import com.example.nbeforegame.ResultsActivity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MainViewModel extends AndroidViewModel {

    private Application application;
    public SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
    private MutableLiveData<Elements> liveDataElements = new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataScore = new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataTime = new MutableLiveData<>();
    private ArrayList<Elements> elementsArrayList = new ArrayList<>();
    private int stepBack = 1;
    private int score = 0;
    private double scoreMultiplier = 1;
    public int lives = 3;
    public static final String KEY_BEST_SCORE = "bestScore";
    public GameTimer gameTimer = new GameTimer(20000, 200);

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        liveDataScore.setValue(0);
        liveDataTime.setValue(100);
        addElements();
        gameTimer.start();
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

    public MutableLiveData<Integer> getLiveDataTime() {
        return liveDataTime;
    }

    public void addElements() {
        Elements elements = createElements();
        elementsArrayList.add(elements);
        liveDataElements.setValue(elements);

        //shuffleContainers(elements); ПОКА ЧТО обработать внутри мейн активити
    }

    private Elements createElements() {
        Random random = new Random();
        //+ 15% chance to duplicate elements
        int i = random.nextInt(100) + 1;
        if (i < 15) {
            if (!elementsArrayList.isEmpty()) {
                return elementsArrayList.get(elementsArrayList.size() - 1);
            }
        }
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
//            figure = R.drawable.triangle_tight;
            figure = R.drawable.triangle_glyph;
        else if (i == 2)
//            figure = R.drawable.circle;
            figure = R.drawable.rhomb_glyph;
        else
//            figure = R.drawable.square_tight2;
            figure = R.drawable.square_glyph;
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
            color = application.getResources().getColor(R.color.colorGreenMaterial);
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

    public Map<Integer, Object> setAndShuffleElements(Elements elements) {
        Random rand = new Random();
        Map<Integer, Object> elementsMap = new TreeMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        int container = 0;
        while (container < 3) {
            int i = rand.nextInt(list.size());
            int element = list.get(i);
            Log.i("dbg", String.valueOf(element));
            if (element == 0) {
                ImageView figure = new ImageView(getApplication());
                figure.setImageResource(elements.getFigure());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                elementsMap.put(container, figure);
            } else if (element == 1) {
                int color = elements.getColor();
                elementsMap.put(container, color);
            } else if (element == 2) {
                TextView elemNum = createTextViewNumber(elements.getNumber());
                elementsMap.put(container, elemNum);
            }
            list.remove(i);
            container++;
        }
        return elementsMap;
    }

    private TextView createTextViewNumber(int number) {
        TextView elemNum = new TextView(getApplication());
        String num = Integer.toString(number);
        elemNum.setText(num);
        elemNum.setTextSize(36);
        elemNum.setGravity(Gravity.CENTER);
        return elemNum;
    }

    private class GameTimer extends CountDownTimer {

        public GameTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            liveDataTime.setValue(liveDataTime.getValue() - 1);
        }

        @Override
        public void onFinish() {
            int score = liveDataScore.getValue();
            int bestScore = preferences.getInt(MainViewModel.KEY_BEST_SCORE, 0);
            Log.d("MainActivity", "score " + score);
            Log.d("MainActivity", "Best score " + bestScore);
            if (score > bestScore)
                preferences.edit().putInt(MainViewModel.KEY_BEST_SCORE, score).apply();
            setLiveDataScore(0);
            Intent startResultActivity = new Intent(application.getApplicationContext(), ResultsActivity.class);
            startResultActivity.putExtra("score", score);
            application.startActivity(startResultActivity);
        }
    }

    public void stopGame() {
        int score = liveDataScore.getValue();
        int bestScore = preferences.getInt(MainViewModel.KEY_BEST_SCORE, 0);
        Log.d("MainActivity", "score " + score);
        Log.d("MainActivity", "Best score " + bestScore);
        if (score > bestScore)
            preferences.edit().putInt(MainViewModel.KEY_BEST_SCORE, score).apply();
        setLiveDataScore(0);
        Intent startResultActivity = new Intent(application.getApplicationContext(), ResultsActivity.class);
        startResultActivity.putExtra("score", score);
        application.startActivity(startResultActivity);
        gameTimer.cancel();
    }
}
