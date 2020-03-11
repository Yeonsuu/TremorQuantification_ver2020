package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.MotorScale_Data;
import com.ahnbcilab.tremorquantification.data.UPDRS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UPDRSActivity extends AppCompatActivity implements View.OnClickListener {

    Button quit_button ;
    TextView page_text;

    FragmentManager fm;
    FragmentTransaction tran;
    Updrs_Test_Fragment1 frag1;
    Updrs_Test_Fragment2 frag2;
    Updrs_Test_Fragment3 frag3;

    String PatientName;
    String task;
    String Clinic_ID;


    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databasepatient;
    DatabaseReference databaseclinicID;
    int motorScale_score;
    String path;
    boolean bool = true;
    int count = 0;
    String smotor_count = "";
    int a, b, c, d, e, f, g, h, i1;
    int j, k, l, m, n, n1, o, p, q, r, s, t, u, v;
    int w, x, y, z;
    int sa, sb, sc, sd, se, sf, sg, sh, si1;
    int sj, sk, sl, sm, sn, sn1, so, sp, sq, sr, ss, st, su, sv;
    int sw, sx, sy, sz, taskno = 0;

    String updrs_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updrs);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("Clinic_ID");
        PatientName = intent.getExtras().getString("PatientName");
        task = intent.getExtras().getString("task");
        updrs_num = intent.getExtras().getString("updrs_num");

        databasepatient = firebaseDatabase.getReference("PatientList");
        databaseclinicID = databasepatient.child(Clinic_ID).child("UPDRS List");

        databaseclinicID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskno = Integer.parseInt(String.valueOf(dataSnapshot.getChildrenCount()));
                // TODO: show the count in the UI
                if (taskno < 10) {
                    smotor_count = "0" + taskno;
                } else {
                    smotor_count = Integer.toString(taskno);
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
        page_text = (TextView) findViewById(R.id.page_text);

        quit_button = (Button) findViewById(R.id.updrs_quit_button) ;

        quit_button.setOnClickListener(this);

        frag1 = new Updrs_Test_Fragment1(); //프래그먼트 객채셍성
        frag2 = new Updrs_Test_Fragment2(); //프래그먼트 객채셍성
        frag3 = new Updrs_Test_Fragment3(); //프래그먼트 객채셍성
        setFrag(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updrs_quit_button:
                alertDisplay();
                break ;
        }
    }

    public void setFrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                frag1.setArguments(bundle1);
                tran.replace(R.id.updrs_test, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                frag2.setArguments(bundle2);
                tran.replace(R.id.updrs_test, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                frag3.setArguments(bundle3);
                tran.replace(R.id.updrs_test, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;

        }
    }


    public void frgment1() {
        Updrs_Test_Fragment1 updrs_test_fragment1 = (Updrs_Test_Fragment1) getFragmentManager().findFragmentById(R.id.updrs_test);
        bool = true;
        if (updrs_test_fragment1.CBpart3_1_0.isChecked()) {
            a = 0;
        } else if (updrs_test_fragment1.CBpart3_1_1.isChecked()) {
            a = 1;
        } else if (updrs_test_fragment1.CBpart3_1_2.isChecked()) {
            a = 2;
        } else if (updrs_test_fragment1.CBpart3_1_3.isChecked()) {
            a = 3;
        } else if (updrs_test_fragment1.CBpart3_1_4.isChecked()) {
            a = 4;
        } else {
            a = -1;
            bool = false;
        }
        sa = a;
        if (sa == -1) {
            sa = 0;
        }

        if (updrs_test_fragment1.CBpart3_2_0.isChecked()) {
            b = 0;
        } else if (updrs_test_fragment1.CBpart3_2_1.isChecked()) {
            b = 1;
        } else if (updrs_test_fragment1.CBpart3_2_2.isChecked()) {
            b = 2;
        } else if (updrs_test_fragment1.CBpart3_2_3.isChecked()) {
            b = 3;
        } else if (updrs_test_fragment1.CBpart3_2_4.isChecked()) {
            b = 4;
        } else {
            b = -1;
            bool = false;
        }
        sb = b;
        if (sb == -1) {
            sb = 0;
        }

        if (updrs_test_fragment1.CBpart3_3_0.isChecked()) {
            c = 0;
        } else if (updrs_test_fragment1.CBpart3_3_1.isChecked()) {
            c = 1;
        } else if (updrs_test_fragment1.CBpart3_3_2.isChecked()) {
            c = 2;
        } else if (updrs_test_fragment1.CBpart3_3_3.isChecked()) {
            c = 3;
        } else if (updrs_test_fragment1.CBpart3_3_4.isChecked()) {
            c = 4;
        } else {
            c = -1;
            bool = false;
        }
        sc = c;
        if (sc == -1) {
            sc = 0;
        }

        if (updrs_test_fragment1.CBpart3_4_0.isChecked()) {
            d = 0;
        } else if (updrs_test_fragment1.CBpart3_4_1.isChecked()) {
            d = 1;
        } else if (updrs_test_fragment1.CBpart3_4_2.isChecked()) {
            d = 2;
        } else if (updrs_test_fragment1.CBpart3_4_3.isChecked()) {
            d = 3;
        } else if (updrs_test_fragment1.CBpart3_4_4.isChecked()) {
            d = 4;
        } else {
            d = -1;
            bool = false;
        }
        sd = d;
        if (sd == -1) {
            sd = 0;
        }

        if (updrs_test_fragment1.CBpart3_5_0.isChecked()) {
            e = 0;
        } else if (updrs_test_fragment1.CBpart3_5_1.isChecked()) {
            e = 1;
        } else if (updrs_test_fragment1.CBpart3_5_2.isChecked()) {
            e = 2;
        } else if (updrs_test_fragment1.CBpart3_5_3.isChecked()) {
            e = 3;
        } else if (updrs_test_fragment1.CBpart3_5_4.isChecked()) {
            e = 4;
        } else {
            e = -1;
            bool = false;
        }
        se = e;
        if (se == -1) {
            se = 0;
        }

        if (updrs_test_fragment1.CBpart3_6_0.isChecked()) {
            f = 0;
        } else if (updrs_test_fragment1.CBpart3_6_1.isChecked()) {
            f = 1;
        } else if (updrs_test_fragment1.CBpart3_6_2.isChecked()) {
            f = 2;
        } else if (updrs_test_fragment1.CBpart3_6_3.isChecked()) {
            f = 3;
        } else if (updrs_test_fragment1.CBpart3_6_4.isChecked()) {
            f = 4;
        } else {
            f = -1;
            bool = false;
        }
        sf = f;
        if (sf == -1) {
            sf = 0;
        }

        if (updrs_test_fragment1.CBpart3_7_0.isChecked()) {
            g = 0;
        } else if (updrs_test_fragment1.CBpart3_7_1.isChecked()) {
            g = 1;
        } else if (updrs_test_fragment1.CBpart3_7_2.isChecked()) {
            g = 2;
        } else if (updrs_test_fragment1.CBpart3_7_3.isChecked()) {
            g = 3;
        } else if (updrs_test_fragment1.CBpart3_7_4.isChecked()) {
            g = 4;
        } else {
            g = -1;
            bool = false;
        }
        sg = g;
        if (sg == -1) {
            sg = 0;
        }

        if (updrs_test_fragment1.CBpart3_8_0.isChecked()) {
            h = 0;
        } else if (updrs_test_fragment1.CBpart3_8_1.isChecked()) {
            h = 1;
        } else if (updrs_test_fragment1.CBpart3_8_2.isChecked()) {
            h = 2;
        } else if (updrs_test_fragment1.CBpart3_8_3.isChecked()) {
            h = 3;
        } else if (updrs_test_fragment1.CBpart3_8_4.isChecked()) {
            h = 4;
        } else {
            h = -1;
            bool = false;
        }
        sh = h;
        if (sh == -1) {
            sh = 0;
        }

        if (updrs_test_fragment1.CBpart3_9_0.isChecked()) {
            i1 = 0;
        } else if (updrs_test_fragment1.CBpart3_9_1.isChecked()) {
            i1 = 1;
        } else if (updrs_test_fragment1.CBpart3_9_2.isChecked()) {
            i1 = 2;
        } else if (updrs_test_fragment1.CBpart3_9_3.isChecked()) {
            i1 = 3;
        } else if (updrs_test_fragment1.CBpart3_9_4.isChecked()) {
            i1 = 4;
        } else {
            i1 = -1;
            bool = false;
        }
        si1 = i1;
        if (si1 == -1) {
            si1 = 0;
        }

        if (bool == false) {
            int rg1_index = updrs_test_fragment1.rg1.getCheckedRadioButtonId();
            if(rg1_index == -1){
                updrs_test_fragment1.updrs_1_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_1_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg1.setBackgroundResource(0);
            }

            int rg2_index = updrs_test_fragment1.rg2.getCheckedRadioButtonId();
            if(rg2_index == -1){
                updrs_test_fragment1.updrs_2_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_2_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg2.setBackgroundResource(0);
            }

            int rg3_index = updrs_test_fragment1.rg3.getCheckedRadioButtonId();
            if(rg3_index == -1){
                updrs_test_fragment1.updrs_3_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_3_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg3.setBackgroundResource(0);
            }

            int rg4_index = updrs_test_fragment1.rg4.getCheckedRadioButtonId();
            if(rg4_index == -1){
                updrs_test_fragment1.updrs_4_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_4_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg4.setBackgroundResource(0);
            }

            int rg5_index = updrs_test_fragment1.rg5.getCheckedRadioButtonId();
            if(rg5_index == -1){
                updrs_test_fragment1.updrs_5_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_5_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg5.setBackgroundResource(0);
            }

            int rg6_index = updrs_test_fragment1.rg6.getCheckedRadioButtonId();
            if(rg6_index == -1){
                updrs_test_fragment1.updrs_6_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_6_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg6.setBackgroundResource(0);
            }

            int rg7_index = updrs_test_fragment1.rg7.getCheckedRadioButtonId();
            if(rg7_index == -1){
                updrs_test_fragment1.updrs_7_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_7_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg7.setBackgroundResource(0);
            }

            int rg8_index = updrs_test_fragment1.rg8.getCheckedRadioButtonId();
            if(rg8_index == -1){
                updrs_test_fragment1.updrs_8_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_8_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg8.setBackgroundResource(0);
            }

            int rg9_index = updrs_test_fragment1.rg9.getCheckedRadioButtonId();
            if(rg9_index == -1){
                updrs_test_fragment1.updrs_9_title.setTextColor(Color.RED);
                updrs_test_fragment1.rg9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment1.updrs_9_title.setTextColor(Color.BLACK);
                updrs_test_fragment1.rg9.setBackgroundResource(0);
            }

            bool = true;

        } else {
            setFrag(1);
        }
    }

    public void frgment2() {
        Updrs_Test_Fragment2 updrs_test_fragment2 = (Updrs_Test_Fragment2) getFragmentManager().findFragmentById(R.id.updrs_test);
        bool = true;
        if (updrs_test_fragment2.CBpart3_10_0.isChecked()) {
            j = 0;
        } else if (updrs_test_fragment2.CBpart3_10_1.isChecked()) {
            j = 1;
        } else if (updrs_test_fragment2.CBpart3_10_2.isChecked()) {
            j = 2;
        } else if (updrs_test_fragment2.CBpart3_10_3.isChecked()) {
            j = 3;
        } else if (updrs_test_fragment2.CBpart3_10_4.isChecked()) {
            j = 4;
        } else {
            j = -1;
            bool = false;
        }
        sj = j;
        if (sj == -1) {
            sj = 0;
        }

        if (updrs_test_fragment2.CBpart3_11_0.isChecked()) {
            k = 0;
        } else if (updrs_test_fragment2.CBpart3_11_1.isChecked()) {
            k = 1;
        } else if (updrs_test_fragment2.CBpart3_11_2.isChecked()) {
            k = 2;
        } else if (updrs_test_fragment2.CBpart3_11_3.isChecked()) {
            k = 3;
        } else if (updrs_test_fragment2.CBpart3_11_4.isChecked()) {
            k = 4;
        } else {
            k = -1;
            bool = false;
        }
        sk = k;
        if (sk == -1) {
            sk = 0;
        }

        if (updrs_test_fragment2.CBpart3_12_0.isChecked()) {
            l = 0;
        } else if (updrs_test_fragment2.CBpart3_12_1.isChecked()) {
            l = 1;
        } else if (updrs_test_fragment2.CBpart3_12_2.isChecked()) {
            l = 2;
        } else if (updrs_test_fragment2.CBpart3_12_3.isChecked()) {
            l = 3;
        } else if (updrs_test_fragment2.CBpart3_12_4.isChecked()) {
            l = 4;
        } else {
            l = -1;
            bool = false;
        }
        sl = l;
        if (sl == -1) {
            sl = 0;
        }

        if (updrs_test_fragment2.CBpart3_13_0.isChecked()) {
            m = 0;
        } else if (updrs_test_fragment2.CBpart3_13_1.isChecked()) {
            m = 1;
        } else if (updrs_test_fragment2.CBpart3_13_2.isChecked()) {
            m = 2;
        } else if (updrs_test_fragment2.CBpart3_13_3.isChecked()) {
            m = 3;
        } else if (updrs_test_fragment2.CBpart3_13_4.isChecked()) {
            m = 4;
        } else {
            m = -1;
            bool = false;
        }
        sm = m;
        if (sm == -1) {
            sm = 0;
        }

        if (updrs_test_fragment2.CBpart3_14_0.isChecked()) {
            n = 0;
        } else if (updrs_test_fragment2.CBpart3_14_1.isChecked()) {
            n = 1;
        } else if (updrs_test_fragment2.CBpart3_14_2.isChecked()) {
            n = 2;
        } else if (updrs_test_fragment2.CBpart3_14_3.isChecked()) {
            n = 3;
        } else if (updrs_test_fragment2.CBpart3_14_4.isChecked()) {
            n = 4;
        } else {
            n = -1;
            bool = false;
        }
        sn = n;
        if (sn == -1) {
            sn = 0;
        }

        if (updrs_test_fragment2.CBpart3_15_0.isChecked()) {
            n1 = 0;
        } else if (updrs_test_fragment2.CBpart3_15_1.isChecked()) {
            n1 = 1;
        } else if (updrs_test_fragment2.CBpart3_15_2.isChecked()) {
            n1 = 2;
        } else if (updrs_test_fragment2.CBpart3_15_3.isChecked()) {
            n1 = 3;
        } else if (updrs_test_fragment2.CBpart3_15_4.isChecked()) {
            n1 = 4;
        } else {
            n1 = -1;
            bool = false;
        }
        sn1 = n1;
        if (sn1 == -1) {
            sn1 = 0;
        }

        if (updrs_test_fragment2.CBpart3_16_0.isChecked()) {
            o = 0;
        } else if (updrs_test_fragment2.CBpart3_16_1.isChecked()) {
            o = 1;
        } else if (updrs_test_fragment2.CBpart3_16_2.isChecked()) {
            o = 2;
        } else if (updrs_test_fragment2.CBpart3_16_3.isChecked()) {
            o = 3;
        } else if (updrs_test_fragment2.CBpart3_16_4.isChecked()) {
            o = 4;
        } else {
            o = -1;
            bool = false;
        }
        so = o;
        if (so == -1) {
            so = 0;
        }

        if (updrs_test_fragment2.CBpart3_17_0.isChecked()) {
            p = 0;
        } else if (updrs_test_fragment2.CBpart3_17_1.isChecked()) {
            p = 1;
        } else if (updrs_test_fragment2.CBpart3_17_2.isChecked()) {
            p = 2;
        } else if (updrs_test_fragment2.CBpart3_17_3.isChecked()) {
            p = 3;
        } else if (updrs_test_fragment2.CBpart3_17_4.isChecked()) {
            p = 4;
        } else {
            p = -1;
            bool = false;
        }
        sp = p;
        if (sp == -1) {
            sp = 0;
        }

        if (updrs_test_fragment2.CBpart3_18_0.isChecked()) {
            q = 0;
        } else if (updrs_test_fragment2.CBpart3_18_1.isChecked()) {
            q = 1;
        } else if (updrs_test_fragment2.CBpart3_18_2.isChecked()) {
            q = 2;
        } else if (updrs_test_fragment2.CBpart3_18_3.isChecked()) {
            q = 3;
        } else if (updrs_test_fragment2.CBpart3_18_4.isChecked()) {
            q = 4;
        } else {
            q = -1;
            bool = false;
        }
        sq = q;
        if (sq == -1) {
            sq = 0;
        }

        if (updrs_test_fragment2.CBpart3_19_0.isChecked()) {
            r = 0;
        } else if (updrs_test_fragment2.CBpart3_19_1.isChecked()) {
            r = 1;
        } else if (updrs_test_fragment2.CBpart3_19_2.isChecked()) {
            r = 2;
        } else if (updrs_test_fragment2.CBpart3_19_3.isChecked()) {
            r = 3;
        } else if (updrs_test_fragment2.CBpart3_19_4.isChecked()) {
            r = 4;
        } else {
            r = -1;
            bool = false;
        }
        sr = r;
        if (sr == -1) {
            sr = 0;
        }

        if (updrs_test_fragment2.CBpart3_20_0.isChecked()) {
            s = 0;
        } else if (updrs_test_fragment2.CBpart3_20_1.isChecked()) {
            s = 1;
        } else if (updrs_test_fragment2.CBpart3_20_2.isChecked()) {
            s = 2;
        } else if (updrs_test_fragment2.CBpart3_20_3.isChecked()) {
            s = 3;
        } else if (updrs_test_fragment2.CBpart3_20_4.isChecked()) {
            s = 4;
        } else {
            s = -1;
            bool = false;
        }
        ss = s;
        if (ss == -1) {
            ss = 0;
        }

        if (updrs_test_fragment2.CBpart3_21_0.isChecked()) {
            t = 0;
        } else if (updrs_test_fragment2.CBpart3_21_1.isChecked()) {
            t = 1;
        } else if (updrs_test_fragment2.CBpart3_21_2.isChecked()) {
            t = 2;
        } else if (updrs_test_fragment2.CBpart3_21_3.isChecked()) {
            t = 3;
        } else if (updrs_test_fragment2.CBpart3_21_4.isChecked()) {
            t = 4;
        } else {
            t = -1;
            bool = false;
        }
        st = t;
        if (st == -1) {
            st = 0;
        }

        if (updrs_test_fragment2.CBpart3_22_0.isChecked()) {
            u = 0;
        } else if (updrs_test_fragment2.CBpart3_22_1.isChecked()) {
            u = 1;
        } else if (updrs_test_fragment2.CBpart3_22_2.isChecked()) {
            u = 2;
        } else if (updrs_test_fragment2.CBpart3_22_3.isChecked()) {
            u = 3;
        } else if (updrs_test_fragment2.CBpart3_22_4.isChecked()) {
            u = 4;
        } else {
            u = -1;
            bool = false;
        }
        su = u;
        if (su == -1) {
            su = 0;
        }

        if (bool == false) {
            int rg10_index = updrs_test_fragment2.rg10.getCheckedRadioButtonId();
            if(rg10_index == -1){
                updrs_test_fragment2.updrs_10_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_10_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg10.setBackgroundResource(0);
            }

            int rg11_index = updrs_test_fragment2.rg11.getCheckedRadioButtonId();
            if(rg11_index == -1){
                updrs_test_fragment2.updrs_11_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_11_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg11.setBackgroundResource(0);
            }

            int rg12_index = updrs_test_fragment2.rg12.getCheckedRadioButtonId();
            if(rg12_index == -1){
                updrs_test_fragment2.updrs_12_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_12_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg12.setBackgroundResource(0);
            }

            int rg13_index = updrs_test_fragment2.rg13.getCheckedRadioButtonId();
            if(rg13_index == -1){
                updrs_test_fragment2.updrs_13_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_13_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg13.setBackgroundResource(0);
            }

            int rg14_index = updrs_test_fragment2.rg14.getCheckedRadioButtonId();
            if(rg14_index == -1){
                updrs_test_fragment2.updrs_14_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_14_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg14.setBackgroundResource(0);
            }

            int rg15_index = updrs_test_fragment2.rg15.getCheckedRadioButtonId();
            if(rg15_index == -1){
                updrs_test_fragment2.updrs_15_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_15_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg15.setBackgroundResource(0);
            }

            int rg16_index = updrs_test_fragment2.rg16.getCheckedRadioButtonId();
            if(rg16_index == -1){
                updrs_test_fragment2.updrs_16_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_16_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg16.setBackgroundResource(0);
            }

            int rg17_index = updrs_test_fragment2.rg17.getCheckedRadioButtonId();
            if(rg17_index == -1){
                updrs_test_fragment2.updrs_17_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_17_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg17.setBackgroundResource(0);
            }

            int rg18_index = updrs_test_fragment2.rg18.getCheckedRadioButtonId();
            if(rg18_index == -1){
                updrs_test_fragment2.updrs_18_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg18.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_18_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg18.setBackgroundResource(0);
            }

            int rg19_index = updrs_test_fragment2.rg19.getCheckedRadioButtonId();
            if(rg19_index == -1){
                updrs_test_fragment2.updrs_19_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg19.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_19_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg19.setBackgroundResource(0);
            }

            int rg20_index = updrs_test_fragment2.rg20.getCheckedRadioButtonId();
            if(rg20_index == -1){
                updrs_test_fragment2.updrs_20_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg20.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_20_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg20.setBackgroundResource(0);
            }

            int rg21_index = updrs_test_fragment2.rg21.getCheckedRadioButtonId();
            if(rg21_index == -1){
                updrs_test_fragment2.updrs_21_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg21.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_21_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg21.setBackgroundResource(0);
            }

            int rg22_index = updrs_test_fragment2.rg22.getCheckedRadioButtonId();
            if(rg22_index == -1){
                updrs_test_fragment2.updrs_22_title.setTextColor(Color.RED);
                updrs_test_fragment2.rg22.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.s_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment2.updrs_22_title.setTextColor(Color.BLACK);
                updrs_test_fragment2.rg22.setBackgroundResource(0);
            }

            bool = true;

        } else {
            setFrag(2);
        }
    }

    public void fragment3() {
        Updrs_Test_Fragment3 updrs_test_fragment3 = (Updrs_Test_Fragment3) getFragmentManager().findFragmentById(R.id.updrs_test);
        bool = true;
        if (updrs_test_fragment3.CBpart3_23_0.isChecked()) {
            v = 0;
        } else if (updrs_test_fragment3.CBpart3_23_1.isChecked()) {
            v = 1;
        } else if (updrs_test_fragment3.CBpart3_23_2.isChecked()) {
            v = 2;
        } else if (updrs_test_fragment3.CBpart3_23_3.isChecked()) {
            v = 3;
        } else if (updrs_test_fragment3.CBpart3_23_4.isChecked()) {
            v = 4;
        } else {
            v = -1;
            bool = false;
        }
        sv = v;
        if (sv == -1) {
            sv = 0;
        }

        if (updrs_test_fragment3.CBpart3_24_0.isChecked()) {
            w = 0;
        } else if (updrs_test_fragment3.CBpart3_24_1.isChecked()) {
            w = 1;
        } else if (updrs_test_fragment3.CBpart3_24_2.isChecked()) {
            w = 2;
        } else if (updrs_test_fragment3.CBpart3_24_3.isChecked()) {
            w = 3;
        } else if (updrs_test_fragment3.CBpart3_24_4.isChecked()) {
            w = 4;
        } else {
            sw = -1;
            bool = false;
        }
        sw = w;
        if (sw == -1) {
            sw = 0;
        }

        if (updrs_test_fragment3.CBpart3_25_0.isChecked()) {
            x = 0;
        } else if (updrs_test_fragment3.CBpart3_25_1.isChecked()) {
            x = 1;
        } else if (updrs_test_fragment3.CBpart3_25_2.isChecked()) {
            x = 2;
        } else if (updrs_test_fragment3.CBpart3_25_3.isChecked()) {
            x = 3;
        } else if (updrs_test_fragment3.CBpart3_25_4.isChecked()) {
            x = 4;
        } else {
            x = -1;
            bool = false;
        }
        sx = x;
        if (sx == -1) {
            sx = 0;
        }


        if (updrs_test_fragment3.CBpart3_26_0.isChecked()) {
            y = 0;
        } else if (updrs_test_fragment3.CBpart3_26_1.isChecked()) {
            y = 1;
        } else if (updrs_test_fragment3.CBpart3_26_2.isChecked()) {
            y = 2;
        } else if (updrs_test_fragment3.CBpart3_26_3.isChecked()) {
            y = 3;
        } else if (updrs_test_fragment3.CBpart3_26_4.isChecked()) {
            y = 4;
        } else {
            y = -1;
            bool = false;
        }
        sy = y;
        if (sy == -1) {
            sy = 0;
        }

        if (updrs_test_fragment3.CBpart3_27_0.isChecked()) {
            z = 0;
        } else if (updrs_test_fragment3.CBpart3_27_1.isChecked()) {
            z = 1;
        } else if (updrs_test_fragment3.CBpart3_27_2.isChecked()) {
            z = 2;
        } else if (updrs_test_fragment3.CBpart3_27_3.isChecked()) {
            z = 3;
        } else if (updrs_test_fragment3.CBpart3_27_4.isChecked()) {
            z = 4;
        } else {
            z = -1;
            bool = false;
        }
        sz = z;
        if (sz == -1) {
            sz = 0;
        }

        if (bool == false) {
            int rg23_index = updrs_test_fragment3.rg23.getCheckedRadioButtonId();
            if(rg23_index == -1){
                updrs_test_fragment3.updrs_23_title.setTextColor(Color.RED);
                updrs_test_fragment3.rg23.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment3.updrs_23_title.setTextColor(Color.BLACK);
                updrs_test_fragment3.rg23.setBackgroundResource(0);
            }

            int rg24_index = updrs_test_fragment3.rg24.getCheckedRadioButtonId();
            if(rg24_index == -1){
                updrs_test_fragment3.updrs_24_title.setTextColor(Color.RED);
                updrs_test_fragment3.rg24.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment3.updrs_24_title.setTextColor(Color.BLACK);
                updrs_test_fragment3.rg24.setBackgroundResource(0);
            }

            int rg25_index = updrs_test_fragment3.rg25.getCheckedRadioButtonId();
            if(rg25_index == -1){
                updrs_test_fragment3.updrs_25_title.setTextColor(Color.RED);
                updrs_test_fragment3.rg25.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment3.updrs_25_title.setTextColor(Color.BLACK);
                updrs_test_fragment3.rg25.setBackgroundResource(0);
            }

            int rg26_index = updrs_test_fragment3.rg26.getCheckedRadioButtonId();
            if(rg26_index == -1){
                updrs_test_fragment3.updrs_26_title.setTextColor(Color.RED);
                updrs_test_fragment3.rg26.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment3.updrs_26_title.setTextColor(Color.BLACK);
                updrs_test_fragment3.rg26.setBackgroundResource(0);
            }

            int rg27_index = updrs_test_fragment3.rg27.getCheckedRadioButtonId();
            if(rg27_index == -1){
                updrs_test_fragment3.updrs_27_title.setTextColor(Color.RED);
                updrs_test_fragment3.rg27.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.l_radiogroup_nonselect));
            }
            else{
                updrs_test_fragment3.updrs_27_title.setTextColor(Color.BLACK);
                updrs_test_fragment3.rg27.setBackgroundResource(0);
            }
            bool = true;

        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            String timestamp = sdf.format(new Date());
            MotorScale_Data motorScale = new MotorScale_Data(a, b, c, d, e, f, g, h, i1, j, k, l, m, n, n1, o, p, q, r, s, t, u, v, w, x, y, z);

            motorScale_score = sa + sb + sc + sd + se + sf + sg + sh + si1 + sj + sk + sl + sm + sn + sn1 + so + sp + sq + sr + ss + st + su + sv + sw + sx + sy + sz;
            //databaseclinicID.child("num").setValue(taskno+1);
            UPDRS motor = new UPDRS(timestamp, taskno, motorScale_score);
            final String key = String.valueOf(databaseclinicID.push().getKey());
            databaseclinicID.child(key).setValue(motor);

            databaseclinicID.child(key).child("UPDRS task").setValue(motorScale);
            databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                        Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                        Long spiral_count = childDataSnapshot.child("Spiral List").getChildrenCount();
                        Long line_count = childDataSnapshot.child("Line List").getChildrenCount();

                        int taskNo = (int)(crts_count + updrs_count + spiral_count + line_count);
                        Log.v("UPDRS", "UPDRSACtivity "+ crts_count+ " "+ updrs_count+" " + spiral_count + " " + line_count) ;
                        databasepatient.child(Clinic_ID).child("TaskNo").setValue(taskNo);
                        //databasepatient.child(Clinic_ID).child("UPDRS List").child("UPDRS_Final_num").setValue(updrs_count) ;
                        if (taskNo == 1) {
                            String FirstDate = String.valueOf(childDataSnapshot.child("UPDRS List").child(key).child("timestamp").getValue());
                            int idx = FirstDate.indexOf(" ");
                            String firstDate1 = FirstDate.substring(0, idx);
                            databasepatient.child(Clinic_ID).child("FirstDate").setValue(firstDate1);
                            databasepatient.child(Clinic_ID).child("FinalDate").setValue(firstDate1);

                        } else {
                            String FinalDate = childDataSnapshot.child("UPDRS List").child(key).child("timestamp").getValue().toString();
                            int idx = FinalDate.indexOf(" ");
                            String finalDate1 = FinalDate.substring(0, idx);
                            databasepatient.child(Clinic_ID).child("FinalDate").setValue(finalDate1);

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databasepatient.child(Clinic_ID).child("UPDRS_Final_Score").setValue(motorScale_score) ;

            Intent intent = new Intent(this, UPDRS_Result_Activity.class);
            intent.putExtra("ClinicID", Clinic_ID);
            intent.putExtra("PatientName", PatientName);
            intent.putExtra("task", "UPDRS");
            intent.putExtra("timestamp", timestamp);
            intent.putExtra("taskscore", String.valueOf(motorScale_score));
            intent.putExtra("updrs_num", updrs_num);
            startActivity(intent);
            finish();
        }
    }

    public void alertDisplay(){

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("종료")
                .setMessage("지금 종료하면 데이터를 모두 잃게됩니다. 종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                        intent.putExtra("ClinicID", Clinic_ID);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("task", "UPDRS");
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
}