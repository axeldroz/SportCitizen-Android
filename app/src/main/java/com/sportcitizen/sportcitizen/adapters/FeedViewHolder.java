package com.sportcitizen.sportcitizen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.sportcitizen.sportcitizen.R;

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

        text = _view.findViewById(R.id.cell_text_description);
        text.setText(description);
    }
}
