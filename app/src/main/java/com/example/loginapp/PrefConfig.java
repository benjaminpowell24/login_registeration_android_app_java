package com.example.loginapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


class prefConfig
{
    private  SharedPreferences sharedPreferences;
    private Context context;
    public prefConfig( Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.pref_file),Context.MODE_PRIVATE);
    }
    public void writeLoginstatus(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status),status);
        editor.commit();
    }

    public boolean readLoginstatus()
    {
        return sharedPreferences.getBoolean(
                context.getString(R.string.pref_login_status),false);
    }

    public void writeNmae(String name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name),name);
        editor.commit();
    }

    public String readName()
    {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name),"User");
    }

    public void displayToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

