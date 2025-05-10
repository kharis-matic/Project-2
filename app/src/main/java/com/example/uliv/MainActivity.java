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

/**
 * MainActivity serves as the initial screen of the application.
 * It appears to be using the layout defined in `activity_login_options.xml` (based on the binding type),
 * suggesting it presents users with choices such as logging in via email or skipping the login process
 * to proceed to a renter-specific main activity.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * View binding object for the `activity_login_options.xml` layout.
     * This allows for type-safe access to the views defined within that layout file,
     * such as buttons for different login methods or skipping login.
     */
    private ActivityLoginOptionsBinding binding;

    /**
     * Called when the activity is first created. This is where most initialization should go:
     * calling {@code setContentView(int)} to inflate the activity's UI, using
     * {@code findViewById(int)} to programmatically interact with widgets in the UI,
     * and setting up listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enables edge-to-edge display for the activity, allowing content to be drawn
        // under the system bars (like the status bar and navigation bar) for a more
        // immersive user experience.
        EdgeToEdge.enable(this);

        // Inflate the layout using ViewBinding. ActivityLoginOptionsBinding corresponds to
        // a layout file named activity_login_options.xml (conventionally).
        binding = ActivityLoginOptionsBinding.inflate(getLayoutInflater());
        // Set the inflated layout as the content view for this activity.
        setContentView(binding.getRoot());

        // Sets up a listener to apply window insets. This is crucial for edge-to-edge display
        // to ensure that important UI elements are not obscured by the system bars.
        // The listener is attached to the 'main' view, which is assumed to be the root
        // element or a primary container in the activity_login_options.xml layout.
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            // Retrieves the insets (padding needed) for the system bars.
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Applies the retrieved insets as padding to the view 'v'.
            // This ensures that the content within 'v' is not drawn under the system bars.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Returns the insets, allowing other views to also react to them if needed.
            return insets;
        });

        // Sets an OnClickListener for the 'loginEmailBtn'.
        // When this button is clicked, it creates an Intent to start the LoginEmailActivity.
        binding.loginEmailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginEmailActivity.class);
            startActivity(intent);
        });

        // Sets an OnClickListener for the 'skipBtn'.
        // When this button is clicked, it creates an Intent to start the RenterMainActivity,
        // presumably allowing the user to bypass the login process and access renter-specific features.
        binding.skipBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RenterMainActivity.class);
            startActivity(intent);
        });

    }
}
