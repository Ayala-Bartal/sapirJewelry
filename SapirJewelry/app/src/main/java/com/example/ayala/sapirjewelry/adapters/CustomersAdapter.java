package com.example.ayala.sapirjewelry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.entities.Customers;

import java.util.List;

/**
 * Created by ayala on 10/20/2016.
 */

public class CustomersAdapter extends RecyclerView.Adapter {

    private List<Customers> m_customersList;

    private final int TYPE_REGUALAR = 1;
    private final int TYPE_SPECIAL = 2;

    private CountryAdapter.MyClickListener myClickListener;

    public interface MyClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public CustomersAdapter(List<Customers> customersList) {
        this.m_customersList = customersList;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView popTextView;

        public CustomerViewHolder(final View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.customer_first_name_card);
            popTextView = (TextView)itemView.findViewById(R.id.customer_last_name_card);
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
        Customers customers = m_customersList.get(position);
        CustomersAdapter.CustomerViewHolder holder1 = (CustomersAdapter.CustomerViewHolder)holder;
        holder1.nameTextView.setText(m_customersList.get(position).getFirstName() + "");
        holder1.popTextView.setText(m_customersList.get(position).getLastName() + "");


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_card_layout,parent,false);
        CustomersAdapter.CustomerViewHolder countryViewHolder = new CustomersAdapter.CustomerViewHolder(v);
        return countryViewHolder;
    }

   /* @Override
    public int getItemViewType(int position) {
        Customers customers = m_customersList.get(position);
        if(customers.isSpecial()) return TYPE_SPECIAL;
        return TYPE_REGUALAR;
        //return super.getItemViewType(position);
    }*/

    @Override
    public int getItemCount() {
        return m_customersList.size();
    }
}
