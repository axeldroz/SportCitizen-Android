package com.sportcitizen.sportcitizen.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Axel Drozdzynski on 20/02/2018.
 */

public class ChallengeModel {
    public String chall_id = "";
    public String creator_user = "";
    public String description = "";
    public String location = "";
    public String photoURL = "";
    public String sport = "";
    public String time = "";
    public String title = "";
    public String phone = "";

    public ChallengeModel() {}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        if (chall_id != null && !chall_id.equals(""))
            result.put("chall_id", chall_id);
        if (creator_user != null && !creator_user.equals(""))
            result.put("creator_user", creator_user);
        if (description != null && !description.equals(""))
            result.put("description", description);
        if (location != null && !location.equals(""))
            result.put("location", location);
        if (photoURL != null && !photoURL.equals(""))
            result.put("photoURL", photoURL);
        if (sport != null && !sport.equals(""))
            result.put("sport", sport);
        if (time != null && !time.equals(""))
            result.put("time", time);
        if (title != null && !title.equals(""))
            result.put("title", title);
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
}
