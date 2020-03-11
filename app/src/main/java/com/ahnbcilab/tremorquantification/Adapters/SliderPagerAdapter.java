package com.ahnbcilab.tremorquantification.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ahnbcilab.tremorquantification.tremorquantification.CRTS_parta_a_Fragment;
import com.ahnbcilab.tremorquantification.tremorquantification.CRTS_parta_b_Fragment;
import com.ahnbcilab.tremorquantification.tremorquantification.CRTS_parta_c_Fragment;

import java.util.List;

public class SliderPagerAdapter extends FragmentStatePagerAdapter {

    int frag_count = 0 ;

    private List<Fragment> fragmentList ;
    public SliderPagerAdapter(FragmentManager fm,int frag_count) {
        super(fm);
        this.frag_count = frag_count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 : return new CRTS_parta_a_Fragment();
            case 1 : return new CRTS_parta_b_Fragment();
            case 2 : return new CRTS_parta_c_Fragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return this.frag_count;
    }
}
