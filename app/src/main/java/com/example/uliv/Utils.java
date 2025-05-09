package com.example.uliv;

//Very kulang, naa steps na miss huhu

// A utility class to hold constants and static data
public class Utils {

    // TAG for logging
    public static final String TAG_UTILS = "ULIV_UTILS_TAG";

    // Property Categories - MODIFIED to match your XML TabLayout
    public static final String[] propertyCategories = {
            "Boarding House",
            "Dorm",
            "Pad",
            "Apartment"
    };
}
    // NOTE: The following arrays for subcategories (propertyTypesHomes, propertyTypesPlots,
    // propertyTypesCommercial) and propertyAreaSizeUnits were in the original Java code
    // based on the video. Since your provided XML does not currently have
    // AutoCompleteTextViews for 'Subcategory' or 'Area Size Unit', these arrays
    // might not be directly used by PostAddActivity's current UI.
    // You can keep them if you plan to add these fields later, or remove them if not needed.


// TODO: Add property detail dropdown

//    public static final String[] propertyTypesBoardingHouse = { // Example subcategories
//            "Male Only", "Female Only", "Co-ed", "With Meals", "Without Meals"
//    };
//    public static final String[] propertyTypesDorm = { // Example subcategories
//            "Bunk Beds", "Private Rooms", "Shared Rooms"
//    };
//    public static final String[] propertyTypesPad = { // Example subcategories
//            "Studio", "1 Bedroom", "Unfurnished", "Furnished"
//    };
//    public static final String[] propertyTypesApartment = { // Example subcategories
//            "1 BHK", "2 BHK", "Studio Apartment", "Furnished", "Semi-Furnished"
//    };