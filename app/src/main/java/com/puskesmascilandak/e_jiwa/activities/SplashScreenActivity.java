package com.puskesmascilandak.e_jiwa.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.puskesmascilandak.e_jiwa.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progress_bar);
        updateProgress();
    }

    private void updateProgress() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentProgress = progressBar.getProgress();
                progressBar.setProgress(currentProgress + 10);

                if (currentProgress < 100) {
                    updateProgress();
                } else {
                    startLoginActivity();
                }
            }
        }, 300);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
