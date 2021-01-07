package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Personal_line extends AppCompatActivity {

    String Clinic_ID;
    String PatientName;
    String taskDate;
    String taskTime;
    int taskNum;
    String handside;
    String downurl;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_line;
    DatabaseReference database_patient;

    android.support.v4.app.FragmentManager fm;
    android.support.v4.app.FragmentTransaction tran;

    TotalFragment frag1;
    HZFragment frag2;
    MagnitudeFragment frag3;
    DistanceFragment frag4;
    TimeFragment frag5;
    SpeedFragment frag6;

    private TabLayout mTabLayout;
    boolean line_result_layout_bool = false;

    public RequestManager mGlideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__spiral);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        taskNum = intent.getExtras().getInt("taskNum", -1);
        taskDate = intent.getExtras().getString("taskDate");
        taskTime = intent.getExtras().getString("taskTime");
        handside = intent.getExtras().getString("handside");

        String timestamp = taskDate + " " + taskTime;
        //String title = taskDate + "     " + taskTime.substring(0, 5);

        TextView t_s = (TextView) findViewById(R.id.today_date);
        TextView t_t = (TextView) findViewById(R.id.task_title);
        TextView c_id = (TextView) findViewById(R.id.clinic_ID);
        TextView p_name = (TextView) findViewById(R.id.patientName);
        final TextView hz_score = (TextView) findViewById(R.id.hz_score);
        final TextView m_score = (TextView) findViewById(R.id.magnitude_score);
        final TextView time_score = (TextView) findViewById(R.id.time_score);
        final TextView distance_score = (TextView) findViewById(R.id.distance_score);
        final TextView speed_score = (TextView) findViewById(R.id.speed_score);

        database_patient = firebaseDatabase.getReference("PatientList");
        database_line = database_patient.child(Clinic_ID).child("Line List").child(handside);

        //의사 ID 얻어오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        final ConstraintLayout line_result_layout = (ConstraintLayout) findViewById(R.id.spiral_result_layout);
        Button line_result_button = (Button) findViewById(R.id.spiral_result_button);
        if (handside.equalsIgnoreCase("right"))
        {
            t_t.setText("Line Test 오른손");

        }
        else
        {
            t_t.setText("Line Test 왼손");
        }



        t_s.setText(timestamp);
        c_id.setText(Clinic_ID);
        p_name.setText(PatientName);

        Button backButton = (Button) findViewById(R.id.gotohome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "LINE TASK");
                startActivity(intent);
            }
        });
        TextView backButton2 = (TextView) findViewById(R.id.gotohome2);
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "LINE TASK");
                startActivity(intent);
            }
        });
        database_line.orderByChild(handside+"_total_count").equalTo(taskNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot mData : dataSnapshot.getChildren()) {
                    hz_score.setText(String.valueOf(mData.child("Line_Result").child("hz").getValue())) ;
                    m_score.setText(String.valueOf(mData.child("Line_Result").child("magnitude").getValue())) ;
                    time_score.setText(String.valueOf(mData.child("Line_Result").child("time").getValue())) ;
                    speed_score.setText(String.valueOf(mData.child("Line_Result").child("speed").getValue())) ;
                    distance_score.setText(String.valueOf(mData.child("Line_Result").child("distance").getValue())) ;
                    downurl = String.valueOf(mData.child("URL").getValue());
                    mGlideRequestManager = Glide.with(Personal_line.this);
                    final ImageView present_line = findViewById(R.id.present_spiral);
                    present_line.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager.load(downurl)
                                    .apply(new RequestOptions().centerCrop().placeholder(R.drawable.image_loading_rotate).error(R.drawable.image_null_rotate).timeout(40000))
                                    .into(present_line);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
        line_result_layout.setVisibility(View.GONE);
        line_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (line_result_layout_bool == false) {
                    line_result_layout.setVisibility(View.VISIBLE);
                    line_result_layout_bool = true;
                } else {
                    line_result_layout.setVisibility(View.GONE);
                    line_result_layout_bool = false;
                }
            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        frag1 = new TotalFragment();
        frag2 = new HZFragment();
        frag3 = new MagnitudeFragment();
        frag4 = new DistanceFragment();
        frag5 = new TimeFragment();
        frag6 = new SpeedFragment();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
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

    }

    public void setFrag(int i) {
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (i) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("handside", handside);
                bundle1.putString("Type","Line List");
                frag1.setArguments(bundle1);
                tran.replace(R.id.spiral_graph, frag1);
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("handside", handside);
                bundle2.putString("Type","Line List");
                frag2.setArguments(bundle2);
                tran.replace(R.id.spiral_graph, frag2);
                tran.commit();
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("handside", handside);
                bundle3.putString("Type","Line List");
                frag3.setArguments(bundle3);
                tran.replace(R.id.spiral_graph, frag3);
                tran.commit();
                break;
            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("Clinic_ID", Clinic_ID);
                bundle4.putString("PatientName", PatientName);
                bundle4.putString("handside", handside);
                bundle4.putString("Type","Line List");
                frag4.setArguments(bundle4);
                tran.replace(R.id.spiral_graph, frag4);
                tran.commit();
                break;
            case 4:
                Bundle bundle5 = new Bundle();
                bundle5.putString("Clinic_ID", Clinic_ID);
                bundle5.putString("PatientName", PatientName);
                bundle5.putString("handside", handside);
                bundle5.putString("Type","Line List");
                frag5.setArguments(bundle5);
                tran.replace(R.id.spiral_graph, frag5);
                tran.commit();
                break;
            case 5:
                Bundle bundle6 = new Bundle();
                bundle6.putString("Clinic_ID", Clinic_ID);
                bundle6.putString("PatientName", PatientName);
                bundle6.putString("handside", handside);
                bundle6.putString("Type","Line List");
                frag6.setArguments(bundle6);
                tran.replace(R.id.spiral_graph, frag6);
                tran.commit();
                break;

        }
    }
}
