package com.example.neonadeuri.commomNeonaderi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neonadeuri.R;

import java.util.ArrayList;

public class InCartAdapter extends BaseAdapter {

    private ArrayList<InCartItem> listViewInCartItemList = new ArrayList<InCartItem>();

    public InCartAdapter() {

    }

    @Override
    public int getCount() {
        return listViewInCartItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewInCartItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.in_cart_list_item, parent, false);
        }
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemPrice = convertView.findViewById(R.id.itemPrice);
        TextView itemNative = convertView.findViewById(R.id.itemOrigin);
        TextView itemInfo = convertView.findViewById(R.id.itemInfo);

        InCartItem inCartItem = listViewInCartItemList.get(position);

        itemName.setText(inCartItem.getItemName());
        itemPrice.setText(inCartItem.getItemPrice());
        itemInfo.setText(inCartItem.getItemInfo());
        itemNative.setText(inCartItem.getItemNative());
        return convertView;
    }
    public void addItem(String name,String price,String origin,String info){
        InCartItem inCartItem  = new InCartItem();

        inCartItem.setItemName(name);
        inCartItem.setItemPrice(price);
        inCartItem.setItemNative(origin);
        inCartItem.setItemInfo(info);

        listViewInCartItemList.add(inCartItem);
    }
}
