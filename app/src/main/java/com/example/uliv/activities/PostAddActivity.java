package com.example.uliv.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri; // Correct import for Uri
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
// ArrayAdapter is not currently used as AutoCompleteTextViews are not in the provided XML
// import android.widget.ArrayAdapter;
import android.widget.PopupMenu; // Import for PopupMenu
import android.widget.Toast; // Import for Toast

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uliv.Utils;
import com.example.uliv.R;
import com.example.uliv.databinding.ActivityPostAddBinding;
import com.google.android.material.tabs.TabLayout;

public class PostAddActivity extends AppCompatActivity {

    private ActivityPostAddBinding binding;
    private static final String TAG = "POST_ADD_TAG";

    private String selectedCategory = "";

    // --- Variables for Image Picking ---
    private Uri imageUri = null; // To hold the URI of the selected/captured image


    // ActivityResultLaunchers for permissions and image picking
    private ActivityResultLauncher<String> requestStoragePermissionLauncher;
    private ActivityResultLauncher<String[]> requestCameraPermissionsLauncher;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ActivityResultLaunchers for image picking and permissions
        initializeActivityLaunchers();

        // Initialize default selected category
        if (Utils.propertyCategories.length > 0) {
            selectedCategory = Utils.propertyCategories[0]; // Default to first category
            setupUIForCategory(selectedCategory);
        } else {
            Log.e(TAG, "Property categories array is empty!");
        }

