package com.example.ayala.sapirjewelry.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayala.sapirjewelry.entities.Customers;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.SapirServerAPiI;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayala on 10/11/2016.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText firstName;
    EditText familyName;
    EditText phoneNumber;
    EditText emailAddress;
    TextView birthday;
    TextView weddingDate;
    SapirServerAPiI sapirServerApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        firstName = (EditText) findViewById(R.id.input_first_name);

        familyName = (EditText) findViewById(R.id.input_family_name);

        phoneNumber = (EditText) findViewById(R.id.input_phone_number);

        emailAddress = (EditText) findViewById(R.id.input_email_address);

        birthday = (TextView) findViewById(R.id.input_birthday);

        weddingDate = (TextView) findViewById(R.id.input_wedding_date);

        sapirServerApi = SapirFactory.create("http://192.168.1.5:8082/");


        Button bithday_btn = (Button) findViewById(R.id.birtday_btn);
        bithday_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getCalender(birthday);
            }
        });

        Button wedding_day_btn = (Button) findViewById(R.id.wedding_date_btn);
        wedding_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalender(weddingDate);
            }
        });

        Button register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customers customer = getCustomerFromInput();
                sendCustomerToServer(customer);
            }
        });


    }
    private void getCalender (final TextView result){
        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int a = i1 + 1;
                Toast.makeText(RegisterActivity.this, i2 + "/" + (i1 + 1) + "/" + i, Toast.LENGTH_SHORT).show();
                result.setText(i2 + "/" + a + "/" + i);
            }
        }, year, month, day);
        dpd.show();

    }
    private Customers getCustomerFromInput (){
        Customers customer = new Customers();
        customer.setFirstName(firstName.getText().toString());
        customer.setLastName(familyName.getText().toString());
        customer.setPhoneNumber(phoneNumber.getText().toString());
        customer.setEmail(emailAddress.getText().toString());
        customer.setBirthday(birthday.getText().toString());
        customer.setWeddingDate(weddingDate.getText().toString());
        return  customer;

    }
    private void sendCustomerToServer(Customers customer){
        Call<Customers> executer = sapirServerApi.postUser(customer);
        executer.enqueue(getCreateCustemerCallBack());
        try {
            executer.execute();
        } catch (Exception e) {
            toast(e.getMessage());
        }
    }

    private Callback getCreateCustemerCallBack(){
        Callback result = new Callback<Customers>() {
            @Override
            public void onResponse(Call<Customers> call, Response<Customers> response) {
                if (response.isSuccessful()) {
                    Customers serverCustomers = response.body();
                    response.toString();
                } else {
                    toast(response.errorBody()+"");
                }
            }
            @Override
            public void onFailure(Call<Customers> call, Throwable t) {
                toast(t.getMessage());
            }
        };
        return result;
    }
    private void toast (String text){
        Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_LONG).show();
    }
}