package com.example.loginapp;

import android.content.Context;
import android.os.Bundle;

import com.example.loginapp.WelcomeFragment.OnLogoutListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, OnLogoutListener
{
    public static com.example.loginapp.prefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefConfig = new prefConfig ( this);
        apiInterface = ApiClient.getApiClient ().create ( ApiInterface.class );
        if(findViewById(R.id.fragment_container)!=null)
        {

            if(savedInstanceState!=null)
            {
                return;
            }

            if (prefConfig.readLoginstatus())
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new WelcomeFragment ()).commit ();
            }
                 else
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFragment ()).commit ();
            }
        }

    }

    @Override
    public void performRegister () {

        getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,
                new RegistrationFragment ()).addToBackStack ( null ).commit ();
    }

    @Override
    public void performLogin ( String name )
    {

        prefConfig.writeNmae ( name );
        getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,new WelcomeFragment () ).commit ();
    }

    @Override
    public void logoutperformed () {
        prefConfig.writeLoginstatus ( false );
        prefConfig.writeNmae ( "User" );
        getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,new LoginFragment () ).commit ();
    }
}
