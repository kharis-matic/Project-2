package com.example.uliv.fragments.renter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uliv.adapters.AdapterProperty;
import com.example.uliv.databinding.FragmentRenterHomeBinding;
import com.example.uliv.models.Property;
import java.util.ArrayList;

/**
 * RenterHomeFragment is a {@link Fragment} subclass that displays a list of properties
 * to a renter. It uses a RecyclerView to show property items, populated with dummy data
 * in this version.
 */
public class RenterHomeFragment extends Fragment {

    /**
     * View binding object for the fragment_renter_home.xml layout.
     * This allows for easy access to views within the fragment's layout.
     */
    private FragmentRenterHomeBinding binding;
    /**
     * Tag for logging messages, used for debugging purposes.
     */
    private static final String TAG = "RENTER_HOME_TAG";
    /**
     * The context of the fragment. Initialized in {@link #onAttach(Context)}.
     */
    private Context mContext;
    /**
     * ArrayList to hold the {@link Property} objects that will be displayed in the RecyclerView.
     */
    private ArrayList<Property> propertyArrayList;
    /**
     * Adapter for the RecyclerView, responsible for binding {@link Property} data to views.
     * See {@link AdapterProperty}.
     */
    private AdapterProperty adapterProperty;

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context The context to which the fragment is attached.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Store the context for later use, e.g., for initializing adapters or layout managers.
        mContext = context;
        Log.d(TAG, "onAttach: Fragment attached to context");
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onViewCreated(View, Bundle)}.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to. The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding.
        binding = FragmentRenterHomeBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: View created using FragmentRenterHomeBinding");
        // Return the root view of the inflated layout.
        return binding.getRoot();
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the RecyclerView with a LinearLayoutManager.
        binding.propertiesRv.setLayoutManager(new LinearLayoutManager(mContext));
        // Initialize the ArrayList to hold property data.
        propertyArrayList = new ArrayList<>();
        Log.d(TAG, "onViewCreated: RecyclerView layout manager set and property list initialized");

        // --- Dummy Data Population ---
        // This section adds sample Property objects to the propertyArrayList for demonstration purposes.
        // In a real application, this data would typically be fetched from a database or API.
        propertyArrayList.add(new Property(
                "1", // id
                "Sunny Stay Dormitel", // title
                "Sayre Highway, Lumbo, Valencia City", // address
                "Boarding house", // category
                "01/01/2025", // date (assuming this is a formatted string)
                1500.0, // price
                "https://via.placeholder.com/150" // imageUrl
        ));
        propertyArrayList.add(new Property(
                "2", "Cozy Pad", "Main St, Cubao, Quezon City",
                "Apartment", "02/01/2025", 2000.0, "https://via.placeholder.com/150"
        ));
        propertyArrayList.add(new Property(
                "3", "Bautista Apartment", "Musuan, Maramag",
                "Apartment", "02/01/2025", 3000.0, "https://via.placeholder.com/150"
        ));
        propertyArrayList.add(new Property(
                "4", "New Rose Dormitory", "CMU, Musuan, Maramag",
                "Dorm", "02/01/2025", 1500.0, "https://via.placeholder.com/150"
        ));
        Log.d(TAG, "onViewCreated: Added " + propertyArrayList.size() + " dummy Property items to the list");

        // Initialize the AdapterProperty with the context and the list of properties.
        adapterProperty = new AdapterProperty(mContext, propertyArrayList);
        // Set the adapter for the RecyclerView.
        binding.propertiesRv.setAdapter(adapterProperty);
        Log.d(TAG, "onViewCreated: AdapterProperty set to RecyclerView with " + propertyArrayList.size() + " items");

        // TODO: Implement fetching of real property data from Firebase or other backend.
        // loadProperties();
    }

    // TODO: Example method for loading properties (to be implemented)
    /*
    private void loadProperties() {
        Log.d(TAG, "loadProperties: Fetching properties...");
        // Clear existing data before loading new data (optional, depends on use case)
        // propertyArrayList.clear();

        // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Properties");
        // ref.addValueEventListener(new ValueEventListener() {
        //     @Override
        //     public void onDataChange(@NonNull DataSnapshot snapshot) {
        //         propertyArrayList.clear(); // Clear list before adding new data to avoid duplicates
        //         for (DataSnapshot ds : snapshot.getChildren()) {
        //             try {
        //                 Property property = ds.getValue(Property.class);
        //                 if (property != null) {
        //                     propertyArrayList.add(property);
        //                 }
        //             } catch (Exception e) {
        //                 Log.e(TAG, "onDataChange: Error parsing property", e);
        //             }
        //         }
        //         Log.d(TAG, "onDataChange: Properties loaded: " + propertyArrayList.size());
        //         if (adapterProperty != null) {
        //             adapterProperty.notifyDataSetChanged();
        //         } else {
        //             adapterProperty = new AdapterProperty(mContext, propertyArrayList);
        //             binding.propertiesRv.setAdapter(adapterProperty);
        //         }
        //         // Hide progress bar if any
        //         // binding.progressBar.setVisibility(View.GONE);
        //     }
        //
        //     @Override
        //     public void onCancelled(@NonNull DatabaseError error) {
        //         Log.e(TAG, "onCancelled: Failed to load properties", error.toException());
        //         Toast.makeText(mContext, "Failed to load properties: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        //         // Hide progress bar if any
        //         // binding.progressBar.setVisibility(View.GONE);
        //     }
        // });
    }
    */

    /**
     * Called when the fragment is no longer attached to its activity. This is called after
     * {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        // Nullify the context to avoid potential memory leaks.
        mContext = null;
        Log.d(TAG, "onDetach: Fragment detached from context");
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment. The next time the fragment needs
     * to be displayed, a new view will be created.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding object when the view is destroyed to prevent memory leaks.
        binding = null;
        Log.d(TAG, "onDestroyView: View binding nulled");
    }
}
