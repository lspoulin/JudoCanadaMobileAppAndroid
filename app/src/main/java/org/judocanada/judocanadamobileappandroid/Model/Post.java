package org.judocanada.judocanadamobileappandroid.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lspoulin on 2018-05-07.
 */

public class Post implements Mappable, Parcelable{
    private final String GET_URL_RE = "(http|ftp|https):[/][/]([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
    private final String IS_IMAGE_RE = "^[^?]*\\.(jpg|jpeg|gif|png)";
    private final String REMOVE_AHREF = "<a.*?</a>";

    private List<String> imageList;

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
        setContent(content);
        this.excerpt = excerpt;
        this.imageList = getImageListFromContent(content);
    }

    private List<String> getImageListFromContent(String content) {
        List<String> imageList = new ArrayList<String>();
        Matcher m = Pattern.compile(GET_URL_RE)
                .matcher(this.content);
        while (m.find()) {
            if (m.group().matches(IS_IMAGE_RE))
                imageList.add(m.group());
        }
        return imageList;
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
        this.content = content.replaceAll(REMOVE_AHREF, "");
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }



    @Override
    public void mapJSON(JSONObject object) {
        try {
            id = object.getInt("id");
            date = object.getString("date");
            dateGMT = object.getString("date_gmt");
            title = object.getJSONObject("title").getString("rendered");
            content =object.getJSONObject("content").getString("rendered");
            excerpt = object.getJSONObject("excerpt").getString("rendered");
            this.imageList = getImageListFromContent(content);
            setContent(content);

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
        parcel.writeStringList(imageList);
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
            List<String> list = new ArrayList<String>();
            parcel.readStringList(list);
            post.setImageList(list );

            return post;
        }

        @Override
        public Post[] newArray(int i) {
            return new Post[i];
        }
    };
}
