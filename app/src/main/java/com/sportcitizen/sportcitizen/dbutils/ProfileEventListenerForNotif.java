package com.sportcitizen.sportcitizen.dbutils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.NotificationModel;
import com.sportcitizen.sportcitizen.models.UserModel;
import com.sportcitizen.sportcitizen.viewholders.NotificationsViewHolder;
import com.sportcitizen.sportcitizen.viewholders.ProfileViewHolder;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

public class ProfileEventListenerForNotif implements ValueEventListener {
    private NotificationsViewHolder _holder;

    public ProfileEventListenerForNotif(NotificationsViewHolder holder) {
        super();
        _holder = holder;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Get Post object and use the values to update the UI
        UserModel model = dataSnapshot.getValue(UserModel.class);
        _holder.setImage(model.photoURL);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Post failed, log a message
        Log.w("OnCancelled", "loadPost:onCancelled", databaseError.toException());
        // ...
    }
}
