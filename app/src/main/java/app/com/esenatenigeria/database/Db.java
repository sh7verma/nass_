package app.com.esenatenigeria.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Applify on 8/11/2016.
 */
public class Db extends SQLiteOpenHelper {

    static final int dbversion = 1;
    public static final String DATABASE = "nass_local";

    public Db(Context context) {
        super(context, DATABASE, null, dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String profile_qry = "create table if not exists " + PROFILE_TABLE
//                + " (" + USER_ID + " TEXT " + " ," + NAME + " TEXT" + " ,"
//                + NUMBER + " TEXT" + " ," + COUNTRY_CODE + " TEXT"
//                + " ," + PROFILE_PIC + " TEXT" + " ," + LANGUAGE
//                + " TEXT" + " ," + STATUS + " TEXT" + " ," + ONLINE_STATUS + " TEXT"
//                + " ," + PUSH_TOKEN + " TEXT ," + CURRENT_USER + " TEXT ," + LINK_PHONE_ID + " TEXT ,"
//                + ACCESS_TOKEN + " TEXT ," + DOB + " TEXT ," + GENDER + " Text ," + THEME_COLOR + " TEXT ,"
//                + EMAIL + " Text ," + YAPYAP_ID + " TEXT ," + PROFILE_PIC_THUMB + " TEXT )";
//        db.execSQL(profile_qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void delete_records() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

//            String qry = "DELETE FROM " + PROFILE_TABLE + " ";
//            db.execSQL(qry);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
