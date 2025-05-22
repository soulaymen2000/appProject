package com.example.appprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojet.R;
import com.example.appprojet.models.Event;
import com.example.appprojet.utils.DBHelper;

public class AddEditEventActivity extends AppCompatActivity {

    EditText titleInput, descriptionInput;
    Button saveButton;
    DBHelper db;
    int eventId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        titleInput = findViewById(R.id.event_title);
        descriptionInput = findViewById(R.id.event_description);
        saveButton = findViewById(R.id.btn_save_event);
        db = new DBHelper(this);

        Intent intent = getIntent();
        if (intent.hasExtra("event_id")) {
            eventId = intent.getIntExtra("event_id", -1);
            Event event = db.getEventById(eventId);
            if (event != null) {
                titleInput.setText(event.title);
                descriptionInput.setText(event.description);
            }
        }

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (eventId == -1) {
                db.insertEvent(title, description);
                Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
            } else {
                db.updateEvent(eventId, title, description);
                Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
