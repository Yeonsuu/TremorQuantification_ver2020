package com.ahnbcilab.tremorquantification.tremorquantification;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class Writing_Fragment extends Fragment {

    String Clinic_ID;
    String PatientName;

    View view;
    File file;

    int list_int;
    String m;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_writing;
    DatabaseReference database_patient;

    TextView clientName;
    TextView writingCount;

    Writing_Rectangle_Fragment frag1;
    Writing_List_Fragment frag2;
    FragmentManager fm;
    FragmentTransaction tran;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        if (getArguments() != null) {
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            //path = getArguments().getString("path");
        }


        database_patient = firebaseDatabase.getReference("PatientList");
        database_writing = database_patient.child(Clinic_ID).child("Writing List");

        view = inflater.inflate(R.layout.task_writing_fragment, container, false);

        clientName = (TextView) view.findViewById(R.id.client_name);
        writingCount = (TextView) view.findViewById(R.id.client_writing_count);
        clientName.setText(PatientName);

        database_writing.addValueEventListener(new ValueEventListener() {
            int temp_count = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    temp_count = (int) dataSnapshot.getChildrenCount();
                    writingCount.setText("총 " + String.valueOf(temp_count) + "번");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        frag1 = new Writing_Rectangle_Fragment();
        frag2 = new Writing_List_Fragment();

        final Button list = (Button) view.findViewById(R.id.list);
        final Button rectangle_list = (Button) view.findViewById(R.id.rectangle_list);
        setFrag(0);


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.list_button_c));
                rectangle_list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.draw_button_nc));
                setFrag(0);
            }
        });


        rectangle_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.list_button_nc));
                rectangle_list.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.draw_button_c));
                setFrag(1);
            }
        });

        return view;

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "WRITING_task_num.txt", Context.MODE_PRIVATE));
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
            InputStream inputStream = context.openFileInput(Clinic_ID + "WRITING_task_num.txt");
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
                frag2.setArguments(bundle1);
                tran.replace(R.id.personal_writing_taskList, frag2);
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                frag1.setArguments(bundle2);
                tran.replace(R.id.personal_writing_taskList, frag1);
                tran.commit();
                break;

        }
    }


}