package com.example.ayala.sapirjewelry.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ayala.sapirjewelry.adapters.ShopAdapter;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.ServerShopAPiI;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.entities.Shop;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayala on 10/18/2016.
 */

public class ShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

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
        ServerShopAPiI serverShopAPiI = SapirFactory.createShopsApi("http://192.168.1.7:8082/");
        Call<Collection<Shop>> callback1 = serverShopAPiI.getAllShopNames();
        callback1.enqueue(getCallBack());
    }
    private Callback getCallBack (){
        Callback result = new Callback<Collection<Shop>>() {
            @Override
            public void onResponse(Call<Collection<Shop>> call, Response<Collection<Shop>> response) {
                if (response.isSuccessful()) {
                    Collection<Shop> lstShop = response.body();
                    for (Shop shop : lstShop){
                        String strShop = shop.toString();
                        shop.toString();
                    }
                    ShopAdapter adapter = new ShopAdapter((List<Shop>) lstShop);
                    recyclerView.setAdapter(adapter);
                } else {
                    toast(response.errorBody()+""); //TODO
                }
            }
            @Override
            public void onFailure(Call<Collection<Shop>> call, Throwable t) {
                toast(t.getMessage()+"");
            }
        };
        return result;
    }

    private void toast (String text){
        Toast.makeText(ShopActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
