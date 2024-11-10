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

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button saveSharedPrefsButton;
    private TextView displayNameTextView;
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
        dbButton = findViewById(R.id.dbButton);
        settingsButton = findViewById(R.id.settingsButton);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

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

        dbButton.setOnClickListener(v -> showDatabaseOptions());

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void showDatabaseOptions() {
        final String[] options = {"Guardar Nombre en SQLite", "Cargar Nombre desde SQLite"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones de Base de Datos");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                saveNameToDatabase();
            } else if (which == 1) {
                loadNameFromDatabase();
            }
        });
        builder.show();
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
        database.close();
        dbHelper.close();
    }
}

