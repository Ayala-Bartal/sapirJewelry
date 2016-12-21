package com.example.ayala.sapirjewelry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.entities.Jewelry;
import com.example.ayala.sapirjewelry.entities.Shop;

import java.util.List;

/**
 * Created by ayala on 12/4/2016.
 */

public class JewelryAdapter extends RecyclerView.Adapter {
    private List<Jewelry> m_jewelryList;
    public JewelryAdapter(List<Jewelry> jewelryListList) {
        this.m_jewelryList = jewelryListList;
    }

    public class JewelryViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView picView;
        TextView priceTextView;
        View itemView;

        public JewelryViewHolder(final View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.jewelry_name);
            picView = (ImageView) itemView.findViewById(R.id.shop_pic);
            priceTextView = (TextView)itemView.findViewById(R.id.jewelry_price);
            this.itemView = itemView;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Jewelry jewelry = m_jewelryList.get(position);
        JewelryAdapter.JewelryViewHolder holder1 = (JewelryAdapter.JewelryViewHolder)holder;
        holder1.nameTextView.setText(jewelry.getName());
        holder1.picView.setImageBitmap(jewelry.getPicView());
        holder1.priceTextView.setText(jewelry.getPrice());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card_layout,parent,false);
        JewelryAdapter.JewelryViewHolder jewelryViewHolder = new JewelryAdapter.JewelryViewHolder(v);
        return jewelryViewHolder;
    }


    @Override
    public int getItemCount() {
        return m_jewelryList.size();
    }
}
