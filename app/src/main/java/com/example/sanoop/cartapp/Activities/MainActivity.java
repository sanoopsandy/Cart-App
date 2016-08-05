package com.example.sanoop.cartapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanoop.cartapp.Constant.ApiClient;
import com.example.sanoop.cartapp.Constant.Constant;
import com.example.sanoop.cartapp.Interface.ApiInterface;
import com.example.sanoop.cartapp.R;
import com.sithagi.countrycodepicker.CountryPicker;
import com.sithagi.countrycodepicker.CountryPickerListener;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    String counCode;
    EditText editCountry, editPhone;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editCountry = (EditText) findViewById(R.id.editCountry);
        editPhone = (EditText) findViewById(R.id.editPhone);
        register = (Button) findViewById(R.id.btnRegister);
        editCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CountryPicker picker = CountryPicker.newInstance("SelectCountry");
                picker.show(getSupportFragmentManager(), "COUNTRY_CODE_PICKER");

                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode) {
                        editCountry.setText(name);
                        counCode = dialCode;
                        picker.dismiss();
                        Log.i("DIAL CODE:", dialCode);
                    }
                });
            }
        });

    }

    public void register(View view){
        final String phone = counCode + editPhone.getText().toString();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.KEY_PHONE, phone)
                .build();
        Call<Void> call = apiService.sendOtpCode(formBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Bad Request Please make sure the data entered is correct", Toast.LENGTH_LONG).show();
                    Log.e("RESPONSE ERROR::", response.errorBody().toString());
                } else {
                    Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                    intent.putExtra(Constant.KEY_PHONE, phone);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Void>call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getApplicationContext(), "The number seems to be invalid. Please enter a valid number", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
