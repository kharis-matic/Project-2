package com.example.uliv.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log; // Import for Log
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uliv.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * RegisterEmailActivity allows users to input their details for creating a new account.
 * It includes fields for first name, last name, gender, role, email, phone number,
 * password, and password confirmation. It performs basic input validation.
 * The actual user registration logic in {@link #registerUser} is a placeholder
 * in this version of the code.
 */
public class RegisterEmailActivity extends AppCompatActivity {

    // --- UI Elements ---
    // EditText fields for user input
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmPassword;
    // Spinners for selecting gender and role
    private Spinner gender;
    private Spinner role;
    // Button to trigger the registration process
    private Button register;

    // --- Firebase ---
    // FirebaseAuth instance for handling user authentication.
    // Although initialized, its usage for registration is not implemented in the registerUser method in this version.
    private FirebaseAuth auth;

    // TAG for logging purposes, helps in debugging
    private static final String TAG = "REGISTER_EMAIL_TAG";


    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up: initialize UI elements,
     * get Firebase instances, and set up listeners.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view from the XML layout (R.layout.activity_register_email)
        setContentView(R.layout.activity_register_email);

        // Initialize UI elements by finding them in the layout using their IDs
        firstName = findViewById(R.id.firstNameEt);
        lastName = findViewById(R.id.lastNameEt);
        gender = findViewById(R.id.genderEt); // Ensure this ID matches your Spinner in XML
        role = findViewById(R.id.roleEt);     // Ensure this ID matches your Spinner in XML
        email = findViewById(R.id.emailEt);
        phoneNumber = findViewById(R.id.phoneEt);
        password = findViewById(R.id.passwordEt);
        confirmPassword = findViewById(R.id.confirmPasswordEt);
        register = findViewById(R.id.registerBtn);

        // Get instance of FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Set an OnClickListener for the register button
        register.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the register button (R.id.registerBtn) is clicked.
             * It retrieves user input from all fields, performs validation checks,
             * and if validation passes, it calls the {@link #registerUser} method.
             * @param v The view that was clicked (the register button).
             */
            @Override
            public void onClick(View v) {
                // Retrieve text from EditText fields and trim whitespace
                String txt_firstName = firstName.getText().toString().trim();
                String txt_lastName = lastName.getText().toString().trim();

                // Retrieve selected items from Spinners.
                // It's good practice to check if an item is selected, especially if a prompt is the first item.
                String txt_gender = (gender.getSelectedItem() != null) ? gender.getSelectedItem().toString() : "";
                String txt_role = (role.getSelectedItem() != null) ? role.getSelectedItem().toString() : "";

                String txt_phoneNumber = phoneNumber.getText().toString().trim();
                String txt_email = email.getText().toString().trim();
                String txt_password = password.getText().toString(); // Passwords usually aren't trimmed for length check
                String txt_confirmPassword = confirmPassword.getText().toString();

                // --- Input Validation ---
                // Check if any of the essential fields are empty
                if (TextUtils.isEmpty(txt_firstName)) {
                    firstName.setError("First name is required");
                    firstName.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "First name is required!", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }
                if (TextUtils.isEmpty(txt_lastName)) {
                    lastName.setError("Last name is required");
                    lastName.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Last name is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Basic check for spinners; more robust validation might check if a non-prompt item is selected.
                if (TextUtils.isEmpty(txt_gender) || (gender.getSelectedItemPosition() == 0 && gender.getAdapter().getCount() > 1 && gender.getItemAtPosition(0).toString().toLowerCase().contains("select"))) {
                    Toast.makeText(RegisterEmailActivity.this, "Please select a gender!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_role) || (role.getSelectedItemPosition() == 0 && role.getAdapter().getCount() > 1 && role.getItemAtPosition(0).toString().toLowerCase().contains("select"))) {
                    Toast.makeText(RegisterEmailActivity.this, "Please select a role!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_email)) {
                    email.setError("Email is required");
                    email.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Email is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_phoneNumber)) {
                    phoneNumber.setError("Phone number is required");
                    phoneNumber.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Phone number is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_password)) {
                    password.setError("Password is required");
                    password.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Password is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_confirmPassword)) {
                    confirmPassword.setError("Confirm password is required");
                    confirmPassword.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Confirm password is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Additional validation for password length
                if (txt_password.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    password.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Password too short! Must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if the password and confirm password fields match
                if (!txt_password.equals(txt_confirmPassword)) {
                    confirmPassword.setError("Passwords do not match");
                    confirmPassword.requestFocus();
                    Toast.makeText(RegisterEmailActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // If all validations pass, proceed to call the registerUser method.
                // The actual registration logic is expected to be within registerUser.
                Log.d(TAG, "Validation successful. Calling registerUser.");
                registerUser(txt_firstName, txt_lastName, txt_gender, txt_role, txt_email, txt_phoneNumber, txt_password, txt_confirmPassword);

                // The empty block below was in the original code structure provided.
                // It doesn't perform any action and can be safely removed if not intended for future use.
                {
                }
            }
        });
    }

    /**
     * Placeholder method for user registration.
     * In this version of the code, this method is empty and does not perform any actual registration.
     * It is intended to be implemented with the logic for creating a new user account,
     * potentially using Firebase Authentication and saving user details to a database.
     *
     * @param fName             The user's first name.
     * @param lName             The user's last name.
     * @param genderSelected    The user's selected gender.
     * @param roleSelected      The user's selected role.
     * @param emailAddress      The user's email address.
     * @param phoneNum          The user's phone number.
     * @param pass              The user's chosen password.
     * @param confirmPass       The user's confirmed password (already validated to match 'pass').
     */
    private void registerUser(String fName, String lName, String genderSelected, String roleSelected,
                              String emailAddress, String phoneNum, String pass, String confirmPass) {
        // TODO: Implement user registration logic here.
        // This would typically involve:
        // 1. Showing a progress indicator.
        // 2. Calling Firebase Auth's createUserWithEmailAndPassword(emailAddress, pass).
        // 3. If Auth creation is successful, save additional user details (fName, lName, etc.)
        //    to a database (e.g., Firebase Realtime Database or Firestore) associated with the new user's UID.
        // 4. Handle success: Navigate to the main app screen or a profile setup screen.
        // 5. Handle failures: Show appropriate error messages to the user (e.g., email already in use, weak password).
        // 6. Hiding the progress indicator.
        Log.d(TAG, "registerUser method called with the following details:");
        Log.d(TAG, "Name: " + fName + " " + lName);
        Log.d(TAG, "Gender: " + genderSelected + ", Role: " + roleSelected);
        Log.d(TAG, "Email: " + emailAddress + ", Phone: " + phoneNum);
        // Do not log passwords in production code.
        // Log.d(TAG, "Password: " + pass);

        // Example Toast to indicate it's a placeholder:
        Toast.makeText(this, "Registration logic not yet implemented.", Toast.LENGTH_LONG).show();
    }
}
