package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.ItemDecoration;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter2;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.ahnbcilab.tremorquantification.data.TaskItem2;
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

public class CRTS_parta_c_Fragment extends Fragment {
    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_spiral;
    DatabaseReference database_patient;

    String Clinic_ID;
    String PatientName;
    String path;
    View view;
    File file;

    String timestamp;
    String hz_score, magnitude_score, distance_score, time_score, velocity_score;
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

    CRTS_parta_a_Fragment frag1;
    CRTS_parta_b_Fragment frag2;
    CRTS_parta_c_Fragment frag3;
    Button nextButton;
    boolean bool = true;

    TextView crts_1_1_title, crts_1_2_title, crts_1_3_title, crts_2_1_title, crts_2_2_title, crts_2_3_title, crts_3_1_title, crts_3_2_title, crts_3_3_title;
    TextView crts_4_1_title, crts_4_2_title, crts_4_3_title, crts_5_1_title, crts_5_2_title, crts_5_3_title, crts_6_1_title, crts_6_2_title, crts_6_3_title;
    TextView crts_7_1_title, crts_7_2_title, crts_7_3_title, crts_8_1_title, crts_8_2_title, crts_8_3_title, crts_9_1_title, crts_9_2_title, crts_9_3_title;
    TextView crts_10_1_title, crts_10_2_title, crts_10_3_title;


    RadioGroup crts1_1, crts1_2, crts1_3, crts2_1, crts2_2, crts2_3, crts3_1, crts3_2, crts3_3, crts4_1, crts4_2, crts4_3, crts5_1, crts5_2, crts5_3;
    RadioGroup crts6_1, crts6_2, crts6_3, crts7_1, crts7_2, crts7_3, crts8_1, crts8_2, crts8_3, crts9_1, crts9_2, crts9_3, crts10_1, crts10_2, crts10_3;

    RadioButton crts1_1_0, crts1_1_1, crts1_1_2, crts1_1_3, crts1_1_4;
    RadioButton crts1_2_0, crts1_2_1, crts1_2_2, crts1_2_3, crts1_2_4;
    RadioButton crts1_3_0, crts1_3_1, crts1_3_2, crts1_3_3, crts1_3_4;

    RadioButton crts2_1_0, crts2_1_1, crts2_1_2, crts2_1_3, crts2_1_4;
    RadioButton crts2_2_0, crts2_2_1, crts2_2_2, crts2_2_3, crts2_2_4;
    RadioButton crts2_3_0, crts2_3_1, crts2_3_2, crts2_3_3, crts2_3_4;

    RadioButton crts3_1_0, crts3_1_1, crts3_1_2, crts3_1_3, crts3_1_4;
    RadioButton crts3_2_0, crts3_2_1, crts3_2_2, crts3_2_3, crts3_2_4;
    RadioButton crts3_3_0, crts3_3_1, crts3_3_2, crts3_3_3, crts3_3_4;

    RadioButton crts4_1_0, crts4_1_1, crts4_1_2, crts4_1_3, crts4_1_4;
    RadioButton crts4_2_0, crts4_2_1, crts4_2_2, crts4_2_3, crts4_2_4;
    RadioButton crts4_3_0, crts4_3_1, crts4_3_2, crts4_3_3, crts4_3_4;

    RadioButton crts5_1_0, crts5_1_1, crts5_1_2, crts5_1_3, crts5_1_4;
    RadioButton crts5_2_0, crts5_2_1, crts5_2_2, crts5_2_3, crts5_2_4;
    RadioButton crts5_3_0, crts5_3_1, crts5_3_2, crts5_3_3, crts5_3_4;

    RadioButton crts6_1_0, crts6_1_1, crts6_1_2, crts6_1_3, crts6_1_4;
    RadioButton crts6_2_0, crts6_2_1, crts6_2_2, crts6_2_3, crts6_2_4;
    RadioButton crts6_3_0, crts6_3_1, crts6_3_2, crts6_3_3, crts6_3_4;

    RadioButton crts7_1_0, crts7_1_1, crts7_1_2, crts7_1_3, crts7_1_4;
    RadioButton crts7_2_0, crts7_2_1, crts7_2_2, crts7_2_3, crts7_2_4;
    RadioButton crts7_3_0, crts7_3_1, crts7_3_2, crts7_3_3, crts7_3_4;

