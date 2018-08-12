package com.yellowspace.coffee.yellowspace;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,
        FeedFragment.OnFragmentInteractionListener,
        ArFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View v = (View) findViewById(R.id.fragment_home);
        v.setBackgroundColor(Color.GREEN);


        Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_home, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
