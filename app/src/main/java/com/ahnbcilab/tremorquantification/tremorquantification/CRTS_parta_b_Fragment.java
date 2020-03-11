package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CRTS_parta_b_Fragment extends Fragment {
    Context context;

    String Clinic_ID;
    String PatientName;
    String path;
    View view;

    int c1_2;
    int c2_2;
    int c3_2;
    int c4_2;
    int c5_2;
    int c6_2;
    int c7_2;
    int c8_2;
    int c9_2;
    int c10_2;

    CRTS_parta_a_Fragment frag1 ;
    CRTS_parta_b_Fragment frag2 ;
    CRTS_parta_c_Fragment frag3 ;

    boolean bool = true;


    RadioGroup  crts1_2, crts2_2, crts3_2, crts4_2, crts5_2;
    RadioGroup crts6_2, crts7_2, crts8_2, crts9_2, crts10_2;

    RadioButton crts1_2_0, crts1_2_1, crts1_2_2, crts1_2_3, crts1_2_4;
    RadioButton crts2_2_0, crts2_2_1, crts2_2_2, crts2_2_3, crts2_2_4;
    RadioButton crts3_2_0, crts3_2_1, crts3_2_2, crts3_2_3, crts3_2_4;
    RadioButton crts4_2_0, crts4_2_1, crts4_2_2, crts4_2_3, crts4_2_4;
    RadioButton crts5_2_0, crts5_2_1, crts5_2_2, crts5_2_3, crts5_2_4;
    RadioButton crts6_2_0, crts6_2_1, crts6_2_2, crts6_2_3, crts6_2_4;
    RadioButton crts7_2_0, crts7_2_1, crts7_2_2, crts7_2_3, crts7_2_4;
    RadioButton crts8_2_0, crts8_2_1, crts8_2_2, crts8_2_3, crts8_2_4;
    RadioButton crts9_2_0, crts9_2_1, crts9_2_2, crts9_2_3, crts9_2_4;
    RadioButton crts10_2_0, crts10_2_1, crts10_2_2, crts10_2_3, crts10_2_4;
    Button nextButton ;
    CRTS_parta_Fragment crts_parta_fragment ;
    int pre ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 초기 화면
        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            pre = getArguments().getInt("pre", 0) ;
        }
        else{

        }

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.crts_parta_b_fragment, container, false);
        Log.v("crts_num", "partA fragment2");

        nextButton = (Button) view.findViewById(R.id.next) ;
        nextButton.setVisibility(View.GONE);

        ConstraintLayout a_select_layout = (ConstraintLayout) view.findViewById(R.id.a_select_layout) ;
        ConstraintLayout b_select_layout = (ConstraintLayout) view.findViewById(R.id.b_select_layout) ;
        ConstraintLayout c_select_layout = (ConstraintLayout) view.findViewById(R.id.c_select_layout) ;
        ImageView a_select = (ImageView) view.findViewById(R.id.a_select) ;
        ImageView b_select = (ImageView) view.findViewById(R.id.b_select) ;
        ImageView c_select = (ImageView) view.findViewById(R.id.c_select) ;

        crts_parta_fragment = (CRTS_parta_Fragment) getFragmentManager().findFragmentById(R.id.crts_test);
        a_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(0);
            }
        }) ;
        a_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crts_parta_fragment.pager.setCurrentItem(0);
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

        crts1_2 = (RadioGroup) view.findViewById(R.id.crg1_2);
        crts2_2 = (RadioGroup) view.findViewById(R.id.crg2_2);
        crts3_2 = (RadioGroup) view.findViewById(R.id.crg3_2);
        crts4_2 = (RadioGroup) view.findViewById(R.id.crg4_2);
        crts5_2 = (RadioGroup) view.findViewById(R.id.crg5_2);
        crts6_2 = (RadioGroup) view.findViewById(R.id.crg6_2);
        crts7_2 = (RadioGroup) view.findViewById(R.id.crg7_2);
        crts8_2 = (RadioGroup) view.findViewById(R.id.crg8_2);
        crts9_2 = (RadioGroup) view.findViewById(R.id.crg9_2);
        crts10_2 = (RadioGroup) view.findViewById(R.id.crg10_2);

        crts1_2_0 = (RadioButton)view.findViewById(R.id.crts1_2_0);
        crts1_2_1 = (RadioButton)view.findViewById(R.id.crts1_2_1);
        crts1_2_2 = (RadioButton)view.findViewById(R.id.crts1_2_2);
        crts1_2_3 = (RadioButton)view.findViewById(R.id.crts1_2_3);
        crts1_2_4 = (RadioButton)view.findViewById(R.id.crts1_2_4);

        crts2_2_0 = (RadioButton)view.findViewById(R.id.crts2_2_0);
        crts2_2_1 = (RadioButton)view.findViewById(R.id.crts2_2_1);
        crts2_2_2 = (RadioButton)view.findViewById(R.id.crts2_2_2);
        crts2_2_3 = (RadioButton)view.findViewById(R.id.crts2_2_3);
        crts2_2_4 = (RadioButton)view.findViewById(R.id.crts2_2_4);

        crts3_2_0 = (RadioButton)view.findViewById(R.id.crts3_2_0);
        crts3_2_1 = (RadioButton)view.findViewById(R.id.crts3_2_1);
        crts3_2_2 = (RadioButton)view.findViewById(R.id.crts3_2_2);
        crts3_2_3 = (RadioButton)view.findViewById(R.id.crts3_2_3);
        crts3_2_4 = (RadioButton)view.findViewById(R.id.crts3_2_4);

        crts4_2_0 = (RadioButton)view.findViewById(R.id.crts4_2_0);
        crts4_2_1 = (RadioButton)view.findViewById(R.id.crts4_2_1);
        crts4_2_2 = (RadioButton)view.findViewById(R.id.crts4_2_2);
        crts4_2_3 = (RadioButton)view.findViewById(R.id.crts4_2_3);
        crts4_2_4 = (RadioButton)view.findViewById(R.id.crts4_2_4);

        crts5_2_0 = (RadioButton)view.findViewById(R.id.crts5_2_0);
        crts5_2_1 = (RadioButton)view.findViewById(R.id.crts5_2_1);
        crts5_2_2 = (RadioButton)view.findViewById(R.id.crts5_2_2);
        crts5_2_3 = (RadioButton)view.findViewById(R.id.crts5_2_3);
        crts5_2_4 = (RadioButton)view.findViewById(R.id.crts5_2_4);


        crts6_2_0 = (RadioButton)view.findViewById(R.id.crts6_2_0);
        crts6_2_1 = (RadioButton)view.findViewById(R.id.crts6_2_1);
        crts6_2_2 = (RadioButton)view.findViewById(R.id.crts6_2_2);
        crts6_2_3 = (RadioButton)view.findViewById(R.id.crts6_2_3);
        crts6_2_4 = (RadioButton)view.findViewById(R.id.crts6_2_4);

        crts7_2_0 = (RadioButton)view.findViewById(R.id.crts7_2_0);
        crts7_2_1 = (RadioButton)view.findViewById(R.id.crts7_2_1);
        crts7_2_2 = (RadioButton)view.findViewById(R.id.crts7_2_2);
        crts7_2_3 = (RadioButton)view.findViewById(R.id.crts7_2_3);
        crts7_2_4 = (RadioButton)view.findViewById(R.id.crts7_2_4);


        crts8_2_0 = (RadioButton)view.findViewById(R.id.crts8_2_0);
        crts8_2_1 = (RadioButton)view.findViewById(R.id.crts8_2_1);
        crts8_2_2 = (RadioButton)view.findViewById(R.id.crts8_2_2);
        crts8_2_3 = (RadioButton)view.findViewById(R.id.crts8_2_3);
        crts8_2_4 = (RadioButton)view.findViewById(R.id.crts8_2_4);


        crts9_2_0 = (RadioButton)view.findViewById(R.id.crts9_2_0);
        crts9_2_1 = (RadioButton)view.findViewById(R.id.crts9_2_1);
        crts9_2_2 = (RadioButton)view.findViewById(R.id.crts9_2_2);
        crts9_2_3 = (RadioButton)view.findViewById(R.id.crts9_2_3);
        crts9_2_4 = (RadioButton)view.findViewById(R.id.crts9_2_4);

        crts10_2_0 = (RadioButton)view.findViewById(R.id.crts10_2_0);
        crts10_2_1 = (RadioButton)view.findViewById(R.id.crts10_2_1);
        crts10_2_2 = (RadioButton)view.findViewById(R.id.crts10_2_2);
        crts10_2_3 = (RadioButton)view.findViewById(R.id.crts10_2_3);
        crts10_2_4 = (RadioButton)view.findViewById(R.id.crts10_2_4);
        if(crts_parta_fragment.pre==1) {
            if(crts_parta_fragment.c1_2==0){
                crts1_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c1_2==1){
                crts1_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c1_2==2){
                crts1_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c1_2==3){
                crts1_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c1_2==4){
                crts1_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c2_2==0){
                crts2_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c2_2==1){
                crts2_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c2_2==2){
                crts2_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c2_2==3){
                crts2_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c2_2==4){
                crts2_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c3_2==0){
                crts3_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c3_2==1){
                crts3_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c3_2==2){
                crts3_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c3_2==3){
                crts3_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c3_2==4){
                crts3_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c4_2==0){
                crts4_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c4_2==1){
                crts4_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c4_2==2){
                crts4_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c4_2==3){
                crts4_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c4_2==4){
                crts4_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c5_2==0){
                crts5_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c5_2==1){
                crts5_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c5_2==2){
                crts5_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c5_2==3){
                crts5_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c5_2==4){
                crts5_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c6_2==0){
                crts6_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c6_2==1){
                crts6_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c6_2==2){
                crts6_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c6_2==3){
                crts6_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c6_2==4){
                crts6_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c7_2==0){
                crts7_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c7_2==1){
                crts7_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c7_2==2){
                crts7_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c7_2==3){
                crts7_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c7_2==4){
                crts7_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c8_2==0){
                crts8_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c8_2==1){
                crts8_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c8_2==2){
                crts8_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c8_2==3){
                crts8_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c8_2==4){
                crts8_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c9_2==0){
                crts9_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c9_2==1){
                crts9_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c9_2==2){
                crts9_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c9_2==3){
                crts9_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c9_2==4){
                crts9_2_4.setChecked(true);
            }

            if(crts_parta_fragment.c10_2==0){
                crts10_2_0.setChecked(true);
            }
            else if(crts_parta_fragment.c10_2==1){
                crts10_2_1.setChecked(true);
            }
            else if(crts_parta_fragment.c10_2==2){
                crts10_2_2.setChecked(true);
            }
            else if(crts_parta_fragment.c10_2==3){
                crts10_2_3.setChecked(true);
            }
            else if(crts_parta_fragment.c10_2==4){
                crts10_2_4.setChecked(true);
            }
//            ((RadioButton)crts1_2.getChildAt(crts_parta_fragment.c1_2)).setChecked(true);
//            ((RadioButton)crts2_2.getChildAt(crts_parta_fragment.c2_2)).setChecked(true);
//            ((RadioButton)crts3_2.getChildAt(crts_parta_fragment.c3_2)).setChecked(true);
//            ((RadioButton)crts4_2.getChildAt(crts_parta_fragment.c4_2)).setChecked(true);
//            ((RadioButton)crts5_2.getChildAt(crts_parta_fragment.c5_2)).setChecked(true);
//            ((RadioButton)crts6_2.getChildAt(crts_parta_fragment.c6_2)).setChecked(true);
//            ((RadioButton)crts7_2.getChildAt(crts_parta_fragment.c7_2)).setChecked(true);
//            ((RadioButton)crts8_2.getChildAt(crts_parta_fragment.c8_2)).setChecked(true);
//            ((RadioButton)crts9_2.getChildAt(crts_parta_fragment.c9_2)).setChecked(true);
//            ((RadioButton)crts10_2.getChildAt(crts_parta_fragment.c10_2)).setChecked(true);
        }
        crts1_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts1_2_0) {
                    crts_parta_fragment.c1_2=0 ;
                }
                else if(checkedId ==R.id.crts1_2_1) {
                    crts_parta_fragment.c1_2 = 1 ;
                }
                else if(checkedId ==R.id.crts1_2_2) {
                    crts_parta_fragment.c1_2 = 2 ;
                }
                else if(checkedId ==R.id.crts1_2_3) {
                    crts_parta_fragment.c1_2 = 3 ;
                }
                else if(checkedId ==R.id.crts1_2_4) {
                    crts_parta_fragment.c1_2 = 4 ;
                }
            }
        });

        crts2_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts2_2_0){
                    crts_parta_fragment.c2_2=0 ;
                }
                else if(checkedId==R.id.crts2_2_1){
                    crts_parta_fragment.c2_2 = 1;
                }
                else if(checkedId==R.id.crts2_2_2){
                    crts_parta_fragment.c2_2 = 2;
                }
                else if(checkedId==R.id.crts2_2_3){
                    crts_parta_fragment.c2_2 =3;
                }
                else if(checkedId==R.id.crts2_2_4){
                    crts_parta_fragment.c2_2 =4;
                }

            }
        });

        crts3_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts3_2_0){
                    crts_parta_fragment.c3_2=0 ;
                }
                else if(checkedId==R.id.crts3_2_1){
                    crts_parta_fragment.c3_2 = 1;
                }
                else if(checkedId==R.id.crts3_2_2){
                    crts_parta_fragment.c3_2 = 2;
                }
                else if(checkedId==R.id.crts3_2_3){
                    crts_parta_fragment.c3_2 =3;
                }
                else if(checkedId==R.id.crts3_2_4){
                    crts_parta_fragment.c3_2 =4;
                }

            }
        });

        crts4_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts4_2_0){
                    crts_parta_fragment.c4_2=0 ;
                }
                else if(checkedId==R.id.crts4_2_1){
                    crts_parta_fragment.c4_2 = 1;
                }
                else if(checkedId==R.id.crts4_2_2){
                    crts_parta_fragment.c4_2 = 2;
                }
                else if(checkedId==R.id.crts4_2_3){
                    crts_parta_fragment.c4_2 =3;
                }
                else if(checkedId==R.id.crts4_2_4){
                    crts_parta_fragment.c4_2 =4;
                }

            }
        });
        crts5_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts5_2_0){
                    crts_parta_fragment.c5_2=0 ;
                }
                else if(checkedId==R.id.crts5_2_1){
                    crts_parta_fragment.c5_2 = 1;
                }
                else if(checkedId==R.id.crts5_2_2){
                    crts_parta_fragment.c5_2 = 2;
                }
                else if(checkedId==R.id.crts5_2_3){
                    crts_parta_fragment.c5_2 =3;
                }
                else if(checkedId==R.id.crts5_2_4){
                    crts_parta_fragment.c5_2 =4;
                }

            }
        });
        crts6_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts6_2_0){
                    crts_parta_fragment.c6_2=0 ;
                }
                else if(checkedId==R.id.crts6_2_1){
                    crts_parta_fragment.c6_2 = 1;
                }
                else if(checkedId==R.id.crts6_2_2){
                    crts_parta_fragment.c6_2 = 2;
                }
                else if(checkedId==R.id.crts6_2_3){
                    crts_parta_fragment.c6_2 =3;
                }
                else if(checkedId==R.id.crts6_2_4){
                    crts_parta_fragment.c6_2 =4;
                }

            }
        });

        crts7_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts7_2_0){
                    crts_parta_fragment.c7_2=0 ;
                }
                else if(checkedId==R.id.crts7_2_1){
                    crts_parta_fragment.c7_2 = 1;
                }
                else if(checkedId==R.id.crts7_2_2){
                    crts_parta_fragment.c7_2 = 2;
                }
                else if(checkedId==R.id.crts7_2_3){
                    crts_parta_fragment.c7_2 =3;
                }
                else if(checkedId==R.id.crts7_2_4){
                    crts_parta_fragment.c7_2 =4;
                }

            }
        });

        crts8_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts8_2_0){
                    crts_parta_fragment.c8_2=0 ;
                }
                else if(checkedId==R.id.crts8_2_1){
                    crts_parta_fragment.c8_2 = 1;
                }
                else if(checkedId==R.id.crts8_2_2){
                    crts_parta_fragment.c8_2 = 2;
                }
                else if(checkedId==R.id.crts8_2_3){
                    crts_parta_fragment.c8_2 =3;
                }
                else if(checkedId==R.id.crts8_2_4){
                    crts_parta_fragment.c8_2 =4;
                }

            }
        });
        crts9_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts9_2_0){
                    crts_parta_fragment.c9_2=0 ;
                }
                else if(checkedId==R.id.crts9_2_1){
                    crts_parta_fragment.c9_2 = 1;
                }
                else if(checkedId==R.id.crts9_2_2){
                    crts_parta_fragment.c9_2 = 2;
                }
                else if(checkedId==R.id.crts9_2_3){
                    crts_parta_fragment.c9_2 =3;
                }
                else if(checkedId==R.id.crts9_2_4){
                    crts_parta_fragment.c9_2 =4;
                }

            }
        });
        crts10_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.crts10_1_0){
                    crts_parta_fragment.c10_2=0 ;
                }
                else if(checkedId==R.id.crts10_2_1){
                    crts_parta_fragment.c10_2 = 1;
                }
                else if(checkedId==R.id.crts10_2_2){
                    crts_parta_fragment.c10_2 = 2;
                }
                else if(checkedId==R.id.crts10_2_3){
                    crts_parta_fragment.c10_2 =3;
                }
                else if(checkedId==R.id.crts10_2_4){
                    crts_parta_fragment.c10_2 =4;
                }

            }
        });
        return view;

    }

}
