package com.example.appprojet.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
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
        ImageView imageView = findViewById(R.id.event_details_image);

        int eventId = getIntent().getIntExtra("event_id", -1);
        if (eventId != -1) {
            DBHelper db = new DBHelper(this);
            Event event = db.getEventById(eventId);
            if (event != null) {
                titleView.setText(event.title);
                descView.setText(event.description);
                // TODO: Load image if available
            }
        }
    }
}
