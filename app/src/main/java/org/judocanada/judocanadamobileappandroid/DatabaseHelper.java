package org.judocanada.judocanadamobileappandroid;

/**
 * Created by lspoulin on 2018-05-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "judocanada_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertUser(String name, String firstname, String email, String dateofBirth, int judoCanadaid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME, name);
        values.put(User.COLUMN_FIRSTNAME, firstname);
        values.put(User.COLUMN_EMAIL, email);
        values.put(User.COLUMN_DATEOFBIRTH, dateofBirth);
        values.put(User.COLUMN_JUDOCANADAID, judoCanadaid);
        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_FIRSTNAME, user.getFirstname());
        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_JUDOCANADAID, user.getJudoCanadaId());

        // updating row
        return db.update(User.TABLE_NAME, values, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public int deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(User.TABLE_NAME, User.COLUMN_ID + "= ?", new String[]{id+""});

    }
}
