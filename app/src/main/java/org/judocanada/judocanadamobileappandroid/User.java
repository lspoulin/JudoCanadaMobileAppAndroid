package org.judocanada.judocanadamobileappandroid;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lspoulin on 2018-05-11.
 */

class User implements Mappable, Parcelable {
    private int id, judoCanadaId;
    private String name, firstname, email;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DATEOFBIRTH = "dateofbirth";
    public static final String COLUMN_JUDOCANADAID = "judocanadaid";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " VARCHAR(50),"
                    + COLUMN_FIRSTNAME + " VARCHAR(50),"
                    + COLUMN_EMAIL + " VARCHAR(50),"
                    + COLUMN_DATEOFBIRTH + " VARCHAR(50),"
                    + COLUMN_JUDOCANADAID + " INTEGER,"
                    + ")";

    public User(){

    }

    public User(int id, String name, String firstname, String email) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJudoCanadaId() {
        return judoCanadaId;
    }

    public void setJudoCanadaId(int judoCanadaId) {
        this.judoCanadaId = judoCanadaId;
    }

    @Override
    public void mapJSON(JSONObject object) {
        try {
            id = object.getInt("id");
            name = object.getString("name");
            firstname = object.getString("firstname");
            email = object.getString("email");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(firstname);
        parcel.writeString(email);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            User user = new User();
            user.setId(parcel.readInt());
            user.setName(parcel.readString());
            user.setFirstname(parcel.readString());
            user.setEmail(parcel.readString());

            return user;
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
}
