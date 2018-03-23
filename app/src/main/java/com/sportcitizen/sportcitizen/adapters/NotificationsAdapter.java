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
import com.sportcitizen.sportcitizen.dbutils.ProfileEventListenerForNotif;
import com.sportcitizen.sportcitizen.models.NotificationModel;
import com.sportcitizen.sportcitizen.viewholders.NotificationsViewHolder;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

public class NotificationsAdapter extends FirebaseRecyclerAdapter<NotificationModel, NotificationsViewHolder> {
    private LayoutInflater _layoutinflater;
    private Activity _context;
    private DatabaseReference _ref;
    private DatabaseReference _userRef;

    public NotificationsAdapter(DatabaseReference ref, Activity context) {
        super(NotificationModel.class, R.layout.cell_notification_cards, NotificationsViewHolder.class, ref);
        _context = context;
        _ref = ref;
        initDatabaseRef();
    }
    @Override
    protected void populateViewHolder(NotificationsViewHolder viewHolder, NotificationModel model, int position) {
        DatabaseReference dbRef;

        viewHolder.setDescription(model.message);
        dbRef = _userRef.child(model.from_id);
        dbRef.addValueEventListener(new ProfileEventListenerForNotif(viewHolder));
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
        _userRef = mDatabase.getReference("users");
    }
}
