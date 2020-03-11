package com.ahnbcilab.tremorquantification.tremorquantification;

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

public class CRTS_detailResult extends AppCompatActivity implements View.OnClickListener {

    String Clinic_ID, PatientName, timestamp, crts_num, spiral_result, line_result;

    TextView c1_1score, c1_2score, c1_3score;
    TextView c2_1score, c2_2score, c2_3score;
    TextView c3_1score, c3_2score, c3_3score;
    TextView c4_1score, c4_2score, c4_3score;
    TextView c5_1score, c5_2score, c5_3score;
    TextView c6_1score, c6_2score, c6_3score;
    TextView c7_1score, c7_2score, c7_3score;
    TextView c8_1score, c8_2score, c8_3score;

    TextView c9_1score, c9_2score, c9_3score;
    TextView c10_1score, c10_2score, c10_3score;
    TextView c11score, c12score, c13score, c14score, c15_1score, c15_2score;
    TextView c16score, c17score, c18score, c19score, c20score, c21score, c22score, c23score;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_crts;
    DatabaseReference database_patient;

    Button crts11_detail, crts12_detail, crts13_detail, crts14_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crts_detail_result);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        timestamp = intent.getExtras().getString("timestamp");
        crts_num = intent.getExtras().getString("crts_num");
        spiral_result = intent.getStringExtra("spiral_result");
        line_result = intent.getStringExtra("line_result");

        database_patient = firebaseDatabase.getReference("PatientList");
        database_crts = database_patient.child(Clinic_ID).child("CRTS List");

        graph();
        c1_1score = (TextView) findViewById(R.id.c1_1score);
        c1_2score = (TextView) findViewById(R.id.c1_2score);
        c1_3score = (TextView) findViewById(R.id.c1_3score);
        c2_1score = (TextView) findViewById(R.id.c2_1score);
        c2_2score = (TextView) findViewById(R.id.c2_2score);
        c2_3score = (TextView) findViewById(R.id.c2_3score);
        c3_1score = (TextView) findViewById(R.id.c3_1score);
        c3_2score = (TextView) findViewById(R.id.c3_2score);
        c3_3score = (TextView) findViewById(R.id.c3_3score);
        c4_1score = (TextView) findViewById(R.id.c4_1score);
        c4_2score = (TextView) findViewById(R.id.c4_2score);
        c4_3score = (TextView) findViewById(R.id.c4_3score);
        c5_1score = (TextView) findViewById(R.id.c5_1score);
        c5_2score = (TextView) findViewById(R.id.c5_2score);
        c5_3score = (TextView) findViewById(R.id.c5_3score);
        c6_1score = (TextView) findViewById(R.id.c6_1score);
        c6_2score = (TextView) findViewById(R.id.c6_2score);
        c6_3score = (TextView) findViewById(R.id.c6_3score);
        c7_1score = (TextView) findViewById(R.id.c7_1score);
        c7_2score = (TextView) findViewById(R.id.c7_2score);
        c7_3score = (TextView) findViewById(R.id.c7_3score);
        c8_1score = (TextView) findViewById(R.id.c8_1score);
        c8_2score = (TextView) findViewById(R.id.c8_2score);
        c8_3score = (TextView) findViewById(R.id.c8_3score);
        c9_1score = (TextView) findViewById(R.id.c9_1score);
        c9_2score = (TextView) findViewById(R.id.c9_2score);
        c9_3score = (TextView) findViewById(R.id.c9_3score);
        c10_1score = (TextView) findViewById(R.id.c10_1score);
        c10_2score = (TextView) findViewById(R.id.c10_2score);
        c10_3score = (TextView) findViewById(R.id.c10_3score);
        c11score = (TextView) findViewById(R.id.c11score);
        c12score = (TextView) findViewById(R.id.c12score);
        c13score = (TextView) findViewById(R.id.c13score);
        c14score = (TextView) findViewById(R.id.c14score);
        c15_1score = (TextView) findViewById(R.id.c15_1score);
        c15_2score = (TextView) findViewById(R.id.c15_2score);
        c16score = (TextView) findViewById(R.id.c16score);
        c17score = (TextView) findViewById(R.id.c17score);
        c18score = (TextView) findViewById(R.id.c18score);
        c19score = (TextView) findViewById(R.id.c19score);
        c20score = (TextView) findViewById(R.id.c20score);
        c21score = (TextView) findViewById(R.id.c21score);
        c22score = (TextView) findViewById(R.id.c22score);
        c23score = (TextView) findViewById(R.id.c23score);


        TextView c_t = (TextView) findViewById(R.id.clinic_ID);
        c_t.setText(Clinic_ID);
        crts11_detail = (Button) findViewById(R.id.crts11_detail);
        crts12_detail = (Button) findViewById(R.id.crts12_detail);
        crts13_detail = (Button) findViewById(R.id.crts13_detail);
        crts14_detail = (Button) findViewById(R.id.crts14_detail);

        crts11_detail.setOnClickListener(this);
        crts12_detail.setOnClickListener(this);
        crts13_detail.setOnClickListener(this);
        crts14_detail.setOnClickListener(this);

        TextView p_t = (TextView) findViewById(R.id.patientName);
        p_t.setText(PatientName);

        String timestamp1 = timestamp.substring(2, 4) + "." + timestamp.substring(5, 7) + "." + timestamp.substring(8, 10);

        TextView t_t = (TextView) findViewById(R.id.today_date);
        t_t.setText(timestamp1);

        database_crts.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String c1_1 = mData.child("CRTS task").child("얼굴_안정시").getValue() + "점";
                    c1_1score.setText(c1_1);
                    String c1_2 = mData.child("CRTS task").child("얼굴_자세시").getValue() + "점";
                    c1_2score.setText(c1_2);
                    String c1_3 = mData.child("CRTS task").child("얼굴_운동시").getValue() + "점";
                    c1_3score.setText(c1_3);
                    String c2_1 = mData.child("CRTS task").child("혀_안정시").getValue() + "점";
                    c2_1score.setText(c2_1);
                    String c2_2 = mData.child("CRTS task").child("혀_자세시").getValue() + "점";
                    c2_2score.setText(c2_2);
                    String c2_3 = mData.child("CRTS task").child("혀_운동시").getValue() + "점";
                    c2_3score.setText(c2_3);
                    String c3_1 = mData.child("CRTS task").child("목소리_안정시").getValue() + "점";
                    c3_1score.setText(c3_1);
                    String c3_2 = mData.child("CRTS task").child("목소리_자세시").getValue() + "점";
                    c3_2score.setText(c3_2);
                    String c3_3 = mData.child("CRTS task").child("목소리_운동시").getValue() + "점";
                    c3_3score.setText(c3_3);
                    String c4_1 = mData.child("CRTS task").child("머리_안정시").getValue() + "점";
                    c4_1score.setText(c4_1);
                    String c4_2 = mData.child("CRTS task").child("머리_자세시").getValue() + "점";
                    c4_2score.setText(c4_2);
                    String c4_3 = mData.child("CRTS task").child("머리_운동시").getValue() + "점";
                    c4_3score.setText(c4_3);
                    String c5_1 = mData.child("CRTS task").child("우측상지_안정시").getValue() + "점";
                    c5_1score.setText(c5_1);
                    String c5_2 = mData.child("CRTS task").child("우측상지_자세시").getValue() + "점";
                    c5_2score.setText(c5_2);
                    String c5_3 = mData.child("CRTS task").child("우측상지_운동시").getValue() + "점";
                    c5_3score.setText(c5_3);
                    String c6_1 = mData.child("CRTS task").child("좌측상지_안정시").getValue() + "점";
                    c6_1score.setText(c6_1);
                    String c6_2 = mData.child("CRTS task").child("좌측상지_자세시").getValue() + "점";
                    c6_2score.setText(c6_2);
                    String c6_3 = mData.child("CRTS task").child("좌측상지_운동시").getValue() + "점";
                    c6_3score.setText(c6_3);
                    String c7_1 = mData.child("CRTS task").child("체간_안정시").getValue() + "점";
                    c7_1score.setText(c7_1);
                    String c7_2 = mData.child("CRTS task").child("체간_자세시").getValue() + "점";
                    c7_2score.setText(c7_2);
                    String c7_3 = mData.child("CRTS task").child("체간_운동시").getValue() + "점";
                    c7_3score.setText(c7_3);
                    String c8_1 = mData.child("CRTS task").child("우측하지_안정시").getValue() + "점";
                    c8_1score.setText(c8_1);
                    String c8_2 = mData.child("CRTS task").child("우측하지_자세시").getValue() + "점";
                    c8_2score.setText(c8_2);
                    String c8_3 = mData.child("CRTS task").child("우측하지_운동시").getValue() + "점";
                    c8_3score.setText(c8_3);
                    String c9_1 = mData.child("CRTS task").child("좌측하지_안정시").getValue() + "점";
                    c9_1score.setText(c9_1);
                    String c9_2 = mData.child("CRTS task").child("좌측하지_자세시").getValue() + "점";
                    c9_2score.setText(c9_2);
                    String c9_3 = mData.child("CRTS task").child("좌측하지_운동시").getValue() + "점";
                    c9_3score.setText(c9_3);
                    String c10_1 = mData.child("CRTS task").child("기립성_안정시").getValue() + "점";
                    c10_1score.setText(c10_1);
                    String c10_2 = mData.child("CRTS task").child("기립성_자세시").getValue() + "점";
                    c10_2score.setText(c10_2);
                    String c10_3 = mData.child("CRTS task").child("기립성_운동시").getValue() + "점";
                    c10_3score.setText(c10_3);

                    String c11 = mData.child("CRTS task").child("글씨_쓰기").getValue() + "점";
                    c11score.setText(c11);
                    String c12 = mData.child("CRTS task").child("그림그리기1").getValue() + "점";
                    c12score.setText(c12);
                    String c13 = mData.child("CRTS task").child("그림그리기2").getValue() + "점";
                    c13score.setText(c13);
                    String c14 = mData.child("CRTS task").child("그림그리기3").getValue() + "점";
                    c14score.setText(c14);
                    String c15_1 = mData.child("CRTS task").child("물따르기").getValue() + "점";
                    c15_1score.setText(c15_1);
                    String c16 = mData.child("CRTS task").child("말하기").getValue() + "점";
                    c16score.setText(c16);
                    String c17 = mData.child("CRTS task").child("음식먹기").getValue() + "점";
                    c17score.setText(c17);
                    String c18 = mData.child("CRTS task").child("물을_입에_갖다대기").getValue() + "점";
                    c18score.setText(c18);
                    String c19 = mData.child("CRTS task").child("개인위생").getValue() + "점";
                    c19score.setText(c19);
                    String c20 = mData.child("CRTS task").child("옷입기").getValue() + "점";
                    c20score.setText(c20);
                    String c21 = mData.child("CRTS task").child("글쓰기").getValue() + "점";
                    c21score.setText(c21);
                    String c22 = mData.child("CRTS task").child("일하기").getValue() + "점";
                    c22score.setText(c22);
                    String c23 = mData.child("CRTS task").child("사회활동").getValue() + "점";
                    c23score.setText(c23);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button back = (Button) findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CRTS_Result_Activity.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("crts_num", crts_num);
                startActivity(intent);
            }
        });
    }

    private void graph() {

        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.crts_barchart);
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e2 = new BarEntry(1, new float[]{55f, 55f, 20f});
        int[] colorClassArray = new int[]{Color.GREEN, Color.YELLOW, Color.RED};
        valueSet1.add(v1e2);
        BarDataSet set1 = new BarDataSet(valueSet1, "today_score");
        set1.setColors(colorClassArray);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(set1);
