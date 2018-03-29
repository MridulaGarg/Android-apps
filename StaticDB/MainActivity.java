package com.example.db.dbintro;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

       Log.d("DB Count : ", String.valueOf(db.getContactsCount()));

        //insert contacts
        Log.d("Insert : ", "Inserting...");
        /*db.addContact(new Contact("Paul", "89787644"));
        db.addContact(new Contact("John", "654326576"));
        db.addContact(new Contact("Rose", "32435646"));
        db.addContact(new Contact("Belle","335467576"));*/

        //read them back
        Log.d("Reading : ", "Reading all contacts...");

        //get one contact
        Contact oneContact = db.getContact(3);
        //oneContact.setName("Mridula");

        //updated contact
        //int newContact = db.updateContact(oneContact);

        //Log.d("One contact : " , "Id : " + String.valueOf(newContact) + " Name : " + oneContact.getName() + " Phone : " + oneContact.getPhoneNumber());

       // db.deleteContact(oneContact);
        List<Contact> contactList = db.getAllContacts();

        for (Contact c : contactList) {
            String log = "ID : " + c.getId() + ", Name : " + c.getName() + ", Phone : " + c.getPhoneNumber();

            Log.d("Name : " , log);
        }

    }
}
