package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.Spiral;
import com.ahnbcilab.tremorquantification.data.SpiralData;
import com.ahnbcilab.tremorquantification.data.SurveyData;
import com.ahnbcilab.tremorquantification.functions.NoSSLv3Factory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class SpiralResultActivity extends AppCompatActivity {
    int res;
    int leftSpiralCount = 0;
    int rightSpiralCount = 0;
    int crts_count;
    int flag = 0;
    static int count = 0;

    int finalleftSpiralCount;
    int finalrightSpiralCount;
    String sspiral_count;
    String finalkey = null ;
    String downurl = null;
    String downurl_pre = null;
    int total_spiral_count;
    final Double[] hz_temp = new Double[2];

    String Clinic_ID;
    String PatientName;
    String handside ;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference firebase_spiral_url = firebaseDatabase.getReference("URL List");
    DatabaseReference databasepatient;
    DatabaseReference databaseclinicID;

    SpiralData spiraldata ;

    private TabLayout mTabLayout;
    private FragmentActivity myContext;

    FragmentManager fm;
    FragmentTransaction tran;

    TotalFragment frag1 ;
    HZFragment frag2 ;
    MagnitudeFragment frag3 ;
    DistanceFragment frag4 ;
    TimeFragment frag5 ;
    SpeedFragment frag6 ;

    boolean spiral_result_layout_bool = false;
    private int both_count;

    public RequestManager mGlideRequestManager;
    public RequestManager mGlideRequestManager_pre;

    static {
        HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral__result);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        //TODO: Spiral downurl 받기
        final double[] spiral_result = intent.getDoubleArrayExtra("spiral_result");
        String path1 = intent.getStringExtra("path1");
        PatientName = intent.getStringExtra("PatientName");
        Clinic_ID = intent.getStringExtra("Clinic_ID");
        final int left = intent.getIntExtra("left", -1);
        Date d = new Date();
        path1 = "Spiral_Test";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        final String timestamp = sdf.format(d);
        String[] today = timestamp.split(" ");
        final String finalPath = path1;

        TextView clinic_ID = (TextView) findViewById(R.id.clinic_ID);
        TextView patientName = (TextView) findViewById(R.id.patientName);
        TextView today_date = (TextView) findViewById(R.id.today_date);
        TextView testTitle = (TextView) findViewById(R.id.testTitle) ;
        TextView result_date = (TextView) findViewById(R.id.result_date) ;
        final TextView pre_result_date = (TextView) findViewById(R.id.pre_result_date) ;
        Button again_test = (Button) findViewById(R.id.again_test) ;

        final TextView pre_hz_score = (TextView)findViewById(R.id.pre_hz_score) ;
        final TextView hz_score = (TextView)findViewById(R.id.hz_score) ;
        final TextView pre_magnitude_score = (TextView)findViewById(R.id.pre_magnitude_score) ;
        final TextView magnitude_score = (TextView)findViewById(R.id.magnitude_score) ;
        final TextView pre_distance_score = (TextView)findViewById(R.id.pre_distance_score) ;
        final TextView distance_score = (TextView)findViewById(R.id.distance_score) ;
        final TextView pre_time_score = (TextView)findViewById(R.id.pre_time_score) ;
        final TextView time_score = (TextView)findViewById(R.id.time_score) ;
        final TextView pre_speed_score = (TextView)findViewById(R.id.pre_speed_score) ;
        final TextView speed_score = (TextView)findViewById(R.id.speed_score) ;
        final TextView hz_improve = (TextView)findViewById(R.id.hz_improve) ;
        final TextView magnitude_improve = (TextView)findViewById(R.id.magnitude_improve) ;
        final TextView distance_improve = (TextView)findViewById(R.id.distance_improve) ;
        final TextView time_improve = (TextView)findViewById(R.id.time_improve) ;
        final TextView speed_improve = (TextView)findViewById(R.id.speed_improve) ;
        final TextView total_improve = (TextView)findViewById(R.id.total_improve) ;
        clinic_ID.setText(Clinic_ID);
        patientName.setText(PatientName);
        today_date.setText(timestamp.substring(0,16));
        result_date.setText("("+today[0].substring(2)+")") ;


        databasepatient = firebaseDatabase.getReference("PatientList");
        databaseclinicID = databasepatient.child(Clinic_ID).child("Spiral List");
        //의사 ID 얻어오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        if(spiral_result[4] >= 100)
            spiral_result[4] = 10;
        //[0]: TM , [1]: TF , [2]:time , [3]: ED , [4]:velocity
        hz_score.setText(String.format("%.2f",spiral_result[1]));
        magnitude_score.setText(String.format("%.2f",spiral_result[0]));
        time_score.setText(String.format("%.2f",spiral_result[2]));
        distance_score.setText(String.format("%.2f",spiral_result[3]));
        speed_score.setText(String.format("%.2f",spiral_result[4]));

        if (left == 0) {
            handside = "Right";
            firebase_spiral_url = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Spiral").child("Right");
            final int[] left_total_spiral_count = new int[1];
            testTitle.setText("Spiral Result 오른손");
            databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot mData : dataSnapshot.getChildren()) {
                        total_spiral_count = (int) mData.child("Spiral List").child("Right").getChildrenCount();
                        left_total_spiral_count[0] = (int) mData.child("Spiral List").child("Left").getChildrenCount();
                        if((int) mData.child("Spiral List").child("Right").getChildrenCount()==0){
                            both_count = 1 ;
                        }
                        else {
                            if((int) mData.child("Spiral List").child("Right").getChildrenCount()%2 == 0) {
                                both_count = ((int) mData.child("Spiral List").child("Right").getChildrenCount()*2) +1 ;
                            }
                            else {
                                both_count = ((int) mData.child("Spiral List").child("Right").getChildrenCount() *2)+1;
                            }
                        }
                    }
                    databaseclinicID.child("Right").child(finalkey).child("Right_total_count").setValue(total_spiral_count);
                    databaseclinicID.child("Right").child(finalkey).child("Both_total_count").setValue(both_count);
                    databaseclinicID.child("Right").child(finalkey).child("total_count").setValue(total_spiral_count+ left_total_spiral_count[0]);
                    databaseclinicID.child(handside).orderByChild(handside+"_total_count").equalTo(total_spiral_count-1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                pre_hz_score.setText((String.valueOf(mData.child("Spiral_Result").child("hz").getValue())));
                                pre_magnitude_score.setText((String.valueOf(mData.child("Spiral_Result").child("magnitude").getValue())));
                                pre_time_score.setText((String.valueOf(mData.child("Spiral_Result").child("time").getValue())));
                                pre_distance_score.setText((String.valueOf(mData.child("Spiral_Result").child("distance").getValue())));
                                pre_speed_score.setText((String.valueOf(mData.child("Spiral_Result").child("speed").getValue())));
                                pre_result_date.setText("("+mData.child("timestamp").getValue().toString().substring(2,10)+")");



                                hz_temp[0] = Double.valueOf(spiraldata.getHz());
                                hz_temp[1] = Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("hz").getValue()));
                                double hz_improvement = 0;

                                if(hz_temp[0]==-1||hz_temp[1]==-1){
                                    flag=1;
                                    hz_improve.setText("비교 측정불가");
                                    hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

                                } else {
                                    hz_improvement = Math.round((Double.valueOf(spiraldata.getHz()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("hz").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("hz").getValue()))*100);
                                    if(hz_improvement >= 0 && hz_improvement< 100){
                                        hz_improve.setText("+" + String.valueOf(hz_improvement)+"%");
                                        Log.d("test1223","돌고는 있는데?1");
                                        hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    }else if (hz_improvement >= 100) {
                                        flag=1;
                                        hz_improve.setText("비교 측정불가");
                                        hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    }
                                    else{
                                        hz_improve.setText(String.valueOf(hz_improvement)+"%");
                                        hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                    }
                                }


                                double magnitude_improvement = Math.round((Double.valueOf(spiraldata.getMagnitude()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("magnitude").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("magnitude").getValue()))*100);
                                if(magnitude_improvement >= 0 && magnitude_improvement< 100){
                                    magnitude_improve.setText("+" + String.valueOf(magnitude_improvement)+"%");
                                    magnitude_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }else if (magnitude_improvement >= 100) {
                                    magnitude_improve.setText("비교 측정불가");
                                    flag=1;
                                    magnitude_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    magnitude_improve.setText(String.valueOf(magnitude_improvement)+"%");
                                    magnitude_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }

                                double distance_improvement = Math.round((Double.valueOf(spiraldata.getDistance()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("distance").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("distance").getValue()))*100);
                                if(distance_improvement >= 0&& distance_improvement< 100){
                                    distance_improve.setText("+" + String.valueOf(distance_improvement)+"%");
                                    distance_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }else if (distance_improvement >= 100) {
                                    distance_improve.setText("비교 측정불가");
                                    flag=1;
                                    distance_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    distance_improve.setText(String.valueOf(distance_improvement)+"%");
                                    distance_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }

                                double time_improvement = Math.round((Double.valueOf(spiraldata.getTime()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("time").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("time").getValue()))*100);
                                if(time_improvement >= 0 && time_improvement< 100){
                                    time_improve.setText("+" + String.valueOf(time_improvement)+"%");
                                    time_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }else if (time_improvement >= 100) {
                                    time_improve.setText("비교 측정불가");
                                    flag=1;
                                    time_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    time_improve.setText(String.valueOf(time_improvement)+"%");
                                    time_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }

                                double speed_improvement = Math.round((Double.valueOf(spiraldata.getSpeed()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("speed").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("speed").getValue()))*100);
                                if(speed_improvement >= 0&& speed_improvement< 100){
                                    speed_improve.setText(String.valueOf((-1)*speed_improvement)+"%");
                                    speed_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }else if (speed_improvement >= 100) {
                                    speed_improve.setText("비교 측정불가");
                                    flag=1;
                                    speed_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    speed_improve.setText("+" + String.valueOf((-1)*speed_improvement)+"%");
                                    speed_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }

                                //double total_improvement = Math.round((Double.valueOf(spiraldata.getTotal()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("total").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("total").getValue()))*100*100)/100.0;
                                double total_improvement = Math.round((hz_improvement+magnitude_improvement+distance_improvement+time_improvement+speed_improvement)/5);
                                if(flag==1){
                                    total_improve.setText("비교 측정불가");
                                    total_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    flag=0;
                                }else {
                                    if (total_improvement >= 0) {
                                        total_improve.setText("+" + String.valueOf(total_improvement) + "%");
                                        total_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    } else {
                                        total_improve.setText(String.valueOf(total_improvement) + "%");
                                        total_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            handside = "Left";
            firebase_spiral_url = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Spiral").child("Left");
            final int[] right_total_spiral_count = new int[1];
            testTitle.setText("Spiral Result 왼손");
            databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot mData : dataSnapshot.getChildren()) {
                        total_spiral_count = (int) mData.child("Spiral List").child("Left").getChildrenCount();
                        right_total_spiral_count[0] = (int) mData.child("Spiral List").child("Right").getChildrenCount();
                        if((int) mData.child("Spiral List").child("Left").getChildrenCount()==0){
                            both_count = 0 ;
                        }
                        else {
                            if((int) mData.child("Spiral List").child("Left").getChildrenCount()%2 == 0) {
                                both_count = (int) mData.child("Spiral List").child("Left").getChildrenCount()*2 ;
                            }
                            else {
                                both_count = (int) mData.child("Spiral List").child("Left").getChildrenCount()*2 ;
                            }
                        }
                    }
                    databaseclinicID.child("Left").child(finalkey).child("Left_total_count").setValue(total_spiral_count);
                    databaseclinicID.child("Left").child(finalkey).child("Both_total_count").setValue(both_count);
                    databaseclinicID.child("Left").child(finalkey).child("total_count").setValue(total_spiral_count+ right_total_spiral_count[0]) ;
                    databaseclinicID.child(handside).orderByChild(handside+"_total_count").equalTo(total_spiral_count-1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot mData : dataSnapshot.getChildren()) {
                                pre_hz_score.setText((String.valueOf(mData.child("Spiral_Result").child("hz").getValue())));
                                pre_magnitude_score.setText((String.valueOf(mData.child("Spiral_Result").child("magnitude").getValue())));
                                pre_time_score.setText((String.valueOf(mData.child("Spiral_Result").child("time").getValue())));
                                pre_distance_score.setText((String.valueOf(mData.child("Spiral_Result").child("distance").getValue())));
                                pre_speed_score.setText((String.valueOf(mData.child("Spiral_Result").child("speed").getValue())));
                                pre_result_date.setText("("+mData.child("timestamp").getValue().toString().substring(2,10)+")");

                                hz_temp[0] = Double.valueOf(spiraldata.getHz());
                                hz_temp[1] = Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("hz").getValue()));

                                double hz_improvement = 0;
                                if(hz_temp[0]==-1||hz_temp[1]==-1){

                                    hz_improve.setText("비교 측정불가");
                                    flag=1;
                                    hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

                                } else {
                                    hz_improvement = Math.round((Double.valueOf(spiraldata.getHz()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("hz").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("hz").getValue()))*100);
                                    if(hz_improvement >= 0 && hz_improvement< 100){
                                        hz_improve.setText("+" + String.valueOf(hz_improvement)+"%");
                                        Log.d("test1223","돌고는 있는데?1");
                                        hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    }else if (hz_improvement >= 100) {
                                        hz_improve.setText("비교 측정불가");
                                        flag=1;
                                        hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    }
                                    else{
                                        hz_improve.setText(String.valueOf(hz_improvement)+"%");
                                        hz_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                    }
                                }

                                double magnitude_improvement = Math.round((Double.valueOf(spiraldata.getMagnitude()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("magnitude").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("magnitude").getValue()))*100);
                                if(magnitude_improvement >= 0 && magnitude_improvement< 100){
                                    magnitude_improve.setText("+" + String.valueOf(magnitude_improvement)+"%");
                                    magnitude_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }else if (magnitude_improvement >= 100) {
                                    magnitude_improve.setText("비교 측정불가");
                                    flag=1;
                                    magnitude_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    magnitude_improve.setText(String.valueOf(magnitude_improvement)+"%");
                                    magnitude_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }

                                double distance_improvement = Math.round((Double.valueOf(spiraldata.getDistance()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("distance").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("distance").getValue()))*100);
                                if(distance_improvement >= 0&& distance_improvement< 100){
                                    distance_improve.setText("+" + String.valueOf(distance_improvement)+"%");
                                    distance_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }else if (distance_improvement >= 100) {
                                    distance_improve.setText("비교 측정불가");
                                    flag=1;
                                    distance_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    distance_improve.setText(String.valueOf(distance_improvement)+"%");
                                    distance_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }

                                double time_improvement = Math.round((Double.valueOf(spiraldata.getTime()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("time").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("time").getValue()))*100);
                                if(time_improvement >= 0 && time_improvement< 100){
                                    time_improve.setText("+" + String.valueOf(time_improvement)+"%");
                                    time_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }else if (time_improvement >= 100) {
                                    time_improve.setText("비교 측정불가");
                                    flag=1;
                                    time_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    time_improve.setText(String.valueOf(time_improvement)+"%");
                                    time_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }

                                double speed_improvement = Math.round((Double.valueOf(spiraldata.getSpeed()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("speed").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("speed").getValue()))*100);
                                if(speed_improvement >= 0&& speed_improvement< 100){
                                    speed_improve.setText(String.valueOf((-1)*speed_improvement)+"%");
                                    speed_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                }else if (speed_improvement >= 100) {
                                    speed_improve.setText("비교 측정불가");
                                    flag=1;
                                    speed_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }
                                else{
                                    speed_improve.setText("+" + String.valueOf((-1)*speed_improvement)+"%");
                                    speed_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                }

                                //double total_improvement = Math.round((Double.valueOf(spiraldata.getTotal()) - Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("total").getValue()))) / Double.valueOf(String.valueOf(mData.child("Spiral_Result").child("total").getValue()))*100*100)/100.0;
                                double total_improvement = Math.round((hz_improvement+magnitude_improvement+distance_improvement+time_improvement+speed_improvement)/5);
                                if(flag==1){
                                    total_improve.setText("비교 측정불가");
                                    total_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    flag=0;
                                }else {
                                    if (total_improvement >= 0) {
                                        total_improve.setText("+" + String.valueOf(total_improvement) + "%");
                                        total_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    } else {
                                        total_improve.setText(String.valueOf(total_improvement) + "%");
                                        total_improve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        mGlideRequestManager = Glide.with(SpiralResultActivity.this);
        mGlideRequestManager_pre = Glide.with(SpiralResultActivity.this);
        //TODO: intent로 가져온 sprial url과 DB쿼리로 가져온 이전 나선이미지도 띄워줌
        //URL 리스트에서 child의 수가 몇 개 있는 지 확인한다
        final ImageView present_spiral = findViewById(R.id.present_spiral);
        firebase_spiral_url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    count = (int)dataSnapshot.getChildrenCount();
                    /* ******************************** download image from firebase *************************************/
                    int count_now = count -1;
                    firebase_spiral_url.child(String.valueOf(count_now)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            downurl = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                            present_spiral.post(new Runnable() {
                                @Override
                                public void run() {
                                    mGlideRequestManager.load(downurl)
                                            .apply(new RequestOptions().centerCrop().placeholder(R.drawable.image_loading_rotate).error(R.drawable.image_null_rotate).timeout(40000))
                                            .into(present_spiral);
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    /* ******************************** end of download image from firebase *************************************/
                    //이전 이미지가 존재할 경우 이전 이미지도 띄워줘야함
                    if(count > 1)
                    {
                        final ImageView pre_spiral = findViewById(R.id.pre_spiral);
                        int pre_count = count -2;
                        Log.v("이전 나선 count", ""+pre_count);
                        firebase_spiral_url.child(String.valueOf(pre_count)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                downurl_pre = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                                pre_spiral.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGlideRequestManager_pre.load(downurl_pre)
                                                .apply(new RequestOptions().centerCrop().placeholder(R.drawable.image_loading_rotate).error(R.drawable.image_null_rotate).timeout(30000))
                                                .into(pre_spiral);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                else
                {
                    present_spiral.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .load(R.drawable.image_err_rotate)//인터넷 너무 느릴 시 실행
                                    .into(present_spiral);

                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query left_query = databaseclinicID.child("Left").orderByChild("path").equalTo("Spiral_Test");
        Query right_query = databaseclinicID.child("Right").orderByChild("path").equalTo("Spiral_Test");
        left_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    String key = fileSnapshot.getKey();
                    leftSpiralCount++;
                    Log.v("Spiral", "SpiralResult : " + key + leftSpiralCount+"Left");
                }
                finalleftSpiralCount = leftSpiralCount;
                Log.v("Spiral", "SpiralResult : " + leftSpiralCount + " " + finalleftSpiralCount+"Left");
                if(left == 0){

                }
                else {
                    databaseclinicID.child("Left").child(finalkey).child("Left_SPIRAL_count").setValue(finalleftSpiralCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        right_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    String key = fileSnapshot.getKey();
                    rightSpiralCount++;
                    Log.v("Spiral", "SpiralResult : " + key + rightSpiralCount+"Right");
                }
                finalrightSpiralCount = rightSpiralCount;
                Log.v("Spiral", "SpiralResult : " + rightSpiralCount + " " + finalrightSpiralCount+"Right");
                if(left == 0){
                    databaseclinicID.child("Right").child(finalkey).child("SPIRAL_count").setValue(finalrightSpiralCount+finalleftSpiralCount);
                    databaseclinicID.child("Right").child(finalkey).child("Right_SPIRAL_count").setValue(finalrightSpiralCount);
                }
                else {
                    databaseclinicID.child("Left").child(finalkey).child("SPIRAL_count").setValue(finalrightSpiralCount+finalleftSpiralCount);
                    //
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (left == 0) {
            Log.v("SpiralResult", "LeftSpiralResult " + leftSpiralCount + " crtsCount " + crts_count);
            Log.v("SpiralResult", "RightSpiralResult " + rightSpiralCount + " crtsCount " + crts_count);
            Spiral spiral = new Spiral(timestamp, finalrightSpiralCount + finalleftSpiralCount);
            spiraldata = new SpiralData(1.0, spiral_result[1], spiral_result[0], spiral_result[3], spiral_result[2], spiral_result[4]);
            finalkey = databaseclinicID.child("Right").push().getKey().toString();
            databaseclinicID.child("Right").child(finalkey).setValue(spiral);
            databaseclinicID.child("Right").child(finalkey).child("path").setValue(finalPath);
            databaseclinicID.child("Right").child(finalkey).child("Spiral_Result").setValue(spiraldata);
            databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                        Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                        Long right_spiral_count = childDataSnapshot.child("Spiral List").child("Right").getChildrenCount();
                        Long left_spiral_count = childDataSnapshot.child("Spiral List").child("Left").getChildrenCount();
                        Long line_count = childDataSnapshot.child("Line List").getChildrenCount();
                        int taskNo = (int) (crts_count + updrs_count + line_count + right_spiral_count + left_spiral_count);
                        databasepatient.child(Clinic_ID).child("TaskNo").setValue(taskNo);

                        if (taskNo == 1) {
                            String FirstDate = String.valueOf(childDataSnapshot.child("Spiral List").child("Right").child(finalkey).child("timestamp").getValue());
                            int idx = FirstDate.indexOf(" ");
                            String firstDate1 = FirstDate.substring(0, idx);
                            databasepatient.child(Clinic_ID).child("FirstDate").setValue(firstDate1);
                            databasepatient.child(Clinic_ID).child("FinalDate").setValue(firstDate1);
                        } else {
                            String FinalDate = String.valueOf(childDataSnapshot.child("Spiral List").child("Right").child(finalkey).child("timestamp").getValue());
                            int idx = FinalDate.indexOf(" ");
                            String finalDate1 = FinalDate.substring(0, idx);
                            databasepatient.child(Clinic_ID).child("FinalDate").setValue(finalDate1);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (left == 1) {


            Log.v("SpiralResult", "LeftSpiralResult " + leftSpiralCount + " crtsCount " + crts_count);
            Log.v("SpiralResult", "RightSpiralResult " + rightSpiralCount + " crtsCount " + crts_count);
            Spiral spiral = new Spiral(timestamp, finalrightSpiralCount + finalleftSpiralCount);
            spiraldata = new SpiralData(1.0, spiral_result[1], spiral_result[0], spiral_result[3], spiral_result[2], spiral_result[4]);
            finalkey = databaseclinicID.child("Left").push().getKey().toString();
            databaseclinicID.child("Left").child(finalkey).setValue(spiral);
            databaseclinicID.child("Left").child(finalkey).child("path").setValue(finalPath);
            databaseclinicID.child("Left").child(finalkey).child("Spiral_Result").setValue(spiraldata);
            databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                        Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                        Long right_spiral_count = childDataSnapshot.child("Spiral List").child("Right").getChildrenCount();
                        Long left_spiral_count = childDataSnapshot.child("Spiral List").child("Left").getChildrenCount();
                        Long line_count = childDataSnapshot.child("Line List").getChildrenCount();
                        int taskNo = (int) (crts_count + updrs_count + line_count + right_spiral_count + left_spiral_count);
                        databasepatient.child(Clinic_ID).child("TaskNo").setValue(taskNo);

                        if (taskNo == 1) {
                            String FirstDate = String.valueOf(childDataSnapshot.child("Spiral List").child("Left").child(finalkey).child("timestamp").getValue());
                            int idx = FirstDate.indexOf(" ");
                            String firstDate1 = FirstDate.substring(0, idx);
                            databasepatient.child(Clinic_ID).child("FirstDate").setValue(firstDate1);
                            databasepatient.child(Clinic_ID).child("FinalDate").setValue(firstDate1);
                        } else {
                            String FinalDate = String.valueOf(childDataSnapshot.child("Spiral List").child("Left").child(finalkey).child("timestamp").getValue());
                            int idx = FinalDate.indexOf(" ");
                            String finalDate1 = FinalDate.substring(0, idx);
                            databasepatient.child(Clinic_ID).child("FinalDate").setValue(finalDate1);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        frag1 = new TotalFragment() ;
        frag2 = new HZFragment() ;
        frag3 = new MagnitudeFragment() ;
        frag4 = new DistanceFragment() ;
        frag5 = new TimeFragment() ;
        frag6 = new SpeedFragment() ;

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                setFrag(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setFrag(0);
        final ConstraintLayout spiral_result_layout = (ConstraintLayout) findViewById(R.id.spiral_result_layout);
        Button spiral_result_button = (Button) findViewById(R.id.spiral_result_button);

        spiral_result_layout.setVisibility(View.GONE);
        spiral_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spiral_result_layout_bool == false) {
                    spiral_result_layout.setVisibility(View.VISIBLE);
                    spiral_result_layout_bool = true;
                } else {
                    spiral_result_layout.setVisibility(View.GONE);
                    spiral_result_layout_bool = false;
                }
            }
        });

        again_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Spiral_Task_Select.class);
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", "main");
                intent.putExtra("task", "spiral") ;
                startActivity(intent);
                finish() ;
            }
        });
        TextView Fbtn = (TextView) findViewById(R.id.gotohome);
        Fbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SpiralResultActivity.this,
                        PersonalPatient.class);
                myIntent.putExtra("ClinicID", Clinic_ID);
                myIntent.putExtra("PatientName", PatientName);
                myIntent.putExtra("task", "SPIRAL TASK");
                startActivity(myIntent);
                finish();
            }
        });

    }
    public void setFrag(int i) {
        fm = getSupportFragmentManager() ;
        tran = fm.beginTransaction();
        switch (i){
            case 0 :
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("handside", handside);
                bundle1.putString("Type","Spiral List");
                frag1.setArguments(bundle1);
                tran.replace(R.id.spiral_graph, frag1);
                tran.commit();
                break;
            case 1 :
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("handside", handside);
                bundle2.putString("Type","Spiral List");
                frag2.setArguments(bundle2);
                tran.replace(R.id.spiral_graph, frag2);
                tran.commit();
                break;
            case 2 :
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("handside", handside);
                bundle3.putString("Type","Spiral List");
                frag3.setArguments(bundle3);
                tran.replace(R.id.spiral_graph, frag3);
                tran.commit();
                break;
            case 3 :
                Bundle bundle4 = new Bundle();
                bundle4.putString("Clinic_ID", Clinic_ID);
                bundle4.putString("PatientName", PatientName);
                bundle4.putString("handside", handside);
                bundle4.putString("Type","Spiral List");
                frag4.setArguments(bundle4);
                tran.replace(R.id.spiral_graph, frag4);
                tran.commit();
                break;
            case 4 :
                Bundle bundle5 = new Bundle();
                bundle5.putString("Clinic_ID", Clinic_ID);
                bundle5.putString("PatientName", PatientName);
                bundle5.putString("handside", handside);
                bundle5.putString("Type","Spiral List");
                frag5.setArguments(bundle5);
                tran.replace(R.id.spiral_graph, frag5);
                tran.commit();
                break;
            case 5 :
                Bundle bundle6 = new Bundle();
                bundle6.putString("Clinic_ID", Clinic_ID);
                bundle6.putString("PatientName", PatientName);
                bundle6.putString("handside", handside);
                bundle6.putString("Type","Spiral List");
                frag6.setArguments(bundle6);
                tran.replace(R.id.spiral_graph, frag6);
                tran.commit();
                break;

        }
    }

}