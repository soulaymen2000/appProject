package com.example.appprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojet.R;
import com.example.appprojet.utils.DBHelper;

public class SignupActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    RadioGroup roleGroup;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        roleGroup = findViewById(R.id.role_group);
        db = new DBHelper(this);

        findViewById(R.id.btn_signup).setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String role = roleGroup.getCheckedRadioButtonId() == R.id.radio_user ? "user" : "admin";

            if (db.insertUser(username, password, role)) {
                Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Error signing up", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
