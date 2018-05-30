package org.judocanada.judocanadamobileappandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.judocanada.judocanadamobileappandroid.Model.Mappable;
import org.judocanada.judocanadamobileappandroid.Model.Video;

import java.util.ArrayList;

/**
 * Created by lspoulin on 2018-05-09.
 */

public class VideoList implements Mappable {
    int page;
    int limit;
    boolean hasMore;
    ArrayList<Video> videos;

    public VideoList(){
        videos = new ArrayList<Video>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    @Override
    public void mapJSON(JSONObject object) {
        try {
            this.page=object.getInt("page");
            this.limit=object.getInt("limit");
            this.hasMore=object.getBoolean("has_more");
            if(videos== null)
                videos = new ArrayList<Video>();
            JSONArray jsonArrayVideos = object.getJSONArray("list");
            for(int i=0;i<jsonArrayVideos.length();i++) {
                Video video = new Video();
                video.mapJSON(jsonArrayVideos.getJSONObject(i));
                videos.add(video);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSON() {
        return null;
    }
}
