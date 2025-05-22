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
        // Set event data to views
        // For admin, set onClickListener to edit event
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditEventActivity.class);
            intent.putExtra("event_id", eventList.get(i).id);
            context.startActivity(intent);
        });
        return itemView;
    }
}
