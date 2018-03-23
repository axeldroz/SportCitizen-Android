package com.sportcitizen.sportcitizen.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.dbutils.MyChallengesGetterListener;
import com.sportcitizen.sportcitizen.models.ChallengeModel;
import com.sportcitizen.sportcitizen.viewholders.FeedViewHolder;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

public class MyChallengesAdapter extends FirebaseRecyclerAdapter<String, FeedViewHolder> {

    private LayoutInflater _layoutinflater;
    private Activity _context;
    private DatabaseReference _ref;
    private DatabaseReference _chalRef;

    public MyChallengesAdapter(DatabaseReference ref, Activity context) {
        super(String.class, R.layout.cell_feed_cards, FeedViewHolder.class, ref);
        _context = context;
        _ref = ref;
        initDatabaseRef();
    }

    @Override
    protected void populateViewHolder(FeedViewHolder viewHolder, String model, int position) {
        DatabaseReference dbRef;

        dbRef = _chalRef.child(model);
        dbRef.addValueEventListener(new MyChallengesGetterListener(viewHolder, _context));
    }

    private void initDatabaseRef() {
        FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase mDatabase = null;

        try {
            mDatabase = FirebaseDatabase.getInstance();
        }catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        DatabaseReference mDatabaseRef = mDatabase.getReference();
        _chalRef = mDatabase.getReference("challenges");
    }
}
