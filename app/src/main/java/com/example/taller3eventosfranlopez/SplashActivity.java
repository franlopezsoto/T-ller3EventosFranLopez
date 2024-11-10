package com.example.taller3eventosfranlopez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    private TextView greetingTextView;
    private Button goToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        greetingTextView = findViewById(R.id.greetingTextView);
        goToMainButton = findViewById(R.id.goToMainButton);

        setGreeting();

        goToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Mostrar el botón después de 2 segundos
        new Handler().postDelayed(() -> goToMainButton.setVisibility(Button.VISIBLE), 2000);
    }

    private void setGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 5 && hour <= 11) {
            greeting = "¡Buenos días!";
        } else if (hour >= 12 && hour <= 17) {
            greeting = "¡Buenas tardes!";
        } else {
            greeting = "¡Buenas noches!";
        }

        greetingTextView.setText(greeting);
    }
}
