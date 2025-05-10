package com.example.uliv;

import android.os.Bundle;
import android.util.Log; // Import Log for debugging
import android.view.MenuItem; // Import MenuItem for onNavigationItemSelected

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment; // Import Fragment
import androidx.fragment.app.FragmentTransaction;

import com.example.uliv.databinding.ActivityRenterMainBinding;
// Assuming you will have these fragments or similar ones:
import com.example.uliv.fragments.NotificationListFragment;
import com.example.uliv.fragments.ProfileFragment;
// import com.example.uliv.fragments.renter.BookingsFragment; // Uncomment when you create this
import com.example.uliv.fragments.renter.RenterHomeFragment;
import com.google.android.material.navigation.NavigationBarView;

/**
 * RenterMainActivity serves as the main navigation hub for users with a "renter" role.
 * It utilizes a BottomNavigationView to allow users to switch between different sections
 * of the app, such as Home, Bookings, Notifications, and Profile. Each section is
 * typically represented by a {@link Fragment}.
 */
public class RenterMainActivity extends AppCompatActivity {

    /**
     * View binding object for the `activity_renter_main.xml` layout.
     * This provides type-safe access to the views defined in the layout file.
     */
    private ActivityRenterMainBinding binding;
    /**
     * Tag for logging messages, used for debugging purposes.
     */
    private static final String TAG = "RENTER_MAIN_ACTIVITY";

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
        binding = ActivityRenterMainBinding.inflate(getLayoutInflater());
        // Set the inflated layout as the content view for this activity.
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: Activity created and view binding initialized.");

        // Load the RenterHomeFragment by default only when the activity is first created
        // (i.e., savedInstanceState is null). This prevents the fragment from being
        // reloaded on configuration changes if it's already present.
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: No saved instance state, loading default RenterHomeFragment.");
            showHomeFragment(); // Use helper method to load the default fragment
            // Set the "Home" item as selected in the BottomNavigationView.
            binding.bottomNavigationView.setSelectedItemId(R.id.item_home);
        }

        // Set a listener for item selections in the BottomNavigationView.
        // This listener handles navigation to different fragments based on the selected item.
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
                    // If "Home" is selected, show the RenterHomeFragment.
                    showHomeFragment();
                    return true; // Event handled, item will be visually selected.
                } else if (itemId == R.id.item_bookings) {
                    // If "Bookings" is selected, show the BookingsFragment.
                    // The original code loaded RenterHomeFragment as a temporary placeholder.
                    showBookingsFragment(); // Placeholder, replace with actual BookingsFragment
                    return true; // Event handled.
                } else if (itemId == R.id.item_notification) {
                    // If "Notification" is selected, show the NotificationListFragment.
                    // The original code loaded RenterHomeFragment as a temporary placeholder.
                    showNotificationListFragment();
                    return true; // Event handled.
                } else if (itemId == R.id.item_profile) {
                    // If "Profile" is selected, show the ProfileFragment.
                    // The original code loaded RenterHomeFragment as a temporary placeholder.
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
     * This method provides a generic way to load fragments and can be extended to set titles or tags.
     *
     * @param fragment The {@link Fragment} to display.
     * @param tag A tag name for the fragment, used for later retrieval by {@link androidx.fragment.app.FragmentManager#findFragmentByTag(String)}.
     * (Currently, the title parameter is not used for setting a toolbar title in this specific implementation
     * but is included for potential future use if a toolbar is added/managed by this activity).
     */
    private void showFragment(Fragment fragment, String tag) {
        Log.d(TAG, "showFragment: Displaying fragment with tag: " + tag);
        // If you have a Toolbar managed by this Activity, you would set its title here.
        // Example: if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        // Or if using a TextView in your layout for the title (e.g., binding.toolbarTitleTv.setText(title);)

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Optional: Add custom animations for fragment transitions.
        // transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
        //                                 android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(binding.fragmentsFl.getId(), fragment, tag);
        // It's often good practice to add to back stack if you want fragment navigation history for fragments.
        // transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * Displays the {@link RenterHomeFragment} in the main content area.
     * Sets "Home" as the conceptual title/tag for this fragment.
     */
    private void showHomeFragment() {
        showFragment(new RenterHomeFragment(), "RenterHomeFragment");
    }

    /**
     * Displays the BookingsFragment (currently a placeholder using RenterHomeFragment)
     * in the main content area.
     * TODO: Replace {@code new RenterHomeFragment()} with {@code new BookingsFragment()} when available.
     */
    private void showBookingsFragment() {
        // Placeholder: Using RenterHomeFragment temporarily.
        // Replace with 'new BookingsFragment()' and appropriate tag when BookingsFragment is created.
        showFragment(new RenterHomeFragment(), "BookingsFragment");
        // Example for actual fragment:
        // showFragment(new BookingsFragment(), "BookingsFragment");
    }

    /**
     * Displays the NotificationListFragment (currently a placeholder using RenterHomeFragment)
     * in the main content area.
     * TODO: Replace {@code new RenterHomeFragment()} with {@code new NotificationListFragment()} when available.
     */
    private void showNotificationListFragment() {
        // Placeholder: Using RenterHomeFragment temporarily.
        // Replace with 'new NotificationListFragment()' and appropriate tag.
        showFragment(new RenterHomeFragment(), "NotificationListFragment");
        // Example for actual fragment:
        // showFragment(new NotificationListFragment(), "NotificationListFragment");
    }

    /**
     * Displays the ProfileFragment (currently a placeholder using RenterHomeFragment)
     * in the main content area.
     * TODO: Replace {@code new RenterHomeFragment()} with {@code new ProfileFragment()} when available.
     */
    private void showProfileFragment() {
        // Placeholder: Using RenterHomeFragment temporarily.
        // Replace with 'new ProfileFragment()' and appropriate tag.
        showFragment(new RenterHomeFragment(), "ProfileFragment");
        // Example for actual fragment:
        // showFragment(new ProfileFragment(), "ProfileFragment");
    }


    /**
     * Original method from the user's code to load the {@link RenterHomeFragment}.
     * This method is specific to RenterHomeFragment. The more generic {@link #showFragment(Fragment, String)}
     * is now used by the helper methods (showHomeFragment, showBookingsFragment, etc.).
     *
     * @param fragment The RenterHomeFragment instance to load.
     */
    private void loadFragment(RenterHomeFragment fragment) {
        Log.d(TAG, "loadFragment (RenterHomeFragment specific - original method): Loading RenterHomeFragment.");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace the content of the FrameLayout (binding.fragmentsFl) with the RenterHomeFragment.
        transaction.replace(binding.fragmentsFl.getId(), fragment, "RenterHomeFragment"); // Tag is hardcoded
        // Commit the transaction.
        transaction.commit();
    }
}