    RadioButton crts8_1_0, crts8_1_1, crts8_1_2, crts8_1_3, crts8_1_4;
    RadioButton crts8_2_0, crts8_2_1, crts8_2_2, crts8_2_3, crts8_2_4;
    RadioButton crts8_3_0, crts8_3_1, crts8_3_2, crts8_3_3, crts8_3_4;

    RadioButton crts9_1_0, crts9_1_1, crts9_1_2, crts9_1_3, crts9_1_4;
    RadioButton crts9_2_0, crts9_2_1, crts9_2_2, crts9_2_3, crts9_2_4;
    RadioButton crts9_3_0, crts9_3_1, crts9_3_2, crts9_3_3, crts9_3_4;

    RadioButton crts10_1_0, crts10_1_1, crts10_1_2, crts10_1_3, crts10_1_4;
    RadioButton crts10_2_0, crts10_2_1, crts10_2_2, crts10_2_3, crts10_2_4;
    RadioButton crts10_3_0, crts10_3_1, crts10_3_2, crts10_3_3, crts10_3_4;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    TaskListViewAdapter2 taskListViewAdapter2;
    ArrayList<TaskItem2> tasks = new ArrayList<TaskItem2>();
    ArrayList<TaskItem2> selected_tasks = new ArrayList<>();
    int pre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 초기 화면
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            pre = getArguments().getInt("pre", 0);
        } else {

        }
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.crts_parta_c_fragment, container, false);

        ConstraintLayout a_select_layout = (ConstraintLayout) view.findViewById(R.id.a_select_layout);
        ConstraintLayout b_select_layout = (ConstraintLayout) view.findViewById(R.id.b_select_layout);
        ConstraintLayout c_select_layout = (ConstraintLayout) view.findViewById(R.id.c_select_layout);
        ImageView a_select = (ImageView) view.findViewById(R.id.a_select);
        ImageView b_select = (ImageView) view.findViewById(R.id.b_select);
        ImageView c_select = (ImageView) view.findViewById(R.id.c_select);
        final CRTS_parta_Fragment crts_parta_fragment = (CRTS_parta_Fragment) getFragmentManager().findFragmentById(R.id.crts_test);

        Log.v("crts_num", "partA fragment2");
        nextButton = (Button) view.findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crts_parta_fragment.fragment0() == true) {
                    ((CRTSActivity)getActivity()).frgment1();
                }
                else {
                    Toast myToast = Toast.makeText(getContext(),"모든 문항을 확인 해주세요.", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });


        a_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(0);
            }
        });
        a_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(0);
            }
        });
        b_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(1);
            }
        });
        b_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(1);
            }
        });

        crts1_3 = (RadioGroup) view.findViewById(R.id.crg1_3);
        crts2_3 = (RadioGroup) view.findViewById(R.id.crg2_3);
        crts3_3 = (RadioGroup) view.findViewById(R.id.crg3_3);

        crts4_3 = (RadioGroup) view.findViewById(R.id.crg4_3);

        crts5_3 = (RadioGroup) view.findViewById(R.id.crg5_3);

        crts6_3 = (RadioGroup) view.findViewById(R.id.crg6_3);
        crts7_3 = (RadioGroup) view.findViewById(R.id.crg7_3);
        crts8_3 = (RadioGroup) view.findViewById(R.id.crg8_3);

        crts9_3 = (RadioGroup) view.findViewById(R.id.crg9_3);

        crts10_3 = (RadioGroup) view.findViewById(R.id.crg10_3);


        crts1_3_0 = (RadioButton) view.findViewById(R.id.crts1_3_0);
        crts1_3_1 = (RadioButton) view.findViewById(R.id.crts1_3_1);
        crts1_3_2 = (RadioButton) view.findViewById(R.id.crts1_3_2);
        crts1_3_3 = (RadioButton) view.findViewById(R.id.crts1_3_3);
        crts1_3_4 = (RadioButton) view.findViewById(R.id.crts1_3_4);

        crts2_3_0 = (RadioButton) view.findViewById(R.id.crts2_3_0);
        crts2_3_1 = (RadioButton) view.findViewById(R.id.crts2_3_1);
        crts2_3_2 = (RadioButton) view.findViewById(R.id.crts2_3_2);
        crts2_3_3 = (RadioButton) view.findViewById(R.id.crts2_3_3);
        crts2_3_4 = (RadioButton) view.findViewById(R.id.crts2_3_4);


        crts3_3_0 = (RadioButton) view.findViewById(R.id.crts3_3_0);
        crts3_3_1 = (RadioButton) view.findViewById(R.id.crts3_3_1);
        crts3_3_2 = (RadioButton) view.findViewById(R.id.crts3_3_2);
        crts3_3_3 = (RadioButton) view.findViewById(R.id.crts3_3_3);
        crts3_3_4 = (RadioButton) view.findViewById(R.id.crts3_3_4);


        crts4_3_0 = (RadioButton) view.findViewById(R.id.crts4_3_0);
        crts4_3_1 = (RadioButton) view.findViewById(R.id.crts4_3_1);
        crts4_3_2 = (RadioButton) view.findViewById(R.id.crts4_3_2);
        crts4_3_3 = (RadioButton) view.findViewById(R.id.crts4_3_3);
        crts4_3_4 = (RadioButton) view.findViewById(R.id.crts4_3_4);


        crts5_3_0 = (RadioButton) view.findViewById(R.id.crts5_3_0);
        crts5_3_1 = (RadioButton) view.findViewById(R.id.crts5_3_1);
        crts5_3_2 = (RadioButton) view.findViewById(R.id.crts5_3_2);
        crts5_3_3 = (RadioButton) view.findViewById(R.id.crts5_3_3);
        crts5_3_4 = (RadioButton) view.findViewById(R.id.crts5_3_4);

        crts6_3_0 = (RadioButton) view.findViewById(R.id.crts6_3_0);
        crts6_3_1 = (RadioButton) view.findViewById(R.id.crts6_3_1);
        crts6_3_2 = (RadioButton) view.findViewById(R.id.crts6_3_2);
        crts6_3_3 = (RadioButton) view.findViewById(R.id.crts6_3_3);
        crts6_3_4 = (RadioButton) view.findViewById(R.id.crts6_3_4);


        crts7_3_0 = (RadioButton) view.findViewById(R.id.crts7_3_0);
        crts7_3_1 = (RadioButton) view.findViewById(R.id.crts7_3_1);
        crts7_3_2 = (RadioButton) view.findViewById(R.id.crts7_3_2);
        crts7_3_3 = (RadioButton) view.findViewById(R.id.crts7_3_3);
        crts7_3_4 = (RadioButton) view.findViewById(R.id.crts7_3_4);

        crts8_3_0 = (RadioButton) view.findViewById(R.id.crts8_3_0);
        crts8_3_1 = (RadioButton) view.findViewById(R.id.crts8_3_1);
        crts8_3_2 = (RadioButton) view.findViewById(R.id.crts8_3_2);
        crts8_3_3 = (RadioButton) view.findViewById(R.id.crts8_3_3);
        crts8_3_4 = (RadioButton) view.findViewById(R.id.crts8_3_4);

        crts9_3_0 = (RadioButton) view.findViewById(R.id.crts9_3_0);
        crts9_3_1 = (RadioButton) view.findViewById(R.id.crts9_3_1);
        crts9_3_2 = (RadioButton) view.findViewById(R.id.crts9_3_2);
        crts9_3_3 = (RadioButton) view.findViewById(R.id.crts9_3_3);
        crts9_3_4 = (RadioButton) view.findViewById(R.id.crts9_3_4);

        crts10_3_0 = (RadioButton) view.findViewById(R.id.crts10_3_0);
        crts10_3_1 = (RadioButton) view.findViewById(R.id.crts10_3_1);
        crts10_3_2 = (RadioButton) view.findViewById(R.id.crts10_3_2);
        crts10_3_3 = (RadioButton) view.findViewById(R.id.crts10_3_3);
        crts10_3_4 = (RadioButton) view.findViewById(R.id.crts10_3_4);

            if (crts_parta_fragment.pre == 1) {
                if (crts_parta_fragment.c1_3 == 0) {
                    crts1_3_0.setChecked(true);
                } else if (crts_parta_fragment.c1_3 == 1) {
                    crts1_3_1.setChecked(true);
                } else if (crts_parta_fragment.c1_3 == 2) {
                    crts1_3_2.setChecked(true);
                } else if (crts_parta_fragment.c1_3 == 3) {
                    crts1_3_3.setChecked(true);
                } else if (crts_parta_fragment.c1_3 == 4) {
                    crts1_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c2_3==0){
                    crts2_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c2_3==1){
                    crts2_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c2_3==2){
                    crts2_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c2_3==3){
                    crts2_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c2_3==4){
                    crts2_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c3_3==0){
                    crts3_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c3_3==1){
                    crts3_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c3_3==2){
                    crts3_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c3_3==3){
                    crts3_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c3_3==4){
                    crts3_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c4_3==0){
                    crts4_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c4_3==1){
                    crts4_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c4_3==2){
                    crts4_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c4_3==3){
                    crts4_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c4_3==4){
                    crts4_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c5_3==0){
                    crts5_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c5_3==1){
                    crts5_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c5_3==2){
                    crts5_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c5_3==3){
                    crts5_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c5_3==4){
                    crts5_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c6_3==0){
                    crts6_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c6_3==1){
                    crts6_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c6_3==2){
                    crts6_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c6_3==3){
                    crts6_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c6_3==4){
                    crts6_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c7_3==0){
                    crts7_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c7_3==1){
                    crts7_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c7_3==2){
                    crts7_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c7_3==3){
                    crts7_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c7_3==4){
                    crts7_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c8_3==0){
                    crts8_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c8_3==1){
                    crts8_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c8_3==2){
                    crts8_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c8_3==3){
                    crts8_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c8_3==4){
                    crts8_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c9_3==0){
                    crts9_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c9_3==1){
                    crts9_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c9_3==2){
                    crts9_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c9_3==3){
                    crts9_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c9_3==4){
                    crts9_3_4.setChecked(true);
                }

                if(crts_parta_fragment.c10_3==0){
                    crts10_3_0.setChecked(true);
                }
                else if(crts_parta_fragment.c10_3==1){
                    crts10_3_1.setChecked(true);
                }
                else if(crts_parta_fragment.c10_3==2){
                    crts10_3_2.setChecked(true);
                }
                else if(crts_parta_fragment.c10_3==3){
                    crts10_3_3.setChecked(true);
                }
                else if(crts_parta_fragment.c10_3==4){
                    crts10_3_4.setChecked(true);
                }
            }
