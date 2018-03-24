package com.sportcitizen.sportcitizen.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Axel Drozdzynski on 23/03/2018.
 */

public class NotificationModel {

    public String chall_id = "";
    public String date = "";
    public String from_id = "";
    public String message = "";
    public String notif_id = "";
    public String type = "";


    public NotificationModel() {}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        if (chall_id != null && !chall_id.equals(""))
            result.put("chall_id", chall_id);
        if (date != null && !date.equals(""))
            result.put("date", date);
        if (from_id != null && !from_id.equals(""))
            result.put("from_id", from_id);
        if (message != null && !message.equals(""))
            result.put("message", message);
        if (notif_id != null && !notif_id.equals(""))
            result.put("notif_id", notif_id);
        if (type != null && !type.equals(""))
            result.put("type", type);
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
    @Exclude
    public void copy(NotificationModel model) {
        chall_id = model.chall_id;
        date = model.date;
        from_id = model.from_id;
        message = model.message;
        notif_id = model.notif_id;
        type = model.type;
    }
}
