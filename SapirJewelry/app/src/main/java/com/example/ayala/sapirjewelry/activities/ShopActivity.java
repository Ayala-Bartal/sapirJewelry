package com.example.ayala.sapirjewelry.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ayala.sapirjewelry.entities.Country;
import com.example.ayala.sapirjewelry.adapters.CountryAdapter;
import com.example.ayala.sapirjewelry.R;

import java.util.ArrayList;

/**
 * Created by ayala on 10/18/2016.
 */

public class ShopActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager lm = new GridLayoutManager(this,2);
        //LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        final ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("China", 99900000, R.drawable.flag_china));
        countries.add(new Country("Greece", 8858458, R.drawable.flag_greece));
        countries.add(new Country("Israel", 6000000, R.drawable.flag_israel));
        countries.add(new Country("Italy", 8000000, R.drawable.flag_italy,true));
        countries.add(new Country("China",99900000,R.drawable.flag_china));
        countries.add(new Country("Greece",8858458,R.drawable.flag_greece,true));
        countries.add(new Country("Israel",6000000,R.drawable.flag_israel,true));
        countries.add(new Country("Italy",8000000,R.drawable.flag_italy));
        countries.add(new Country("China",99900000,R.drawable.flag_china,true));
        countries.add(new Country("Greece",8858458,R.drawable.flag_greece));
        countries.add(new Country("Israel",6000000,R.drawable.flag_israel));
        countries.add(new Country("Italy",8000000,R.drawable.flag_italy,true));
        countries.add(new Country("China",99900000,R.drawable.flag_china,true));
        countries.add(new Country("Greece",8858458,R.drawable.flag_greece));
        countries.add(new Country("Israel",6000000,R.drawable.flag_israel));
        countries.add(new Country("Italy",8000000,R.drawable.flag_italy));

        final CountryAdapter adapter = new CountryAdapter(countries);

        recyclerView.setAdapter(adapter);

    }
}
