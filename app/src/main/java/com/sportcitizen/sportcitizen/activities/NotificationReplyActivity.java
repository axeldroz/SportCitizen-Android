package com.sportcitizen.sportcitizen.activities;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.fragments.ProfileViewerFragment;

public class NotificationReplyActivity extends AppCompatActivity {

    private Button _accept;
    private Button _decline;
    private Button _later;
    private String _notifId = "";
    private String _userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reply);
        initButton();
        _notifId = getIntent().getExtras().getString("notifId");
        _userId = getIntent().getExtras().getString("userId");
        Log.d("_notifId", (_notifId != null) ? _notifId : "null");
        initFragment();
    }

    /**
     * find and init Buttons
     * and run click event inits
     */
    private void initButton() {
        _accept = findViewById(R.id.notification_reply_view_accept);
        _decline = findViewById(R.id.notification_reply_view_button_decline);
        _later = findViewById(R.id.notification_reply_view_button_answer_later);
        addAcceptClick();
        addDeclineClick();
        addLaterClick();
    }

    /**
     * add onclick event on Accept Button
     */
    private void addAcceptClick() {
        _accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * add onclick event on Accept Button
     */
    private void addDeclineClick() {
        _decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * add onclick event on Accept Button
     */
    private void addLaterClick() {
        final Activity self = this;

        _later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                self.finish();
            }
        });
    }

    /**
     * Load fragmentcin view
     */
    public boolean loadFragment(android.support.v4.app.Fragment fragment) {
        if (fragment == null)
            return (false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notification_reply_view_frame_layout, fragment)
                .commit();
        return (true);
    }

    /**
     * Init Fragment
     * Pass it parameters
     * Call loadFragment
     */
    public boolean initFragment() {
        Fragment fragment;

        fragment = ProfileViewerFragment.newInstance(_notifId, _userId);
        return (loadFragment(fragment));
    }


}
