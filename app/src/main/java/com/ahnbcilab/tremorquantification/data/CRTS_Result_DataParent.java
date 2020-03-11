package com.ahnbcilab.tremorquantification.data;

import android.os.Parcel;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;
import java.util.UUID;

public class CRTS_Result_DataParent extends ExpandableGroup<CRTS_Result_DataChild> {

    private String totalScore;
    private String title ;

    public CRTS_Result_DataParent(String title, List<CRTS_Result_DataChild> items, String totalScore) {
        super(title, items);
        this.totalScore = totalScore;
        this.title = title;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
