package com.example.uliv;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import com.example.uliv.activities.LoginEmailActivity;


//import androidx.activity.EdgeToEdge;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import com.example.uliv.databinding.ActivityLoginOptionsBinding;
import com.example.uliv.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityLoginOptionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Example usage: binding.loginPhoneBtn.setOnClickListener(...)
        binding.loginEmailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginEmailActivity.class);
            startActivity(intent);
        });

        binding.skipBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RenterMainActivity.class);
            startActivity(intent);
        });

    }
}