        // Setup for Property Category TabLayout
        binding.propertyCategoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position >= 0 && position < Utils.propertyCategories.length) {
                    selectedCategory = Utils.propertyCategories[position];
                    setupUIForCategory(selectedCategory);
                }
                Log.d(TAG, "onTabSelected: Selected Category: " + selectedCategory);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() >= 0 && tab.getPosition() < Utils.propertyCategories.length) {
                    setupUIForCategory(Utils.propertyCategories[tab.getPosition()]);
                }
            }
        });

        // Back Button Click
        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Or onBackPressed();
            }
        });

        // Submit Button Click (Placeholder)
        binding.submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement data validation and submission logic
                Log.d(TAG, "Submit button clicked. Selected Category: " + selectedCategory);
                if (imageUri != null) {
                    Log.d(TAG, "Selected Image URI: " + imageUri.toString());
                }
                // Gather data from:
                // String propertyTitle = binding.propertyTitleEt.getText().toString();
                // String description = binding.descriptionEt.getText().toString();
                // String bedSpaces = binding.bedroomsEt.getText().toString();
                // String bathrooms = binding.bathroomsEt.getText().toString();
                // String location = binding.locationEt.getText().toString();
                // String rent = binding.rentEt.getText().toString();
            }
        });

        // --- Click Listener for Image Selection ---
        // Ensure your "Pick Images" TextView in XML has android:id="@+id/pickImagesTextView"
        binding.pickImagesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickOptions();
            }
        });
    }

                                        // Permissions for accessing images on gallery and camera
    private void initializeActivityLaunchers() {
        // Launcher for Storage Permission (Gallery)
        requestStoragePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    Log.d(TAG, "requestStoragePermissionLauncher: isGranted: " + isGranted);
                    if (isGranted) {
                        pickImageGallery();
                    } else {
                        Toast.makeText(this, "Storage permission denied.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Launcher for Camera Permissions
        requestCameraPermissionsLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    Log.d(TAG, "requestCameraPermissionsLauncher: Result: " + result.toString());
                    boolean areAllGranted = true;
                    // Check if all permissions in the map are granted
                    for (String permission : result.keySet()) {
                        if (result.get(permission) != null && !result.get(permission)) {
                            areAllGranted = false;
                            break;
                        }
                    }

                    if (areAllGranted) {
                        pickImageCamera();
                    } else {
                        Toast.makeText(this, "Camera or Storage permissions denied.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Launcher for Gallery Intent result
        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.d(TAG, "galleryActivityResultLauncher: onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            imageUri = data.getData();
                            Log.d(TAG, "galleryActivityResultLauncher: Image Url: " + imageUri.toString());
                            // TODO: Display this image or add to a list for RecyclerView
                            // Example: binding.someImageView.setImageURI(imageUri);
                            Toast.makeText(this, "Picked image: " + imageUri.toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Failed to get image from gallery.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Gallery pick cancelled.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Launcher for Camera Intent result
        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.d(TAG, "cameraActivityResultLauncher: onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Image was captured and saved to imageUri specified in pickImageCamera()
                        Log.d(TAG, "cameraActivityResultLauncher: Image Uri: " + imageUri.toString());
                        // TODO: Display this image or add to a list for RecyclerView
                        // Example: binding.someImageView.setImageURI(imageUri);
                        Toast.makeText(this, "Captured image: " + imageUri.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Camera capture cancelled.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    private void showImagePickOptions() {
        Log.d(TAG, "showImagePickOptions: ");
        PopupMenu popupMenu = new PopupMenu(this, binding.pickImagesTv); // Anchor to the pickImagesTextView
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Camera"); // itemId 1 for Camera
        popupMenu.getMenu().add(Menu.NONE, 2, 2, "Gallery"); // itemId 2 for Gallery
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == 1) { // Camera
                    Log.d(TAG, "onMenuItemClick: Camera Option Clicked");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
                        requestCameraPermissionsLauncher.launch(new String[]{Manifest.permission.CAMERA});
                    } else {
                        requestCameraPermissionsLauncher.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                    }
                } else if (itemId == 2) { // Gallery
                    Log.d(TAG, "onMenuItemClick: Gallery Option Clicked");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
                        pickImageGallery(); // No explicit storage permission needed for ACTION_PICK
                    } else {
                        requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE); // For older versions
                    }
                }
                return true;
            }
        });
    }

    private void pickImageGallery() {
        Log.d(TAG, "pickImageGallery: ");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private void pickImageCamera() {
        Log.d(TAG, "pickImageCamera: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image_" + System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }


                                    // Just sets up the UI based on the selected Property Type/Category.
    private void setupUIForCategory(String category) {
        Log.d(TAG, "setupUIForCategory: Setting up for " + category + ". Using default XML hints for bedrooms/bathrooms.");

        // Common fields that are always visible (as per previous structure):
        binding.propertyTitleTil.setVisibility(View.VISIBLE);
        binding.descriptionTil.setVisibility(View.VISIBLE);
        binding.locationTil.setVisibility(View.VISIBLE);
        binding.rentTil.setVisibility(View.VISIBLE);

        // Assuming bedroomsTil and bathroomsTil should also always be visible
        // and their hints are set correctly in the XML for all categories:
        binding.bedroomsTil.setVisibility(View.VISIBLE);
        binding.bathroomsTil.setVisibility(View.VISIBLE);
    }
}


//    private void setupUIForCategory(String category) {
//        Log.d(TAG, "setupUIForCategory: Setting up for " + category);
//
//        // Reset all potentially conditional fields to a default state (e.g., visible)
//        // Then, adjust visibility based on the selected category.
//        // These are the fields available in your XML:
//        // propertyTitleTil, descriptionTil, bedroomsTil, bathroomsTil, locationTil, rentTil
//
//        // Common fields that are likely always visible:
//        binding.propertyTitleTil.setVisibility(View.VISIBLE);
//        binding.descriptionTil.setVisibility(View.VISIBLE);
//        binding.locationTil.setVisibility(View.VISIBLE);
//        binding.rentTil.setVisibility(View.VISIBLE);
//
//        // Conditional fields based on category:  Pretty much if we want to change the hints on the input fields, we can do it here.
//        if (category.equals("Boarding House")) {
//            binding.bedroomsTil.setHint("Bed Spaces Available"); // Example: Change hint
//            binding.bedroomsTil.setVisibility(View.VISIBLE);
//            binding.bathroomsTil.setHint("Bathrooms On Property"); // Example
//            binding.bathroomsTil.setVisibility(View.VISIBLE);
//        } else if (category.equals("Dorm")) {
//            binding.bedroomsTil.setHint("Bed Spaces Available"); // Example
//            binding.bedroomsTil.setVisibility(View.VISIBLE);
//            binding.bathroomsTil.setHint("Bathrooms On Property"); // Example
//            binding.bathroomsTil.setVisibility(View.VISIBLE);
//        } else if (category.equals("Pad")) {
//            binding.bedroomsTil.setHint("Number of Bedrooms"); // Example
//            binding.bedroomsTil.setVisibility(View.VISIBLE);
//            binding.bathroomsTil.setHint("Number of Bathrooms"); // Example
//            binding.bathroomsTil.setVisibility(View.VISIBLE);
//        } else if (category.equals("Apartment")) {
//            binding.bedroomsTil.setHint("Number of Bedrooms (e.g., 2BHK)"); // Example
//            binding.bedroomsTil.setVisibility(View.VISIBLE);
//            binding.bathroomsTil.setHint("Number of Bathrooms"); // Example
//            binding.bathroomsTil.setVisibility(View.VISIBLE);
//        } else {
//            // Default case or for other categories if added later
//            binding.bedroomsTil.setVisibility(View.VISIBLE);
//            binding.bathroomsTil.setVisibility(View.VISIBLE);
//            binding.bedroomsTil.setHint("Bed Spaces"); // Reset to default hint
//            binding.bathroomsTil.setHint("Bathrooms in Property"); // Reset to default hint
//        }
//    }