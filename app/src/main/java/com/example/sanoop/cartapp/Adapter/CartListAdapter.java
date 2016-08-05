package com.example.sanoop.cartapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sanoop.cartapp.Activities.ListCreationActivity;
import com.example.sanoop.cartapp.Model.Cart;
import com.example.sanoop.cartapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanoop on 8/3/2016.
 */
public class CartListAdapter extends ArrayAdapter<Cart> {

    public Context context;
    private List<Cart> list = new ArrayList<Cart>();
    public CartListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public void add(Cart object) {
        list.add(object);
        super.add(object);
    }

    static class Holder{
        TextView cartName, cartEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.cart_list_row_layout, parent, false);
            holder = new Holder();
            holder.cartName = (TextView) row.findViewById(R.id.cartName);
            holder.cartEdit = (TextView) row.findViewById(R.id.cartEdit);
            row.setTag(holder);
        }
        else {
            holder = (Holder) row.getTag();
        }
        final Cart cart= (Cart) getItem(position);
        holder.cartName.setText(cart.getName());
        holder.cartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListCreationActivity.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putString("ID", String.valueOf(cart.getId()));
                bundleObject.putSerializable("items", cart.getItems());
                intent.putExtras(bundleObject);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return row;
    }


}