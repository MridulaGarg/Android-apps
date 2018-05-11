package com.example.grocery.grocerylist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.grocery.grocerylist.Model.Grocery;
import com.example.grocery.grocerylist.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{

    private Context ctx;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_GROCERY_TABLE = "create table " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " integer primary key,"
                + Constants.KEY_GROCERY_ITEM + " text," + Constants.KEY_QTY_NUMBER + " texr," + Constants.KEY_DATE_NAME + " long);";

        sqLiteDatabase.execSQL(CREATE_GROCERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists " + Constants.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }

    /**
     * CRUD operations : Create, Read, Update, Delete
     */

    //add grocery
    public void addGrocery(Grocery grocery) {

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        contentValues.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        contentValues.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //insert the row
        db.insert(Constants.TABLE_NAME, null, contentValues);

        Log.d("Saved!!", "Saved to DB");

    }

    //get a grocery item
    public Grocery getGrocery(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM,
                Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME}, Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            //convert time stamp to something readablre
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());

            grocery.setDateItemadded(formatedDate);

        return grocery;

    }

    //get all groceries
    public List<Grocery> getAllGroceries() {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> groceryListt = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM,
                Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME}, null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {

            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());

                grocery.setDateItemadded(formatedDate);

                //add to the grocery list
                groceryListt.add(grocery);

            }while (cursor.moveToNext());
        }

        return groceryListt;

    }

    //update grocery
    public int updateGrocery(Grocery grocery) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        contentValues.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        contentValues.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); //get system time

        //update row
         return db.update(Constants.TABLE_NAME, contentValues, Constants.KEY_ID + "=?", new String[] {String.valueOf(grocery.getId())});

    }

    //delete grocery
    public void deleteGrocery(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[] {String .valueOf(id)});

        db.close();
    }

    //count grocery items
    public int getCount() {

        String countQuery = "select * from " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        return count;

    }

}