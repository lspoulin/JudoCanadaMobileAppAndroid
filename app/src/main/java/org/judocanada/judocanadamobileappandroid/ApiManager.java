package org.judocanada.judocanadamobileappandroid;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by lspoulin on 2018-05-07.
 */

public class ApiManager <T extends Mappable>{

    public static final String BASE_URL = "http://judocanada.org/wp-json/";
    public static final String POST_ENDPOINT = "wp/v2/posts";
    public static final String VIDEO_BASE_URL = "https://api.dailymotion.com";
    public static final String VIDEO_ENDPOINT = "/video/";
    public static final String VIDEO_LIST_ENDPOINT = "/user/JudoCanada/videos";


    // This is a convoluted way to create instances of the template type
    private final Constructor<? extends T> ctor;

    private T field;

    ApiManager(Class<? extends T> impl) throws NoSuchMethodException {
        this.ctor = impl.getConstructor();
    }

    public static String getPostURL(){
        return BASE_URL+POST_ENDPOINT;
    }

    public static String getVideoURI(String id){return  "http://www.dailymotion.com/embed/video/"+id;}

    public static String getVideoList(){return VIDEO_BASE_URL+VIDEO_LIST_ENDPOINT;}

    public void getReturnMappableArray(String url, final Context context, final Callback callBack){
        field = null;
        StringRequest requete = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Mappable> objects = new ArrayList<Mappable>();
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);

                            for(i=0;i<jsonResponse.length();i++) {
                                field = ctor.newInstance();
                                field.mapJSON(jsonResponse.getJSONObject(i));
                                objects.add(field);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        finally {
                            if(objects.size()>0)
                                callBack.methodToCallBack(objects);
                            else
                                callBack.methodToCallBack(null);
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


    public void getReturnMappable(String url, final Context context, final Callback callBack){
        field = null;
        StringRequest requete = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONObject jsonResponse = new JSONObject(response);
                            field = ctor.newInstance();
                            field.mapJSON(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        finally {
                            callBack.methodToCallBack(field);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.methodToCallBack(null);
                    }
                }
        ) {};
        Volley.newRequestQueue(context).add(requete);

    }




}
