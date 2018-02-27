package com.sportcitizen.sportcitizen.dbutils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.UserModel;

/**
 * Created by Axel Drozdzynski on 27/02/2018.
 */

public class UserEventListener implements ValueEventListener {
    private UserModel _user;

    public UserEventListener(UserModel user) {
        super();
        _user = user;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        UserModel model = dataSnapshot.getValue(UserModel.class);

        _user.copy(model);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
