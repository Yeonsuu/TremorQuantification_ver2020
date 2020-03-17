package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.ContentsPagerAdapter;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.math3.util.DoubleArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class LineTask_Fragment extends Fragment {

    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_line;
    DatabaseReference database_patient;
    int line_box_num = 1;
    int crts_box_num = 1;
    DoubleArray spiral_result;
    String crts_num = "";

    LineLeftFragment slf;
    LineRightFragment srf;

    Line_Rectangle_Fragment frag1;
    Line_List_Fragment frag2;

    Line_Both_Rectangle_Fragment bothhand_frag;
    LineRight_Fragment Right_frag;
    LineLeft_Fragment Left_frag;


    String Clinic_ID;
    String PatientName;
    String path;
    View view;
    File file;
    String m;
    String handside = "Right";
    String timestamp;
    String hz_score, magnitude_score, distance_score, time_score, velocity_score;
    int list_int;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    TaskListViewAdapter taskListViewAdapter;
    TaskListViewAdapter taskListViewAdapter2;
    ArrayList<TaskItem> tasks = new ArrayList<TaskItem>();
    ArrayList<TaskItem> selected_tasks = new ArrayList<>();

    FragmentManager fm;
    FragmentTransaction tran;

    TextView clientName;
    TextView lineCount;

    TextView righthand;
    TextView lefthand;
    TextView bothhand;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ContentsPagerAdapter mContentPagerAdapter;
    private FragmentActivity myContext;
    private String listtype = "list";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            path = getArguments().getString("path");
        }


        // 초기 화면
        view = inflater.inflate(R.layout.non_task_fragment, container, false);
        Button add_task = (Button) view.findViewById(R.id.add_task);

        add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Spiral_Task_Select.class);
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", "main");
                intent.putExtra("task", "line");
                startActivity(intent);
            }
        });

        // 환자 별 Spiral_task 개수 file 저장
        file = new File(view.getContext().getFilesDir(), Clinic_ID + "LINE_task_num.txt");
        //writeToFile("0", view.getContext());


        //환자 별 Spiral_task 개수 database에서 받아오기
        database_patient = firebaseDatabase.getReference("PatientList");
        database_patient.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    boolean data_exists = dataSnapshot.child(Clinic_ID).child("Line List").exists();
                    if(data_exists==false) writeToFile("0", view.getContext());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        database_line = database_patient.child(Clinic_ID).child("Line List");
        database_line.addValueEventListener(new ValueEventListener() {
            int temp_count = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    temp_count = (int) dataSnapshot.getChildrenCount();
                    writeToFile(String.valueOf(temp_count), view.getContext());
                    //writeToFile(String.valueOf("0"), view.getContext());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // 환자별 Spiral_task의 개수가 1개 이상이면 view 바꾸기
        if (file.exists()) {
            list_int = 1;


            m = readFromFile(view.getContext());
            if (Integer.parseInt(m) > 0) {
                view = inflater.inflate(R.layout.task_line_fragment, container, false);

                clientName = (TextView) view.findViewById(R.id.client_name);
                lineCount = (TextView) view.findViewById(R.id.client_line_count);
                clientName.setText(PatientName);
                lineCount.setText("총 " + m + "번");

                Button add_line = (Button) view.findViewById(R.id.line_add);

                // Spiral task 추가
                add_line.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view.getContext(), Spiral_Task_Select.class);
                        intent.putExtra("Clinic_ID", Clinic_ID);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("path", "main");
                        intent.putExtra("task", "line");
                        startActivity(intent);
                    }
                });

                bothhand_frag = new Line_Both_Rectangle_Fragment();
                Right_frag = new LineRight_Fragment();
                Left_frag = new LineLeft_Fragment();

                righthand = (TextView) view.findViewById(R.id.right_hand_line);
                lefthand = (TextView) view.findViewById(R.id.left_hand_line);
                bothhand = (TextView) view.findViewById(R.id.both_hand_line);
