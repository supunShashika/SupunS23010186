package com.s23010186.lab05;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.editTextTextPassword);
        passwordInput = findViewById(R.id.editTextNumberPassword);
        dbHelper = new DatabaseHelper(this);
    }

    public void submit(View view) {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        boolean result = dbHelper.insertData(username, password);

        if (result) {
            Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login failed!", Toast.LENGTH_LONG).show();
        }
    }
}
