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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.dbutils.ProfileEventListener;
import com.sportcitizen.sportcitizen.fragments.ProfileViewerFragment;
import com.sportcitizen.sportcitizen.models.NotificationModel;
import com.sportcitizen.sportcitizen.viewholders.ProfileViewHolder;

public class NotificationReplyActivity extends AppCompatActivity {

    private Button _accept;
    private Button _decline;
    private Button _later;
    private String _notifId = "";
    private String _userId = "";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser _user;
    private DatabaseReference _dbRef;

    final NotificationModel _notification = new NotificationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reply);
        initDatabaseRef();
        _notifId = getIntent().getExtras().getString("notifId");
        _userId = getIntent().getExtras().getString("userId");
        _notification.fetchData(_dbRef.child("notifications").child(_notifId));
        Log.d("_notifId", (_notifId != null) ? _notifId : "null");
        initButton();
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
        if (_notifId == null)
            return;
        final Activity self = this;


        _accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationModel acceptModel = new NotificationModel();
                DatabaseReference ref = mDatabase.getReference("users").child(_notification.from_id).child("notifications");

                acceptModel.notif_id = ref.push().getKey();
                acceptModel.message = _user.getDisplayName() + " accepted you in his challenge";
                acceptModel.chall_id = _notification.chall_id;
                Log.d("CHallID", acceptModel.chall_id);
                acceptModel.date = "";
                acceptModel.from_id = _user.getUid();
                acceptModel.type = "challaccept";
                acceptModel.updateToDB(ref.child(acceptModel.notif_id));
                mDatabase.getReference("users").child(_notification.from_id).child("my_challenges").child(acceptModel.chall_id).setValue(acceptModel.chall_id);
                _dbRef.child("notifications").child(_notifId).removeValue();
                self.finish();
            }
        });
    }

    /**
     * add onclick event on Accept Button
     * remove notification
     */
    private void addDeclineClick() {
        if (_notifId == null)
            return;
        final DatabaseReference ref = _dbRef.child("notifications").child(_notifId);
        final Activity self = this;

        _decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationModel declineModel = new NotificationModel();
                DatabaseReference ref2 = mDatabase.getReference("users").child(_notification.from_id).child("notifications");

                declineModel.notif_id = ref2.push().getKey();
                declineModel.message = _user.getDisplayName() + " decline your apply. Sorry.";
                declineModel.chall_id = _notification.chall_id;
                declineModel.date = "";
                declineModel.from_id = _user.getUid();
                declineModel.type = "challdecline";
                declineModel.updateToDB(ref2.child(declineModel.notif_id));
                ref.removeValue();
                self.finish();
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

    /**
     * Initalise firebase
     */
    private void initDatabaseRef() {
        _user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            mDatabase = FirebaseDatabase.getInstance();
        }catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        mDatabaseRef = mDatabase.getReference();
        _dbRef = mDatabase.getReference("users").child(_user.getUid());
    }
}
