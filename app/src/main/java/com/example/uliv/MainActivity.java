package com.example.uliv;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.uliv.activities.LoginEmailActivity;


//import androidx.activity.EdgeToEdge;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import com.example.uliv.activities.RegisterEmailActivity;
import com.example.uliv.databinding.ActivityLoginOptionsBinding;
import com.example.uliv.databinding.ActivityMainBinding;
import com.example.uliv.databinding.ActivityRegisterEmailBinding;

public class MainActivity extends AppCompatActivity {

//    private ActivityLoginOptionsBinding binding;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//         Example usage: binding.loginPhoneBtn.setOnClickListener(...)

        // Proceeds to Log In Activity
        binding.loginEmailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginEmailActivity.class);
            startActivity(intent);
        });

        binding.registerBtn.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, RegisterEmailActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("MainActivity", "Error starting RegisterActivity", e);
            }
        });

        binding.skipBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterEmailActivity.class);
            startActivity(intent);
        });



    }
}
