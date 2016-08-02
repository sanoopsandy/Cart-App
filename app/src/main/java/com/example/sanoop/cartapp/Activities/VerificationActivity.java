package com.example.sanoop.cartapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanoop.cartapp.Constant.Constant;
import com.example.sanoop.cartapp.Constant.PhoneAuth;
import com.example.sanoop.cartapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

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
        Verify ver = new Verify();
        ver.execute(txtPhone.getText().toString(), editCode.getText().toString());
    }

    public void resend(View view){
        Resend res = new Resend();
        res.execute(txtPhone.getText().toString());
    }

    private class Verify extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            PhoneAuth otp = new PhoneAuth();
            Response response =  otp.verifyOtpCode(params[0], params[1]);
            String result, memberId = null;
            JSONObject verifyJson = null;
            boolean verification = true;
            try {
                result = response.body().string();
                verifyJson = new JSONObject(result);
                verification = verifyJson.getBoolean("verfication");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("VERIFICATION:::::", String.valueOf(verification));
            if (response != null && response.code() == 200){
                try {
                    memberId = String.valueOf(verifyJson.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(VerificationActivity.this, CartActivity.class);
                intent.putExtra("ID", memberId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.i("SUCCESS:::::", String.valueOf(response));
            }else if (!verification){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "The Verification Code seems to be invalid. Hit resend to get a new code", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
            else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "There seems to be some error with the verification code. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
            Log.i("RESPONSE:", response.toString());
            return null;
        }
    }

    private class Resend extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            PhoneAuth otp = new PhoneAuth();
            Response response =  otp.sendOtpCode(params[0]);
            if (response != null && response.code() == 201){

            } else{
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "The number seems to be invalid. Please enter a valid number", Toast.LENGTH_LONG).show();
                        }
                    }
                );
            }
            Log.i("RESPONSE:", response.toString());
            return null;
        }
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
