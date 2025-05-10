package com.example.uliv.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// FrameLayout might not be needed if MaterialCardView is the root of row_image_picked.xml
// import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.uliv.R;
// Assuming your row_image_picked.xml (the MaterialCardView one) generates this binding class name.
// If the XML file is named row_image_picked.xml, the binding class will be RowImagePickedBinding.
import com.example.uliv.databinding.RowImagesPickedBinding;
// ShapeableImageView and MaterialButton are not directly used here if accessed via binding.

import java.util.ArrayList;
import java.util.List;

public class AdapterImagePicked extends RecyclerView.Adapter<AdapterImagePicked.ImageViewHolder> {

    private Context context;
    private List<Uri> imageUris;
    private OnImageRemoveListener removeListener;

    public interface OnImageRemoveListener {
        void onImageRemoved(int position, Uri imageUri);
    }

    public AdapterImagePicked(Context context, List<Uri> imageUris, OnImageRemoveListener removeListener) {
        this.context = context;
        // Initialize imageUris to prevent NullPointerException if null is passed
        this.imageUris = (imageUris != null) ? imageUris : new ArrayList<>();
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate using ViewBinding, assuming row_image_picked.xml is the file name
        // that generates RowImagePickedBinding
        RowImagesPickedBinding binding = RowImagesPickedBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = imageUris.get(position);
        Glide.with(context)
                .load(imageUri)
                .placeholder(R.drawable.ic_image_placeholder) // Ensure you have this drawable
                .error(R.drawable.ic_broken_image) // Ensure you have this drawable
                .into(holder.binding.imageIv); // CORRECTED: Was pickedImageIv

        holder.binding.closeBtn.setOnClickListener(v -> { // CORRECTED: Was removeImageBtn
            if (removeListener != null) {
                // Use getBindingAdapterPosition() for more safety if items are removed/moved
                // or getAdapterPosition() if using an older RecyclerView library version.
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Make sure to get the URI from the list using the currentPosition,
                    // as the 'position' parameter of onBindViewHolder can become stale
                    // if items are removed rapidly or out of order.
                    removeListener.onImageRemoved(currentPosition, imageUris.get(currentPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUris == null ? 0 : imageUris.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        RowImagesPickedBinding binding; // ViewBinding instance

        ImageViewHolder(RowImagesPickedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Helper method to update the list of URIs
    public void updateImageUris(List<Uri> newImageUris) {
        this.imageUris.clear();
        if (newImageUris != null) {
            this.imageUris.addAll(newImageUris);
        }
        notifyDataSetChanged(); // Consider more specific notify methods for performance
    }

    // Method to add a single image
    public void addImageUri(Uri imageUri) {
        if (imageUri != null && !this.imageUris.contains(imageUri)) {
            this.imageUris.add(imageUri);
            notifyItemInserted(this.imageUris.size() - 1);
        }
    }

    // Method to add multiple images
    public void addImageUris(List<Uri> uris) {
        if (uris != null) {
            int startPosition = this.imageUris.size();
            int newImagesCount = 0;
            for (Uri uri : uris) {
                if (!this.imageUris.contains(uri)) {
                    this.imageUris.add(uri);
                    newImagesCount++;
                }
            }
            if (newImagesCount > 0) {
                notifyItemRangeInserted(startPosition, newImagesCount);
            }
        }
    }
}
