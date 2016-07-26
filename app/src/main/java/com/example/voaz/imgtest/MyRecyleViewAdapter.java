package com.example.voaz.imgtest;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by amir on 26.07.16.
 */
public class MyRecyleViewAdapter extends RecyclerView.Adapter<MyRecyleViewAdapter.ViewHolder> {
    ArrayList<String> imagesURL;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ViewHolder(ImageView itemView) {
            super(itemView);
            mImageView = itemView;
        }
    }
    public MyRecyleViewAdapter(ArrayList<String> items) {
        imagesURL = items;
    }

    @Override
    public MyRecyleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_and_text_layout, parent, false);
        ViewHolder vh = new ViewHolder((ImageView) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageURI(Uri.parse(imagesURL.get(position)));

    }

    @Override
    public int getItemCount() {
        return imagesURL.size();
    }

}
