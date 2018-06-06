package org.judocanada.judocanadamobileappandroid.Model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lspoulin on 2018-05-29.
 */

public class Event implements Mappable, Comparable<Event>{
    String id;
    Date dateStart;
    Date dateEnd;
    String url;
    String summary;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        String p1 = dateStart.substring(0,8);
        java.text.DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);

        try {
            this.dateStart = format.parse(p1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        String p1 = dateEnd.substring(0,8);
        java.text.DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);

        try {
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(p1));
            c.add(Calendar.HOUR_OF_DAY, -24);
            this.dateEnd = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



    @Override
    public void mapJSON(JSONObject object) {
        try {
            id = object.getString("uid");
            setDateStart(object.getString("dtstart"));
            setDateEnd(object.getString("dtend"));
            url = object.getString("url");
            summary = object.getString("summary");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSON() {
        return null;
    }


    @Override
    public int compareTo(@NonNull Event event) {

        Date date2 = this.dateStart;
        Date date1 = event.getDateStart();
        if (date1.after(date2)) {
            return 1;
        } else if (date1.before(date2)){
            return -1;
        } else {
            return 0;
        }
    }
}
