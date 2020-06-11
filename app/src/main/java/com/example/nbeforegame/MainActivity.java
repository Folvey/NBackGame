package com.example.nbeforegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
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

import com.example.nbeforegame.data.Elements;
import com.example.nbeforegame.data.MainViewModel;
import com.example.nbeforegame.data.MainViewModelFactory;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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
    private ProgressBar progressBar;
    private ConstraintLayout rootLayout;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        init();
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
        final MutableLiveData<Integer> timeLiveData = viewModel.getLiveDataTime();
        timeLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                progressBar.setProgress(timeLiveData.getValue());
            }
        });
    }

    private void init() {
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
        progressBar = findViewById(R.id.progressBar);
        rootLayout = findViewById(R.id.mainLayout);
        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this.getApplication())).get(MainViewModel.class);
        progressBar.setProgress(100);
    }

    private void clearElementContainers() {
        Animation appear = AnimationUtils.loadAnimation(this, R.anim.appear);
        cardViewElement1.removeAllViewsInLayout();
        cardViewElement1.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement2.removeAllViewsInLayout();
        cardViewElement2.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement3.removeAllViewsInLayout();
        cardViewElement3.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardViewElement1.setAnimation(appear);
        cardViewElement2.setAnimation(appear);
        cardViewElement3.setAnimation(appear);
    }

    private TextView createTextViewNumber(int number) {
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
                int square = R.drawable.square_glyph;
                ImageView figure = new ImageView(this);
                figure.setImageResource(elements.getFigure());
                CardView.LayoutParams layoutParams = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (elements.getFigure() == square)
                    layoutParams.setMargins(12, 12,12,12);
                else
                    layoutParams.setMargins(10, 10,10,10);
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
        final Drawable whiteToGreen = getDrawable(R.drawable.transition_wtg_button);
        final Drawable whiteToRed = getDrawable(R.drawable.transition_wtr_button);
        TransitionDrawable transitionDrawable;
        if (viewModel.compareElements(1)) {
            buttonAnswer1.setBackground(whiteToGreen);
            transitionDrawable = (TransitionDrawable) buttonAnswer1.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        } else {
            viewModel.lives--;
            setLifeImages(viewModel.lives);
            if (viewModel.lives <= 0) {
                viewModel.stopGame();
            }
            buttonAnswer1.setBackground(whiteToRed);
            transitionDrawable = (TransitionDrawable) buttonAnswer1.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        }
        viewModel.addElements();
    }

    public void onClickButtonAnswer2(View view) {
        final Drawable whiteToGreen = getDrawable(R.drawable.transition_wtg_button);
        final Drawable whiteToRed = getDrawable(R.drawable.transition_wtr_button);
        TransitionDrawable transitionDrawable;
        if (viewModel.compareElements(2)) {
            buttonAnswer2.setBackground(whiteToGreen);
            transitionDrawable = (TransitionDrawable) buttonAnswer2.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        } else {
            viewModel.lives--;
            setLifeImages(viewModel.lives);
            if (viewModel.lives <= 0) {
                viewModel.stopGame();
            }
            buttonAnswer2.setBackground(whiteToRed);
            transitionDrawable = (TransitionDrawable) buttonAnswer2.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        }
        viewModel.addElements();
    }

    public void onClickButtonAnswer3(View view) {
        final Drawable whiteToGreen = getDrawable(R.drawable.transition_wtg_button);
        final Drawable whiteToRed = getDrawable(R.drawable.transition_wtr_button);
        TransitionDrawable transitionDrawable;
        if (viewModel.compareElements(3)) {
            buttonAnswer3.setBackground(whiteToGreen);
            transitionDrawable = (TransitionDrawable) buttonAnswer3.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        } else {
            viewModel.lives--;
            setLifeImages(viewModel.lives);
            if (viewModel.lives <= 0) {
                viewModel.stopGame();
            }
            buttonAnswer3.setBackground(whiteToRed);
            transitionDrawable = (TransitionDrawable) buttonAnswer3.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        }
        viewModel.addElements();
    }

    public void onClickButtonAnswer0(View view) {
        final Drawable whiteToGreen = getDrawable(R.drawable.transition_wtg_button);
        final Drawable whiteToRed = getDrawable(R.drawable.transition_wtr_button);
        TransitionDrawable transitionDrawable;
        if (viewModel.compareElements(0)) {
            buttonAnswer0.setBackground(whiteToGreen);
            transitionDrawable = (TransitionDrawable) buttonAnswer0.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        } else {
            viewModel.lives--;
            setLifeImages(viewModel.lives);
            if (viewModel.lives <= 0) {
                viewModel.stopGame();
            }
            buttonAnswer0.setBackground(whiteToRed);
            transitionDrawable = (TransitionDrawable) buttonAnswer0.getBackground();
            transitionDrawable.startTransition(600);
            transitionDrawable.reverseTransition(600);
        }
        viewModel.addElements();
    }

    private void setLifeImages(Integer lives) {
        if (lives == 3) {
            imageViewLife1.setVisibility(View.VISIBLE);
            imageViewLife2.setVisibility(View.VISIBLE);
            imageViewLife3.setVisibility(View.VISIBLE);
        } else if (lives == 2) {
            imageViewLife3.setVisibility(View.INVISIBLE);
        } else if (lives == 1) {
            imageViewLife3.setVisibility(View.INVISIBLE);
            imageViewLife2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
