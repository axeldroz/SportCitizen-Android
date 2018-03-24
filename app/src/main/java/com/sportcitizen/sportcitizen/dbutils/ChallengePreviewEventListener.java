package com.sportcitizen.sportcitizen.dbutils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.ChallengeModel;
import com.sportcitizen.sportcitizen.viewholders.ChallengePreviewViewHolder;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

public class ChallengePreviewEventListener implements ValueEventListener {
    private ChallengePreviewViewHolder _holder;

    public ChallengePreviewEventListener(ChallengePreviewViewHolder holder) {
        super();
        _holder = holder;
    }

    /* get ChallengeModel Model and modify view display througth a ChallengePreviewViewHolder */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ChallengeModel model = dataSnapshot.getValue(ChallengeModel.class);

        _holder.setImage(model.photoURL);
        _holder.setTitle(model.title);
        _holder.setSport(model.sport);
        _holder.setLocation(model.location);
        _holder.setDay(model.time);
        _holder.setTime(model.time);
        _holder.setDescription(model.description);
        _holder.setPhone(model.phone);
        _holder.setOnclickJoinButton(model);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
