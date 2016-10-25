package com.example.ayala.sapirjewelry.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.example.ayala.sapirjewelry.adapters.ShopAdapter;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.ServerShopAPiI;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.entities.Shop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okhttp3.ResponseBody;
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
        recyclerView = getRecylerView();
        putUserInView();
    }

    private RecyclerView getRecylerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        return recyclerView;
    }

    private void putUserInView() {
        ServerShopAPiI serverShopAPiI = SapirFactory.createShopsApi("http://192.168.1.7:8082/");
        Call<Collection<Shop>> callback1 = serverShopAPiI.getAllShopNames();
        callback1.enqueue(getCallBack());
    }

    private Callback getCallBack() {
        Callback result = new Callback<Collection<Shop>>() {
            @Override
            public void onResponse(Call<Collection<Shop>> call, Response<Collection<Shop>> response) {
                if (response.isSuccessful()) {
                    Collection<Shop> lstShop = response.body();
                    for (Shop shop : lstShop) {
                        getImage(shop);
                    }
                } else {
                    toast(response.errorBody() + ""); //TODO
                }
            }

            @Override
            public void onFailure(Call<Collection<Shop>> call, Throwable t) {
                toast(t.getMessage() + "");
            }
        };
        return result;
    }

    private void toast(String text) {
        Toast.makeText(ShopActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private void getImage(final Shop shop) {
        String pathName = "http://192.168.1.7:8082/";
        ServerShopAPiI service = SapirFactory.createShopsApi(pathName);
        Call<ResponseBody> call = service.getImageDetails(shop.getName());
        String strUrl = call.request().url().toString();
        call.enqueue(getImageCallback(shop));
    }

    private Callback getImageCallback(final Shop shop) {
        Callback result = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    downloadImage(response.body(), shop);
                    List<Shop> lstShop = new ArrayList<Shop>();
                    lstShop.add(shop);
                    ShopAdapter adapter = new ShopAdapter((List<Shop>) lstShop);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                   toast(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        };
        return result;
    }

    private void downloadImage (ResponseBody body, Shop shop) throws Exception {
        InputStream inStream = body.byteStream();
        File outFile = getOutFile(shop.getName()); //TODO
        OutputStream outStream = new FileOutputStream(outFile);
        org.apache.commons.io.IOUtils.copy(inStream, outStream);
        processLocalImage(shop, outFile);
    }
    private File getOutFile(String fileName) throws Exception {
        File baseDire = getExternalFilesDir(null);
        File outFile = new File(baseDire + File.separator + fileName);
        boolean bCreateFile = outFile.createNewFile();
        if (!bCreateFile){
            //TODO:
         //   throw new Exception ("Faild to create file: " + outFile);
        }
        return outFile;
    }

    private void processLocalImage(Shop shop, File outFile) {
        int width, height;
        long nFileSize = outFile.length();
        Bitmap bMap = BitmapFactory.decodeFile(outFile.getAbsolutePath());
        width = 2 * bMap.getWidth();
        height = 2 * bMap.getHeight();
        Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
        shop.setPicView(bMap2);
    }
}