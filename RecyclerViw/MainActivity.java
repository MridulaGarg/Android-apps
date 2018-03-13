package com.example.recyclerview.recyclerviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.ListItem;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        //ListItem item1 = new ListItem("Movie 1", "It's about someone crazy...", "Wow! Great!")

        for (int i = 0 ; i < 10 ; i++) {
            ListItem item = new ListItem(
                    "Item" + (i+1),
                    "Description",
                    "Satisfactory"
            );

            listItems.add(item);

        }

        adapter = new MyAdapter(this, listItems);
        recyclerView.setAdapter(adapter);

    }
}
