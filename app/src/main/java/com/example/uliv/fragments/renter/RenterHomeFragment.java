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

public class RenterHomeFragment extends Fragment {

    private FragmentRenterHomeBinding binding;
    private static final String TAG = "RENTER_HOME_TAG";
    private Context mContext;
    private ArrayList<Property> propertyArrayList;
    private AdapterProperty adapterProperty;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        Log.d(TAG, "onAttach: Fragment attached to context");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRenterHomeBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: View created");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.propertiesRv.setLayoutManager(new LinearLayoutManager(mContext));
        propertyArrayList = new ArrayList<>();
        Log.d(TAG, "onViewCreated: Property list initialized");

        // Dummy data
        propertyArrayList.add(new Property(
                "1", "Sunny Stay Dormitel", "Sayre Highway, Lumbo, Valencia City",
                "Boarding house", "01/01/2025", 1500.0, "https://via.placeholder.com/150"
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
        Log.d(TAG, "onViewCreated: Added " + propertyArrayList.size() + " dummy items");

        adapterProperty = new AdapterProperty(mContext, propertyArrayList);
        binding.propertiesRv.setAdapter(adapterProperty);
        Log.d(TAG, "onViewCreated: Adapter set with " + propertyArrayList.size() + " items");
    }
}