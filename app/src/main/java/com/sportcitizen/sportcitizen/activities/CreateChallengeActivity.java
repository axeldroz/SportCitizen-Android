package com.sportcitizen.sportcitizen.activities;

/**
 * Created by Axel Drozdzynski
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
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
import com.sportcitizen.sportcitizen.dbutils.UserEventListener;
import com.sportcitizen.sportcitizen.models.ChallengeModel;
import com.sportcitizen.sportcitizen.models.LocationModel;
import com.sportcitizen.sportcitizen.models.UserModel;
import com.sportcitizen.sportcitizen.utils.MyLocationListener;

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
    private ChallengeModel _model;
    private UserModel _userInfo;
    private LocationModel _locationModel;
    private LocationManager _locationManager;
    MyLocationListener _locationListener;

    private final long DAY_TIMESTAMP = 3600 * 24 * 1000;
    private final long MONTH_TIMESTAMP = DAY_TIMESTAMP * 30;

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

        _model = new ChallengeModel();
        _locationModel = new LocationModel();
        _locationListener = new MyLocationListener(_locationModel, this);
        _locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        _calendar = Calendar.getInstance();
        initDatabaseRef();
        _userInfo = new UserModel();
        _dbRef.addListenerForSingleValueEvent(new UserEventListener(_userInfo));
        initVerticalStepperForm();
        getLocation();
    }

    /**
     * Initalise firebase
     */
    private void initDatabaseRef() {
        _user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            mDatabase = FirebaseDatabase.getInstance();
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        _databaseRef = mDatabase.getReference();
        _dbRef = mDatabase.getReference("users").child(_user.getUid());
    }

    /**
     * Init and instance of the vertical stepper form
     */
    private void initVerticalStepperForm() {
        String[] steps = {"Name and sport", "Description", "Date", "Time"};
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
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.checkNameAndSport();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        ref = _databaseRef.child("sports");
        adapter = new EditFavoriteSportListAdapter(this, String.class, R.layout.support_simple_spinner_dropdown_item, ref);
        _sportSpinner.setAdapter(adapter);
        return (content);
    }

    /**
     * Create Description View form
     */
    public View createDescriptionView() {
        LayoutInflater inflater;
        ConstraintLayout content;
        final CreateChallengeActivity activity = this;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_description, null, false);
        _descriptionEdit = content.findViewById(R.id.create_challenge_description_edit);
        _descriptionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.checkDescription();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return (content);
    }

    /**
     * Create Date View form
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View createDateView() {
        LayoutInflater inflater;
        ConstraintLayout content;
        final CreateChallengeActivity activity = this;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_date, null, false);
        _datePicker = content.findViewById(R.id.create_challenge_date_picker);
        _datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                activity.checkDate();
            }
        });
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
        final CreateChallengeActivity activity = this;

        inflater = LayoutInflater.from(getBaseContext());
        content = (ConstraintLayout) inflater.inflate(R.layout.form_challenge_time, null, false);
        _timePicker = content.findViewById(R.id.create_challenge_time_picker);
        _timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                activity.checkTime();
            }
        });
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
            case 4:
                Log.d("LAST STEP", "Confirm !");
                break;
        }
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
            sport = (String) _sportSpinner.getSelectedItem();
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
        _model.title = name;
        _model.sport = sport;
        _verticalStepper.setActiveStepAsCompleted();
        return ("");
    }

    /**
     * Check Description Form
     * And fill model if it's ok
     */
    private String checkDescription() {
        String description;

        if (_descriptionEdit == null) {
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

        if (_datePicker == null)
            return ("Something wrong");
        today = Calendar.getInstance();
        calendar = getDate();

        if (today.getTimeInMillis() > calendar.getTimeInMillis() + DAY_TIMESTAMP) {
            _verticalStepper.setActiveStepAsUncompleted("You can't place an challenge in a past date : ");
            return ("You can't place an challenge in a past date");
        }
        if (today.getTimeInMillis() < calendar.getTimeInMillis() - MONTH_TIMESTAMP) {
            _verticalStepper.setActiveStepAsUncompleted("Stop to live in the future !");
            return ("Stop to live in the future !");
        }
        _verticalStepper.setActiveStepAsCompleted();
        _model.time = (_calendar.getTimeInMillis() / 1000) + "";
        return ("");
    }

    /**
     * Check Time
     * set Calendar
     */
    private String checkTime() {
        int hour, minute;

        hour = _timePicker.getHour();
        minute = _timePicker.getMinute();
        _calendar.set(Calendar.HOUR, hour);
        _calendar.set(Calendar.MINUTE, minute);
        _verticalStepper.setActiveStepAsCompleted();
        String t = DateFormat.format(" MMM EEEE d h:mm a", _calendar).toString();
        Log.d("TIME ", t);
        _model.time = (_calendar.getTimeInMillis() / 1000) + "";
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

    /**
     * Finish to fillthe model
     */
    private void fillModel() {
        DatabaseReference ref;

        ref = _databaseRef.child("challenges");

        Log.d("UserInfo", "name = " + _userInfo.name);

        _model.location = "Bordeaux"; //subject to change for the real location
        _model.creator_user = _user.getUid();
        _model.photoURL = _userInfo.photoURL;
        _model.chall_id = ref.push().getKey();
        if (_model.location.equals("")) {
            getLocation();
        }

        String t = DateFormat.format(" MMM EEEE d h:mm a", Long.parseLong(_model.time)).toString();
        Log.d("Final TIME ", t);
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("GPS", "Active the gps please");
            return;
        }
        _locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, _locationListener);
        _model.location = _locationModel.city;
    }

    @Override
    public void sendData() {
        DatabaseReference ref;

        if (displayGpsStatus()) {
            Log.d("LAST STEP", "Send Data !");
            fillModel();
            ref = _databaseRef.child("challenges").child(_model.chall_id);
            _model.updateToDB(ref);
            this.finish();
        }
        else
            Log.d("GPS", "Active the gps please");
    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }
}
