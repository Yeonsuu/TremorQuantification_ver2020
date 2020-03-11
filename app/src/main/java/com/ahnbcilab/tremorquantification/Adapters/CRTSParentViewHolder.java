package com.ahnbcilab.tremorquantification.Adapters;

import android.view.View;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataChild;
import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataParent;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class CRTSParentViewHolder extends GroupViewHolder {
    TextView textView1;
    TextView totalScore;

    public CRTSParentViewHolder(View itemView){
        super(itemView) ;

        textView1 = itemView.findViewById(R.id.textView1);
        totalScore = itemView.findViewById(R.id.totalScore);
    }
    public void bind(CRTS_Result_DataParent parent){
        textView1.setText(parent.getTitle());
        totalScore.setText(parent.getTotalScore());
    }
}
