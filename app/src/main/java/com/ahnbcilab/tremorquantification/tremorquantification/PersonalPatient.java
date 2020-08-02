package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.ContentsPagerAdapter;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class PersonalPatient extends AppCompatActivity implements View.OnClickListener {
        TextView fb1, fb2, fb3, fb4, fb5, fb6;
        TextView edit, person_delete, personal_diseaseType, personal_date;
        FragmentManager fm;
        FragmentTransaction tran;
        UPDRS_Fragment frag1;
        CRTS_Fragment frag2;
        SpiralTask_Fragment frag3;
        LineTask_Fragment frag4;
        Gear_Fragment frag5;
        Writing_Fragment frag6;
        NonTaskFragment frag7 ;
        public static String Clinic_ID;
        public static String taskType;
        String PatientName;
        String task;
        private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databasePatientList;
        boolean data_exists;
        String taskNum ;
        Button dot;
        ImageView bt1_under, bt2_under, bt3_under, bt4_under, bt5_under, bt6_under;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_patient);

        // intent 값 받아오기
        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        task = intent.getExtras().getString("task");

        dot = (Button) findViewById(R.id.dot);


        TextView c = (TextView) findViewById(R.id.patientclinicID);
        c.setText(Clinic_ID);
        TextView p = (TextView) findViewById(R.id.patientClinicName);
        p.setText(PatientName);
        // ID랑 이름 표출
        fb1 = (TextView) findViewById(R.id.bt1);
        fb2 = (TextView) findViewById(R.id.bt2);
        fb3 = (TextView) findViewById(R.id.bt3);
        fb4 = (TextView) findViewById(R.id.bt4);
        fb5 = (TextView) findViewById(R.id.bt5);
        fb6 = (TextView) findViewById(R.id.bt6);
        // 각 상단 버튼 선언
        bt1_under = (ImageView) findViewById(R.id.bt1_under);
        bt2_under = (ImageView) findViewById(R.id.bt2_under);
        bt3_under = (ImageView) findViewById(R.id.bt3_under);
        bt4_under = (ImageView) findViewById(R.id.bt4_under);
        bt5_under = (ImageView) findViewById(R.id.bt5_under);
        bt6_under = (ImageView) findViewById(R.id.bt6_under);
        // 버튼 밑에 들어가는 이미지선
        personal_diseaseType = (TextView) findViewById(R.id.diseasetype);
        personal_date = (TextView) findViewById(R.id.date);

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // ... 메뉴 눌렀을때
                PopupMenu popupMenu = new PopupMenu(PersonalPatient.this, dot);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                show(Clinic_ID);
                                return true;

                            case R.id.person_delete:
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PersonalPatient.this);
                                dialogBuilder.setMessage("삭제 하시겠습니까?");
                                dialogBuilder.setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(Clinic_ID);

                                                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                                                            deleteSnapshot.getRef().removeValue();
                                                            Intent intent = new Intent(PersonalPatient.this, PatientListActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        });
                                dialogBuilder.setNegativeButton("아니오",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                dialogBuilder.create().show();
                                return true;

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        databasePatientList = firebaseDatabase.getReference("PatientList");
        databasePatientList.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String disease = String.valueOf(mData.child("DiseaseType").getValue());
                    if (disease.equals("P")) { // 데이터 받아와서 파킨슨과 본태성 진전 구분을 위한 표시
                        personal_diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_p));
                    } else if (disease.equals("ET")) {
                        personal_diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_e));
                    } else {
                        personal_diseaseType.setBackgroundResource(0);
                        personal_diseaseType.setText("ㅡ");
                    }
                    String firstDate = String.valueOf(mData.child("FirstDate").getValue());
                    String finalDate = String.valueOf(mData.child("FinalDate").getValue());
                    if (!firstDate.equals("null") && !finalDate.equals("null")) {
                        String date = firstDate + " - " + finalDate;
                        personal_date.setText(date);
                    } else {
                        personal_date.setText("");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fb1.setOnClickListener(this);
        fb2.setOnClickListener(this);
        fb3.setOnClickListener(this);
        fb4.setOnClickListener(this);
        fb5.setOnClickListener(this);
        fb6.setOnClickListener(this);

        frag1 = new UPDRS_Fragment(); //프래그먼트 객채셍성
        frag2 = new CRTS_Fragment(); //프래그먼트 객채셍성
        frag3 = new SpiralTask_Fragment(); //프래그먼트 객채셍성
        frag4 = new LineTask_Fragment();
        frag5 = new Gear_Fragment();
        frag6 = new Writing_Fragment();
        frag7 = new NonTaskFragment();
        setFrag(0);


        // 완료한 task에 맞게 레이아웃 변경
        if (task.equals("CRTS")) {
            bt1_under.setVisibility(View.INVISIBLE);
            bt2_under.setVisibility(View.VISIBLE);
            bt3_under.setVisibility(View.INVISIBLE);
            bt4_under.setVisibility(View.INVISIBLE);
            bt5_under.setVisibility(View.INVISIBLE);
            bt6_under.setVisibility(View.INVISIBLE);
            setFrag(1);
        } else if (task.equals("SPIRAL TASK")) {
            bt1_under.setVisibility(View.INVISIBLE);
            bt2_under.setVisibility(View.INVISIBLE);
            bt3_under.setVisibility(View.VISIBLE);
            bt4_under.setVisibility(View.INVISIBLE);
            bt5_under.setVisibility(View.INVISIBLE);
            bt6_under.setVisibility(View.INVISIBLE);
            setFrag(2);
        } else if (task.equals("GEAR")) {
            bt1_under.setVisibility(View.INVISIBLE);
            bt2_under.setVisibility(View.INVISIBLE);
            bt3_under.setVisibility(View.INVISIBLE);
            bt4_under.setVisibility(View.INVISIBLE);
            bt5_under.setVisibility(View.VISIBLE);
            bt6_under.setVisibility(View.INVISIBLE);
            setFrag(4);
        } else if (task.equals("LINE TASK")) {
            bt1_under.setVisibility(View.INVISIBLE);
            bt2_under.setVisibility(View.INVISIBLE);
            bt3_under.setVisibility(View.INVISIBLE);
            bt4_under.setVisibility(View.VISIBLE);
            bt5_under.setVisibility(View.INVISIBLE);
            bt6_under.setVisibility(View.INVISIBLE);
            setFrag(3);
        } else if (task.equals("UPDRS")) {
            bt1_under.setVisibility(View.VISIBLE);
            bt2_under.setVisibility(View.INVISIBLE);
            bt3_under.setVisibility(View.INVISIBLE);
            bt4_under.setVisibility(View.INVISIBLE);
            bt5_under.setVisibility(View.INVISIBLE);
            bt6_under.setVisibility(View.INVISIBLE);
            setFrag(0);
        } else {
            bt1_under.setVisibility(View.INVISIBLE);
            bt2_under.setVisibility(View.INVISIBLE);
            bt3_under.setVisibility(View.INVISIBLE);
            bt4_under.setVisibility(View.INVISIBLE);
            bt5_under.setVisibility(View.INVISIBLE);
            bt6_under.setVisibility(View.VISIBLE);
            setFrag(5);
        }


        // Backbutton 클릭
        Button b = (Button) findViewById(R.id.backButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    // task별 버튼 클릭
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:  // 선택한 메뉴에 맞게 밑줄 레이아웃 표출되게
                bt1_under.setVisibility(View.VISIBLE);
                bt2_under.setVisibility(View.INVISIBLE);
                bt3_under.setVisibility(View.INVISIBLE);
                bt4_under.setVisibility(View.INVISIBLE);
                bt5_under.setVisibility(View.INVISIBLE);
                bt6_under.setVisibility(View.INVISIBLE);
                setFrag(0);
                break;
            case R.id.bt2:
                bt1_under.setVisibility(View.INVISIBLE);
                bt2_under.setVisibility(View.VISIBLE);
                bt3_under.setVisibility(View.INVISIBLE);
                bt4_under.setVisibility(View.INVISIBLE);
                bt5_under.setVisibility(View.INVISIBLE);
                bt6_under.setVisibility(View.INVISIBLE);
                setFrag(1);
                break;
            case R.id.bt3:
                bt1_under.setVisibility(View.INVISIBLE);
                bt2_under.setVisibility(View.INVISIBLE);
                bt3_under.setVisibility(View.VISIBLE);
                bt4_under.setVisibility(View.INVISIBLE);
                bt5_under.setVisibility(View.INVISIBLE);
                bt6_under.setVisibility(View.INVISIBLE);
                setFrag(2);
                break;

            case R.id.bt4:
                bt1_under.setVisibility(View.INVISIBLE);
                bt2_under.setVisibility(View.INVISIBLE);
                bt3_under.setVisibility(View.INVISIBLE);
                bt4_under.setVisibility(View.VISIBLE);
                bt5_under.setVisibility(View.INVISIBLE);
                bt6_under.setVisibility(View.INVISIBLE);
                setFrag(3);
                break;

            case R.id.bt5:
                bt1_under.setVisibility(View.INVISIBLE);
                bt2_under.setVisibility(View.INVISIBLE);
                bt3_under.setVisibility(View.INVISIBLE);
                bt4_under.setVisibility(View.INVISIBLE);
                bt5_under.setVisibility(View.VISIBLE);
                bt6_under.setVisibility(View.INVISIBLE);
                setFrag(4);
                break;

            case R.id.bt6:
                bt1_under.setVisibility(View.INVISIBLE);
                bt2_under.setVisibility(View.INVISIBLE);
                bt3_under.setVisibility(View.INVISIBLE);
                bt4_under.setVisibility(View.INVISIBLE);
                bt5_under.setVisibility(View.INVISIBLE);
                bt6_under.setVisibility(View.VISIBLE);
                setFrag(5);
                break;
        }
    }


    // fragment 교체
    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();

        switch (n) {
            case 0:
                databasePatientList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            data_exists = dataSnapshot.child(Clinic_ID).child("UPDRS List").exists();
                            taskNum = String.valueOf(dataSnapshot.child(Clinic_ID).child("UPDRS List").getChildrenCount());
                            tran = fm.beginTransaction();
                            frag7 = new NonTaskFragment();
                            Bundle bundle1 = new Bundle();
                            taskType = "UPDRS List";
                            bundle1.putString("Clinic_ID", Clinic_ID);
                            bundle1.putString("PatientName", PatientName);
                            bundle1.putString("taskType", taskType);
                            bundle1.putString("taskNum", taskNum);

                            Log.v("ppersonal", "pperssonal"+taskType);
                            if(data_exists==false){
                                frag7.setArguments(bundle1);
                                tran.replace(R.id.main_frame, frag7);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.

                            }
                            else {
                                frag1.setArguments(bundle1);
                                tran.replace(R.id.main_frame, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                            }
                            tran.commit();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case 1:
                databasePatientList.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            tran = fm.beginTransaction();
                            frag7 = new NonTaskFragment();
                            data_exists = dataSnapshot.child(Clinic_ID).child("CRTS List").exists();
                            taskNum = String.valueOf(dataSnapshot.child(Clinic_ID).child("CRTS List").getChildrenCount());
                            Bundle bundle2 = new Bundle();
                            taskType = "CRTS List";
                            bundle2.putString("Clinic_ID", Clinic_ID);
                            bundle2.putString("PatientName", PatientName);
                            bundle2.putString("taskType", taskType);
                            bundle2.putString("taskNum", taskNum);

                            if(data_exists==false){
                                Log.v("ppersonal", "pperssonal"+taskType);
                                frag7.setArguments(bundle2);
                                tran.replace(R.id.main_frame, frag7);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.

                            }
                            else {
                                frag2.setArguments(bundle2);
                                tran.replace(R.id.main_frame, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                            }
                            tran.commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case 2:
                databasePatientList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            data_exists = dataSnapshot.child(Clinic_ID).child("Spiral List").exists();
                            taskNum = String.valueOf(dataSnapshot.child(Clinic_ID).child("Spiral List").getChildrenCount());
                            tran = fm.beginTransaction();
                            frag7 = new NonTaskFragment();
                            Bundle bundle3 = new Bundle();
                            taskType = "Spiral List";
                            bundle3.putString("Clinic_ID", Clinic_ID);
                            bundle3.putString("PatientName", PatientName);

                            if(data_exists==false){
                                Log.v("ppersonal", "pperssonal"+taskType);
                                bundle3.putString("taskType", taskType);
                                bundle3.putString("taskNum", taskNum);
                                frag7.setArguments(bundle3);
                                tran.replace(R.id.main_frame, frag7);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.

                            }
                            else {
                                bundle3.putString("path", "main");
                                frag3.setArguments(bundle3);
                                tran.replace(R.id.main_frame, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                            }
                            tran.commit();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case 3:
                databasePatientList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            data_exists = dataSnapshot.child(Clinic_ID).child("Line List").exists();
                            taskNum = String.valueOf(dataSnapshot.child(Clinic_ID).child("Line List").getChildrenCount());
                            tran = fm.beginTransaction();
                            frag7 = new NonTaskFragment();
                            Bundle bundle4 = new Bundle();
                            taskType = "Line List";
                            bundle4.putString("Clinic_ID", Clinic_ID);
                            bundle4.putString("PatientName", PatientName);

                            if(data_exists==false){
                                Log.v("ppersonal", "pperssonal"+taskType);
                                bundle4.putString("taskType", taskType);
                                bundle4.putString("taskNum", taskNum);
                                frag7.setArguments(bundle4);
                                tran.replace(R.id.main_frame, frag7);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.

                            }
                            else {
                                bundle4.putString("path", "main");
                                frag4.setArguments(bundle4);
                                tran.replace(R.id.main_frame, frag4);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                            }
                            tran.commit();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case 4:
                tran.replace(R.id.main_frame, frag5);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                taskType = "Gear";
                break;
            case 5:
                databasePatientList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            data_exists = dataSnapshot.child(Clinic_ID).child("Writing List").exists();
                            taskNum = String.valueOf(dataSnapshot.child(Clinic_ID).child("Writing List").getChildrenCount());
                            tran = fm.beginTransaction();
                            frag7 = new NonTaskFragment();
                            Bundle bundle6 = new Bundle();
                            taskType = "Writing List";
                            bundle6.putString("Clinic_ID", Clinic_ID);
                            bundle6.putString("PatientName", PatientName);
                            Log.v("ppersonal", "pperssonal"+taskType);
                            if(data_exists==false){
                                bundle6.putString("taskType", taskType);
                                bundle6.putString("taskNum", taskNum);
                                frag7.setArguments(bundle6);
                                tran.replace(R.id.main_frame, frag7);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.

                            }
                            else {
                                frag6.setArguments(bundle6);
                                tran.replace(R.id.main_frame, frag6);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                            }
                            tran.commit();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

        }
    }


    // Backbutton 클릭 method
    @Override
    public void onBackPressed() {
        onStop();
        Intent intent = new Intent(getApplicationContext(), PatientListActivity.class);
        startActivity(intent);
    }

    public void show(final String clinical_ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_patient_dialog, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.patientEditButton);
        final Button cancel = (Button) view.findViewById(R.id.patientEditCancel);
        final EditText patientName = (EditText) view.findViewById(R.id.editPatientName);
        final RadioButton p = (RadioButton) view.findViewById(R.id.parkinson);
        final RadioButton e = (RadioButton) view.findViewById(R.id.essential_tremor);
        final TextView p_name = (TextView) findViewById(R.id.patientClinicName);
        final TextView diseaseType = (TextView) findViewById(R.id.diseasetype);

        databasePatientList = firebaseDatabase.getReference("PatientList");

        final AlertDialog dialog = builder.create();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String patient_Name = patientName.getText().toString();
                Log.v("editPatient", "PatientName : " + patient_Name);
                String patient_disease = "null";
                if (p.isChecked()) {
                    patient_disease = "parkinson";
                } else if (e.isChecked()) {
                    patient_disease = "tremor";
                } else {

                }

                final String finalPatient_disease = patient_disease;

                databasePatientList.orderByChild("ClinicID").equalTo(clinical_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!(patient_Name.equals(""))) {
                            databasePatientList.child(clinical_ID).child("ClinicName").setValue(patient_Name);
                            p_name.setText(patient_Name);
                        }

                        if ((!finalPatient_disease.equals("null"))) {
                            if (finalPatient_disease.equals("parkinson")) {
                                databasePatientList.child(clinical_ID).child("DiseaseType").setValue("P");
                                diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.parkinson));
                            } else {
                                databasePatientList.child(clinical_ID).child("DiseaseType").setValue("ET");
                                diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.essential_tremor));
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.dismiss();
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

