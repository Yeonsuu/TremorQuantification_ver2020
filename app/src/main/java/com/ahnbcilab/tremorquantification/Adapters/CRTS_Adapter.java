package com.ahnbcilab.tremorquantification.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahnbcilab.tremorquantification.data.CRTS;
import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataChild;
import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataParent;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;

import android.animation.ValueAnimator;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.CRTS_Result_Data;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CRTS_Adapter extends ExpandableRecyclerViewAdapter<CRTSParentViewHolder, CRTSChildViewHolder> {

    public CRTS_Adapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CRTSParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crts_result_item, parent, false) ;
        return new CRTSParentViewHolder(v);
    }

    @Override
    public CRTSChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crts_result_item2, parent, false) ;
        return new CRTSChildViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(CRTSChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final CRTS_Result_DataChild child = (CRTS_Result_DataChild) group.getItems().get(childIndex) ;
        holder.bind(child) ;
    }

    @Override
    public void onBindGroupViewHolder(CRTSParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        final CRTS_Result_DataParent parent = (CRTS_Result_DataParent) group ;
        holder.bind(parent) ;

    }
}
