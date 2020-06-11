package com.example.nbeforegame.data;

import android.app.Application;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;


public class MainViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link AndroidViewModel}
     */
    public MainViewModelFactory(@NonNull Application application) {
        super(application);
    }
}
