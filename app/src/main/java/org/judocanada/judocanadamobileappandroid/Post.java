package org.judocanada.judocanadamobileappandroid;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lspoulin on 2018-05-07.
 */

class Post implements Mappable{
    private int id;
    private String date;
    private String dateGMT;
    private String title;
    private String content;
    private String exerpt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGMT() {
        return dateGMT;
    }

    public void setDateGMT(String dateGMT) {
        this.dateGMT = dateGMT;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExerpt() {
        return exerpt;
    }

    public void setExerpt(String exerpt) {
        this.exerpt = exerpt;
    }

    @Override
    public void mapJSON(JSONObject object) {
        try {
            id = object.getInt("id");
            date = object.getString("date");
            dateGMT = object.getString("date_gmt");
            title = object.getJSONObject("title").getString("rendered");
            content = object.getJSONObject("content").getString("rendered");
            exerpt = object.getJSONObject("exerpt").getString("rendered");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSON() {
        return null;
    }
}
