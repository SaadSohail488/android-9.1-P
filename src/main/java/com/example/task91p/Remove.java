package com.example.task91p;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task91p.R;

public class Remove extends AppCompatActivity {

    private TextView Head;
    private TextView Detail;
    private DatabaseClass databaseClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        // Initialize the DatabaseClass
        databaseClass = new DatabaseClass(this);

        // Get references to the TextViews in the layout
        Head = findViewById(R.id.head);
        Detail = findViewById(R.id.detail);

        // Get the ID passed from the previous activity
        int id = getIntent().getIntExtra("id", -1);

        if (id != -1) {
            // Retrieve the data based on the ID
            DATA data = databaseClass.getDataById(id);

            if (data != null) {
                // Set the head text to display the type of lost or found item and its name
                Head.setText(data.getIsLostOrFound() + ": " + data.getName());

                // Build the detail text to display date, location, phone, and description
                StringBuilder detailBuilder = new StringBuilder();
                detailBuilder.append(data.getDate()).append("\n");
                detailBuilder.append(data.getLocation()).append("\n");
                detailBuilder.append(data.getPhone()).append("\n");
                detailBuilder.append(data.getDescription());
                Detail.setText(detailBuilder.toString());
            }
        }

        // Get a reference to the remove button
        Button removeButton = findViewById(R.id.remove);
        // Set an onClickListener for the remove button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != -1) {
                    // Delete the data from the database based on the ID
                    databaseClass.deleteDataById(id);
                    // Finish the activity and return to the previous activity
                    finish();
                }
            }
        });
    }
}
