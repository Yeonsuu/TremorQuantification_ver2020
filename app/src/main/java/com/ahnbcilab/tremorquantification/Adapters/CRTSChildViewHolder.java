package com.ahnbcilab.tremorquantification.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.CRTS_Result_DataChild;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class CRTSChildViewHolder extends com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder {
    ImageView image;
    TextView hz_score;
    TextView magnitude_score;
    TextView distance_score;
    TextView time_score;
    TextView speed_score;

    public CRTSChildViewHolder(View itemView) {
        super(itemView) ;

        image = itemView.findViewById(R.id.image);
        hz_score = itemView.findViewById(R.id.hz_score);
        magnitude_score = itemView.findViewById(R.id.magnitude_score);
        distance_score = itemView.findViewById(R.id.distance_score);
        time_score = itemView.findViewById(R.id.time_score);
        speed_score = itemView.findViewById(R.id.speed_score);
    }
    public void bind(CRTS_Result_DataChild child) {
        image.setBackgroundResource(child.getImage());
        hz_score.setText((int) child.getHz_score() + "");
        magnitude_score.setText((int) child.getMagnitude_score() + "");
        distance_score.setText((int) child.getDistance_score() + "");
        time_score.setText((int) child.getTime_score() + "");
        speed_score.setText((int) child.getSpeed_score() + "");

    }
}
