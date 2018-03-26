package com.sportcitizen.sportcitizen.dbutils;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sportcitizen.sportcitizen.models.ChallengeModel;
import com.sportcitizen.sportcitizen.viewholders.FeedViewHolder;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

public class MyChallengesGetterListener implements ValueEventListener {
    private FeedViewHolder _holder;
    private Activity _context;

    public MyChallengesGetterListener(FeedViewHolder holder, Activity context) {
        super();
        _holder = holder;
        _context = context;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ChallengeModel model = dataSnapshot.getValue(ChallengeModel.class);
        _holder.setTitle(model.title);
        _holder.setDescription(model.description);
        _holder.setImage(model.photoURL);
        _holder.setLocation(model.location);
        _holder.setDay(model.time);
        _holder.setTime(model.time);
        _holder.setBackgroundColor(Color.parseColor("#efe7e0"));
        _holder.setOnclick(_context, model, "ok");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("OnCancelled", "loadPost:onCancelled", databaseError.toException());
    }
}
