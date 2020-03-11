package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Personal_CRTS extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction tran;
    Personal_CRTS_Fragment personal_crts_fragment;

    String Clinic_ID;
    String PatientName;
    String taskDate;
    String taskTime;
    String taskScore;
    String timestamp;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_crts;
    DatabaseReference database_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__crts);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        taskDate = intent.getExtras().getString("taskDate");
        taskTime = intent.getExtras().getString("taskTime");
        taskScore = intent.getExtras().getString("taskScore");

        database_patient = firebaseDatabase.getReference("PatientList");
        database_crts = database_patient.child(Clinic_ID).child("CRTS List");

        personal_crts_fragment = new Personal_CRTS_Fragment(); //프래그먼트 객채셍성

        timestamp = taskDate + " " + taskTime;
        String title = taskDate + "     " + taskTime.substring(0, 5);

        TextView t_s = (TextView) findViewById(R.id.timestamp);
        TextView c_id = (TextView) findViewById(R.id.clinicalID);
        TextView p_name = (TextView) findViewById(R.id.patientName);
        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "CRTS");
                startActivity(intent);
            }
        });

        t_s.setText(title);
        c_id.setText(Clinic_ID);
        p_name.setText(PatientName);

        setFrag(0);
    }

    public void setFrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("timestamp", timestamp);
                personal_crts_fragment.setArguments(bundle1);
                tran.replace(R.id.personal_crts_frame, personal_crts_fragment);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
        }
    }

}
