package com.sportcitizen.sportcitizen.dbutils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.UserModel;
import com.sportcitizen.sportcitizen.viewholders.EditProfileViewHolder;
import com.sportcitizen.sportcitizen.viewholders.ProfileViewHolder;

/**
 * Created by Axel Drozdzynski on 21/02/2018.
 */

public class EditProfileEventListener implements ValueEventListener {
    private EditProfileViewHolder _holder;

    public EditProfileEventListener(EditProfileViewHolder holder) {
        super();
        _holder = holder;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Get Post object and use the values to update the UI
        UserModel model = dataSnapshot.getValue(UserModel.class);
        _holder.setImage(model.photoURL);
        _holder.setPhone(model.phone);
        _holder.setBio(model.bio);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Post failed, log a message
        Log.w("OnCancelled", "loadPost:onCancelled", databaseError.toException());
        // ...
    }
}