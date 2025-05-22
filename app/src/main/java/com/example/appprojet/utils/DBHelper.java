package com.example.appprojet.utils;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.appprojet.models.User;
import com.example.appprojet.models.Event;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "EventsApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "role TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        db.execSQL("CREATE TABLE events(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "description TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        ContentValues cv = new ContentValues();
        cv.put("username", "admin");
        cv.put("password", "admin");
        cv.put("role", "admin");
        db.insert("users", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }

    public boolean insertUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role);
        long result = db.insert("users", null, cv);
        return result != -1;
    }

    public User login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        if (c.moveToFirst()) {
            return new User(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            );
        }
        return null;
    }

    public boolean insertEvent(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("description", description);
        return db.insert("events", null, cv) != -1;
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM events ORDER BY created_at DESC", null);
        while (c.moveToNext()) {
            list.add(new Event(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return list;
    }

    public Event getEventById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM events WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            return new Event(c.getInt(0), c.getString(1), c.getString(2));
        }
        return null;
    }

    public boolean updateEvent(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("description", description);
        return db.update("events", cv, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("events", "id=?", new String[]{String.valueOf(id)}) > 0;
    }
}
