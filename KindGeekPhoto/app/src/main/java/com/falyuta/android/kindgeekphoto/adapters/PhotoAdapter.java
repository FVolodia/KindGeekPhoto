package com.falyuta.android.kindgeekphoto.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.interfaces.PhotoClickListener;
import com.falyuta.android.kindgeekphoto.models.Photo;

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
//        final String orderId = String.valueOf(currentShipment.getId());
//        final String dateTime = (new StringBuffer().append(currentShipment.getPickup_date_text()).append(", ")
//                .append(currentShipment.getPickup_time_text()).toString());
//        final String addressFrom = currentShipment.getStart_location().getAddress();
//        final String addressTo = currentShipment.getEnd_location().getAddress();
//
//        String itemDescription = "";
//        if (!currentShipment.getRequested_delivery_items_desc().isEmpty()){
//            itemDescription = currentShipment.getRequested_delivery_items_desc().get(0);
//        }
//
//        final String what = currentShipment.getQuantity() + " "
//                + itemDescription;
//        final String payment = ShipmentUtils.getPayment(currentShipment);

//        holder.mShipmentView.setOrderId(orderId);
//        holder.mShipmentView.setDateTime(dateTime, false);
//        holder.mShipmentView.setAddressFrom(addressFrom, false);
//        holder.mShipmentView.setAddressTo(addressTo, false);
//        holder.mShipmentView.setWhat(what);
//        holder.mShipmentView.setPayment(payment);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_container_photo)
        CardView container;




        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
