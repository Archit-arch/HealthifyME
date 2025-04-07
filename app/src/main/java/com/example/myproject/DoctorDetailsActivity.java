package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private String[][] doctor_details1={
            {"Doctor Name : Ajit Saste","Hospital Address : Pipri","Exp : 5yrs","Mobile No:9090909090","600"},
            {"Doctor Name : Prasad Pawar","Hospital Address : Nigdi","Exp : 15yrs","Mobile No:8080808080","900"},
            {"Doctor Name : Swapnil Deshmukh","Hospital Address : Pune","Exp : 8yrs","Mobile No:4932052345","400"},
            {"Doctor Name : Deepak Kale","Hospital Address : Chinchwad","Exp : 6yrs","Mobile No:7483748323","500"},
            {"Doctor Name : Ashok Panda","Hospital Address : Katraj","Exp : 7yrs","Mobile No:7878787878","800"}
    };
    private String[][] doctor_details2={
            {"Doctor Name : Rishavh Raut","Hospital Address : Pipri","Exp : 5yrs","Mobile No:9090909090","600"},
            {"Doctor Name : jayant Pawar","Hospital Address : Nigdi","Exp : 15yrs","Mobile No:8080808080","900"},
            {"Doctor Name : Swapnil Kulkarni","Hospital Address : Pune","Exp : 8yrs","Mobile No:4932052345","400"},
            {"Doctor Name : Pralhad Kale","Hospital Address : Chinchwad","Exp : 6yrs","Mobile No:7483748323","500"},
            {"Doctor Name : Ashok Panda","Hospital Address : Katraj","Exp : 7yrs","Mobile No:7878787878","800"}
    };
    private String[][] doctor_details3={
            {"Doctor Name : Ajit Saste","Hospital Address : Pipri","Exp : 5yrs","Mobile No:9090909090","600"},
            {"Doctor Name : Prasad Pawar","Hospital Address : Nigdi","Exp : 15yrs","Mobile No:8080808080","900"},
            {"Doctor Name : Swapnil Deshmukh","Hospital Address : Pune","Exp : 8yrs","Mobile No:4932052345","400"},
            {"Doctor Name : Deepak Kale","Hospital Address : Chinchwad","Exp : 6yrs","Mobile No:7483748323","500"},
            {"Doctor Name : Ashok Panda","Hospital Address : Katraj","Exp : 7yrs","Mobile No:7878787878","800"}
    };
    private String[][] doctor_details4={
            {"Doctor Name : Ajit Saste","Hospital Address : Pipri","Exp : 5yrs","Mobile No:9090909090","600"},
            {"Doctor Name : Prasad Pawar","Hospital Address : Nigdi","Exp : 15yrs","Mobile No:8080808080","900"},
            {"Doctor Name : Swapnil Deshmukh","Hospital Address : Pune","Exp : 8yrs","Mobile No:4932052345","400"},
            {"Doctor Name : Deepak Kale","Hospital Address : Chinchwad","Exp : 6yrs","Mobile No:7483748323","500"},
            {"Doctor Name : Ashok Panda","Hospital Address : Katraj","Exp : 7yrs","Mobile No:7878787878","800"}
    };
    private String[][] doctor_details5={
            {"Doctor Name : Ajit Saste","Hospital Address : Pipri","Exp : 5yrs","Mobile No:9090909090","600"},
            {"Doctor Name : Prasad Pawar","Hospital Address : Nigdi","Exp : 15yrs","Mobile No:8080808080","900"},
            {"Doctor Name : Swapnil Deshmukh","Hospital Address : Pune","Exp : 8yrs","Mobile No:4932052345","400"},
            {"Doctor Name : Deepak Kale","Hospital Address : Chinchwad","Exp : 6yrs","Mobile No:7483748323","500"},
            {"Doctor Name : Ashok Panda","Hospital Address : Katraj","Exp : 7yrs","Mobile No:7878787878","800"}
    };

    TextView tv;
    Button btn;
    String[][] doctor_details ={};
    HashMap<String,String> item;
    ArrayList list ;
    SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv =findViewById(R.id.textViewODTitle);
        btn=findViewById(R.id.button);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if (title.compareTo("Cardiologists")==0) {
            doctor_details = doctor_details1;
        } else
        if (title.compareTo("Dietician")==0) {
            doctor_details = doctor_details2;
        } else
        if (title.compareTo("Dentist")==0) {
            doctor_details = doctor_details3;
        } else
        if (title.compareTo("Surgeon")==0) {
            doctor_details = doctor_details4;
        } else
            doctor_details = doctor_details5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class));
            }
        });
        list = new ArrayList();
        for (int i =0;i<doctor_details.length;i++){
            item = new HashMap<String,String>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees : "+doctor_details[i][4]+"/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,R.layout.activity_multi_lines,new String[]{"line1","line2","line3","line4","line5",},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e,}
        );
        ListView lst = findViewById(R.id.list1);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String contactFull = doctor_details[i][3]; // "Mobile No:9090909090"
                String contact = contactFull.split(":")[1]; // Get just the number

                String fees = doctor_details[i][4]; // This is already just the number

                Intent it = new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[i][0]);
                it.putExtra("text3", doctor_details[i][1]);
                it.putExtra("text4", contact);
                it.putExtra("text5", fees);
                startActivity(it);
            }
        });
    }
}