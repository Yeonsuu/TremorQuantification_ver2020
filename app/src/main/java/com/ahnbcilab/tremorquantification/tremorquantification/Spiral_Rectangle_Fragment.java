package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
import com.ahnbcilab.tremorquantification.Adapters.ItemDecoration;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter2;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.ahnbcilab.tremorquantification.data.TaskItem2;
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

public class Spiral_Rectangle_Fragment extends Fragment {
    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_spiral;
    DatabaseReference database_patient;

    String Clinic_ID;
    String PatientName;
    String path;
    View view;
    File file;

    CheckBox spiral_box;
    CheckBox crts_box;

    String timestamp;
    String taskName;
    String tasktime;
    String handside;
    String image_url;
    String hz_score, magnitude_score, distance_score, time_score, velocity_score;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    TaskListViewAdapter2 taskListViewAdapter2;
    ArrayList<TaskItem2> tasks = new ArrayList<TaskItem2>();
    ArrayList<TaskItem2> selected_tasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 초기 화면
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            handside = getArguments().getString("handside");
            //uid = getArguments().getString("doc_uid");
        }
        view = inflater.inflate(R.layout.spiral_rectangle_fragment, container, false);
        database_patient = firebaseDatabase.getReference("PatientList");
        database_spiral = database_patient.child(Clinic_ID).child("Spiral List");
        spiral_box = (CheckBox) getActivity().findViewById(R.id.check_spiral);
        crts_box = (CheckBox) getActivity().findViewById(R.id.check_crts);
        recyclerView = (RecyclerView) view.findViewById(R.id.spiral_taskRectangle);
        taskListViewAdapter2 = new TaskListViewAdapter2(getActivity(), tasks, selected_tasks);
        recyclerView.addItemDecoration(new ItemDecoration(view.getContext()));
        recyclerViewLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListViewAdapter2);

        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0, 0), true, 100);
                taskListViewAdapter2.clear();
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                    for (int i = 0; i < number; i++) {
                        list2(i, mData, graphView, series);
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
                TextView taskNum = (TextView) view.findViewById(R.id.taskNum) ;
                TextView taskTime = (TextView) view.findViewById(R.id.taskTime) ;
                TextView taskDate = (TextView) view.findViewById(R.id.taskDate) ;

                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("handside", handside);
                intent.putExtra("taskTime", taskTime.getText().toString());
                intent.putExtra("taskDate", taskDate.getText().toString());
                intent.putExtra("taskNum", position);
                Log.v("테스크 넘버 확인3", taskNum.getText().toString());
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
                    Log.d("TAG=>isChecked", "spiral True");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter2.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    list2(i, mData, graphView, series);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Log.d("TAG=>isChecked", "spiral false");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter2.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    list2(i, mData, graphView, series);
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
                    Log.d("TAG=>isChecked", "crts True");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter2.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    list2(i, mData, graphView, series);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Log.d("TAG=>isChecked", "crts false");//replace your own stuffs here
                    database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                            series.appendData(new DataPoint(0, 0), true, 100);
                            taskListViewAdapter2.clear();
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                Long number = mData.child("Spiral List").child(handside).getChildrenCount();
                                for (int i = 0; i < number; i++) {
                                    list2(i, mData, graphView, series);
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


    private void list2(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series) {
        if (handside.equals("Right")) {
            Query query = database_patient.child(Clinic_ID).child("Spiral List").child(handside).orderByChild("Right_total_count").equalTo(i);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        timestamp = String.valueOf(mData.child("Spiral List").child(handside).child(key).child("timestamp").getValue());
                        String taskDate = timestamp.substring(0, timestamp.indexOf(" "));
                        taskDate = taskDate.substring(2, 10);
                        tasktime = timestamp.substring(timestamp.indexOf(" ") + 1, timestamp.lastIndexOf(":"));
                        image_url = String.valueOf(mData.child("Spiral List").child(handside).child(key).child("URL").getValue());
                        taskName = String.valueOf(mData.child("Spiral List").child(handside).child(key).child("path").getValue());
                        if (taskName.equals("Spiral_Test")) {
                            taskName = "Spiral";
                        }
                        if (spiral_box.isChecked() && crts_box.isChecked()) {
                            tasks.add(new TaskItem2(taskDate, tasktime,taskName, String.valueOf(i + 1), image_url, taskName,""));
                            taskListViewAdapter2.notifyDataSetChanged();
                        } else if (spiral_box.isChecked() && (crts_box.isChecked() == false)) {
                            if (taskName.equals("Spiral")) {
                                tasks.add(new TaskItem2(taskDate, tasktime,taskName, String.valueOf(i + 1), image_url, taskName,""));
                                taskListViewAdapter2.notifyDataSetChanged();

                            }
                        } else if ((spiral_box.isChecked() == false) && crts_box.isChecked()) {
                            if (taskName.equals("CRTS")) {
                                tasks.add(new TaskItem2(taskDate, tasktime,taskName, String.valueOf(i + 1), image_url, taskName,""));
                                taskListViewAdapter2.notifyDataSetChanged();

                            }
                        } else {

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (handside.equals("Left")) {
            Query query = database_patient.child(Clinic_ID).child("Spiral List").child(handside).orderByChild("Left_total_count").equalTo(i);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        timestamp = String.valueOf(mData.child("Spiral List").child(handside).child(key).child("timestamp").getValue());
                        String taskDate = timestamp.substring(0, timestamp.indexOf(" "));
                        taskDate = taskDate.substring(2, 10);
                        tasktime = timestamp.substring(timestamp.indexOf(" ") + 1, timestamp.lastIndexOf(":"));
                        image_url = String.valueOf(mData.child("Spiral List").child(handside).child(key).child("URL").getValue());
                        taskName = String.valueOf(mData.child("Spiral List").child(handside).child(key).child("path").getValue());
                        if (taskName.equals("Spiral_Test")) {
                            taskName = "Spiral";
                        }
                        if (spiral_box.isChecked() && crts_box.isChecked()) {
                            tasks.add(new TaskItem2(taskDate, tasktime,taskName, String.valueOf(i + 1), image_url, taskName,""));
                            taskListViewAdapter2.notifyDataSetChanged();
                        } else if (spiral_box.isChecked() && (crts_box.isChecked() == false)) {
                            if (taskName.equals("Spiral")) {
                                tasks.add(new TaskItem2(taskDate, tasktime,taskName, String.valueOf(i + 1), image_url, taskName,""));
                                taskListViewAdapter2.notifyDataSetChanged();

                            }
                        } else if ((spiral_box.isChecked() == false) && crts_box.isChecked()) {
                            if (taskName.equals("CRTS")) {
                                tasks.add(new TaskItem2(taskDate, tasktime,taskName, String.valueOf(i + 1), image_url, taskName,""));
                                taskListViewAdapter2.notifyDataSetChanged();

                            }
                        } else {

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

        }


    }


}

