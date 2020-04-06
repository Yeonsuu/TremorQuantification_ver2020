package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.Spiral;
import com.ahnbcilab.tremorquantification.data.SpiralData;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CRTS_SpiralResult extends AppCompatActivity {

    RadioButton crts12_1_0, crts12_1_1, crts12_1_2, crts12_1_3, crts12_1_4;
    int crts11,crts12, crts13, crts14;
    boolean bool = true;
    int check ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databasepatient;
    DatabaseReference databaseclinicID;
    private DatabaseReference firebase_spiral_url = firebaseDatabase.getReference("URL List");
    static int count = 0;
    String downurl = null;

    int res ;
    int leftSpiralCount=0;
    int rightSpiralCount=0 ;
    int crts_count ;
    private int finalrightSpiralCount;
    private int finalleftSpiralCount;

    int total_spiral_count;
    public RequestManager mGlideRequestManager;
    ImageView present_spiral;

    RadioGroup r_group_crts;
    Button next;
    RadioButton r_crt_arr[] = new RadioButton[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crts__spiral_result);

        Intent intent = getIntent();
        //TODO: spiral url 받아오기
        final double[] spiral_result = intent.getDoubleArrayExtra("spiral_result");
        final double[] left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result");
        final double[] line_result = intent.getDoubleArrayExtra("line_result");
        final String path = intent.getStringExtra("path1");
        final String edit = intent.getStringExtra("edit") ;
        final String line_downurl = intent.getStringExtra("line_downurl");
        final String crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl");
        final String crts_left_spiral_downurl = intent.getStringExtra("crts_left_spiral_downurl");
        final String writing_downurl = intent.getStringExtra("writing_downurl");
        final String PatientName = intent.getStringExtra("PatientName");
        final String Clinic_ID = intent.getStringExtra("Clinic_ID");
        final String crts_num = intent.getStringExtra("crts_num");
        final int left = intent.getIntExtra("left", -1) ;
        crts11 = intent.getIntExtra("crts11", -1);

        if(edit.equals("yes")) {
            crts11 = intent.getIntExtra("crts11", -1);
            crts12 = intent.getIntExtra("crts12", -1);
            crts13 = intent.getIntExtra("crts13", -1);
            crts14 = intent.getIntExtra("crts14", -1);

        }
        check= intent.getIntExtra("crts12", -1) ;
        //의사 ID 얻어오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        firebase_spiral_url = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Spiral").child("Right");

        //나선 이미지 불러오기
        mGlideRequestManager = Glide.with(CRTS_SpiralResult.this);
        present_spiral = findViewById(R.id.present_spiral);
        present_spiral.post(new Runnable() {
            @Override
            public void run() {
                mGlideRequestManager
                        .asBitmap()
                        .load(crts_right_spiral_downurl)
                        .apply(new RequestOptions().centerCrop().placeholder(R.drawable.image_loading).timeout(40000))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Matrix matrix = new Matrix();
                                matrix.postRotate(90);
                                int width = resource.getWidth();
                                int height = resource.getHeight();

                                resource = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
                                present_spiral.setImageBitmap(resource);
                            }
                        });
            }
        });

        final Button quit = (Button) findViewById(R.id.spiral_result_quit_button) ;

        TextView hz_score = (TextView) findViewById(R.id.hz_score);
        TextView magnitude_score = (TextView) findViewById(R.id.magnitude_score);
        TextView time_score = (TextView) findViewById(R.id.time_score);
        TextView speed_score = (TextView) findViewById(R.id.speed_score);
        TextView distance_score = (TextView) findViewById(R.id.distance_score);

        //TM[0], TF[1], Time[2], ED[3], Velocity[4]
        hz_score.setText(String.format("%.1f", spiral_result[1]));
        magnitude_score.setText(String.format("%.1f", spiral_result[0]));
        distance_score.setText(String.format("%.1f", spiral_result[3]));
        time_score.setText(String.format("%.1f", spiral_result[2]));
        speed_score.setText(String.format("%.1f", spiral_result[4]));

        Date d = new Date();
        final String path1 = "CRTS";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        final String timestamp = sdf.format(d);

        databasepatient = firebaseDatabase.getReference("PatientList");
        databaseclinicID = databasepatient.child(Clinic_ID).child("Spiral List").child("Right");

        databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot mData : dataSnapshot.getChildren()){
                    total_spiral_count = (int) mData.child("Spiral List").child("Right").getChildrenCount() ;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query left_query = databasepatient.child(Clinic_ID).child("Spiral List").child("Left").orderByChild("path").equalTo("CRTS") ;
        Query right_query = databasepatient.child(Clinic_ID).child("Spiral List").child("Right").orderByChild("path").equalTo("CRTS") ;
        left_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    String key = fileSnapshot.getKey() ;
                    leftSpiralCount++ ;
                    Log.v("CRTS", "SpiralResult : " + key + leftSpiralCount) ;
                }
                finalleftSpiralCount = leftSpiralCount ;
                Log.v("CRTS", "SpiralResult : " + leftSpiralCount + " " + finalleftSpiralCount) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        right_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    String key = fileSnapshot.getKey() ;
                    rightSpiralCount++ ;
                    Log.v("CRTS", "SpiralResult : " + key + rightSpiralCount) ;
                }
                finalrightSpiralCount = rightSpiralCount ;
                Log.v("CRTS", "SpiralResult : " + rightSpiralCount + " " + finalrightSpiralCount) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        crts12_1_0 = (RadioButton) findViewById(R.id.crts12_1_0);
        crts12_1_1 = (RadioButton) findViewById(R.id.crts12_1_1);
        crts12_1_2 = (RadioButton) findViewById(R.id.crts12_1_2);
        crts12_1_3 = (RadioButton) findViewById(R.id.crts12_1_3);
        crts12_1_4 = (RadioButton) findViewById(R.id.crts12_1_4);

        next = (Button) findViewById(R.id.spiral_next);

        if (!edit.equals("yes")) {
            next.setVisibility(View.GONE);
        }
        r_group_crts = (RadioGroup) findViewById(R.id.crg11_1);
        r_crt_arr[0] = crts12_1_0;
        r_crt_arr[1] = crts12_1_1;
        r_crt_arr[2] = crts12_1_2;
        r_crt_arr[3] = crts12_1_3;
        r_crt_arr[4] = crts12_1_4;

        for(int i = 0; i<r_crt_arr.length; i++)
        {
            final int index = i;
            r_crt_arr[index].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    next.setVisibility(View.VISIBLE);
                }
            });

        }

        Button pre_button = (Button) findViewById(R.id.writing_pre) ;
        if(path.equals("CRTS_detail")) {
            if (!edit.equals("yes")) {
                next.setVisibility(View.GONE);
            }
            quit.setText("돌아가기");
            if (check==0) {
                crts12_1_0.setChecked(true);
            } else if (check==1) {
                crts12_1_1.setChecked(true);
            } else if (check==2) {
                crts12_1_2.setChecked(true);
            } else if (check==3) {
                crts12_1_3.setChecked(true);
            } else if (check==4) {
                crts12_1_4.setChecked(true);
            }
        }
        else if(edit.equals("yes")){
            if(crts12==0) {
                crts12_1_0.setChecked(true);
            }
            else if(crts12==1) {
                crts12_1_1.setChecked(true);
            }
            else if(crts12==2) {
                crts12_1_2.setChecked(true);
            }
            else if(crts12==3) {
                crts12_1_3.setChecked(true);
            }
            else if(crts12==4) {
                crts12_1_4.setChecked(true);
            }
            pre_button.setVisibility(View.GONE);
        }
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quit.getText().toString().equals("돌아가기")){
                    onBackPressed() ;
                    finish();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.equals("yes")) {

                }
                else{
                    //TM[0], TF[1], Time[2], ED[3], Velocity[4]
                    Spiral spiral = new Spiral(timestamp,finalrightSpiralCount+finalleftSpiralCount);
                    SpiralData spiraldata = new SpiralData(1.0, spiral_result[1], spiral_result[0], spiral_result[3], spiral_result[2], spiral_result[4]);
                    final String key = databaseclinicID.push().getKey().toString() ;
                    databaseclinicID.child(key).setValue(spiral);
                    databaseclinicID.child(key).child("Right_SPIRAL_count").setValue(finalrightSpiralCount) ;
                    databaseclinicID.child(key).child("path").setValue(path1) ;
                    databaseclinicID.child(key).child("Spiral_Result").setValue(spiraldata);
                    databaseclinicID.child(key).child("Right_total_count").setValue(total_spiral_count);
                    databaseclinicID.child(key).child("URL").setValue(crts_right_spiral_downurl);

                    databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                                Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                                Long right_spiral_count = childDataSnapshot.child("Spiral List").child("Right").getChildrenCount();
                                Long left_spiral_count = childDataSnapshot.child("Spiral List").child("Left").getChildrenCount();
                                Long line_count = childDataSnapshot.child("Line List").getChildrenCount() ;
                                int taskNo = (int)(crts_count + updrs_count + line_count + right_spiral_count + left_spiral_count);
                                databasepatient.child(Clinic_ID).child("TaskNo").setValue(taskNo);

                                if(taskNo==1) {
                                    String FirstDate = String.valueOf(childDataSnapshot.child("Spiral List").child("Right").child(key).child("timestamp").getValue());
                                    int idx = FirstDate.indexOf(" ") ;
                                    String firstDate1 = FirstDate.substring(0, idx) ;
                                    databasepatient.child(Clinic_ID).child("FirstDate").setValue(firstDate1) ;
                                    databasepatient.child(Clinic_ID).child("FinalDate").setValue(firstDate1) ;
                                }
                                else{
                                    String FinalDate = String.valueOf(childDataSnapshot.child("Spiral List").child("Right").child(key).child("timestamp").getValue());
                                    int idx = FinalDate.indexOf(" ") ;
                                    String finalDate1 = FinalDate.substring(0, idx);
                                    databasepatient.child(Clinic_ID).child("FinalDate").setValue(finalDate1) ;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


                if(crts12_1_0.isChecked()){
                    crts12 = 0;
                }
                else if(crts12_1_1.isChecked()){
                    crts12 = 1;
                }
                else if(crts12_1_2.isChecked()){
                    crts12 = 2;
                }
                else if(crts12_1_3.isChecked()){
                    crts12 = 3;
                }
                else if(crts12_1_4.isChecked()){
                    crts12 = 4;
                }
                else{
                    bool = false;
                }
                if(bool){
                    //TODO : spiral_left downurl 보내기
                    if(edit.equals("yes")) {
                        Intent intent = new Intent(getApplicationContext(), CRTSActivity.class) ;
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("path", path);
                        intent.putExtra("path1", path);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("Clinic_ID", Clinic_ID);
                        intent.putExtra("crts_num", crts_num);
                        intent.putExtra("crts11", crts11);
                        intent.putExtra("crts12", crts12);
                        intent.putExtra("crts13", crts13);
                        intent.putExtra("crts14", crts14);
                        intent.putExtra("line_result", line_result) ;
                        intent.putExtra("line_downurl", line_downurl);
                        intent.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
                        intent.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
                        intent.putExtra("left_spiral_result", left_spiral_result) ;
                        intent.putExtra("spiral_result", spiral_result) ;
                        startActivity(intent) ;
                        finish() ;
                    }
                    else if(edit.equals("no")) {
                        Intent intent = new Intent(getApplicationContext(), CRTS_LeftSpiralResult.class);
                        intent.putExtra("spiral_result", spiral_result);
                        intent.putExtra("left_spiral_result", left_spiral_result);
                        intent.putExtra("line_result", line_result);
                        intent.putExtra("path", path);
                        intent.putExtra("path1", path);
                        intent.putExtra("edit", edit) ;
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("Clinic_ID", Clinic_ID);
                        intent.putExtra("crts_num", crts_num);
                        intent.putExtra("line_downurl", line_downurl);
                        intent.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl);
                        intent.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl);
                        intent.putExtra("writing_downurl", writing_downurl);
                        intent.putExtra("crts11", crts11);
                        intent.putExtra("crts12", crts12);
                        intent.putExtra("left", left) ;//left : 0, right : 1
                        startActivity(intent);
                    }
                }
                else{

                }

            }
        });

        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish() ;
            }
        });

    }
    @Override
    protected  void onDestroy() {
        Drawable d = present_spiral.getDrawable();
        if (d instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            bitmap.recycle();
            bitmap = null;
        }
        d.setCallback(null);

        super.onDestroy();
    }

}