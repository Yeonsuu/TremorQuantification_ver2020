package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.SliderPagerAdapter;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.data.DoctorData;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


public class CRTS_parta_Fragment extends Fragment {

    public ViewPager pager ;
    public PagerAdapter pagerAdapter ;
    int c1_1 , c1_2 , c1_3;
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
    int pre_num ;
    boolean bool = true;

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

    String Clinic_ID, PatientName;
    ArrayList<Integer> crtsa = new ArrayList<>() ;
    String crts_num;
    FragmentTransaction tran;
    Button next_button ;
    TextView page_text ;
    FragmentManager fm ;

    ImageView a_select, b_select, c_select ;
    int pre;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.crtsa_layout, container, false);

        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            pre = getArguments().getInt("pre", 0) ;
            if(pre==1){
                crtsa = getArguments().getIntegerArrayList("crtsa_num") ;
            }
            Log.v("crts_num", "partA fragment 작동"+pre);
        }
        if(pre!=1){
            c1_1 = -1 ;
            c1_2 = -1 ;
            c1_3 = -1 ;
            c2_1 = -1 ;
            c2_2 = -1 ;
            c2_3 = -1 ;
            c3_1 = -1 ;
            c3_2 = -1 ;
            c3_3 = -1 ;
            c4_1 = -1 ;
            c4_2 = -1 ;
            c4_3 = -1 ;
            c5_1 = -1 ;
            c5_2 = -1 ;
            c5_3 = -1 ;
            c6_1 = -1 ;
            c6_2 = -1 ;
            c6_3 = -1 ;
            c7_1 = -1 ;
            c7_2 = -1 ;
            c7_3 = -1 ;
            c8_1 = -1 ;
            c8_2 = -1 ;
            c8_3 = -1 ;
            c9_1 = -1 ;
            c9_2 = -1 ;
            c9_3 = -1 ;
            c10_1 = -1 ;
            c10_2 = -1 ;
            c10_3 = -1 ;
        }
        else if(pre==1){
            c1_1 = crtsa.get(0) ;
            c2_1 = crtsa.get(1) ;
            c3_1 = crtsa.get(2);
            c4_1 = crtsa.get(3);
            c5_1 = crtsa.get(4);
            c6_1 = crtsa.get(5);
            c7_1 = crtsa.get(6);
            c8_1 = crtsa.get(7);
            c9_1 = crtsa.get(8);
            c10_1 = crtsa.get(9);
            c1_2 = crtsa.get(10);
            c2_2 = crtsa.get(11);
            c3_2 = crtsa.get(12);
            c4_2 = crtsa.get(13);
            c5_2 = crtsa.get(14);
            c6_2 = crtsa.get(15);
            c7_2 = crtsa.get(16);
            c8_2 = crtsa.get(17);
            c9_2 = crtsa.get(18);
            c10_2 = crtsa.get(19);
            c1_3 = crtsa.get(20);
            c2_3 = crtsa.get(21);
            c3_3 = crtsa.get(22);
            c4_3 = crtsa.get(23);
            c5_3 = crtsa.get(24);
            c6_3 = crtsa.get(25);
            c7_3 = crtsa.get(26);
            c8_3 = crtsa.get(27);
            c9_3 = crtsa.get(28);
            c10_3 = crtsa.get(29);
        }

        Log.v("crts_num", "partA fragment");
        List<Fragment> list = new ArrayList<>() ;
        list.add(new CRTS_parta_a_Fragment()) ;
        list.add(new CRTS_parta_b_Fragment()) ;
        list.add(new CRTS_parta_b_Fragment()) ;

        frag1 = new CRTS_parta_a_Fragment() ;
        frag2 = new CRTS_parta_b_Fragment() ;
        frag3 = new CRTS_parta_c_Fragment() ;

        pager = view.findViewById(R.id.crtsa_fragment) ;
        pagerAdapter = new SliderPagerAdapter(getActivity().getSupportFragmentManager(), 3) ;
        pager.setAdapter(pagerAdapter);
        if(pre==1) {
            pager.setCurrentItem(0);
        }

