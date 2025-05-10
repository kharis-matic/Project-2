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
import android.widget.PopupMenu; // Import for PopupMenu
import android.widget.Toast; // Import for Toast
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uliv.Utils;
import com.example.uliv.R;
import com.example.uliv.databinding.ActivityPostAddBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * PostAddActivity is responsible for allowing users to create and add new property posts.
 * It handles user input for post details, image selection from gallery or camera,
 * and categorizing the post.
 */
public class PostAddActivity extends AppCompatActivity {

    // Binding object for accessing views in the layout activity_post_add.xml
    private ActivityPostAddBinding binding;
    // TAG for logging purposes, helps in debugging
    private static final String TAG = "POST_ADD_TAG";

    // Stores the currently selected property category by the user
    private String selectedCategory = "";

    // --- Variables for Image Picking ---
    // Uri to store the path of the image selected from gallery or captured by camera
    private Uri imageUri = null;


    // --- ActivityResultLaunchers for handling results from other activities ---
    // Launcher for requesting storage permission (for accessing gallery)
    private ActivityResultLauncher<String> requestStoragePermissionLauncher;
    // Launcher for requesting camera and storage permissions (for taking photos)
    private ActivityResultLauncher<String[]> requestCameraPermissionsLauncher;
    // Launcher for handling the result of picking an image from the gallery
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    // Launcher for handling the result of capturing an image with the camera
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;

    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up: create views,
     * bind data to lists, etc.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this activity using view binding
        binding = ActivityPostAddBinding.inflate(getLayoutInflater());
        // Set the inflated layout as the content view
        setContentView(binding.getRoot());

        // Initialize all ActivityResultLaunchers for permissions and activity results
        initializeActivityLaunchers();

        // Set up the initial state of the UI based on the default or first property category
        if (Utils.propertyCategories.length > 0) {
            selectedCategory = Utils.propertyCategories[0]; // Default to the first category in the Utils array
            setupUIForCategory(selectedCategory); // Adjust UI elements based on this category
        } else {
            Log.e(TAG, "Property categories array is empty! Cannot set default category.");
        }

