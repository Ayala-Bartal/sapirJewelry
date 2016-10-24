package com.example.ayala.sapirjewelry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.entities.Shop;

import java.util.List;

/**
 * Created by ayala on 10/24/2016.
 */

public class ShopAdapter extends RecyclerView.Adapter{
    private List<Shop> m_shopList;

    private final int TYPE_REGUALAR = 1;
    private final int TYPE_SPECIAL = 2;

    private MyClickListener myClickListener;

    public interface MyClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public ShopAdapter(List<Shop> shopList) {
        this.m_shopList = shopList;
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView picView;

        public ShopViewHolder(final View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.shop_name);
            picView = (ImageView) itemView.findViewById(R.id.shop_pic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myClickListener.onItemClick(getAdapterPosition(),itemView);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    myClickListener.onItemLongClick(getAdapterPosition(),itemView);
                    return false;
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shop shop = m_shopList.get(position);
        ShopAdapter.ShopViewHolder holder1 = (ShopAdapter.ShopViewHolder)holder;
        holder1.nameTextView.setText(m_shopList.get(position).getName() + "");
        holder1.picView.setImageBitmap(m_shopList.get(position).getPicView());


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card_layout,parent,false);
        ShopAdapter.ShopViewHolder countryViewHolder = new ShopAdapter.ShopViewHolder(v);
        return countryViewHolder;
    }


    @Override
    public int getItemCount() {
        return m_shopList.size();
    }
}
