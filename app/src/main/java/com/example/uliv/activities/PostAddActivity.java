package com.example.uliv.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uliv.R;
import com.example.uliv.Utils;
import com.example.uliv.adapters.AdapterImagePicked;
import com.example.uliv.databinding.ActivityPostAddBinding;
import com.example.uliv.databinding.ItemRoomInputBinding;
// Assuming the layout file for a single picked image item is "row_image_picked.xml",
// which will generate "RowImagePickedBinding". If your file is "row_images_picked.xml",
// then the binding class would be "RowImagesPickedBinding". Ensure this matches your project.
import com.example.uliv.databinding.RowImagesPickedBinding;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * PostAddActivity allows users to create new property listings.
 * It handles input for property details, address, multiple images,
 * dynamic room information, and category selection. It includes
 * data validation and prepares data for submission.
 */
public class PostAddActivity extends AppCompatActivity implements AdapterImagePicked.OnImageRemoveListener {

    /**
     * View binding object for the `activity_post_add.xml` layout.
     */
    private ActivityPostAddBinding binding;
    /**
     * Tag for logging messages from this activity.
     */
    private static final String TAG = "POST_ADD_TAG";
    /**
     * Stores the currently selected property category (e.g., "Boarding House", "Dorm").
     */
    private String selectedCategory = "";

    /**
     * List of URIs for images selected by the user for the property.
     */
    private ArrayList<Uri> selectedImageUris;
    /**
     * Adapter for displaying the selected images in a RecyclerView.
     */
    private AdapterImagePicked adapterImagePicked;

    /**
     * Launcher for requesting storage permission (e.g., for accessing the gallery).
     */
    private ActivityResultLauncher<String> requestStoragePermissionLauncher;
    /**
     * Launcher for requesting multiple permissions, typically for camera and associated storage access.
     */
    private ActivityResultLauncher<String[]> requestCameraPermissionsLauncher;
    /**
     * Launcher for handling the result of picking image(s) from the gallery.
     */
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    /**
     * Launcher for handling the result of capturing an image with the camera.
     */
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    /**
     * Temporarily stores the URI for an image being captured by the camera.
     * This is used because the camera intent needs an output URI before it's launched.
     */
    private Uri tempCameraImageUri = null;

    /**
     * List to store {@link Room} objects, each representing a room's details added by the user.
     */
    private ArrayList<Room> roomsDataList;
    /**
     * Counter to assign a unique UI number to newly added room input sections.
     */
    private int nextRoomNumber = 1;

    /**
     * Dialog to show progress to the user during operations like data validation or submission.
     */
    private ProgressDialog progressDialog;

    /**
     * Inner class representing a structured address for a property.
     */
    private static class Address {
        String street, purok, barangay, municipality, province;

        /**
         * Constructs an Address object.
         * @param street The street name (optional).
         * @param purok The purok, zone, block, or lot (required).
         * @param barangay The barangay (required).
         * @param municipality The municipality or city (required).
         * @param province The province (required).
         */
        public Address(String street, String purok, String barangay, String municipality, String province) {
            this.street = street; this.purok = purok; this.barangay = barangay;
            this.municipality = municipality; this.province = province;
        }

