package com.example.administrator.forimm5.Email;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.example.administrator.forimm5.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017-07-16.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {

    ArrayList<Uri> imageUri = new ArrayList<>();
    Context context;
    EmailFragment fragment;

    public ImageAdapter(EmailFragment fragment) {
        this.fragment = fragment;
    }

    public void setData(ArrayList<Uri> images){
        this.imageUri = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email_image, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Uri uri = imageUri.get(position);
        Glide.with(context).load(uri).bitmapTransform(new RoundedCornersTransformation(context, 4, 4)).into(holder.image);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return imageUri.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView image, cancel;
        int position;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.attachImage);
            cancel = (ImageView) itemView.findViewById(R.id.attachCancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageUri.remove(position);
                    notifyDataSetChanged();
                    if(imageUri.size() == 0){
                        fragment.setImageRecyclerVisibility();
                    }
                }
            });
        }

    }
}
