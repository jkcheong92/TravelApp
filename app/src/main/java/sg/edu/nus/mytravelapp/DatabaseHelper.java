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
    public static final String BUDGET_TABLE = "Budget_table";
    public static final String ID_COL = "ID";
    public static final String FOOD_COL = "FOOD";
    public static final String TRAVEL_COL = "TRAVEL";
    public static final String ACCOMODATION_COL = "ACCOMODATION";
    public static final String PLAY_COL = "PLAY";
    public static final String SHOPPING_COL = "SHOPPING";
    public static final String BUDGET_COL = "BUDGET";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("DROP TABLE IF EXISTS" + BUDGET_TABLE);
        db.execSQL("CREATE TABLE " + BUDGET_TABLE + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FOOD DOUBLE NOT NULL, " +
                "TRAVEL DOUBLE NOT NULL, " +
                "ACCOMODATION DOUBLE NOT NULL, " +
                "PLAY DOUBLE NOT NULL, " +
                "SHOPPING DOUBLE NOT NULL, " +
                "BUDGET INTEGER NOT NULL" +
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + BUDGET_TABLE);
        onCreate(db);
    }
}
