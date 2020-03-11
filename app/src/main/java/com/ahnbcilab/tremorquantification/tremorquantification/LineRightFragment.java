package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahnbcilab.tremorquantification.Adapters.ContentsPagerAdapter;


public class LineRightFragment extends Fragment {

    View view;
    private TabLayout mTabLayout;
    private FragmentActivity myContext;

    FragmentManager fm;
    FragmentTransaction tran;

    TotalFragment frag1;
    HZFragment frag2;
    MagnitudeFragment frag3;
    DistanceFragment frag4;
    TimeFragment frag5;
    SpeedFragment frag6;

    String Clinic_ID;
    String PatientName;

    String handside = "Right";

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spiral_right, container, false);

        Log.v("kaka", "여기와?");

        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            handside = getArguments().getString("handside");
            PatientName = getArguments().getString("PatientName");
        }

        mTabLayout = (TabLayout) view.findViewById(R.id.spiral_right_tab);
        frag1 = new TotalFragment();
        frag2 = new HZFragment();
        frag3 = new MagnitudeFragment();
        frag4 = new DistanceFragment();
        frag5 = new TimeFragment();
        frag6 = new SpeedFragment();

        mTabLayout.addTab(mTabLayout.newTab().setText("Total"));
        mTabLayout.addTab(mTabLayout.newTab().setText("떨림의 주파"));
        mTabLayout.addTab(mTabLayout.newTab().setText("떨림의 세기"));
        mTabLayout.addTab(mTabLayout.newTab().setText("벗어난 거리"));
        mTabLayout.addTab(mTabLayout.newTab().setText("검사 수행 시간"));
        mTabLayout.addTab(mTabLayout.newTab().setText("검사 평균 속도"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                setFrag(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setFrag(0);


        return view;
    }

    public void setFrag(int i) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (i) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("handside", handside);
                bundle1.putString("Type","Line List");
                frag1.setArguments(bundle1);
                tran.replace(R.id.spiral_right_viewpager, frag1);
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("handside", handside);
                bundle2.putString("Type","Line List");
                frag2.setArguments(bundle2);
                tran.replace(R.id.spiral_right_viewpager, frag2);
                tran.commit();
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("handside", handside);
                bundle3.putString("Type","Line List");
                frag3.setArguments(bundle3);
                tran.replace(R.id.spiral_right_viewpager, frag3);
                tran.commit();
                break;
            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("Clinic_ID", Clinic_ID);
                bundle4.putString("PatientName", PatientName);
                bundle4.putString("handside", handside);
                bundle4.putString("Type","Line List");
                frag4.setArguments(bundle4);
                tran.replace(R.id.spiral_right_viewpager, frag4);
                tran.commit();
                break;
            case 4:
                Bundle bundle5 = new Bundle();
                bundle5.putString("Clinic_ID", Clinic_ID);
                bundle5.putString("PatientName", PatientName);
                bundle5.putString("handside", handside);
                bundle5.putString("Type","Line List");
                frag5.setArguments(bundle5);
                tran.replace(R.id.spiral_right_viewpager, frag5);
                tran.commit();
                break;
            case 5:
                Bundle bundle6 = new Bundle();
                bundle6.putString("Clinic_ID", Clinic_ID);
                bundle6.putString("PatientName", PatientName);
                bundle6.putString("handside", handside);
                bundle6.putString("Type","Line List");
                frag6.setArguments(bundle6);
                tran.replace(R.id.spiral_right_viewpager, frag6);
                tran.commit();
                break;

        }
    }

}
