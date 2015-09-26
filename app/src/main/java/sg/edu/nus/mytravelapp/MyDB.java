package sg.edu.nus.mytravelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

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
    public boolean insertData(Double cost) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(dbHelper.COL_2, cost);

        long result = db.insert(dbHelper.TABLE_NAME, null, initialValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int updateData(long id, Double cost) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(dbHelper.COL_2, cost);
        return db.update(dbHelper.TABLE_NAME, initialValues, dbHelper.COL_1 + "=" + id, null);
    }


    // QUERY
    public Cursor getAllData() {
        String[] columns = {dbHelper.COL_1, dbHelper.COL_2};
        Cursor mCursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);

        return mCursor;
    }

    public Cursor getData(long id) {
        Cursor mCursor = db.query(dbHelper.TABLE_NAME,
                new String[] {
                        dbHelper.COL_1,
                        dbHelper.COL_2},
                dbHelper.COL_1 + "=" + id,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // DELETE
    public int deleteAllRecords() {
        int rowNum = db.delete(dbHelper.TABLE_NAME, null, null);
        return rowNum;
    }

    public int deleteRecord(long id) {
        return  db.delete(dbHelper.TABLE_NAME, dbHelper.COL_1 + "=" + id, null);
    }
}
