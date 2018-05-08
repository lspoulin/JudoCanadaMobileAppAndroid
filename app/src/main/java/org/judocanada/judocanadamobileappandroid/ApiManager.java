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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by lspoulin on 2018-05-07.
 */

public class ApiManager <T extends Mappable>{

    public static final String BASE_URL = "http://judocanada.org/wp-json/";
    public static final String POST_ENDPOINT = "wp/v2/posts";

    // This is a convoluted way to create instances of the template type
    private final Constructor<? extends T> ctor;

    private T field;

    ApiManager(Class<? extends T> impl) throws NoSuchMethodException {
        this.ctor = impl.getConstructor();
    }

    public static String getPostURL(){
        return BASE_URL+POST_ENDPOINT;
    }

    public void getReturnMappableArray(String url, final Context context, final Callback callBack){
        StringRequest requete = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            ArrayList<Mappable> objects = new ArrayList<Mappable>();
                            for(i=0;i<jsonResponse.length();i++) {
                                field = ctor.newInstance();
                                field.mapJSON(jsonResponse.getJSONObject(i));
                                objects.add(field);
                            }
                            callBack.methodToCallBack(objects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
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
