package org.judocanada.judocanadamobileappandroid;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lspoulin on 2018-05-07.
 */

class Post implements Mappable, Parcelable{
    private int id;
    private String date;
    private String dateGMT;
    private String title;
    private String content;
    private String excerpt;


    public Post(){

    }

    public Post(int id, String title, String content, String excerpt, String date, String dateGMT){
        this.id = id;
        this.title = title;
        this.date = date;
        this.dateGMT = dateGMT;
        this.content = content;
        this.excerpt = excerpt;
    }


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

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }



    @Override
    public void mapJSON(JSONObject object) {
        try {
            id = object.getInt("id");
            date = object.getString("date");
            dateGMT = object.getString("date_gmt");
            title = object.getJSONObject("title").getString("rendered");
            content = object.getJSONObject("content").getString("rendered");
            excerpt = object.getJSONObject("excerpt").getString("rendered");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(excerpt);
        parcel.writeString(date);
        parcel.writeString(dateGMT);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Creator<Post>() {

        @Override
        public Post createFromParcel(Parcel parcel) {
            Post post = new Post();
            post.setId(parcel.readInt());
            post.setTitle(parcel.readString());
            post.setContent(parcel.readString());
            post.setExcerpt(parcel.readString());
            post.setDate(parcel.readString());
            post.setDateGMT(parcel.readString());

            return post;
        }

        @Override
        public Post[] newArray(int i) {
            return new Post[i];
        }
    };
}
