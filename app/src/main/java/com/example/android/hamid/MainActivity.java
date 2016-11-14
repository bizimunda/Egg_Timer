package com.example.android.hamid;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private SeekBar timerSeekbar;
    private TextView timerTextview;
    boolean counterIsActive = false;
    private Button controllerButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton=(Button)findViewById(R.id.controllerButton);

        timerSeekbar = (SeekBar) findViewById(R.id.timerSeekbar);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerTextview = (TextView) findViewById(R.id.timerTextview);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void controlTimer(View view) {

        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer=new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);


                }

                @Override
                public void onFinish() {

                    resetTimer();
                    timerTextview.setText("0:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    mediaPlayer.start();

                }
            }.start();
        } else {
            resetTimer();

        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondsString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondsString = "0" + secondsString;
        }

        timerTextview.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    public void resetTimer(){
        timerTextview.setText("0:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterIsActive=false;
    }
}
