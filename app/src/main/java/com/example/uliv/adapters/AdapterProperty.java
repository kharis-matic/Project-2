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
import com.example.uliv.models.Property;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;

/**
 * AdapterProperty is a RecyclerView adapter for displaying a list of {@link Property} items.
 * It creates and binds views for each property, including its image, title, address,
 * category, date, and price.
 */
public class AdapterProperty extends RecyclerView.Adapter<AdapterProperty.HolderProperty> {

    /**
     * View binding object for the row_property.xml layout.
     * This instance is initialized in {@link #onCreateViewHolder(ViewGroup, int)} and
     * is used by {@link HolderProperty} to access views.
     * Note: This is an instance variable in the adapter. In typical ViewHolder patterns,
     * the binding object is often held directly by the ViewHolder instance itself,
     * or a new binding is inflated and passed to the ViewHolder's constructor.
     */
    private RowPropertyBinding binding;
    /**
     * Tag for logging messages, used for debugging purposes.
     */
    private static final String TAG = "PROPERTY_TAG";
    /**
     * The application context, used for layout inflation and Glide image loading.
     */
    private Context context;
    /**
     * The list of {@link Property} objects that this adapter will display.
     */
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
        // Log statement to confirm adapter initialization and the number of items received.
        Log.d(TAG, "AdapterProperty: Initialized with " + (propertyArrayList != null ? propertyArrayList.size() : 0) + " items");
    }

    /**
     * Called when RecyclerView needs a new {@link HolderProperty} of the given type to represent
     * an item. This new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(HolderProperty, int)}.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new HolderProperty that holds a View for an item.
     */
    @NonNull
    @Override
    public HolderProperty onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single property item using RowPropertyBinding.
        // The binding instance variable of the adapter is updated here.
        binding = RowPropertyBinding.inflate(LayoutInflater.from(context), parent, false);
        Log.d(TAG, "onCreateViewHolder: Created view holder");
        // The root view of the inflated layout is passed to the HolderProperty constructor.
        return new HolderProperty(binding.getRoot());
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * updates the contents of the {@link HolderProperty#itemView} to reflect the item at the
     * given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HolderProperty holder, int position) {
        // Get the Property object for the current position.
        Property modelProperty = propertyArrayList.get(position);

        // Extract data from the Property model.
        String title = modelProperty.getTitle();
        String address = modelProperty.getAddress();
        String category = modelProperty.getType();
//        String date = modelProperty.getDate(); // Assumed to be a pre-formatted date string.
        // Format the price as a whole number string.
//        String price = String.format("%.0f", modelProperty.getPrice());
        // Get the image URL from the Property model.
        String imageUrl = modelProperty.getPrimaryImageUrl(); // Assumes Property model has getImageUrl()

        // Log the binding process for the current item.
        Log.d(TAG, "onBindViewHolder: Binding position " + position + ": title=" + title);

        // Set the data to the TextViews in the ViewHolder.
        // Provides "N/A" if data is null to avoid NullPointerExceptions and show placeholder text.
        holder.titleTv.setText(title != null ? title : "N/A");
        holder.locationTv.setText(address != null ? address : "N/A");
        holder.categoryTv.setText(category != null ? category : "N/A");
        holder.dateTv.setText(date != null ? date : "N/A");
        holder.priceTv.setText(price != null ? price : "N/A"); // Price is formatted above.
        holder.priceSymbolTv.setText("₱"); // Sets the currency symbol (Philippine Peso).

        // Load the property image into the ImageView.
        loadPropertyImage(imageUrl, holder);

        // TODO: Add item click listener if needed.
        // holder.itemView.setOnClickListener(v -> {
        //     // Handle click event for the item at 'position'.
        // });
    }

    /**
     * Loads an image from the given URL into the ImageView of the {@link HolderProperty}.
     * Uses Glide library for image loading, placeholder, and error handling.
     *
     * @param imageUrl The URL of the image to load.
     * @param holder The ViewHolder containing the ImageView to load the image into.
     */
    private void loadPropertyImage(String imageUrl, HolderProperty holder) {
        Log.d(TAG, "loadPropertyImage: Loading image URL: " + imageUrl);
        // Check if the image URL is valid (not null and not empty).
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl) // Source of the image.
                    .placeholder(R.drawable.building_asset01) // Drawable to show while loading.
                    .error(R.drawable.building_asset01)       // Drawable to show if loading fails.
                    .into(holder.propertyIv); // Target ImageView.
        } else {
            // If the URL is invalid, log a warning and set a default placeholder image.
            Log.w(TAG, "loadPropertyImage: Image URL is null or empty, using placeholder");
            holder.propertyIv.setImageResource(R.drawable.building_asset01);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        // Return the size of the propertyArrayList, or 0 if it's null.
        int count = (propertyArrayList != null) ? propertyArrayList.size() : 0;
        Log.d(TAG, "getItemCount: Returning " + count + " items");
        return count;
    }

    /**
     * ViewHolder class for {@code AdapterProperty}.
     * Holds and initializes the views for a single property item in the RecyclerView.
     * Views are accessed through the adapter's 'binding' instance variable, which is set
     * in {@link AdapterProperty#onCreateViewHolder(ViewGroup, int)}.
     */
    class HolderProperty extends RecyclerView.ViewHolder {
        // UI elements for a single property item.
        ShapeableImageView propertyIv;
        TextView titleTv, locationTv, categoryTv, dateTv, priceTv, priceSymbolTv;

        /**
         * Constructs a {@code HolderProperty}.
         *
         * @param itemView The View for a single item, inflated from row_property.xml.
         * This is the root view of the {@code RowPropertyBinding}.
         */
        public HolderProperty(@NonNull View itemView) {
            super(itemView);
            // Initialize views using the adapter's 'binding' object.
            // This assumes 'binding' has been correctly inflated for the current item
            // in onCreateViewHolder before this constructor is called.
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
