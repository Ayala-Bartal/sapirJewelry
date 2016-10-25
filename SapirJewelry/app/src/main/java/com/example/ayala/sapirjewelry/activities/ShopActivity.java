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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient m_gClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);
        recyclerView = getRecylerView();
        putUserInView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        m_gClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                    Shop shop2 = null;
                    for (Shop shop : lstShop) {
                        shop2 = shop;
                        getImage(shop);
                        shop2 = shop;
                    }
              //      ShopAdapter adapter = new ShopAdapter((List<Shop>) lstShop);
               //     recyclerView.setAdapter(adapter);
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
        String pathName = "http://www.alljobs.co.il/"; //images/HomePage
        pathName = "http://www.alljobs.co.il/";

        ServerShopAPiI service = SapirFactory.createShopsApi(pathName);
        Call<ResponseBody> call = service.getImageDetails();
        String strUrl = call.request().url().toString();
        call.enqueue(getImageCallback(shop));
    }

    private Callback getImageCallback(final Shop shop) {
        Callback result = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Log.d("onResponse", "Response came from server");
                    boolean FileDownloaded = downloadImage(response.body(), shop);
                    Log.d("onResponse", "Image is downloaded and saved ? " + FileDownloaded);

                    List<Shop> lstShop = new ArrayList<Shop>();
                    lstShop.add(shop);
                    ShopAdapter adapter = new ShopAdapter((List<Shop>) lstShop);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        };
        return result;
    }

    private boolean downloadImage (ResponseBody body, Shop shop) {
        try {
            InputStream in = body.byteStream();
            File outFile = getOutFile("a.png");
            OutputStream out = new FileOutputStream(outFile);
            org.apache.commons.io.IOUtils.copy(in, out);
            processLocalImage(shop, outFile);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private File getOutFile(String fileName) throws Exception {
        File baseDire = getExternalFilesDir(null);
        File outFile = new File(baseDire + File.separator + fileName);
        boolean bCreateFile = outFile.createNewFile();
        return outFile;
    }

    private void processLocalImage(Shop shop, File outFile) {
        int width, height;
        long nFileSize = outFile.length();
        Bitmap bMap = BitmapFactory.decodeFile(outFile.getAbsolutePath());
        width = 2 * bMap.getWidth();
        height = 6 * bMap.getHeight();
        Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
        shop.setPicView(bMap2);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Shop Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        m_gClient.connect();
        AppIndex.AppIndexApi.start(m_gClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(m_gClient, getIndexApiAction());
        m_gClient.disconnect();
    }
}
