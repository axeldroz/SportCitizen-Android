package com.sportcitizen.sportcitizen.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Axel Drozdzynski on 21/02/2018.
 */

public class UserModel {
    public String bio = "";
    public String email = "";
    public String favoriteSport = "";
    public String name = "";
    public String photoURL;
    public String city = "";
    public long age = -1;
    public String phone = "";

    public UserModel() {}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        if (bio != null && !bio.equals(""))
            result.put("bio", bio);
        if (email != null && !email.equals(""))
            result.put("email", email);
        if (favoriteSport != null && !favoriteSport.equals(""))
            result.put("favoriteSport", favoriteSport);
        if (name != null && !name.equals(""))
            result.put("name", name);
        if (photoURL != null && !photoURL.equals(""))
            result.put("photoURL", photoURL);
        if (city != null && !city.equals(""))
            result.put("city", city);
        if (age != -1)
            result.put("age", age);
        if (phone != null && !phone.equals(""))
            result.put("phone", phone);
        return (result);
    }

    /**
     * Allow to update your model directly in db as soon as you've filled it
     * @param ref
     */
    @Exclude
    public void updateToDB(DatabaseReference ref) {
        String key = ref.push().getKey();
        Map<String, Object> values = this.toMap();

        ref.updateChildren(values);
    }

    /**
     * Copy value of an other model
     */
    public void copy(UserModel model) {
        bio = model.bio;
        email = model.bio;
        favoriteSport = model.favoriteSport;
        name = model.name;
        photoURL = model.photoURL;
        city = model.city;
        age = model.age;
        phone = model.phone;
    }
}
