package com.example.uliv;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.uliv.databinding.ActivityMainBinding;
import com.example.uliv.databinding.ActivityOwnerMainBinding;
import com.example.uliv.fragments.owner.EnquiriesListFragment;
import com.example.uliv.fragments.owner.OwnerHomeFragment;
import com.example.uliv.fragments.NotificationListFragment;
import com.example.uliv.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class OwnerMainActivity extends AppCompatActivity {

    //View Binding
    private ActivityOwnerMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity_main.xml = ActivityMainBinding;
        binding = ActivityOwnerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {

            // Bottom navigation bar for Owner
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                // MISSING: should have if(user.owner) here so that whenever it redirects here the
                // page should redirect to home
                if (itemId == R.id.item_home) {
                    showOwnerHomeFragment();

                } else if (itemId == R.id.item_enquiries) {
                    showEnquriesListFragment();

                } else if (itemId == R.id.item_upload) {
                    showUploadListFragment();
                } else if (itemId == R.id.item_notification) {
                    showNotificationListFragment();

                } else if (itemId == R.id.item_profile) {
                    showProfileFragment();

                }

                return;

            }
        });
    }

    // Show functions that redirects to the fragments class
    private void showOwnerHomeFragment() {

        binding.toolbarTitleTv.setText("Home");


        OwnerHomeFragment homeFragment = new OwnerHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), homeFragment, "HomeFragment");
        fragmentTransaction.commit();
    }

    private void showEnquriesListFragment() {

        binding.toolbarTitleTv.setText("Enquries");


        EnquiriesListFragment enquriesListFragment = new EnquiriesListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), enquriesListFragment, "EnquriesListFragment");
        fragmentTransaction.commit();
    }

    private void showUploadListFragment() {

        binding.toolbarTitleTv.setText("Upload");


        EnquiriesListFragment uploadListFragment = new EnquiriesListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), uploadListFragment, "UploadListFragment");
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