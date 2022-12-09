package com.example.loginapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView RegText;
    private EditText Username, Userpassword;
    private Button LoginBn;
    OnLoginFormActivityListener loginFormActivityListener;


    public interface OnLoginFormActivityListener {
        void performRegister ();

        void performLogin ( String name );
    }

    public LoginFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_login , container , false );
        RegText = view.findViewById ( R.id.register_txt );

        Username = view.findViewById ( R.id.user_name );
        Userpassword = view.findViewById ( R.id.user_pass );
        LoginBn = view.findViewById ( R.id.login_bn );


        LoginBn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                performLogin ();
            }
        } );


        RegText.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                loginFormActivityListener.performRegister ( );
            }
        } );
        return view;
    }

    @Override
    public void onAttach ( @NonNull Context context ) {
        super.onAttach ( context );
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;

    }

    private void performLogin()
    {

        String username = Username.getText ().toString ();
        String password = Userpassword.getText ().toString ();

        Call<User> call = MainActivity.apiInterface.performUserLogin ( username,password );
        call.enqueue ( new Callback <User> ( ) {
            @Override
            public void onResponse ( Call <User> call , Response <User> response ) {
                if ( response.body ().getResponse ().equals ( "ok" ) )
                {
                    MainActivity.prefConfig.writeLoginstatus ( true );
                    loginFormActivityListener.performLogin ( response.body ().getName () );
                }
                else if ( response.body ().getResponse ().equals ( "failed" ) )
                {
                    MainActivity.prefConfig.displayToast ( "Login failed...please try again..." );
                }
            }

            @Override
            public void onFailure ( Call <User> call , Throwable t ) {

            }
        } );

        Username.setText ( "" );
        Userpassword.setText ( "" );
    }
}