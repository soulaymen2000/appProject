package com.example.appprojet.models;

public class Event {
    public int id;
    public String title;
    public String description;

    public Event(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getStatus() {
        // Placeholder: return a status, e.g., "active" or "pending". Adjust as needed.
        return "active";
    }

    public String getLocation() {
        // Placeholder: return a location if available, or empty string
        return "";
    }

    public String getDate() {
        // Placeholder: return a date if available, or empty string
        return "";
    }

    public String getTitle() {
        return title;
    }

    public static java.util.List<Event> fetchAll(android.content.Context context) {
        com.example.appprojet.utils.DBHelper dbHelper = new com.example.appprojet.utils.DBHelper(context);
        return dbHelper.getAllEvents();
    }
}