package com.example.sanoop.cartapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanoop.cartapp.Adapter.ItemListAdapter;
import com.example.sanoop.cartapp.Constant.ApiClient;
import com.example.sanoop.cartapp.Constant.Constant;
import com.example.sanoop.cartapp.Interface.ApiInterface;
import com.example.sanoop.cartapp.Model.Item;
import com.example.sanoop.cartapp.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ListCreationActivity extends AppCompatActivity {

    String cartId;
    String barcode = "";
    List<Item> items;
    ItemListAdapter adapter;
    TextView txtItemBarcode;
    ListView listView;
    EditText editItemName, editItemPrice, editItemQnty, editItemCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        editItemName = (EditText) findViewById(R.id.itemName);
        editItemPrice = (EditText) findViewById(R.id.itemPrice);
        editItemQnty = (EditText) findViewById(R.id.itemQuantity);
        editItemCategory = (EditText) findViewById(R.id.itemCategory);
        txtItemBarcode = (TextView) findViewById(R.id.txtBarcode);
        listView = (ListView) findViewById(R.id.itemListView);
        try {
            Bundle bundleObject = getIntent().getExtras();
            items = (ArrayList<Item>) bundleObject.getSerializable("items");
            cartId = bundleObject.getString("ID");
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("ITEMS::::", String.valueOf(items));
        Log.i("CARTID::::", String.valueOf(cartId));
        if (items.size() > 0) {
            loadCreatedList();
        }
    }

    private void loadCreatedList() {
        adapter = new ItemListAdapter(getApplicationContext(), R.layout.item_row_layout);
        for (Item item : items){
            adapter.add(item);
        }
        listView.setAdapter(adapter);
    }

    public void barcode(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void addItem(View view){
        if (validateInputFields()) {
            Calendar calendar = Calendar.getInstance();
            String addedTime = DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            RequestBody formBody = new FormBody.Builder()
                    .add(Constant.KEY_NAME, editItemName.getText().toString())
                    .add(Constant.KEY_PRICE, editItemPrice.getText().toString())
                    .add(Constant.KEY_QUANTITY, editItemQnty.getText().toString())
                    .add(Constant.KEY_CART, cartId)
                    .add(Constant.KEY_TIME, addedTime)
                    .add(Constant.KEY_CATEGORYID, String.valueOf(51))
                    .add(Constant.KEY_BARCODE, barcode)
                    .build();
            Call<Item> call = apiService.addItemToCart(formBody);
            call.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, retrofit2.Response<Item> response) {
                    if (response.code() == 400) {
                        Toast.makeText(getApplicationContext(), "Bad Request Please make sure the data entered is correct", Toast.LENGTH_LONG).show();
                        Log.e("RESPONSE ERROR::", response.errorBody().toString());
                    } else {
                        Item newItem = response.body();
                        items.add(newItem);
                        adapter.notifyDataSetChanged();
                        loadCreatedList();
                        editItemName.setText("");
                        editItemPrice.setText("");
                        editItemQnty.setText("");
                        editItemCategory.setText("");
                        barcode = "";
                        txtItemBarcode.setText("");
                        Toast.makeText(getApplicationContext(), "Item successfully added", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    Log.e("Throwable ERROR::", t.getMessage());
                }
            });
        }
    }

    public void cancel(View view){
        onBackPressed();
    }

    public boolean validateInputFields(){
        boolean flag = false;
        if( editItemName.getText().toString().trim().equals("")){
            editItemName.setError("Item name is required");
        }else if (editItemPrice.getText().toString().trim().equals("")){
            editItemPrice.setError("Item name is required");
        }else if (editItemQnty.getText().toString().trim().equals("")){
            editItemQnty.setError("Item name is required");
        }else {
            flag = true;
        }
        return flag;
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                txtItemBarcode.setVisibility(View.VISIBLE);
                barcode = result.getContents();
                txtItemBarcode.setText("Barcode : => " + barcode);
                result.getFormatName();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_creation, menu);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
