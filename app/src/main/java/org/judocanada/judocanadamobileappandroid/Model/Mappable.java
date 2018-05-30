package org.judocanada.judocanadamobileappandroid.Model;

import android.annotation.SuppressLint;

import org.json.JSONObject;

/**
 * Created by lspoulin on 2018-05-07.
 */

public interface Mappable {

    void mapJSON(JSONObject object);
    String toJSON();
}
