package com.sportcitizen.sportcitizen.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Calendar;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CreateChallengeActivity extends AppCompatActivity implements VerticalStepperForm {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge_2);

        initDatabaseRef();
        initVerticalStepperForm();
        //_viewHolder = new CreateChallengeViewHolder(_databaseRef, this);
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

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_name_and_sport, null, false);
        _sportSpinner = content.findViewById(R.id.create_challenge_sport_spinner);
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

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_description, null, false);
        _descriptionEdit = content.findViewById(R.id.create_challenge_description_edit);
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
        _datePicker = findViewById(R.id.create_challenge_date_picker);
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
        _timePicker = findViewById(R.id.create_challenge_time_picker);
        return (content);
    }

    /**
     * Check if the step is completed
     * If it is, it will set the the step as completed
     * @param stepNumber
     */
    @Override
    public void onStepOpening(int stepNumber) {
        _verticalStepper.setActiveStepAsCompleted();
    }

    @Override
    public void sendData() {

    }
}
