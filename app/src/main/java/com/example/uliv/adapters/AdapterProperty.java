package com.example.uliv.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.uliv.R;
import com.example.uliv.databinding.RowPropertyBinding; // Assuming this is the binding class for your property item
import com.example.uliv.models.Property;

import java.util.ArrayList;

/**
 * AdapterProperty is a RecyclerView adapter for displaying a list of {@link Property} items.
 * It creates and binds views for each property, including its image, title, address,
 * category, date, and price.
 */
public class AdapterProperty extends RecyclerView.Adapter<AdapterProperty.HolderProperty> {

    // Removed the adapter-level 'binding' instance variable.
    // private RowPropertyBinding binding; // THIS WAS THE ISSUE

    private static final String TAG = "PROPERTY_ADAPTER_TAG"; // Changed tag
    private Context context;
    private ArrayList<Property> propertyArrayList;

    /**
     * Constructs an {@code AdapterProperty}.
     *
     * @param context The current context.
     * @param propertyArrayList The list of {@link Property} items to display.
     */
    public AdapterProperty(Context context, ArrayList<Property> propertyArrayList) {
        this.context = context;
        this.propertyArrayList = propertyArrayList;
        Log.d(TAG, "AdapterProperty: Initialized with " + (propertyArrayList != null ? propertyArrayList.size() : 0) + " items");
    }

    @NonNull
    @Override
    public HolderProperty onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single property item using RowPropertyBinding.
        // This binding is specific to this ViewHolder instance.
        RowPropertyBinding itemBinding = RowPropertyBinding.inflate(LayoutInflater.from(context), parent, false);
        Log.d(TAG, "onCreateViewHolder: Created view holder");
        return new HolderProperty(itemBinding); // Pass the specific binding to the ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProperty holder, int position) {
        Property modelProperty = propertyArrayList.get(position);

        String title = modelProperty.getTitle();
        // Assuming Property model has methods like getFullAddress(), getFormattedDate(), getFormattedPrice()
        // Or construct them here if needed.
        String address = modelProperty.getAddress(); // Using the existing getAddress()
        String category = modelProperty.getType(); // As per your previous AdapterProperty
        String date = modelProperty.getDate(); // Assuming getDate() exists and returns a suitable string
        String price = String.format("₱%.0f", modelProperty.getPrice()); // Added currency symbol here

        String imageUrl = modelProperty.getPrimaryImageUrl();

        Log.d(TAG, "onBindViewHolder: Binding position " + position + ": title=" + title);

        holder.binding.titleTv.setText(title != null ? title : "N/A");
        holder.binding.locationTv.setText(address != null ? address : "N/A");
        holder.binding.categoryTv.setText(category != null ? category : "N/A");
        holder.binding.dateTv.setText(date != null ? date : "N/A");
        holder.binding.priceTv.setText(price); // Price now includes currency symbol
        // holder.binding.priceSymbolTv.setText("₱"); // No longer needed if price string includes it

        loadPropertyImage(imageUrl, holder);

        // holder.itemView.setOnClickListener(v -> {
        //     Log.d(TAG, "Clicked on property: " + title);
        // });
    }

    private void loadPropertyImage(String imageUrl, HolderProperty holder) {
        Log.d(TAG, "loadPropertyImage: Loading image URL: " + imageUrl);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.building_asset01)
                    .error(R.drawable.building_asset01)
                    .into(holder.binding.propertyIv); // Access propertyIv via holder's binding
        } else {
            Log.w(TAG, "loadPropertyImage: Image URL is null or empty, using placeholder");
            holder.binding.propertyIv.setImageResource(R.drawable.building_asset01);
        }
    }

    @Override
    public int getItemCount() {
        int count = (propertyArrayList != null) ? propertyArrayList.size() : 0;
        Log.d(TAG, "getItemCount: Returning " + count + " items");
        return count;
    }

    /**
     * ViewHolder class for {@code AdapterProperty}.
     * Holds and initializes the views for a single property item in the RecyclerView.
     * It now correctly holds its own instance of RowPropertyBinding.
     */
    static class HolderProperty extends RecyclerView.ViewHolder {
        // This binding is specific to this ViewHolder instance.
        private RowPropertyBinding binding;

        // Views are accessed via the binding object, no need to declare them separately here
        // ShapeableImageView propertyIv;
        // TextView titleTv, locationTv, categoryTv, dateTv, priceTv, priceSymbolTv;

        /**
         * Constructs a {@code HolderProperty}.
         * @param itemBinding The RowPropertyBinding object for this item's layout.
         */
        public HolderProperty(@NonNull RowPropertyBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding; // Store the passed binding object
            // Views are now accessed via this.binding.viewId
            Log.d(TAG, "HolderProperty: Initialized views using item-specific binding");
        }
    }
}
