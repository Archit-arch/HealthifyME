package com.example.myproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CartBuyMedicineActivity extends AppCompatActivity {

    HashMap<String,String> item;
    ArrayList list;
    ListView lst;
    SimpleAdapter sa;
    TextView tvTotal;
    private String[][] packages = {};
    private DatePickerDialog datePickerDialog;
    Button btnCheckout, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy_medicine);


        btnCheckout = findViewById(R.id.button6);
        btnBack = findViewById(R.id.btn);
        tvTotal = findViewById(R.id.textView7);
        lst = findViewById(R.id.list1);


        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        Database db = new Database(getApplicationContext());
        float totalAmount = 0;
        ArrayList dbData = db.getCartData(username,"medicine");
        //Toast.makeText(this, ""+dbData, Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for (int i = 0;i<packages.length;i++){
            packages[i]= new String[5];
        }

        for (int i=0;i<dbData.size();i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0]= strData[0];
            packages[i][4]= "Cost : "+strData[1]+"/-";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost : "+totalAmount);

        list = new ArrayList();
        for(int i = 0;i<packages.length;i++ ){
            item= new HashMap<String,String>();
            item.put("line1",packages[i][0]);
            item.put("line2",packages[i][1]);
            item.put("line3",packages[i][2]);
            item.put("line4",packages[i][3]);
            item.put("line5",packages[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list, R.layout.activity_multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e,});
        lst.setAdapter(sa);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartBuyMedicineActivity.this,BuyMedicineActivity.class));

            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(new Intent(CartBuyMedicineActivity.this,BuyMedicineBookActivity.class));
                //it.putExtra("price",tvTotal.getText());
                startActivity(it);
            }
        });
    }

}