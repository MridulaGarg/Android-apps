package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATEBASE_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //SQL
        String CREATE_CONTACT_TABLE = "create table " + Util.TABLE_NAME + "(" + Util.KEY_ID
                + " integer primary key," + Util.KEY_NAME + " text," + Util.KEY_PHONE_NUMBER
                + " text" + ")";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists " + Util.TABLE_NAME);

        //create table again
        onCreate(sqLiteDatabase);

    }

    /**
    * CRUD operations : Create, Read, Update, Delete
     */

    //add contact
    public void  addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //insert to a row
        db.insert(Util.TABLE_NAME, null, value);
        db.close();
    }

    //get a contact
    public Contact getContact(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[] { Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null,
                null );

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return  contact;
    }

    //get all contacts
    public List<Contact> getAllContacts() {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Contact> contactList = new ArrayList<>();

        //select all contacts
        String selectAll = "select * from " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //loop through our contacts
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //add contact object to our list
                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        return contactList;
    }

    //update contact
    public int updateContact(Contact c) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, c.getName());
        contentValues.put(Util.KEY_PHONE_NUMBER, c.getPhoneNumber());

        //update row
        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[] {String.valueOf(c.getId())});

    }

    //delete one contact
    public void  deleteContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?", new String[] {String.valueOf(c.getId())});

        db.close();
    }

    //get contacts count
    public int getContactsCount() {

        String countQuery = "select * from " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}