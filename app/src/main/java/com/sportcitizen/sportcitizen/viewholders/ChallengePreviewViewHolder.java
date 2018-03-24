package com.sportcitizen.sportcitizen.viewholders;

/**
 * Created by axeldroz on 23/03/2018.
 */

import android.app.Activity;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.models.ChallengeModel;
import com.sportcitizen.sportcitizen.models.NotificationModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

/**
 * View holder to describe a challenge
 */
public class ChallengePreviewViewHolder {
    private View _mainView;
    private DatabaseReference _ref;
    private Activity _activity;
    private FirebaseDatabase mDatabase;
    private DatabaseReference _databaseRef;
    private FirebaseUser _user;
    private DatabaseReference _dbRef;

    public ChallengePreviewViewHolder(View view, DatabaseReference ref, Activity activity) {
        _mainView = view;
        _ref = ref;
        _activity = activity;
        initDatabaseRef();
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

    public void hideButtonJoin() {
        Button button;

        button = _mainView.findViewById(R.id.challenge_view_join_button);
        button.setVisibility(View.GONE);
    }

    public void showPhoneNumber() {
        ImageView image;
        TextView text;

        image = _mainView.findViewById(R.id.challenge_view_phone_image);
        text = _mainView.findViewById(R.id.challenge_view_phone_text);
        image.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
    }

    public void setPhone(String value) {
        TextView text;

        text = _mainView.findViewById(R.id.challenge_view_phone_text);
        text.setText(value);
    }

    public void setOnclickJoinButton(final ChallengeModel challenge) {
        final Button button = _mainView.findViewById(R.id.challenge_view_join_button);
        NotificationModel model = new NotificationModel();

        final DatabaseReference ref = _dbRef.child(challenge.creator_user).child("notifications");
        model.chall_id = challenge.chall_id;
        model.date = "";
        model.from_id = _user.getUid();
        model.message = _user.getDisplayName() + " wants to join " + challenge.title;
        model.notif_id = ref.push().getKey();
        model.type = "joinchall";

        final NotificationModel constModel = model;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constModel.updateToDB(ref.child(constModel.notif_id));
                button.setText("Requested");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        });
    }

    /**
     * Initalise firebase
     */
    private void initDatabaseRef() {
        _user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            mDatabase = FirebaseDatabase.getInstance();
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        _databaseRef = mDatabase.getReference();
        _dbRef = mDatabase.getReference("users");
    }
}
