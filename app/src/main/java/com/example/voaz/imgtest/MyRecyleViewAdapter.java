package com.example.voaz.imgtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amir on 26.07.16.
 */
public class MyRecyleViewAdapter extends RecyclerView.Adapter<MyRecyleViewAdapter.ViewHolder> {
    public ArrayList<String> imagesURL;
    private Activity activity;
    public int screenWidth;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ivItemGridImage);
        }
    }
    public MyRecyleViewAdapter(Activity activity, ArrayList<String> items) {
        this.activity = activity;
        imagesURL = items;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_and_text_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.mImageView.setImageURI(Uri.parse(imagesURL.get(position)));
        final ViewHolder myHolder = (ViewHolder) holder;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagesURL.get(position), opts);
        opts.inJustDecodeBounds = false;

        Picasso.with(activity)
                .load(imagesURL.get(position))
                .resize(screenWidth / 2, 300)
                .centerCrop()
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return imagesURL.size();
    }

}
