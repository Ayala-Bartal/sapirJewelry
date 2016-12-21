package com.example.ayala.sapirjewelry.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayala.sapirjewelry.IpAdrress;
import com.example.ayala.sapirjewelry.entities.Customers;
import com.example.ayala.sapirjewelry.R;
import com.example.ayala.sapirjewelry.api.SapirFactory;
import com.example.ayala.sapirjewelry.api.ServerUsersAPiI;

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
    EditText gender;
    EditText city;
    EditText birthday;
    EditText weddingDate;
    ServerUsersAPiI serverUsersApi;
    String m_ip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        m_ip = ((IpAdrress)this.getApplication()).getIpAdrress();

        firstName = (EditText) findViewById(R.id.ed_first_name);

        familyName = (EditText) findViewById(R.id.ed_last_name);

        phoneNumber = (EditText) findViewById(R.id.ed_phone);

        emailAddress = (EditText) findViewById(R.id.ed_email);

        city = (EditText)findViewById(R.id.ed_city);

        gender = (EditText)findViewById(R.id.ed_gender);
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGenderDialog(gender);
            }
        });

        birthday = (EditText) findViewById(R.id.ed_birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalender(birthday);
            }
        });

        weddingDate = (EditText) findViewById(R.id.ed_wedding_date);
        weddingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalender(weddingDate);
            }
        });

        serverUsersApi = SapirFactory.createUsersApi(m_ip);

        Button register_btn = (Button) findViewById(R.id.ed_register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customers customer = getCustomerFromInput();
                sendCustomerToServer(customer);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
               toast("נרשמת בהצלחה למועדון הלקוחות");
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

    private void getGenderDialog (final TextView result){
        final CharSequence gender[] = new CharSequence[] {"זכר","נקבה"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String curnet =  gender[which].toString();
                result.setText(curnet);
                toast(curnet);
            }
        });
        builder.show();
    }
    private Customers getCustomerFromInput (){
        Customers customer = new Customers();
        customer.setFirstName(firstName.getText().toString());
        customer.setLastName(familyName.getText().toString());
        customer.setPhoneNumber(phoneNumber.getText().toString());
        customer.setEmail(emailAddress.getText().toString());
        customer.setBirthday(birthday.getText().toString());
        customer.setWeddingDate(weddingDate.getText().toString());
        customer.setGender(gender.getText().toString());
        customer.setCity(city.getText().toString());
        return  customer;

    }
    private void sendCustomerToServer(Customers customer){
        Call<Customers> executer = serverUsersApi.postUser(customer);
        executer.enqueue(getCreateCustemerCallBack());
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