package com.junhuayu.countdowntimerbutton;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by junhua.yu on 2018/5/9.
 */

public class CountDownTimerButton extends AppCompatButton {


    private Timer timer;

    private TimerTask timerTask;

    private String beforeText;

    private String countDownText;

    private float time = 60;



    public CountDownTimerButton(Context context) {
        this(context,null);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.countDownTimerButton);
        beforeText = a.getString(R.styleable.countDownTimerButton_beforeText);
        countDownText = a.getString(R.styleable.countDownTimerButton_countTimeText);
        time = a.getFloat(R.styleable.countDownTimerButton_time,60);
        a.recycle();
        initTime();
        initView();
    }

    private void initView() {
        this.setText(beforeText);
    }

    private void initTime() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountDownTimerButton.this.setText(time+countDownText);
            time--;
            if (time < 0){
                CountDownTimerButton.this.setText(beforeText);
                CountDownTimerButton.this.setClickable(true);
                clearTimer();
            }
        }
    };

    public void start(){
        timer.schedule(timerTask,0,1000);
        this.setClickable(false);
    }

    private void clearTimer(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        if (timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        clearTimer();
        super.onDetachedFromWindow();
    }
}
