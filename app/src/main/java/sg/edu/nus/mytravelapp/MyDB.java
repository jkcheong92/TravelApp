package sg.edu.nus.mytravelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Asus on 9/26/2015.
 */
public class MyDB {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    final Context context;

    public MyDB(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(this.context);
    }

    /* Open, Close DatabaseHelper */
    public MyDB open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /* METHODS TO WRITE AND QUERY INTO DATABASE */
    // INSERT
    public boolean insertData(Double food, Double travel, Double accomodation, Double play, Double shopping, int budget) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(dbHelper.FOOD_COL, food);
        initialValues.put(dbHelper.TRAVEL_COL, travel);
        initialValues.put(dbHelper.ACCOMODATION_COL, accomodation);
        initialValues.put(dbHelper.PLAY_COL, play);
        initialValues.put(dbHelper.SHOPPING_COL, shopping);
        initialValues.put(dbHelper.BUDGET_COL, budget);

        long result = db.insert(dbHelper.BUDGET_TABLE, null, initialValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int updateData(long id, Double food,Double travel, Double accomodation, Double play, Double shopping) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(dbHelper.FOOD_COL, food);
        initialValues.put(dbHelper.TRAVEL_COL, travel);
        initialValues.put(dbHelper.ACCOMODATION_COL, accomodation);
        initialValues.put(dbHelper.PLAY_COL, play);
        initialValues.put(dbHelper.SHOPPING_COL, shopping);

        return db.update(dbHelper.BUDGET_TABLE, initialValues, dbHelper.ID_COL + "=" + id, null);
    }


    // QUERY
    public Cursor getAllData() {
        String[] columns = {dbHelper.ID_COL, dbHelper.FOOD_COL, dbHelper.TRAVEL_COL, dbHelper.ACCOMODATION_COL, dbHelper.PLAY_COL, dbHelper.SHOPPING_COL, dbHelper.BUDGET_COL};
        Cursor mCursor = db.query(dbHelper.BUDGET_TABLE, columns, null, null, null, null, null);

        return mCursor;
    }

    public Cursor getData(long id) {
        Cursor mCursor = db.query(dbHelper.BUDGET_TABLE,
                new String[] {
                        dbHelper.ID_COL,
                        dbHelper.FOOD_COL,
                        dbHelper.TRAVEL_COL,
                        dbHelper.ACCOMODATION_COL,
                        dbHelper.PLAY_COL,
                        dbHelper.SHOPPING_COL,
                        dbHelper.BUDGET_COL
                },
                dbHelper.ID_COL + "=" + id,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getBudget() {
        Cursor mCursor = db.query(dbHelper.BUDGET_TABLE,
                new String[] {
                        dbHelper.ID_COL,
                        dbHelper.FOOD_COL,
                        dbHelper.TRAVEL_COL,
                        dbHelper.ACCOMODATION_COL,
                        dbHelper.PLAY_COL,
                        dbHelper.SHOPPING_COL,
                        dbHelper.BUDGET_COL
                },
                dbHelper.BUDGET_COL + "=" + 1,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // DELETE
    public int deleteAllRecords() {
        int rowNum = db.delete(dbHelper.BUDGET_TABLE, null, null);
        return rowNum;
    }

    public int deleteRecord(long id) {
        return  db.delete(dbHelper.BUDGET_TABLE, dbHelper.ID_COL + "=" + id, null);
    }
}
