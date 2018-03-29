package com.example.honeylist.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = (Button) findViewById(R.id.button);
        list = (EditText) findViewById(R.id.editText2);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logic goes here
                if( !list.getText().toString().equals("")) {
                    String message = list.getText().toString();
                    writeToFile(message);
                } else {
                    //donothing for now
                }
            }
        });

        try {
            if (readFromFile() != null) {
                list.setText(readFromFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String message) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("todolist.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(message);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() throws IOException {

        String result = "";

        InputStream inputStream = openFileInput("todolist.txt");

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String temp = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (temp = bufferedReader.readLine()) !=null ) {
                stringBuilder.append(temp + "\n");
            }

            inputStream.close();
            result = stringBuilder.toString();
        }

        return result;
    }
}
