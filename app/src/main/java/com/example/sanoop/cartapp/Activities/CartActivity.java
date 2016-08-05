package com.example.sanoop.cartapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sanoop.cartapp.Adapter.CartListAdapter;
import com.example.sanoop.cartapp.Constant.ApiClient;
import com.example.sanoop.cartapp.Interface.ApiInterface;
import com.example.sanoop.cartapp.Model.Cart;
import com.example.sanoop.cartapp.Model.CartResponse;
import com.example.sanoop.cartapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CartActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        loadCartList(id);
    }

    private void loadCartList(String id) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse> call = apiService.getCartList(id);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, retrofit2.Response<CartResponse> response) {
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Bad Request Please make sure the data entered is correct", Toast.LENGTH_LONG).show();
                    Log.e("RESPONSE ERROR::", response.errorBody().toString());
                } else {
                    List<Cart> carts = response.body().getResults();
                    ListView listView = (ListView) findViewById(R.id.listView);
                    CartListAdapter adapter = new CartListAdapter(getApplicationContext(), R.layout.cart_list_row_layout);
                    for (Cart cart : carts) {
                        adapter.add(cart);
                    }
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
