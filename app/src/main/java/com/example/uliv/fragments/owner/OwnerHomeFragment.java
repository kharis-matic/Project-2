package com.example.uliv.fragments.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uliv.R;

// Here Resides the Home contents of the Property Owner
// SHOWS: List of properties that the owner owns
public class OwnerHomeFragment extends Fragment {

    public OwnerHomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}