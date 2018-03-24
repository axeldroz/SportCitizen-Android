package com.sportcitizen.sportcitizen.viewholders;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

/**
 * Created by Axel Drozdzynski on 21/02/2018.
 */

/**
 * View holder to describe and fill profile information in Profile View
 */
public class ProfileViewHolder {
    private View _mainView;
    private DatabaseReference _ref;
    private Activity _activity;

    public ProfileViewHolder(View view, DatabaseReference ref, Activity activity) {
        _mainView = view;
        _ref = ref;
        _activity = activity;
    }

    public void setImage(String url) {
        ImageView image;
        Transformation transformation;

        Log.d("PHOTOURL", url);
        image = _mainView.findViewById(R.id.profile_view_image);
        Log.d("Image", image != null ? "ok" : null);
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

    public void setName(String name) {
        TextView text;

        text = _mainView.findViewById(R.id.profile_view_name);
        text.setText(name);
    }

    public void setCityAndAge(String city, long age) {
        TextView text;
        String str;

        text = _mainView.findViewById(R.id.profile_view_city_age);
        str = city + "   |   " + age + " years old";
        text.setText(str);
    }

    public void setFavoriteSport(String sport) {
        TextView text;

        text = _mainView.findViewById(R.id.profile_view_favorite_sport);
        text.setText(sport);
    }

    public void setBio(String bio) {
        TextView text;

        text = _mainView.findViewById(R.id.profile_view_bio);
        text.setText(bio);
    }
}
