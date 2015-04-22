package com.deston.AutoRecall;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class HomeActivity extends Activity implements View.OnClickListener {
    private Button mCallBtn;
    private Button mExitBtn;
    private EditText mNumberEt;
    private EditText mRepeatDelayEt;
    private String mNumber;
    private int mDelay;
    private int mRepeatCount;
    private static final String TAG = "HomeActivity";
    public static boolean sIsCallActive;
    private static final String TEL_PREFIX = "tel:";
    private static final String TIME_SCALE = 1000;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
    }

    private void initViews() {
        mCallBtn = (Button) findViewById(R.id.call_btn);
        mExitBtn = (Button) findViewById(R.id.exit_btn);
        mNumberEt = (EditText) findViewById(R.id.number_et);
        mRepeatDelayEt = (EditText) findViewById(R.id.repeat_call_et);
        mCallBtn.setOnClickListener(this);
        mExitBtn.setOnClickListener(this);
    }

    public void call(String number) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse(TEL_PREFIX + number));
        startActivity(intent);
    }


    private class RepeatHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (!sIsCallActive)  {
                call(mNumber);
                Log.i(TAG, "repeat call count : " + ++mRepeatCount);
            }
            sendEmptyMessageDelayed(0, mDelay);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_btn:
                setPageData();
                call(mNumber);
                RepeatHandler handler = new RepeatHandler();
                handler.sendEmptyMessageDelayed(0, mDelay);
                break;
            case R.id.exit_btn:
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
    }

    private void setPageData() {
        mNumber = mNumberEt.getEditableText().toString();
        mDelay = Integer.parseInt(mRepeatDelayEt.getEditableText().toString()) * TIME_SCALE;
    }

}
