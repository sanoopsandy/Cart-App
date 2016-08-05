package com.example.sanoop.cartapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanoop.cartapp.Constant.ApiClient;
import com.example.sanoop.cartapp.Constant.Constant;
import com.example.sanoop.cartapp.Interface.ApiInterface;
import com.example.sanoop.cartapp.Model.Member;
import com.example.sanoop.cartapp.R;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class VerificationActivity extends AppCompatActivity {
    String phone;
    TextView txtPhone;
    EditText editCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        editCode = (EditText) findViewById(R.id.edtVerify);
        Intent intent = getIntent();
        phone = intent.getStringExtra(Constant.KEY_PHONE);
        txtPhone.setText(phone);

    }

    public void verify(View view){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.KEY_PHONE, txtPhone.getText().toString())
                .add(Constant.KEY_CODE, editCode.getText().toString())
                .build();
        Call<Member> call = apiService.verifyOtpCode(formBody);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, retrofit2.Response<Member> response) {
                if (response.body() != null) {
                    if (response.code() == 400) {
                        Toast.makeText(getApplicationContext(), "Bad Request Please make sure the data entered is correct", Toast.LENGTH_LONG).show();
                        Log.e("RESPONSE ERROR::", response.errorBody().toString());
                    } else {
                        Member member = response.body();
                        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                        intent.putExtra("ID", member.getId().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "The Verification Code seems to be invalid. Hit resend to get a new code", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getApplicationContext(), "The number seems to be invalid. Please enter a valid number", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void resend(View view){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.KEY_PHONE, txtPhone.getText().toString())
                .build();
        Call<Void> call = apiService.sendOtpCode(formBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                Toast.makeText(getApplicationContext(), "OTP sent, you will receive a message soon.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Bad Request Please make sure the data entered is correct", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verification, menu);
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
