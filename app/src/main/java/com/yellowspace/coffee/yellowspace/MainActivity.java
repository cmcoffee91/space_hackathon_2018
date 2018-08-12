package com.yellowspace.coffee.yellowspace;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
implements RegisterFragment.OnFragmentInteractionListener,
        StartFragment.OnFragmentInteractionListener,
        SignInFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = (View) findViewById(R.id.fragment_container);
        v.setBackgroundColor(Color.GREEN);


        Fragment fragment = null;
        Class fragmentClass = StartFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();


    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
