package com.ahnbcilab.tremorquantification.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ahnbcilab.tremorquantification.tremorquantification.DistanceFragment;
import com.ahnbcilab.tremorquantification.tremorquantification.HZFragment;
import com.ahnbcilab.tremorquantification.tremorquantification.MagnitudeFragment;
import com.ahnbcilab.tremorquantification.tremorquantification.SpeedFragment;
import com.ahnbcilab.tremorquantification.tremorquantification.TimeFragment;
import com.ahnbcilab.tremorquantification.tremorquantification.TotalFragment;


public class ContentsPagerAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;
    private String clinic_ID;
    private String handside;



    public ContentsPagerAdapter(android.support.v4.app.FragmentManager fm, int pageCount) {

        super(fm);

        this.mPageCount = pageCount;

    }



    @Override

    public android.support.v4.app.Fragment getItem(int position) {


        switch (position) {

            case 0:

                TotalFragment totalFragment = new TotalFragment();
                return totalFragment;



            case 1:

                HZFragment hzFragment = new HZFragment();

                return hzFragment;



            case 2:

                MagnitudeFragment magnitudeFragment = new MagnitudeFragment();

                return magnitudeFragment;



            case 3:

                DistanceFragment distanceFragment = new DistanceFragment();

                return distanceFragment;



            case 4:

                TimeFragment timeFragment = new TimeFragment();

                return timeFragment;

            case 5:

                SpeedFragment speedFragment = new SpeedFragment();
                return speedFragment;



            default:
                totalFragment = new TotalFragment();
                return totalFragment;
        }

    }



    @Override

    public int getCount() {

        return mPageCount;

    }

}