package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class CRTS_partb_Fragment extends Fragment {
    CRTS_parta_Fragment frag1;
    CRTS_partc_Fragment frag3;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    String crts_num;
    Button pre_button, next_button ;
    Button crts11_edit, crts12_edit, crts13_edit, crts14_edit ;
    int c11;
    int c12;
    int c13;
    int c14;
    int c15;

    RadioGroup crg11, crg12, crg13, crg14, crg15;

    TextView crts11_title, crts12_title, crts13_title, crts14_title, crts15_title;

    RadioButton crts11_0, crts11_1, crts11_2, crts11_3, crts11_4;
    RadioButton crts12_0, crts12_1, crts12_2, crts12_3, crts12_4;
    RadioButton crts13_0, crts13_1, crts13_2, crts13_3, crts13_4;
    RadioButton crts14_0, crts14_1, crts14_2, crts14_3, crts14_4;
    RadioButton crts15_0, crts15_1, crts15_2, crts15_3, crts15_4;

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

    int intent_crts11, intent_crts12, intent_crts13, intent_crts14;

    boolean bool = true;

    String Clinic_ID, PatientName, path;
    String crts_right_spiral_downurl;
    String crts_left_spiral_downurl;
    String writing_downurl;
    String line_downurl;
    double[] spiral_result, line_result, left_spiral_result;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.crts_partb_fragment, container, false);

        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            path = getArguments().getString("path");
            spiral_result = getArguments().getDoubleArray("spiral_result") ;
            line_result = getArguments().getDoubleArray("line_result");
            Log.v("Crts12_edit", "Crts12_edit"+ spiral_result) ;
            left_spiral_result = getArguments().getDoubleArray("left_spiral_result");
            crts11 = getArguments().getInt("crts11", -1);
            crts12 = getArguments().getInt("crts12", -1);
            crts13= getArguments().getInt("crts13", -1) ;
            crts14 = getArguments().getInt("crts14", -1);
            crts_right_spiral_downurl = getArguments().getString("crts_right_spiral_downurl");
            writing_downurl = getArguments().getString("writing_downurl");
            crts_left_spiral_downurl = getArguments().getString("crts_left_spiral_downurl");
            line_downurl = getArguments().getString("line_downurl");

        }

        Log.v("crts 11", "CRTS_Activity_b"+crts11) ;
        TextView tv = (TextView)getActivity().findViewById(R.id.crts_title);
        tv.setText("CRTS Test");

        pre_button = (Button) view.findViewById(R.id.preButton) ;
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity)getActivity()).frgment2();
            }
        });
        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity) getActivity()).frag1 = new CRTS_parta_Fragment() ;
                ((CRTSActivity)getActivity()).setFrag(4);
            }
        });

        crts11_edit = (Button)view.findViewById(R.id.crts11_edit) ;
        crts12_edit = (Button)view.findViewById(R.id.crts12_edit) ;
        crts13_edit = (Button)view.findViewById(R.id.crts13_edit) ;
        crts14_edit = (Button)view.findViewById(R.id.crts14_edit) ;

        crts11_title = (TextView) view.findViewById(R.id.crts11_title);
        crts12_title = (TextView) view.findViewById(R.id.crts12_title);
        crts13_title = (TextView) view.findViewById(R.id.crts13_title);
        crts14_title = (TextView) view.findViewById(R.id.crts14_title);
        crts15_title = (TextView) view.findViewById(R.id.crts15_title);

        crg11 = (RadioGroup) view.findViewById(R.id.crg11);
        crg12 = (RadioGroup) view.findViewById(R.id.crg12);
        crg13 = (RadioGroup) view.findViewById(R.id.crg13);
        crg14 = (RadioGroup) view.findViewById(R.id.crg14);
        crg15 = (RadioGroup) view.findViewById(R.id.crg15);

        crts11_0 = (RadioButton)view.findViewById(R.id.crts11_0);
        crts11_1 = (RadioButton)view.findViewById(R.id.crts11_1);
        crts11_2 = (RadioButton)view.findViewById(R.id.crts11_2);
        crts11_3 = (RadioButton)view.findViewById(R.id.crts11_3);
        crts11_4 = (RadioButton)view.findViewById(R.id.crts11_4);

        crts12_0 = (RadioButton)view.findViewById(R.id.crts12_0);
        crts12_1 = (RadioButton)view.findViewById(R.id.crts12_1);
        crts12_2 = (RadioButton)view.findViewById(R.id.crts12_2);
        crts12_3 = (RadioButton)view.findViewById(R.id.crts12_3);
        crts12_4 = (RadioButton)view.findViewById(R.id.crts12_4);

        crts13_0 = (RadioButton)view.findViewById(R.id.crts13_0);
        crts13_1 = (RadioButton)view.findViewById(R.id.crts13_1);
        crts13_2 = (RadioButton)view.findViewById(R.id.crts13_2);
        crts13_3 = (RadioButton)view.findViewById(R.id.crts13_3);
        crts13_4 = (RadioButton)view.findViewById(R.id.crts13_4);

        crts14_0 = (RadioButton)view.findViewById(R.id.crts14_0);
        crts14_1 = (RadioButton)view.findViewById(R.id.crts14_1);
        crts14_2 = (RadioButton)view.findViewById(R.id.crts14_2);
        crts14_3 = (RadioButton)view.findViewById(R.id.crts14_3);
        crts14_4 = (RadioButton)view.findViewById(R.id.crts14_4);

        crts15_0 = (RadioButton)view.findViewById(R.id.crts15_0);
        crts15_1 = (RadioButton)view.findViewById(R.id.crts15_1);
        crts15_2 = (RadioButton)view.findViewById(R.id.crts15_2);
        crts15_3 = (RadioButton)view.findViewById(R.id.crts15_3);
        crts15_4 = (RadioButton)view.findViewById(R.id.crts15_4);
        Log.v("CRTS_parb_Fragment", "crts11 =" +crts11) ;

        ((RadioButton)crg11.getChildAt(crts11)).setChecked(true);
        ((RadioButton)crg12.getChildAt(crts12)).setChecked(true);
        ((RadioButton)crg13.getChildAt(crts13)).setChecked(true);
        ((RadioButton)crg14.getChildAt(crts14)).setChecked(true);

        if(crts11_0.isChecked()){
            intent_crts11 = 0;
        }
        else if(crts11_1.isChecked()){
            intent_crts11 = 1;
        }
        else if(crts11_2.isChecked()){
            intent_crts11 = 2;
        }
        else if(crts11_3.isChecked()){
            intent_crts11 = 3;
        }
        else if(crts11_4.isChecked()){
            intent_crts11 = 4;
        }
        else{

        }
        if(crts12_0.isChecked()){
            intent_crts12 = 0;
        }
        else if(crts12_1.isChecked()){
            intent_crts12 = 1;
        }
        else if(crts12_2.isChecked()){
            intent_crts12 = 2;
        }
        else if(crts12_3.isChecked()){
            intent_crts12 = 3;
        }
        else if(crts12_4.isChecked()){
            intent_crts12 = 4;
        }
        else{

        }
        if(crts13_0.isChecked()){
            intent_crts13 = 0;
        }
        else if(crts13_1.isChecked()){
            intent_crts13 = 1;
        }
        else if(crts13_2.isChecked()){
            intent_crts13 = 2;
        }
        else if(crts13_3.isChecked()){
            intent_crts13 = 3;
        }
        else if(crts13_4.isChecked()){
            intent_crts13 = 4;
        }
        else{

        }
        if(crts14_0.isChecked()){
            intent_crts14 = 0;
        }
        else if(crts14_1.isChecked()){
            intent_crts14 = 1;
        }
        else if(crts14_2.isChecked()){
            intent_crts14 = 2;
        }
        else if(crts14_3.isChecked()){
            intent_crts14 = 3;
        }
        else if(crts14_4.isChecked()){
            intent_crts14 = 4;
        }
        else{

        }
        crts11_edit.setOnClickListener(new View.OnClickListener() {  // 수정하기 클릭
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), WritingResult.class) ;
                intent1.putExtra("line_result", line_result) ;
                intent1.putExtra("left_spiral_result", left_spiral_result) ;
                intent1.putExtra("path1","CRTS") ;
                intent1.putExtra("edit", "yes") ;
                intent1.putExtra("Clinic_ID", Clinic_ID) ;
                intent1.putExtra("PatientName", PatientName) ;
                intent1.putExtra("spiral_result", spiral_result) ;
                intent1.putExtra("crts_num", crts_num) ;
                intent1.putExtra("crts11", intent_crts11) ;
                intent1.putExtra("crts12", intent_crts12) ;
                intent1.putExtra("crts13", intent_crts13) ;
                intent1.putExtra("crts14", intent_crts14) ;
                intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
                intent1.putExtra("writing_downurl", writing_downurl);
                intent1.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
                intent1.putExtra("line_downurl", line_downurl);
                Log.v("CRTS_parb_Fragment", "CRTS_Activity_intent " + intent_crts11) ;
                startActivity(intent1) ;
            }
        });
        crts12_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), CRTS_SpiralResult.class) ;
                Log.v("Crts12_edit", "Crts12_edit"+ spiral_result) ;
                intent1.putExtra("line_result", line_result) ;
                intent1.putExtra("left_spiral_result", left_spiral_result) ;
                intent1.putExtra("path1","CRTS") ;
                intent1.putExtra("edit", "yes") ;
                intent1.putExtra("Clinic_ID", Clinic_ID) ;
                intent1.putExtra("PatientName", PatientName) ;
                intent1.putExtra("spiral_result", spiral_result) ;
                intent1.putExtra("crts_num", crts_num) ;
                intent1.putExtra("crts11", intent_crts11) ;
                intent1.putExtra("crts12", intent_crts12) ;
                intent1.putExtra("crts13", intent_crts13) ;
                intent1.putExtra("crts14", intent_crts14) ;
                intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
                intent1.putExtra("writing_downurl", writing_downurl);
                intent1.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
                intent1.putExtra("line_downurl", line_downurl);
                startActivity(intent1) ;
            }
        });
        crts13_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(),CRTS_LeftSpiralResult.class) ;
                intent1.putExtra("line_result", line_result) ;
                intent1.putExtra("left_spiral_result", left_spiral_result) ;
                intent1.putExtra("path1","CRTS") ;
                intent1.putExtra("edit", "yes") ;
                intent1.putExtra("Clinic_ID", Clinic_ID) ;
                intent1.putExtra("PatientName", PatientName) ;
                intent1.putExtra("spiral_result", spiral_result) ;
                intent1.putExtra("crts_num", crts_num) ;
                intent1.putExtra("crts11", intent_crts11) ;
                intent1.putExtra("crts12", intent_crts12) ;
                intent1.putExtra("crts13", intent_crts13) ;
                intent1.putExtra("crts14", intent_crts14) ;
                intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
                intent1.putExtra("writing_downurl", writing_downurl);
                intent1.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
                intent1.putExtra("line_downurl", line_downurl);
                startActivity(intent1) ;
            }
        });
        crts14_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), CRTS_LineResult.class) ;
                intent1.putExtra("line_result", line_result) ;
                intent1.putExtra("left_spiral_result", left_spiral_result) ;
                intent1.putExtra("path1","CRTS") ;
                intent1.putExtra("edit", "yes") ;
                intent1.putExtra("Clinic_ID", Clinic_ID) ;
                intent1.putExtra("PatientName", PatientName) ;
                intent1.putExtra("spiral_result", spiral_result) ;
                intent1.putExtra("crts_num", crts_num) ;
                intent1.putExtra("crts11", intent_crts11) ;
                intent1.putExtra("crts12", intent_crts12) ;
                intent1.putExtra("crts13", intent_crts13) ;
                intent1.putExtra("crts14", intent_crts14) ;
                intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
                intent1.putExtra("writing_downurl", writing_downurl);
                intent1.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
                intent1.putExtra("line_downurl", line_downurl);
                startActivity(intent1) ;
            }
        });
        return view;

    }

}