package com.example.task91p;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task91p.R;

import java.util.ArrayList;
import java.util.List;

public class Show extends AppCompatActivity implements SetOnItemClickListener {

    private RecyclerView recyclerView; // RecyclerView to display the data
    private Adapter adapter; // Adapter for the RecyclerView
    private DatabaseClass databaseClass; // Database helper class
    private TextView noData; // TextView to display a message when no data is available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        databaseClass = new DatabaseClass(this); // Create an instance of the database helper class
        recyclerView = findViewById(R.id.lostAndFound); // Get the RecyclerView from the layout file
        noData = findViewById(R.id.noData); // Get the TextView for displaying "no data" message

        adapter = new Adapter(this); // Create an instance of the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager for the RecyclerView
        recyclerView.setAdapter(adapter); // Set the adapter for the RecyclerView
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<DATA> dataList = databaseClass.getData(); // Get the data from the database
        ArrayList<DATA> list = new ArrayList<>(dataList); // Create an ArrayList from the data
        if (list.isEmpty()) {
            noData.setVisibility(View.VISIBLE); // Show the "no data" message if the list is empty
        } else {
            noData.setVisibility(View.GONE); // Hide the "no data" message if the list is not empty
        }

        adapter.submit(list); // Submit the data to the adapter for display
    }

    @Override
    public void onItemClickListener(DATA DATA) {
        Intent intent = new Intent(Show.this, Remove.class); // Create an intent to open the Remove activity
        intent.putExtra("id", DATA.getId()); // Pass the ID of the clicked item to the Remove activity
        startActivity(intent); // Start the Remove activity
    }
}
