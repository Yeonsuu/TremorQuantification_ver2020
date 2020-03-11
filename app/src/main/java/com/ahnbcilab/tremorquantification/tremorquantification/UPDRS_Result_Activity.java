package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class UPDRS_Result_Activity extends AppCompatActivity {

    String Clinic_ID, PatientName, timestamp;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_updrs;
    DatabaseReference database_patient;

    TextView u1score, u2score, u3score, u4score, u5score, u6score, u7score, u8score, u9score, u10score;
    TextView u11score, u12score, u13score, u14score, u15score, u16score, u17score, u18score, u19score, u20score;
    TextView u21score, u22score, u23score, u24score, u25score, u26score, u27score;

    String updrs_score;
    String taskscore;

    String updrs_num;

    boolean result_bool = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updrs__result_);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        timestamp = intent.getExtras().getString("timestamp");
        updrs_num = intent.getExtras().getString("updrs_num");
        taskscore = intent.getExtras().getString("taskscore");

        database_patient = firebaseDatabase.getReference("PatientList");
        database_updrs = database_patient.child(Clinic_ID).child("UPDRS List");

        u1score = (TextView) findViewById(R.id.u1score);
        u2score = (TextView) findViewById(R.id.u2score);
        u3score = (TextView) findViewById(R.id.u3score);
        u4score = (TextView) findViewById(R.id.u4score);
        u5score = (TextView) findViewById(R.id.u5score);
        u6score = (TextView) findViewById(R.id.u6score);
        u7score = (TextView) findViewById(R.id.u7score);
        u8score = (TextView) findViewById(R.id.u8score);
        u9score = (TextView) findViewById(R.id.u9score);
        u10score = (TextView) findViewById(R.id.u10score);
        u11score = (TextView) findViewById(R.id.u11score);
        u12score = (TextView) findViewById(R.id.u12score);
        u13score = (TextView) findViewById(R.id.u13score);
        u14score = (TextView) findViewById(R.id.u14score);
        u15score = (TextView) findViewById(R.id.u15score);
        u16score = (TextView) findViewById(R.id.u16score);
        u17score = (TextView) findViewById(R.id.u17score);
        u18score = (TextView) findViewById(R.id.u18score);
        u19score = (TextView) findViewById(R.id.u19score);
        u20score = (TextView) findViewById(R.id.u20score);
        u21score = (TextView) findViewById(R.id.u21score);
        u22score = (TextView) findViewById(R.id.u22score);
        u23score = (TextView) findViewById(R.id.u23score);
        u24score = (TextView) findViewById(R.id.u24score);
        u25score = (TextView) findViewById(R.id.u25score);
        u26score = (TextView) findViewById(R.id.u26score);
        u27score = (TextView) findViewById(R.id.u27score);



        TextView c_t = (TextView) findViewById(R.id.clinic_ID);
        c_t.setText(Clinic_ID);

        TextView p_t = (TextView) findViewById(R.id.patientName);
        p_t.setText(PatientName);

        String timestamp1 = timestamp.substring(2,4) + "." + timestamp.substring(5,7) + "." + timestamp.substring(8,10)+ "   " + timestamp.substring(timestamp.indexOf(" ")+1, timestamp.lastIndexOf(":"));

        TextView t_t = (TextView) findViewById(R.id.today_date);
        t_t.setText(timestamp1);

        TextView home = (TextView) findViewById(R.id.gotohome);

        Button share = (Button) findViewById(R.id.result_share);
        ConstraintLayout button = (ConstraintLayout) findViewById(R.id.button) ;
        final ConstraintLayout result = (ConstraintLayout) findViewById(R.id.result)  ;
        result.setVisibility(result_bool?View.VISIBLE:View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result_bool==false) {
                    result.setVisibility(View.VISIBLE);
                    result_bool=true ;
                }
                else{
                    result_bool=false ;
                    result.setVisibility(View.GONE);
                }
            }
        });

        database_updrs.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()){
                    String u1 = mData.child("UPDRS task").child("말하기").getValue() + "점";
                    String u2 = mData.child("UPDRS task").child("얼굴표정").getValue() + "점";
                    String u3 = mData.child("UPDRS task").child("안정시진정_얼굴과턱").getValue() + "점";
                    String u4 = mData.child("UPDRS task").child("안정시진정_오른쪽팔").getValue() + "점";
                    String u5 = mData.child("UPDRS task").child("안정시진정_왼쪽팔").getValue() + "점";
                    String u6 = mData.child("UPDRS task").child("안정시진정_오른쪽다리").getValue() + "점";
                    String u7 = mData.child("UPDRS task").child("안정시진정_왼쪽다리").getValue() + "점";
                    String u8 = mData.child("UPDRS task").child("운동또는자세성진정_오른쪽팔").getValue() + "점";
                    String u9 = mData.child("UPDRS task").child("운동또는자세성진정_왼쪽팔").getValue() + "점";
                    String u10 = mData.child("UPDRS task").child("경직_목").getValue() + "점";
                    String u11 = mData.child("UPDRS task").child("경직_오른쪽팔").getValue() + "점";
                    String u12 = mData.child("UPDRS task").child("경직_왼쪽팔").getValue() + "점";
                    String u13 = mData.child("UPDRS task").child("경직_오른쪽다리").getValue() + "점";
                    String u14 = mData.child("UPDRS task").child("경직_왼쪽다리").getValue() + "점";
                    String u15 = mData.child("UPDRS task").child("손가락벌렸다오므리기_오른쪽손").getValue() + "점";
                    String u16 = mData.child("UPDRS task").child("손가락벌렸다오므리기_왼쪽손").getValue() + "점";
                    String u17 = mData.child("UPDRS task").child("손운동_오른쪽손").getValue() + "점";
                    String u18 = mData.child("UPDRS task").child("손운동_왼쪽손").getValue() + "점";
                    String u19 = mData.child("UPDRS task").child("빠른손놀림_오른쪽손").getValue() + "점";
                    String u20 = mData.child("UPDRS task").child("빠른손놀림_왼쪽손").getValue() + "점";
                    String u21 = mData.child("UPDRS task").child("다리의민첩성_오른쪽다리").getValue() + "점";
                    String u22 = mData.child("UPDRS task").child("다리의민첩성_왼쪽다리").getValue() + "점";
                    String u23 = mData.child("UPDRS task").child("의자에서일어서기").getValue() + "점";
                    String u24 = mData.child("UPDRS task").child("서있는자세").getValue() + "점";
                    String u25 = mData.child("UPDRS task").child("걸음걸이").getValue() + "점";
                    String u26 = mData.child("UPDRS task").child("자세안정").getValue() + "점";
                    String u27 = mData.child("UPDRS task").child("느린행동").getValue() + "점";

                    u1score.setText(u1);
                    u2score.setText(u2);
                    u3score.setText(u3);
                    u4score.setText(u4);
                    u5score.setText(u5);
                    u6score.setText(u6);
                    u7score.setText(u7);
                    u8score.setText(u8);
                    u9score.setText(u9);
                    u10score.setText(u10);
                    u11score.setText(u11);
                    u12score.setText(u12);
                    u13score.setText(u13);
                    u14score.setText(u14);
                    u15score.setText(u15);
                    u16score.setText(u16);
                    u17score.setText(u17);
                    u18score.setText(u18);
                    u19score.setText(u19);
                    u20score.setText(u20);
                    u21score.setText(u21);
                    u22score.setText(u22);
                    u23score.setText(u23);
                    u24score.setText(u24);
                    u25score.setText(u25);
                    u26score.setText(u26);
                    u27score.setText(u27);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Button detail = (Button) findViewById(R.id.detail_result);

//        detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), UPDRS_detailResult.class);
//                intent.putExtra("ClinicID", Clinic_ID);
//                intent.putExtra("PatientName", PatientName);
//                intent.putExtra("timestamp", timestamp);
//                intent.putExtra("updrs_num", updrs_num);
//                startActivity(intent);
//            }
//        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "UPDRS");
                startActivity(intent);
            }
        });

        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GraphView graphView = (GraphView)findViewById(R.id.updrs_result_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0,0), true, 100);
                for (DataSnapshot mData : dataSnapshot.getChildren()){
                    Long number = mData.child("UPDRS List").getChildrenCount() ;
                    for(int i = 0 ; i<number ; i++){
                        list(i, mData, graphView, series, updrs_num) ;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series, final String updrs_num) {
        Query query = database_patient.child(Clinic_ID).child("UPDRS List").orderByChild("UPDRS_count").equalTo(i) ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String key = dataSnapshot1.getKey() ;
                    updrs_score = String.valueOf(mData.child("UPDRS List").child(key).child("UPDRS_score").getValue()) ;
                    series.appendData(new DataPoint(i+1,Integer.parseInt(updrs_score)), true, 100);
                    //series.setDrawDataPoints(true);
                    series.setColor(Color.parseColor("#78B5AA"));
                    graphView.removeAllSeries();
                    graphView.addSeries(series);
                    graphView.getViewport().setScalableY(true);
                    graphView.getViewport().setScrollableY(true);
                    graphView.getViewport().setMinX(0.0);
                    //graphView.getViewport().setMaxX(Integer.parseInt(updrs_num));

                    PieChartView pieChartView = (PieChartView)findViewById(R.id.updrs_result_chart);
                    List<SliceValue> pieData = new ArrayList<>();
                    pieData.add(new SliceValue(100-Integer.parseInt(taskscore), Color.parseColor("#E5E5E5")));
                    pieData.add(new SliceValue(Integer.parseInt(taskscore), Color.parseColor("#78B5AA")));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasCenterCircle(true).setCenterText1(taskscore);
                    pieChartData.setCenterText1Color(Color.parseColor("#78B5AA"));
                    pieChartData.setCenterText1FontSize(48);
                    pieChartView.setPieChartData(pieChartData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}