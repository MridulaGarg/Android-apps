package com.example.recyclerview.recyclerviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView name, description, rating;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        extras = getIntent().getExtras();

        name = (TextView) findViewById(R.id.dNameID);
        description = (TextView) findViewById(R.id.dDescriptionID);
        rating = (TextView) findViewById(R.id.dRatingID);

        if(extras !=  null)
        {
            name.setText(extras.getString("Name"));
            description.setText(extras.getString("Description"));
            rating.setText(extras.getString("Rating"));
        }
    }
}
