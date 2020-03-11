package com.ahnbcilab.tremorquantification.tremorquantification;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter3;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.ahnbcilab.tremorquantification.data.TaskItem3;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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


public class Spiral_List_Fragment extends Fragment {
    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_spiral;
    DatabaseReference database_patient;

    String Clinic_ID;
    String PatientName;
    String path;

    TextView righthand;
    TextView lefthand;
    TextView bothhand;

    View view;
    File file;

    int spiralbox;
    int crtsbox;

    CheckBox spiral_box;
    CheckBox crts_box;

    String timestamp;
    String handside;
    String taskName;
    String total_score, hz_score, magnitude_score, distance_score, time_score, speed_score;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    TaskListViewAdapter3 taskListViewAdapter;
    ArrayList<TaskItem3> tasks = new ArrayList<TaskItem3>();
    ArrayList<TaskItem3> selected_tasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 초기 화면
        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            handside = getArguments().getString("handside");
        }

        Log.v("handside!", "몇 번 들어와?");


        view = inflater.inflate(R.layout.spiral_list_fragment, container, false);
        spiral_box = (CheckBox)getActivity().findViewById(R.id.check_spiral);
        crts_box = (CheckBox)getActivity().findViewById(R.id.check_crts);

        righthand = (TextView)getActivity().findViewById(R.id.right_hand);
        lefthand = (TextView)getActivity().findViewById(R.id.left_hand);
        bothhand = (TextView)getActivity().findViewById(R.id.both_hand);

        database_patient = firebaseDatabase.getReference("PatientList");
        database_spiral = database_patient.child(Clinic_ID).child("Spiral List");
        recyclerView = (RecyclerView) view.findViewById(R.id.spiral_taskList);
        taskListViewAdapter = new TaskListViewAdapter3(getActivity(), tasks, selected_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListViewAdapter);
        //Log.v("Spiral", "Spiral List Fragment"+Clinic_ID) ;


        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 1;
                GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0, 0), true, 100);
                taskListViewAdapter.clear();
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                    for (int i = 0; i < number; i++) {
                        list(i, mData, graphView, series);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view1, int position) {
                Intent intent = null;
                if(handside.equalsIgnoreCase("both")) {

                }
                else {
                    intent = new Intent(getActivity(), Personal_spiral.class);
                }
                TextView taskDate = view1.findViewById(R.id.taskDate);
                TextView taskTime = view1.findViewById(R.id.taskTime);
                TextView taskNum = (TextView) view.findViewById(R.id.taskNum) ;
                TextView taskscore = view1.findViewById(R.id.taskscore);

                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("taskDate", taskDate.getText());
                intent.putExtra("taskTime", taskTime.getText());
                intent.putExtra("taskNum", position);
                intent.putExtra("handside", handside);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view1, int position) {

            }
        }));

        initViews();
        return view;

    }

    private void initViews() {

        spiral_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    spiralbox = 1;
                    Log.d("TAG=>isChecked", "spiral True");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int count = 1;
                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    if(crtsbox == 1 && spiralbox == 1){
                                        list(i, mData, graphView, series);
                                    }
                                    else{
                                        list1(i, mData, graphView, series);
                                    }
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                else {
                    spiralbox = 0;
                    Log.d("TAG=>isChecked", "spiral false");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int count = 1;
                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    list1(i, mData, graphView, series);
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        crts_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    crtsbox = 1;
                    Log.d("TAG=>isChecked", "spiral True");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int count = 1;
                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    if(crtsbox == 1 && spiralbox == 1){
                                        list(i, mData, graphView, series);
                                    }
                                    else{
                                        list1(i, mData, graphView, series);
                                    }
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                else {
                    crtsbox = 0;
                    Log.d("TAG=>isChecked", "spiral false");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int count = 1;
                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    list1(i, mData, graphView, series);
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }





    public void list(final int i, final DataSnapshot mData, GraphView graphView, LineGraphSeries<DataPoint> series) {
        if(handside.equals("Right")){
            Query query = database_patient.child(Clinic_ID).child("Spiral List").child("Right").orderByChild("Right_total_count").equalTo(i);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        timestamp = String.valueOf(mData.child("Spiral List").child("Right").child(key).child("timestamp").getValue());
                        taskName = String.valueOf(mData.child("Spiral List").child("Right").child(key).child("path").getValue());
                        if(taskName.equals("Spiral_Test")){
                            taskName = "Spiral";
                        }
                        String taskDate = timestamp.substring(0, timestamp.indexOf(" "));

                        String taskTime =  timestamp.substring(timestamp.indexOf(" ") + 1);


                        if(spiral_box.isChecked() && crts_box.isChecked()){
                            tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                            taskListViewAdapter.notifyDataSetChanged();
                        }
                        else if(spiral_box.isChecked() && (crts_box.isChecked() == false)){
                            if(taskName.equals("Spiral")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else if((spiral_box.isChecked() == false) && crts_box.isChecked()){
                            if(taskName.equals("CRTS")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else{

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(handside.equals("Left")){
            Query query = database_patient.child(Clinic_ID).child("Spiral List").child("Left").orderByChild("Left_total_count").equalTo(i);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        timestamp = String.valueOf(mData.child("Spiral List").child("Left").child(key).child("timestamp").getValue());
                        taskName = String.valueOf(mData.child("Spiral List").child("Left").child(key).child("path").getValue());
                        if(taskName.equals("Spiral_Test")){
                            taskName = "Spiral";
                        }
                        String taskDate = timestamp.substring(0, timestamp.indexOf(" "));

                        String taskTime =  timestamp.substring(timestamp.indexOf(" ") + 1);


                        if(spiral_box.isChecked() && crts_box.isChecked()){
                            tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                            taskListViewAdapter.notifyDataSetChanged();
                        }
                        else if(spiral_box.isChecked() && (crts_box.isChecked() == false)){
                            if(taskName.equals("Spiral")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else if((spiral_box.isChecked() == false) && crts_box.isChecked()){
                            if(taskName.equals("CRTS")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else{

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{

        }

    }

    public void list1(final int i, final DataSnapshot mData, GraphView graphView, LineGraphSeries<DataPoint> series) {

        if(handside.equals("Right")){
            Query query = database_patient.child(Clinic_ID).child("Spiral List").child("Right").orderByChild("Right_SPIRAL_count").equalTo(i);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        timestamp = String.valueOf(mData.child("Spiral List").child("Right").child(key).child("timestamp").getValue());
                        taskName = String.valueOf(mData.child("Spiral List").child("Right").child(key).child("path").getValue());
                        Log.v("TAGG", taskName+"!!");
                        if(taskName.equals("Spiral_Test")){
                            taskName = "Spiral";
                        }
                        String taskDate = timestamp.substring(0, timestamp.indexOf(" "));

                        String taskTime = timestamp.substring(timestamp.indexOf(" ") + 1);


                        if(spiral_box.isChecked() && crts_box.isChecked()){
                            tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                            taskListViewAdapter.notifyDataSetChanged();
                        }
                        else if(spiral_box.isChecked() && (crts_box.isChecked() == false)){
                            if(taskName.equals("Spiral")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else if((spiral_box.isChecked() == false) && crts_box.isChecked()){
                            if(taskName.equals("CRTS")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else{

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(handside.equals("Left")){
            Query query = database_patient.child(Clinic_ID).child("Spiral List").child("Left").orderByChild("Left_SPIRAL_count").equalTo(i);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        timestamp = String.valueOf(mData.child("Spiral List").child("Left").child(key).child("timestamp").getValue());
                        taskName = String.valueOf(mData.child("Spiral List").child("Left").child(key).child("path").getValue());

                        Log.v("TAGG", taskName+"!!");

                        if(taskName.equals("Spiral_Test")){
                            taskName = "Spiral";
                        }
                        String taskDate = timestamp.substring(0, timestamp.indexOf(" "));

                        String taskTime =  timestamp.substring(timestamp.indexOf(" ") + 1);


                        if(spiral_box.isChecked() && crts_box.isChecked()){
                            tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                            taskListViewAdapter.notifyDataSetChanged();
                        }
                        else if(spiral_box.isChecked() && (crts_box.isChecked() == false)){
                            if(taskName.equals("Spiral")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else if((spiral_box.isChecked() == false) && crts_box.isChecked()){
                            if(taskName.equals("CRTS")){
                                tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName ,taskName));
                                taskListViewAdapter.notifyDataSetChanged();

                            }
                        }
                        else{

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{

        }

    }

}

