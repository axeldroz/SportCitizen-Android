package com.sportcitizen.sportcitizen.activities;

import android.app.Activity;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.adapters.EditFavoriteSportListAdapter;
import com.sportcitizen.sportcitizen.models.Challenge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CreateChallengeActivity extends AppCompatActivity implements VerticalStepperForm{
    private FirebaseDatabase mDatabase;
    private DatabaseReference _databaseRef;
    private FirebaseUser _user;
    private DatabaseReference _dbRef;
    private VerticalStepperFormLayout _verticalStepper;
    private EditText _nameEdit;
    private EditText _descriptionEdit;
    private Spinner _sportSpinner;
    private DatePicker _datePicker;
    private TimePicker _timePicker;
    private Button _createButton;
    private Button _cancelButton;
    private Calendar _calendar;
    private Challenge _model;

    /*
    private enum Step {
        NAME_AND_SPORT_FROM(0),
        DESCRIPTION_FORM(1),
        DATE_FORM(2),
        TIME_FORM(3);

        private int _value;

        Step(int value) {
            this._value = value;
        }

        public int getValue() {
            return (this._value);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge_2);

        _model = new Challenge();
        _calendar = Calendar.getInstance();
        initDatabaseRef();
        initVerticalStepperForm();
    }

    /**
     * Initalise firebase
     */
    private void initDatabaseRef() {
        _user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            mDatabase = FirebaseDatabase.getInstance();
        }catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        _databaseRef = mDatabase.getReference();
        _dbRef = mDatabase.getReference("users").child(_user.getUid());
    }

    /**
     * Init and instance of the vertical stepper form
     */
    private void initVerticalStepperForm() {
        String [] steps = {"Name and sport", "Description", "Date", "Time"};
        int color1, color2;

        _verticalStepper = findViewById(R.id.vertical_stepper_form);
        color1 = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        color2 = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        VerticalStepperFormLayout.Builder.newInstance(_verticalStepper, steps, this, this)
                .primaryColor(color1)
                .primaryDarkColor(color2)
                .displayBottomNavigation(true)
                .init();
    }

    /**
     * Create the view
     *
     * @param stepNumber
     * @return View created
     */
    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;

        switch (stepNumber) {
            case 0:
                view = createNameAndSportView();
                break;
            case 1:
                view = createDescriptionView();
                break;

            case 2:
                view = createDateView();
                break;

            case 3:
                view = createTimeView();
                break;
        }
        return (view);
    }

    /**
     * Create and return Name and Sport form
     *
     * @return View
     */
    public View createNameAndSportView() {
        LayoutInflater inflater;
        ConstraintLayout content;
        DatabaseReference ref;
        EditFavoriteSportListAdapter adapter;
        final CreateChallengeActivity activity = this;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_name_and_sport, null, false);
        _sportSpinner = content.findViewById(R.id.create_challenge_sport_spinner);
        _nameEdit = content.findViewById(R.id.create_challenge_name_edit);
        _nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.checkNameAndSport();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        ref = _databaseRef.child("sports");
        adapter = new EditFavoriteSportListAdapter(this, String.class, R.layout.support_simple_spinner_dropdown_item, ref);
        _sportSpinner.setAdapter(adapter);
        return (content);
    }

    /**
     * Create Description View form
     */
    public View createDescriptionView () {
        LayoutInflater inflater;
        ConstraintLayout content;
        final CreateChallengeActivity activity = this;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_description, null, false);
        _descriptionEdit = content.findViewById(R.id.create_challenge_description_edit);
        _descriptionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.checkDescription();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return (content);
    }

    /**
     * Create Date View form
     */
    public View createDateView () {
        LayoutInflater inflater;
        ConstraintLayout content;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_date, null, false);
        _datePicker = content.findViewById(R.id.create_challenge_date_picker);
        if (_datePicker == null)
            Log.d("DATEPICKER", "null");
        return (content);
    }

    /**
     * Create Time View form
     */
    public View createTimeView() {
        LayoutInflater inflater;
        ConstraintLayout content;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_time, null, false);
        _timePicker = content.findViewById(R.id.create_challenge_time_picker);
        return (content);
    }

    /**
     * Check if the step is completed
     * If it is, it will set the the step as completed
     * @param stepNumber
     */
    @Override
    public void onStepOpening(int stepNumber) {
        String error = "";

        switch (stepNumber) {
            case 0:
                error = checkNameAndSport();
                break;
            case 1:
                error = checkDescription();
                break;
            case 2:
                error = checkDate();
                break;
            case 3:
                error = checkTime();
                break;
        }
        Log.d("CHECKING", "Error = " + error);
    }

    /**
     * Check Name and Sport form
     * And fill model if it's ok
     */
    private String checkNameAndSport() {
        String name;
        String sport;

        if (_nameEdit == null) {
            _verticalStepper.setActiveStepAsUncompleted("Something wrong");
            return ("Something wrong");
        }
        name = _nameEdit.getText().toString();
        Log.d("NAME", name + " ");
        if (_sportSpinner != null)
            sport = (String)_sportSpinner.getSelectedItem();
        else {
            _verticalStepper.setActiveStepAsUncompleted("Something wrong");
            return ("Something wrong");
        }
        if (name.equals("") || name.length() < 3) {
            _verticalStepper.setActiveStepAsUncompleted("The challenge name is too short");
            return ("The challenge name is too short");
        }
        if (name.length() > 25) {
            _verticalStepper.setActiveStepAsUncompleted("The challenge name is too long");
            return ("The challenge name is too long");
        }
        if (sport.equals("")) {
            _verticalStepper.setActiveStepAsUncompleted("Please select an sport");
            return ("Please select an sport");
        }
        _model.name = name;
        _model.sport = sport;
        _verticalStepper.setActiveStepAsCompleted();
        return ("");
    }

    private void checkName() {
        String name = "";

        if (_nameEdit != null)
            name = _nameEdit.getText().toString();
        if(name.length() >= 3 && name.length() <= 40) {
            _verticalStepper.setActiveStepAsCompleted();
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "The name must have between 3 and 40 characters";
            _verticalStepper.setActiveStepAsUncompleted(errorMessage);
        }
    }

    /**
     * Check Description Form
     * And fill model if it's ok
     */
    private String checkDescription() {
        String description;

        if(_descriptionEdit == null) {
            _verticalStepper.setActiveStepAsUncompleted("Something is wrong");
            return ("Something is wrong");
        }
        description = _descriptionEdit.getText().toString();
        if (description.length() < 15) {
            _verticalStepper.setActiveStepAsUncompleted("The description must be longer than 14 characters");
            return ("The description must be longer than 14 characters");
        }
        if (description.length() > 240) {
            _verticalStepper.setActiveStepAsUncompleted("The description must be shorter than 241 characters");
            return ("The description must be shorter than 241 characters");
        }
        _model.description = description;
        _verticalStepper.setActiveStepAsCompleted();
        return ("");
    }

    /**
     * Check Date Form
     * And fill model if it's ok
     */
    private String checkDate() {
        Calendar calendar;
        Calendar today;
        Calendar max;

        if (_datePicker == null)
            return ("Something wrong");
        today = Calendar.getInstance();
        calendar = getDate();
        max = Calendar.getInstance();
        max.set(Calendar.YEAR, 1);
        if (today.getTimeInMillis() > _calendar.getTimeInMillis())
            return ("You can't place an challenge in a past date");
        if (today.getTimeInMillis() < calendar.getTimeInMillis() - max.getTimeInMillis())
            return ("Stop to live in the future !");
        _verticalStepper.setActiveStepAsCompleted();
        return ("");
    }

    /**
     * Check Time
     * set Calendar
     */
    private String checkTime() {
        int hour, minute;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = _timePicker.getHour();
            minute = _timePicker.getMinute();
            _calendar.set(Calendar.HOUR, hour);
            _calendar.set(Calendar.MINUTE, minute);
        }
        return ("");
    }

    /**
     * fill date with info
     * @return
     */
    public Calendar getDate() {
        int day, month, year;

        day = _datePicker.getDayOfMonth();
        month = _datePicker.getMonth();
        year = _datePicker.getYear();
        _calendar.set(Calendar.YEAR, year);
        _calendar.set(Calendar.MONTH, month);
        _calendar.set(Calendar.DAY_OF_MONTH, day);
        return (_calendar);
    }

    @Override
    public void sendData() {

    }
}
