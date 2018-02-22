package com.sportcitizen.sportcitizen.adapters;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.activities.MainActivity;
import com.sportcitizen.sportcitizen.fragments.ChallengeViewFragment;
import com.sportcitizen.sportcitizen.models.Challenge;
import com.sportcitizen.sportcitizen.viewholders.FeedViewHolder;

/**
 * Created by Axel Drozdzynski on 20/02/2018.
 */

public class FeedAdapter extends FirebaseRecyclerAdapter<Challenge, FeedViewHolder> {

    private int _count;
    private LayoutInflater _layoutinflater;
    private Activity _context;

    public FeedAdapter(DatabaseReference ref, Activity context) {
        super(Challenge.class, R.layout.cell_feed_cards, FeedViewHolder.class, ref);
        _context = context;
    }

    @Override
    protected void populateViewHolder(FeedViewHolder holder, Challenge model, int position) {
            holder.setTitle(model.title);
            holder.setDescription(model.description);
            holder.setImage(model.photoURL);
            holder.setLocation(model.location);
            holder.setDay(model.time);
            holder.setTime(model.time);
            holder.setBackgroundColor(Color.parseColor("#efe7e0"));
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuItem item = ((MainActivity)_context).getMenu().getItem(0);
                    ((MainActivity)_context).addItemInStack(item);
                    ((MainActivity)_context).back(4);
                }
            });
            Log.d("creator_user", model.creator_user);
    }
}
