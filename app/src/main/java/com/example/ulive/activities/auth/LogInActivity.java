package com.example.ulive;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ulive.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    // Binds this Activity to activity_log_in.xml
    // Enables this activity to have direct references to View IDs on the layout file without using findViewById
    private ActivityLogInBinding binding;

    // Tag to log in logcat
    public static final String TAG = "LOGIN__TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creates an instance of the binding class
        // Inflate is like building the xml blueprint into actual Views that Java can use
        // LayoutInflater is responsible for this
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);

        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}