//        a_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(pre_num!=0) {
//                    setFrag(0) ;
//                    pre_num = 4 ;
//                }
//                else{
//                    pre_num=0 ;
//                }
//            }
//        }) ;
//
//        a_select_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(pre_num!=0) {
//                    setFrag(0) ;
//                    pre_num = 4 ;
//                }
//                else{
//                    pre_num=0 ;
//                }
//            }
//        }) ;
//
//        b_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(pre_num==0||pre_num==4){
//                    Log.v("되냐", "되냐11") ;
//                    if(fragment1()==true) {
//                        Log.v("되냐", "되냐14") ;
//                        pre_num = 1 ;
//                        setFrag(1) ;
//                    }
//                }
//                else if(pre_num==2){
//                    pre_num=1 ;
//                    setFrag(1) ;
//                }
//            }
//        }) ;
//        b_select_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(pre_num==0||pre_num==4){
//                    Log.v("되냐", "되냐11") ;
//                    if(fragment1()==true) {
//                        Log.v("되냐", "되냐14") ;
//                        pre_num = 1 ;
//                        setFrag(1) ;
//                    }
//                }
//                else if(pre_num==2){
//                    pre_num=1 ;
//                    setFrag(1) ;
//                }
//            }
//        }) ;
//        c_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(pre_num==1){
//                    Log.v("되냐", "되냐31") ;
//                    if(fragment2()==true) {
//                        Log.v("되냐", "되냐34") ;
//                        pre_num = 2 ;
//                        setFrag(2) ;
//                    }
//                }
//
//                else {
//                    if(fragment1()==true) {
//                        pre_num = 2 ;
//                        setFrag(2) ;
//                    }
//                }
//            }
//        }) ;
//        c_select_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(pre_num==1){
//                    if(fragment2()==true) {
//                        pre_num = 2 ;
//                        setFrag(2) ;
//                    }
//                }
//                else {
//                    if(fragment1()==true) {
//                        pre_num = 2 ;
//                        setFrag(2) ;
//                    }
//                }
//            }
//        }) ;
//        next_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(fragment3()==true){
//                    int[] array = new int[30] ;
//
//                    ((CRTSActivity)getActivity()).frgment1();
//                }
//                else{
//                }
//            }
//        });

        return view;
    }

    public void setFrag(int n) {
        fm = getFragmentManager();
        tran= fm.beginTransaction();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putInt("pre", pre);
                //frag1 = new CRTS_parta_a_Fragment() ;
                frag1.setArguments(bundle1);
                tran.replace(R.id.crtsa_fragment, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putInt("pre", pre);
                frag2.setArguments(bundle2);
                getFragmentManager().beginTransaction().replace(R.id.crtsa_fragment, frag2).commit() ;
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putInt("pre", pre);
                //frag3 = new CRTS_parta_c_Fragment() ;
                frag3.setArguments(bundle3);
                getFragmentManager().beginTransaction().replace(R.id.crtsa_fragment, frag3).commit() ;
                break;

        }
    }
    public boolean fragment0() {
        bool = true ;
        if(c1_1==-1 || c1_2==-1 || c1_3==-1){
            bool = false ;
        }
        if(c2_1==-1 || c2_2==-1 || c2_3 == -1) {
            bool = false ;
        }
        if(c3_1==-1 || c3_2==-1 || c3_3 == -1) {
            bool = false ;
        }
        if(c4_1==-1 || c4_2==-1 || c4_3 == -1) {
            bool = false ;
        }
        if(c5_1 == -1 || c5_2==-1 || c5_3 == -1) {
            bool = false ;
        }
        if(c6_1==-1 || c6_2==-1 || c6_3 == -1) {
            bool = false ;
        }
        if(c7_1==-1 || c7_2==-1 || c7_3 == -1) {
            bool = false ;
        }
        if(c8_1==-1 || c8_2==-1 || c8_3 == -1) {
            bool = false ;
        }
        if(c9_1==-1 || c9_2==-1 || c9_3 == -1) {
            bool = false ;
        }
        if(c10_1==-1 || c10_2==-1 || c10_3 == -1) {
            bool = false ;
        }
        Log.v("CRTS_parta", "CRTS = //" + c1_1 + " " + c1_2+ " " + c1_3+ " " + c2_1+ " " + c2_2+ " " + c2_3+ " " + c3_1+ " " + c3_2+ " " + c3_3+ " "
                + c4_1+ " " + c4_2+ " " + c4_3+ " " + c5_1+ " " + c5_2+ " " + c5_3+ " " + c6_1+ " " + c6_2+ " " + c6_3+ " " + c7_1+ " " + c7_2+ " " + c7_3 + " " +
                c8_1 + " " + c8_2 + " " + c8_3+ " " + c9_1+ " " + c9_2 + " " + c9_3+ " " + c10_1+ " " + c10_2+ " " + c10_3) ;
        return bool ;
    }
    public boolean fragment1(){
        CRTS_parta_a_Fragment crts_parta_a_fragment = (CRTS_parta_a_Fragment) getFragmentManager().findFragmentById(R.id.crtsa_fragment);
        bool=true;
        if (crts_parta_a_fragment.crts1_1_0.isChecked()) {
            c1_1 = 0;
        } else if (crts_parta_a_fragment.crts1_1_1.isChecked()) {
            c1_1 = 1;
        } else if (crts_parta_a_fragment.crts1_1_2.isChecked()) {
            c1_1 = 2;
        } else if (crts_parta_a_fragment.crts1_1_3.isChecked()) {
            c1_1 = 3;
        } else if (crts_parta_a_fragment.crts1_1_4.isChecked()) {
            c1_1 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_a_fragment.crts2_1_0.isChecked()) {
            c2_1 = 0;
        } else if (crts_parta_a_fragment.crts2_1_1.isChecked()) {
            c2_1 = 1;
        } else if (crts_parta_a_fragment.crts2_1_2.isChecked()) {
            c2_1 = 2;
        } else if (crts_parta_a_fragment.crts2_1_3.isChecked()) {
            c2_1 = 3;
        } else if (crts_parta_a_fragment.crts2_1_4.isChecked()) {
            c2_1 = 4;
        } else {
            bool = false;
        }

        if (crts_parta_a_fragment.crts3_1_0.isChecked()) {
            c3_1 = 0;
        } else if (crts_parta_a_fragment.crts4_1_1.isChecked()) {
            c3_1 = 1;
        } else if (crts_parta_a_fragment.crts4_1_2.isChecked()) {
            c3_1 = 2;
        } else if (crts_parta_a_fragment.crts4_1_3.isChecked()) {
            c3_1 = 3;
        } else if (crts_parta_a_fragment.crts4_1_4.isChecked()) {
            c3_1 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_a_fragment.crts4_1_0.isChecked()) {
            c4_1 = 0;
        } else if (crts_parta_a_fragment.crts4_1_1.isChecked()) {
            c4_1 = 1;
        } else if (crts_parta_a_fragment.crts4_1_2.isChecked()) {
            c4_1 = 2;
        } else if (crts_parta_a_fragment.crts4_1_3.isChecked()) {
            c4_1 = 3;
        } else if (crts_parta_a_fragment.crts4_1_4.isChecked()) {
            c4_1 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_a_fragment.crts5_1_0.isChecked()) {
            c5_1 = 0;
        } else if (crts_parta_a_fragment.crts5_1_1.isChecked()) {
            c5_1 = 1;
        } else if (crts_parta_a_fragment.crts5_1_2.isChecked()) {
            c5_1 = 2;
        } else if (crts_parta_a_fragment.crts5_1_3.isChecked()) {
            c5_1 = 3;
        } else if (crts_parta_a_fragment.crts5_1_4.isChecked()) {
            c5_1 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_a_fragment.crts6_1_0.isChecked()) {
            c6_1 = 0;
        } else if (crts_parta_a_fragment.crts6_1_1.isChecked()) {
            c6_1 = 1;
        } else if (crts_parta_a_fragment.crts6_1_2.isChecked()) {
            c6_1 = 2;
        } else if (crts_parta_a_fragment.crts6_1_3.isChecked()) {
            c6_1 = 3;
        } else if (crts_parta_a_fragment.crts6_1_4.isChecked()) {
            c6_1 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_a_fragment.crts7_1_0.isChecked()) {
            c7_1 = 0;
        } else if (crts_parta_a_fragment.crts7_1_1.isChecked()) {
            c7_1 = 1;
        } else if (crts_parta_a_fragment.crts7_1_2.isChecked()) {
            c7_1 = 2;
        } else if (crts_parta_a_fragment.crts7_1_3.isChecked()) {
            c7_1 = 3;
        } else if (crts_parta_a_fragment.crts7_1_4.isChecked()) {
            c7_1 = 4;
        } else {
            bool = false;
        }
        if (crts_parta_a_fragment.crts8_1_0.isChecked()) {
            c8_1 = 0;
        } else if (crts_parta_a_fragment.crts8_1_1.isChecked()) {
            c8_1 = 1;
        } else if (crts_parta_a_fragment.crts8_1_2.isChecked()) {
            c8_1 = 2;
        } else if (crts_parta_a_fragment.crts8_1_3.isChecked()) {
            c8_1 = 3;
        } else if (crts_parta_a_fragment.crts8_1_4.isChecked()) {
            c8_1 = 4;
        } else {
            bool = false;
        }
        if (crts_parta_a_fragment.crts9_1_0.isChecked()) {
            c9_1 = 0;
        } else if (crts_parta_a_fragment.crts9_1_1.isChecked()) {
            c9_1 = 1;
        } else if (crts_parta_a_fragment.crts9_1_2.isChecked()) {
            c9_1 = 2;
        } else if (crts_parta_a_fragment.crts9_1_3.isChecked()) {
            c9_1 = 3;
        } else if (crts_parta_a_fragment.crts9_1_4.isChecked()) {
            c9_1 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_a_fragment.crts10_1_0.isChecked()) {
            c10_1 = 0;
        } else if (crts_parta_a_fragment.crts10_1_1.isChecked()) {
            c10_1 = 1;
        } else if (crts_parta_a_fragment.crts10_1_2.isChecked()) {
            c10_1 = 2;
        } else if (crts_parta_a_fragment.crts10_1_3.isChecked()) {
            c10_1 = 3;
        } else if (crts_parta_a_fragment.crts10_1_4.isChecked()) {
            c10_1 = 4;
        } else {
            bool = false;
        }
        return bool ;

    }
    public boolean fragment2(){
        CRTS_parta_b_Fragment crts_parta_b_fragment = (CRTS_parta_b_Fragment) getFragmentManager().findFragmentById(R.id.crtsa_fragment);
        bool = true;
        if ( crts_parta_b_fragment.crts1_2_0.isChecked()) {
            c1_2 = 0;
        } else if ( crts_parta_b_fragment.crts1_2_1.isChecked()) {
            c1_2 = 1;
        } else if ( crts_parta_b_fragment.crts1_2_2.isChecked()) {
            c1_2 = 2;
        } else if ( crts_parta_b_fragment.crts1_2_3.isChecked()) {
            c1_2 = 3;
        } else if ( crts_parta_b_fragment.crts1_2_4.isChecked()) {
            c1_2 = 4;
        } else {
            bool = false;
        }

        if ( crts_parta_b_fragment.crts2_2_0.isChecked()) {
            c2_2 = 0;
        } else if ( crts_parta_b_fragment.crts2_2_1.isChecked()) {
            c2_2 = 1;
        } else if ( crts_parta_b_fragment.crts2_2_2.isChecked()) {
            c2_2 = 2;
        } else if ( crts_parta_b_fragment.crts2_2_3.isChecked()) {
            c2_2 = 3;
        } else if ( crts_parta_b_fragment.crts2_2_4.isChecked()) {
            c2_2 = 4;
        } else {
            bool = false;
        }

        if (crts_parta_b_fragment.crts3_2_0.isChecked()) {
            c3_2 = 0;
        } else if (crts_parta_b_fragment.crts3_2_1.isChecked()) {
            c3_2 = 1;
        } else if (crts_parta_b_fragment.crts3_2_2.isChecked()) {
            c3_2 = 2;
        } else if (crts_parta_b_fragment.crts3_2_3.isChecked()) {
            c3_2 = 3;
        } else if (crts_parta_b_fragment.crts3_2_4.isChecked()) {
            c3_2 = 4;
        } else {
            bool = false;
        }

        if (crts_parta_b_fragment.crts4_2_0.isChecked()) {
            c4_2 = 0;
        } else if (crts_parta_b_fragment.crts4_2_1.isChecked()) {
            c4_2 = 1;
        } else if (crts_parta_b_fragment.crts4_2_2.isChecked()) {
            c4_2 = 2;
        } else if (crts_parta_b_fragment.crts4_2_3.isChecked()) {
            c4_2 = 3;
        } else if (crts_parta_b_fragment.crts4_2_4.isChecked()) {
            c4_2 = 4;
        } else {
            bool = false;
        }

        if (crts_parta_b_fragment.crts5_2_0.isChecked()) {
            c5_2 = 0;
        } else if (crts_parta_b_fragment.crts5_2_1.isChecked()) {
            c5_2 = 1;
        } else if (crts_parta_b_fragment.crts5_2_2.isChecked()) {
            c5_2 = 2;
        } else if (crts_parta_b_fragment.crts5_2_3.isChecked()) {
            c5_2 = 3;
        } else if (crts_parta_b_fragment.crts5_2_4.isChecked()) {
            c5_2 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_b_fragment.crts6_2_0.isChecked()) {
            c6_2 = 0;
        } else if (crts_parta_b_fragment.crts6_2_1.isChecked()) {
            c6_2 = 1;
        } else if (crts_parta_b_fragment.crts6_2_2.isChecked()) {
            c6_2 = 2;
        } else if (crts_parta_b_fragment.crts6_2_3.isChecked()) {
            c6_2 = 3;
        } else if (crts_parta_b_fragment.crts6_2_4.isChecked()) {
            c6_2 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_b_fragment.crts7_2_0.isChecked()) {
            c7_2 = 0;
        } else if (crts_parta_b_fragment.crts7_2_1.isChecked()) {
            c7_2 = 1;
        } else if (crts_parta_b_fragment.crts7_2_2.isChecked()) {
            c7_2 = 2;
        } else if (crts_parta_b_fragment.crts7_2_3.isChecked()) {
            c7_2 = 3;
        } else if (crts_parta_b_fragment.crts7_2_4.isChecked()) {
            c7_2 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_b_fragment.crts8_2_0.isChecked()) {
            c8_2 = 0;
        } else if (crts_parta_b_fragment.crts8_2_1.isChecked()) {
            c8_2 = 1;
        } else if (crts_parta_b_fragment.crts8_2_2.isChecked()) {
            c8_2 = 2;
        } else if (crts_parta_b_fragment.crts8_2_3.isChecked()) {
            c8_2 = 3;
        } else if (crts_parta_b_fragment.crts8_2_4.isChecked()) {
            c8_2 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_b_fragment.crts9_2_0.isChecked()) {
            c9_2 = 0;
        } else if (crts_parta_b_fragment.crts9_2_1.isChecked()) {
            c9_2 = 1;
        } else if (crts_parta_b_fragment.crts9_2_2.isChecked()) {
            c9_2 = 2;
        } else if (crts_parta_b_fragment.crts9_2_3.isChecked()) {
            c9_2 = 3;
        } else if (crts_parta_b_fragment.crts9_2_4.isChecked()) {
            c9_2 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_b_fragment.crts10_2_0.isChecked()) {
            c10_2 = 0;
        } else if (crts_parta_b_fragment.crts10_2_1.isChecked()) {
            c10_2 = 1;
        } else if (crts_parta_b_fragment.crts10_2_2.isChecked()) {
            c10_2 = 2;
        } else if (crts_parta_b_fragment.crts10_2_3.isChecked()) {
            c10_2 = 3;
        } else if (crts_parta_b_fragment.crts10_2_4.isChecked()) {
            c10_2 = 4;
        } else {
            bool = false;
        }
        return bool ;

    }
    public boolean fragment3(){
        CRTS_parta_c_Fragment crts_parta_c_fragment = (CRTS_parta_c_Fragment) getFragmentManager().findFragmentById(R.id.crtsa_fragment);
        bool = true;

        if (crts_parta_c_fragment .crts1_3_0.isChecked()) {
            c1_3 = 0;
        } else if (crts_parta_c_fragment .crts1_3_1.isChecked()) {
            c1_3 = 1;
        } else if (crts_parta_c_fragment .crts1_3_2.isChecked()) {
            c1_3 = 2;
        } else if (crts_parta_c_fragment .crts1_3_3.isChecked()) {
            c1_3 = 3;
        } else if (crts_parta_c_fragment .crts1_3_4.isChecked()) {
            c1_3 = 4;
        } else {
            bool = false;
        }

        if (crts_parta_c_fragment .crts2_3_0.isChecked()) {
            c2_3 = 0;
        } else if (crts_parta_c_fragment .crts2_3_1.isChecked()) {
            c2_3 = 1;
        } else if (crts_parta_c_fragment .crts2_3_2.isChecked()) {
            c2_3 = 2;
        } else if (crts_parta_c_fragment .crts2_3_3.isChecked()) {
            c2_3 = 3;
        } else if (crts_parta_c_fragment .crts2_3_4.isChecked()) {
            c2_3 = 4;
        } else {
            bool = false;
        }



        if (crts_parta_c_fragment .crts3_3_0.isChecked()) {
            c3_3 = 0;
        } else if (crts_parta_c_fragment .crts3_3_1.isChecked()) {
            c3_3 = 1;
        } else if (crts_parta_c_fragment .crts3_3_2.isChecked()) {
            c3_3 = 2;
        } else if (crts_parta_c_fragment .crts3_3_3.isChecked()) {
            c3_3 = 3;
        } else if (crts_parta_c_fragment .crts3_3_4.isChecked()) {
            c3_3 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_c_fragment .crts4_3_0.isChecked()) {
            c4_3 = 0;
        } else if (crts_parta_c_fragment .crts4_3_1.isChecked()) {
            c4_3 = 1;
        } else if (crts_parta_c_fragment .crts4_3_2.isChecked()) {
            c4_3 = 2;
        } else if (crts_parta_c_fragment .crts4_3_3.isChecked()) {
            c4_3 = 3;
        } else if (crts_parta_c_fragment .crts4_3_4.isChecked()) {
            c4_3 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_c_fragment .crts5_3_0.isChecked()) {
            c5_3 = 0;
        } else if (crts_parta_c_fragment .crts5_3_1.isChecked()) {
            c5_3 = 1;
        } else if (crts_parta_c_fragment .crts5_3_2.isChecked()) {
            c5_3 = 2;
        } else if (crts_parta_c_fragment .crts5_3_3.isChecked()) {
            c5_3 = 3;
        } else if (crts_parta_c_fragment .crts5_3_4.isChecked()) {
            c5_3 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_c_fragment .crts6_3_0.isChecked()) {
            c6_3 = 0;
        } else if (crts_parta_c_fragment .crts6_3_1.isChecked()) {
            c6_3 = 1;
        } else if (crts_parta_c_fragment .crts6_3_2.isChecked()) {
            c6_3 = 2;
        } else if (crts_parta_c_fragment .crts6_3_3.isChecked()) {
            c6_3 = 3;
        } else if (crts_parta_c_fragment .crts6_3_4.isChecked()) {
            c6_3 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_c_fragment .crts7_3_0.isChecked()) {
            c7_3 = 0;
        } else if (crts_parta_c_fragment .crts7_3_1.isChecked()) {
            c7_3 = 1;
        } else if (crts_parta_c_fragment .crts7_3_2.isChecked()) {
            c7_3 = 2;
        } else if (crts_parta_c_fragment .crts7_3_3.isChecked()) {
            c7_3 = 3;
        } else if (crts_parta_c_fragment .crts7_3_4.isChecked()) {
            c7_3 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_c_fragment .crts8_3_0.isChecked()) {
            c8_3 = 0;
        } else if (crts_parta_c_fragment .crts8_3_1.isChecked()) {
            c8_3 = 1;
        } else if (crts_parta_c_fragment .crts8_3_2.isChecked()) {
            c8_3 = 2;
        } else if (crts_parta_c_fragment .crts8_3_3.isChecked()) {
            c8_3 = 3;
        } else if (crts_parta_c_fragment .crts8_3_4.isChecked()) {
            c8_3 = 4;
        } else {
            bool = false;
        }

        if (crts_parta_c_fragment .crts9_3_0.isChecked()) {
            c9_3 = 0;
        } else if (crts_parta_c_fragment .crts9_3_1.isChecked()) {
            c9_3 = 1;
        } else if (crts_parta_c_fragment .crts9_3_2.isChecked()) {
            c9_3 = 2;
        } else if (crts_parta_c_fragment .crts9_3_3.isChecked()) {
            c9_3 = 3;
        } else if (crts_parta_c_fragment .crts9_3_4.isChecked()) {
            c9_3 = 4;
        } else {
            bool = false;
        }


        if (crts_parta_c_fragment .crts10_3_0.isChecked()) {
            c10_3 = 0;
        } else if (crts_parta_c_fragment .crts10_3_1.isChecked()) {
            c10_3 = 1;
        } else if (crts_parta_c_fragment .crts10_3_2.isChecked()) {
            c10_3 = 2;
        } else if (crts_parta_c_fragment .crts10_3_3.isChecked()) {
            c10_3 = 3;
        } else if (crts_parta_c_fragment .crts10_3_4.isChecked()) {
            c10_3 = 4;
        } else {
            bool = false;
        }

        return bool ;
    }


}