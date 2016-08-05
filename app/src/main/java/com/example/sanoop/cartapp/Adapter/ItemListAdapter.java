package com.example.sanoop.cartapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sanoop.cartapp.Activities.ListCreationActivity;
import com.example.sanoop.cartapp.Model.Cart;
import com.example.sanoop.cartapp.Model.Item;
import com.example.sanoop.cartapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanoop on 8/4/2016.
 */
public class ItemListAdapter extends ArrayAdapter<Item> {

    public Context context;
    private List<Item> list = new ArrayList<Item>();
    public ItemListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public void add(Item object) {
        list.add(object);
        super.add(object);
    }

    static class Holder{
        TextView itemName, itemPrice, itemQnty;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_row_layout, parent, false);
            holder = new Holder();
            holder.itemName = (TextView) row.findViewById(R.id.txtItemName);
            holder.itemPrice = (TextView) row.findViewById(R.id.txtItemPrice);
            holder.itemQnty = (TextView) row.findViewById(R.id.txtItemQnty);
            row.setTag(holder);
        }
        else {
            holder = (Holder) row.getTag();
        }
        final Item item= (Item) getItem(position);
        holder.itemName.setText(item.getName());
        holder.itemQnty.setText("Qnty: " + item.getQuantity());
        holder.itemPrice.setText("Price: " + (int) item.getPrice());
        return row;
    }


}