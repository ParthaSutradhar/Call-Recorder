package com.xemosoft.callrecorder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.xemosoft.callrecorder.service.RecorderService;
import com.xemosoft.callrecorder.utils.Util;

import java.util.Date;

public class PhoneReceiver extends BroadcastReceiver {


    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static final String TAG = PhoneReceiver.class.getSimpleName();
    private String savedNumber = null;
    private boolean isIncoming = false;
    private Intent serviceIntent = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        serviceIntent = new Intent(context,RecorderService.class);

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            savedNumber = intent.getExtras().getString(Intent.EXTRA_PHONE_NUMBER);
            Log.d(TAG,savedNumber);
        }else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            assert stateStr != null;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            if (savedNumber !=null){
                Log.d(TAG,savedNumber);
            }
            onCallStateChanged(context,number,state);
        }
    }

    private void onCallStateChanged(Context context, String number, int state) {
        if (lastState == state){
            return;
        }
        switch (state){
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG,"Call State Is IDLE");
                stopRecorderService(context);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG,"Call State Is ANSWERED");
                // Start A Recording Service
                isIncoming = true;
                startRecorderService(context,number);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG,"Call State Is Ringing");
                break;
        }
        lastState = state;
    }

    private void stopRecorderService(Context context) {
        if (Util.getRecorderState(context)){
            context.stopService(serviceIntent);
        }
    }


    private void startRecorderService(Context context, String number) {
        serviceIntent.putExtra("number",number);
        if (Util.getRecorderState(context)){
            context.startService(serviceIntent);
        }else{
            Toast.makeText(context, "Turn On Recorder", Toast.LENGTH_SHORT).show();

        }
    }
}
