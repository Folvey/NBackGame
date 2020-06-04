package com.example.nbeforegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nbeforegame.data.Elements;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Elements> elementsArrayList = new ArrayList<>();
    private int score = 0;
    private double scoreMultiplier = 1;
    private int stepBack = 1;
    int progress = 100;

    private CardView cardViewElement1;
    private CardView cardViewElement2;
    private CardView cardViewElement3;
    private Button buttonAnswer1;
    private Button buttonAnswer2;
    private Button buttonAnswer3;
    private Button buttonAnswer0;
    private ImageView imageViewLife1;
    private ImageView imageViewLife2;
    private ImageView imageViewLife3;
    private TextView textViewAnswerResult;
    private TextView textViewScore;
    private TextView textViewStepBack;
    private TextView textViewStepBackNum;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        cardViewElement1 = findViewById(R.id.cardViewElement1);
        cardViewElement2 = findViewById(R.id.cardViewElement2);
        cardViewElement3 = findViewById(R.id.cardViewElement3);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer0 = findViewById(R.id.buttonAnswer0);
        imageViewLife1 = findViewById(R.id.imageViewLife1);
        imageViewLife2 = findViewById(R.id.imageViewLife2);
        imageViewLife3 = findViewById(R.id.imageViewLife3);
        textViewAnswerResult = findViewById(R.id.textViewAnswerResult);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStepBack = findViewById(R.id.textViewStepBack);
        textViewStepBackNum = findViewById(R.id.textViewStepBackNum);
        progressBar = findViewById(R.id.progressBar);


        progressBar.setProgress(progress);
        textViewScore.setText(Integer.toString(score));
        addElements();
        new Timer(20000, 200).start();

    }

    class Timer extends android.os.CountDownTimer {

        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progressBar.incrementProgressBy(-1);
        }

        @Override
        public void onFinish() {
            Toast.makeText(MainActivity.this, "ВСЕ ДОИГРАЛСЯ", Toast.LENGTH_LONG).show();
            score = 0;
            progress = 100;
            progressBar.setProgress(progress);
            textViewScore.setText(Integer.toString(score));
        }
    }

    private void addElements() {
        Elements elements = createElements();
        elementsArrayList.add(elements);
        clearElementContainers();
        shuffleContainers(elements);
    }

    private void clearElementContainers() {
        cardViewElement1.removeAllViewsInLayout();
        cardViewElement1.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement2.removeAllViewsInLayout();
        cardViewElement2.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement3.removeAllViewsInLayout();
        cardViewElement3.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    private TextView createTextViewNumber(int number) {
        TextView elemNum = new TextView(this);
        String num = Integer.toString(number);
        elemNum.setText(num);
        elemNum.setTextSize(36);
        elemNum.setGravity(Gravity.CENTER);
        return elemNum;
    }

    private void shuffleContainers(Elements elements) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        Random rand = new Random();
        int container = 0;
        while (container < 3) {
            int i = rand.nextInt(list.size());
            int element = list.get(i);
            Log.i("dbg", String.valueOf(element));
            if (element == 0) {
                //set figure
                ImageView figure = new ImageView(this);
                figure.setImageResource(elements.getFigure());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (container == 1)
                    cardViewElement1.addView(figure, layoutParams);
                else if (container == 2)
                    cardViewElement2.addView(figure, layoutParams);
                else
                    cardViewElement3.addView(figure, layoutParams);
            } else if (element == 1) {
                //set color
                int color = elements.getColor();
                if (container == 1)
                    cardViewElement1.setCardBackgroundColor(color);
                else if (container == 2)
                    cardViewElement2.setCardBackgroundColor(color);
                else
                    cardViewElement3.setCardBackgroundColor(color);
            } else if (element == 2) {
                // set number
                TextView elemNum = createTextViewNumber(elements.getNumber());
                if (container == 1)
                    cardViewElement1.addView(elemNum);
                else if (container == 2)
                    cardViewElement2.addView(elemNum);
                else
                    cardViewElement3.addView(elemNum);
            }
            list.remove(i);
            container++;
        }
    }

    private Elements createElements() {
        Random random = new Random();
        int i = random.nextInt(100) + 1;
        if (!elementsArrayList.isEmpty() && i < 20) {
            return elementsArrayList.get(elementsArrayList.size() - 1);
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
            color = getResources().getColor(R.color.colorPrimary);
        else if (colorRand == 2)
            color = getResources().getColor(R.color.colorYellow);
        else
            color = getResources().getColor(R.color.colorDarkGreen);
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

    public void onClickButtonAnswer1(View view) {
        textViewAnswerResult.setText("1");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 1, stepBack)) {
            buttonAnswer1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
            scoreMultiplier += 0.5;
            score += 1 * scoreMultiplier;
        } else {
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
            buttonAnswer1.setBackgroundColor(getResources().getColor(R.color.colorRed));
            scoreMultiplier = 1;
        }
        textViewScore.setText(String.format("%d", score));
        addElements();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(150);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonAnswer1.setBackgroundColor(Color.parseColor("#FFFFFF")); // use whatever other color you want here
                    }
                });
            }
        }).start();
    }

    private void setButtonColorToWhite(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    public void onClickButtonAnswer2(View view) {
        textViewAnswerResult.setText("2");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 2, stepBack)) {
            buttonAnswer2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
            scoreMultiplier += 0.5;
            score += 1 * scoreMultiplier;
        } else {
            buttonAnswer2.setBackgroundColor(getResources().getColor(R.color.colorRed));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
            scoreMultiplier = 1;
        }
        textViewScore.setText(String.format("%d", score));
        addElements();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(150);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonAnswer2.setBackgroundColor(Color.parseColor("#FFFFFF")); // use whatever other color you want here
                    }
                });
            }
        }).start();
    }

    public void onClickButtonAnswer3(View view) {
        textViewAnswerResult.setText("3");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 3, stepBack)) {
            buttonAnswer3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
            scoreMultiplier += 0.5;
            score += 1 * scoreMultiplier;
        } else {
            buttonAnswer3.setBackgroundColor(getResources().getColor(R.color.colorRed));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
            scoreMultiplier = 1;
        }
        textViewScore.setText(String.format("%d", score));
        addElements();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(150);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonAnswer3.setBackgroundColor(Color.parseColor("#FFFFFF")); // use whatever other color you want here
                    }
                });
            }
        }).start();
    }

    public void onClickButtonAnswer0(View view) {
        textViewAnswerResult.setText("0");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 0, stepBack)) {
            buttonAnswer0.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
            scoreMultiplier += 0.5;
            score += 1 * scoreMultiplier;
        } else {
            buttonAnswer0.setBackgroundColor(getResources().getColor(R.color.colorRed));
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
            scoreMultiplier = 1;
        }
        textViewScore.setText(String.format("%d", score));
        addElements();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(150);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonAnswer0.setBackgroundColor(Color.parseColor("#FFFFFF")); // use whatever other color you want here
                    }
                });
            }
        }).start();
    }
}
