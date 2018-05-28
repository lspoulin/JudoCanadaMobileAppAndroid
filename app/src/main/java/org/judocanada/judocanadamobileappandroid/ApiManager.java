package org.judocanada.judocanadamobileappandroid;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
    public static final String POST_ENDPOINT = "posts";
    public static final String VIDEO_LIST_ENDPOINT = "user/JudoCanada/videos";
    public static final String USER_ENDPOINT = "users/";
    public static final String VIDEO_THUMBNAIL_ENDPOINT = "thumbnail/video/";


    // This is a convoluted way to create instances of the template type
    private final Constructor<? extends T> ctor;
    private Context context;
    private T field;

    ApiManager(Class<? extends T> impl, Context context) throws NoSuchMethodException {
        this.context = context;
        this.ctor = impl.getConstructor();
    }

    public static String getPostURL(){
        return BuildConfig.BASE_URL+POST_ENDPOINT;
    }

    public static String getUserURL(String id){return  BuildConfig.USER_BASE_URL+USER_ENDPOINT+id;}
    public static String getUserURL(){return  BuildConfig.USER_BASE_URL+USER_ENDPOINT;}

    public static String getVideoList(){return BuildConfig.VIDEO_BASE_URL+VIDEO_LIST_ENDPOINT;}

    public static String getThumbnailURL(String id) {
        return BuildConfig.DAILYMOTION_BASE_URL + VIDEO_THUMBNAIL_ENDPOINT+id;
    }

    public void getReturnMappableArray(String url, final Callback callBack){
        field = null;
        StringRequest requete = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Mappable value = null;
                        ArrayList<Mappable> objects = new ArrayList<Mappable>();
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);

                            for(i=0;i<jsonResponse.length();i++) {
                                field = ctor.newInstance();
                                field.mapJSON(jsonResponse.getJSONObject(i));
                                value = field;
                                objects.add(value);
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
                        Toast.makeText(context, context.getResources().getString(R.string.no_internet_message), Toast.LENGTH_LONG).show();
                        callBack.methodToCallBack(null);
                    }
                }
        ) {};
        Volley.newRequestQueue(context).add(requete);

    }


    public void getReturnMappable(String url, final Callback callBack){
        field = null;
        StringRequest requete = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Mappable callbackValue = null;
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONObject jsonResponse = new JSONObject(response);
                            field = ctor.newInstance();
                            field.mapJSON(jsonResponse);
                            callbackValue = field;
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
                            callBack.methodToCallBack(callbackValue);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getResources().getString(R.string.no_internet_message), Toast.LENGTH_LONG).show();
                        callBack.methodToCallBack(null);
                    }
                }
        ) {};
        Volley.newRequestQueue(context).add(requete);
    }

    public void postReturnId(String url, Context context,  final Callback callBack, Mappable mappable){
        try{
            final JSONObject jsonBody = new JSONObject(mappable.toJSON());

            JsonObjectRequest requete = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String resp = "";
                    try {
                        resp = response.get("id").toString();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finally {
                        callBack.methodToCallBack(resp);
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
        }catch(Exception e){}

    }


}
