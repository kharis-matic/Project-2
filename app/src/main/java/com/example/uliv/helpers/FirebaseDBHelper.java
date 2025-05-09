package com.example.uliv.helpers;

import androidx.annotation.NonNull;

import com.example.uliv.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;

public class FirebaseDBHelper {

    private final DatabaseReference databaseRef;

    public FirebaseDBHelper() {
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    // Save user data
    public void saveUser(@NonNull User user, @NonNull String userId, @NonNull OnCompleteListener<Void> listener) {
        databaseRef.child("users").child(userId).setValue(user).addOnCompleteListener(listener);
    }

    // Get user data by ID
    public void getUser(@NonNull String userId, @NonNull ValueEventListener listener) {
        databaseRef.child("users").child(userId).addListenerForSingleValueEvent(listener);
    }

    // Get all users with specific role (e.g., renter or owner)
    public void getUsersByRole(@NonNull String role, @NonNull ValueEventListener listener) {
        databaseRef.child("users").orderByChild("role").equalTo(role).addListenerForSingleValueEvent(listener);
    }

    // Example: Get all users (if needed)
    public void getAllUsers(@NonNull ValueEventListener listener) {
        databaseRef.child("users").addListenerForSingleValueEvent(listener);
    }


}
