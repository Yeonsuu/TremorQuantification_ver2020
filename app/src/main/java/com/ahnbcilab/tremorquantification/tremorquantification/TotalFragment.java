package com.ahnbcilab.tremorquantification.tremorquantification;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class TotalFragment extends Fragment {
    View view;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_spiral;
    DatabaseReference database_patient;

    int spiral_count = 0;
    String totalscore = "";


    SpiralTask_Fragment spiralright;
    private String Clinic_ID;
    private String handside;
    private String PatientName;
    private String type ;
    private String count ;
    private String result ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_total, container, false);
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            handside = getArguments().getString("handside");
            PatientName = getArguments().getString("PatientName");
            type = getArguments().getString("Type") ;
        }
        //spiralright = (SpiralTask_Fragment)getFragmentManager().findFragmentById(R.id.main_frame);
        if(type.contains("Spiral")){
            result = "Spiral_Result" ;
        }
        else{
            result = "Line_Result" ;
        }
        database_patient = firebaseDatabase.getReference("PatientList");
        database_spiral = database_patient.child(Clinic_ID).child(type);
        database_spiral.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    spiral_count = (int) dataSnapshot.child(handside).getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //int count = 1;
                GraphView graphView = (GraphView) view.findViewById(R.id.total_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0, 0), true, 100);
                for (DataSnapshot mData : dataSnapshot.getChildren()) {

                    Long number = mData.child(type).child(handside).getChildrenCount();
                    for (int i = 0; i <= number; i++) {
                        list(i, mData, graphView, series, String.valueOf(spiral_count));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series, final String spiral_num) {
        Query query = database_patient.child(Clinic_ID).child(type).child(handside).orderByChild(handside+"_total_count").equalTo(i);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    totalscore = String.valueOf(mData.child(type).child(handside).child(key).child(result).child("total").getValue());
                    series.appendData(new DataPoint(i + 1, Float.parseFloat(totalscore)), true, 100);
                    series.setColor(Color.parseColor("#78B5AA"));
                    graphView.removeAllSeries();
                    Log.v("Line", "Line :: " +key) ;
                    graphView.addSeries(series);
                    graphView.getViewport().setScalableY(true);
                    graphView.getViewport().setScrollableY(true);
                    graphView.getViewport().setMinX(0);
                    graphView.getViewport().setMaxX(Integer.parseInt(spiral_num));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
