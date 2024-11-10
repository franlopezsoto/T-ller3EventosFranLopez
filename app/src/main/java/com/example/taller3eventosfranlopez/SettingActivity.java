package com.example.taller3eventosfranlopez;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private RadioGroup colorRadioGroup;
    private Button saveColorButton;
    private Button backButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aplicar el color de fondo guardado
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int bgColor = sharedPreferences.getInt("bgColor", Color.WHITE);
        getWindow().getDecorView().setBackgroundColor(bgColor);

        setContentView(R.layout.activity_settings);

        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        saveColorButton = findViewById(R.id.saveColorButton);
        backButton = findViewById(R.id.backButton);

        // Seleccionar el radio button correspondiente al color guardado
        if (bgColor == Color.WHITE) {
            colorRadioGroup.check(R.id.radioWhite);
        } else if (bgColor == Color.LTGRAY) {
            colorRadioGroup.check(R.id.radioGray);
        } else if (bgColor == Color.CYAN) {
            colorRadioGroup.check(R.id.radioCyan);
        } else {
            colorRadioGroup.check(R.id.radioWhite);
        }

        saveColorButton.setOnClickListener(v -> {
            int selectedColor;
            int selectedId = colorRadioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.radioWhite) {
                selectedColor = Color.WHITE;
            } else if (selectedId == R.id.radioGray) {
                selectedColor = Color.LTGRAY;
            } else if (selectedId == R.id.radioCyan) {
                selectedColor = Color.CYAN;
            } else {
                selectedColor = Color.WHITE;
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("bgColor", selectedColor);
            editor.apply();

            // Actualizar el color de fondo
            getWindow().getDecorView().setBackgroundColor(selectedColor);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}

