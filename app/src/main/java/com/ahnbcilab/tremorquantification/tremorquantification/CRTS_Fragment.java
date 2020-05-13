package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class CRTS_Fragment extends Fragment {
    RecyclerView recyclerView;
    TaskListViewAdapter taskListViewAdapter;
    ArrayList<TaskItem> tasks = new ArrayList<TaskItem>();
    ArrayList<TaskItem> selected_tasks = new ArrayList<TaskItem>();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_crts;
    DatabaseReference database_patient;

    String Clinic_ID;
    String PatientName;

    View view;
    File file;
    String m = "0";
    String timestamp;

    String crts_score;
    String partA_score, partB_score, partC_score;

    TextView recentdate;
    TextView clientName;
    TextView crtsCount;
    String taskType ;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        // 이전 Activity value 받아오기
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            taskType = getArguments().getString("taskType");
            m = getArguments().getString("taskNum");
        }
        view = inflater.inflate(R.layout.task_crts_fragment, container, false);
        database_patient = firebaseDatabase.getReference("PatientList");
        database_crts = database_patient.child(Clinic_ID).child("CRTS List");
        recyclerView = (RecyclerView) view.findViewById(R.id.personal_crts_taskList);
        recentdate = (TextView) view.findViewById(R.id.recent_date);
        clientName = (TextView) view.findViewById(R.id.client_name);
        crtsCount = (TextView) view.findViewById(R.id.client_crts_count);
        taskListViewAdapter = new TaskListViewAdapter(getActivity(), tasks, selected_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListViewAdapter);


        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //int count = 1;
                GraphView graphView = (GraphView) view.findViewById(R.id.crts_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0, 0), true, 172);
                taskListViewAdapter.clear();
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    Long number = mData.child("CRTS List").getChildrenCount();
                    //Toast.makeText(view.getContext(), number+"", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < number; i++) {
                        list(i, mData, graphView, series, m);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button add_crts = (Button) view.findViewById(R.id.crts_add);

        add_crts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), CRTSActivity.class);
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", "main");
                intent.putExtra("crts_num", m);
                startActivity(intent);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view1, int position) {
                TextView taskDate = view1.findViewById(R.id.taskDate);
                TextView taskTime = view1.findViewById(R.id.taskTime);
                TextView taskscore = view1.findViewById(R.id.taskscore);
                Intent intent = new Intent(getActivity(), CRTS_Result_Activity.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("taskscore", taskscore.getText());
                intent.putExtra("timestamp", taskDate.getText() + " " + taskTime.getText());
                intent.putExtra("crts_num", m);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view1, int position) {

            }
        }));


        return view;

    }


    // write File
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "CRTS_task_num.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    // read File
    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(Clinic_ID + "CRTS_task_num.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series, final String crts_num) {
        Query query = database_patient.child(Clinic_ID).child("CRTS List").orderByChild("CRTS_count").equalTo(i);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();

                    partA_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partA_score").getValue());
                    partB_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partB_score").getValue());
                    partC_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partC_score").getValue());
                    crts_score = String.valueOf(Integer.parseInt(partA_score) + Integer.parseInt(partB_score) + Integer.parseInt(partC_score));
                    String crts_date = String.valueOf(mData.child("CRTS List").child(key).child("timestamp").getValue());
                    crts_date = crts_date.substring(2, 10);
                    series.appendData(new DataPoint(i + 1, Integer.parseInt(crts_score)), true, 172);
                    //series.setDrawDataPoints(true);
                    series.setColor(Color.parseColor("#78B5AA"));
                    graphView.removeAllSeries();
                    graphView.addSeries(series);
                    graphView.getViewport().setScalableY(true);
                    graphView.getViewport().setScrollableY(true);
                    graphView.getViewport().setMinX(0.0);
                    graphView.getViewport().setMaxX(Integer.parseInt(crts_num));

                    timestamp = String.valueOf(mData.child("CRTS List").child(key).child("timestamp").getValue());
                    String taskDate = timestamp.substring(0, timestamp.indexOf(" "));
                    String taskTime = timestamp.substring(timestamp.indexOf(" ") + 1);
                    tasks.add(new TaskItem(String.valueOf(i + 1), taskDate, taskTime, crts_score, "/ 172"));
                    taskListViewAdapter.notifyDataSetChanged();

                    String crts_final_score = String.valueOf(mData.child("CRTS_Final_Score").getValue());
                    if (crts_final_score == "null") crts_final_score = crts_score;
                    clientName.setText(PatientName);
                    crtsCount.setText("총 " + crts_num + "번");
                    recentdate.setText("(" + crts_date + ")");
                    PieChartView pieChartView = (PieChartView) view.findViewById(R.id.crts_chart);
                    List<SliceValue> pieData = new ArrayList<>();
                    pieData.add(new SliceValue(172 - Integer.parseInt(crts_final_score), Color.parseColor("#E5E5E5")));
                    pieData.add(new SliceValue(Integer.parseInt(crts_final_score), Color.parseColor("#78B5AA")));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasCenterCircle(true).setCenterText1(crts_final_score);
                    pieChartData.setCenterText1Color(Color.parseColor("#78B5AA"));
                    pieChartData.setCenterText1FontSize(48);
                    pieChartView.setPieChartData(pieChartData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}