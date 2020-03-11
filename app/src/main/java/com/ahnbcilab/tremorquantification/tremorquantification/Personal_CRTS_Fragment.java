package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.Adapters.CRTS_Adapter;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerAdapter;
import com.ahnbcilab.tremorquantification.data.CRTS_Result_Data;
import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataChild;
import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataParent;
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

public class Personal_CRTS_Fragment extends Fragment {

    String Clinic_ID;
    String timestamp;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_crts;
    DatabaseReference database_patient;
    HorizontalBarChart chart;


    View view;

    CRTS_Adapter adapter;
    ArrayList<CRTS_Result_DataParent> parentData = new ArrayList<>();
    ArrayList<CRTS_Result_DataChild> childData = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    String draw1, draw2, draw3, write;
    TextView crts1, crts2, crts3, crts4, crts5, crts6, crts7, crts8, crts9, crts10, crts11, crts12, crts13, crts14, crts15;
    TextView crts16, crts17, crts18, crts19, crts20, crts21, crts22, crts23, crts24, crts25, crts26, crts27, crts28, crts29, crts30;
    TextView crts31, crts32, crts33, crts34, crts35, crts36, crts37, crts38, crts39, crts40;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //이전 Activity value 받아오기
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            timestamp = getArguments().getString("timestamp");
        }

        // 초기 화면
        view = inflater.inflate(R.layout.personal_crts_fragment, container, false);

        database_patient = firebaseDatabase.getReference("PatientList");
        database_crts = database_patient.child(Clinic_ID).child("CRTS List");
        init();
        graph();

        crts1 = (TextView) view.findViewById(R.id.crts1);
        crts2 = (TextView) view.findViewById(R.id.crts2);
        crts3 = (TextView) view.findViewById(R.id.crts3);
        crts4 = (TextView) view.findViewById(R.id.crts4);
        crts5 = (TextView) view.findViewById(R.id.crts5);
        crts6 = (TextView) view.findViewById(R.id.crts6);
        crts7 = (TextView) view.findViewById(R.id.crts7);
        crts8 = (TextView) view.findViewById(R.id.crts8);
        crts9 = (TextView) view.findViewById(R.id.crts9);
        crts10 = (TextView) view.findViewById(R.id.crts10);
        crts11 = (TextView) view.findViewById(R.id.crts11);
        crts12 = (TextView) view.findViewById(R.id.crts12);
        crts13 = (TextView) view.findViewById(R.id.crts13);
        crts14 = (TextView) view.findViewById(R.id.crts14);
        crts15 = (TextView) view.findViewById(R.id.crts15);
        crts16 = (TextView) view.findViewById(R.id.crts16);
        crts17 = (TextView) view.findViewById(R.id.crts17);
        crts18 = (TextView) view.findViewById(R.id.crts18);
        crts19 = (TextView) view.findViewById(R.id.crts19);
        crts20 = (TextView) view.findViewById(R.id.crts20);
        crts21 = (TextView) view.findViewById(R.id.crts21);
        crts22 = (TextView) view.findViewById(R.id.crts22);
        crts23 = (TextView) view.findViewById(R.id.crts23);
        crts24 = (TextView) view.findViewById(R.id.crts24);
        crts25 = (TextView) view.findViewById(R.id.crts25);
        crts26 = (TextView) view.findViewById(R.id.crts26);
        crts27 = (TextView) view.findViewById(R.id.crts27);
        crts28 = (TextView) view.findViewById(R.id.crts28);
        crts29 = (TextView) view.findViewById(R.id.crts29);
        crts30 = (TextView) view.findViewById(R.id.crts30);
        crts31 = (TextView) view.findViewById(R.id.crts31);
        crts32 = (TextView) view.findViewById(R.id.crts32);
        crts33 = (TextView) view.findViewById(R.id.crts33);
        crts34 = (TextView) view.findViewById(R.id.crts34);
        crts35 = (TextView) view.findViewById(R.id.crts35);
        crts36 = (TextView) view.findViewById(R.id.crts36);
        crts37 = (TextView) view.findViewById(R.id.crts37);
        crts38 = (TextView) view.findViewById(R.id.crts38);
        crts39 = (TextView) view.findViewById(R.id.crts39);
        crts40 = (TextView) view.findViewById(R.id.crts40);
        Log.v("CRTS task", "CRTS task" + timestamp);
        database_crts.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String c1 = mData.child("CRTS task").child("얼굴_안정시").getValue() + "점";
                    String c2 = mData.child("CRTS task").child("얼굴_자세시").getValue() + "점";
                    String c3 = mData.child("CRTS task").child("얼굴_운동시").getValue() + "점";
                    String c4 = mData.child("CRTS task").child("혀_안정시").getValue() + "점";
                    String c5 = mData.child("CRTS task").child("혀_자세시").getValue() + "점";
                    String c6 = mData.child("CRTS task").child("혀_운동시").getValue() + "점";
                    String c7 = mData.child("CRTS task").child("목소리_안정시").getValue() + "점";
                    String c8 = mData.child("CRTS task").child("목소리_자세시").getValue() + "점";
                    String c9 = mData.child("CRTS task").child("목소리_운동시").getValue() + "점";
                    String c10 = mData.child("CRTS task").child("머리_안정시").getValue() + "점";
                    String c11 = mData.child("CRTS task").child("머리_자세시").getValue() + "점";
                    String c12 = mData.child("CRTS task").child("머리_운동시").getValue() + "점";
                    String c13 = mData.child("CRTS task").child("우측상지_안정시").getValue() + "점";
                    String c14 = mData.child("CRTS task").child("우측상지_자세시").getValue() + "점";
                    String c15 = mData.child("CRTS task").child("우측상지_운동시").getValue() + "점";
                    String c16 = mData.child("CRTS task").child("좌측상지_안정시").getValue() + "점";
                    String c17 = mData.child("CRTS task").child("좌측상지_자세시").getValue() + "점";
                    String c18 = mData.child("CRTS task").child("좌측상지_운동시").getValue() + "점";
                    String c19 = mData.child("CRTS task").child("체간_안정시").getValue() + "점";
                    String c20 = mData.child("CRTS task").child("체간_자세시").getValue() + "점";
                    String c21 = mData.child("CRTS task").child("체간_운동시").getValue() + "점";
                    String c22 = mData.child("CRTS task").child("우측하지_안정시").getValue() + "점";
                    String c23 = mData.child("CRTS task").child("우측하지_자세시").getValue() + "점";
                    String c24 = mData.child("CRTS task").child("우측하지_운동시").getValue() + "점";
                    String c25 = mData.child("CRTS task").child("좌측하지_안정시").getValue() + "점";
                    String c26 = mData.child("CRTS task").child("좌측하지_자세시").getValue() + "점";
                    String c27 = mData.child("CRTS task").child("좌측하지_운동시").getValue() + "점";
                    String c28 = mData.child("CRTS task").child("기립성_안정시").getValue() + "점";
                    String c29 = mData.child("CRTS task").child("기립성_자세시").getValue() + "점";
                    String c30 = mData.child("CRTS task").child("기립성_운동시").getValue() + "점";
                    String c31 = mData.child("CRTS task").child("물따르기").getValue() + "점";
                    String c32 = mData.child("CRTS task").child("물따르기").getValue() + "점";
                    String c33 = mData.child("CRTS task").child("말하기").getValue() + "점";
                    String c34 = mData.child("CRTS task").child("음식먹기").getValue() + "점";
                    String c35 = mData.child("CRTS task").child("물을_입에_갖다대기").getValue() + "점";
                    String c36 = mData.child("CRTS task").child("개인위생").getValue() + "점";
                    String c37 = mData.child("CRTS task").child("옷입기").getValue() + "점";
                    String c38 = mData.child("CRTS task").child("글쓰기").getValue() + "점";
                    String c39 = mData.child("CRTS task").child("일하기").getValue() + "점";
                    String c40 = mData.child("CRTS task").child("사회활동").getValue() + "점";
                    draw1 = mData.child("CRTS task").child("그림그리기1").getValue() + "점";
                    draw2 = mData.child("CRTS task").child("그림그리기2").getValue() + "점";
                    draw3 = mData.child("CRTS task").child("그림그리기3").getValue() + "점";
                    write = mData.child("CRTS task").child("글씨_쓰기").getValue() + "점";

                    ArrayList<CRTS_Result_DataParent> parents = new ArrayList<>();

                    ArrayList<CRTS_Result_DataChild> child_11 = new ArrayList<>();
                    child_11.add(new CRTS_Result_DataChild(4, 1, 1, 3, 1, R.drawable.spiral));
                    CRTS_Result_DataParent parent_11 = new CRTS_Result_DataParent("11. 글쓰기", child_11, write);
                    parents.add(parent_11);

                    ArrayList<CRTS_Result_DataChild> child_12 = new ArrayList<>();
                    child_12.add(new CRTS_Result_DataChild(5, 2, 1, 3, 1, R.drawable.spiral));
                    CRTS_Result_DataParent parent_12 = new CRTS_Result_DataParent("12. 그리기", child_12, draw1);
                    parents.add(parent_12);

                    ArrayList<CRTS_Result_DataChild> child_13 = new ArrayList<>();
                    child_13.add(new CRTS_Result_DataChild(5, 7, 2, 3, 1, R.drawable.spiral));
                    CRTS_Result_DataParent parent_13 = new CRTS_Result_DataParent("13. 그리기", child_13, draw2);
                    parents.add(parent_13);

                    ArrayList<CRTS_Result_DataChild> child_14 = new ArrayList<>();
                    child_14.add(new CRTS_Result_DataChild(4, 1, 1, 3, 1, R.drawable.spiral));
                    CRTS_Result_DataParent parent_14 = new CRTS_Result_DataParent("14. 그리기", child_14, draw3);
                    parents.add(parent_14);


                    // adapter의 값이 변경되었다는 것을 알려줍니다.
                    adapter = new CRTS_Adapter(parents);
                    recyclerView.setAdapter(adapter);

                    crts1.setText(c1);
                    crts2.setText(c2);
                    crts3.setText(c3);
                    crts4.setText(c4);
                    crts5.setText(c5);
                    crts6.setText(c6);
                    crts7.setText(c7);
                    crts8.setText(c8);
                    crts9.setText(c9);
                    crts10.setText(c10);
                    crts11.setText(c11);
                    crts12.setText(c12);
                    crts13.setText(c13);
                    crts14.setText(c14);
                    crts15.setText(c15);
                    crts16.setText(c16);
                    crts17.setText(c17);
                    crts18.setText(c18);
                    crts19.setText(c19);
                    crts20.setText(c20);
                    crts21.setText(c21);
                    crts22.setText(c22);
                    crts23.setText(c23);
                    crts24.setText(c24);
                    crts25.setText(c25);
                    crts26.setText(c26);
                    crts27.setText(c27);
                    crts28.setText(c28);
                    crts29.setText(c29);
                    crts30.setText(c30);
                    crts31.setText(c31);
                    crts32.setText(c32);
                    crts33.setText(c33);
                    crts34.setText(c34);
                    crts35.setText(c35);
                    crts36.setText(c36);
                    crts37.setText(c37);
                    crts38.setText(c38);
                    crts39.setText(c39);
                    crts40.setText(c40);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        init();
        return view;

    }

    private void graph() {
        chart = (HorizontalBarChart) view.findViewById(R.id.crts_barchart);
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e2 = new BarEntry(1, new float[]{55f, 55f, 20f});
        int[] colorClassArray = new int[]{Color.GREEN, Color.YELLOW, Color.RED};
        valueSet1.add(v1e2);
        BarDataSet set1 = new BarDataSet(valueSet1, "today_score");
        set1.setColors(colorClassArray);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data1 = new BarData(set1);
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
        chart.setData(data1);

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

    private void init() {
        recyclerView = view.findViewById(R.id.crts_list);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

    }

}