//
////                final CheckBox line_box = (CheckBox) view.findViewById(R.id.check_line);
////                final CheckBox crts_box = (CheckBox) view.findViewById(R.id.check_crts_line);
////                final Button list = (Button) view.findViewById(R.id.list_line);
////                final Button rectangle_list = (Button) view.findViewById(R.id.rectangle_list_line);
//
                database_line.addValueEventListener(new ValueEventListener() {
                    int temp_count = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            int left_count = (int) dataSnapshot.child("Left").getChildrenCount();
                            int right_count = (int) dataSnapshot.child("Right").getChildrenCount();
                            int total_count = left_count + right_count;
                            lineCount.setText("총 " + total_count + "번");

                            righthand.setText("오른손(" + right_count + ")");
                            lefthand.setText("왼손(" + left_count + ")");

                            //writeToFile(String.valueOf("0"), view.getContext());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//
//                Log.v("handside!!", handside);
//
////                setfrag(0);
                setFrag(0);
//
////                if (line_box.isChecked()) {
////                    line_box_num = 1;
////                } else {
////                    line_box_num = 0;
////                }
////
////                if (crts_box.isChecked()) {
////                    crts_box_num = 1;
////                } else {
////                    crts_box_num = 0;
////                }
//
//
//                /*
//                database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        int count = 1;
//                        GraphView graphView = (GraphView) view.findViewById(R.id.spiral_graph);
//                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//                        series.appendData(new DataPoint(0, 0), true, 100);
//                        //taskListViewAdapter2.clear();
//                        for (DataSnapshot mData : dataSnapshot.getChildren()) {
//                            Long number = mData.child("Spiral List").getChildrenCount();
//                            for (int i = 0; i < number; i++) {
//                                //list(i, mData, graphView, series);
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                */
//
//
////                list.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        listtype = "list";
////                        list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.list_button_c));
////                        rectangle_list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.draw_button_nc));
////                        setFrag(0);
////                    }
////                });
////
////                rectangle_list.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        listtype = "grid";
////                        list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.list_button_nc));
////                        rectangle_list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.draw_button_c));
////                        setFrag(1);
////                    }
////                });
//
                righthand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handside = "Right";
//                        if (listtype.equals("list")) {
//                            frag2 = new Line_List_Fragment();
//                            setFrag(0);
//                        } else {
//                            frag1 = new Line_Rectangle_Fragment();
//                            setFrag(1);
//                        }
//                        srf = new LineRightFragment();
                        Right_frag = new LineRight_Fragment() ;
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
//                        if (listtype.equals("list")) {
//                            frag2 = new Line_List_Fragment();
//                            setFrag(0);
//                        } else {
//                            frag1 = new Line_Rectangle_Fragment();
//                            setFrag(1);
//                        }
//                        slf = new LineLeftFragment();
                        lefthand.setBackgroundColor(Color.WHITE);
                        Left_frag = new LineLeft_Fragment() ;
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
                        handside = "both";
                        bothhand_frag = new Line_Both_Rectangle_Fragment();
                        bothhand.setBackgroundColor(Color.WHITE);
                        bothhand.setTextColor(Color.rgb(84, 84, 84));
                        righthand.setBackgroundColor(Color.rgb(209, 209, 209));
                        righthand.setTextColor(Color.WHITE);
                        lefthand.setBackgroundColor(Color.rgb(209, 209, 209));
                        lefthand.setTextColor(Color.WHITE);
                        setFrag(1);
                    }
                });


            }

        }

        return view;

    }


    // write File
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "LINE_task_num.txt", Context.MODE_PRIVATE));
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
            InputStream inputStream = context.openFileInput(Clinic_ID + "LINE_task_num.txt");
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


    public void setFrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("path", path);
                bundle1.putString("handside", "Right");
                Right_frag.setArguments(bundle1);
                tran.replace(R.id.handside_line, Right_frag);
                tran.commit();
                break;

            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("path", path);
                bundle2.putString("handside", handside);
                bothhand_frag.setArguments(bundle2);
                tran.replace(R.id.handside_line, bothhand_frag);
                tran.commit();
                break;

            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("path", path);
                bundle3.putString("handside", "Left");
                Left_frag.setArguments(bundle3);
                tran.replace(R.id.handside_line, Left_frag);
                tran.commit();
                break;

        }
    }

    public void setfrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("handside", handside);
                srf.setArguments(bundle1);
                tran.replace(R.id.line_graph, srf);
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("handside", handside);
                slf.setArguments(bundle2);
                tran.replace(R.id.line_graph, slf);
                tran.commit();
                break;

        }
    }


}
