package com.example.appprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojet.R;
import com.example.appprojet.models.Event;
import com.example.appprojet.utils.DBHelper;

public class AddEditEventActivity extends AppCompatActivity {

    EditText titleInput, descriptionInput, dateInput;
    ImageView imageView;
    Button saveButton, pickImageButton;
    DBHelper db;
    int eventId = -1;
    String imageUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        titleInput = findViewById(R.id.event_title);
        descriptionInput = findViewById(R.id.event_description);
        dateInput = findViewById(R.id.event_date);
        imageView = findViewById(R.id.event_image);
        pickImageButton = findViewById(R.id.btn_pick_image);
        saveButton = findViewById(R.id.btn_save_event);
        db = new DBHelper(this);

        Intent intent = getIntent();
        if (intent.hasExtra("event_id")) {
            eventId = intent.getIntExtra("event_id", -1);
            Event event = db.getEventById(eventId);
            if (event != null) {
                titleInput.setText(event.title);
                descriptionInput.setText(event.description);
                dateInput.setText(event.date);
                imageUri = event.imageUri;
                if (!imageUri.isEmpty()) imageView.setImageURI(android.net.Uri.parse(imageUri));
            }
        }

        pickImageButton.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, 101);
        });

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();
            String date = dateInput.getText().toString();

            if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (eventId == -1) {
                db.insertEvent(title, description, date, imageUri);
                Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
            } else {
                db.updateEvent(eventId, title, description, date, imageUri);
                Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            android.net.Uri uri = data.getData();
            if (uri != null) {
                imageUri = uri.toString();
                imageView.setImageURI(uri);
                // Persist URI permission for future access
                final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                try {
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);
                } catch (SecurityException ignored) {}
            }
        }
    }
}
