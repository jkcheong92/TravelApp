package sg.edu.nus.mytravelapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Asus on 9/26/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // TODO: Define more columns as necessary
    public static final String DATABASE_NAME = "Travel.db";
    public static final String TABLE_NAME = "Budget_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TOTAL";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TOTAL DOUBLE NOT NULL" +
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
