package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LabTestActivity extends AppCompatActivity {
    private String[][] packages ={
            {"Package 1 : Full Body Checkup","","","","999"},
            {"Package 2 : Blood Glucose Fasting","","","","299"},
            {"Package 3 : Covid Antibody - Igs","","","","899"},
            {"Package 4 : Thyroid Check","","","","499"},
            {"Package 5 : Immunity Check","","","","699"}
    };
    private String[] package_details ={
            "Blood Glucose Fasting\n"+
                    "Complete Hemogram\n"+
                    "HbA1c\n"+
                    "Iron Studies\n"+
                    "Kidney Function Test\n"+
                    "LDH Lactate Dehydrogenase, Serum\n"+
                    "Lipid Profile\n"+
                    "Liver Function Test\n",
            "Blood Glucose Fasting",
            "Covid-19 Antibody - IgG",
            "Thyroid Profile - Total (T3, T4, TSH Ultra-Sensitive)",
            "Complete Hemogram\n"+
                    "CPR (C Reactive Protein) Quantitative, Serum\n"+
                    "Iron Studies\n"+
                    "Kidney Function Test\n"+
                    "Vitamin D Total-25 Hydroxy\n"+
                    "Liver Function Test\n"+
                    "Lipid Profile"
    };

    Button btnGoToCart, btnBack;
    ListView listView;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        btnGoToCart = findViewById(R.id.buttonLTDAddToCart);
        btnBack = findViewById(R.id.buttonBMBack);
        listView = findViewById(R.id.listViewDD);

        // Initialize the list
        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            list.add(packages[i][0] + "\nTotal Cost : " + packages[i][4] + "/-");
        }

        // Set up the adapter for the ListView
        CustomAdapter adapter = new CustomAdapter(list);
        listView.setAdapter(adapter);

        // Set up item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });

        // Button listeners
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));
            }
        });
    }

    // Custom Adapter to handle list item views
    private class CustomAdapter extends android.widget.BaseAdapter {
        private ArrayList<String> data;

        public CustomAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(data.get(position));
            return convertView;
        }
    }
}
