package com.example.uliv;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.uliv.databinding.ActivityRenterMainBinding;
import com.example.uliv.fragments.renter.RenterHomeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class RenterMainActivity extends AppCompatActivity {

    private ActivityRenterMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRenterMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            loadFragment(new RenterHomeFragment());
            binding.bottomNavigationView.setSelectedItemId(R.id.item_home);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.item_home) {
                    loadFragment(new RenterHomeFragment());
                    return true;
                } else if (itemId == R.id.item_bookings) {
                    loadFragment(new RenterHomeFragment()); // Temporary
                    return true;
                } else if (itemId == R.id.item_notification) {
                    loadFragment(new RenterHomeFragment()); // Temporary
                    return true;
                } else if (itemId == R.id.item_profile) {
                    loadFragment(new RenterHomeFragment()); // Temporary
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(RenterHomeFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.fragmentsFl.getId(), fragment, "RenterHomeFragment");
        transaction.commit();
    }
}