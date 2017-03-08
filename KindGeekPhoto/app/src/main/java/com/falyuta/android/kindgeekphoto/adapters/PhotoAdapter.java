package com.falyuta.android.kindgeekphoto.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.interfaces.PhotoClickListener;
import com.falyuta.android.kindgeekphoto.models.Photo;

import java.io.File;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by volodymyr on 3/7/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> mList = Collections.emptyList();
    private PhotoClickListener mListener;

    public void setListPhotos(List<Photo> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public PhotoAdapter() {
    }

    public PhotoAdapter withPhotoClickListener(PhotoClickListener clickListener) {
        mListener = clickListener;
        return this;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        final Photo currentphoto = mList.get(position);
        final String urlPath = currentphoto.getPhotoPath();
        final String photoName = currentphoto.getPhotoName();
        final String photoDate = currentphoto.getPhotoDate();

        Glide
                .with(holder.mPhotoIv.getContext())
                .load(new File(urlPath))
                .centerCrop()
                .crossFade()
                .into(holder.mPhotoIv);

        holder.mPhotoNameTv.setText(photoName);
        holder.mPhotoDateTv.setText(photoDate);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onPhotoClick(view, position, currentphoto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_container_photo)
        CardView container;
        @BindView(R.id.photo_iv)
        ImageView mPhotoIv;
        @BindView(R.id.delete_iv)
        ImageView mDeleteIv;
        @BindView(R.id.title_name_photo_tv)
        TextView mPhotoNameTv;
        @BindView(R.id.title_date_photo_tv)
        TextView mPhotoDateTv;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
