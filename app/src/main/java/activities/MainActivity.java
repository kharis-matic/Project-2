package activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

//import androidx.activity.EdgeToEdge;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import com.example.ulive.R;
import com.example.ulive.data.AppDatabase;
import com.example.ulive.data.dao.UserDao;
import com.example.ulive.data.models.User;
import com.example.ulive.databinding.ActivityMainBinding;
import com.example.uliv.fragments.PropertiesListFragment;
import com.example.uliv.fragments.NotificationListFragment;
import com.example.uliv.fragments.HomeFragment;
import com.example.uliv.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

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

                } else if (itemId == R.id.item_properties) {
                    showPropertiesListFragment();

                } else if (itemId == R.id.item_notification) {
                    showNotificationListFragment();

                } else if (itemId == R.id.item_profile) {
                    showProfileFragment();

                }

                return;

            }
        });

        testRoomDatabase();
    }

    private void showHomeFragment() {

        binding.toolbarTitleTv.setText("Home");


        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), homeFragment, "HomeFragment");
        fragmentTransaction.commit();
    }

    private void showPropertiesListFragment() {

        binding.toolbarTitleTv.setText("Properties");


        PropertiesListFragment propertiesListFragment = new PropertiesListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), propertiesListFragment, "PropertiesListFragment");
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

    private void testRoomDatabase() {
        Log.d("DB_TEST", "Starting Room database test");

        // Create database on a background thread
        new Thread(() -> {
            try {
                Log.d("DB_TEST", "Getting database instance");
                AppDatabase db = AppDatabase.getDatabase(MainActivity.this);

                if (db == null) {
                    Log.e("DB_TEST", "Database instance is null!");
                    return;
                }

                Log.d("DB_TEST", "Getting userDao");
                UserDao userDao = db.userDao();

                if (userDao == null) {
                    Log.e("DB_TEST", "UserDao is null!");
                    return;
                }

                // Create test user
                Log.d("DB_TEST", "Creating test user");
                User user = new User("Test", "User", "Male", "Renter", "test@example.com", "1234567890");

                // Insert user
                Log.d("DB_TEST", "Inserting user into database");
                userDao.insert(user);

                // Verify user was inserted
                Log.d("DB_TEST", "Querying all users");
                List<User> users = userDao.getAll();

                Log.d("DB_TEST", "Found " + users.size() + " users in database");
                for (User u : users) {
                    Log.d("DB_TEST", "User: " + u.getFullName() + ", Email: " + u.getEmail());
                }

                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,
                            "Database test complete. Found " + users.size() + " users",
                            Toast.LENGTH_LONG).show();
                });

            } catch (Exception e) {
                Log.e("DB_TEST", "Database test failed: " + e.getMessage());
                e.printStackTrace();

                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,
                            "Database test failed: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }




}