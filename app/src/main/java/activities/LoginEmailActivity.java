package activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import activities.MainActivity;
import com.example.ulive.R;
import com.example.ulive.databinding.ActivityLoginEmailBinding;

public class LoginEmailActivity extends AppCompatActivity {

    // View Binding
    private ActivityLoginEmailBinding binding;

    // TAG for logs
    private static final String TAG = "LOGIN_TAG";

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Firebase Auth - Unavailable (remains commented)
//    private FirebaseAuth firebaseAuth;

    // Member variables for email and password
    private String email = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Call before setContentView

        // Inflate layout using View Binding
        // Connects to activity_login_email.xml
        binding = ActivityLoginEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Set content view using the binding's root

        // Apply window insets listener
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e(TAG, "onCreate: View with ID R.id.main not found. Edge-to-edge insets may not apply correctly.");
        }


        // Init Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        // Init Firebase Auth (remains commented)
        // At least where it should init
//        firebaseAuth = FirebaseAuth.getInstance();

        // Handle click: Go back
        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Handle click: Login button (Temporary - direct navigation to MainActivity)
        binding.loginBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Login button clicked. Navigating to MainActivity (Temporary).");
                Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class);
                startActivity(intent);
                // finish();
            }
        });
    } // End of onCreate method


    // EventListener for Firebase -- that is not available yet

    // validateData method is now a proper member of LoginEmailActivity
    private void validateData() {
        Log.d(TAG, "validateData: Validating data..."); // Added more descriptive log
        // Get data
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();

        Log.d(TAG, "validateData: email: " + email);
        Log.d(TAG, "validateData: password: " + password);

        // Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid Email Format");
            binding.emailEt.requestFocus();
        } else if (password.isEmpty()) {
            binding.passwordEt.setError("Enter Password");
            binding.passwordEt.requestFocus();
        } else {
            // Data is valid, login user (this call is currently bypassed by the login button's temporary behavior)
            Log.d(TAG, "validateData: Data is valid. Proceeding to loginUser (if called).");
            loginUser();
        }
    }



    private void loginUser() {
        Log.d(TAG, "loginUser: Attempting to log in user (Firebase logic commented out)...");
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        // Login success
//                        Log.d(TAG, "onSuccess: Logged In...");
//                        progressDialog.dismiss();
//
//                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                        String currentEmail = firebaseUser.getEmail(); // Renamed to avoid conflict with member 'email'
//                        Log.d(TAG, "onSuccess: Logged In as " + currentEmail);
//                        Toast.makeText(LoginEmailActivity.this, "Logged In as " + currentEmail, Toast.LENGTH_SHORT).show();
//
//                        // Start Main Activity
//                        Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class); // Added Intent import
//                        startActivity(intent);
//                        finishAffinity();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) { // Added @NonNull import if needed: androidx.annotation.NonNull
//                        // Login failed
//                        Log.e(TAG, "onFailure: Login failed", e);
//                        progressDialog.dismiss();
//                        Toast.makeText(LoginEmailActivity.this, "Login failed due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

    } // End of loginUser method
} // End of LoginEmailActivity class