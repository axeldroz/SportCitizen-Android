package com.sportcitizen.sportcitizen.activities;

import android.app.Activity;
//import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.fragments.ChallengeFragment;
import com.sportcitizen.sportcitizen.fragments.FeedFragment;
import com.sportcitizen.sportcitizen.fragments.NotificationsFragment;
import com.sportcitizen.sportcitizen.fragments.ProfileFragment;

import android.support.v4.app.Fragment;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private TextView mTextMessage;

    protected BottomNavigationView navigation;
    private Menu menu;
    private MenuItem menuItem;


    private boolean loadFragment(android.support.v4.app.Fragment fragment) {
        if (fragment == null)
            return (false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
        return (true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Sport Citizen");
        toolbar.setBackgroundColor(Color.parseColor("#d0b5a4"));
        navigation = findViewById(R.id.navigation);
        menu = navigation.getMenu();
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new FeedFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_feed:
                menuItem = menu.getItem(0);
                fragment = new FeedFragment();
                break;

            case R.id.navigation_challenges:
                menuItem = menu.getItem(1);
                fragment = new ChallengeFragment();
                break;

            case R.id.navigation_notifications:
                menuItem = menu.getItem(2);
                fragment = new NotificationsFragment();
                break;

            case R.id.navigation_profile:
                menuItem = menu.getItem(3);
                fragment = new ProfileFragment();
                break;

            default:
                return (false);
        }
        return (loadFragment(fragment));
    }
}
