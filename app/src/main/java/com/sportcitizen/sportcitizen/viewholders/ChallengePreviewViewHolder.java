package com.sportcitizen.sportcitizen.viewholders;

/**
 * Created by axeldroz on 23/03/2018.
 */

import android.app.Activity;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Calendar;
import java.util.Locale;

/**
 * View holder to describe a challenge
 */
public class ChallengePreviewViewHolder {
    private View _mainView;
    private DatabaseReference _ref;
    private Activity _activity;

    public ChallengePreviewViewHolder(View view, DatabaseReference ref, Activity activity) {
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

    public void setTitle(String value) {
        TextView text;

        text = _mainView.findViewById(R.id.challenge_view_titleView);
        text.setText(value);
    }

    public void setSport(String value) {
        TextView text;

        text = _mainView.findViewById(R.id.challenge_view_sport);
        text.setText(value);
    }

    public void setLocation(String value) {
        TextView text;

        text = _mainView.findViewById(R.id.challenge_view_location);
        text.setText(value);
    }

    public void setDescription(String value) {
        TextView text;

        text = _mainView.findViewById(R.id.challenge_view_description);
        text.setText(value);
    }

    public void setDay(String timestamp) {
        TextView text;
        float nb;

        nb = Float.parseFloat(timestamp) * 1000;
        text = _mainView.findViewById(R.id.challenge_view_dateText);
        text.setText(getDate((long)nb));
    }

    public void setTime(String timestamp) {
        TextView text;
        float nb;

        nb = Float.parseFloat(timestamp) * 1000;
        text = _mainView.findViewById(R.id.challenge_view_time_text);
        text.setText(getTime((long)nb));
    }

    /* get string of date */
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("MMM EE d", cal).toString();
        return date;
    }

    /* get string of time */
    private String getTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("h:mm a", cal).toString();
        return date;
    }
}
