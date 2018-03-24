package com.sportcitizen.sportcitizen.dbutils;

/**
 * Created by axeldroz on 24/03/2018.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.NotificationModel;
import com.sportcitizen.sportcitizen.models.UserModel;

/**
 * Created by Axel Drozdzynski on 27/02/2018.
 */

public class NotificationEventListener implements ValueEventListener {
    private NotificationModel _model;

    public NotificationEventListener(NotificationModel model) {
        super();
        _model = model;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        NotificationModel model = dataSnapshot.getValue(NotificationModel.class);

        if (_model != null)
            _model.copy(model);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}