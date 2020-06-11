package com.example.nbeforegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nbeforegame.data.MainViewModel;

public class ResultsActivity extends AppCompatActivity {

    private TextView textViewNewScore;
    private TextView textViewBestScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ConstraintLayout rootLayout = findViewById(R.id.rootResultLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) rootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        textViewNewScore = findViewById(R.id.textViewNewScore);
        textViewBestScore = findViewById(R.id.textViewBestScore);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bestScore = Integer.toString(sharedPreferences.getInt(MainViewModel.KEY_BEST_SCORE, 0));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("score")) {
            String scoreResult = Integer.toString(intent.getIntExtra("score", 0));
            Log.d("ResultActivity", scoreResult);
            Log.d("ResultActivity", "best score " + bestScore);
            textViewNewScore.setText(scoreResult);
            textViewBestScore.setText(bestScore);
        }
    }

    public void onClickStartAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
