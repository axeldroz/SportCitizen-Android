package com.sportcitizen.sportcitizen.viewholders;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private ImageView _image;
    private EditText _phone;
    private EditText _bio;

    public EditProfileViewHolder(DatabaseReference ref, Activity activity) {
        _ref = ref;
        _activity = activity;
        _image = _activity.findViewById(R.id.edit_profile_image);
        _phone = _activity.findViewById(R.id.edit_profile_view_phone_edit);
        _bio = _activity.findViewById(R.id.edit_profile_view_bio_edit);
    }

    public void setImage(String url) {
        Transformation transformation;

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
                .into(_image);
    }

    public void setBio(String bioText) {
        _bio.setText(bioText);
    }

    public String getBio() {
        return (_bio.getText().toString());
    }

    public void setPhone(String number) {
        _phone.setText(number);
    }

    public String getPhone() {
        return (_phone.getText().toString());
    }
}
