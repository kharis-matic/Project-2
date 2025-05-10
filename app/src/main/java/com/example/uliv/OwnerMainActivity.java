package com.example.uliv;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.util.Log; // Import Log for debugging
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment; // Import Fragment
import androidx.fragment.app.FragmentTransaction;

// Import PostAddActivity (ensure the package name is correct)
import com.example.uliv.activities.PostAddActivity;
import com.example.uliv.databinding.ActivityOwnerMainBinding;
import com.example.uliv.fragments.owner.EnquiriesListFragment;
import com.example.uliv.fragments.owner.OwnerHomeFragment;
import com.example.uliv.fragments.NotificationListFragment;
import com.example.uliv.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

/**
 * OwnerMainActivity serves as the main navigation hub for property owners.
 * It uses a BottomNavigationView to switch between different functional areas
 * represented by Fragments (Home, Enquiries, Notifications, Profile).
 * The "Upload" option in the navigation directly starts the {@link PostAddActivity}.
 */
public class OwnerMainActivity extends AppCompatActivity {

    /**
     * View binding object for the `activity_owner_main.xml` layout.
     * This allows for type-safe access to views within the layout.
     */
    private ActivityOwnerMainBinding binding;
    /**
     * Tag for logging messages, used for debugging purposes.
     */
    private static final String TAG = "OWNER_MAIN_ACTIVITY"; // Added a TAG for logging

    /**
     * Called when the activity is first created. This is where most initialization should go:
     * calling {@code setContentView(int)} to inflate the activity's UI, initializing
     * view binding, setting up the bottom navigation, and loading the default fragment.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using ViewBinding.
        binding = ActivityOwnerMainBinding.inflate(getLayoutInflater());
        // Set the inflated layout as the content view for this activity.
        setContentView(binding.getRoot());

        Log.d(TAG, "onCreate: Activity created and view binding initialized.");

        // Show OwnerHomeFragment by default only when the activity is first created
        // (i.e., savedInstanceState is null). This prevents the fragment from being
        // reloaded on configuration changes if it's already present.
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: No saved instance state, loading default OwnerHomeFragment.");
            showOwnerHomeFragment();
            // Optionally select the home item in the bottom navigation view to reflect the default fragment.
            binding.bottomNavigationView.setSelectedItemId(R.id.item_home);
        }


        // Set a listener for item selections in the BottomNavigationView.
        // This listener handles navigation to different fragments or activities based on the selected item.
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            /**
             * Called when an item in the bottom navigation menu is selected.
             *
             * @param item The selected menu item.
             * @return {@code true} to display the item as the selected item and false if the item
             * selection should be ignored.
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId(); // Get the ID of the selected menu item.
                Log.d(TAG, "onNavigationItemSelected: Item selected: " + item.getTitle());

                if (itemId == R.id.item_home) {
                    // If "Home" is selected, show the OwnerHomeFragment.
                    showOwnerHomeFragment();
                    return true; // Event handled, item will be visually selected.
                } else if (itemId == R.id.item_enquiries) {
                    // If "Enquiries" is selected, show the EnquiriesListFragment.
                    showEnquiriesListFragment();
                    return true; // Event handled.
                } else if (itemId == R.id.item_upload) {
                    // If "Upload" is selected, start the PostAddActivity to create a new post.
                    Log.d(TAG, "onNavigationItemSelected: Upload item selected, starting PostAddActivity.");
                    Intent intent = new Intent(OwnerMainActivity.this, PostAddActivity.class);
                    startActivity(intent);

                    // Returning false here means the "Upload" item might not stay visually selected
                    // in the BottomNavigationView after starting the new Activity. This is often
                    // desired behavior as the user is navigating away from the current screen's
                    // fragment-based layout. If "Upload" should appear selected, return true.
                    return false;
                } else if (itemId == R.id.item_notification) {
                    // If "Notification" is selected, show the NotificationListFragment.
                    showNotificationListFragment();
                    return true; // Event handled.
                } else if (itemId == R.id.item_profile) {
                    // If "Profile" is selected, show the ProfileFragment.
                    showProfileFragment();
                    return true; // Event handled.
                }
                // If the itemId does not match any known items, return false.
                Log.w(TAG, "onNavigationItemSelected: Unknown item selected with ID: " + itemId);
                return false; // Default case: event not handled.
            }
        });
    }

    /**
     * Helper method to replace the content of the fragment container with a new fragment.
     * It also updates the toolbar title.
     *
     * @param fragment The {@link Fragment} to display.
     * @param tag A tag name for the fragment, used for later retrieval by {@link androidx.fragment.app.FragmentManager#findFragmentByTag(String)}.
     * @param title The title to set on the toolbar for this fragment.
     */
    private void showFragment(Fragment fragment, String tag, String title) {
        Log.d(TAG, "showFragment: Displaying fragment with tag: " + tag + ", Title: " + title);
        // Set the toolbar title.
        binding.toolbarTitleTv.setText(title);
        // Begin a fragment transaction.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // Optional: Add custom animations for fragment transitions.
        // fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
        //                                         android.R.anim.fade_in, android.R.anim.fade_out);
        // Replace the content of the FrameLayout (binding.fragmentsFl) with the new fragment.
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, tag);
        // It's often good practice to add to back stack if you want fragment navigation history.
        // fragmentTransaction.addToBackStack(tag);
        // Commit the transaction.
        fragmentTransaction.commit();
    }

    /**
     * Displays the {@link OwnerHomeFragment} in the main content area and sets the toolbar title to "Home".
     */
    private void showOwnerHomeFragment() {
        showFragment(new OwnerHomeFragment(), "OwnerHomeFragment", "Home");
    }

    /**
     * Displays the {@link EnquiriesListFragment} in the main content area and sets the toolbar title to "Enquiries".
     */
    private void showEnquiriesListFragment() {
        showFragment(new EnquiriesListFragment(), "EnquiriesListFragment", "Enquiries");
    }

    // The showUploadListFragment() method is commented out as the "Upload" action
    // now directly starts PostAddActivity instead of showing a fragment.
    /*
    private void showUploadListFragment() {
        // This logic was replaced by starting PostAddActivity.
        // showFragment(new UploadListFragment(), "UploadListFragment", "Upload"); // Example if it were a fragment
    }
    */

    /**
     * Displays the {@link NotificationListFragment} in the main content area and sets the toolbar title to "Notifications".
     */
    private void showNotificationListFragment() {
        showFragment(new NotificationListFragment(), "NotificationListFragment", "Notifications");
    }

    /**
     * Displays the {@link ProfileFragment} in the main content area and sets the toolbar title to "Profile".
     */
    private void showProfileFragment() {
        showFragment(new ProfileFragment(), "ProfileFragment", "Profile");
    }
}
