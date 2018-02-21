package com.sportcitizen.sportcitizen.dbutils;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.sportcitizen.sportcitizen.models.UserModel;

/**
 * Created by Axel Drozdzynski on 21/02/2018.
 */

public class ProfileUpdater {
    private DatabaseReference _ref;
    private UserModel _profile;

    public ProfileUpdater(DatabaseReference ref) {
        _ref = ref;
    }

    public void setProfile(UserModel profile) {
        _profile = profile;
    }

    public UserModel getProfile() {
        return (_profile);
    }


}
