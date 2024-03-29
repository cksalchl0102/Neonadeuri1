package com.example.neonadeuri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

import com.example.neonadeuri.commomNeonaderi.ApiService;
import com.example.neonadeuri.commomNeonaderi.CustomTask;
import com.example.neonadeuri.commomNeonaderi.InCartAdapter;
import com.example.neonadeuri.commomNeonaderi.InCartItem;
import com.example.neonadeuri.commomNeonaderi.Message;
import com.example.neonadeuri.commomNeonaderi.ProductDTO;
import com.example.neonadeuri.commomNeonaderi.ProductTask;
import com.example.neonadeuri.commomNeonaderi.ProductsTask;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Home extends AppCompatActivity {

    Button buttonEventItem, buttonRecoItem;
    ImageView imageView = null;
    ListView listView;
    InCartAdapter inCartAdapter;
    public String minusPro = "";

    SeekBar seekBar = null;
    TextView curMoneyTextView = null;
    Button settingMoneyBtn = null;
    String budgetItThis; //시용자의 현재 금액 저장
    TextView budgetTextview; //현재 금액 보이기 View
    int curMoney = 0;
    EditText barcode;

    ImageButton refreshItemInCart = null;

    Button logoutButton = null;
    HashMap<String, String> nameChange = new HashMap<>();
    HashMap<String,String> dbNames=new HashMap<>();
    String[] productAll;
    String[] pi;
    String barcodeString;
    String barcodeString2;
    String productNameForCompare = "";

    String[] piForC;
    String[] ApiForC;
    String[] BpiForC;

    TextView hansungProductName;
    TextView hansungProductPrice;
    TextView hansungCompareResult;
    TextView AProductName;
    TextView AProductPrice;
    TextView ACompareResult;
    TextView BProductName;
    TextView BProductPrice;
    TextView BCompareResult;

    View focusView = null;
    String[] tmp;

    LayoutInflater inflater;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent(); //데이터 수신
        String userPhoneNum = intent.getExtras().getString("phoneNumber");
        String userName = intent.getExtras().getString("name");
        setTitle(userName + ", " + userPhoneNum + "접속");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*buttonEventItem = findViewById(R.id.buttonEventItemCall);
        buttonRecoItem = findViewById(R.id.buttonRecoItemCall);
        imageView = findViewById(R.id.imageView);*/
/*
        buttonEventItem.setOnClickListener(imageChangeButtonEvent);
        buttonRecoItem.setOnClickListener(imageChangeButtonEvent);*/
        tmp = new String[]{"00000001", "00000005", "00000005", "00000024", "00000045", "00000045", "00000045"};

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
                focusBarcode();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
/*

        inCartAdapter.addItem("iPhone XS", "10", "1", "- ");
        inCartAdapter.addItem("팜스 사과 3kg", "10", "1", "할인중");
        inCartAdapter.addItem("임금님표 이천쌀", "10", "1", "-");
        inCartAdapter.addItem("홍삼정", "10", "1", "-");
        inCartAdapter.addItem("이니스프리 선크림", "10", "2", "1+1 행사중");
        inCartAdapter.addItem("르꼬끄 백팩", "10", "1", "-");
*/
       /* inCartAdapter.addItem(" 허니 버터 아몬드", "1200", "3", "30% discount");
        inCartAdapter.addItem("카카오프렌즈 아이스 텀블러", "12000", "2", "1+1");
        inCartAdapter.addItem("임금님표  이천쌀", "40000", "1", "20% discount");
        inCartAdapter.addItem("종가집 보쌈 무말랭이", "4000", "3", "1+1");
        inCartAdapter.addItem("로지텍G 게이밍 무선 마우스", "107000", "1", "-");
*/

        barcode = findViewById(R.id.barcode);
        barcode.setInputType(0);
        /*barcode.setFocusableInTouchMode(true);
        barcode.requestFocus();*/
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(barcode.getWindowToken(), 0);
        forFirstBudget();
        /*barcode.setFocusableInTouchMode(true);
        barcode.requestFocus();
        barcode.setInputType(0);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);*/
        //checkProduct("88010193");


        //compareProducts(productNameForCompare);

        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //if (barcode.getText().toString().length() > 7) {
                //  barcodeString = barcode.getText().toString();
                //checkProduct(barcodeString);
                // Log.i("chanmi","pi[0] = "+pi[0]);
                //productNameForCompare = pi[1].toString();
                //compareProducts(productNameForCompare);

                  /*  if (compareProducts(barcodeString)) {
                        settingCompareTable();
                    }else{
                        Toast.makeText(getApplicationContext(),"가격비교 불가",Toast.LENGTH_LONG).show();
                    }
*/
                // Log.i("chanmi", barcodeString);
                    /*if(checkProduct(barcodeString)){
                        inCartAdapter.addItem(pi[1],pi[2],"1",pi[3]);
                        inCartAdapter.notifyDataSetChanged();*/
                //}

                //}
            }

            @Override
            public void afterTextChanged(Editable editable) {
                barcode = findViewById(R.id.barcode);
                Log.d("gg", barcode.getText().toString());
                if (barcode.getText().toString().length() == 8) {
                    barcodeString = barcode.getText().toString();
                    barcodeString2 = barcode.getText().toString();
                    if (checkProduct(barcodeString)) { //db에서 바코드 추출

                        checkProductNumber(barcodeString);

                        getName(pi[1]);//영어-한글 로 번역.
                        compareProducts(productNameForCompare);//3사 비교.
                        settingCompareTable(piForC, ApiForC, BpiForC);//3사 비교 결과
                        if (checkItemNumber(getName(pi[1]))) {
                            //일치하는게 있다면 true;
                            plusItemNumber(getName(pi[1]));
                            focusBarcode();
                            if (focusView.equals(R.id.barcode)) {
                                Log.i("focus", "in old product : success");
                            } else {
                                Log.i("focus", "in old product : Fail");
                            }
                        } else {
                            //새로운 상품이라면....
                            listAddItem(pi);
                            inCartAdapter.notifyDataSetChanged();
                            focusBarcode();
                           /* barcode.setFocusableInTouchMode(true);
                            barcode.requestFocus();*/

                            if (focusView.equals(R.id.barcode)) {
                                Log.i("focus", "in new product : success");
                            } else {
                                Log.i("focus", "in new product : Fail");
                            }

                        }
                        focusBarcode();
                    } else {
                        focusBarcode();
                        Toast.makeText(Home.this, "등록된 상품이 아닙니다.", Toast.LENGTH_LONG).show();
                    }

                    Log.i("chanmi", barcodeString);


                }
                Log.i("chanmi", "in afterTextChanged barcodeEditText : " + barcode.getText());

            }
        });

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.in_cart_list_item, null);
        final ImageButton imageButton = view.findViewById(R.id.list_item_minus_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"minus",Toast.LENGTH_SHORT).show();
            }
        });

        hansungProductName = findViewById(R.id.hansungMart_product_name_editText);
        hansungProductPrice = findViewById(R.id.hansungMart_product_price_editText);
        hansungCompareResult = findViewById(R.id.hansungMart_product_result_editText);
        AProductName = findViewById(R.id.A_product_name_editText);
        AProductPrice = findViewById(R.id.A_product_price_editText);
        ACompareResult = findViewById(R.id.A_product_result_editText);

        BProductName = findViewById(R.id.B_product_name_editText);
        BProductPrice = findViewById(R.id.B_product_price_editText);
        BCompareResult = findViewById(R.id.B_product_result_editText);
        //settingCompareTable(piForC, ApiForC, BpiForC);
        //listAddItem(pi);
        /*refreshItemInCart.performClick();
        sampleingTable();*/
        listView.setOnItemClickListener(itemListener);

        chectProductAll();
    }
    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String name =  ((TextView)view.findViewById(R.id.itemName)).getText().toString();

            Log.i("dingni", "Home name = " + name);

            String productName = getKey(nameChange, name);
            Log.i("dingni", name + productName);

            productNameForCompare = productName;   //productNameForCompare은 HashMap을 거치기 전 'product@' 형식 이어야 함

            compareProducts(productNameForCompare); //3사 비교
            settingCompareTable(piForC, ApiForC, BpiForC);  //3사 결과 비교값 테이블 만들기*/
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.list_item_minus_imageButton);
            switch (view.getId()){
                case R.id.list_item_minus_imageButton:
                    Toast.makeText(Home.this,"minus버튼 눌림",Toast.LENGTH_SHORT).show();
                    break;
            }
            if(view.getId()==R.id.list_item_minus_imageButton){
                Toast.makeText(Home.this,"minus버튼 눌림",Toast.LENGTH_SHORT).show();
            }

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Home.this,"minus버튼 눌림",Toast.LENGTH_SHORT).show();
                   /* String name2 =  ((TextView)view.findViewById(R.id.itemName)).getText().toString();
                    checkProductNumberMinus(name2);*/
                }
            });
        }
    };
    //HashMap에서 value로 key값 찾기
    public String getKey(HashMap<String, String> map, String name) {
        Set key = map.keySet();
        String keyName = "";

        for (Iterator iterator = key.iterator(); iterator.hasNext();) {
            keyName = (String) iterator.next();
            if(map.get(keyName).equals(name))
                return keyName;
        }
        return keyName;
    }

    /*
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView textView = view.findViewById(R.id.itemName);
            String name = (String) textView.getText();
            Toast.makeText(Home.this, "삭제 : " + name, Toast.LENGTH_LONG).show();
            Log.i("chanmi", " imageButton.setOnClickListener");
        }
    };*/


    //로그아웃
    View.OnClickListener logoutFuncListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logoutButton:
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
                    break;
                /*case R.id.in_cart_list:
                    Toast.makeText(Home.this, "listView Click", Toast.LENGTH_LONG).show();
                    Log.d("chanmi","list Click");

                    break;*/
            }
        }

    };

    //카드 안에 있는 물품 새로 고침
    View.OnClickListener refreshItemInCartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {/*
         *//*checkProduct("00000001");
            checkProduct("00000005");
            checkProduct("00000005");
            checkProduct("00000024");
            checkProduct("00000045");
            checkProduct("00000045");
            checkProduct("00000045");*//*
            for (int i = 0; i < tmp.length; i++) {
                if (checkProduct(tmp[i])) {
                    compareProducts(productNameForCompare);
                    settingCompareTable(piForC, ApiForC, BpiForC);
                    if (checkItemNumber(pi[1])) {
                        //일치하는게 있다면 true;
                        plusItemNumber(pi[1]);
                        focusBarcode();
                        if (focusView.equals(R.id.barcode)) {
                            Log.i("focus", "in old product : success");
                        } else {
                            Log.i("focus", "in old product : Fail");
                        }
                    } else {
                        //새로운 상품이라면....
                        listAddItem(pi);
                        inCartAdapter.notifyDataSetChanged();
                        focusBarcode();
                           *//* barcode.setFocusableInTouchMode(true);
                            barcode.requestFocus();*//*

                        if (focusView.equals(R.id.barcode)) {
                            Log.i("focus", "in new product : success");
                        } else {
                            Log.i("focus", "in new product : Fail");
                        }

                    }
                    focusBarcode();
                } else {
                    focusBarcode();
                    Toast.makeText(Home.this, "등록된 상품이 아닙니다.", Toast.LENGTH_LONG).show();
                }
            }*/
            calculateTotalMoney();
            Log.i("chanmi", String.valueOf(calculateTotalMoney()));
        }
    };

    View.OnClickListener settingMoneyBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setting_money_dialog_show();
        }
    };
