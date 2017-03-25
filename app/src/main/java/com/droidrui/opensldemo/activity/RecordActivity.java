package com.droidrui.opensldemo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.droidrui.opensldemo.R;
import com.droidrui.opensldemo.jni.AudioJNI;

import java.io.File;

public class RecordActivity extends AppCompatActivity {

    private Button mBtn;
    private int mState;
    private AudioJNI mAudioJNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initView();
        mAudioJNI = new AudioJNI();
        File file = new File(Environment.getExternalStorageDirectory(), "record.pcm");
        mAudioJNI.initRecord(44100, 1, 1024, file.getAbsolutePath());
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState();
            }
        });
    }

    private void setState() {
        switch (mState) {
            case 0:
                mState = 1;
                mBtn.setText("停止");
                mAudioJNI.startRecord();
                break;
            case 1:
                mState = 0;
                mBtn.setText("录音");
                mAudioJNI.stopRecord();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mState == 1) {
            mState = 0;
            mAudioJNI.stopRecord();
        }
    }
}
