package com.sportcitizen.sportcitizen.viewholders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.activities.MainActivity;
import com.sportcitizen.sportcitizen.models.Challenge;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Axel Drozdzynski on 20/02/2018.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {
    private View _view;
    public FeedViewHolder(View itemView) {
        super(itemView);
        _view = itemView;
    }

    public void setTitle(String title) {
        TextView text;

        text = _view.findViewById(R.id.cell_title);
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

        image = _view.findViewById(R.id.cell_image_feed);
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

    public void setLocation(String value) {
        TextView text;

        text = _view.findViewById(R.id.cell_feed_text_location);
        text.setText(value);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("MMM EEEE d", cal).toString();
        return date;
    }

    private String getTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("h:mm a", cal).toString();
        return date;
    }

    public void setDay(String timestamp) {
        TextView text;
        float nb;

        nb = Float.parseFloat(timestamp) * 1000;
        text = _view.findViewById(R.id.cell_feed_text_date);
        text.setText(getDate((long)nb));
    }

    public void setTime(String timestamp) {
        TextView text;
        float nb;

        nb = Float.parseFloat(timestamp) * 1000;
        text = _view.findViewById(R.id.cell_feed_text_time);
        text.setText(getTime((long)nb));
    }

    public void setOnclick(final Activity context, final Challenge model) {
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuItem item = ((MainActivity)context).getMenu().getItem(0);
                ((MainActivity)context).addItemInStack(item);
                ((MainActivity)context).runChallengeView(model.title, model.chall_id);
            }
        });
    }

    public void setBackgroundColor(int color) {
        _view.setBackgroundColor(color);
    }

    public View getView() {
        return (_view);
    }
}
