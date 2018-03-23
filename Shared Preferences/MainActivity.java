package com.example.sharedata.sharedpref;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button saveButton;
    private TextView result;
    private EditText enterMsg;
    private SharedPreferences myPrefs;
    private static final String PREFS_NAME = "myPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterMsg = (EditText) findViewById(R.id.enterName);
        saveButton = (Button) findViewById(R.id.saveButton);
        result = (TextView) findViewById(R.id.resultText);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPrefs = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = myPrefs.edit();

                editor.putString("message", enterMsg.getText().toString());
                editor.commit();

            }
        });

        //GET DATA BACK
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);

        if(prefs.contains("message")) {
            String msg = prefs.getString("message", "not found");
            result.setText("Message : " + msg);
        }
    }
}
