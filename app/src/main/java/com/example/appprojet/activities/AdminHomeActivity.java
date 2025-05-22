package com.example.appprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojet.R;
import com.example.appprojet.adapters.EventAdapter;
import com.example.appprojet.models.Event;
import com.example.appprojet.utils.DBHelper;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    ListView listView;
    Button addEventButton;
    DBHelper db;
    EventAdapter adapter;
    List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        listView = findViewById(R.id.admin_event_list);
        addEventButton = findViewById(R.id.btn_add_event);
        db = new DBHelper(this);
        eventList = db.getAllEvents();
        adapter = new EventAdapter(this, eventList);
        listView.setAdapter(adapter);

        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditEventActivity.class);
            startActivity(intent);
        });
    }
}
