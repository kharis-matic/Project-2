package com.example.uliv;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//import androidx.activity.EdgeToEdge;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import com.example.uliv.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // What should be inside MainActivity? It could be the LOGIN SCREEN or SPLASHSCREEN nato
    // but in our case since naa tay landing page, it should be the landingpage/LoginOptionsActivity
    // will then start sa Login process or Register process

    //View Binding
    private ActivityMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity_main.xml = ActivityMainBinding;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}