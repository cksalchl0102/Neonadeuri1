package com.example.neonadeuri.commomNeonaderi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.neonadeuri.Home;
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

    public String getPrice(int i) {
        return listViewInCartItemList.get(i).getItemPrice();
    }

    public String getNumber(int i) {
        return listViewInCartItemList.get(i).getItemNumber();
    }
    public String getInfo(int i){
        return listViewInCartItemList.get(i).getItemInfo();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        //리스트 뷰 안에서 이미지 버튼 클릭시 이벤트 처리하는 부분
        ImageButton imageButton = convertView.findViewById(R.id.list_item_minus_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chanmi", "minus button click OK");
                try {
                    listViewInCartItemList.remove(position);
                    notifyDataSetInvalidated();
                    notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d("chanmi", "삭제 안돼 ㅎㅎ ㅜ.ㅜ");
                }
            }
        });
        InCartItem inCartItem = listViewInCartItemList.get(position);

        itemName.setText(inCartItem.getItemName());
        itemPrice.setText(inCartItem.getItemPrice());
        itemInfo.setText(inCartItem.getItemInfo());
        itemNative.setText(inCartItem.getItemNumber());
        return convertView;

    }

    public void addItem(String name, String price, String number, String info) {
        InCartItem inCartItem = new InCartItem();

        inCartItem.setItemName(name);
        inCartItem.setItemPrice(price);
        inCartItem.setItemNumber(number);
        inCartItem.setItemInfo(info);

        listViewInCartItemList.add(inCartItem);
    }

}