/*
    View.OnClickListener imageChangeButtonEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          *//*  switch (view.getId()) {
                case R.id.buttonEventItemCall:
                    imageView.setImageResource(R.drawable.bulgogi);
                    break;
                case R.id.buttonRecoItemCall:
                    imageView.setImageResource(R.drawable.bulgogi);
                    break;
            }*//*
            if (view.getId() == R.id.buttonEventItemCall) {
                imageView.setImageResource(R.drawable.bulgogi);
            } else
                imageView.setImageResource(R.drawable.bulgogi);

            focusBarcode();
        }
    };*/

    private void seekBarSetText() {
        int progress = seekBar.getProgress();
        int max = seekBar.getMax();
        int offset = seekBar.getThumbOffset();
        float percent = ((float) progress) / (float) max;
        int width = seekBar.getWidth() - 2 * offset;
        int answer = ((int) (width * percent + offset - curMoneyTextView.getWidth() / 2));
        curMoneyTextView.setX(answer);
        //focusBarcode();
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
        focusBarcode();
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

    //딩니가 새로 짜는 부분
    public String getName(String pname) {   //HashMap 이용하여 한글 이름으로 변경
        Log.i("chanmi", "현재 상품 이름 : " + pname);
        Log.i("chanmi", "상품 한글 이름 : " + nameChange.get(pname));
        return nameChange.get(pname);
    }
    public String getDBNAme(String honeName){



        return null;
    }

    //딩니가 새로 짜는 부분
    public boolean chectProductAll() {  //모든 DB 불러와서 HashMap에 저장
        String resultAll = "";
        try {
            resultAll = new ProductTask().execute("-", "getProductsAll").get();
            productAll = resultAll.split("/");

            Log.i("chanmi", "resultAll = " + resultAll);
            if (productAll[0].equals("getProductOK")) {
                for (int i = 1; i < productAll.length; i++) {
                    Resources res = getResources();
                    String[] p_array = res.getStringArray(R.array.products);
                    nameChange.put(productAll[i], p_array[i - 1]);
                    Log.i("chanmi :", productAll[i] + "=" + nameChange.get(productAll[i]));
                }

                barcodeString = "";
                barcode.setText("");
               /* barcode.setFocusableInTouchMode(true);
                barcode.requestFocus();*/
                Log.i("chanmi", "모든 DB 불러들이기 성공");
                return true;
            } else {
                Message.information(Home.this, "알림", "DB 불러오기 실패");
                Log.i("chanmi", "모든 DB 불러오기 실패!@!@##!#@!");
                barcodeString = "";
                barcode.setText("");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean checkProduct(String searchbarcode) {
        /*
        Log.e("chanmi", searchbarcode);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://113.198.84.24:8080/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        service.neonaduriProduct(searchbarcode, "getProducts")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {

                        try {
                            Log.i("chanmi", response.body().string());
                            pi = response.body().string().split("/");
                            Log.i("chanmi", pi[0]);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

        String resultt = "";
        try {
            //  sendMsg = "productId=" + strings[0] + "&type=" + strings[1];
            resultt = new ProductTask().execute(searchbarcode, "getProducts").get();
            //pi[1] = name;
            //returns = "getProductOK" + "\t" + name + "\t" + price + "\t" + info;
            pi = resultt.split("/");
          /*  Log.i("chanmi", "checkProduct result=" + resultt);
            Log.i("chanmi", "checkProduct pi=" + pi[0] + ", " + pi[1]);
            Log.i("chanmi", "ProductTask()..execute() OK ");
            Log.i("chanmi", pi[0]);*/


            if (pi[0].equals("getProductOK")) {
                //리스트에 아이템 추가

                //비교대상 아이템 이름 저장.
                productNameForCompare = null;
                productNameForCompare = pi[1];


                //barcodeString = "";
                //barcode.setText("");
               /* barcode.setFocusableInTouchMode(true);
                barcode.requestFocus();*/
                Log.i("chanmi", "barcode :" + barcode.getText().toString());
                return true;
            } else {
                Message.information(Home.this, "알림", "등록된 상품이 아닙니다.");

                //barcode.setText("");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return false;
    }

    public boolean compareProducts(String productName) {

        String result2 = "";
        String result3 = "";
        String result4 = "";

        try {
            // sendMsg = "pname=" + strings[0] + "&type=" + strings[1];
            result2 = new ProductsTask().execute(productName, "productsCompareFromHansung").get();
            result3 = new ProductsTask().execute(productName, "productsCompareFromA").get();
            result4 = new ProductsTask().execute(productName, "productsCompareFromB").get();

            // returns="loadHansungProductOK"+"\t"+hansungName+"\t"+hansungPrice;
            piForC = result2.split("\t");
            if (piForC.length == 3) {
                Log.i("chanmi", "piForC : " + piForC[0] + ", " + piForC[1] + ", " + piForC[2]);
            }
            ApiForC = result3.split("\t");
            if (ApiForC.length == 3) {
                Log.i("chanmi", "ApiForC : " + ApiForC[0] + ", " + ApiForC[1] + ", " + ApiForC[2]);
            }
            BpiForC = result4.split("\t");
            if (BpiForC.length == 3) {
                Log.i("chanmi", "BpiForC : " + BpiForC[0] + ", " + BpiForC[1] + ", " + BpiForC[2]);
            }

            if (piForC[0].equals("loadHansungProductOK")) {
                focusBarcode();
                return true;
            } else if (piForC[0].equals("loadHansungProductFail")) {
                focusBarcode();
                Message.information(Home.this, "알림", "등록된 상품이 아닙니다.");
                return false;
            } else {
                focusBarcode();
                return false;
            }

           /* Log.i("chanmi","piForC : "+ piForC[0]+piForC[1]+piForC[2]);
            Log.i("chanmi","ApiForC : "+ ApiForC[0]+ApiForC[1]+ApiForC[2]);
            Log.i("chanmi","BpiForC : "+ BpiForC[0]+BpiForC[1]+BpiForC[2]);*/
            /*if (piForC[0].equals("loadHansungProductOK")) {
                return true;
            } else {
                Message.information(Home.this, "알림", "등록된 상품이 아닙니다.");
            }*/
/*

            ApiForC = result3.split("\t");
            BpiForC = result4.split("\t");
*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        focusBarcode();
        return false;
    }

    public void settingCompareTable(String[] hs, String[] as, String[] bs) {
        int h = 0, a = 0, b = 0;
        //"1+1"  "20% discount"  "30% discount"
        h = Integer.parseInt(hs[2]);
        a = Integer.parseInt(as[2]);
        b = Integer.parseInt(bs[2]);

        int big = 0, mid = 0, small = 0;

        String hsName = getName(hs[1]);

        big = (h > a) && (h > b) ? h : (b > a ? b : a);
        small = (a > h) && (b > h) ? h : (a > b ? b : a);
        mid = (h > a) ? ((h > b) ? ((a > b) ? a : b) : h) : ((a > b) ? ((h > b) ? h : b) : a);


        hansungProductName.setText(hsName);
        hansungProductPrice.setText(hs[2]);
        AProductName.setText(hsName);
        AProductPrice.setText(as[2]);
        BProductName.setText(hsName);
        BProductPrice.setText(bs[2]);

        if (big == mid && mid == small) {
            hansungCompareResult.setText("가장 싸다.");
            ACompareResult.setText("가장 싸다.");
            BCompareResult.setText("가장 싸다.");
        } else if (small == mid && mid != big) {
            if (big == h) {
                hansungCompareResult.setText("가장 비싸다.");
                ACompareResult.setText("가장 싸다.");
                BCompareResult.setText("가장 싸다.");
            } else if (big == a) {
                hansungCompareResult.setText("가장 싸다.");
                ACompareResult.setText("가장 비싸다.");
                BCompareResult.setText("가장 싸다.");
            } else {
                hansungCompareResult.setText("가장 싸다.");
                ACompareResult.setText("가장 싸다.");
                BCompareResult.setText("가장 비싸다.");
            }
        } else if (big == mid && mid != small) {
            if (small == h) {
                hansungCompareResult.setText("가장 싸다.");
                ACompareResult.setText("가장 비싸다.");
                BCompareResult.setText("가장 비싸다.");
            } else if (small == a) {
                hansungCompareResult.setText("가장 비싸다.");
                ACompareResult.setText("가장 싸다.");
                BCompareResult.setText("가장 비싸다.");
            } else if (small == b) {
                hansungCompareResult.setText("가장 비싸다.");
                ACompareResult.setText("가장 비싸다.");
                BCompareResult.setText("가장 싸다.");
            }

        } else if (big != mid && mid != small) {
            if (big == h) {
                if (mid == a) {
                    hansungCompareResult.setText("가장 비싸다.");
                    ACompareResult.setText("중간");
                    BCompareResult.setText("가장 싸다.");
                } else {
                    hansungCompareResult.setText("가장 비싸다.");
                    ACompareResult.setText("가장 싸다.");
                    BCompareResult.setText("중간");
                }
            } else if (mid == h) {
                if (big == a) {
                    hansungCompareResult.setText("중간");
                    ACompareResult.setText("가장 비싸다.");
                    BCompareResult.setText("가장 싸다.");
                } else {
                    hansungCompareResult.setText("중간");
                    ACompareResult.setText("가장 싸다.");
                    BCompareResult.setText("가장 비싸다.");
                }
            } else {//small == h
                if (big == a) {
                    hansungCompareResult.setText("가장 싸다");
                    ACompareResult.setText("가장 비싸다.");
                    BCompareResult.setText("중간");
                } else {
                    hansungCompareResult.setText("중간");
                    ACompareResult.setText("가장 싸다.");
                    BCompareResult.setText("가장 비싸다.");
                }
            }

        }

    }

    /*
        public void settingCompareTable() {
            int h = 0, a = 0, b = 0;
            int big, mid, small;


            // returns = result + "\t" + result2 + "\t" + result3 + "\t" + hansungName + "\t" + hansungPrice + "\t" + AName
            // + "\t" + APrice + "\t" + BName + "\t" + BPrice;*//*

        h = Integer.parseInt(compareResult[4]);
        a = Integer.parseInt(compareResult[6]);
        b = Integer.parseInt(compareResult[8]);

        big = (h > a) && (h > b) ? h : (b > a ? b : a);
        small = (a > h) && (b > h) ? h : (a > b ? b : a);
        mid = (h > a) ? ((h > b) ? ((a > b) ? a : b) : h) : ((a > b) ? ((h > b) ? h : b) : a);

        hansungProductName.setText(compareResult[3]);
        hansungProductPrice.setText(compareResult[4]);
        AProductName.setText(compareResult[5]);
        AProductPrice.setText(compareResult[6]);
        BProductName.setText(compareResult[7]);
        BProductPrice.setText(compareResult[8]);

        if (big == mid && mid == small) {
            hansungCompareResult.setText("가장 싸다.");
            ACompareResult.setText("가장 싸다.");
            BCompareResult.setText("가장 싸다.");
        } else if (small == mid && mid != big) {
            if (big == h) {
                hansungCompareResult.setText("가장 비싸다.");
                ACompareResult.setText("가장 싸다.");
                BCompareResult.setText("가장 싸다.");
            } else if (big == a) {
                hansungCompareResult.setText("가장 싸다.");
                ACompareResult.setText("가장 비싸다.");
                BCompareResult.setText("가장 싸다.");
            } else {
                hansungCompareResult.setText("가장 싸다.");
                ACompareResult.setText("가장 싸다.");
                BCompareResult.setText("가장 비싸다.");
            }
        } else if (big == mid && mid != small) {
            if (small == h) {
                hansungCompareResult.setText("가장 싸다.");
                ACompareResult.setText("가장 비싸다.");
                BCompareResult.setText("가장 비싸다.");
            } else if (small == a) {
                hansungCompareResult.setText("가장 비싸다.");
                ACompareResult.setText("가장 싸다.");
                BCompareResult.setText("가장 비싸다.");
            } else if (small == b) {
                hansungCompareResult.setText("가장 비싸다.");
                ACompareResult.setText("가장 비싸다.");
                BCompareResult.setText("가장 싸다.");
            }

        } else if (big != mid && mid != small) {
            if (big == h) {
                if (mid == a) {
                    hansungCompareResult.setText("가장 비싸다.");
                    ACompareResult.setText("중간");
                    BCompareResult.setText("가장 싸다.");
                } else {
                    hansungCompareResult.setText("가장 비싸다.");
                    ACompareResult.setText("가장 싸다.");
                    BCompareResult.setText("중간");
                }
            } else if (mid == h) {
                if (big == a) {
                    hansungCompareResult.setText("중간");
                    ACompareResult.setText("가장 비싸다.");
                    BCompareResult.setText("가장 싸다.");
                } else {
                    hansungCompareResult.setText("중간");
                    ACompareResult.setText("가장 싸다.");
                    BCompareResult.setText("가장 비싸다.");
                }
            } else {//small == h
                if (big == a) {
                    hansungCompareResult.setText("가장 싸다");
                    ACompareResult.setText("가장 비싸다.");
                    BCompareResult.setText("중간");
                } else {
                    hansungCompareResult.setText("중간");
                    ACompareResult.setText("가장 싸다.");
                    BCompareResult.setText("가장 비싸다.");
                }
            }

        }


    }*/
    public void listAddItem(String[] args) {
        String name, price, num, info;
        name = getName(args[1]);
        price = args[2];
        if (args[3].equals("null"))
            info = "-";
        else
            info = args[3];
        num = "1";
        InCartItem inCartItem = new InCartItem();
        inCartItem.setItemName(name);
        inCartItem.setItemPrice(price);

        inCartItem.setItemInfo(info);
        inCartItem.setItemNumber(num);

        //public void addItem(String name, String price, String number, String info)
        inCartAdapter.addItem(name, price, num, info);
        refreshItemInCart.performClick();
      /*  clearBarcodeText();
        focusBarcode();*/
        focusBarcode();

        barcode.setFocusableInTouchMode(true);
        barcode.requestFocus();
        refreshItemInCart.performClick();
    }

    public void clearBarcodeText() {
        barcode.setText(null);
    }

    public void youAddItemQ(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.MyAlertDialogStyle);
        builder.setTitle("상품 추가");
        builder.setMessage(name + "상품을 추가하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    boolean checkItemNumber(String name) {
        for (int i = 0; i < inCartAdapter.getCount(); i++) {
            if (name.equals(inCartAdapter.getName(i))) {
                //일치하는게 있다면.
                return true;
            }
        }
        return false;
    }


    public void plusItemNumber(String name) {
        for (int i = 0; i < inCartAdapter.getCount(); i++) {
            if (name.equals(inCartAdapter.getName(i))) {
                inCartAdapter.plusItemNumber(i);
            }
        }
        inCartAdapter.notifyDataSetChanged();
        refreshItemInCart.performClick();
        focusBarcode();
    }

    public void forFirstBudget() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.MyAlertDialogStyle);
        builder.setTitle("예산안 입력하기");
        builder.setMessage("먼저 예산안을 입력해주세요");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setting_money_dialog_show();
            }
        });
        builder.show();
        focusBarcode();
    }

    public void focusBarcode() {
        barcodeString = "";
        barcode.setText("");
        barcode.setFocusable(true);
        barcode.setFocusableInTouchMode(true);
        barcode.requestFocus();
        barcode.setInputType(0);
        focusView = getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        imm.hideSoftInputFromWindow(barcode.getWindowToken(), 0);
    }

    public int calculateTotalMoney() {
        curMoney = 0;
        inCartAdapter.notifyDataSetInvalidated();
        inCartAdapter.notifyDataSetChanged();
        Log.i("chanmi", String.valueOf(inCartAdapter.getCount()));

        for (int i = 0; i < inCartAdapter.getCount(); i++) {
            int cntNum = Integer.parseInt(inCartAdapter.getNumber(i));
            int curPrice = Integer.parseInt(inCartAdapter.getPrice(i));
            String curInfo = inCartAdapter.getInfo(i);
            if (curInfo.equals("1+1")) {
                if (cntNum % 2 == 1) {
                    cntNum /= 2;
                    cntNum++;
                } else
                    cntNum /= 2;
                curMoney = curMoney + curPrice * cntNum;
            } else if (curInfo.equals("20% discount")) {
                curMoney = curMoney + (int) (curPrice * 0.8);
            } else if (curInfo.equals("30% discount")) {
                curMoney = curMoney + (int) (curPrice * 0.7);
            } else {
                curMoney += curPrice * cntNum;
            }

            Log.i("chanmi", i + "번째 : " + curPrice);
            Log.i("chanmi", "현재 총 금액 : " + curMoney);
        }
        curMoneyTextView.setText(String.valueOf(curMoney) + "원");
        Log.i("chanmi", String.valueOf(curMoney));
        seekBar.setProgress(curMoney);
        clearBarcodeText();
        focusBarcode();

        Log.i("chanmi", "in refreshItemInCartListener : " + barcode.getText());

        return curMoney;
    }

    public void sampleingTable() {
        String name = "허니 버터 아몬드";
        double hsPrice = 1200 * 0.7;
        double aPrice = 1200, bPrice = 1200;
        int hsPriceResult = (int) hsPrice;
        int aPriceResult = (int) aPrice;
        int bPriceResult = (int) bPrice;
        String hsResult = "가장 싸다";
        String abResult = "가장 비싸다.";

        hansungProductName.setText(name);
        AProductName.setText(name);
        BProductName.setText(name);

        hansungProductPrice.setText(String.valueOf(hsPriceResult + "원"));
        AProductPrice.setText(String.valueOf(aPriceResult) + "원");
        BProductPrice.setText(String.valueOf(bPriceResult) + "원");


        hansungCompareResult.setText(hsResult);
        ACompareResult.setText(abResult);
        BCompareResult.setText(abResult);

    }

    public boolean checkProductNumber(String bbarcode) {
        String resultNumberType = "";
        try {
            resultNumberType = new ProductTask().execute(bbarcode, "setProductNumberHS").get();
            Log.i("chanmi", "resultNumberType : " + resultNumberType);
            if (resultNumberType.equals("setProductNumber_HS success!")) {
                Log.i("chanmi", "재고 업데이트 성공!");
                barcode.setText(null);
                barcodeString = "";
                return true;
            } else {
                Log.i("chanmi", "재고 업데이트 실패!");
                barcode.setText(null);
                barcodeString = "";
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        barcode.setText(null);
        barcodeString = "";
        return false;
    }
    public boolean checkProductNumberMinus(String productName){
        String resultProductMinus = "";
        try{
            resultProductMinus = new ProductsTask().execute(productName,"setProductNumberMinusHS").get();
            if(resultProductMinus.equals("setProductNumberMinus_HS success!")){
                Log.i("chanmi","재고 관리 Minus 업데이트 성공");
            }else{
                Log.i("chanmi","재고 관리 Minus 업데이트 실패");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
