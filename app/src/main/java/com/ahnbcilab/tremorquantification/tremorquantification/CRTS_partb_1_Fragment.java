package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CRTS_partb_1_Fragment extends Fragment {
    CRTS_parta_Fragment frag1;
    CRTS_partc_Fragment frag3;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    String crts_num;
    Button pre_button, next_button;

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

    boolean bool = true;

    String Clinic_ID, PatientName, path;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.crtsb_layout, container, false);

        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            path = getArguments().getString("path");
            crts11 = getArguments().getInt("crts11", -1);
            crts12 = getArguments().getInt("crts12", -1);
            crts13 = getArguments().getInt("crts13", -1);
            crts14 = getArguments().getInt("crts14", -1);
        }

        Log.v("llloooggg", "crts11 = " + crts11 + "crts12 = " + crts12 + "crts14 = " + crts14);

        TextView tv = (TextView) getActivity().findViewById(R.id.crts_title);
        tv.setText("CRTS Test");

        Button gotowrite = (Button) view.findViewById(R.id.gotowrite);

        pre_button = (Button) view.findViewById(R.id.preButton);
        next_button = (Button) view.findViewById(R.id.nextButton);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity) getActivity()).frgment2();
            }
        });
        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ((CRTSActivity) getActivity()).frag1 = new CRTS_parta_Fragment() ;
                ((CRTSActivity) getActivity()).setFrag(4);
            }
        });


        gotowrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WritingActivity.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("crts_num", crts_num);
                Context context= view.getContext();
                LayoutInflater inflater= getActivity().getLayoutInflater();
                View customToast =inflater.inflate(R.layout.toast_custom, null);
                Toast customtoast=new Toast(context);
                customtoast.setView(customToast);
                customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
                customtoast.setDuration(Toast.LENGTH_LONG);
                customtoast.show();
                startActivity(intent);
            }
        });


        return view;

    }
}

