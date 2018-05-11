package com.example.grocery.grocerylist.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.grocery.grocerylist.R;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

        private TextView itemName;
        private TextView quantity;
        private TextView dateAdded;
        private int groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemName = (TextView) findViewById(R.id.itemNameDetail);
        quantity = (TextView) findViewById(R.id.quantityDetail);
        dateAdded = (TextView)findViewById(R.id.dateAddedDetail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryId = bundle.getInt("id");
        }
    }
}
