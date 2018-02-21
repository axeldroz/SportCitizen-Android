package com.sportcitizen.sportcitizen.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Axel Drozdzynski on 21/02/2018.
 */

public class EditFavoriteSportListAdapter extends FirebaseListAdapter<String> {

    public EditFavoriteSportListAdapter(Activity activity, Class<String> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, String model, int position) {
        ((TextView)v).setText(model);
    }
}
