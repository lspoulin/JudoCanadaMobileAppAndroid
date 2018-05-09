package org.judocanada.judocanadamobileappandroid;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lspoulin on 2018-05-09.
 */

public class Video implements Mappable, Parcelable {
    String id, title, chanel, owner;

    public Video(){

    }

    public Video(String id, String title, String chanel, String owner){
        this.id = id;
        this.title = title;
        this.chanel = chanel;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public void mapJSON(JSONObject object) {
        try {
            id = object.getString("id");
            title = object.getString("title");
            chanel = object.getString("chanel");
            owner = object.getString("owner");

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
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(chanel);
        parcel.writeString(owner);
    }

    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {

        @Override
        public Video createFromParcel(Parcel parcel) {
            Video video = new Video();
            video.setId(parcel.readString());
            video.setTitle(parcel.readString());
            video.setChanel(parcel.readString());
            video.setOwner(parcel.readString());

            return video;
        }

        @Override
        public Video[] newArray(int i) {
            return new Video[i];
        }
    };
}