        /**
         * Returns a string representation of the address.
         * @return A string detailing the address components.
         */
        @Override public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", purok='" + purok + '\'' +
                    ", barangay='" + barangay + '\'' +
                    ", municipality='" + municipality + '\'' +
                    ", province='" + province + '\'' +
                    '}';
        }
    }

    /**
     * Inner class representing a single room within a property.
     * It stores details like bed spaces, rent, description, and a reference to its UI view.
     */
    private static class Room {
        int uiRoomNumber;
        String bedSpaces, monthlyRent, description;
        View associatedView; // Reference to the dynamically created view for this room

        /**
         * Constructs a Room object.
         * @param uiRoomNumber The number displayed on the UI for this room (e.g., "Room #1").
         * @param bedSpaces Number of bed spaces in the room.
         * @param monthlyRent Monthly rent for the room.
         * @param description Description of the room.
         * @param view The View object representing this room's input fields in the UI.
         */
        public Room(int uiRoomNumber, String bedSpaces, String monthlyRent, String description, View view) {
            this.uiRoomNumber = uiRoomNumber; this.bedSpaces = bedSpaces;
            this.monthlyRent = monthlyRent; this.description = description;
            this.associatedView = view;
        }

        // Getters
        public String getBedSpaces() { return bedSpaces; }
        public String getMonthlyRent() { return monthlyRent; }
        public String getDescription() { return description; }
        public int getUiRoomNumber() { return uiRoomNumber; }
        public View getAssociatedView() { return associatedView; }

        /**
         * Returns a string representation of the room.
         * @return A string detailing the room's attributes.
         */
        @Override public String toString() {
            return "Room{" +
                    "uiRoomNumber=" + uiRoomNumber +
                    ", bedSpaces='" + bedSpaces + '\'' +
                    ", monthlyRent='" + monthlyRent + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    /**
     * Called when the activity is first created.
     * Initializes view binding, lists, progress dialog, activity result launchers,
     * and UI components like the image picker RecyclerView, TabLayout, and click listeners.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedImageUris = new ArrayList<>();
        roomsDataList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        initializeActivityLaunchers();
        setupImagePickerRecyclerView();

        setupTabLayout();
        setupClickListeners();
    }

    /**
     * Initializes the RecyclerView for displaying selected images.
     * Sets up its layout manager and adapter.
     */
    private void setupImagePickerRecyclerView() {
        adapterImagePicked = new AdapterImagePicked(this, selectedImageUris, this);
        binding.pickImagesRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.pickImagesRv.setAdapter(adapterImagePicked);
        Log.d(TAG, "setupImagePickerRecyclerView: RecyclerView for picked images is set up.");
    }

    /**
     * Sets up the TabLayout for property categories.
     * It first checks if tabs are defined in XML. If so, it uses them and sets the
     * initial selected category. If not, it falls back to populating tabs from
     * {@link Utils#propertyCategories}. If no categories are found in either,
     * the TabLayout is hidden.
     */
    private void setupTabLayout() {
        if (binding.propertyCategoryTabLayout.getTabCount() > 0) {
            Log.d(TAG, "setupTabLayout: Tabs found in XML. Count: " + binding.propertyCategoryTabLayout.getTabCount());
            TabLayout.Tab firstTab = binding.propertyCategoryTabLayout.getTabAt(0);
            if (firstTab != null && firstTab.getText() != null) {
                if (binding.propertyCategoryTabLayout.getSelectedTabPosition() == -1 || binding.propertyCategoryTabLayout.getSelectedTabPosition() == 0) {
                    firstTab.select();
                    selectedCategory = firstTab.getText().toString();
                    Log.d(TAG, "setupTabLayout: Initial selected category from XML: " + selectedCategory);
                } else {
                    TabLayout.Tab currentSelectedTab = binding.propertyCategoryTabLayout.getTabAt(binding.propertyCategoryTabLayout.getSelectedTabPosition());
                    if (currentSelectedTab != null && currentSelectedTab.getText() != null) {
                        selectedCategory = currentSelectedTab.getText().toString();
                        Log.d(TAG, "setupTabLayout: Retained selected category from XML: " + selectedCategory);
                    }
                }
            } else {
                Log.w(TAG, "setupTabLayout: First tab from XML is null or has no text.");
                if (Utils.propertyCategories != null && Utils.propertyCategories.length > 0) {
                    selectedCategory = Utils.propertyCategories[0];
                    Log.d(TAG, "setupTabLayout: Fallback to Utils for initial category: " + selectedCategory);
                } else {
                    selectedCategory = "";
                }
            }
        } else {
            Log.d(TAG, "setupTabLayout: No tabs found in XML. Populating from Utils.propertyCategories.");
            if (Utils.propertyCategories == null || Utils.propertyCategories.length == 0) {
                Log.e(TAG, "setupTabLayout: No property categories in Utils to display.");
                binding.propertyCategoryTabLayout.setVisibility(View.GONE);
                selectedCategory = "";
                return;
            }

            binding.propertyCategoryTabLayout.setVisibility(View.VISIBLE);
            for (String category : Utils.propertyCategories) {
                binding.propertyCategoryTabLayout.addTab(binding.propertyCategoryTabLayout.newTab().setText(category));
            }

            if (binding.propertyCategoryTabLayout.getTabCount() > 0) {
                TabLayout.Tab firstTab = binding.propertyCategoryTabLayout.getTabAt(0);
                if (firstTab != null) {
                    firstTab.select();
                    selectedCategory = Objects.requireNonNull(firstTab.getText()).toString();
                    Log.d(TAG, "setupTabLayout: First tab (from Utils) selected: " + selectedCategory);
                }
            }
        }

        binding.propertyCategoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() != null) {
                    selectedCategory = tab.getText().toString();
                    Log.d(TAG, "onTabSelected: Selected Category: " + selectedCategory);
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getText() != null) {
                    selectedCategory = tab.getText().toString();
                    Log.d(TAG, "onTabReselected: Category: " + selectedCategory);
                }
            }
        });
    }

    /**
     * Sets up click listeners for various UI elements like toolbar back button,
     * image picking trigger, add room button, and submit post button.
     */
    private void setupClickListeners() {
        binding.toolbarBackBtn.setOnClickListener(v -> finish());
        binding.pickImagesTv.setOnClickListener(v -> showImagePickOptions());
        binding.addRoomBtn.setOnClickListener(v -> addNewRoomInputView());
        binding.submitPostBtn.setOnClickListener(v -> validateAndSubmitPost());
    }

    /**
     * Dynamically inflates and adds a new room input section (from `item_room_input.xml`)
     * to the `roomsContainer` LinearLayout. Assigns a unique UI number to the room
     * and sets up a remove button for that room section.
     */
    private void addNewRoomInputView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final ItemRoomInputBinding roomBinding = ItemRoomInputBinding.inflate(inflater, binding.roomsContainer, false);

        roomBinding.roomNumberTv.setText(getString(R.string.room_number_prefix, nextRoomNumber));
        final int currentRoomUiNumber = nextRoomNumber;
        nextRoomNumber++;

        final Room currentRoomData = new Room(currentRoomUiNumber, "", "", "", roomBinding.getRoot());
        roomBinding.getRoot().setTag(currentRoomData);

        roomBinding.removeRoomBtn.setOnClickListener(v -> {
            Object tag = roomBinding.getRoot().getTag();
            if (tag instanceof Room) {
                Room roomAssociatedWithView = (Room) tag;
                Log.d(TAG, "Removing view for UI Room #" + roomAssociatedWithView.getUiRoomNumber());
            } else {
                Log.w(TAG, "Could not find associated Room data object for view to remove from list.");
            }
            binding.roomsContainer.removeView(roomBinding.getRoot());
            Toast.makeText(this, getString(R.string.room_view_removed, currentRoomUiNumber), Toast.LENGTH_SHORT).show();
        });

        binding.roomsContainer.addView(roomBinding.getRoot());
        Log.d(TAG, "addNewRoomInputView: Added room view #" + currentRoomUiNumber);
    }

    /**
     * Validates all user inputs for the property post.
     * If all validations pass, it prepares the data (including property details,
     * address, room details, and image URIs) for submission to a database.
     * Shows progress and error messages to the user.
     */
    private void validateAndSubmitPost() {
        progressDialog.setMessage(getString(R.string.validating_data));
        progressDialog.show();

        String propertyTitle = Objects.requireNonNull(binding.propertyTitleEt.getText()).toString().trim();
        String propertyDescription = Objects.requireNonNull(binding.descriptionEt.getText()).toString().trim();

        if (TextUtils.isEmpty(propertyTitle)) {
            binding.propertyTitleEt.setError(getString(R.string.error_field_required));
            binding.propertyTitleEt.requestFocus();
            Toast.makeText(this, "Property title is required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(propertyDescription)) {
            binding.descriptionEt.setError(getString(R.string.error_field_required));
            binding.descriptionEt.requestFocus();
            Toast.makeText(this, "Property description is required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(selectedCategory)) {
            Toast.makeText(this, "Please select a property category.", Toast.LENGTH_SHORT).show();
            binding.propertyCategoryTabLayout.requestFocus();
            progressDialog.dismiss();
            return;
        }

        String street = Objects.requireNonNull(binding.streetEt.getText()).toString().trim();
        String purok = Objects.requireNonNull(binding.purokEt.getText()).toString().trim();
        String barangay = Objects.requireNonNull(binding.barangayEt.getText()).toString().trim();
        String municipality = Objects.requireNonNull(binding.municipalityEt.getText()).toString().trim();
        String province = Objects.requireNonNull(binding.provinceEt.getText()).toString().trim();

        if (TextUtils.isEmpty(purok)) { binding.purokEt.setError(getString(R.string.error_field_required)); binding.purokEt.requestFocus(); progressDialog.dismiss(); return; }
        else binding.purokEt.setError(null);
        if (TextUtils.isEmpty(barangay)) { binding.barangayEt.setError(getString(R.string.error_field_required)); binding.barangayEt.requestFocus(); progressDialog.dismiss(); return; }
        else binding.barangayEt.setError(null);
        if (TextUtils.isEmpty(municipality)) { binding.municipalityEt.setError(getString(R.string.error_field_required)); binding.municipalityEt.requestFocus(); progressDialog.dismiss(); return; }
        else binding.municipalityEt.setError(null);
        if (TextUtils.isEmpty(province)) { binding.provinceEt.setError(getString(R.string.error_field_required)); binding.provinceEt.requestFocus(); progressDialog.dismiss(); return; }
        else binding.provinceEt.setError(null);

        Address address = new Address(street, purok, barangay, municipality, province);
        String totalBathroomsStr = Objects.requireNonNull(binding.bathroomsEt.getText()).toString().trim();
        if (TextUtils.isEmpty(totalBathroomsStr)) {
            binding.bathroomsEt.setError(getString(R.string.error_field_required));
            binding.bathroomsEt.requestFocus();
            Toast.makeText(this, "Total bathrooms required.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        } else binding.bathroomsEt.setError(null);
        int totalBathrooms;
        try {
            totalBathrooms = Integer.parseInt(totalBathroomsStr);
            if (totalBathrooms < 0) {
                binding.bathroomsEt.setError("Cannot be negative");
                binding.bathroomsEt.requestFocus();
                Toast.makeText(this, "Bathrooms cannot be negative.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        } catch (NumberFormatException e) {
            binding.bathroomsEt.setError("Invalid number");
            binding.bathroomsEt.requestFocus();
            Toast.makeText(this, "Invalid number for bathrooms.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        roomsDataList.clear();
        boolean allRoomsValid = true;
        if (binding.roomsContainer.getChildCount() == 0) {
            Log.d(TAG, "No rooms added by user.");
            // TODO: Determine if rooms are mandatory. If so, show error and return.
        }

        for (int i = 0; i < binding.roomsContainer.getChildCount(); i++) {
            View roomView = binding.roomsContainer.getChildAt(i);
            ItemRoomInputBinding currentRoomBinding = ItemRoomInputBinding.bind(roomView);
            TextView roomNumberTv = currentRoomBinding.roomNumberTv;
            TextInputEditText bedSpacesEt = currentRoomBinding.roomBedSpacesEt;
            TextInputEditText rentEt = currentRoomBinding.roomRentEt;
            TextInputEditText roomDescriptionEt = currentRoomBinding.roomDescriptionEt;

            String bedSpacesStr = Objects.requireNonNull(bedSpacesEt.getText()).toString().trim();
            String rentStr = Objects.requireNonNull(rentEt.getText()).toString().trim();
            String roomDescription = Objects.requireNonNull(roomDescriptionEt.getText()).toString().trim();
            int uiRoomNum = Integer.parseInt(roomNumberTv.getText().toString().replace(getString(R.string.room_number_prefix_no_format), "").trim());

            if (TextUtils.isEmpty(bedSpacesStr)) { bedSpacesEt.setError(getString(R.string.error_field_required)); allRoomsValid = false; }
            else bedSpacesEt.setError(null);
            if (TextUtils.isEmpty(rentStr)) { rentEt.setError(getString(R.string.error_field_required)); allRoomsValid = false; }
            else rentEt.setError(null);

            if (allRoomsValid) {
                try {
                    int bedSpaces = Integer.parseInt(bedSpacesStr);
                    if (bedSpaces <= 0) { bedSpacesEt.setError("Must be > 0"); allRoomsValid = false;}
                } catch (NumberFormatException e) { bedSpacesEt.setError("Invalid number"); allRoomsValid = false; }
                try {
                    double rent = Double.parseDouble(rentStr);
                    if (rent <= 0) { rentEt.setError("Must be > 0"); allRoomsValid = false;}
                } catch (NumberFormatException e) { rentEt.setError("Invalid number"); allRoomsValid = false; }
            }

            if (!allRoomsValid) {
                Toast.makeText(this, getString(R.string.error_in_room, roomNumberTv.getText().toString()), Toast.LENGTH_SHORT).show();
                break;
            }
            roomsDataList.add(new Room(uiRoomNum, bedSpacesStr, rentStr, roomDescription, roomView));
        }

        if (!allRoomsValid) {
            Toast.makeText(this, "Please correct errors in room details.", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        if (selectedImageUris.isEmpty()) {
            Toast.makeText(this, "Please select at least one image for the property.", Toast.LENGTH_SHORT).show();
            binding.pickImagesTv.requestFocus();
            progressDialog.dismiss();
            return;
        }

        progressDialog.setMessage(getString(R.string.preparing_data));
        Log.d(TAG, "All validations passed. Preparing data...");

        Map<String, Object> propertyData = new HashMap<>();
        propertyData.put("title", propertyTitle);
        propertyData.put("description", propertyDescription);
        propertyData.put("category", selectedCategory);

        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("street", address.street);
        addressMap.put("purok", address.purok);
        addressMap.put("barangay", address.barangay);
        addressMap.put("municipality", address.municipality);
        addressMap.put("province", address.province);
        propertyData.put("address", addressMap);
        propertyData.put("totalBathrooms", totalBathrooms);

        List<Map<String, Object>> roomsToSubmit = new ArrayList<>();
        for (Room room : roomsDataList) {
            Map<String, Object> roomMap = new HashMap<>();
            roomMap.put("roomNumber", room.getUiRoomNumber());
            try {
                roomMap.put("bedSpaces", Integer.parseInt(room.getBedSpaces()));
                roomMap.put("monthlyRent", Double.parseDouble(room.getMonthlyRent()));
            } catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing room data for submission: " + room.toString(), e);
                Toast.makeText(this, "Internal error preparing room data for " + getString(R.string.room_number_prefix, room.getUiRoomNumber()), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
            roomMap.put("description", room.getDescription());
            roomsToSubmit.add(roomMap);
        }
        propertyData.put("rooms", roomsToSubmit);
        propertyData.put("timestampCreated", System.currentTimeMillis());
        // TODO: Add ownerUid: propertyData.put("ownerUid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        // TODO: Add sexRestriction if you have a UI element for it
        // TODO: Add availabilityStatus for the whole property if you have a UI element for it.

        // TODO: Implement image uploading to Firebase Storage.
        // This involves iterating selectedImageUris, uploading each, getting download URLs,
        // and adding those URLs to propertyData (e.g., as a List<String> under "imageUrls").
        // This entire process should be asynchronous.
        // After successful image uploads, then save propertyData to the database.

        List<String> imageUrisToUploadLog = new ArrayList<>();
        for(Uri uri : selectedImageUris){
            imageUrisToUploadLog.add(uri.toString());
        }
        propertyData.put("imageUris_local_paths_for_upload", imageUrisToUploadLog);

        Log.d(TAG, "Final Property Data (before image URL upload): " + propertyData.toString());

        // TODO: Replace with actual database submission logic (Firebase or SQL).
        new android.os.Handler().postDelayed(() -> {
            progressDialog.dismiss();
            Toast.makeText(PostAddActivity.this, "Property data prepared. Implement DB submission.", Toast.LENGTH_LONG).show();
            // finish();
        }, 2000);
    }

    /**
     * Initializes the {@link ActivityResultLauncher} instances for handling
     * permission requests and activity results for image picking.
     */
    private void initializeActivityLaunchers() {
        requestStoragePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Log.d(TAG, "Storage permission granted, launching gallery.");
                        pickImageGallery();
                    } else {
                        Toast.makeText(this, "Storage permission denied. Cannot access gallery.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestCameraPermissionsLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean areAllGranted = true;
                    for (String permission : result.keySet()) {
                        if (Boolean.FALSE.equals(result.get(permission))) {
                            areAllGranted = false;
                            Log.w(TAG, "Permission denied: " + permission);
                            break;
                        }
                    }
                    if (areAllGranted) {
                        Log.d(TAG, "Camera & related storage permissions granted, launching camera.");
                        pickImageCamera();
                    } else {
                        Toast.makeText(this, "Camera and/or Storage permissions denied.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        int previousSize = selectedImageUris.size();
                        if (data.getClipData() != null) {
                            ClipData clipData = data.getClipData();
                            Log.d(TAG, "Multiple images selected from gallery. Count: " + clipData.getItemCount());
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                if (!selectedImageUris.contains(imageUri)) {
                                    selectedImageUris.add(imageUri);
                                }
                            }
                        } else if (data.getData() != null) {
                            Uri imageUri = data.getData();
                            Log.d(TAG, "Single image selected from gallery: " + imageUri.toString());
                            if (!selectedImageUris.contains(imageUri)) {
                                selectedImageUris.add(imageUri);
                            }
                        }

                        if (selectedImageUris.size() > previousSize) {
                            adapterImagePicked.notifyItemRangeInserted(previousSize, selectedImageUris.size() - previousSize);
                        } else if (selectedImageUris.size() == previousSize && !selectedImageUris.isEmpty()) {
                            adapterImagePicked.notifyDataSetChanged();
                        }
                        Log.d(TAG, "Total images after gallery selection: " + selectedImageUris.size());
                    } else {
                        Log.d(TAG, "Gallery pick cancelled or failed. Result code: " + result.getResultCode());
                        Toast.makeText(this, "Gallery pick cancelled or failed.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (tempCameraImageUri != null) {
                            Log.d(TAG, "Image captured by camera: " + tempCameraImageUri.toString());
                            if (!selectedImageUris.contains(tempCameraImageUri)) {
                                selectedImageUris.add(tempCameraImageUri);
                                adapterImagePicked.notifyItemInserted(selectedImageUris.size() - 1);
                            } else {
                                Log.d(TAG, "Camera image was already in the list.");
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                                try {
                                    getContentResolver().update(tempCameraImageUri, values, null, null);
                                } catch (Exception e) {
                                    Log.e(TAG, "Error unsetting IS_PENDING for camera image: " + tempCameraImageUri, e);
                                }
                            }
                            tempCameraImageUri = null;
                        } else {
                            Log.e(TAG, "Camera capture returned OK, but tempCameraImageUri was null.");
                            Toast.makeText(this, "Failed to retrieve captured image.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "Camera capture cancelled or failed. Result code: " + result.getResultCode());
                        Toast.makeText(this, "Camera capture cancelled.", Toast.LENGTH_SHORT).show();
                        if (tempCameraImageUri != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            try {
                                getContentResolver().delete(tempCameraImageUri, null, null);
                                Log.d(TAG, "Deleted pending camera image entry due to cancellation: " + tempCameraImageUri);
                            } catch (Exception e) {
                                Log.e(TAG, "Error deleting pending camera image entry: " + tempCameraImageUri, e);
                            }
                        }
                        tempCameraImageUri = null;
                    }
                }
        );
    }

    /**
     * Creates a temporary URI for the camera to save the captured image to.
     * For Android Q and above, it attempts to use {@link MediaStore} with `IS_PENDING` flag.
     */
    private void pickImageCamera() {
        ContentValues contentValues = new ContentValues();
        String fileName = "Temp_Image_" + System.currentTimeMillis();
        contentValues.put(MediaStore.Images.Media.TITLE, fileName);
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName + ".jpg");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temporary Image for Post");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri imageCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/UlivApp");
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
            imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        tempCameraImageUri = getContentResolver().insert(imageCollection, contentValues);

        if (tempCameraImageUri != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempCameraImageUri);
            try {
                cameraActivityResultLauncher.launch(intent);
            } catch (Exception e) {
                Log.e(TAG, "Error launching camera: " + e.getMessage());
                Toast.makeText(this, "Could not launch camera.", Toast.LENGTH_SHORT).show();
                if (tempCameraImageUri != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        try {
                            getContentResolver().delete(tempCameraImageUri, null, null);
                            Log.d(TAG, "Deleted pending MediaStore entry as camera launch failed: " + tempCameraImageUri);
                        } catch (Exception deleteEx) {
                            Log.e(TAG, "Error deleting pending MediaStore entry: " + tempCameraImageUri, deleteEx);
                        }
                    }
                    tempCameraImageUri = null;
                }
            }
        } else {
            Toast.makeText(this, "Could not create file for camera image.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Shows a PopupMenu with options to pick an image from the Camera or Gallery.
     * Handles permission requests based on the selected option and Android version.
     */
    private void showImagePickOptions() {
        PopupMenu popupMenu = new PopupMenu(this, binding.pickImagesTv);
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Camera");
        popupMenu.getMenu().add(Menu.NONE, 2, 2, "Gallery");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == 1) { // Camera
                String[] cameraPermissions;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    cameraPermissions = new String[]{Manifest.permission.CAMERA};
                } else {
                    cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                }
                Log.d(TAG, "Requesting camera permissions: " + String.join(", ", cameraPermissions));
                requestCameraPermissionsLauncher.launch(cameraPermissions);
            } else if (itemId == 2) { // Gallery
                String storagePermission;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    storagePermission = Manifest.permission.READ_MEDIA_IMAGES;
                } else {
                    storagePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
                }
                Log.d(TAG, "Requesting storage permission for gallery: " + storagePermission);
                requestStoragePermissionLauncher.launch(storagePermission);
            }
            return true;
        });
    }

    /**
     * Creates and launches an Intent to pick image(s) from the device's gallery.
     * Allows multiple image selection on supported Android versions.
     */
    private void pickImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        try {
            galleryActivityResultLauncher.launch(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error launching gallery picker: " + e.getMessage());
            Toast.makeText(this, "Could not open gallery.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback method from {@link AdapterImagePicked.OnImageRemoveListener}.
     * Called when the user clicks the remove button on a selected image in the RecyclerView.
     * Removes the image URI from the list and updates the adapter.
     * @param position The position of the image to remove.
     * @param imageUri The URI of the image to remove. (Note: This parameter is redundant if using position correctly)
     */
    @Override
    public void onImageRemoved(int position, Uri imageUri) { // imageUri param can be removed if only position is used
        if (selectedImageUris != null && position >= 0 && position < selectedImageUris.size()) {
            Uri removedUri = selectedImageUris.remove(position);
            adapterImagePicked.notifyItemRemoved(position);
            adapterImagePicked.notifyItemRangeChanged(position, selectedImageUris.size() - position);
            Log.d(TAG, "Image removed at position: " + position + ", URI: " + removedUri.toString());
            Toast.makeText(this, "Image removed", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Attempted to remove image at invalid position: " + position + " from list size: " + (selectedImageUris != null ? selectedImageUris.size() : "null"));
        }
    }
}
