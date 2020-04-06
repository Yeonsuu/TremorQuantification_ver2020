package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
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

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class NonTaskFragment extends Fragment {
    RecyclerView recyclerView;
    TaskListViewAdapter taskListViewAdapter;
    ArrayList<TaskItem> tasks = new ArrayList<TaskItem>();
    ArrayList<TaskItem> selected_tasks = new ArrayList<>();
    AlertDialogHelper alertDialogHelper;

    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_updrs;
    DatabaseReference database_patient;

    ActionMode mActionMode;
    Menu context_menu;
    boolean isMultiSelect = false;

    String Clinic_ID;
    String PatientName;
    String taskType ;
    View view;
    File file;
    String m;
    String timestamp;
    String updrs_score;

    TextView recentdate;
    TextView clientName;
    TextView updrsCount;

    boolean data_exists ;
    String exits = "null";

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

        view = inflater.inflate(R.layout.non_task_fragment, container, false);
        Log.v("ppersonal", "pperssonal NonTaskFragment"+taskType);
        final Button add_task = (Button) view.findViewById(R.id.add_task);
        if(taskType.equals("Writing List")){
            add_task.setVisibility(View.GONE);
        }
        else{
            add_task.setVisibility(View.VISIBLE);
        }
        // UPDRS task 추가
        add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(taskType.equals("UPDRS List")){
                    Intent intent = new Intent(view.getContext(), UPDRSActivity.class);
                    intent.putExtra("Clinic_ID", Clinic_ID);
                    intent.putExtra("PatientName", PatientName);
                    intent.putExtra("updrs_num", m);
                    startActivity(intent);
                }
                else if(taskType.equals("CRTS List")){
                    Intent intent = new Intent(view.getContext(), CRTSActivity.class);
                    intent.putExtra("Clinic_ID", Clinic_ID);
                    intent.putExtra("PatientName", PatientName);
                    intent.putExtra("path", "main");
                    intent.putExtra("crts_num", m);
                    startActivity(intent);
                }
                else if(taskType.equals("Spiral List")){
                    Intent intent = new Intent(view.getContext(), Spiral_Task_Select.class);
                    intent.putExtra("Clinic_ID", Clinic_ID);
                    intent.putExtra("PatientName", PatientName);
                    intent.putExtra("path", "main");
                    intent.putExtra("task", "spiral");
                    startActivity(intent);
                }
                else if(taskType.equals("Line List")){
                    Intent intent = new Intent(view.getContext(), Spiral_Task_Select.class);
                    intent.putExtra("Clinic_ID", Clinic_ID);
                    intent.putExtra("PatientName", PatientName);
                    intent.putExtra("path", "main");
                    intent.putExtra("task", "line");
                    startActivity(intent);
                }


            }
        });
        return view;

    }


    // write File
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "UPDRS_task_num.txt", Context.MODE_PRIVATE));
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
            InputStream inputStream = context.openFileInput(Clinic_ID + "UPDRS_task_num.txt");
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


    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series, final String updrs_num) {
        Query query = database_patient.child(Clinic_ID).child("UPDRS List").orderByChild("UPDRS_count").equalTo(i);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    updrs_score = String.valueOf(mData.child("UPDRS List").child(key).child("UPDRS_score").getValue());
                    String updrs_date = String.valueOf(mData.child("UPDRS List").child(key).child("timestamp").getValue());
                    updrs_date = updrs_date.substring(2, 10);
                    series.appendData(new DataPoint(i + 1, Integer.parseInt(updrs_score)), true, 100);
                    //series.setDrawDataPoints(true);
                    series.setColor(Color.parseColor("#78B5AA"));
                    graphView.removeAllSeries();
                    graphView.addSeries(series);
                    graphView.getViewport().setScalableY(true);
                    graphView.getViewport().setScrollableY(true);
                    graphView.getViewport().setMinX(0);
                    graphView.getViewport().setMaxX(Integer.parseInt(updrs_num));

                    timestamp = String.valueOf(mData.child("UPDRS List").child(key).child("timestamp").getValue());
                    String taskDate = timestamp.substring(0, timestamp.indexOf(" "));
                    String taskTime = timestamp.substring(timestamp.indexOf(" ") + 1);
                    tasks.add(new TaskItem(String.valueOf(i + 1), taskDate, taskTime, updrs_score, "/ 108"));
                    taskListViewAdapter.notifyDataSetChanged();

                    String updrs_final_score = String.valueOf(mData.child("UPDRS_Final_Score").getValue());
                    if (updrs_final_score == "null") {
                        updrs_final_score = updrs_score;
                    }
                    clientName.setText(PatientName);
                    updrsCount.setText("총 " + updrs_num + "번");
                    recentdate.setText("(" + updrs_date + ")");
                    PieChartView pieChartView = (PieChartView) view.findViewById(R.id.updrs_chart);
                    List<SliceValue> pieData = new ArrayList<>();
                    pieData.add(new SliceValue(100 - Integer.parseInt(updrs_final_score), Color.parseColor("#E5E5E5")));
                    pieData.add(new SliceValue(Integer.parseInt(updrs_final_score), Color.parseColor("#78B5AA")));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasCenterCircle(true).setCenterText1(updrs_final_score);
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