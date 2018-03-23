package com.sportcitizen.sportcitizen.viewholders;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by axeldroz on 23/03/2018.
 */

public class NotificationsViewHolder extends RecyclerView.ViewHolder {
    private View _view;

    public NotificationsViewHolder(View itemView) {
        super(itemView);
        _view = itemView;
    }

    public void setTitle(String title) {
        TextView text;

        text = _view.findViewById(R.id.cell_notification_title);
        text.setText(title);
    }

    public void setDescription(String description) {
        TextView text;
        String shortDescription;

        text = _view.findViewById(R.id.cell_text_description);
        if (description.length() < 50)
            shortDescription = description;
        else {
            shortDescription = description.substring(0, 50);
            shortDescription += " ...";
        }
        text.setText(shortDescription);
    }

    public void setImage(String url) {
        ImageView image;
        Transformation transformation;

        image = _view.findViewById(R.id.cell_image);
        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(_view.getContext())
                .load(url)
                .centerCrop()
                .fit()
                .transform(transformation)
                .into(image);
    }
}
