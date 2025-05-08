package com.example.uliv;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.uliv.databinding.ActivityMainBinding;
import com.example.uliv.fragments.owner.NotificationListFragment;
import com.example.uliv.fragments.owner.ProfileFragment;
import com.example.uliv.fragments.renter.BookingsFragment;
import com.example.uliv.fragments.renter.RenterHomeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class RenterMainActivity extends AppCompatActivity {

    //View Binding
    private ActivityMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity_main.xml = ActivityMainBinding;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.item_home) {
                    showRenterHomeFragment();

                } else if (itemId == R.id.item_bookings) {
                    showBookingsFragment();

                } else if (itemId == R.id.item_notification) {
                    showNotificationListFragment();

                } else if (itemId == R.id.item_profile) {
                    showProfileFragment();

                }

                return;

            }
        });
    }

    private void showRenterHomeFragment() {

        binding.toolbarTitleTv.setText("Home");


        RenterHomeFragment homeFragment = new RenterHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), homeFragment, "HomeFragment");
        fragmentTransaction.commit();
    }

    private void showBookingsFragment() {

        binding.toolbarTitleTv.setText("Bookings");


        BookingsFragment bookingsFragment = new BookingsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), bookingsFragment, "BookingsFragment");
        fragmentTransaction.commit();
    }

    private void showNotificationListFragment() {

        binding.toolbarTitleTv.setText("Notifications");


        NotificationListFragment notificationListFragment = new NotificationListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), notificationListFragment, "NotificationListFragment");
        fragmentTransaction.commit();
    }

    private void showProfileFragment() {

        binding.toolbarTitleTv.setText("Profile");


        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), profileFragment, "profileFragment");
        fragmentTransaction.commit();
    }
}
