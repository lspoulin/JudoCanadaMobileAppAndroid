package org.judocanada.judocanadamobileappandroid;

import android.content.Context;

/**
 * Created by lspoulin on 2018-05-08.
 */

public class ApiHelper {
    private ApiManager<Post> apiManagerPost;
    private ApiManager<VideoList> apiManagerVideo;

    public ApiHelper(){
        try {
            apiManagerPost = new ApiManager<Post>(Post.class);
            apiManagerVideo = new ApiManager<VideoList>(VideoList.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void getPosts(Context context, Callback callBack){
        apiManagerPost.getReturnMappableArray(ApiManager.getPostURL(),context, callBack);
    }

    public void getVideos(Context context, Callback callback){
        apiManagerVideo.getReturnMappable(ApiManager.getVideoList(), context, callback);
    }
}
