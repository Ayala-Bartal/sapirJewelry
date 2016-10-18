package com.example.ayala.sapirjewelry;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ayala on 10/18/2016.
 */

public class CountryAdapter extends RecyclerView.Adapter {

    private List<Country> countries;

    private final int TYPE_REGUALAR = 1;
    private final int TYPE_SPECIAL = 2;

    private MyClickListener myClickListener;

    public interface MyClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public CountryAdapter(List<Country> countries) {
        this.countries = countries;
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTextView;
        TextView popTextView;

        public CountryViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.country_flag);
            nameTextView = (TextView)itemView.findViewById(R.id.country_name);
            popTextView = (TextView)itemView.findViewById(R.id.country_population);
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
        Country country = countries.get(position);
            CountryViewHolder holder1 = (CountryViewHolder)holder;
            holder1.imageView.setImageResource(countries.get(position).getFlagResId());
            holder1.nameTextView.setText(countries.get(position).getName() + "");
            holder1.popTextView.setText(countries.get(position).getPopulation() + "");


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        CountryViewHolder countryViewHolder = new CountryViewHolder(v);
        return countryViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        Country country = countries.get(position);
        if(country.isSpecial()) return TYPE_SPECIAL;
        return TYPE_REGUALAR;
        //return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
