package com.example.womensafety.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

public class Preference {
    public static final String TAG=Preference.class.getSimpleName();
    public static final String Number1="Number1";
    public static final String Number2="Number2";
    public static  final String Email="Email";
    public static final String Message="Meassage";

    public static void Storedata(Context context, String PhoneNumber1,String PhoneNumber2,String email,String message)
    {
        Log.d(TAG,"Data Saved");
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(Number1,PhoneNumber1);
        editor.putString(Number2,PhoneNumber2);
        editor.putString(Email,email);
        editor.putString(Message,message);
        editor.apply();
    }
}
