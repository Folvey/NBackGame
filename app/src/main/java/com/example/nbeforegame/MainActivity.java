package com.example.nbeforegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Elements> elementsArrayList = new ArrayList<>();
    private int stepBack = 1;
    private CardView cardViewElement1;
    private CardView cardViewElement2;
    private CardView cardViewElement3;
    private Button buttonAnswer1;
    private Button buttonAnswer2;
    private Button buttonAnswer3;
    private Button buttonAnswer0;
    private ImageView imageViewElement2;
    private ImageView imageViewLife1;
    private ImageView imageViewLife2;
    private ImageView imageViewLife3;
    private TextView textViewElement3;
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
        imageViewElement2 = findViewById(R.id.imageViewElement2);
        imageViewLife1 = findViewById(R.id.imageViewLife1);
        imageViewLife2 = findViewById(R.id.imageViewLife2);
        imageViewLife3 = findViewById(R.id.imageViewLife3);
        textViewElement3 = findViewById(R.id.textViewElement3);
        textViewAnswerResult = findViewById(R.id.textViewAnswerResult);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStepBack = findViewById(R.id.textViewStepBack);
        textViewStepBackNum = findViewById(R.id.textViewStepBackNum);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setProgress(100);
        addElements();
    }

    private void addElements() {
        Elements elements = createElements();
        elementsArrayList.add(elements);
        int color = elements.getColor();
        cardViewElement1.setCardBackgroundColor(color);
        imageViewElement2.setImageResource(elements.getFigure());
        String number = Integer.toString(elements.getNumber());
        textViewElement3.setText(number);
    }

    private void setColorToElement() {
        int i = getResources().getColor(R.color.colorAccent);
        cardViewElement1.setCardBackgroundColor(i);
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
            color = getResources().getColor(R.color.colorPrimaryDark);
        else if (colorRand == 2)
            color = getResources().getColor(R.color.colorYellow);
        else
            color = getResources().getColor(R.color.colorGreen);
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
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        else
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
        addElements();
    }
    public void onClickButtonAnswer2(View view) {
        textViewAnswerResult.setText("2");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 2, stepBack)) {
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        else
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
        addElements();
    }
    public void onClickButtonAnswer3(View view) {
        textViewAnswerResult.setText("3");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 3, stepBack))
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
        else
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
        addElements();
    }
    public void onClickButtonAnswer0(View view) {
        textViewAnswerResult.setText("0");
        Elements elements = elementsArrayList.get(elementsArrayList.size() - 1);
        if (compareElements(elements, 0, stepBack))
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorGreen));
        else
            textViewAnswerResult.setTextColor(getResources().getColor(R.color.colorRed));
        addElements();
    }
}
