package com.sportcitizen.sportcitizen.dbutils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.Challenge;
import com.sportcitizen.sportcitizen.viewholders.ChallengeViewHolder;

/**
 * Created by axeldroz on 23/03/2018.
 */

public class ChallengeEventListener implements ValueEventListener {
    private ChallengeViewHolder _holder;

    public ChallengeEventListener(ChallengeViewHolder holder) {
        super();
        _holder = holder;
    }

    /* get Challenge Model and modify view display througth a ChallengeViewHolder */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Challenge model = dataSnapshot.getValue(Challenge.class);

        _holder.setImage(model.photoURL);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
