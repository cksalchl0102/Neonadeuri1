package com.example.neonadeuri.commomNeonaderi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import java.util.HashMap;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.ExecutionException;

public class InCartAdapter extends BaseAdapter {

    private ArrayList<InCartItem> listViewInCartItemList = new ArrayList<InCartItem>();
    String getNum = "";
    int num = 0;
    String setNum = "";
    String returns = "";
    String minusName = "";
    String setMinusName="";
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

    public String getName(int i) {
        return listViewInCartItemList.get(i).getItemName();
    }

    public String getNumber(int i) {
        return listViewInCartItemList.get(i).getItemNumber();
    }

    public String getInfo(int i) {
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
                    if (listViewInCartItemList.get(position).getItemNumber().equals("1")) {
                        minusName = listViewInCartItemList.get(position).getItemName().toString();

                        listViewInCartItemList.remove(position);
                        /*minusName = listViewInCartItemList.get(position).getItemName().toString();
                        Log.i("chanmi","minusName : "+minusName);*/
                        checkProductNumberMinus(minusName);
                        notifyDataSetInvalidated();
                        notifyDataSetChanged();

                    } else {
                        getNum = listViewInCartItemList.get(position).getItemNumber();
                        num = Integer.parseInt(getNum);
                        num = num - 1;
                        setNum = Integer.toString(num);
                        listViewInCartItemList.get(position).setItemNumber(setNum);
                        minusName = listViewInCartItemList.get(position).getItemName().toString();

                        checkProductNumberMinus(minusName);
                        notifyDataSetInvalidated();
                        notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.d("chanmi", "삭제 안돼 ㅎㅎ ㅜ.ㅜ");
                }

                Log.i("chanmi", "minusName : " + minusName);
            }
        });
        InCartItem inCartItem = listViewInCartItemList.get(position);

        itemName.setText(inCartItem.getItemName());
        itemPrice.setText(inCartItem.getItemPrice());
        itemInfo.setText(inCartItem.getItemInfo());
        itemNative.setText(inCartItem.getItemNumber());

        //아이템의 세로 크기 조정


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

    public String getMinusName() {
        return minusName;
    }

    public void setMinusName(String name) {
        this.minusName = name;
    }

    public void plusItemNumber(int i) {
        listViewInCartItemList.get(i).setItemNumber();
    }


    public boolean checkProductNumberMinus(String productName) {
        String resultProductMinus = "";
        try {
            resultProductMinus = new ProductTask().execute(productName, "setProductNumberMinusHS").get();
            if (resultProductMinus.equals("setProductNumberMinus_HS success!")) {
                Log.i("chanmi", "재고 관리 Minus 업데이트 성공");
            } else {
                Log.i("chanmi", "재고 관리 Minus 업데이트 실패");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
