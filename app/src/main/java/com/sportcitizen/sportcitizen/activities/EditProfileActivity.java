package com.sportcitizen.sportcitizen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.adapters.EditFavoriteSportListAdapter;
import com.sportcitizen.sportcitizen.dbutils.EditProfileEventListener;
import com.sportcitizen.sportcitizen.viewholders.EditProfileViewHolder;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference _databaseRef;
    private FirebaseUser _user;
    private DatabaseReference _dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditProfileViewHolder holder;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initDatabaseRef();
        setSpinnerFavoriteSport();
        holder = new EditProfileViewHolder(_dbRef, this);
        setProfileInfoListener(holder);
    }

    /**
     * set spinner row with Database
     */
    private void setSpinnerFavoriteSport() {
        Spinner spinner;
        DatabaseReference ref;
        EditFavoriteSportListAdapter adapter;

        spinner = findViewById(R.id.edit_profile_view_sport_spinner);
        ref = _databaseRef.child("sports");
        adapter = new EditFavoriteSportListAdapter(this, String.class, R.layout.support_simple_spinner_dropdown_item, ref);
        spinner.setAdapter(adapter);
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
        _databaseRef = mDatabase.getReference();
        _dbRef = mDatabase.getReference("users").child(_user.getUid());
    }

    /**
     * Set listener which manage profile info
     */
    private void setProfileInfoListener(final EditProfileViewHolder holder) {
        _dbRef.addValueEventListener(new EditProfileEventListener(holder));
    }
}
