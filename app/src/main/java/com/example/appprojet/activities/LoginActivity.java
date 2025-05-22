package com.example.appprojet.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojet.R;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.DBHelper;
import com.example.appprojet.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    DBHelper db;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        db = new DBHelper(this);
        session = new SessionManager(this);

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            User user = db.login(username, password);
            if (user != null) {
                session.saveSession(user.username, user.role);
                if (user.role.equals("admin")) {
                    startActivity(new Intent(this, AdminHomeActivity.class));
                } else {
                    startActivity(new Intent(this, UserHomeActivity.class));
                }
                finish();
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.link_signup).setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class))
        );
    }
}

