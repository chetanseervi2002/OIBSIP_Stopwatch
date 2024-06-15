package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView time;
    Button start, pause, reset;
    long MillisecondTime,StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.tvTimer);
        start = findViewById(R.id.btStart);
        pause = findViewById(R.id.btPause);
        reset = findViewById(R.id.btReset);

        handler = new Handler();

        start.setOnClickListener(v -> {
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            reset.setEnabled(false);
        });

        pause.setOnClickListener(v -> {
            TimeBuff += MillisecondTime;
            handler.removeCallbacks(runnable);
            reset.setEnabled(true);
        });

        reset.setOnClickListener(v -> {
            MilliSecond = 0;
            MillisecondTime = 0L;
            StartTime = 0L;
            TimeBuff = 0L;
            UpdateTime = 0L;
            Seconds = 0;
            Minutes = 0;

            time.setText("00:00:00");

        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSecond = (int) (UpdateTime % 1000);

            time.setText(String.format("%d:%s:%s", Minutes, String.format("%02d",
                    Seconds), String.format("%02d", MilliSecond)));

            handler.postDelayed(this, 0);
        }
    };
}