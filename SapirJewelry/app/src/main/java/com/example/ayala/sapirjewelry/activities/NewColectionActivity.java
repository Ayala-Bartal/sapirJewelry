package com.example.ayala.sapirjewelry.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ayala.sapirjewelry.entities.Customers;
import com.example.ayala.sapirjewelry.adapters.CustomersAdapter;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.SapirServerAPiI;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayala on 10/20/2016.
 */

public class NewColectionActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_colection_activity);

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager lm = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(lm);

        SapirServerAPiI sapirServer = SapirFactory.create("http://192.168.1.5:8082");
        Call<Collection<Customers>> callback1 = sapirServer.getAllUsersNames();
        callback1.enqueue(new Callback<Collection<Customers>>() {
            @Override
            public void onResponse(Call<Collection<Customers>> call, Response<Collection<Customers>> response) {
                if (response.isSuccessful()) {
                    Collection<Customers> lstCustomers = response.body();
                    for (Customers user : lstCustomers){
                       String strUser = user.toString();
                        user.toString();

                    }
                    CustomersAdapter adapter = new CustomersAdapter((List<Customers>) lstCustomers);
                    recyclerView.setAdapter(adapter);

                } else {
                    System.out.println("response.errorBody: " + response.errorBody() + " call:" + call);
                }
            }
            @Override
            public void onFailure(Call<Collection<Customers>> call, Throwable t) {
                System.out.println("Throwable: " + t + " call:" + call);
            }
        });
        try {
            callback1.execute();
        } catch (Exception e) {
            System.out.println("e.getMessage(): " + e);
        }


    }
}
