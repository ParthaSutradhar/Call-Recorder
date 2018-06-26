package com.xemosoft.callrecorder.service;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xemosoft.callrecorder.database.AppDatabase;
import com.xemosoft.callrecorder.database.Call;
import com.xemosoft.callrecorder.utils.CryptoUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecorderService extends Service {

    private static final String TAG = RecorderService.class.getSimpleName();
    private MediaRecorder mRecorder;
    private File root = Environment.getExternalStorageDirectory();
    private File dir = null;
    private static String savedNumber = null;
    private long start,end;


    @Override
    public void onCreate() {
        super.onCreate();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        start = System.currentTimeMillis();

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
            Log.d(TAG, savedNumber + dir.getAbsolutePath());
            mRecorder.setOutputFile(dir.getAbsolutePath() + "/" + CryptoUtil.MD5(savedNumber + getTime()) + ".3gp");
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh-mm-ss");
        return dateFormat.format(cal1.getTime());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        saveToDatabase();
        mRecorder.stop();
        mRecorder.release();
        super.onDestroy();
    }

    private void saveToDatabase() {


        end = System.currentTimeMillis();

        Call call = new Call();
        call.setNumber(savedNumber);
        call.setDate(getTime());
        call.setFileName(CryptoUtil.MD5(savedNumber+getTime()));
        call.setTalkTime(String.valueOf(end-start));

        AppDatabase appDatabase = Room.databaseBuilder(this,AppDatabase.class,"appdeve").allowMainThreadQueries().build();
        appDatabase.getCallDao().insert(call);
    }
}
