package com.sportcitizen.sportcitizen.adapters;
import android.app.Activity;
import android.view.LayoutInflater;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.models.Challenge;

/**
 * Created by Axel Drozdzynski on 20/02/2018.
 */

public class FeedAdapter extends FirebaseRecyclerAdapter<Challenge, FeedViewHolder> {

    private int _count;
    private LayoutInflater _layoutinflater;
    private Activity _context;

    FeedAdapter(DatabaseReference ref, Activity context) {
        super(Challenge.class, R.layout.cell_feed_cards, FeedViewHolder.class, ref);
        _context = context;
    }

    @Override
    protected void populateViewHolder(FeedViewHolder viewHolder, Challenge model, int position) {

    }
}
