package com.example.theodhor.stripeandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stripe.model.Plan;
import java.util.ArrayList;

/**
 * Created by Dori on 10/30/2016.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>{
    private ArrayList<MainActivity.SimplePlan> planArrayList;
    Activity activity;

    public ItemsAdapter(Activity activity,ArrayList<MainActivity.SimplePlan> planArrayList){
        this.planArrayList = planArrayList;
        this.activity = activity;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ItemsViewHolder viewHolder = new ItemsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, final int i) {
        holder.planName.setText(planArrayList.get(i).getName());
        holder.planPrice.setText("$"+(planArrayList.get(i).getDescription()));
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyIntent = new Intent(activity,PayActivity.class);
                buyIntent.putExtra("plan_price",planArrayList.get(i).getAmount());
                buyIntent.putExtra("plan_name",""+planArrayList.get(i).getName());
                activity.startActivity(buyIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planArrayList.size();
    }


    class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView planName,planPrice;
        Button buy;

        ItemsViewHolder(View itemView) {
            super(itemView);
            planName = (TextView) itemView.findViewById(R.id.item_name);
            planPrice = (TextView) itemView.findViewById(R.id.item_price);
            buy = (Button) itemView.findViewById(R.id.buyButton);
        }
    }

}
