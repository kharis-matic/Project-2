package com.example.uliv.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.uliv.R;
import com.example.uliv.databinding.RowPropertyBinding;
import com.example.uliv.models.ModelProperty;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;

public class AdapterProperty extends RecyclerView.Adapter<AdapterProperty.HolderProperty> {

    private RowPropertyBinding binding;
    private static final String TAG = "PROPERTY_TAG";
    private Context context;
    private ArrayList<ModelProperty> propertyArrayList;

    public AdapterProperty(Context context, ArrayList<ModelProperty> propertyArrayList) {
        this.context = context;
        this.propertyArrayList = propertyArrayList;
        Log.d(TAG, "AdapterProperty: Initialized with " + propertyArrayList.size() + " items");
    }

    @NonNull
    @Override
    public HolderProperty onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPropertyBinding.inflate(LayoutInflater.from(context), parent, false);
        Log.d(TAG, "onCreateViewHolder: Created view holder");
        return new HolderProperty(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProperty holder, int position) {
        ModelProperty modelProperty = propertyArrayList.get(position);
        String title = modelProperty.getTitle();
        String address = modelProperty.getAddress();
        String category = modelProperty.getCategory();
        String date = modelProperty.getDate();
        String price = String.format("%.0f", modelProperty.getPrice());
        String imageUrl = modelProperty.getImageUrl();

        Log.d(TAG, "onBindViewHolder: Binding position " + position + ": title=" + title);
        holder.titleTv.setText(title != null ? title : "N/A");
        holder.locationTv.setText(address != null ? address : "N/A");
        holder.categoryTv.setText(category != null ? category : "N/A");
        holder.dateTv.setText(date != null ? date : "N/A");
        holder.priceTv.setText(price != null ? price : "N/A");
        holder.priceSymbolTv.setText("₱"); // Hardcoded for now

        loadPropertyImage(imageUrl, holder);
    }

    private void loadPropertyImage(String imageUrl, HolderProperty holder) {
        Log.d(TAG, "loadPropertyImage: Loading image URL: " + imageUrl);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.building_asset01)
                    .error(R.drawable.building_asset01)
                    .into(holder.propertyIv);
        } else {
            Log.w(TAG, "loadPropertyImage: Image URL is null or empty, using placeholder");
            holder.propertyIv.setImageResource(R.drawable.building_asset01);
        }
    }

    @Override
    public int getItemCount() {
        int count = propertyArrayList.size();
        Log.d(TAG, "getItemCount: Returning " + count + " items");
        return count;
    }

    class HolderProperty extends RecyclerView.ViewHolder {
        ShapeableImageView propertyIv;
        TextView titleTv, locationTv, categoryTv, dateTv, priceTv, priceSymbolTv;

        public HolderProperty(@NonNull View itemView) {
            super(itemView);
            propertyIv = binding.propertyIv;
            titleTv = binding.titleTv;
            locationTv = binding.locationTv;
            categoryTv = binding.categoryTv;
            dateTv = binding.dateTv;
            priceTv = binding.priceTv;
            priceSymbolTv = binding.priceSymbolTv;
            Log.d(TAG, "HolderProperty: Initialized views");
        }
    }
}