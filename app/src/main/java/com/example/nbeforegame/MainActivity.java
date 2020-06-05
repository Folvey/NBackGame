package com.example.nbeforegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nbeforegame.data.Elements;
import com.example.nbeforegame.data.MainViewModel;
import com.example.nbeforegame.data.MainViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

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
        textViewScore = findViewById(R.id.textViewScore);
        textViewStepBack = findViewById(R.id.textViewStepBack);
        textViewStepBackNum = findViewById(R.id.textViewStepBackNum);
        progressBar = findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this.getApplication())).get(MainViewModel.class);
        MutableLiveData<Elements> elementsLiveData = viewModel.getElements();
        elementsLiveData.observe(this, new Observer<Elements>() {
            @Override
            public void onChanged(Elements elements) {
                clearElementContainers();
                setAndShuffleElements(elements);
            }
        });
        MutableLiveData<Integer> scoreLiveData =  viewModel.getScore();
        scoreLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String score = Integer.toString(integer);
                textViewScore.setText(score);
            }
        });
        progressBar.setProgress(100);
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
            int score = viewModel.getScore().getValue();
            viewModel.setLiveDataScore(0);
            Toast.makeText(MainActivity.this, "ВСЕ ДОИГРАЛСЯ", Toast.LENGTH_LONG).show();
            Intent startResultActivity = new Intent(MainActivity.this, ResultsActivity.class);
            Log.d("MainActivity", "score " + Integer.toString(score));
            startResultActivity.putExtra("score", score);
            startActivity(startResultActivity);
        }
    }

    private void clearElementContainers() {
        cardViewElement1.removeAllViewsInLayout();
        cardViewElement1.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement2.removeAllViewsInLayout();
        cardViewElement2.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement3.removeAllViewsInLayout();
        cardViewElement3.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    public TextView createTextViewNumber(int number) {
        TextView elemNum = new TextView(this);
        String num = Integer.toString(number);
        elemNum.setText(num);
        elemNum.setTextSize(36);
        elemNum.setGravity(Gravity.CENTER);
        return elemNum;
    }

    private void setAndShuffleElements(Elements elements) {
        Random rand = new Random();
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

    public void onClickButtonAnswer1(View view) {
        if (viewModel.compareElements(1)) {
            buttonAnswer1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        } else {
            buttonAnswer1.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
        Animation animationFading = AnimationUtils.loadAnimation(this, R.anim.fade);
        buttonAnswer1.startAnimation(animationFading);
        viewModel.addElements();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(200);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonAnswer1.setBackgroundColor(Color.parseColor("#FFFFFF")); // use whatever other color you want here
                    }
                });
            }
        }).start();
    }

    public void onClickButtonAnswer2(View view) {
        if (viewModel.compareElements(2)) {
            buttonAnswer2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        } else {
            buttonAnswer2.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
        Animation animationFading = AnimationUtils.loadAnimation(this, R.anim.fade);
        buttonAnswer2.startAnimation(animationFading);
        viewModel.addElements();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(200);
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
        if (viewModel.compareElements(3)) {
            buttonAnswer3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        } else {
            buttonAnswer3.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
        viewModel.addElements();
        Animation animationFading = AnimationUtils.loadAnimation(this, R.anim.fade);
        buttonAnswer3.startAnimation(animationFading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(200);
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
        if (viewModel.compareElements(0)) {
            buttonAnswer0.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        } else {
            buttonAnswer0.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
        viewModel.addElements();
        Animation animationFading = AnimationUtils.loadAnimation(this, R.anim.fade);
        buttonAnswer0.startAnimation(animationFading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(200);
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
