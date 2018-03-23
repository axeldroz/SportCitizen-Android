package com.sportcitizen.sportcitizen.viewholders;

/**
 * Created by axeldroz on 23/03/2018.
 */

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * View holder to describe a challenge
 */
public class ChallengeViewHolder {
    private View _mainView;
    private DatabaseReference _ref;
    private Activity _activity;

    public ChallengeViewHolder(View view, DatabaseReference ref, Activity activity) {
        _mainView = view;
        _ref = ref;
        _activity = activity;
    }

    public void setImage(String url) {
        ImageView image;
        Transformation transformation;

        image = _mainView.findViewById(R.id.challenge_view_image);
        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(_mainView.getContext())
                .load(url)
                .centerCrop()
                .fit()
                .transform(transformation)
                .into(image);
    }
}
