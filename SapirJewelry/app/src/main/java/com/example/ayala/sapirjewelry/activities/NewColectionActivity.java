package com.example.ayala.sapirjewelry.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ayala.sapirjewelry.adapters.CustomersAdapter;
import com.example.ayala.sapirjewelry.entities.Customers;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.ServerUsersAPiI;

import java.util.Collection;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayala on 10/20/2016.
 */

public class NewColectionActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_colection_activity);
        recyclerView =getRecylerView();
        putUserInView();
        getCallBack();
    }
    private RecyclerView getRecylerView (){
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager lm = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(lm);
        return recyclerView;
    }
    private void putUserInView (){
        ServerUsersAPiI serverUsersApi = SapirFactory.createUsersApi("http://192.168.100.62:8082/");
        Call<Collection<Customers>> callback1 = serverUsersApi.getAllUsersNames();
        HttpUrl url = callback1.request().url();
        String strURL = url.toString();
        toast(strURL);
        callback1.enqueue(getCallBack());
    }
    private Callback getCallBack (){
        Callback result = new Callback<Collection<Customers>>() {
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
                    toast(response.errorBody()+""); //TODO
                }
            }
            @Override
            public void onFailure(Call<Collection<Customers>> call, Throwable t) {
                toast(t.getMessage()+"");
            }
        };
        return result;
    }

    private void toast (String text){
        Toast.makeText(NewColectionActivity.this, text, Toast.LENGTH_LONG).show();
    }
}


