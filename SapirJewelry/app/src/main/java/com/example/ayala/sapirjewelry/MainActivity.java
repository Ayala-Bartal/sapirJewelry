package com.example.ayala.sapirjewelry;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    final String REGISTER_FRAGMENT_TAG = "register_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout shop = (LinearLayout)findViewById(R.id.shop_btn);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayout colaction = (LinearLayout)findViewById(R.id.new_colaction);
        colaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayout sales = (LinearLayout)findViewById(R.id.sales);
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayout club = (LinearLayout)findViewById(R.id.club);
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = RegisterFragment.newInstance();
                android.app.FragmentManager manager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, registerFragment, REGISTER_FRAGMENT_TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
               /* RegisterFragment registerFragment = new RegisterFragment();
                MainActivity.this.getFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment, REGISTER_FRAGMENT_TAG).addToBackStack(null).commit();*/
            }
        });

        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageView contact = (ImageView) findViewById(R.id.conact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
