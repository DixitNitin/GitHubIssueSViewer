package app.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Github {

    private String title;
    private String events_url;
    private int id;


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return events_url;
    }

    public int getID() {
        return id;
    }
}
