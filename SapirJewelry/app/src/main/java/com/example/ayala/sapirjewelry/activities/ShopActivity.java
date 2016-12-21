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

import com.example.ayala.sapirjewelry.IpAdrress;
import com.example.ayala.sapirjewelry.adapters.ShopAdapter;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.ServerShopAPiI;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.entities.Shop;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayala on 10/18/2016.
 */

public class ShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String m_ip;
    int m_counter = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_ip = ((IpAdrress)this.getApplication()).getIpAdrress();
        setContentView(R.layout.shop_activity);
        recyclerView = getRecylerView();
        putShopsInView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private RecyclerView getRecylerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        return recyclerView;
    }

    private void putShopsInView() {
        ServerShopAPiI serverShopAPiI = SapirFactory.createShopsApi(m_ip);
        Call<Collection<Shop>> callback1 = serverShopAPiI.getAllShopNames();
        callback1.enqueue(getShopsCallBack());
    }

    private Callback getShopsCallBack() {
        Callback result = new Callback<Collection<Shop>>() {
            @Override
            public void onResponse(Call<Collection<Shop>> call, Response<Collection<Shop>> response) {
                if (response.isSuccessful()) {
                    Collection<Shop> lstShop = response.body();
                    getImages(lstShop);
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

    private void getImages(final Collection<Shop> lstShop) { //TODO: remove
        m_counter = lstShop.size();
        for (final Shop shop : lstShop) {
            getImage(shop, lstShop);
        }
    }

    private void getImage(final Shop shop, final Collection<Shop> lstShop) {
        ServerShopAPiI service = SapirFactory.createShopsApi(m_ip);
        Call<ResponseBody> call = service.getImageDetails(shop.getName());
        String strUrl = call.request().url().toString();
        call.enqueue(getImageCallback(shop, lstShop));
    }

    private Callback getImageCallback(final Shop shop, final Collection<Shop> lstShop) {
        Callback result = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    downloadImage(response.body(), shop);
                    setImagesInView(lstShop);
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

    synchronized void setImagesInView(final Collection<Shop> lstShop) {
        m_counter--;
        if (m_counter > 0) {
            return;
        }
        ShopAdapter adapter = new ShopAdapter(ShopActivity.this, (List<Shop>) lstShop);
        recyclerView.setAdapter(adapter);
    }

    private void downloadImage(ResponseBody body, Shop shop) throws Exception {
        InputStream inStream = body.byteStream();
        File outFile = getOutFile(shop.getName()); //TODO
        OutputStream outStream = new FileOutputStream(outFile);
        IOUtils.copy(inStream, outStream);
        processLocalImage(shop, outFile);
    }

    private File getOutFile(String fileName) throws Exception {
        File baseDire = getExternalFilesDir(null);
        File outFile = new File(baseDire + File.separator + fileName);
        boolean bCreateFile = outFile.createNewFile();
        if (!bCreateFile) {
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
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}