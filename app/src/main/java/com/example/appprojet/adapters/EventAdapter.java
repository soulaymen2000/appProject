package com.example.appprojet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.appprojet.R;
import com.example.appprojet.activities.AddEditEventActivity;
import com.example.appprojet.models.Event;
import java.util.List;

public class EventAdapter extends BaseAdapter {

    Context context;
    List<Event> eventList;
    LayoutInflater inflater;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return eventList.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = inflater.inflate(R.layout.event_item, viewGroup, false);
        android.widget.TextView titleView = itemView.findViewById(R.id.event_title);
        android.widget.TextView descView = itemView.findViewById(R.id.event_description);
        android.widget.Button deleteBtn = itemView.findViewById(R.id.btn_delete_event);
        android.widget.ImageView imageView = itemView.findViewById(R.id.event_image);

        Event event = eventList.get(i);
        titleView.setText(event.title);
        descView.setText(event.description);

        // Load event image or show placeholder
        if (event.imageUri != null && !event.imageUri.isEmpty()) {
            try {
                context.getContentResolver().openInputStream(android.net.Uri.parse(event.imageUri)).close();
                imageView.setImageURI(android.net.Uri.parse(event.imageUri));
            } catch (Exception e) {
                imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Check if user is admin
        com.example.appprojet.utils.SessionManager sessionManager = new com.example.appprojet.utils.SessionManager(context);
        String role = sessionManager.getRole();
        boolean isAdmin = "admin".equals(role);

        // Only show delete button for admin
        if (isAdmin) {
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(v -> {
                com.example.appprojet.utils.DBHelper dbHelper = new com.example.appprojet.utils.DBHelper(context);
                boolean deleted = dbHelper.deleteEvent(event.id);
                if (deleted) {
                    eventList.remove(i);
                    notifyDataSetChanged();
                    android.widget.Toast.makeText(context, "Event deleted", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    android.widget.Toast.makeText(context, "Delete failed", android.widget.Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            deleteBtn.setVisibility(View.GONE);
        }

        // Only allow admin to edit event on click
        if (isAdmin) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddEditEventActivity.class);
                intent.putExtra("event_id", event.id);
                context.startActivity(intent);
            });
        } else {
            // For users: open event details page
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, com.example.appprojet.activities.EventDetailsActivity.class);
                intent.putExtra("event_id", event.id);
                context.startActivity(intent);
            });
        }

        return itemView;
    }
}
