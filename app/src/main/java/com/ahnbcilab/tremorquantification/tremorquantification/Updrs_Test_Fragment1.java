package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Updrs_Test_Fragment1 extends Fragment {
    String Clinic_ID;
    String PatientName;
    String uid;
    File file;
    Button pre_button, next_button ;
    TextView page_text ;

    RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9;

    TextView updrs_1_title, updrs_2_title, updrs_3_title, updrs_4_title, updrs_5_title, updrs_6_title, updrs_7_title, updrs_8_title, updrs_9_title;

    TextView TBpart3_1_0;
    TextView TBpart3_1_1;
    TextView TBpart3_1_2;
    TextView TBpart3_1_3;
    TextView TBpart3_1_4;

    TextView TBpart3_2_0;
    TextView TBpart3_2_1;
    TextView TBpart3_2_2;
    TextView TBpart3_2_3;
    TextView TBpart3_2_4;

    RadioButton CBpart3_1_0 ;
    RadioButton CBpart3_1_1 ;
    RadioButton CBpart3_1_2 ;
    RadioButton CBpart3_1_3 ;
    RadioButton CBpart3_1_4 ;

    RadioButton CBpart3_2_0 ;
    RadioButton CBpart3_2_1 ;
    RadioButton CBpart3_2_2 ;
    RadioButton CBpart3_2_3 ;
    RadioButton CBpart3_2_4 ;

    RadioButton CBpart3_3_0 ;
    RadioButton CBpart3_3_1 ;
    RadioButton CBpart3_3_2 ;
    RadioButton CBpart3_3_3 ;
    RadioButton CBpart3_3_4 ;

    RadioButton CBpart3_4_0 ;
    RadioButton CBpart3_4_1 ;
    RadioButton CBpart3_4_2 ;
    RadioButton CBpart3_4_3 ;
    RadioButton CBpart3_4_4 ;

    RadioButton CBpart3_5_0 ;
    RadioButton CBpart3_5_1 ;
    RadioButton CBpart3_5_2 ;
    RadioButton CBpart3_5_3 ;
    RadioButton CBpart3_5_4 ;

    RadioButton CBpart3_6_0 ;
    RadioButton CBpart3_6_1 ;
    RadioButton CBpart3_6_2 ;
    RadioButton CBpart3_6_3 ;
    RadioButton CBpart3_6_4 ;

    RadioButton CBpart3_7_0 ;
    RadioButton CBpart3_7_1 ;
    RadioButton CBpart3_7_2 ;
    RadioButton CBpart3_7_3 ;
    RadioButton CBpart3_7_4 ;

    RadioButton CBpart3_8_0 ;
    RadioButton CBpart3_8_1 ;
    RadioButton CBpart3_8_2 ;
    RadioButton CBpart3_8_3 ;
    RadioButton CBpart3_8_4 ;

    RadioButton CBpart3_9_0 ;
    RadioButton CBpart3_9_1 ;
    RadioButton CBpart3_9_2 ;
    RadioButton CBpart3_9_3 ;
    RadioButton CBpart3_9_4 ;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        // 이전 Activity value 받아오기
        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            uid = getArguments().getString("doc_uid");
        }

        // 초기 화면
        View view = inflater.inflate(R.layout.updrs_test_fragment1, container, false);
        pre_button = (Button) view.findViewById(R.id.preButton) ;
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        page_text = (TextView) view.findViewById(R.id.page_text) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UPDRSActivity)getActivity()).frgment1();
            }
        });
        pre_button.setVisibility(View.GONE);
        rg1 = (RadioGroup) view.findViewById(R.id.rg1);
        rg2 = (RadioGroup) view.findViewById(R.id.rg2);
        rg3 = (RadioGroup) view.findViewById(R.id.rg3);
        rg4 = (RadioGroup) view.findViewById(R.id.rg4);
        rg5 = (RadioGroup) view.findViewById(R.id.rg5);
        rg6 = (RadioGroup) view.findViewById(R.id.rg6);
        rg7 = (RadioGroup) view.findViewById(R.id.rg7);
        rg8 = (RadioGroup) view.findViewById(R.id.rg8);
        rg9 = (RadioGroup) view.findViewById(R.id.rg9);

        updrs_1_title = (TextView) view.findViewById(R.id.updrs_1_title);
        updrs_2_title = (TextView) view.findViewById(R.id.updrs_2_title);
        updrs_3_title = (TextView) view.findViewById(R.id.updrs_3_title);
        updrs_4_title = (TextView) view.findViewById(R.id.updrs_4_title);
        updrs_5_title = (TextView) view.findViewById(R.id.updrs_5_title);
        updrs_6_title = (TextView) view.findViewById(R.id.updrs_6_title);
        updrs_7_title = (TextView) view.findViewById(R.id.updrs_7_title);
        updrs_8_title = (TextView) view.findViewById(R.id.updrs_8_title);
        updrs_9_title = (TextView) view.findViewById(R.id.updrs_9_title);


        CBpart3_1_0 = (RadioButton) view.findViewById(R.id.CBpart3_1_0);
        CBpart3_1_1 = (RadioButton) view.findViewById(R.id.CBpart3_1_1) ;
        CBpart3_1_2 = (RadioButton) view.findViewById(R.id.CBpart3_1_2) ;
        CBpart3_1_3 = (RadioButton) view.findViewById(R.id.CBpart3_1_3) ;
        CBpart3_1_4 = (RadioButton) view.findViewById(R.id.CBpart3_1_4) ;

        CBpart3_2_0 = (RadioButton) view.findViewById(R.id.CBpart3_2_0);
        CBpart3_2_1 = (RadioButton) view.findViewById(R.id.CBpart3_2_1) ;
        CBpart3_2_2 = (RadioButton) view.findViewById(R.id.CBpart3_2_2) ;
        CBpart3_2_3 = (RadioButton) view.findViewById(R.id.CBpart3_2_3) ;
        CBpart3_2_4 = (RadioButton) view.findViewById(R.id.CBpart3_2_4) ;

        CBpart3_3_0 = (RadioButton) view.findViewById(R.id.CBpart3_3_0);
        CBpart3_3_1 = (RadioButton) view.findViewById(R.id.CBpart3_3_1) ;
        CBpart3_3_2 = (RadioButton) view.findViewById(R.id.CBpart3_3_2) ;
        CBpart3_3_3 = (RadioButton) view.findViewById(R.id.CBpart3_3_3) ;
        CBpart3_3_4 = (RadioButton) view.findViewById(R.id.CBpart3_3_4) ;

        CBpart3_4_0 = (RadioButton) view.findViewById(R.id.CBpart3_4_0);
        CBpart3_4_1 = (RadioButton) view.findViewById(R.id.CBpart3_4_1) ;
        CBpart3_4_2 = (RadioButton) view.findViewById(R.id.CBpart3_4_2) ;
        CBpart3_4_3 = (RadioButton) view.findViewById(R.id.CBpart3_4_3) ;
        CBpart3_4_4 = (RadioButton) view.findViewById(R.id.CBpart3_4_4) ;

        CBpart3_5_0 = (RadioButton) view.findViewById(R.id.CBpart3_5_0);
        CBpart3_5_1 = (RadioButton) view.findViewById(R.id.CBpart3_5_1) ;
        CBpart3_5_2 = (RadioButton) view.findViewById(R.id.CBpart3_5_2) ;
        CBpart3_5_3 = (RadioButton) view.findViewById(R.id.CBpart3_5_3) ;
        CBpart3_5_4 = (RadioButton) view.findViewById(R.id.CBpart3_5_4) ;

        CBpart3_6_0 = (RadioButton) view.findViewById(R.id.CBpart3_6_0);
        CBpart3_6_1 = (RadioButton) view.findViewById(R.id.CBpart3_6_1) ;
        CBpart3_6_2 = (RadioButton) view.findViewById(R.id.CBpart3_6_2) ;
        CBpart3_6_3 = (RadioButton) view.findViewById(R.id.CBpart3_6_3) ;
        CBpart3_6_4 = (RadioButton) view.findViewById(R.id.CBpart3_6_4) ;

        CBpart3_7_0 = (RadioButton) view.findViewById(R.id.CBpart3_7_0);
        CBpart3_7_1 = (RadioButton) view.findViewById(R.id.CBpart3_7_1) ;
        CBpart3_7_2 = (RadioButton) view.findViewById(R.id.CBpart3_7_2) ;
        CBpart3_7_3 = (RadioButton) view.findViewById(R.id.CBpart3_7_3) ;
        CBpart3_7_4 = (RadioButton) view.findViewById(R.id.CBpart3_7_4) ;

        CBpart3_8_0 = (RadioButton) view.findViewById(R.id.CBpart3_8_0);
        CBpart3_8_1 = (RadioButton) view.findViewById(R.id.CBpart3_8_1) ;
        CBpart3_8_2 = (RadioButton) view.findViewById(R.id.CBpart3_8_2) ;
        CBpart3_8_3 = (RadioButton) view.findViewById(R.id.CBpart3_8_3) ;
        CBpart3_8_4 = (RadioButton) view.findViewById(R.id.CBpart3_8_4) ;

        CBpart3_9_0 = (RadioButton) view.findViewById(R.id.CBpart3_9_0);
        CBpart3_9_1 = (RadioButton) view.findViewById(R.id.CBpart3_9_1) ;
        CBpart3_9_2 = (RadioButton) view.findViewById(R.id.CBpart3_9_2) ;
        CBpart3_9_3 = (RadioButton) view.findViewById(R.id.CBpart3_9_3) ;
        CBpart3_9_4 = (RadioButton) view.findViewById(R.id.CBpart3_9_4) ;

        TBpart3_1_0 = (TextView) view.findViewById(R.id.TBpart3_1_0);
        TBpart3_1_1 = (TextView) view.findViewById(R.id.TBpart3_1_1) ;
        TBpart3_1_2 = (TextView) view.findViewById(R.id.TBpart3_1_2) ;
        TBpart3_1_3 = (TextView) view.findViewById(R.id.TBpart3_1_3) ;
        TBpart3_1_4 = (TextView) view.findViewById(R.id.TBpart3_1_4) ;

        TBpart3_2_0 = (TextView) view.findViewById(R.id.TBpart3_2_0);
        TBpart3_2_1 = (TextView) view.findViewById(R.id.TBpart3_2_1) ;
        TBpart3_2_2 = (TextView) view.findViewById(R.id.TBpart3_2_2) ;
        TBpart3_2_3 = (TextView) view.findViewById(R.id.TBpart3_2_3) ;
        TBpart3_2_4 = (TextView) view.findViewById(R.id.TBpart3_2_4) ;


        TBpart3_1_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_1_0.toggle();
            }
        });
        TBpart3_1_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_1_1.toggle();
            }
        });
        TBpart3_1_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_1_2.toggle();
            }
        });
        TBpart3_1_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_1_3.toggle();
            }
        });
        TBpart3_1_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_1_4.toggle();
            }
        });

        TBpart3_2_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_2_0.toggle();
            }
        });
        TBpart3_2_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_2_1.toggle();
            }
        });
        TBpart3_2_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_2_2.toggle();
            }
        });
        TBpart3_2_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_2_3.toggle();
            }
        });
        TBpart3_2_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                CBpart3_2_4.toggle();
            }
        });
        return view ;

    }
}