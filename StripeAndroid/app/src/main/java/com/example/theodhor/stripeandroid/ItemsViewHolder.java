package com.example.theodhor.stripeandroid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Dori on 10/30/2016.
 */

public class ItemsViewHolder extends RecyclerView.ViewHolder{

    public TextView planName,planPrice;
    Button buy;

    public ItemsViewHolder(View itemView) {
        super(itemView);
        planName = (TextView) itemView.findViewById(R.id.item_name);
        planPrice = (TextView) itemView.findViewById(R.id.item_price);
        buy = (Button) itemView.findViewById(R.id.buyButton);
    }
}
