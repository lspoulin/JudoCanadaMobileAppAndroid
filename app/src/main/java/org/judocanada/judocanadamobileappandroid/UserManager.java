package org.judocanada.judocanadamobileappandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lspoulin on 2018-05-25.
 */

public class UserManager {
    public final static String PREFERENCES = "JudoCanadaPreferences";
    public final static String USERID = "JudoCanadaPreferencesUserid";
    public final static String USERNAME = "JudoCanadaPreferencesUsername";
    public final static String NAME = "JudoCanadaPreferencesName";
    public final static String FIRSTNAME = "JudoCanadaPreferencesFirstname";
    public final static String EMAIL = "JudoCanadaPreferencesEmail";
    public final static String DATEOFBIRTH = "JudoCanadaPreferencesDateofbirth";
    public final static String JUDOCANADAID = "JudoCanadaPreferencesJudoCanadaId";

    private static UserManager instance;
    private User user;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public static UserManager getInstance(){
        if (instance == null){
            instance = new UserManager();
        }
        return instance;
    }
    private UserManager(){

    }

    public void saveUserToPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(user!=null){
            editor.putInt(USERID, user.getId());
            editor.putString(USERNAME, user.getUsername());
            editor.putString(NAME, user.getName());
            editor.putString(FIRSTNAME, user.getFirstname());
            editor.putString(EMAIL, user.getEmail());
            editor.putString(DATEOFBIRTH, user.getEmail());
            editor.putInt(JUDOCANADAID, user.getJudoCanadaId());
            editor.commit();
        }
    }

    public void deleteUserToPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(user!=null){
            editor.remove(USERID);
            editor.remove(USERNAME);
            editor.remove(NAME);
            editor.remove(FIRSTNAME);
            editor.remove(EMAIL);
            editor.remove(DATEOFBIRTH);
            editor.remove(JUDOCANADAID);
            editor.commit();
        }
    }

    public User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String username = preferences.getString(USERNAME, "");
        User user = null;
        if (!username.equals("")) {
            user = new User();
            user.setUsername(preferences.getString(USERNAME, ""));
            user.setName(preferences.getString(NAME, ""));
            user.setEmail(preferences.getString(EMAIL, ""));
            user.setFirstname(preferences.getString(FIRSTNAME, ""));
            user.setDateofbirth(preferences.getString(DATEOFBIRTH, ""));
            user.setJudoCanadaId(preferences.getInt(JUDOCANADAID, -1));
            user.setId(preferences.getInt(USERID, -1));
            this.user = user;
        }

        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
