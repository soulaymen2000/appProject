package com.example.appprojet.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("userSession", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveSession(String username, String role) {
        editor.putString("username", username);
        editor.putString("role", role);
        editor.apply();
    }

    public String getRole() {
        return prefs.getString("role", null);
    }

    public String getUsername() {
        return prefs.getString("username", null);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}

