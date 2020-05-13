package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class CRTS_Result_Activity extends AppCompatActivity {

    Button crtsb_11_detail_button ;
    Button crtsb_12_detail_button ;
    Button crtsb_13_detail_button ;
    Button crtsb_14_detail_button ;
    ImageButton crtsc_result_button ;
    boolean crtsb_11_detail_bool = false ;
    boolean crtsb_12_detail_bool = false ;
    boolean crtsb_13_detail_bool = false ;
    boolean crtsb_14_detail_bool = false ;
    boolean crtsc_result_bool = false ;
    boolean crtsa_result_bool = false ;
    boolean crtsb_result_bool = false ;

    String Clinic_ID, PatientName, timestamp, downurl;
    double[] spiral_result, line_result, left_spiral_result;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_crts;
    DatabaseReference database_patient;

    String crts_score;
    String partA_score, partB_score, partC_score;
    String crts_num;
    String taskscore;
    String line_downurl;
    String crts_right_spiral_downurl;
    String crts_left_spiral_downurl;
    String writing_downurl;


    TextView c1_1score, c1_2score, c1_3score;
    TextView c2_1score, c2_2score, c2_3score;
    TextView c3_1score, c3_2score, c3_3score;
    TextView c4_1score, c4_2score, c4_3score;
    TextView c5_1score, c5_2score, c5_3score;
    TextView c6_1score, c6_2score, c6_3score;
    TextView c7_1score, c7_2score, c7_3score;
    TextView c8_1score, c8_2score, c8_3score;
    TextView c9_1score, c9_2score, c9_3score;
    TextView c10_1score, c10_2score, c10_3score;
    TextView c11score, c12score, c13score, c14score, c15_1score, c15_2score;
    TextView c16score, c17score, c18score, c19score, c20score, c21score, c22score, c23score;

    ImageView crtsb_11_image, crtsb_12_image, crtsb_13_image, crtsb_14_image;
    public RequestManager mGlideRequestManager;
    static int count = 0, left;

    Button pre_button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crts_result);

        Intent intent = getIntent();

        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        timestamp = intent.getExtras().getString("timestamp");
        crts_num = intent.getExtras().getString("crts_num");
        spiral_result = intent.getDoubleArrayExtra("spiral_result") ;
        line_result = intent.getDoubleArrayExtra("line_result");
        left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result");
        taskscore = intent.getStringExtra("taskscore") ;
        left = intent.getIntExtra("left", -1);
        line_downurl = intent.getStringExtra("line_downurl");
        crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl");
        crts_left_spiral_downurl = intent.getStringExtra("crts_left_spiral_downurl");
        writing_downurl = intent.getStringExtra("writing_downurl");
        database_patient = firebaseDatabase.getReference("PatientList");
        database_crts = database_patient.child(Clinic_ID).child("CRTS List");

        TextView c_t = (TextView) findViewById(R.id.clinic_ID);
        c_t.setText(Clinic_ID);

        TextView p_t = (TextView) findViewById(R.id.patientName);
        p_t.setText(PatientName);

        String timestamp1 = timestamp.substring(2,4) + "." + timestamp.substring(5,7) + "." + timestamp.substring(8,10);

        TextView t_t = (TextView) findViewById(R.id.today_date);
        t_t.setText(timestamp1);
        TextView home = (TextView) findViewById(R.id.gotohome);
        Button share = (Button) findViewById(R.id.result_share);

        crtsb_11_detail_button = (Button) findViewById(R.id.crtsb_11_detail_button) ;
        crtsb_12_detail_button = (Button) findViewById(R.id.crtsb_12_detail_button) ;
        crtsb_13_detail_button = (Button) findViewById(R.id.crtsb_13_detail_button) ;
        crtsb_14_detail_button = (Button) findViewById(R.id.crtsb_14_detail_button) ;

        final ConstraintLayout crtsa_result = (ConstraintLayout) findViewById(R.id.crtsa_result) ;
        final ImageButton crtsa_result_button = (ImageButton) findViewById(R.id. crtsa_result_button) ;

        final ImageButton crtsb_result_button = (ImageButton) findViewById(R.id. crtsb_result_button) ;
        final ConstraintLayout crtsb_11_layout = (ConstraintLayout) findViewById(R.id.crtsb_11_layout) ;
        final ConstraintLayout crtsb_11_detail = (ConstraintLayout) findViewById(R.id.crtsb_11_detail) ;
        final ConstraintLayout crtsb_12_layout = (ConstraintLayout) findViewById(R.id.crtsb_12_layout) ;
        final ConstraintLayout crtsb_12_detail = (ConstraintLayout) findViewById(R.id.crtsb_12_detail) ;
        final ConstraintLayout crtsb_13_layout = (ConstraintLayout) findViewById(R.id.crtsb_13_layout) ;
        final ConstraintLayout crtsb_13_detail = (ConstraintLayout) findViewById(R.id.crtsb_13_detail) ;
        final ConstraintLayout crtsb_14_layout = (ConstraintLayout) findViewById(R.id.crtsb_14_layout) ;
        final ConstraintLayout crtsb_14_detail = (ConstraintLayout) findViewById(R.id.crtsb_14_detail) ;
        final ConstraintLayout crtsb_15_layout = (ConstraintLayout) findViewById(R.id.crtsb_15) ;

        final ImageButton crtsc_result_button = (ImageButton) findViewById(R.id. crtsc_result_button) ;
        final ConstraintLayout crtsc_result = (ConstraintLayout) findViewById(R.id.crtsc_result) ;

        crtsb_11_image = (ImageView) findViewById(R.id.crtsb_11_image);
        crtsb_12_image = (ImageView) findViewById(R.id.crtsb_12_image);
        crtsb_13_image = (ImageView) findViewById(R.id.crtsb_13_image);
        crtsb_14_image = (ImageView) findViewById(R.id.crtsb_14_image);

        final int is_true = 1;
        final int is_false = 0;


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "CRTS");
                startActivity(intent);
            }
        });


        if(crtsa_result_bool ==true) {
            crtsa_result.setVisibility(View.VISIBLE);

        }
        else {
            crtsa_result.setVisibility(View.GONE);
        }
        final TextView right_spiral_hz = (TextView) findViewById(R.id.right_spiral_hz);
        final TextView right_spiral_magnitude = (TextView) findViewById(R.id.right_spiral_magnitude);
        final TextView right_spiral_distance = (TextView) findViewById(R.id.right_spiral_distance);
        final TextView right_spiral_time = (TextView) findViewById(R.id.right_spiral_time);
        final TextView right_spiral_speed = (TextView) findViewById(R.id.right_spiral_speed);

        final TextView left_spiral_hz = (TextView) findViewById(R.id.left_spiral_hz);
        final TextView left_spiral_magnitude = (TextView) findViewById(R.id.left_spiral_magnitude);
        final TextView left_spiral_distance = (TextView) findViewById(R.id.left_spiral_distance);
        final TextView left_spiral_time = (TextView) findViewById(R.id.left_spiral_time);
        final TextView left_spiral_speed = (TextView) findViewById(R.id.left_spiral_speed);

        final TextView line_hz = (TextView) findViewById(R.id.line_hz);
        final TextView line_magnitude = (TextView) findViewById(R.id.line_magnitude);
        final TextView line_distance = (TextView) findViewById(R.id.line_distance);
        final TextView line_time = (TextView) findViewById(R.id.line_time);
        final TextView line_speed = (TextView) findViewById(R.id.line_speed);

        c1_1score = (TextView)findViewById(R.id.c1_1score);
        c1_2score = (TextView)findViewById(R.id.c1_2score);
        c1_3score = (TextView)findViewById(R.id.c1_3score);
        c2_1score = (TextView)findViewById(R.id.c2_1score);
        c2_2score = (TextView)findViewById(R.id.c2_2score);
        c2_3score = (TextView)findViewById(R.id.c2_3score);
        c3_1score = (TextView)findViewById(R.id.c3_1score);
        c3_2score = (TextView)findViewById(R.id.c3_2score);
        c3_3score = (TextView)findViewById(R.id.c3_3score);
        c4_1score = (TextView)findViewById(R.id.c4_1score);
        c4_2score = (TextView)findViewById(R.id.c4_2score);
        c4_3score = (TextView)findViewById(R.id.c4_3score);
        c5_1score = (TextView)findViewById(R.id.c5_1score);
        c5_2score = (TextView)findViewById(R.id.c5_2score);
        c5_3score = (TextView)findViewById(R.id.c5_3score);
        c6_1score = (TextView)findViewById(R.id.c6_1score);
        c6_2score = (TextView)findViewById(R.id.c6_2score);
        c6_3score = (TextView)findViewById(R.id.c6_3score);
        c7_1score = (TextView)findViewById(R.id.c7_1score);
        c7_2score = (TextView)findViewById(R.id.c7_2score);
        c7_3score = (TextView)findViewById(R.id.c7_3score);
        c8_1score = (TextView)findViewById(R.id.c8_1score);
        c8_2score = (TextView)findViewById(R.id.c8_2score);
        c8_3score = (TextView)findViewById(R.id.c8_3score);
        c9_1score = (TextView)findViewById(R.id.c9_1score);
        c9_2score = (TextView)findViewById(R.id.c9_2score);
        c9_3score = (TextView)findViewById(R.id.c9_3score);
        c10_1score = (TextView)findViewById(R.id.c10_1score);
        c10_2score = (TextView)findViewById(R.id.c10_2score);
        c10_3score = (TextView)findViewById(R.id.c10_3score);
        c11score = (TextView)findViewById(R.id.c11score);
        c12score = (TextView)findViewById(R.id.c12score);
        c13score = (TextView)findViewById(R.id.c13score);
        c14score = (TextView)findViewById(R.id.c14score);
        c15_1score = (TextView)findViewById(R.id.c15_1score);
        c15_2score = (TextView)findViewById(R.id.c15_2score);
        c16score = (TextView)findViewById(R.id.c16score);
        c17score = (TextView)findViewById(R.id.c17score);
        c18score = (TextView)findViewById(R.id.c18score);
        c19score = (TextView)findViewById(R.id.c19score);
        c20score = (TextView)findViewById(R.id.c20score);
        c21score = (TextView)findViewById(R.id.c21score);
        c22score = (TextView)findViewById(R.id.c22score);
        c23score = (TextView)findViewById(R.id.c23score);

        crtsb_11_detail.setVisibility(View.GONE);
        crtsb_11_layout.setVisibility(View.GONE);
        crtsb_12_detail.setVisibility(View.GONE);
        crtsb_12_layout.setVisibility(View.GONE);
        crtsb_13_detail.setVisibility(View.GONE);
        crtsb_13_layout.setVisibility(View.GONE);
        crtsb_14_detail.setVisibility(View.GONE);
        crtsb_14_layout.setVisibility(View.GONE);
        crtsb_15_layout.setVisibility(View.GONE);
        crtsc_result.setVisibility(View.GONE);

        crtsa_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsa_result_bool ==false) {
                    crtsa_result_bool =true ;
                    crtsa_result_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.crtsa_result_button));
                    crtsa_result.setVisibility(View.VISIBLE);
                }
                else{
                    crtsa_result_bool =false;
                    crtsa_result_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.crtsa_result_button_no));
                    crtsa_result.setVisibility(View.GONE);
                }
            }
        });
        crtsb_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsb_result_bool ==false) {
                    crtsb_result_bool =true ;
                    crtsb_result_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.crtsb_result_button));
                    crtsb_11_layout.setVisibility(View.VISIBLE);
                    crtsb_12_layout.setVisibility(View.VISIBLE);
                    crtsb_13_layout.setVisibility(View.VISIBLE);
                    crtsb_14_layout.setVisibility(View.VISIBLE);
                    crtsb_15_layout.setVisibility(View.VISIBLE);

                }
                else{
                    crtsb_result_bool =false;
                    crtsb_result_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.crtsb_result_button_no));
                    crtsb_11_layout.setVisibility(View.GONE);
                    crtsb_12_layout.setVisibility(View.GONE);
                    crtsb_13_layout.setVisibility(View.GONE);
                    crtsb_14_layout.setVisibility(View.GONE);
                    crtsb_15_layout.setVisibility(View.GONE);
                }
            }
        });
        //detail button group
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        //add image to detail_image_view
        mGlideRequestManager = Glide.with(CRTS_Result_Activity.this);
        //Writing
        crtsb_11_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsb_11_detail_bool ==false) {
                    crtsb_11_detail_bool =true ;
                    crtsb_11_detail.setVisibility(View.VISIBLE);


                }
                else{
                    crtsb_11_detail_bool =false;
                    crtsb_11_detail.setVisibility(View.GONE);
                }
            }
        });
        //Spiral Right Side
        crtsb_12_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsb_12_detail_bool ==false) {
                    crtsb_12_detail_bool =true ;
                    crtsb_12_detail.setVisibility(View.VISIBLE);


                }
                else{
                    crtsb_12_detail_bool =false;
                    crtsb_12_detail.setVisibility(View.GONE);
                }
            }
        });
        //Spiral Left Side
        crtsb_13_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsb_13_detail_bool ==false) {
                    crtsb_13_detail_bool =true ;
                    crtsb_13_detail.setVisibility(View.VISIBLE);

                }
                else{
                    crtsb_13_detail_bool =false;
                    crtsb_13_detail.setVisibility(View.GONE);
                }
            }
        });
        //Line
        crtsb_14_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsb_14_detail_bool ==false) {
                    crtsb_14_detail_bool =true ;
                    crtsb_14_detail.setVisibility(View.VISIBLE);

                }
                else{
                    crtsb_14_detail_bool =false;
                    crtsb_14_detail.setVisibility(View.GONE);
                }
            }
        });
        crtsc_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crtsc_result_bool ==false) {
                    crtsc_result_bool =true ;
                    crtsc_result.setVisibility(View.VISIBLE);
                    crtsc_result_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.crtsc_result_button));
                }
                else{
                    crtsc_result_bool =false;
                    crtsc_result.setVisibility(View.GONE);
                    crtsc_result_button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.crtsc_result_button_no));
                }
            }
        });

        database_crts.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()){
                    String c1_1 = mData.child("CRTS task").child("얼굴_안정시").getValue() + "점";
                    c1_1score.setText(c1_1);
                    String c1_2 = mData.child("CRTS task").child("얼굴_자세시").getValue() + "점";
                    c1_2score.setText(c1_2);
                    String c1_3 = mData.child("CRTS task").child("얼굴_운동시").getValue() + "점";
                    c1_3score.setText(c1_3);
                    String c2_1 = mData.child("CRTS task").child("혀_안정시").getValue() + "점";
                    c2_1score.setText(c2_1);
                    String c2_2 = mData.child("CRTS task").child("혀_자세시").getValue() + "점";
                    c2_2score.setText(c2_2);
                    String c2_3 = mData.child("CRTS task").child("혀_운동시").getValue() + "점";
                    c2_3score.setText(c2_3);
                    String c3_1 = mData.child("CRTS task").child("목소리_안정시").getValue() + "점";
                    c3_1score.setText(c3_1);
                    String c3_2 = mData.child("CRTS task").child("목소리_자세시").getValue() + "점";
                    c3_2score.setText(c3_2);
                    String c3_3 = mData.child("CRTS task").child("목소리_운동시").getValue() + "점";
                    c3_3score.setText(c3_3);
                    String c4_1 = mData.child("CRTS task").child("머리_안정시").getValue() + "점";
                    c4_1score.setText(c4_1);
                    String c4_2 = mData.child("CRTS task").child("머리_자세시").getValue() + "점";
                    c4_2score.setText(c4_2);
                    String c4_3 = mData.child("CRTS task").child("머리_운동시").getValue() + "점";
                    c4_3score.setText(c4_3);
                    String c5_1 = mData.child("CRTS task").child("우측상지_안정시").getValue() + "점";
                    c5_1score.setText(c5_1);
                    String c5_2 = mData.child("CRTS task").child("우측상지_자세시").getValue() + "점";
                    c5_2score.setText(c5_2);
                    String c5_3 = mData.child("CRTS task").child("우측상지_운동시").getValue() + "점";
                    c5_3score.setText(c5_3);
                    String c6_1 = mData.child("CRTS task").child("좌측상지_안정시").getValue() + "점";
                    c6_1score.setText(c6_1);
                    String c6_2 = mData.child("CRTS task").child("좌측상지_자세시").getValue() + "점";
                    c6_2score.setText(c6_2);
                    String c6_3 = mData.child("CRTS task").child("좌측상지_운동시").getValue() + "점";
                    c6_3score.setText(c6_3);
                    String c7_1 = mData.child("CRTS task").child("체간_안정시").getValue() + "점";
                    c7_1score.setText(c7_1);
                    String c7_2 = mData.child("CRTS task").child("체간_자세시").getValue() + "점";
                    c7_2score.setText(c7_2);
                    String c7_3 = mData.child("CRTS task").child("체간_운동시").getValue() + "점";
                    c7_3score.setText(c7_3);
                    String c8_1 = mData.child("CRTS task").child("우측하지_안정시").getValue() + "점";
                    c8_1score.setText(c8_1);
                    String c8_2 = mData.child("CRTS task").child("우측하지_자세시").getValue() + "점";
                    c8_2score.setText(c8_2);
                    String c8_3 = mData.child("CRTS task").child("우측하지_운동시").getValue() + "점";
                    c8_3score.setText(c8_3);
                    String c9_1 = mData.child("CRTS task").child("좌측하지_안정시").getValue() + "점";
                    c9_1score.setText(c9_1);
                    String c9_2 = mData.child("CRTS task").child("좌측하지_자세시").getValue() + "점";
                    c9_2score.setText(c9_2);
                    String c9_3 = mData.child("CRTS task").child("좌측하지_운동시").getValue() + "점";
                    c9_3score.setText(c9_3);
                    String c10_1 = mData.child("CRTS task").child("기립성_안정시").getValue() + "점";
                    c10_1score.setText(c10_1);
                    String c10_2 = mData.child("CRTS task").child("기립성_자세시").getValue() + "점";
                    c10_2score.setText(c10_2);
                    String c10_3 = mData.child("CRTS task").child("기립성_운동시").getValue() + "점";
                    c10_3score.setText(c10_3);

                    String c11 = mData.child("CRTS task").child("글씨_쓰기").getValue() + "점";
                    c11score.setText(c11);
                    String c12 = mData.child("CRTS task").child("그림그리기1").getValue() + "점";
                    c12score.setText(c12);
                    String c13 = mData.child("CRTS task").child("그림그리기2").getValue() + "점" ;
                    c13score.setText(c13);
                    String c14 = mData.child("CRTS task").child("그림그리기3").getValue() + "점" ;
                    c14score.setText(c14);
                    String c15_1 = mData.child("CRTS task").child("물따르기").getValue() + "점";
                    c15_1score.setText(c15_1);
                    String c16 = mData.child("CRTS task").child("말하기").getValue() + "점";
                    c16score.setText(c16);
                    String c17 = mData.child("CRTS task").child("음식먹기").getValue() + "점";
                    c17score.setText(c17);
                    String c18 = mData.child("CRTS task").child("물을_입에_갖다대기").getValue() + "점";
                    c18score.setText(c18);
                    String c19 = mData.child("CRTS task").child("개인위생").getValue() + "점";
                    c19score.setText(c19);
                    String c20 = mData.child("CRTS task").child("옷입기").getValue() + "점";
                    c20score.setText(c20);
                    String c21 = mData.child("CRTS task").child("글쓰기").getValue() + "점";
                    c21score.setText(c21);
                    String c22 = mData.child("CRTS task").child("일하기").getValue() + "점";
                    c22score.setText(c22);
                    String c23 = mData.child("CRTS task").child("사회활동").getValue() + "점";
                    c23score.setText(c23);

                    //결과값 text 채워넣는 부분
                    //TM[0], TF[1], Time[2], ED[3], Velocity[4]
                    right_spiral_hz.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Right_Spiral_Result").child("hz").getValue()))));
                    right_spiral_magnitude.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Right_Spiral_Result").child("magnitude").getValue())))) ;
                    right_spiral_distance.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Right_Spiral_Result").child("distance").getValue())))) ;
                    right_spiral_speed.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Right_Spiral_Result").child("speed").getValue())))) ;
                    right_spiral_time.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Right_Spiral_Result").child("time").getValue()))));

                    left_spiral_hz.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Left_Spiral_Result").child("hz").getValue())))) ;
                    left_spiral_magnitude.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Left_Spiral_Result").child("magnitude").getValue()))));
                    left_spiral_distance.setText(String.format("%.1f", Double.parseDouble(String.valueOf( mData.child("Left_Spiral_Result").child("distance").getValue()))));
                    left_spiral_speed.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Left_Spiral_Result").child("speed").getValue()))));
                    left_spiral_time.setText(String.format("%.1f", Double.parseDouble(String.valueOf( mData.child("Left_Spiral_Result").child("time").getValue()))));

                    line_hz.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Line_Result").child("hz").getValue())))) ;
                    line_magnitude.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Line_Result").child("magnitude").getValue()))));
                    line_distance.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Line_Result").child("distance").getValue()))));
                    line_speed.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Line_Result").child("speed").getValue()))));
                    line_time.setText(String.format("%.1f", Double.parseDouble(String.valueOf(mData.child("Line_Result").child("time").getValue()))));

                    //downurl
                    writing_downurl = String.valueOf(mData.child("Writing_URL").getValue());
                    crts_right_spiral_downurl = String.valueOf(mData.child("Right_Spiral_URL").getValue());
                    crts_left_spiral_downurl = String.valueOf(mData.child("Left_Spiral_URL").getValue());
                    line_downurl = String.valueOf(mData.child("Line_URL").getValue());
                    //put the image(writing)
                    crtsb_11_image.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .asBitmap()
                                    .load(writing_downurl)
                                    .placeholder(R.drawable.image_loading)
                                    .apply(new RequestOptions().centerCrop().timeout(40000))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Matrix matrix = new Matrix();
                                            matrix.postRotate(90);
                                            int width = resource.getWidth();
                                            int height = resource.getHeight();

                                            resource = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
                                            crtsb_11_image.setImageBitmap(resource);
                                        }
                                    });
                        }
                    });

                    //put the image(right side)
                    crtsb_12_image.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .asBitmap()
                                    .load(crts_right_spiral_downurl)
                                    .placeholder(R.drawable.image_loading)
                                    .apply(new RequestOptions().centerCrop().timeout(40000))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Matrix matrix = new Matrix();
                                            matrix.postRotate(90);
                                            int width = resource.getWidth();
                                            int height = resource.getHeight();

                                            resource = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
                                            crtsb_12_image.setImageBitmap(resource);
                                        }
                                    });
                        }
                    });

                    //put the image(left spiral)
                    crtsb_13_image.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .asBitmap()
                                    .load(crts_left_spiral_downurl)
                                    .placeholder(R.drawable.image_loading)
                                    .apply(new RequestOptions().centerCrop().timeout(40000))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Matrix matrix = new Matrix();
                                            matrix.postRotate(90);
                                            int width = resource.getWidth();
                                            int height = resource.getHeight();

                                            resource = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
                                            crtsb_13_image.setImageBitmap(resource);
                                        }
                                    });
                        }
                    });

                    //put the image(line)
                    crtsb_14_image.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .asBitmap()
                                    .load(line_downurl)
                                    .placeholder(R.drawable.image_loading)
                                    .apply(new RequestOptions().centerCrop().timeout(40000))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Matrix matrix = new Matrix();
                                            matrix.postRotate(90);
                                            int width = resource.getWidth();
                                            int height = resource.getHeight();

                                            resource = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
                                            crtsb_14_image.setImageBitmap(resource);
                                        }
                                    });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GraphView graphView = (GraphView)findViewById(R.id.crts_result_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0,0), true, 100);
                for (DataSnapshot mData : dataSnapshot.getChildren()){
                    Long number = mData.child("CRTS List").getChildrenCount() ;
                    //Toast.makeText(view.getContext(), number+"", Toast.LENGTH_SHORT).show();
                    for(int i = 0 ; i<number; i++) {
                        list(i, mData, graphView, series, number) ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series, final long crts_num) {
        Query query = database_patient.child(Clinic_ID).child("CRTS List").orderByChild("CRTS_count").equalTo(i) ;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey() ;
                    partA_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partA_score").getValue()) ;
                    partB_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partB_score").getValue()) ;
                    partC_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partC_score").getValue()) ;
                    Log.v("CRTSResultActivity", "partA"+partA_score+"partB"+partB_score+"partC"+partC_score);
//                    while(partA_score.equals("null")&&partB_score.equals("null")&&partC_score.equals("null")){
//                        Log.v("CRTSResultActivitylllllllll", "partA"+partA_score+"partB"+partB_score+"partC"+partC_score + " Key " + key);
//                        partA_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partA_score").getValue()) ;
//                        partB_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partB_score").getValue()) ;
//                        partC_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partC_score").getValue()) ;
//                    }
                    if(partA_score.equals("null")){
                        series.appendData(new DataPoint(i+1, Integer.parseInt(taskscore)), true, 172);
                        //series.setDrawDataPoints(true);
                        series.setColor(Color.parseColor("#78B5AA"));
                        graphView.removeAllSeries();
                        graphView.addSeries(series);
                        graphView.getViewport().setScalableY(true);
                        graphView.getViewport().setScrollableY(true);
                        graphView.getViewport().setMinX(0.0);
                        graphView.getViewport().setMaxX(crts_num);
                    }
                    else{
                        crts_score = String.valueOf(Integer.parseInt(partA_score) + Integer.parseInt(partB_score) + Integer.parseInt(partC_score));//오류
                        series.appendData(new DataPoint(i+1, Integer.parseInt(crts_score)), true, 172);
                        //series.setDrawDataPoints(true);
                        series.setColor(Color.parseColor("#78B5AA"));
                        graphView.removeAllSeries();
                        graphView.addSeries(series);
                        graphView.getViewport().setScalableY(true);
                        graphView.getViewport().setScrollableY(true);
                        graphView.getViewport().setMinX(0.0);
                        graphView.getViewport().setMaxX(crts_num);
                    }
                    //crts_score = String.valueOf(Integer.parseInt(partA_score) + Integer.parseInt(partB_score) + Integer.parseInt(partC_score));//오류


                    PieChartView pieChartView = (PieChartView)findViewById(R.id.crts_result_chart);
                    List<SliceValue> pieData = new ArrayList<>();
                    pieData.add(new SliceValue(172-Integer.parseInt(taskscore), Color.parseColor("#E5E5E5")));
                    pieData.add(new SliceValue(Integer.parseInt(taskscore), Color.parseColor("#78B5AA")));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasCenterCircle(true).setCenterText1(taskscore);
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

