package com.sportcitizen.sportcitizen.activities;

//import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

        import com.sportcitizen.sportcitizen.fragments.ChallengeViewFragment;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.fragments.ChallengeFragment;
import com.sportcitizen.sportcitizen.fragments.FeedFragment;
import com.sportcitizen.sportcitizen.fragments.NotificationsFragment;
import com.sportcitizen.sportcitizen.fragments.ProfileFragment;

import android.support.v4.app.Fragment;

        import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigation;
    private Menu menu;
    private MenuItem menuItem;
    private List<MenuItem> _previousItems = new ArrayList<>();

    public boolean loadFragment(android.support.v4.app.Fragment fragment) {
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
        menuItem = menu.getItem(0);
        addItemInStack(menuItem);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new FeedFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_feed:
                menuItem = menu.getItem(0);
                addItemInStack(menuItem);
                fragment = new FeedFragment();
                break;

            case R.id.navigation_challenges:
                menuItem = menu.getItem(1);
                addItemInStack(menuItem);
                fragment = new ChallengeFragment();
                break;

            case R.id.navigation_notifications:
                menuItem = menu.getItem(2);
                addItemInStack(menuItem);
                fragment = new NotificationsFragment();
                break;

            case R.id.navigation_profile:
                menuItem = menu.getItem(3);
                addItemInStack(menuItem);
                fragment = new ProfileFragment();
                break;

            default:
                return (false);
        }
        return (loadFragment(fragment));
    }


    public void addItemInStack(MenuItem item) {
        if (_previousItems.size() > 32)
            _previousItems.remove((int)0);
        _previousItems.add(item);
    }

    public boolean back(@NonNull MenuItem item) {
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

    public boolean back(int id) {
        Fragment fragment = null;

        switch (id) {
            case 0:
                fragment = new FeedFragment();
                break;

            case 1:
                fragment = new ChallengeFragment();
                break;

            case 2:
                fragment = new NotificationsFragment();
                break;

            case 3:
                fragment = new ProfileFragment();
                break;

            case 4:
                fragment = new ChallengeViewFragment();
                break;

            default:
                return (false);
        }
        if (id < 4)
            menuItem = menu.getItem(id);
        return (loadFragment(fragment));
    }

    public void runChallengeView(String challTitle, String challId) {
        Fragment fragment;

        fragment = ChallengeViewFragment.newInstance(challTitle, challId);
        //menuItem = menu.getItem(4)
        loadFragment(fragment);
    }


    @Override
    public void onBackPressed() {
        MenuItem item;
        int index;

        if (_previousItems.size() > 1) {
            index = _previousItems.size() - 1;
            item = _previousItems.get(index - 1);
            _previousItems.remove(index);
            item.setChecked(true);
            back(item);
        }
        else
            back(menuItem);
        Log.d("List of Item : ", "size = " + _previousItems.size());
    }

    public Menu getMenu() {
        return (menu);
    }
}
