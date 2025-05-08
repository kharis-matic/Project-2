package com.example.uliv.fragments.renter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uliv.R;

// SHOWS: What renters enquired; Also shows booking status (Pending, Accepter, Canceled)
public class BookingsFragment extends Fragment {

    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renter_bookings, container, false);
    }
}