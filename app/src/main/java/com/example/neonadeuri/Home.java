package com.example.neonadeuri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neonadeuri.commomNeonaderi.InCartAdapter;
import com.example.neonadeuri.commomNeonaderi.Message;
import com.example.neonadeuri.commomNeonaderi.ProductDTO;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    Button buttonEventItem, buttonRecoItem;
    ImageView imageView = null;
    ListView listView;
    InCartAdapter inCartAdapter;

    SeekBar seekBar = null;
    TextView curMoneyTextView = null;
    Button settingMoneyBtn = null;
    String budgetItThis; //시용자의 현재 금액 저장
    TextView budgetTextview; //현재 금액 보이기 View
    int curMoney = 0;

    ImageButton refreshItemInCart = null;

    Button logoutButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent(); //데이터 수신
        String userPhoneNum = intent.getExtras().getString("phoneNumber");
        String userName = intent.getExtras().getString("name");
        setTitle(userName + ", " + userPhoneNum + "님의 접속");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonEventItem = findViewById(R.id.buttonEventItemCall);
        buttonRecoItem = findViewById(R.id.buttonRecoItemCall);
        imageView = findViewById(R.id.imageView);

        buttonEventItem.setOnClickListener(imageChangeButtonEvent);
        buttonRecoItem.setOnClickListener(imageChangeButtonEvent);

        //refreshItemInCart
        refreshItemInCart = findViewById(R.id.refreshItemInCart);
        refreshItemInCart.setOnClickListener(refreshItemInCartListener);


        curMoneyTextView = findViewById(R.id.curMoney);
        settingMoneyBtn = findViewById(R.id.settingMoneyBtn);
        settingMoneyBtn.setOnClickListener(settingMoneyBtnListener);

        //예산안 보이기.
        budgetTextview = findViewById(R.id.maxMoneyTextView);

        //로그아웃 버튼
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(logoutFuncListener);

        inCartAdapter = new InCartAdapter();
        listView = findViewById(R.id.in_cart_list);
        listView.setAdapter(inCartAdapter);


        seekBar = findViewById(R.id.seekBar1);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarSetText();
                curMoneyTextView.setText(i + "원");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        inCartAdapter.addItem("iPhone XS", "10", "1", "- ");
        inCartAdapter.addItem("팜스 사과 3kg", "10", "1", "할인중");
        inCartAdapter.addItem("임금님표 이천쌀", "10", "1", "-");
        inCartAdapter.addItem("홍삼정", "10", "1", "-");
        inCartAdapter.addItem("이니스프리 선크림", "10", "2", "1+1 행사중");
        inCartAdapter.addItem("르꼬끄 백팩", "10", "1", "-");

    }

    //로그아웃
    View.OnClickListener logoutFuncListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.MyAlertDialogStyle);
            builder.setTitle("Log Out");
            builder.setMessage("정말로 로그아웃 하시겠습니까??? ");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("취소", null);
            builder.show();
        }
    };

    //카드 안에 있는 물품 새로 고침
    View.OnClickListener refreshItemInCartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            curMoney = 0;
            inCartAdapter.notifyDataSetInvalidated();
            inCartAdapter.notifyDataSetChanged();
            for (int i = 0; i < inCartAdapter.getCount(); i++) {

                if (inCartAdapter.getNumber(i) != "1") {
                    int cnt = Integer.parseInt(inCartAdapter.getNumber(i));//
                    if(inCartAdapter.getInfo(i) == "1+1 행사중"){
                        cnt = cnt/2;
                    }
                    curMoney = curMoney + Integer.parseInt(inCartAdapter.getPrice(i)) * cnt;
                } else {
                    curMoney = curMoney + Integer.parseInt(inCartAdapter.getPrice(i));
                }
            }
            curMoneyTextView.setText(String.valueOf(curMoney));
            //seekBar.setProgress(seekBar.getProgress()+curMoney);
            seekBar.setProgress(curMoney);
            /*curMoney = calculMoney();
            curMoneyTextView.setText(String.valueOf(curMoney));
            seekBar.setProgress(curMoney);*/
        }
    };

    View.OnClickListener settingMoneyBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setting_money_dialog_show();
        }
    };

    View.OnClickListener imageChangeButtonEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonEventItemCall:
                    imageView.setImageResource(R.drawable.bulgogi);
                    break;
                case R.id.buttonRecoItemCall:
                    imageView.setImageResource(R.drawable.tonymory);

                    break;
            }
        }
    };

    private void seekBarSetText() {
        int progress = seekBar.getProgress();
        int max = seekBar.getMax();
        int offset = seekBar.getThumbOffset();
        float percent = ((float) progress) / (float) max;
        int width = seekBar.getWidth() - 2 * offset;
        int answer = ((int) (width * percent + offset - curMoneyTextView.getWidth() / 2));
        curMoneyTextView.setX(answer);
    }

    public void setting_money_dialog_show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.MyAlertDialogStyle);
        builder.setTitle("예산안 입력하기");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_money_dialog, null);
        builder.setView(view);

        final EditText budgetText = view.findViewById(R.id.budgetText);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String budget = budgetText.getText().toString();
                if (budget == "") {
                    //아무 입력도 하지 않음.
                    //이거 인식이 안됨 나중에 고쳐야할 것 우선순위 : 4
                    Message.information(Home.this, "경고", "입력하지 않으셨습니다.");
                    return;
                } else {
                    budgetItThis = budget;
                    Toast.makeText(getApplicationContext(), budgetItThis + "원 확인 ", Toast.LENGTH_LONG).show();
                    budgetTextview.setText(budgetItThis + "원 ♥");
                    seekBar.setMax(Integer.parseInt(budgetItThis));

                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /*
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
        };*/
/*
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
    }*/
    public int calculMoney() {
        int result = 0;
        int cur = 0;
        for (int i = 0; i < inCartAdapter.getCount(); i++) {
            cur = Integer.parseInt(inCartAdapter.getPrice(i));
            if (Integer.parseInt(inCartAdapter.getNumber(i)) != 1) {
                int cnt = Integer.parseInt(inCartAdapter.getNumber(i));
                cur = cur * cnt;
            }
            result = result + cur;
        }
        return result;
    }
}
