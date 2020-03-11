package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter3;
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

import java.io.File;
import java.util.ArrayList;


public class Writing_List_Fragment extends Fragment {
    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_writing;
    DatabaseReference database_patient;

    String Clinic_ID;
    String PatientName;
    String path;

    View view;
    File file;


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
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
        }


        view = inflater.inflate(R.layout.writing_list_fragment, container, false);


        database_patient = firebaseDatabase.getReference("PatientList");
        database_writing = database_patient.child(Clinic_ID).child("Writing List");
        recyclerView = (RecyclerView) view.findViewById(R.id.writing_taskList);
        taskListViewAdapter = new TaskListViewAdapter3(getActivity(), tasks, selected_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListViewAdapter);


        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                taskListViewAdapter.clear();
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    Long number = mData.child("Writing List").getChildrenCount();
                    for (int i = 0; i < number; i++) {
                        list(i, mData);
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

                Intent intent = new Intent(getActivity(), Personal_writing.class);
                TextView taskDate = view1.findViewById(R.id.taskDate);
                TextView taskTime = view1.findViewById(R.id.taskTime);
                intent.putExtra("taskNum", position);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("taskDate", taskDate.getText());
                intent.putExtra("taskTime", taskTime.getText());

                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view1, int position) {

            }
        }));

        return view;

    }


    public void list(final int i, final DataSnapshot mData) {
        Query query = database_patient.child(Clinic_ID).child("Writing List").orderByChild("writing_count").equalTo(i);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    timestamp = String.valueOf(mData.child("Writing List").child(key).child("timestamp").getValue());
                    taskName = "";

                    String taskDate = timestamp.substring(0, timestamp.indexOf(" "));

                    String taskTime = timestamp.substring(timestamp.indexOf(" ") + 1, timestamp.lastIndexOf(":"));

                    tasks.add(new TaskItem3(String.valueOf(i + 1), taskDate, taskTime, taskName, taskName));
                    taskListViewAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

