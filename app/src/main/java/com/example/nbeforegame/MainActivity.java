package com.example.nbeforegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Elements> elementsArrayList = new ArrayList<>();

    CardView cardViewElement1;
    CardView cardViewElement2;
    CardView cardViewElement3;
    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer0;
    ImageView imageViewElement2;
    ImageView imageViewLife1;
    ImageView imageViewLife2;
    ImageView imageViewLife3;
    TextView textViewElement3;
    TextView textViewAnswerResult;
    TextView textViewScore;
    TextView textViewStepBack;
    TextView textViewStepBackNum;
    ProgressBar progressBar;

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
        Elements elements = new Elements(1, R.drawable.triangle, R.color.colorAccent);
        setElements(elements);
    }

    public void setElements(Elements elements) {
        int i = getResources().getColor(R.color.colorAccent);
        cardViewElement1.setCardBackgroundColor(i);
        imageViewElement2.setImageResource(elements.getFigure());
        String number = Integer.toString(elements.getNumber());
        textViewElement3.setText(number);
    }
}
