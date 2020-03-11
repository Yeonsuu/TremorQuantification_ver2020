package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;

public class Updrs_Test_Fragment2 extends Fragment {
    String Clinic_ID;
    String PatientName;
    String uid;
    View view;
    File file;
    Button pre_button, next_button ;
    RadioGroup rg10, rg11, rg12, rg13, rg14, rg15, rg16, rg17, rg18, rg19, rg20;
    RadioGroup rg21, rg22;

    TextView updrs_10_title, updrs_11_title, updrs_12_title, updrs_13_title, updrs_14_title;
    TextView updrs_15_title, updrs_16_title, updrs_17_title, updrs_18_title, updrs_19_title;
    TextView updrs_20_title, updrs_21_title, updrs_22_title;


    RadioButton CBpart3_10_0 ;
    RadioButton CBpart3_10_1 ;
    RadioButton CBpart3_10_2 ;
    RadioButton CBpart3_10_3 ;
    RadioButton CBpart3_10_4 ;

    RadioButton CBpart3_11_0 ;
    RadioButton CBpart3_11_1 ;
    RadioButton CBpart3_11_2 ;
    RadioButton CBpart3_11_3 ;
    RadioButton CBpart3_11_4 ;

    RadioButton CBpart3_12_0 ;
    RadioButton CBpart3_12_1 ;
    RadioButton CBpart3_12_2 ;
    RadioButton CBpart3_12_3 ;
    RadioButton CBpart3_12_4 ;

    RadioButton CBpart3_13_0 ;
    RadioButton CBpart3_13_1 ;
    RadioButton CBpart3_13_2 ;
    RadioButton CBpart3_13_3 ;
    RadioButton CBpart3_13_4 ;

    RadioButton CBpart3_14_0 ;
    RadioButton CBpart3_14_1 ;
    RadioButton CBpart3_14_2 ;
    RadioButton CBpart3_14_3 ;
    RadioButton CBpart3_14_4 ;

    RadioButton CBpart3_15_0 ;
    RadioButton CBpart3_15_1 ;
    RadioButton CBpart3_15_2 ;
    RadioButton CBpart3_15_3 ;
    RadioButton CBpart3_15_4 ;

    RadioButton CBpart3_16_0 ;
    RadioButton CBpart3_16_1 ;
    RadioButton CBpart3_16_2 ;
    RadioButton CBpart3_16_3 ;
    RadioButton CBpart3_16_4 ;

    RadioButton CBpart3_17_0 ;
    RadioButton CBpart3_17_1 ;
    RadioButton CBpart3_17_2 ;
    RadioButton CBpart3_17_3 ;
    RadioButton CBpart3_17_4 ;

    RadioButton CBpart3_18_0 ;
    RadioButton CBpart3_18_1 ;
    RadioButton CBpart3_18_2 ;
    RadioButton CBpart3_18_3 ;
    RadioButton CBpart3_18_4 ;

    RadioButton CBpart3_19_0 ;
    RadioButton CBpart3_19_1 ;
    RadioButton CBpart3_19_2 ;
    RadioButton CBpart3_19_3 ;
    RadioButton CBpart3_19_4 ;

    RadioButton CBpart3_20_0 ;
    RadioButton CBpart3_20_1 ;
    RadioButton CBpart3_20_2 ;
    RadioButton CBpart3_20_3 ;
    RadioButton CBpart3_20_4 ;

    RadioButton CBpart3_21_0 ;
    RadioButton CBpart3_21_1 ;
    RadioButton CBpart3_21_2 ;
    RadioButton CBpart3_21_3 ;
    RadioButton CBpart3_21_4 ;

