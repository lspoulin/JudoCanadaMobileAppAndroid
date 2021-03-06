package org.judocanada.judocanadamobileappandroid.Api;

import android.content.Context;

import org.judocanada.judocanadamobileappandroid.Model.Event;
import org.judocanada.judocanadamobileappandroid.Model.Post;
import org.judocanada.judocanadamobileappandroid.Model.Product;
import org.judocanada.judocanadamobileappandroid.Model.User;
import org.judocanada.judocanadamobileappandroid.Model.VideoList;

/**
 * Created by lspoulin on 2018-05-08.
 */

public class ApiHelper {
    private ApiManager<Post> apiManagerPost;
    private ApiManager<VideoList> apiManagerVideo;
    private ApiManager<User> apiManagerUser;
    private ApiManager<Event> apiManagerEvent;
    private ApiManager<Product> apiManagerProduct;


    public ApiHelper(Context context){
        try {
            apiManagerPost = new ApiManager<Post>(Post.class, context);
            apiManagerVideo = new ApiManager<VideoList>(VideoList.class, context);
            apiManagerUser = new ApiManager<User>(User.class, context);
            apiManagerEvent = new ApiManager<Event>(Event.class, context);
            apiManagerProduct = new ApiManager<Product>(Product.class, context);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void getPosts(Context context, Callback callBack){
        apiManagerPost.getReturnMappableArray(ApiManager.getPostURL(), callBack);
    }

    public void getVideos(Context context, Callback callback){
        apiManagerVideo.getReturnMappable(ApiManager.getVideoList(), callback);
    }

    public void getUser(Context context, Callback callback){
        apiManagerUser.getReturnMappableArray(ApiManager.getUserURL(), callback);
    }

    public void postUser(User user, Context context, Callback callback){
        apiManagerUser.postReturnId(ApiManager.getUserURL(),context, callback, user);
    }

    public void getEvents(Context context, Callback callback){
        apiManagerEvent.getReturnMappableArray(ApiManager.getEventURL(), callback);
    }

    public void getProducts(Context context, Callback callback){
        apiManagerProduct.getReturnMappableArray(ApiManager.getProductURL(), callback);
    }
}
