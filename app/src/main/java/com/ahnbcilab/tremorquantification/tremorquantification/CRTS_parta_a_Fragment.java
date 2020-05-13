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

public class CRTS_parta_a_Fragment extends Fragment {

    String Clinic_ID, PatientName;
    String crts_num;

    boolean bool = true;

    RadioGroup crts1_1, crts2_1, crts3_1, crts4_1, crts5_1;
    RadioGroup crts6_1, crts7_1, crts8_1, crts9_1, crts10_1;

    public RadioButton crts1_1_0, crts1_1_1, crts1_1_2, crts1_1_3, crts1_1_4;
    public RadioButton crts2_1_0, crts2_1_1, crts2_1_2, crts2_1_3, crts2_1_4;
    public RadioButton crts3_1_0, crts3_1_1, crts3_1_2, crts3_1_3, crts3_1_4;
    public RadioButton crts4_1_0, crts4_1_1, crts4_1_2, crts4_1_3, crts4_1_4;
    public RadioButton crts5_1_0, crts5_1_1, crts5_1_2, crts5_1_3, crts5_1_4;
    public RadioButton crts6_1_0, crts6_1_1, crts6_1_2, crts6_1_3, crts6_1_4;
    public RadioButton crts7_1_0, crts7_1_1, crts7_1_2, crts7_1_3, crts7_1_4;
    public RadioButton crts8_1_0, crts8_1_1, crts8_1_2, crts8_1_3, crts8_1_4;
    public RadioButton crts9_1_0, crts9_1_1, crts9_1_2, crts9_1_3, crts9_1_4;
    public RadioButton crts10_1_0, crts10_1_1, crts10_1_2, crts10_1_3, crts10_1_4;

