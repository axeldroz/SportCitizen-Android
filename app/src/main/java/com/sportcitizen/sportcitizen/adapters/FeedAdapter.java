package com.sportcitizen.sportcitizen.adapters;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.models.ChallengeModel;
import com.sportcitizen.sportcitizen.viewholders.FeedViewHolder;

/**
 * Created by Axel Drozdzynski on 20/02/2018.
 */

public class FeedAdapter extends FirebaseRecyclerAdapter<ChallengeModel, FeedViewHolder> {
    private Activity _context;

    public FeedAdapter(DatabaseReference ref, Activity context) {
        super(ChallengeModel.class, R.layout.cell_feed_cards, FeedViewHolder.class, ref);
        _context = context;
    }

    @Override
    protected void populateViewHolder(FeedViewHolder holder, ChallengeModel model, int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /*if (model.creator_user.equals(user.getUid()))
            holder.hide();
        else {*/
            holder.setTitle(model.title);
            holder.setDescription(model.description);
            holder.setImage(model.photoURL);
            holder.setLocation(model.location);
            holder.setDay(model.time);
            holder.setTime(model.time);
            holder.setBackgroundColor(Color.parseColor("#efe7e0"));
            if (model.creator_user.equals(user.getUid()))
                holder.setOnclick(_context, model, "me");
            else
                holder.setOnclick(_context, model);
            Log.d("creator_user", model.creator_user);
    }
}
