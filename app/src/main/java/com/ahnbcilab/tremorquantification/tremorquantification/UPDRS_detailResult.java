package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UPDRS_detailResult extends AppCompatActivity {

    String Clinic_ID, PatientName, timestamp, updrs_num;

    TextView u1score, u2score, u3score, u4score, u5score, u6score, u7score, u8score, u9score, u10score;
    TextView u11score, u12score, u13score, u14score, u15score, u16score, u17score, u18score, u19score, u20score;
    TextView u21score, u22score, u23score, u24score, u25score, u26score, u27score;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_updrs;
    DatabaseReference database_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updrs_detail_result);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        timestamp = intent.getExtras().getString("timestamp");
        updrs_num = intent.getExtras().getString("updrs_num");

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


        database_updrs.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
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

        Button back = (Button) findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UPDRS_Result_Activity.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("updrs_num", updrs_num);
                startActivity(intent);
            }
        });

    }
}
