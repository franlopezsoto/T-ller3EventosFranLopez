package com.example.taller3eventosfranlopez;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private RadioGroup colorRadioGroup;
    private Button saveColorButton;
    private Button backButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int bgColor = sharedPreferences.getInt("bgColor", Color.WHITE);
        getWindow().getDecorView().setBackgroundColor(bgColor);

        setContentView(R.layout.activity_settings);

        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        saveColorButton = findViewById(R.id.saveColorButton);
        backButton = findViewById(R.id.backButton);

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
            try {
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

                getWindow().getDecorView().setBackgroundColor(selectedColor);
                Toast.makeText(SettingActivity.this, "Color de fondo guardado", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(SettingActivity.this, "Error al guardar el color", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(SettingActivity.this, "Error al volver a la pantalla principal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
