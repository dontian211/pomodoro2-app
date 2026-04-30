package com.example.pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TextView timerText;
    private TextView statusText;
    private Button startButton;
    private Button resetButton;
    private CountDownTimer timer;
    private boolean isRunning = false;
    private long timeLeft = 25 * 60 * 1000;
    private static final long WORK_TIME = 25 * 60 * 1000;
    private static final long BREAK_TIME = 5 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timerText);
        statusText = findViewById(R.id.statusText);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> {
            if (isRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        resetButton.setOnClickListener(v -> resetTimer());
        updateTimerText();
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                startButton.setText("开始");
                statusText.setText("休息一下！");
                timeLeft = BREAK_TIME;
                updateTimerText();
            }
        }.start();
        isRunning = true;
        startButton.setText("暂停");
        statusText.setText("专注中...");
    }

    private void pauseTimer() {
        timer.cancel();
        isRunning = false;
        startButton.setText("继续");
    }

    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        isRunning = false;
        timeLeft = WORK_TIME;
        startButton.setText("开始");
        statusText.setText("准备开始");
        updateTimerText();
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
