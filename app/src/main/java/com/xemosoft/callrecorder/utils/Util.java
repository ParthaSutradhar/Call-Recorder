package com.xemosoft.callrecorder.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xemosoft.callrecorder.R;

public class Util {


    public static boolean setRecorderState(boolean state, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.recorder_state),context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.state),state);
        return editor.commit();
    }

    public static boolean getRecorderState(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.recorder_state),context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.state),false);
    }


    public static boolean checkForPermission(){
        return false;
    }

}
