package com.example.neonadeuri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Home extends AppCompatActivity {


    TableLayout t1;
    TableRow.LayoutParams rowLayout;
    //페이지 넘김
    Button buttonEventItem, buttonRecoItem;
    ImageView imageView = null, imageView2 = null;

    boolean imageViewIndex = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent(); //데이터 수신
        String userPhoneNum = intent.getExtras().getString("phoneNumber");
        setTitle(userPhoneNum+"님의 접속");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        t1 = (TableLayout) findViewById(R.id.inMyCartTableLinearLayout);
        rowLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        createTableRow(this);
        buttonEventItem = findViewById(R.id.buttonEventItemCall);
        buttonRecoItem = findViewById(R.id.buttonRecoItemCall);
        buttonRecoItem.setOnClickListener(callItem);
        buttonEventItem.setOnClickListener(callItem);
        imageView = findViewById(R.id.imageView);
        //imageView2 = findViewById(R.id.imageView);
        /*imageView1.setImageResource(R.drawable.image1);
        imageView2.setImageResource(R.drawable.image2);
        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.INVISIBLE);*/
    }

    View.OnClickListener callItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonEventItemCall) {
                imageView.setImageResource(R.drawable.image2);
                imageViewIndex = false;
            } else if (v.getId() == R.id.buttonRecoItemCall) {
                imageView.setImageResource(R.drawable.image1);
                imageViewIndex = true;
            }
        }
    };

    public void createTableRow(Home v) {

        TableRow row[] = new TableRow[6];
        TextView text[][] = new TextView[6][5];
        //tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        for (int i = 0; i < 6; i++) {
            row[i] = new TableRow(this);
            for (int j = 0; j < 5; j++) {
                if (j == 4) {
                    text[i][j] = new TextView(this);
                    text[i][j].setBackgroundResource(R.drawable.minus);
                    text[i][j].setGravity(Gravity.CENTER);

                    row[i].addView(text[i][j]);
                } else {
                    text[i][j] = new TextView(this);
                    text[i][j].setText("데이터" + i + "번째의 " + j);
                    text[i][j].setTextSize(11);
                    text[i][j].setTextColor(Color.BLACK);
                    text[i][j].setGravity(Gravity.CENTER);

                    row[i].addView(text[i][j]);
                }
            }
            t1.addView(row[i], rowLayout);
        }
    }
}
