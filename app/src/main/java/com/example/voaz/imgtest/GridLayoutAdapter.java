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
 * Created by amir on 25.07.16.
 */

public class GridLayoutAdapter extends MyAdapter {
    private Activity activity;
    private ArrayList<String> images;
    private int screenWidth;
    public GridLayoutAdapter(Activity activity, ArrayList<String> images) {
        this.activity = activity;
        this.images = images;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.image_and_text_layout, parent, false);
        Holder dataObjectHolder = new Holder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Holder myHolder = (Holder) holder;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(images.get(position), opts);
        opts.inJustDecodeBounds = false;
        Picasso.with(activity)
                .load(images.get(position))
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.ic_launcher)
                .resize(screenWidth / 2, 300)
                .centerCrop()
                .into((myHolder.images));
    }
    @Override
    public int getItemCount() {
        return images.size();
    }
    public class Holder extends MyViewHolder {
        private ImageView images;
        public Holder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.ivItemGridImage);
        }
    }
}