package com.example.neonadeuri.commomNeonaderi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.neonadeuri.R;

import java.util.ArrayList;

public class ListViewBtnAdapter extends ArrayAdapter implements View.OnClickListener {
    public interface ListBtnClickListener {
        void onListBtnClick(int position);
    }

    int resourceId;
    private ListBtnClickListener listBtnClickListener;

    public ListViewBtnAdapter(@NonNull Context context, int resource, ArrayList<InCartItem> list, ListBtnClickListener clickListener) {
        super(context, resource, list);
        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource;

        this.listBtnClickListener = clickListener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.list_item_minus_imageButton);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)view.getTag()) ;
        }
    }
}
