package org.judocanada.judocanadamobileappandroid;

import android.annotation.SuppressLint;

import org.json.JSONObject;

/**
 * Created by lspoulin on 2018-05-07.
 */

interface Mappable {

    void mapJSON(JSONObject object);
    String toJSON();
}
