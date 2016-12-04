package com.example.ayala.sapirjewelry.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ayala.sapirjewelry.R;

/**
 * Created by ayala on 11/4/2016.
 */

public class ContactActivity extends AppCompatActivity {

    public static String M_FACEBOOK_URL = "https://www.facebook.com/SapirBridalJewelry";
    public static String M_FACEBOOK_PAGE_ID = "SapirBridalJewelry";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

       LinearLayout address = (LinearLayout)findViewById(R.id.contact_address_btn);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapAddress = "geo:0,0?q=32.167423,34.844369(הרצליה)";
                Uri uri = Uri.parse(mapAddress);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        LinearLayout email = (LinearLayout)findViewById(R.id.contact_email_btn);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "Sapir567@gmail.com";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                intent.putExtra(Intent.EXTRA_SUBJECT, "This is the email subject");
                intent.putExtra(Intent.EXTRA_TEXT, "This is the email body");
                intent.setType("text/html");
                startActivity(intent);
            }
        });

        LinearLayout facebook = (LinearLayout)findViewById(R.id.contact_facebook_btn);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(ContactActivity.this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        LinearLayout instegram = (LinearLayout)findViewById(R.id.contact_instegram_btn);
        instegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/Sapirfeldmantal");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/Sapirfeldmantal")));
                }
            }
        });

        LinearLayout first_number = (LinearLayout)findViewById(R.id.contact_number_1);
        first_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"+"077-9120256"));
                startActivity(intent);
            }
        });

        LinearLayout seconed_number = (LinearLayout)findViewById(R.id.contact_number_2);
        seconed_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("tel:"+"054-8078003"));
                startActivity(intent);
            }
        });
    }


    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + M_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + M_FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return M_FACEBOOK_URL; //normal web url
        }
    }
}
