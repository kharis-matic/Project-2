package com.example.uliv.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.uliv.R;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class RegisterEmailActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private Spinner gender;
    private Spinner role;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        firstName = findViewById(R.id.firstNameEt);
        lastName = findViewById(R.id.lastNameEt);
        gender = findViewById(R.id.genderEt);
        role = findViewById(R.id.roleEt);
        email = findViewById(R.id.emailEt);
        phoneNumber = findViewById(R.id.phoneEt);
        password = findViewById(R.id.passwordEt);
        confirmPassword = findViewById(R.id.confirmPasswordEt);
        register = findViewById(R.id.registerBtn);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String txt_firstName = firstName.getText().toString();
                String txt_lastName = lastName.getText().toString();
                String txt_gender = gender.getSelectedItem().toString();
                String txt_role = role.getSelectedItem().toString();
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
                    registerUser(txt_firstName, txt_lastName, txt_gender, txt_role, txt_email, txt_phoneNumber, txt_password, txt_confirmPassword);
                }

                {
                }
            }
        });


    }

    private void registerUser(String txtFirstName, String txt_lastName, String txt_gender, String txt_role, String txt_email, String txt_phoneNumber, String txt_password, String txt_confirmPassword) {


    }
}