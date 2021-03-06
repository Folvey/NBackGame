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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        init();
        getScore();
    }

    public void onClickStartAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void init() {
        textViewNewScore = findViewById(R.id.textViewNewScore);
        textViewBestScore = findViewById(R.id.textViewBestScore);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void gradientAnimation() {
        ConstraintLayout rootLayout = findViewById(R.id.rootResultLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_background__animation);
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(5000);
        rootLayout.setBackground(animationDrawable);
        animationDrawable.start();
    }

    private void getScore() {
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
}
