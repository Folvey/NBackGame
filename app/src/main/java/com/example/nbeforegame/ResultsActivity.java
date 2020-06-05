package com.example.nbeforegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView textViewNewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textViewNewScore = findViewById(R.id.textViewNewScore);
        Intent intent = getIntent();
        String scoreResult = Integer.toString(intent.getIntExtra("score", 0));
        Log.d("ResultActivity", scoreResult);
        textViewNewScore.setText(scoreResult);
    }
}
