package com.sportcitizen.sportcitizen.viewholders;

import android.app.Activity;
import android.graphics.Color;
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

public class EditProfileViewHolder {
    private View _mainView;
    private DatabaseReference _ref;
    private Activity _activity;

    public EditProfileViewHolder(DatabaseReference ref, Activity activity) {
        _ref = ref;
        _activity = activity;
    }

    public void setImage(String url) {
        ImageView image;
        Transformation transformation;

        image = _activity.findViewById(R.id.edit_profile_image);
        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(_activity)
                .load(url)
                .centerCrop()
                .fit()
                .transform(transformation)
                .into(image);
    }

    public void setNumber(String number) {
        TextView text;

        text = _activity.findViewById(R.id.edit_profile_view_phone_edit);
        text.setText(number);
    }
}
