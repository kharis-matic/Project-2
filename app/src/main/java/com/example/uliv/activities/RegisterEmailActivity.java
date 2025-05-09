package com.example.uliv.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uliv.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterEmailActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
//    private Spinner gender;
//    private Spinner role;

    private MaterialAutoCompleteTextView gender;
    private MaterialAutoCompleteTextView role;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    private FirebaseAuth auth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.firstNameEt);
        lastName = findViewById(R.id.lastNameEt);
        gender = findViewById(R.id.genderEt);
        role = findViewById(R.id.roleEt);
        email = findViewById(R.id.emailEt);
        phoneNumber = findViewById(R.id.phoneEt);
        password = findViewById(R.id.passwordEt);
        confirmPassword = findViewById(R.id.confirmPasswordEt);
        register = findViewById(R.id.registerBtn);

        // When register button is submitted, validate user input and register user
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String txt_firstName = firstName.getText().toString();
                String txt_lastName = lastName.getText().toString();
//                String txt_gender = gender.getSelectedItem().toString();
//                String txt_role = role.getSelectedItem().toString();
                String txt_gender = gender.getText().toString();
                String txt_role = role.getText().toString();
                String txt_phoneNumber = phoneNumber.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_confirmPassword = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(txt_firstName) ||
                        TextUtils.isEmpty(txt_lastName) ||
                        TextUtils.isEmpty(txt_gender) ||
                        TextUtils.isEmpty(txt_role) ||
                        TextUtils.isEmpty(txt_email) ||
                        TextUtils.isEmpty(txt_phoneNumber) ||
                        TextUtils.isEmpty(txt_password) ||
                        TextUtils.isEmpty(txt_confirmPassword)) {

                    Toast.makeText(RegisterEmailActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterEmailActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else if (!txt_password.equals(txt_confirmPassword)) {
                    Toast.makeText(RegisterEmailActivity.this, "Password must be the same!", Toast.LENGTH_SHORT).show();
                }else {
                    // Register User
                    registerUser(txt_firstName, txt_lastName, txt_gender, txt_role, txt_email, txt_phoneNumber, txt_password, txt_confirmPassword);
                }

                {

                }
            }
        });




    }


    private void registerUser(String txtFirstName, String txtLastName, String txtGender, String txtRole, String txtEmail, String txtPhoneNumber, String txtPassword, String txtConfirmPassword) {

        // Register with email and password to firebase

        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            // Runs after Firebase finishes create user request
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If request is successful, updates the UI depending on the current user
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = auth.getCurrentUser();



                } else { // If log in fails, display message to user
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterEmailActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        }


}
