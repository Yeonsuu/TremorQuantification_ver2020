package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Spiral_Task_Select extends AppCompatActivity {

    private String Clinic_ID;
    private String PatientName;
    private String path;
    private String task;
    private int right_count ;
    private int left_count ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral__task__select);
        Intent intent2 = getIntent();

        Clinic_ID = intent2.getStringExtra("Clinic_ID");
        PatientName = intent2.getStringExtra("PatientName");
        path = intent2.getStringExtra("path");
        task = intent2.getStringExtra("task");
        right_count = intent2.getIntExtra("right_count", -1) ;
        left_count = intent2.getIntExtra("left_count", -1) ;
        Button right_select = (Button) findViewById(R.id.right_select);
        Button left_select = (Button) findViewById(R.id.left_select);
        Button back = (Button) findViewById(R.id.backButton);
        ConstraintLayout back2 = (ConstraintLayout) findViewById(R.id.constraintLayout4);

        right_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (task.equalsIgnoreCase("line"))
                    intent = new Intent(Spiral_Task_Select.this, Line.class);
                else intent = new Intent(Spiral_Task_Select.this, Spiral.class);
                intent.putExtra("crts_num", "null");
                intent.putExtra("right_spiral", "null");
                intent.putExtra("spiral_result", new double[]{0.0});
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("left_spiral_result", 0.0);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", path);
                intent.putExtra("left_count", -1) ;
                intent.putExtra("right_count", right_count) ;
                intent.putExtra("left", 0);
                intent.putExtra("lorr", true);
                Context context = getApplicationContext();
                LayoutInflater inflater = getLayoutInflater();
                View customToast = inflater.inflate(R.layout.toast_custom, null);
                Toast customtoast = new Toast(context);
                customtoast.setView(customToast);
                customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                customtoast.setDuration(Toast.LENGTH_LONG);
                customtoast.show();
                startActivity(intent);
                finish();
            }
        });

        left_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (task.equalsIgnoreCase("line"))
                    intent = new Intent(Spiral_Task_Select.this, Line.class);
                else intent = new Intent(Spiral_Task_Select.this, Spiral.class);
                intent.putExtra("crts_num", "null");
                intent.putExtra("right_spiral", "null");
                intent.putExtra("spiral_result", new double[]{0.0});
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("left_spiral_result", 0.0);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", path);
                intent.putExtra("left_count", left_count) ;
                intent.putExtra("right_count", -1) ;
                intent.putExtra("left", 1);
                intent.putExtra("lorr", false);
                Context context = getApplicationContext();
                LayoutInflater inflater = getLayoutInflater();
                View customToast = inflater.inflate(R.layout.toast_custom, null);
                Toast customtoast = new Toast(context);
                customtoast.setView(customToast);
                customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                customtoast.setDuration(Toast.LENGTH_LONG);
                customtoast.show();
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Spiral_Task_Select.this, PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "SPIRAL TASK");
                startActivity(intent);
                finish();
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Spiral_Task_Select.this, PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "SPIRAL TASK");
                startActivity(intent);
                finish();
            }
        });
    }
}
