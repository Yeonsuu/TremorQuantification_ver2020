package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.CRTS;
import com.ahnbcilab.tremorquantification.data.CRTS_Data;
import com.ahnbcilab.tremorquantification.data.CRTS_Score;
import com.ahnbcilab.tremorquantification.data.LineData;
import com.ahnbcilab.tremorquantification.data.SpiralData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CRTSActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fm;
    FragmentTransaction tran;
    CRTS_parta_Fragment frag1;
    CRTS_partb_Fragment frag2;
    CRTS_partc_Fragment frag3;
    CRTS_partb_1_Fragment frag4;

    Button quit_button;
    TextView page_text;

    String Clinic_ID;
    String PatientName;
    String path;
    String crts_num;
    String line_downurl;
    String crts_right_spiral_downurl;
    String crts_left_spiral_downurl;
    String writing_downurl;
    double[] spiral_result;
    double[] left_spiral_result;
    double[] line_result;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databasePatientList;
    DatabaseReference databaseCRTS;

    int crts_score;
    boolean bool = true;
    int taskno = 0;
    String scrts_count = "";

    int c1_1, c1_2, c1_3;
    int c2_1, c2_2, c2_3;
    int c3_1, c3_2, c3_3;
    int c4_1, c4_2, c4_3;
    int c5_1, c5_2, c5_3;
    int c6_1, c6_2, c6_3;
    int c7_1, c7_2, c7_3;
    int c8_1, c8_2, c8_3;
    int c9_1, c9_2, c9_3;
    int c10_1, c10_2, c10_3;

    int crts11, crts12, crts13, crts14;
    ArrayList<Integer> crtsa ;
    int c11;
    int c12;
    int c13;
    int c14;
    int c15;
    int c16, c17, c18, c19;
    int c20, c21, c22, c23;
    int left, right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crts);

        // intent 값 받아오기
        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("Clinic_ID");
        PatientName = intent.getExtras().getString("PatientName");
        path = intent.getExtras().getString("path");
        crts_num = intent.getExtras().getString("crts_num");
        left = intent.getExtras().getInt("left");
        int num = 0;
        databasePatientList = firebaseDatabase.getReference("PatientList");
        databaseCRTS = databasePatientList.child(Clinic_ID).child("CRTS List");
        databaseCRTS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskno = Integer.parseInt(String.valueOf(dataSnapshot.getChildrenCount()));
                // TODO: show the count in the UI
                if (taskno < 10) {
                    scrts_count = "0" + taskno;
                } else {
                    scrts_count = Integer.toString(taskno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView c = (TextView) findViewById(R.id.clinic_id);
        c.setText(Clinic_ID);
        TextView p = (TextView) findViewById(R.id.patient_name);
        p.setText(PatientName);

        quit_button = (Button) findViewById(R.id.crts_quit_button);

        quit_button.setOnClickListener(this);

        frag1 = new CRTS_parta_Fragment(); //프래그먼트 객채셍성
        frag2 = new CRTS_partb_Fragment(); //프래그먼트 객채셍성
        frag3 = new CRTS_partc_Fragment(); //프래그먼트 객채셍성
        frag4 = new CRTS_partb_1_Fragment();

        if (path.equals("CRTS")) {
            crts11 = intent.getIntExtra("crts11", -1);
            crts12 = intent.getIntExtra("crts12", -1);
            crts13 = intent.getIntExtra("crts13", -1);
            crts14 = intent.getIntExtra("crts14", -1);
            spiral_result = intent.getDoubleArrayExtra("spiral_result");
            line_result =  intent.getDoubleArrayExtra("line_result");
            left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result") ;
            line_downurl = intent.getStringExtra("line_downurl");
            crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl");
            crts_left_spiral_downurl = intent.getStringExtra("crts_left_spiral_downurl");
            writing_downurl = intent.getStringExtra("writing_downurl");
            Log.v("5/14 (2)", writing_downurl);
            Log.v("llloooggg crts", "crts11 = " + crts11 + "crts12 = " + crts12 + "crts14 = " + crts14);
            setFrag(1);

        } else {
            Log.v("crts_num", "setfrag(0)");
            setFrag(0);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);

        Clinic_ID = intent.getExtras().getString("Clinic_ID");
        PatientName = intent.getExtras().getString("PatientName");
        path = intent.getExtras().getString("path");
        crts_num = intent.getExtras().getString("crts_num");
        frag2 = new CRTS_partb_Fragment() ;
        if (path.equals("CRTS")) {
            crts11 = intent.getIntExtra("crts11", -1);
            crts12 = intent.getIntExtra("crts12", -1);
            crts13 = intent.getIntExtra("crts13", -1);
            crts14 = intent.getIntExtra("crts14", -1);
            spiral_result = intent.getDoubleArrayExtra("spiral_result");
            line_result =  intent.getDoubleArrayExtra("line_result");
            left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result") ;
            line_downurl = intent.getStringExtra("line_downurl");
            crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl");
            crts_left_spiral_downurl = intent.getStringExtra("crts_left_spiral_downurl");
            writing_downurl = intent.getStringExtra("writing_downurl");
            setFrag(1);
            Log.v("crts_num", "CRTS_Activity_CRTS" + path + crts11);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crts_quit_button:
                alertDisplay();
                break;
        }
    }

    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                frag1.setArguments(bundle1);
                tran.replace(R.id.crts_test, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                Log.v("CRTS_Activity is", "CRTS_Activity is :2" + crts11);
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("path", "CRTS");
                bundle2.putInt("crts11", crts11);
                bundle2.putInt("crts12", crts12);
                bundle2.putInt("crts13", crts13);
                bundle2.putInt("crts14", crts14);
                bundle2.putDoubleArray("spiral_result",spiral_result);
                bundle2.putDoubleArray("left_spiral_result",left_spiral_result);
                bundle2.putDoubleArray("line_result", line_result);
                bundle2.putString("line_downurl", line_downurl);
                bundle2.putString("crts_right_spiral_downurl", crts_right_spiral_downurl);
                bundle2.putString("crts_left_spiral_downurl", crts_left_spiral_downurl);
                bundle2.putString("writing_downurl", writing_downurl);
                bundle2.putInt("left", left);
                frag2.setArguments(bundle2);
                tran.replace(R.id.crts_test, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commitAllowingStateLoss();
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putInt("left", left);
                frag3.setArguments(bundle3);
                tran.replace(R.id.crts_test, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commitAllowingStateLoss();
                break;

            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("Clinic_ID", Clinic_ID);
                bundle4.putString("PatientName", PatientName);
                bundle4.putString("path", "main");
                frag4.setArguments(bundle4);
                tran.replace(R.id.crts_test, frag4);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commitAllowingStateLoss();
                break;
            case 4:
                Bundle bundle5 = new Bundle();
                bundle5.putString("Clinic_ID", Clinic_ID);
                bundle5.putString("PatientName", PatientName);
                bundle5.putIntegerArrayList("crtsa_num",crtsa);
                bundle5.putInt("pre", 1) ;
                frag1.setArguments(bundle5);
                tran.replace(R.id.crts_test, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                //crts_parta_fragment.pager.setCurrentItem(2);
                break;
        }
    }

    public void frgment1() {
        crtsa = new ArrayList<>() ;
        CRTS_parta_Fragment crts_parta_fragment = (CRTS_parta_Fragment) getSupportFragmentManager().findFragmentById(R.id.crts_test);
        c1_1 = crts_parta_fragment.c1_1;
        crtsa.add(c1_1) ;
        c2_1 = crts_parta_fragment.c2_1;
        crtsa.add(c2_1);
        c3_1 = crts_parta_fragment.c3_1;
        crtsa.add(c3_1);
        c4_1 = crts_parta_fragment.c4_1;
        crtsa.add(c4_1);
        c5_1 = crts_parta_fragment.c5_1;
        crtsa.add(c5_1);
        c6_1 = crts_parta_fragment.c6_1;
        crtsa.add(c6_1);
        c7_1 = crts_parta_fragment.c7_1;
        crtsa.add(c7_1);
        c8_1 = crts_parta_fragment.c8_1;
        crtsa.add(c8_1);
        c9_1 = crts_parta_fragment.c9_1;
        crtsa.add(c9_1);
        c10_1 = crts_parta_fragment.c10_1;
        crtsa.add(c10_1);

        c1_2 = crts_parta_fragment.c1_2;
        crtsa.add(c1_2) ;
        c2_2 = crts_parta_fragment.c2_2;
        crtsa.add(c2_2);
        c3_2 = crts_parta_fragment.c3_2;
        crtsa.add(c3_2);
        c4_2 = crts_parta_fragment.c4_2;
        crtsa.add(c4_2);
        c5_2 = crts_parta_fragment.c5_2;
        crtsa.add(c5_2);
        c6_2 = crts_parta_fragment.c6_2;
        crtsa.add(c6_2);
        c7_2 = crts_parta_fragment.c7_2;
        crtsa.add(c7_2);
        c8_2 = crts_parta_fragment.c8_2;
        crtsa.add(c8_2);
        c9_2 = crts_parta_fragment.c9_2;
        crtsa.add(c9_2);
        c10_2 = crts_parta_fragment.c10_2;
        crtsa.add(c10_2);

        c1_3 = crts_parta_fragment.c1_3;
        crtsa.add(c1_3);
        c2_3 = crts_parta_fragment.c2_3;
        crtsa.add(c2_3);
        c3_3 = crts_parta_fragment.c3_3;
        crtsa.add(c3_3);
        c4_3 = crts_parta_fragment.c4_3;
        crtsa.add(c4_3);
        c5_3 = crts_parta_fragment.c5_3;
        crtsa.add(c5_3);
        c6_3 = crts_parta_fragment.c6_3;
        crtsa.add(c6_3);
        c7_3 = crts_parta_fragment.c7_3;
        crtsa.add(c7_3);
        c8_3 = crts_parta_fragment.c8_3;
        crtsa.add(c8_3);
        c9_3 = crts_parta_fragment.c9_3;
        crtsa.add(c9_3);
        c10_3 = crts_parta_fragment.c10_3;
        crtsa.add(c10_3);

        Log.v("CRTSActivity", "CRTS :: " + crtsa) ;
        if (path.equals("main")) {
            setFrag(3);
        } else {
            setFrag(1);
        }
    }

    public void frgment2() {
        CRTS_partb_Fragment crts_partb_fragment = (CRTS_partb_Fragment) getSupportFragmentManager().findFragmentById(R.id.crts_test);
        bool = true;
        if (crts_partb_fragment.crts11_0.isChecked()) {
            c11 = 0;
        } else if (crts_partb_fragment.crts11_1.isChecked()) {
            c11 = 1;
        } else if (crts_partb_fragment.crts11_2.isChecked()) {
            c11 = 2;
        } else if (crts_partb_fragment.crts11_3.isChecked()) {
            c11 = 3;
        } else if (crts_partb_fragment.crts11_4.isChecked()) {
            c11 = 4;
        } else {
            bool = false;
        }

        if (crts_partb_fragment.crts12_0.isChecked()) {
            c12 = 0;
        } else if (crts_partb_fragment.crts12_1.isChecked()) {
            c12 = 1;
        } else if (crts_partb_fragment.crts12_2.isChecked()) {
            c12 = 2;
        } else if (crts_partb_fragment.crts12_3.isChecked()) {
            c12 = 3;
        } else if (crts_partb_fragment.crts12_4.isChecked()) {
            c12 = 4;
        } else {
            bool = false;
        }

        if (crts_partb_fragment.crts13_0.isChecked()) {
            c13 = 0;
        } else if (crts_partb_fragment.crts13_1.isChecked()) {
            c13 = 1;
        } else if (crts_partb_fragment.crts13_2.isChecked()) {
            c13 = 2;
        } else if (crts_partb_fragment.crts13_3.isChecked()) {
            c13 = 3;
        } else if (crts_partb_fragment.crts13_4.isChecked()) {
            c13 = 4;
        } else {
            bool = false;
        }

        if (crts_partb_fragment.crts14_0.isChecked()) {
            c14 = 0;
        } else if (crts_partb_fragment.crts14_1.isChecked()) {
            c14 = 1;
        } else if (crts_partb_fragment.crts14_2.isChecked()) {
            c14 = 2;
        } else if (crts_partb_fragment.crts14_3.isChecked()) {
            c14 = 3;
        } else if (crts_partb_fragment.crts14_4.isChecked()) {
            c14 = 4;
        } else {
            bool = false;
        }

        if (crts_partb_fragment.crts15_0.isChecked()) {
            c15 = 0;
        } else if (crts_partb_fragment.crts15_1.isChecked()) {
            c15 = 1;
        } else if (crts_partb_fragment.crts15_2.isChecked()) {
            c15 = 2;
        } else if (crts_partb_fragment.crts15_3.isChecked()) {
            c15 = 3;
        } else if (crts_partb_fragment.crts15_4.isChecked()) {
            c15 = 4;
        } else {
            bool = false;
        }

        if (bool == false) {
            /*
            int rg1_index = updrs_test_fragment1.rg1.getCheckedRadioButtonId();
            if(rg1_index == -1){
                updrs_test_fragment1.updrs_1_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_1_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg1.setBackgroundResource(0);
            }*/
            bool = true;
            Toast myToast = Toast.makeText(this.getApplicationContext(),"모든 문항을 확인 해주세요.", Toast.LENGTH_LONG);
            myToast.show();
        } else {
            setFrag(2);
        }
    }

    public void fragment3() {
        CRTS_partc_Fragment crts_partc_fragment = (CRTS_partc_Fragment) getSupportFragmentManager().findFragmentById(R.id.crts_test);
        bool = true;
        if (crts_partc_fragment.crts16_0.isChecked()) {
            c16 = 0;
        } else if (crts_partc_fragment.crts16_1.isChecked()) {
            c16 = 1;
        } else if (crts_partc_fragment.crts16_2.isChecked()) {
            c16 = 2;
        } else if (crts_partc_fragment.crts16_3.isChecked()) {
            c16 = 3;
        } else if (crts_partc_fragment.crts16_4.isChecked()) {
            c16 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts17_0.isChecked()) {
            c17 = 0;
        } else if (crts_partc_fragment.crts17_1.isChecked()) {
            c17 = 1;
        } else if (crts_partc_fragment.crts17_2.isChecked()) {
            c17 = 2;
        } else if (crts_partc_fragment.crts17_3.isChecked()) {
            c17 = 3;
        } else if (crts_partc_fragment.crts17_4.isChecked()) {
            c17 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts18_0.isChecked()) {
            c18 = 0;
        } else if (crts_partc_fragment.crts18_1.isChecked()) {
            c18 = 1;
        } else if (crts_partc_fragment.crts18_2.isChecked()) {
            c18 = 2;
        } else if (crts_partc_fragment.crts18_3.isChecked()) {
            c18 = 3;
        } else if (crts_partc_fragment.crts18_4.isChecked()) {
            c18 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts19_0.isChecked()) {
            c19 = 0;
        } else if (crts_partc_fragment.crts19_1.isChecked()) {
            c19 = 1;
        } else if (crts_partc_fragment.crts19_2.isChecked()) {
            c19 = 2;
        } else if (crts_partc_fragment.crts19_3.isChecked()) {
            c19 = 3;
        } else if (crts_partc_fragment.crts19_4.isChecked()) {
            c19 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts20_0.isChecked()) {
            c20 = 0;
        } else if (crts_partc_fragment.crts20_1.isChecked()) {
            c20 = 1;
        } else if (crts_partc_fragment.crts20_2.isChecked()) {
            c20 = 2;
        } else if (crts_partc_fragment.crts20_3.isChecked()) {
            c20 = 3;
        } else if (crts_partc_fragment.crts20_4.isChecked()) {
            c20 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts21_0.isChecked()) {
            c21 = 0;
        } else if (crts_partc_fragment.crts21_1.isChecked()) {
            c21 = 1;
        } else if (crts_partc_fragment.crts21_2.isChecked()) {
            c21 = 2;
        } else if (crts_partc_fragment.crts21_3.isChecked()) {
            c21 = 3;
        } else if (crts_partc_fragment.crts21_4.isChecked()) {
            c21 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts22_0.isChecked()) {
            c22 = 0;
        } else if (crts_partc_fragment.crts22_1.isChecked()) {
            c22 = 1;
        } else if (crts_partc_fragment.crts22_2.isChecked()) {
            c22 = 2;
        } else if (crts_partc_fragment.crts22_3.isChecked()) {
            c22 = 3;
        } else if (crts_partc_fragment.crts22_4.isChecked()) {
            c22 = 4;
        } else {
            bool = false;
        }

        if (crts_partc_fragment.crts23_0.isChecked()) {
            c23 = 0;
        } else if (crts_partc_fragment.crts23_1.isChecked()) {
            c23 = 1;
        } else if (crts_partc_fragment.crts23_2.isChecked()) {
            c23 = 2;
        } else if (crts_partc_fragment.crts23_3.isChecked()) {
            c23 = 3;
        } else if (crts_partc_fragment.crts23_4.isChecked()) {
            c23 = 4;
        } else {
            bool = false;
        }


        if (bool == false) {

            bool = true;
            Toast myToast = Toast.makeText(this.getApplicationContext(),"모든 문항을 확인 해주세요.", Toast.LENGTH_LONG);
            myToast.show();

        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            String timestamp = sdf.format(System.currentTimeMillis());

            CRTS_Data my_crts = new CRTS_Data(c1_1, c1_2, c1_3, c2_1, c2_2, c2_3, c3_1, c3_2, c3_3, c4_1, c4_2, c4_3, c5_1, c5_2, c5_3, c6_1, c6_2, c6_3, c7_1, c7_2, c7_3,
                    c8_1, c8_2, c8_3, c9_1, c9_2, c9_3, c10_1, c10_2, c10_3, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23);

            int crts_partA_score = c1_1 + c1_2 + c1_3 + c2_1 + c2_2 + c2_3 + c3_1 + c3_2 + c3_3 + c4_1 + c4_2 + c4_3 + c5_1 + c5_2 + c5_3 + c6_1 + c6_2 + c6_3 + c7_1 + c7_2 + c7_3 +
                    c8_1 + c8_2 + c8_3 + c9_1 + c9_2 + c9_3 + c10_1 + c10_2 + c10_3;
            int crts_partB_score = c11 + c12 + c13 + c14 + c15;
            int crts_partC_score = c16 + c17 + c18 + c19 + c20 + c21 + c22 + c23;

            CRTS_Score crts_score = new CRTS_Score(crts_partA_score, crts_partB_score, crts_partC_score);
            CRTS m_crts = new CRTS(timestamp, taskno);
            SpiralData spiraldata = new SpiralData(1.0, spiral_result[1], spiral_result[0], spiral_result[3], spiral_result[2], spiral_result[4]);
            //SpiralData left_spiraldata = new SpiralData(left_spiral_result[2], left_spiral_result[3],left_spiral_result[4], "CRTS");
            SpiralData left_spiraldata = new SpiralData(1.0,left_spiral_result[1], left_spiral_result[0], left_spiral_result[3], left_spiral_result[2], left_spiral_result[4]);
            LineData linedata = new LineData(1.0,line_result[1], line_result[0], line_result[3], line_result[2], line_result[4]);
            final String key = databaseCRTS.push().getKey();

            if (key != null) {
                databaseCRTS.child(key).child("timestamp").setValue(timestamp);
                databaseCRTS.child(key).child("CRTS_count").setValue(taskno);
                databaseCRTS.child(key).child("CRTS score").setValue(crts_score);
                databaseCRTS.child(key).child("CRTS task").setValue(my_crts);
                databaseCRTS.child(key).child("Right_Spiral_Result").setValue(spiraldata) ;
                databaseCRTS.child(key).child("Left_Spiral_Result").setValue(left_spiraldata) ;
                databaseCRTS.child(key).child("Line_Result").setValue(linedata);
                databaseCRTS.child(key).child("Right_Spiral_URL").setValue(crts_right_spiral_downurl);
                databaseCRTS.child(key).child("Left_Spiral_URL").setValue(crts_left_spiral_downurl);
                databaseCRTS.child(key).child("Writing_URL").setValue(writing_downurl);
                Log.v("5/14 (3)", writing_downurl);
                databaseCRTS.child(key).child("Line_URL").setValue(line_downurl);

            }
            databasePatientList.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot mData : dataSnapshot.getChildren()) {
                        int crts_num = (int) mData.child("CRTS List").getChildrenCount();
                        int updrs_num = (int) mData.child("UPDRS List").getChildrenCount();
                        int spiral_num = (int) mData.child("Spiral List").getChildrenCount();
                        int line_num = (int) mData.child("Line List").getChildrenCount();
                        int taskNo = crts_num + updrs_num + spiral_num + line_num;

                        databasePatientList.child(Clinic_ID).child("TaskNo").setValue(taskNo);
                        if (taskNo == 1) {
                            String FirstDate = String.valueOf(mData.child("CRTS List").child(key).child("timestamp").getValue());
                            int idx = FirstDate.indexOf(" ");
                            String FirstDate1 = FirstDate.substring(0, idx);
                            databasePatientList.child(Clinic_ID).child("FirstDate").setValue(FirstDate1);
                            databasePatientList.child(Clinic_ID).child("FinalDate").setValue(FirstDate1);

                        } else {
                            String FinalDate = String.valueOf(mData.child("CRTS List").child(key).child("timestamp").getValue());
                            int idx = FinalDate.indexOf(" ");
                            String FinalDate1 = FinalDate.substring(0, idx);
                            databasePatientList.child(Clinic_ID).child("FinalDate").setValue(FinalDate1);

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databasePatientList.child(Clinic_ID).child("CRTS_Final_Score").setValue(crts_partA_score + crts_partB_score + crts_partC_score);
            Intent intent = new Intent(this, CRTS_Result_Activity.class);
            intent.putExtra("spiral_result", spiral_result);
            intent.putExtra("line_result", line_result);
            intent.putExtra("left_spiral_result", left_spiral_result);
            intent.putExtra("ClinicID", Clinic_ID);
            intent.putExtra("PatientName", PatientName);
            intent.putExtra("timestamp", timestamp);
            intent.putExtra("line_downurl", line_downurl);
            intent.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
            intent.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
            intent.putExtra("writing_downurl", writing_downurl);
            intent.putExtra("left", left);
            intent.putExtra("taskscore", String.valueOf(crts_partA_score + crts_partB_score + crts_partC_score));
            intent.putExtra("crts_num", String.valueOf(taskno));
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "검사 종료를 원하신다면 '종료' 버튼을 눌러 종료시켜주세요.", Toast.LENGTH_SHORT).show();
    }
    public void alertDisplay() {

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("종료")
                .setMessage("지금 종료하면 데이터를 모두 잃게됩니다. 종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                        intent.putExtra("ClinicID", Clinic_ID);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("task", "CRTS");
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }


/*
    // Backbutton 클릭 method
    @Override
    public void onBackPressed(){
        onStop();
        Intent intent = new Intent(getApplicationContext(), PersonalPatient.class) ;
        startActivity(intent) ;
    }
    */
}