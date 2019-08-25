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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neonadeuri.commomNeonaderi.InCartAdapter;
import com.example.neonadeuri.commomNeonaderi.Message;

public class Home extends AppCompatActivity {

    Button buttonEventItem, buttonRecoItem;
    ImageView imageView = null;
    ListView listView;
    InCartAdapter inCartAdapter;

    SeekBar seekBar = null;
    TextView curMoneyTextView = null;
    Button settingMoneyBtn = null;
    String budgetItThis;
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

        curMoneyTextView = findViewById(R.id.curMoney);
        settingMoneyBtn = findViewById(R.id.settingMoneyBtn);
        settingMoneyBtn.setOnClickListener(settingMoneyBtnListener);

        inCartAdapter = new InCartAdapter();
        listView = findViewById(R.id.in_cart_list);
        listView.setAdapter(inCartAdapter);

        seekBar = findViewById(R.id.seekBar1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarSetText();
                curMoneyTextView.setText(i * 15000 + "원");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        inCartAdapter.addItem("iPhone XS", "1500000", "  1", "- ");
        inCartAdapter.addItem("팜스 사과 3kg", "19800", "  1", "할인중");
        inCartAdapter.addItem("임금님표 이천쌀", "47900", "  1", "-");
        inCartAdapter.addItem("홍삼정", "172260", "  1", "-");
        inCartAdapter.addItem("이니스프리 선크림", "13200", "  2", "1+1 행사중");
        inCartAdapter.addItem("르꼬끄 백팩", "128000", "  1", "-");
    }

    View.OnClickListener settingMoneyBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           // Message.information(Home.this, "예산 설정하기", "오늘 계획하신 예산을 입력해주세요. ");
           /* String money = Message.infoGetString(Home.this,"예산 설정하기","오늘의 계획 예산은? ");
            Toast.makeText(getApplicationContext(),money,Toast.LENGTH_LONG).show();*/
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
    public void setting_money_dialog_show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this,R.style.MyAlertDialogStyle);
        builder.setTitle("예산안 입력하기");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_money_dialog,null);
        builder.setView(view);

        final EditText budgetText = view.findViewById(R.id.budgetText);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               String budget = budgetText.getText().toString();
                if(budget == ""){
                    //아무 입력도 하지 않음.
                    Message.information(Home.this,"경고","입력하지 않으셨습니다.");
                    return;
                }else{
                    budgetItThis = budget;
                    Toast.makeText(getApplicationContext(),budgetItThis+"원 확인 ",Toast.LENGTH_LONG).show();
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
}