        // Setup listener for tab selection in the Property Category TabLayout
        binding.propertyCategoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /**
             * Called when a tab enters the selected state.
             * @param tab The tab that was selected
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                // Ensure the position is valid
                if (position >= 0 && position < Utils.propertyCategories.length) {
                    selectedCategory = Utils.propertyCategories[position]; // Update the selected category
                    setupUIForCategory(selectedCategory); // Update the UI based on the new category
                }
                Log.d(TAG, "onTabSelected: Selected Category: " + selectedCategory);
            }

            /**
             * Called when a tab exits the selected state.
             * @param tab The tab that was unselected
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No specific action needed when a tab is unselected in this context
            }

            /**
             * Called when a tab that is already selected is chosen again by the user.
             * Some applications may use this action to return to the top level of a category.
             * @param tab The tab that was reselected.
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optionally, re-setup UI if needed or perform other actions on reselection
                if (tab.getPosition() >= 0 && tab.getPosition() < Utils.propertyCategories.length) {
                    setupUIForCategory(Utils.propertyCategories[tab.getPosition()]);
                }
            }
        });

        // Set up click listener for the toolbar's back button
        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the back button is clicked.
             * Finishes the current activity, returning the user to the previous screen.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                finish(); // Closes the current activity
                // Alternatively, onBackPressed(); could be used for standard back navigation behavior.
            }
        });

        // Set up click listener for the submit post button (currently a placeholder)
        binding.submitPostBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the submit button is clicked.
             * This is where data validation and post submission logic will be implemented.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                // TODO: Implement data validation (e.g., check if all required fields are filled)
                // TODO: Implement submission logic (e.g., save data to a database or send to a server)
                Log.d(TAG, "Submit button clicked. Selected Category: " + selectedCategory);
                if (imageUri != null) {
                    Log.d(TAG, "Selected Image URI: " + imageUri.toString());
                }
                // Example of how to gather data from EditText fields:
                // String propertyTitle = binding.propertyTitleEt.getText().toString().trim();
                // String description = binding.descriptionEt.getText().toString().trim();
                // String bedSpaces = binding.bedroomsEt.getText().toString().trim();
                // String bathrooms = binding.bathroomsEt.getText().toString().trim();
                // String location = binding.locationEt.getText().toString().trim();
                // String rent = binding.rentEt.getText().toString().trim();

                // Validate data here before proceeding. For example:
                // if (propertyTitle.isEmpty()) {
                //     binding.propertyTitleEt.setError("Title is required");
                //     binding.propertyTitleEt.requestFocus();
                //     return;
                // }
                // if (imageUri == null) {
                //     Toast.makeText(PostAddActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                //     return;
                // }
                // Proceed with submitting the post
            }
        });

        // Set up click listener for the "Pick Images" TextView
        // This TextView (pickImagesTv) should exist in your activity_post_add.xml layout
        binding.pickImagesTv.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the "Pick Images" TextView is clicked.
             * Shows options to pick an image from the camera or gallery.
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                showImagePickOptions(); // Display a popup menu for image source selection
            }
        });
    }

    /**
     * Initializes the ActivityResultLaunchers used for handling permissions and activity results.
     * This method sets up launchers for:
     * 1. Requesting storage permission (for gallery access on older Android versions).
     * 2. Requesting camera and storage permissions (for camera access).
     * 3. Handling the result from the gallery image picker.
     * 4. Handling the result from the camera image capture.
     */
    private void initializeActivityLaunchers() {
        // Launcher for requesting WRITE_EXTERNAL_STORAGE permission (primarily for gallery on pre-Tiramisu)
        requestStoragePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), // Contract for requesting a single permission
                isGranted -> { // Callback with the permission result
                    Log.d(TAG, "requestStoragePermissionLauncher: isGranted: " + isGranted);
                    if (isGranted) {
                        pickImageGallery(); // If permission granted, open the gallery
                    } else {
                        // Permission denied, show a toast message
                        Toast.makeText(this, "Storage permission denied. Cannot access gallery.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Launcher for requesting multiple permissions: CAMERA and WRITE_EXTERNAL_STORAGE (for camera on pre-Tiramisu)
        // or just CAMERA on Android 13 (Tiramisu) and above.
        requestCameraPermissionsLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), // Contract for requesting multiple permissions
                result -> { // Callback with a map of permissions and their grant status
                    Log.d(TAG, "requestCameraPermissionsLauncher: Result: " + result.toString());
                    boolean areAllGranted = true;
                    // Iterate through the results to check if all requested permissions were granted
                    for (String permission : result.keySet()) {
                        // If any permission in the map is not null and not granted, set areAllGranted to false
                        if (result.get(permission) != null && !result.get(permission)) {
                            areAllGranted = false;
                            break; // No need to check further if one is denied
                        }
                    }

                    if (areAllGranted) {
                        pickImageCamera(); // If all permissions granted, open the camera
                    } else {
                        // Permissions denied, show a toast message
                        Toast.makeText(this, "Camera and/or Storage permissions denied. Cannot use camera.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Launcher for handling the result from the gallery image picker intent
        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), // Contract for starting an activity and getting a result
                result -> { // Callback with the activity result
                    Log.d(TAG, "galleryActivityResultLauncher: onActivityResult");
                    // Check if the result was successful (RESULT_OK)
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData(); // Get the intent data
                        // Check if data and the image URI are not null
                        if (data != null && data.getData() != null) {
                            imageUri = data.getData(); // Store the URI of the selected image
                            Log.d(TAG, "galleryActivityResultLauncher: Image Uri: " + imageUri.toString());
                            // TODO: Display this image in an ImageView or add to a list for a RecyclerView
                            // Example: binding.yourImageView.setImageURI(imageUri);
                            // binding.imagePreviewIv.setImageURI(imageUri); // Assuming you have an ImageView with this ID
                            // binding.imagePreviewIv.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "Picked image: " + imageUri.toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Failed to get image from gallery.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User cancelled the gallery operation
                        Toast.makeText(this, "Gallery pick cancelled.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Launcher for handling the result from the camera image capture intent
        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), // Contract for starting an activity and getting a result
                result -> { // Callback with the activity result
                    Log.d(TAG, "cameraActivityResultLauncher: onActivityResult");
                    // Check if the result was successful (RESULT_OK)
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // The image was captured and saved to the 'imageUri' provided to the camera intent
                        Log.d(TAG, "cameraActivityResultLauncher: Image Uri: " + imageUri.toString());
                        // TODO: Display this image in an ImageView or add to a list for a RecyclerView
                        // Example: binding.yourImageView.setImageURI(imageUri);
                        // binding.imagePreviewIv.setImageURI(imageUri); // Assuming you have an ImageView
                        // binding.imagePreviewIv.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Captured image: " + imageUri.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        // User cancelled the camera operation
                        Toast.makeText(this, "Camera capture cancelled.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    /**
     * Displays a PopupMenu with options to choose an image from the Camera or Gallery.
     * The menu is anchored to the `pickImagesTv` TextView.
     */
    private void showImagePickOptions() {
        Log.d(TAG, "showImagePickOptions: Displaying image source options.");
        // Create a new PopupMenu, anchored to the pickImagesTv TextView
        PopupMenu popupMenu = new PopupMenu(this, binding.pickImagesTv);
        // Add "Camera" option to the menu with ID 1
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Camera");
        // Add "Gallery" option to the menu with ID 2
        popupMenu.getMenu().add(Menu.NONE, 2, 2, "Gallery");
        // Show the PopupMenu
        popupMenu.show();

        // Set a listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /**
             * Called when a menu item in the PopupMenu is clicked.
             * @param item The menu item that was clicked.
             * @return True if the event was handled, false otherwise.
             */
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId(); // Get the ID of the clicked item
                if (itemId == 1) { // "Camera" option selected
                    Log.d(TAG, "onMenuItemClick: Camera Option Clicked");
                    // Check Android version for permission handling
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33) and above
                        // For Android 13+, only CAMERA permission is needed for camera.
                        // WRITE_EXTERNAL_STORAGE is not required for app-specific storage or MediaStore.
                        requestCameraPermissionsLauncher.launch(new String[]{Manifest.permission.CAMERA});
                    } else { // Below Android 13
                        // Request CAMERA and WRITE_EXTERNAL_STORAGE permissions
                        requestCameraPermissionsLauncher.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                    }
                } else if (itemId == 2) { // "Gallery" option selected
                    Log.d(TAG, "onMenuItemClick: Gallery Option Clicked");
                    // Check Android version for permission handling related to gallery
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33) and above
                        // For ACTION_PICK on Android 13+, no explicit storage permission is needed
                        // as the system photo picker is used.
                        pickImageGallery();
                    } else { // Below Android 13
                        // Request WRITE_EXTERNAL_STORAGE permission to access gallery images
                        requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }
                return true; // Indicate that the click event was handled
            }
        });
    }

    /**
     * Initiates an intent to pick an image from the device's gallery.
     * Uses {@link #galleryActivityResultLauncher} to handle the result.
     */
    private void pickImageGallery() {
        Log.d(TAG, "pickImageGallery: Opening gallery.");
        // Create an intent to pick an image from external storage
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Set the type of content to pick (images only)
        intent.setType("image/*");
        // Launch the intent using the galleryActivityResultLauncher
        galleryActivityResultLauncher.launch(intent);
    }

    /**
     * Initiates an intent to capture an image using the device's camera.
     * The captured image will be saved to the MediaStore, and its URI will be stored in {@link #imageUri}.
     * Uses {@link #cameraActivityResultLauncher} to handle the result.
     */
    private void pickImageCamera() {
        Log.d(TAG, "pickImageCamera: Opening camera.");
        // Prepare ContentValues for the new image details in MediaStore
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image_" + System.currentTimeMillis()); // Set image title
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description"); // Set image description

        // Get a URI for the new image by inserting it into the MediaStore.
        // This URI will be used by the camera app to save the captured image.
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        // Create an intent to capture an image
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Provide the URI where the camera app should store the captured image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // Launch the intent using the cameraActivityResultLauncher
        cameraActivityResultLauncher.launch(intent);
    }


    /**
     * Sets up the UI elements based on the selected property category.
     * Currently, this method ensures that common input fields are visible.
     * The commented-out section shows how hints for EditText fields like
     * bedrooms and bathrooms could be dynamically changed based on the category.
     *
     * @param category The selected property category (e.g., "Boarding House", "Apartment").
     */
    private void setupUIForCategory(String category) {
        Log.d(TAG, "setupUIForCategory: Setting up for category: " + category + ". Using default XML hints for bedrooms/bathrooms.");

        // Ensure common TextInputLayouts are visible for all categories.
        // to provide sensible defaults. This method can then override them if specific categories
        // require different hint text.

        binding.propertyTitleTil.setVisibility(View.VISIBLE);
        binding.descriptionTil.setVisibility(View.VISIBLE);
        binding.locationTil.setVisibility(View.VISIBLE);
        binding.rentTil.setVisibility(View.VISIBLE);

        // Assuming bedroomsTil and bathroomsTil are also common fields that should always be visible.
        // Their hints are expected to be set appropriately in the XML or can be adjusted here
        // if they vary significantly per category. For now, we're just ensuring visibility.
        binding.bedroomsTil.setVisibility(View.VISIBLE);
        binding.bathroomsTil.setVisibility(View.VISIBLE);


        // This section demonstrates how you could customize hints for specific categories.
        // If you uncomment this, ensure the Utils.propertyCategories array contains these exact strings.
        /*
        Log.d(TAG, "setupUIForCategory: Setting up for " + category);

        // Reset all potentially conditional fields to a default state (e.g., visible)
        // Then, adjust visibility based on the selected category.
        // These are the fields available in your XML:
        // propertyTitleTil, descriptionTil, bedroomsTil, bathroomsTil, locationTil, rentTil

        // Common fields that are likely always visible:
        binding.propertyTitleTil.setVisibility(View.VISIBLE);
        binding.descriptionTil.setVisibility(View.VISIBLE);
        binding.locationTil.setVisibility(View.VISIBLE);
        binding.rentTil.setVisibility(View.VISIBLE);

        // Conditional fields based on category:
        // Here, we can change the hints on the input fields based on the selected property type.
        if (category.equals(Utils.CATEGORY_BOARDING_HOUSE)) { // Assuming Utils.CATEGORY_BOARDING_HOUSE = "Boarding House"
            binding.bedroomsTil.setHint("Bed Spaces Available");
            binding.bedroomsTil.setVisibility(View.VISIBLE);
            binding.bathroomsTil.setHint("Shared/Private Bathrooms");
            binding.bathroomsTil.setVisibility(View.VISIBLE);
        } else if (category.equals(Utils.CATEGORY_DORM)) { // Assuming Utils.CATEGORY_DORM = "Dorm"
            binding.bedroomsTil.setHint("Bed Spaces/Room Capacity");
            binding.bedroomsTil.setVisibility(View.VISIBLE);
            binding.bathroomsTil.setHint("Common Bathrooms");
            binding.bathroomsTil.setVisibility(View.VISIBLE);
        } else if (category.equals(Utils.CATEGORY_PAD)) { // Assuming Utils.CATEGORY_PAD = "Pad"
            binding.bedroomsTil.setHint("Number of Bedrooms");
            binding.bedroomsTil.setVisibility(View.VISIBLE);
            binding.bathroomsTil.setHint("Number of Bathrooms");
            binding.bathroomsTil.setVisibility(View.VISIBLE);
        } else if (category.equals(Utils.CATEGORY_APARTMENT)) { // Assuming Utils.CATEGORY_APARTMENT = "Apartment"
            binding.bedroomsTil.setHint("Number of Bedrooms (e.g., Studio, 1BHK, 2BHK)");
            binding.bedroomsTil.setVisibility(View.VISIBLE);
            binding.bathroomsTil.setHint("Number of Bathrooms");
            binding.bathroomsTil.setVisibility(View.VISIBLE);
        } else {
            // Default case or for other categories if added later
            binding.bedroomsTil.setVisibility(View.VISIBLE);
            binding.bathroomsTil.setVisibility(View.VISIBLE);
            // Reset to generic hints or use hints defined in XML as default
            binding.bedroomsTil.setHint(getString(R.string.default_bedrooms_hint)); // Assuming you have this in strings.xml
            binding.bathroomsTil.setHint(getString(R.string.default_bathrooms_hint)); // Assuming you have this in strings.xml
        }
        */
    }
}