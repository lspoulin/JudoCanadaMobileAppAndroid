package org.judocanada.judocanadamobileappandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lspoulin on 2018-05-07.
 */

public class ApiManager {

    public static final String BASE_URL = "http://judocanada.org/";
    public static final String POST_ENDPOINT = "wp-json/wp/v2/posts";

    public static String getPostURL(){
        return BASE_URL+POST_ENDPOINT;
    }

    public void getPosts(final Context context, final CallBack callBack){

        StringRequest requete = new StringRequest(Request.Method.GET, getPostURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            ArrayList<Post> posts = new ArrayList<Post>();
                            Post post = new Post();
                            for(i=1;i<jsonResponse.length();i++) {
                                post = new Post();
                                post.mapJSON(jsonResponse.getJSONObject(i));
                                posts.add(post);
                            }

                            callBack.methodToCallBack(posts);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {};
        Volley.newRequestQueue(context).add(requete);

    }

}
