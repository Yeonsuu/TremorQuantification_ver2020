package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
import com.ahnbcilab.tremorquantification.Adapters.ContentsPagerAdapter;
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

import org.hsqldb.rights.Right;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SpiralTask_Fragment extends Fragment {

    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_spiral;
    DatabaseReference database_patient;
    AlertDialogHelper alertDialogHelper;
    int spiral_box_num = 1;
    int crts_box_num = 1;

    ActionMode mActionMode;
    Menu context_menu;
    boolean isMultiSelect = false;

    SpiralLeftFragment slf;
    SpiralRightFragment srf;
    SpiralBothFragment sbf;

    Spiral_Rectangle_Fragment frag1;
    Spiral_List_Fragment frag2;

    Spiral_Both_Rectangle_Fragment bothhand_frag;
    SpiralRight_Fragment Right_frag;
    SpiralLeft_Fragment Left_frag;


    String listtype = "list";

    String Clinic_ID;
    String PatientName;
    String path;
    View view;
    File file;
    String m;
    int list_int;
    String timestamp;
    String hz_score, magnitude_score, distance_score, time_score, velocity_score;
    String handside = "Right";

    FragmentManager fm;
    FragmentTransaction tran;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    TaskListViewAdapter taskListViewAdapter;
    TaskListViewAdapter taskListViewAdapter2;
    ArrayList<TaskItem> tasks = new ArrayList<TaskItem>();
    ArrayList<TaskItem> selected_tasks = new ArrayList<>();

    TextView clientName;
    TextView spiralCount;


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ContentsPagerAdapter mContentPagerAdapter;
    private FragmentActivity myContext;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        // 이전 Activity value 받아오기
        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            path = getArguments().getString("path");
        }

        view = inflater.inflate(R.layout.task_spiral_fragment, container, false);
        database_patient = firebaseDatabase.getReference("PatientList");
        database_spiral = database_patient.child(Clinic_ID).child("Spiral List");
        clientName = (TextView) view.findViewById(R.id.client_name);
        spiralCount = (TextView) view.findViewById(R.id.client_spiral_count);
        clientName.setText(PatientName);


        Button add_spiral = (Button) view.findViewById(R.id.spiral_add);

        // Spiral task 추가
        add_spiral.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Spiral_Task_Select.class);
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", "main");
                intent.putExtra("task", "spiral");
                startActivity(intent);
            }
        });

        bothhand_frag = new Spiral_Both_Rectangle_Fragment();
        Right_frag = new SpiralRight_Fragment();
        Left_frag = new SpiralLeft_Fragment();
        final TextView righthand = (TextView) view.findViewById(R.id.right_hand);
        final TextView lefthand = (TextView) view.findViewById(R.id.left_hand);
        final TextView bothhand = (TextView) view.findViewById(R.id.both_hand);
        database_spiral.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    int left_count = (int) dataSnapshot.child("Left").getChildrenCount();
                    int right_count = (int) dataSnapshot.child("Right").getChildrenCount();
                    int total_count = left_count + right_count;
                    spiralCount.setText("총 " + total_count + "번");

                    righthand.setText("오른손(" + right_count + ")");
                    lefthand.setText("왼손(" + left_count + ")");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setFrag(0);
        righthand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handside = "Right";
                Log.v("SpiralTask", "SpiralTask : " + listtype);
                Right_frag = new SpiralRight_Fragment();
                righthand.setBackgroundColor(Color.WHITE);
                righthand.setTextColor(Color.rgb(84, 84, 84));
                lefthand.setBackgroundColor(Color.rgb(209, 209, 209));
                lefthand.setTextColor(Color.WHITE);
                bothhand.setBackgroundColor(Color.rgb(209, 209, 209));
                bothhand.setTextColor(Color.WHITE);
                setFrag(0);

            }
        });
        lefthand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handside = "Left";
                Log.v("SpiralTask", "SpiralTask : " + listtype);
                Left_frag = new SpiralLeft_Fragment();
                lefthand.setBackgroundColor(Color.WHITE);
                lefthand.setTextColor(Color.rgb(84, 84, 84));
                righthand.setBackgroundColor(Color.rgb(209, 209, 209));
                righthand.setTextColor(Color.WHITE);
                bothhand.setBackgroundColor(Color.rgb(209, 209, 209));
                bothhand.setTextColor(Color.WHITE);
                setFrag(2);

            }
        });
        bothhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handside = "Both";
                bothhand_frag = new Spiral_Both_Rectangle_Fragment();
                bothhand.setBackgroundColor(Color.WHITE);
                bothhand.setTextColor(Color.rgb(84, 84, 84));
                righthand.setBackgroundColor(Color.rgb(209, 209, 209));
                righthand.setTextColor(Color.WHITE);
                lefthand.setBackgroundColor(Color.rgb(209, 209, 209));
                lefthand.setTextColor(Color.WHITE);
                setFrag(1);
            }
        });



        return view;

    }

    // write File
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "SPIRAL_task_num.txt", Context.MODE_PRIVATE));
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
            InputStream inputStream = context.openFileInput(Clinic_ID + "SPIRAL_task_num.txt");
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

    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series) {
        Query query = database_patient.child(Clinic_ID).child("Spiral List").orderByChild("SPIRAL_count").equalTo(i);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    timestamp = String.valueOf(mData.child("Spiral List").child(key).child("timestamp").getValue());
                    String taskDate = timestamp.substring(0, timestamp.indexOf(" "));
                    String taskTime = timestamp.substring(timestamp.indexOf(" ") + 1);
                    tasks.add(new TaskItem(String.valueOf(i + 1), taskDate, taskTime, "23", "/ 100"));
                    taskListViewAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setFrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        Left_frag = new SpiralLeft_Fragment();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("path", path);
                bundle1.putString("handside", "Right");
                Right_frag.setArguments(bundle1);
                tran.replace(R.id.handside, Right_frag);
                tran.commit();
                break;

            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("path", path);
                bundle2.putString("handside", handside);
                bothhand_frag.setArguments(bundle2);
                tran.replace(R.id.handside, bothhand_frag);
                tran.commit();
                break;

            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("path", path);
                bundle3.putString("handside", "Left");
                Left_frag.setArguments(bundle3);
                tran.replace(R.id.handside, Left_frag);
                tran.commit();
                break;

        }
    }
//    public void setFrag(int n) {
//        fm = getFragmentManager();
//        tran = fm.beginTransaction();
//
//        switch (n) {
//            case 0:
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("Clinic_ID", Clinic_ID);
//                bundle1.putString("PatientName", PatientName);
//                bundle1.putString("handside", handside);
//                frag2.setArguments(bundle1);
//                tran.replace(R.id.personal_spiral_taskList, frag2);
//                tran.commit();
//                break;
//            case 1:
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("Clinic_ID", Clinic_ID);
//                bundle2.putString("PatientName", PatientName);
//                bundle2.putString("handside", handside);
//                frag1.setArguments(bundle2);
//                tran.replace(R.id.personal_spiral_taskList, frag1);
//                tran.commit();
//                break;
//
//
//        }
//    }

}
