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

import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.adapters.JewelryAdapter;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.ServerJewelryAPiI;
import com.example.ayala.sapirjewelry.entities.Jewelry;
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
import java.util.Collection;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayala on 12/4/2016.
 */

public class JewelryActivity extends AppCompatActivity {

    final String m_serverUrl = "http://192.168.100.62:8082/";
    RecyclerView recyclerView;
    int m_counter = 0;
    String m_j;


    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jewelery_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            m_j = extras.getString("Jewelry");
            presentJewelry(m_j);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void presentJewelry(String j) {
        recyclerView = getRecylerView();
        m_j = j;
        putJewelryInView(j);
    }

    private RecyclerView getRecylerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        return recyclerView;
    }

    private void putJewelryInView(String category) {
        ServerJewelryAPiI serverJewelryAPiI = SapirFactory.createJewelryApi(m_serverUrl);
        Call<Collection<Jewelry>> call = serverJewelryAPiI.getJewelriesByCategory(category);
        String strUrl = call.request().url().toString();
        call.enqueue(getJewelryCallBack());
    }

    private Callback<Collection<Jewelry>> getJewelryCallBack() {
        Callback<Collection<Jewelry>> result = new Callback<Collection<Jewelry>>() {
            @Override
            public void onResponse(Call<Collection<Jewelry>> call, Response<Collection<Jewelry>> response) {
                if (response.isSuccessful()) {
                    Collection<Jewelry> lstJewelry = response.body();
                    getImages(lstJewelry);
                } else {
                    toast(response.errorBody() + ""); //TODO
                }
            }

            @Override
            public void onFailure(Call<Collection<Jewelry>> call, Throwable t) {
                toast(t.getMessage() + "");
            }
        };
        return result;
    }

    private void toast(String text) {
        Toast.makeText(JewelryActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private void getImages(final Collection<Jewelry> lstJewelry) { //TODO: remove
        m_counter = lstJewelry.size();
        for (final Jewelry jewelry : lstJewelry) {
            getImage(jewelry, lstJewelry);
        }
    }

    private void getImage(final Jewelry jewelry, final Collection<Jewelry> lstJewelry) {
        ServerJewelryAPiI service = SapirFactory.createJewelryApi(m_serverUrl);
        Call<ResponseBody> call = service.getImageDetails(jewelry.getName());
        String strUrl = call.request().url().toString();
        call.enqueue(getImageCallback(jewelry, lstJewelry));
    }

    private Callback<ResponseBody> getImageCallback(final Jewelry jewelry, final Collection<Jewelry> lstJewelry) {
        Callback<ResponseBody> result = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    downloadImage(body, jewelry);
                    setImagesInView(lstJewelry);
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

    synchronized void setImagesInView(final Collection<Jewelry> lstJewelry) {
        m_counter--;
        if (m_counter > 0) {
            return;
        }
        JewelryAdapter adapter = new JewelryAdapter((List<Jewelry>) lstJewelry);
        recyclerView.setAdapter(adapter);
    }

    private void downloadImage(ResponseBody body, Jewelry jewelry) throws Exception {
        InputStream inStream = body.byteStream();
        File outFile = getOutFile(jewelry.getName()); //TODO
        OutputStream outStream = new FileOutputStream(outFile);
        IOUtils.copy(inStream, outStream);
        processLocalImage(jewelry, outFile);
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

    private void processLocalImage(Jewelry jewelry, File outFile) {
        int width, height;
        long nFileSize = outFile.length();
        Bitmap bMap = BitmapFactory.decodeFile(outFile.getAbsolutePath());
        width = 2 * bMap.getWidth();
        height = 2 * bMap.getHeight();
        Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
        jewelry.setPicView(bMap2);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Jewelry Page") // TODO: Define a title for the content shown.
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