//        YAxis left = chart.getAxisLeft();
//        left.setDrawLabels(false);
//        left.setDrawAxisLine(false);
//        left.setDrawGridLines(false);
//        chart.setDrawGridBackground(false);
//        chart.getLegend().setEnabled(false);
//        chart.setTouchEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawAxisLine(false);
        right.setDrawGridLines(false);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("");
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        chart.setData(data);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        // hide legend
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setAxisMaximum(172);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.animateY(1000);
        chart.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crts11_detail:
                Intent intent = new Intent(this, WritingResult.class);
                intent.putExtra("line_result", line_result);
                intent.putExtra("path1", "CRTS_detail");
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("crts11", c11score.getText().toString());
                startActivity(intent);
                break;

            case R.id.crts12_detail:
                Intent intent1 = new Intent(getApplicationContext(), CRTS_SpiralResult.class);
                intent1.putExtra("spiral_result", spiral_result);
                intent1.putExtra("line_result", line_result);
                intent1.putExtra("path", "CRTS_detail");
                intent1.putExtra("PatientName", PatientName);
                intent1.putExtra("Clinic_ID", Clinic_ID);
                intent1.putExtra("crts_num", crts_num);
                intent1.putExtra("crts12", c12score.getText().toString());
                startActivity(intent1);
                break;

            case R.id.crts13_detail:
                break;

            case R.id.crts14_detail:
                Intent intent4 = new Intent(getApplicationContext(), CRTS_LineResult.class);
                intent4.putExtra("line_result", line_result);
                intent4.putExtra("path", "CRTS_detail");
                intent4.putExtra("PatientName", PatientName);
                intent4.putExtra("Clinic_ID", Clinic_ID);
                intent4.putExtra("crts_num", crts_num);
                intent4.putExtra("crts14", c14score.getText().toString());
                startActivity(intent4);
                break;
        }
    }
}
