package com.example.taller3eventosfranlopez;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button saveSharedPrefsButton;
    private TextView displayNameTextView;
    private TextView greetingTextView;
    private Button dbButton;
    private Button settingsButton;

    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aplicar el color de fondo guardado
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int bgColor = prefs.getInt("bgColor", getResources().getColor(R.color.white));
        getWindow().getDecorView().setBackgroundColor(bgColor);

        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        saveSharedPrefsButton = findViewById(R.id.saveSharedPrefsButton);
        displayNameTextView = findViewById(R.id.displayNameTextView);
        greetingTextView = findViewById(R.id.greetingTextView);
        dbButton = findViewById(R.id.dbButton);
        settingsButton = findViewById(R.id.settingsButton);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        // Mostrar saludo en el TextView
        String greeting = getGreetingMessage();
        greetingTextView.setText(greeting);

        // Cargar el nombre guardado en SharedPreferences
        String savedName = sharedPreferences.getString("userName", "No hay nombre guardado");
        displayNameTextView.setText("Nombre guardado: " + savedName);

        saveSharedPrefsButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            if (!name.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", name);
                editor.apply();
                displayNameTextView.setText("Nombre guardado: " + name);
                Toast.makeText(MainActivity.this, "Nombre guardado en SharedPreferences", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show();
            }
        });

        dbButton.setOnClickListener(v -> {
            saveNameToDatabase();
            loadNameFromDatabase();
        });

        settingsButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error al abrir la configuración", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getGreetingMessage() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 5 && hour < 12) {
            return "¡Buenos días!";
        } else if (hour >= 12 && hour < 18) {
            return "¡Buenas tardes!";
        } else {
            return "¡Buenas noches!";
        }
    }

    private void saveNameToDatabase() {
        String name = nameEditText.getText().toString();
        if (!name.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, name);
            database.insert(DatabaseHelper.TABLE_USERS, null, values);
            Toast.makeText(this, "Nombre guardado en SQLite", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNameFromDatabase() {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_NAME},
                null, null, null, null,
                DatabaseHelper.COLUMN_ID + " DESC",
                "1"
        );

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            displayNameTextView.setText("Nombre desde SQLite: " + name);
        } else {
            displayNameTextView.setText("No hay nombres guardados en SQLite");
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
        dbHelper.close();
    }
}
