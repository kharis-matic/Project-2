package com.example.uliv.activities;

// Import necessary Android and Firebase classes
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uliv.MainActivity;
import com.example.uliv.OwnerMainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.uliv.R;

/**
 * Handles the user login process via email and password.
 * Displays a login screen and authenticates users using Firebase.
 */
public class LoginEmailActivity extends AppCompatActivity {

    // Input fields for user email and password
    private EditText email;
    private EditText password;

    // Button to trigger the login process
    private Button login;

    // Firebase authentication instance
    private FirebaseAuth auth;

    /**
     * Called when the activity is first created.
     * Initializes UI components and sets the login button action.
     *
     * @param savedInstanceState Bundle containing previous activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email); // Sets the layout for this activity

        // Connect UI elements to their corresponding views
        email = findViewById(R.id.emailEt);          // Email input field
        password = findViewById(R.id.passwordEt);    // Password input field
        login = findViewById(R.id.loginBtnTv);       // Login button

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Set action when login button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text input from email and password fields
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                // Attempt to log the user in with provided credentials
                loginUser(txt_email, txt_password);
            }
        });
    }

    /**
     * Attempts to log in the user using Firebase Authentication.
     *
     * @param email    User-provided email address
     * @param password User-provided password
     */
    private void loginUser(String email, String password) {

        // Use FirebaseAuth to sign in with email and password
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // If login is successful, show confirmation and navigate to OwnerMainActivity
                Toast.makeText(LoginEmailActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginEmailActivity.this, OwnerMainActivity.class));
                finish(); // Finish login activity to prevent going back to it
            }
        });

    }
}
