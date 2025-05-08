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

import com.example.uliv.R;
import com.example.uliv.adapters.AdapterProperty;
import com.example.uliv.databinding.FragmentHomeBinding;
import com.example.uliv.databinding.FragmentRenterHomeBinding;
import com.example.uliv.models.ModelProperty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// Here Resides the Home contents of Renter
// SHOWS: Property list and Search bar
public class RenterHomeFragment extends Fragment {

    private FragmentRenterHomeBinding binding;

    private static final String TAG = "RENTER_HOME_TAG";

    private Context mContext;

    private ArrayList<ModelProperty> propertyArrayList;

    private AdapterProperty adapterProperty;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mContext = context;
    }

    public RenterHomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRenterHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        binding.propertiesRv.setLayoutManager(new LinearLayoutManager(mContext));
        loadProperties();
    }

    public void loadProperties(){
        Log.d(TAG, "loadProperties: ");

        propertyArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Properties");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                propertyArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){

                    ModelProperty modelProperty = ds.getValue(ModelProperty.class);

                    propertyArrayList.add(modelProperty);
                }
                adapterProperty = new AdapterProperty(mContext, propertyArrayList);
                binding.propertiesRv.setAdapter(adapterProperty);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}