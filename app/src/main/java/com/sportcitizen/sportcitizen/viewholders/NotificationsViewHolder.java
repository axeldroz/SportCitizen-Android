package com.sportcitizen.sportcitizen.viewholders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sportcitizen.sportcitizen.activities.NotificationReplyActivity;
import com.sportcitizen.sportcitizen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
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

    public void setOnClick(final Activity context, final String notifId, final String userId) {
        LinearLayout layout;

        layout = _view.findViewById(R.id.cell_cards_notification_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotificationReplyActivity.class);
                intent.putExtra("notifId", notifId);
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }
        });
    }

    public void setOnClick(final Activity context, final String notifId, final String userId, String type) {
        LinearLayout layout;

        layout = _view.findViewById(R.id.cell_cards_notification_layout);
        if (type.equals("joinchall")) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NotificationReplyActivity.class);
                    intent.putExtra("notifId", notifId);
                    intent.putExtra("userId", userId);
                    context.startActivity(intent);
                }
            });
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase mDatabase;
                    mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mDatabaseRef = mDatabase.getReference();
                    DatabaseReference dbRef = mDatabase.getReference("users").child(user.getUid());
                    dbRef.child("notifications").child(notifId).removeValue();
                }
            });
        }
    }
}
