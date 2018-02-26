package com.sportcitizen.sportcitizen.viewholders;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.adapters.EditFavoriteSportListAdapter;
import com.sportcitizen.sportcitizen.dbutils.CreateChallengeButtonListener;

import java.util.Calendar;

import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by Axel Drozdzynski on 23/02/2018.
 */

public class CreateChallengeViewHolder implements VerticalStepperForm {
    private Activity _activity;
    private DatabaseReference _ref;
    private EditText _nameEdit;
    private Spinner _sportSpinner;
    private DatePicker _datePicker;
    private Button _createButton;
    private Button _cancelButton;
    private Calendar _calendar;

    public CreateChallengeViewHolder(DatabaseReference ref, Activity activity) {
        _activity = activity;
        _ref = ref;
        init();
    }

    public void init() {
        _nameEdit = _activity.findViewById(R.id.create_challenge_name_edit);
        _datePicker = _activity.findViewById(R.id.create_challenge_date_picker);
        _cancelButton = _activity.findViewById(R.id.create_challenge_button_cancel);
        initSportSpinner();
        initCreateButton();
        initCancelButton();
        _calendar = Calendar.getInstance();

    }

    public void initSportSpinner() {
        DatabaseReference ref;
        EditFavoriteSportListAdapter adapter;

        _sportSpinner = _activity.findViewById(R.id.create_challenge_sport_spinner);
        ref = _ref.child("sports");
        adapter = new EditFavoriteSportListAdapter(_activity, String.class, R.layout.support_simple_spinner_dropdown_item, ref);
        _sportSpinner.setAdapter(adapter);
    }

    private void initCreateButton() {
        _createButton = _activity.findViewById(R.id.create_challenge_button_create);
        _createButton.setOnClickListener(new CreateChallengeButtonListener(_activity));
    }

    private void initCancelButton() {
        Button cancel;
        final Activity activity = _activity;

        _cancelButton = _activity.findViewById(R.id.create_challenge_button_cancel);
        _cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public void setName(String name) {
        _nameEdit.setText(name);
    }

    public String getName() {
        return (_nameEdit.getText().toString());
    }

    public String getSport() {
        return (_sportSpinner.getSelectedItem().toString());
    }

    public String getDate() {
        int day, month, year, hour;

        day = _datePicker.getDayOfMonth();
        month = _datePicker.getMonth();
        year = _datePicker.getYear();

        _calendar.set(year, month, day);
        return ("");
    }

    @Override
    public View createStepContentView(int stepNumber) {
        return null;
    }

    @Override
    public void onStepOpening(int stepNumber) {

    }

    @Override
    public void sendData() {

    }
}