    RadioButton CBpart3_22_0 ;
    RadioButton CBpart3_22_1 ;
    RadioButton CBpart3_22_2 ;
    RadioButton CBpart3_22_3 ;
    RadioButton CBpart3_22_4 ;

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
        view = inflater.inflate(R.layout.updrs_test_fragment2, container, false);
        pre_button = (Button) view.findViewById(R.id.preButton) ;
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UPDRSActivity)getActivity()).frgment2();
            }
        });
        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UPDRSActivity)getActivity()).setFrag(0);
            }
        });
        rg10 = (RadioGroup) view.findViewById(R.id.rg10);
        rg11 = (RadioGroup) view.findViewById(R.id.rg11);
        rg12 = (RadioGroup) view.findViewById(R.id.rg12);
        rg13 = (RadioGroup) view.findViewById(R.id.rg13);
        rg14 = (RadioGroup) view.findViewById(R.id.rg14);
        rg15 = (RadioGroup) view.findViewById(R.id.rg15);
        rg16 = (RadioGroup) view.findViewById(R.id.rg16);
        rg17 = (RadioGroup) view.findViewById(R.id.rg17);
        rg18 = (RadioGroup) view.findViewById(R.id.rg18);
        rg19 = (RadioGroup) view.findViewById(R.id.rg19);
        rg20 = (RadioGroup) view.findViewById(R.id.rg20);
        rg21 = (RadioGroup) view.findViewById(R.id.rg21);
        rg22 = (RadioGroup) view.findViewById(R.id.rg22);

        updrs_10_title = (TextView) view.findViewById(R.id.updrs_10_title);
        updrs_11_title = (TextView) view.findViewById(R.id.updrs_11_title);
        updrs_12_title = (TextView) view.findViewById(R.id.updrs_12_title);
        updrs_13_title = (TextView) view.findViewById(R.id.updrs_13_title);
        updrs_14_title = (TextView) view.findViewById(R.id.updrs_14_title);
        updrs_15_title = (TextView) view.findViewById(R.id.updrs_15_title);
        updrs_16_title = (TextView) view.findViewById(R.id.updrs_16_title);
        updrs_17_title = (TextView) view.findViewById(R.id.updrs_17_title);
        updrs_18_title = (TextView) view.findViewById(R.id.updrs_18_title);
        updrs_19_title = (TextView) view.findViewById(R.id.updrs_19_title);
        updrs_20_title = (TextView) view.findViewById(R.id.updrs_20_title);
        updrs_21_title = (TextView) view.findViewById(R.id.updrs_21_title);
        updrs_22_title = (TextView) view.findViewById(R.id.updrs_22_title);

        CBpart3_10_0 = (RadioButton) view.findViewById(R.id.CBpart3_10_0);
        CBpart3_10_1 = (RadioButton) view.findViewById(R.id.CBpart3_10_1);
        CBpart3_10_2 = (RadioButton) view.findViewById(R.id.CBpart3_10_2);
        CBpart3_10_3 = (RadioButton) view.findViewById(R.id.CBpart3_10_3);
        CBpart3_10_4 = (RadioButton) view.findViewById(R.id.CBpart3_10_4);

        CBpart3_11_0 = (RadioButton) view.findViewById(R.id.CBpart3_11_0);
        CBpart3_11_1 = (RadioButton) view.findViewById(R.id.CBpart3_11_1) ;
        CBpart3_11_2 = (RadioButton) view.findViewById(R.id.CBpart3_11_2) ;
        CBpart3_11_3 = (RadioButton) view.findViewById(R.id.CBpart3_11_3) ;
        CBpart3_11_4 = (RadioButton) view.findViewById(R.id.CBpart3_11_4) ;

        CBpart3_12_0 = (RadioButton) view.findViewById(R.id.CBpart3_12_0);
        CBpart3_12_1 = (RadioButton) view.findViewById(R.id.CBpart3_12_1) ;
        CBpart3_12_2 = (RadioButton) view.findViewById(R.id.CBpart3_12_2) ;
        CBpart3_12_3 = (RadioButton) view.findViewById(R.id.CBpart3_12_3) ;
        CBpart3_12_4 = (RadioButton) view.findViewById(R.id.CBpart3_12_4) ;

        CBpart3_13_0 = (RadioButton) view.findViewById(R.id.CBpart3_13_0);
        CBpart3_13_1 = (RadioButton) view.findViewById(R.id.CBpart3_13_1) ;
        CBpart3_13_2 = (RadioButton) view.findViewById(R.id.CBpart3_13_2) ;
        CBpart3_13_3 = (RadioButton) view.findViewById(R.id.CBpart3_13_3) ;
        CBpart3_13_4 = (RadioButton) view.findViewById(R.id.CBpart3_13_4) ;

        CBpart3_14_0 = (RadioButton) view.findViewById(R.id.CBpart3_14_0);
        CBpart3_14_1 = (RadioButton) view.findViewById(R.id.CBpart3_14_1) ;
        CBpart3_14_2 = (RadioButton) view.findViewById(R.id.CBpart3_14_2) ;
        CBpart3_14_3 = (RadioButton) view.findViewById(R.id.CBpart3_14_3) ;
        CBpart3_14_4 = (RadioButton) view.findViewById(R.id.CBpart3_14_4) ;

        CBpart3_15_0 = (RadioButton) view.findViewById(R.id.CBpart3_15_0);
        CBpart3_15_1 = (RadioButton) view.findViewById(R.id.CBpart3_15_1) ;
        CBpart3_15_2 = (RadioButton) view.findViewById(R.id.CBpart3_15_2) ;
        CBpart3_15_3 = (RadioButton) view.findViewById(R.id.CBpart3_15_3) ;
        CBpart3_15_4 = (RadioButton) view.findViewById(R.id.CBpart3_15_4) ;

        CBpart3_16_0 = (RadioButton) view.findViewById(R.id.CBpart3_16_0);
        CBpart3_16_1 = (RadioButton) view.findViewById(R.id.CBpart3_16_1) ;
        CBpart3_16_2 = (RadioButton) view.findViewById(R.id.CBpart3_16_2) ;
        CBpart3_16_3 = (RadioButton) view.findViewById(R.id.CBpart3_16_3) ;
        CBpart3_16_4 = (RadioButton) view.findViewById(R.id.CBpart3_16_4) ;

        CBpart3_17_0 = (RadioButton) view.findViewById(R.id.CBpart3_17_0);
        CBpart3_17_1 = (RadioButton) view.findViewById(R.id.CBpart3_17_1) ;
        CBpart3_17_2 = (RadioButton) view.findViewById(R.id.CBpart3_17_2) ;
        CBpart3_17_3 = (RadioButton) view.findViewById(R.id.CBpart3_17_3) ;
        CBpart3_17_4 = (RadioButton) view.findViewById(R.id.CBpart3_17_4) ;

        CBpart3_18_0 = (RadioButton) view.findViewById(R.id.CBpart3_18_0);
        CBpart3_18_1 = (RadioButton) view.findViewById(R.id.CBpart3_18_1) ;
        CBpart3_18_2 = (RadioButton) view.findViewById(R.id.CBpart3_18_2) ;
        CBpart3_18_3 = (RadioButton) view.findViewById(R.id.CBpart3_18_3) ;
        CBpart3_18_4 = (RadioButton) view.findViewById(R.id.CBpart3_18_4) ;

        CBpart3_19_0 = (RadioButton) view.findViewById(R.id.CBpart3_19_0);
        CBpart3_19_1 = (RadioButton) view.findViewById(R.id.CBpart3_19_1) ;
        CBpart3_19_2 = (RadioButton) view.findViewById(R.id.CBpart3_19_2) ;
        CBpart3_19_3 = (RadioButton) view.findViewById(R.id.CBpart3_19_3) ;
        CBpart3_19_4 = (RadioButton) view.findViewById(R.id.CBpart3_19_4) ;

        CBpart3_20_0 = (RadioButton) view.findViewById(R.id.CBpart3_20_0);
        CBpart3_20_1 = (RadioButton) view.findViewById(R.id.CBpart3_20_1) ;
        CBpart3_20_2 = (RadioButton) view.findViewById(R.id.CBpart3_20_2) ;
        CBpart3_20_3 = (RadioButton) view.findViewById(R.id.CBpart3_20_3) ;
        CBpart3_20_4 = (RadioButton) view.findViewById(R.id.CBpart3_20_4) ;

        CBpart3_21_0 = (RadioButton) view.findViewById(R.id.CBpart3_21_0);
        CBpart3_21_1 = (RadioButton) view.findViewById(R.id.CBpart3_21_1) ;
        CBpart3_21_2 = (RadioButton) view.findViewById(R.id.CBpart3_21_2) ;
        CBpart3_21_3 = (RadioButton) view.findViewById(R.id.CBpart3_21_3) ;
        CBpart3_21_4 = (RadioButton) view.findViewById(R.id.CBpart3_21_4) ;

        CBpart3_22_0 = (RadioButton) view.findViewById(R.id.CBpart3_22_0);
        CBpart3_22_1 = (RadioButton) view.findViewById(R.id.CBpart3_22_1) ;
        CBpart3_22_2 = (RadioButton) view.findViewById(R.id.CBpart3_22_2) ;
        CBpart3_22_3 = (RadioButton) view.findViewById(R.id.CBpart3_22_3) ;
        CBpart3_22_4 = (RadioButton) view.findViewById(R.id.CBpart3_22_4) ;

        return view ;

    }
}