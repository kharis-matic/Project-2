package com.example.uliv;

import android.util.Log; // It's good practice to import Log if you use it, though TAG_UTILS isn't used yet.

/**
 * A utility class to hold constants, static data, and helper methods for the Uliv application.
 */
public class Utils {

    // TAG for logging from this utility class (if needed in the future)
    public static final String TAG_UTILS = "ULIV_UTILS_TAG";

    /**
     * Defines the main categories for properties.
     * This array is used by PostAddActivity to populate the TabLayout for property type selection.
     */
    public static final String[] propertyCategories = {
            "Boarding House",
            "Dorm",
            "Pad",
            "Apartment"
    };

    // NOTE: The following arrays for subcategories (e.g., propertyTypesBoardingHouse)
    // and other details like area size units were potentially part of a broader plan.
    // Currently, PostAddActivity's UI (as per the provided XML and Java)
    // does not implement dropdowns or input fields for these specific subcategories
    // or area units.
    // They are kept here (commented out) for potential future use if you plan to
    // add more detailed filtering or input fields. If they are not planned,
    // they can be removed to keep the Utils class cleaner.

    // TODO: Implement UI elements (e.g., AutoCompleteTextViews or Spinners) in PostAddActivity
    //       and corresponding logic if these subcategories or other details are needed.

    /*
    // Example Subcategories for different Property Types:
    public static final String[] propertyTypesBoardingHouse = {
            "Male Only", "Female Only", "Co-ed", "With Meals", "Without Meals"
    };

    public static final String[] propertyTypesDorm = {
            "Bunk Beds", "Private Rooms", "Shared Rooms"
    };

    public static final String[] propertyTypesPad = {
            "Studio", "1 Bedroom", "Unfurnished", "Furnished"
    };

    public static final String[] propertyTypesApartment = {
            "1 BHK", "2 BHK", "Studio Apartment", "Furnished", "Semi-Furnished"
    };

    // Example for Area Size Units (if you add an area input field):
    public static final String[] propertyAreaSizeUnits = {
            "sq ft", "sq m", "acres", "hectares"
    };
    */

    // You can add other static utility methods here as your project grows.
    // For example, date formatting, validation helpers, etc.

}
