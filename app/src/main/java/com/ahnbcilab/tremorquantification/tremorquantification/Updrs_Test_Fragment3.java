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

public class Updrs_Test_Fragment3 extends Fragment {
    String Clinic_ID;
    String PatientName;
    String uid;
    View view;
    File file;
    Button pre_button, next_button ;
    RadioGroup rg23, rg24, rg25, rg26, rg27;

    TextView updrs_23_title, updrs_24_title, updrs_25_title, updrs_26_title, updrs_27_title;

    RadioButton CBpart3_23_0;
    RadioButton CBpart3_23_1;
    RadioButton CBpart3_23_2;
    RadioButton CBpart3_23_3;
    RadioButton CBpart3_23_4;

    RadioButton CBpart3_24_0;
    RadioButton CBpart3_24_1;
    RadioButton CBpart3_24_2;
    RadioButton CBpart3_24_3;
    RadioButton CBpart3_24_4;

    RadioButton CBpart3_25_0;
    RadioButton CBpart3_25_1;
    RadioButton CBpart3_25_2;
    RadioButton CBpart3_25_3;
    RadioButton CBpart3_25_4;

    RadioButton CBpart3_26_0;
    RadioButton CBpart3_26_1;
    RadioButton CBpart3_26_2;
    RadioButton CBpart3_26_3;
    RadioButton CBpart3_26_4;

    RadioButton CBpart3_27_0;
    RadioButton CBpart3_27_1;
    RadioButton CBpart3_27_2;
    RadioButton CBpart3_27_3;
    RadioButton CBpart3_27_4;

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
        view = inflater.inflate(R.layout.updrs_test_fragment3, container, false);
        pre_button = (Button) view.findViewById(R.id.preButton) ;
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UPDRSActivity)getActivity()).fragment3();
            }
        });
        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UPDRSActivity)getActivity()).setFrag(1);
            }
        });
        rg23 = (RadioGroup) view.findViewById(R.id.rg23);
        rg24 = (RadioGroup) view.findViewById(R.id.rg24);
        rg25 = (RadioGroup) view.findViewById(R.id.rg25);
        rg26 = (RadioGroup) view.findViewById(R.id.rg26);
        rg27 = (RadioGroup) view.findViewById(R.id.rg27);

        updrs_23_title = (TextView) view.findViewById(R.id.updrs_23_title);
        updrs_24_title = (TextView) view.findViewById(R.id.updrs_24_title);
        updrs_25_title = (TextView) view.findViewById(R.id.updrs_25_title);
        updrs_26_title = (TextView) view.findViewById(R.id.updrs_26_title);
        updrs_27_title = (TextView) view.findViewById(R.id.updrs_27_title);

        CBpart3_23_0 = (RadioButton) view.findViewById(R.id.CBpart3_23_0);
        CBpart3_23_1 = (RadioButton) view.findViewById(R.id.CBpart3_23_1) ;
        CBpart3_23_2 = (RadioButton) view.findViewById(R.id.CBpart3_23_2) ;
        CBpart3_23_3 = (RadioButton) view.findViewById(R.id.CBpart3_23_3) ;
        CBpart3_23_4 = (RadioButton) view.findViewById(R.id.CBpart3_23_4) ;

        CBpart3_24_0 = (RadioButton) view.findViewById(R.id.CBpart3_24_0);
        CBpart3_24_1 = (RadioButton) view.findViewById(R.id.CBpart3_24_1) ;
        CBpart3_24_2 = (RadioButton) view.findViewById(R.id.CBpart3_24_2) ;
        CBpart3_24_3 = (RadioButton) view.findViewById(R.id.CBpart3_24_3) ;
        CBpart3_24_4 = (RadioButton) view.findViewById(R.id.CBpart3_24_4) ;

        CBpart3_25_0 = (RadioButton) view.findViewById(R.id.CBpart3_25_0);
        CBpart3_25_1 = (RadioButton) view.findViewById(R.id.CBpart3_25_1) ;
        CBpart3_25_2 = (RadioButton) view.findViewById(R.id.CBpart3_25_2) ;
        CBpart3_25_3 = (RadioButton) view.findViewById(R.id.CBpart3_25_3) ;
        CBpart3_25_4 = (RadioButton) view.findViewById(R.id.CBpart3_25_4) ;

        CBpart3_26_0 = (RadioButton) view.findViewById(R.id.CBpart3_26_0);
        CBpart3_26_1 = (RadioButton) view.findViewById(R.id.CBpart3_26_1) ;
        CBpart3_26_2 = (RadioButton) view.findViewById(R.id.CBpart3_26_2) ;
        CBpart3_26_3 = (RadioButton) view.findViewById(R.id.CBpart3_26_3) ;
        CBpart3_26_4 = (RadioButton) view.findViewById(R.id.CBpart3_26_4) ;

        CBpart3_27_0 = (RadioButton) view.findViewById(R.id.CBpart3_27_0);
        CBpart3_27_1 = (RadioButton) view.findViewById(R.id.CBpart3_27_1) ;
        CBpart3_27_2 = (RadioButton) view.findViewById(R.id.CBpart3_27_2) ;
        CBpart3_27_3 = (RadioButton) view.findViewById(R.id.CBpart3_27_3) ;
        CBpart3_27_4 = (RadioButton) view.findViewById(R.id.CBpart3_27_4) ;

        return view ;

    }
}