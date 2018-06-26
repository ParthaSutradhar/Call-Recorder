package com.xemosoft.callrecorder.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecorderService extends Service {

    private static final String TAG = RecorderService.class.getSimpleName();
    private MediaRecorder mRecorder;
    private File root = Environment.getExternalStorageDirectory();
    private File dir = null;
    private static String savedNumber = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String number = intent.getStringExtra("number");
        savedNumber = number;
        dir = new File(root, "Call Recorder");
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            Log.d(TAG,savedNumber + dir.getAbsolutePath());
            mRecorder.setOutputFile(dir.getAbsolutePath()+"/"+ savedNumber +" "+ getTime()+ ".3gp");
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    public String getTime() {
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh-mm-ss a");
        return dateFormat.format(cal1.getTime());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mRecorder.stop();
        mRecorder.release();
        super.onDestroy();
    }
}
