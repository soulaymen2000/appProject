package com.example.appprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojet.R;
import com.example.appprojet.models.Event;
import com.example.appprojet.utils.DBHelper;

public class EventDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        TextView titleView = findViewById(R.id.event_details_title);
        TextView descView = findViewById(R.id.event_details_description);
        TextView dateView = findViewById(R.id.event_details_date);
        ImageView imageView = findViewById(R.id.event_details_image);

        int eventId = getIntent().getIntExtra("event_id", -1);
        if (eventId != -1) {
            DBHelper db = new DBHelper(this);
            Event event = db.getEventById(eventId);
            if (event != null) {
                titleView.setText(event.title);
                descView.setText(event.description);
                dateView.setText(event.date);
                if (event.imageUri != null && !event.imageUri.isEmpty()) {
                    android.net.Uri uri = android.net.Uri.parse(event.imageUri);
                    try {
                        imageView.setImageURI(uri);
                    } catch (SecurityException e) {
                        // Show built-in placeholder if permission is denied
                        imageView.setImageResource(android.R.drawable.ic_menu_report_image);
                        Toast.makeText(this, "Cannot display image: permission denied", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        imageView.setImageResource(android.R.drawable.ic_menu_report_image);
                        Toast.makeText(this, "Cannot display image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