    CRTS_parta_Fragment crts_parta_fragment ;
    int pre ;
    Button nextButton ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 초기 화면
        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            pre = getArguments().getInt("pre", 0) ;
        }

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.crts_parta_a_fragment, container, false);

        ConstraintLayout a_select_layout = (ConstraintLayout) view.findViewById(R.id.a_select_layout) ;
        ConstraintLayout b_select_layout = (ConstraintLayout) view.findViewById(R.id.b_select_layout) ;
        ConstraintLayout c_select_layout = (ConstraintLayout) view.findViewById(R.id.c_select_layout) ;
        ImageView a_select = (ImageView) view.findViewById(R.id.a_select) ;
        ImageView b_select = (ImageView) view.findViewById(R.id.b_select) ;
        ImageView c_select = (ImageView) view.findViewById(R.id.c_select) ;

        crts_parta_fragment = (CRTS_parta_Fragment) getFragmentManager().findFragmentById(R.id.crts_test);

        b_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(1);

            }
        }) ;
        b_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(1);
            }
        }) ;
        c_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(2);
            }
        }) ;
        c_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(2);
            }
        }) ;

        nextButton = (Button) view.findViewById(R.id.next) ;
        nextButton.setVisibility(View.GONE);
        crts1_1 = (RadioGroup) view.findViewById(R.id.crg1_1);
        crts2_1 = (RadioGroup) view.findViewById(R.id.crg2_1);
        crts3_1 = (RadioGroup) view.findViewById(R.id.crg3_1);
        crts4_1 = (RadioGroup) view.findViewById(R.id.crg4_1);
        crts5_1 = (RadioGroup) view.findViewById(R.id.crg5_1);
        crts6_1 = (RadioGroup) view.findViewById(R.id.crg6_1);
        crts7_1 = (RadioGroup) view.findViewById(R.id.crg7_1);
        crts8_1 = (RadioGroup) view.findViewById(R.id.crg8_1);
        crts9_1 = (RadioGroup) view.findViewById(R.id.crg9_1);
        crts10_1 = (RadioGroup) view.findViewById(R.id.crg10_1);


        crts1_1_0 = (RadioButton)view.findViewById(R.id.crts1_1_0);
        crts1_1_1 = (RadioButton)view.findViewById(R.id.crts1_1_1);
        crts1_1_2 = (RadioButton)view.findViewById(R.id.crts1_1_2);
        crts1_1_3 = (RadioButton)view.findViewById(R.id.crts1_1_3);
        crts1_1_4 = (RadioButton)view.findViewById(R.id.crts1_1_4);


        crts2_1_0 = (RadioButton)view.findViewById(R.id.crts2_1_0);
        crts2_1_1 = (RadioButton)view.findViewById(R.id.crts2_1_1);
        crts2_1_2 = (RadioButton)view.findViewById(R.id.crts2_1_2);
        crts2_1_3 = (RadioButton)view.findViewById(R.id.crts2_1_3);
        crts2_1_4 = (RadioButton)view.findViewById(R.id.crts2_1_4);


        crts3_1_0 = (RadioButton)view.findViewById(R.id.crts3_1_0);
        crts3_1_1 = (RadioButton)view.findViewById(R.id.crts3_1_1);
        crts3_1_2 = (RadioButton)view.findViewById(R.id.crts3_1_2);
        crts3_1_3 = (RadioButton)view.findViewById(R.id.crts3_1_3);
        crts3_1_4 = (RadioButton)view.findViewById(R.id.crts3_1_4);



        crts4_1_0 = (RadioButton)view.findViewById(R.id.crts4_1_0);
        crts4_1_1 = (RadioButton)view.findViewById(R.id.crts4_1_1);
        crts4_1_2 = (RadioButton)view.findViewById(R.id.crts4_1_2);
        crts4_1_3 = (RadioButton)view.findViewById(R.id.crts4_1_3);
        crts4_1_4 = (RadioButton)view.findViewById(R.id.crts4_1_4);

        crts5_1_0 = (RadioButton)view.findViewById(R.id.crts5_1_0);
        crts5_1_1 = (RadioButton)view.findViewById(R.id.crts5_1_1);
        crts5_1_2 = (RadioButton)view.findViewById(R.id.crts5_1_2);
        crts5_1_3 = (RadioButton)view.findViewById(R.id.crts5_1_3);
        crts5_1_4 = (RadioButton)view.findViewById(R.id.crts5_1_4);



        crts6_1_0 = (RadioButton)view.findViewById(R.id.crts6_1_0);
        crts6_1_1 = (RadioButton)view.findViewById(R.id.crts6_1_1);
        crts6_1_2 = (RadioButton)view.findViewById(R.id.crts6_1_2);
        crts6_1_3 = (RadioButton)view.findViewById(R.id.crts6_1_3);
        crts6_1_4 = (RadioButton)view.findViewById(R.id.crts6_1_4);


        crts7_1_0 = (RadioButton)view.findViewById(R.id.crts7_1_0);
        crts7_1_1 = (RadioButton)view.findViewById(R.id.crts7_1_1);
        crts7_1_2 = (RadioButton)view.findViewById(R.id.crts7_1_2);
        crts7_1_3 = (RadioButton)view.findViewById(R.id.crts7_1_3);
        crts7_1_4 = (RadioButton)view.findViewById(R.id.crts7_1_4);


        crts8_1_0 = (RadioButton)view.findViewById(R.id.crts8_1_0);
        crts8_1_1 = (RadioButton)view.findViewById(R.id.crts8_1_1);
        crts8_1_2 = (RadioButton)view.findViewById(R.id.crts8_1_2);
        crts8_1_3 = (RadioButton)view.findViewById(R.id.crts8_1_3);
        crts8_1_4 = (RadioButton)view.findViewById(R.id.crts8_1_4);

        crts9_1_0 = (RadioButton)view.findViewById(R.id.crts9_1_0);
        crts9_1_1 = (RadioButton)view.findViewById(R.id.crts9_1_1);
        crts9_1_2 = (RadioButton)view.findViewById(R.id.crts9_1_2);
        crts9_1_3 = (RadioButton)view.findViewById(R.id.crts9_1_3);
        crts9_1_4 = (RadioButton)view.findViewById(R.id.crts9_1_4);

        crts10_1_0 = (RadioButton)view.findViewById(R.id.crts10_1_0);
        crts10_1_1 = (RadioButton)view.findViewById(R.id.crts10_1_1);
        crts10_1_2 = (RadioButton)view.findViewById(R.id.crts10_1_2);
        crts10_1_3 = (RadioButton)view.findViewById(R.id.crts10_1_3);
        crts10_1_4 = (RadioButton)view.findViewById(R.id.crts10_1_4);
        if(crts_parta_fragment.pre==1) {
            if(crts_parta_fragment.c1_1==0){
                crts1_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c1_1==1){
                crts1_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c1_1==2){
                crts1_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c1_1==3){
                crts1_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c1_1==4){
                crts1_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c2_1==0){
                crts2_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c2_1==1){
                crts2_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c2_1==2){
                crts2_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c2_1==3){
                crts2_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c2_1==4){
                crts2_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c3_1==0){
                crts3_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c3_1==1){
                crts3_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c3_1==2){
                crts3_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c3_1==3){
                crts3_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c3_1==4){
                crts3_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c4_1==0){
                crts4_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c4_1==1){
                crts4_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c4_1==2){
                crts4_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c4_1==3){
                crts4_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c4_1==4){
                crts4_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c5_1==0){
                crts5_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c5_1==1){
                crts5_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c5_1==2){
                crts5_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c5_1==3){
                crts5_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c5_1==4){
                crts5_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c6_1==0){
                crts6_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c6_1==1){
                crts6_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c6_1==2){
                crts6_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c6_1==3){
                crts6_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c6_1==4){
                crts6_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c7_1==0){
                crts7_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c7_1==1){
                crts7_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c7_1==2){
                crts7_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c7_1==3){
                crts7_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c7_1==4){
                crts7_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c8_1==0){
                crts8_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c8_1==1){
                crts8_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c8_1==2){
                crts8_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c8_1==3){
                crts8_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c8_1==4){
                crts8_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c9_1==0){
                crts9_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c9_1==1){
                crts9_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c9_1==2){
                crts9_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c9_1==3){
                crts9_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c9_1==4){
                crts9_1_4.setChecked(true);
            }

            if(crts_parta_fragment.c10_1==0){
                crts10_1_0.setChecked(true);
            }
            else if(crts_parta_fragment.c10_1==1){
                crts10_1_1.setChecked(true);
            }
            else if(crts_parta_fragment.c10_1==2){
                crts10_1_2.setChecked(true);
            }
            else if(crts_parta_fragment.c10_1==3){
                crts10_1_3.setChecked(true);
            }
            else if(crts_parta_fragment.c10_1==4){
                crts10_1_4.setChecked(true);
            }
            ((RadioButton)crts1_1.getChildAt(crts_parta_fragment.c1_1)).setChecked(true);
//            ((RadioButton)crts2_1.getChildAt(crts_parta_fragment.c2_1)).setChecked(true);
//            ((RadioButton)crts3_1.getChildAt(crts_parta_fragment.c3_1)).setChecked(true);
//            ((RadioButton)crts4_1.getChildAt(crts_parta_fragment.c4_1)).setChecked(true);
//            ((RadioButton)crts5_1.getChildAt(crts_parta_fragment.c5_1)).setChecked(true);
//            ((RadioButton)crts6_1.getChildAt(crts_parta_fragment.c6_1)).setChecked(true);
//            ((RadioButton)crts7_1.getChildAt(crts_parta_fragment.c7_1)).setChecked(true);
//            ((RadioButton)crts8_1.getChildAt(crts_parta_fragment.c8_1)).setChecked(true);
//            ((RadioButton)crts9_1.getChildAt(crts_parta_fragment.c9_1)).setChecked(true);
//            ((RadioButton)crts10_1.getChildAt(crts_parta_fragment.c10_1)).setChecked(true);
        }
        crts1_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts1_1_0) {
                    crts_parta_fragment.c1_1=0 ;
                }
                else if(checkedId ==R.id.crts1_1_1) {
                    crts_parta_fragment.c1_1 = 1 ;
                }
                else if(checkedId ==R.id.crts1_1_2) {
                    crts_parta_fragment.c1_1 = 2 ;
                }
                else if(checkedId ==R.id.crts1_1_3) {
                    crts_parta_fragment.c1_1 = 3 ;
                }
                else if(checkedId ==R.id.crts1_1_4) {
                    crts_parta_fragment.c1_1 = 4 ;
                }
            }
        });

        crts2_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts2_1_0){
                    crts_parta_fragment.c2_1=0 ;
                }
                else if(checkedId==R.id.crts2_1_1){
                    crts_parta_fragment.c2_1 = 1;
                }
                else if(checkedId==R.id.crts2_1_2){
                    crts_parta_fragment.c2_1 = 2;
                }
                else if(checkedId==R.id.crts2_1_3){
                    crts_parta_fragment.c2_1 =3;
                }
                else if(checkedId==R.id.crts2_1_4){
                    crts_parta_fragment.c2_1 =4;
                }

            }
        });

        crts3_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts3_1_0){
                    crts_parta_fragment.c3_1=0 ;
                }
                else if(checkedId==R.id.crts3_1_1){
                    crts_parta_fragment.c3_1 = 1;
                }
                else if(checkedId==R.id.crts3_1_2){
                    crts_parta_fragment.c3_1 = 2;
                }
                else if(checkedId==R.id.crts3_1_3){
                    crts_parta_fragment.c3_1 =3;
                }
                else if(checkedId==R.id.crts3_1_4){
                    crts_parta_fragment.c3_1 =4;
                }

            }
        });

        crts4_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts4_1_0){
                    crts_parta_fragment.c4_1=0 ;
                }
                else if(checkedId==R.id.crts4_1_1){
                    crts_parta_fragment.c4_1 = 1;
                }
                else if(checkedId==R.id.crts4_1_2){
                    crts_parta_fragment.c4_1 = 2;
                }
                else if(checkedId==R.id.crts4_1_3){
                    crts_parta_fragment.c4_1 =3;
                }
                else if(checkedId==R.id.crts4_1_4){
                    crts_parta_fragment.c4_1 =4;
                }

            }
        });
        crts5_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts5_1_0){
                    crts_parta_fragment.c5_1=0 ;
                }
                else if(checkedId==R.id.crts5_1_1){
                    crts_parta_fragment.c5_1 = 1;
                }
                else if(checkedId==R.id.crts5_1_2){
                    crts_parta_fragment.c5_1 = 2;
                }
                else if(checkedId==R.id.crts5_1_3){
                    crts_parta_fragment.c5_1 =3;
                }
                else if(checkedId==R.id.crts5_1_4){
                    crts_parta_fragment.c5_1 =4;
                }

            }
        });
        crts6_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts6_1_0){
                    crts_parta_fragment.c6_1=0 ;
                }
                else if(checkedId==R.id.crts6_1_1){
                    crts_parta_fragment.c6_1 = 1;
                }
                else if(checkedId==R.id.crts6_1_2){
                    crts_parta_fragment.c6_1 = 2;
                }
                else if(checkedId==R.id.crts6_1_3){
                    crts_parta_fragment.c6_1 =3;
                }
                else if(checkedId==R.id.crts6_1_4){
                    crts_parta_fragment.c6_1 =4;
                }

            }
        });

        crts7_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts7_1_0){
                    crts_parta_fragment.c7_1=0 ;
                }
                else if(checkedId==R.id.crts7_1_1){
                    crts_parta_fragment.c7_1 = 1;
                }
                else if(checkedId==R.id.crts7_1_2){
                    crts_parta_fragment.c7_1 = 2;
                }
                else if(checkedId==R.id.crts7_1_3){
                    crts_parta_fragment.c7_1 =3;
                }
                else if(checkedId==R.id.crts7_1_4){
                    crts_parta_fragment.c7_1 =4;
                }

            }
        });

        crts8_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts8_1_0){
                    crts_parta_fragment.c8_1=0 ;
                }
                else if(checkedId==R.id.crts8_1_1){
                    crts_parta_fragment.c8_1 = 1;
                }
                else if(checkedId==R.id.crts8_1_2){
                    crts_parta_fragment.c8_1 = 2;
                }
                else if(checkedId==R.id.crts8_1_3){
                    crts_parta_fragment.c8_1 =3;
                }
                else if(checkedId==R.id.crts8_1_4){
                    crts_parta_fragment.c8_1 =4;
                }

            }
        });
        crts9_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts9_1_0){
                    crts_parta_fragment.c9_1=0 ;
                }
                else if(checkedId==R.id.crts9_1_1){
                    crts_parta_fragment.c9_1 = 1;
                }
                else if(checkedId==R.id.crts9_1_2){
                    crts_parta_fragment.c9_1 = 2;
                }
                else if(checkedId==R.id.crts9_1_3){
                    crts_parta_fragment.c9_1 =3;
                }
                else if(checkedId==R.id.crts9_1_4){
                    crts_parta_fragment.c9_1 =4;
                }

            }
        });
        crts10_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts10_1_0){
                    crts_parta_fragment.c10_1=0 ;
                }
                else if(checkedId==R.id.crts10_1_1){
                    crts_parta_fragment.c10_1 = 1;
                }
                else if(checkedId==R.id.crts10_1_2){
                    crts_parta_fragment.c10_1 = 2;
                }
                else if(checkedId==R.id.crts10_1_3){
                    crts_parta_fragment.c10_1 =3;
                }
                else if(checkedId==R.id.crts10_1_4){
                    crts_parta_fragment.c10_1 =4;
                }

            }
        });
        return view;

    }


}