//            ((RadioButton)crts1_3.getChildAt(crts_parta_fragment.c1_3)).setChecked(true);
//            ((RadioButton)crts2_3.getChildAt(crts_parta_fragment.c2_3)).setChecked(true);
//            ((RadioButton)crts3_3.getChildAt(crts_parta_fragment.c3_3)).setChecked(true);
//            ((RadioButton)crts4_3.getChildAt(crts_parta_fragment.c4_3)).setChecked(true);
//            ((RadioButton)crts5_3.getChildAt(crts_parta_fragment.c5_3)).setChecked(true);
//            ((RadioButton)crts6_3.getChildAt(crts_parta_fragment.c6_3)).setChecked(true);
//            ((RadioButton)crts7_3.getChildAt(crts_parta_fragment.c7_3)).setChecked(true);
//            ((RadioButton)crts8_3.getChildAt(crts_parta_fragment.c8_3)).setChecked(true);
//            ((RadioButton)crts9_3.getChildAt(crts_parta_fragment.c9_3)).setChecked(true);
//            ((RadioButton)crts10_3.getChildAt(crts_parta_fragment.c10_3)).setChecked(true);
            crts1_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts1_3_0) {
                        crts_parta_fragment.c1_3 = 0;
                    } else if (checkedId == R.id.crts1_3_1) {
                        crts_parta_fragment.c1_3 = 1;
                    } else if (checkedId == R.id.crts1_3_2) {
                        crts_parta_fragment.c1_3 = 2;
                    } else if (checkedId == R.id.crts1_3_3) {
                        crts_parta_fragment.c1_3 = 3;
                    } else if (checkedId == R.id.crts1_3_4) {
                        crts_parta_fragment.c1_3 = 4;
                    }
                }
            });

            crts2_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts2_3_0) {
                        crts_parta_fragment.c2_3 = 0;
                    } else if (checkedId == R.id.crts2_3_1) {
                        crts_parta_fragment.c2_3 = 1;
                    } else if (checkedId == R.id.crts2_3_2) {
                        crts_parta_fragment.c2_3 = 2;
                    } else if (checkedId == R.id.crts2_3_3) {
                        crts_parta_fragment.c2_3 = 3;
                    } else if (checkedId == R.id.crts2_3_4) {
                        crts_parta_fragment.c2_3 = 4;
                    }

                }
            });

            crts3_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts3_3_0) {
                        crts_parta_fragment.c3_3 = 0;
                    } else if (checkedId == R.id.crts3_3_1) {
                        crts_parta_fragment.c3_3 = 1;
                    } else if (checkedId == R.id.crts3_3_2) {
                        crts_parta_fragment.c3_3 = 2;
                    } else if (checkedId == R.id.crts3_3_3) {
                        crts_parta_fragment.c3_3 = 3;
                    } else if (checkedId == R.id.crts3_3_4) {
                        crts_parta_fragment.c3_3 = 4;
                    }

                }
            });

            crts4_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts4_3_0) {
                        crts_parta_fragment.c4_3 = 0;
                    } else if (checkedId == R.id.crts4_3_1) {
                        crts_parta_fragment.c4_3 = 1;
                    } else if (checkedId == R.id.crts4_3_2) {
                        crts_parta_fragment.c4_3 = 2;
                    } else if (checkedId == R.id.crts4_3_3) {
                        crts_parta_fragment.c4_3 = 3;
                    } else if (checkedId == R.id.crts4_3_4) {
                        crts_parta_fragment.c4_3 = 4;
                    }

                }
            });
            crts5_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts5_3_0) {
                        crts_parta_fragment.c5_3 = 0;
                    } else if (checkedId == R.id.crts5_3_1) {
                        crts_parta_fragment.c5_3 = 1;
                    } else if (checkedId == R.id.crts5_3_2) {
                        crts_parta_fragment.c5_3 = 2;
                    } else if (checkedId == R.id.crts5_3_3) {
                        crts_parta_fragment.c5_3 = 3;
                    } else if (checkedId == R.id.crts5_3_4) {
                        crts_parta_fragment.c5_3 = 4;
                    }

                }
            });
            crts6_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts6_3_0) {
                        crts_parta_fragment.c6_3 = 0;
                    } else if (checkedId == R.id.crts6_3_1) {
                        crts_parta_fragment.c6_3 = 1;
                    } else if (checkedId == R.id.crts6_3_2) {
                        crts_parta_fragment.c6_3 = 2;
                    } else if (checkedId == R.id.crts6_3_3) {
                        crts_parta_fragment.c6_3 = 3;
                    } else if (checkedId == R.id.crts6_3_4) {
                        crts_parta_fragment.c6_3 = 4;
                    }

                }
            });

            crts7_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts7_3_0) {
                        crts_parta_fragment.c7_3 = 0;
                    } else if (checkedId == R.id.crts7_3_1) {
                        crts_parta_fragment.c7_3 = 1;
                    } else if (checkedId == R.id.crts7_3_2) {
                        crts_parta_fragment.c7_3 = 2;
                    } else if (checkedId == R.id.crts7_3_3) {
                        crts_parta_fragment.c7_3 = 3;
                    } else if (checkedId == R.id.crts7_3_4) {
                        crts_parta_fragment.c7_3 = 4;
                    }

                }
            });

            crts8_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts8_3_0) {
                        crts_parta_fragment.c8_3 = 0;
                    } else if (checkedId == R.id.crts8_3_1) {
                        crts_parta_fragment.c8_3 = 1;
                    } else if (checkedId == R.id.crts8_3_2) {
                        crts_parta_fragment.c8_3 = 2;
                    } else if (checkedId == R.id.crts8_3_3) {
                        crts_parta_fragment.c8_3 = 3;
                    } else if (checkedId == R.id.crts8_3_4) {
                        crts_parta_fragment.c8_3 = 4;
                    }

                }
            });
            crts9_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts9_3_0) {
                        crts_parta_fragment.c9_3 = 0;
                    } else if (checkedId == R.id.crts9_3_1) {
                        crts_parta_fragment.c9_3 = 1;
                    } else if (checkedId == R.id.crts9_3_2) {
                        crts_parta_fragment.c9_3 = 2;
                    } else if (checkedId == R.id.crts9_3_3) {
                        crts_parta_fragment.c9_3 = 3;
                    } else if (checkedId == R.id.crts9_3_4) {
                        crts_parta_fragment.c9_3 = 4;
                    }

                }
            });
            crts10_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.crts10_3_0) {
                        crts_parta_fragment.c10_3 = 0;
                    } else if (checkedId == R.id.crts10_3_1) {
                        crts_parta_fragment.c10_3 = 1;
                    } else if (checkedId == R.id.crts10_3_2) {
                        crts_parta_fragment.c10_3 = 2;
                    } else if (checkedId == R.id.crts10_3_3) {
                        crts_parta_fragment.c10_3 = 3;
                    } else if (checkedId == R.id.crts10_3_4) {
                        crts_parta_fragment.c10_3 = 4;
                    }

                }
            });
            return view;

        }


}