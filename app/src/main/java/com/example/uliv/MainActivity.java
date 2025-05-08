package com.example.uliv;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

//import androidx.activity.EdgeToEdge;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import com.example.uliv.databinding.ActivityMainBinding;
import com.example.uliv.fragments.EnquiriesListFragment;
import com.example.uliv.fragments.NotificationListFragment;
import com.example.uliv.fragments.HomeFragment;
import com.example.uliv.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

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
                    showHomeFragment();

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

    private void showHomeFragment() {

        binding.toolbarTitleTv.setText("Home");


        HomeFragment homeFragment = new HomeFragment();
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