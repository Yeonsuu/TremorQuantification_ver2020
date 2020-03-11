package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.Adapters.ContentsPagerAdapter;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Personal_writing extends AppCompatActivity {

    String Clinic_ID;
    String PatientName;
    String taskDate;
    String taskTime;
    Integer taskNum;
    String downurl;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_write_url;

    public RequestManager mGlideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__writing);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        taskDate = intent.getExtras().getString("taskDate");
        taskTime = intent.getExtras().getString("taskTime");
        taskNum = intent.getExtras().getInt("taskNum");


        String timestamp = taskDate + " " + taskTime;
        String title = taskDate + "     " + taskTime.substring(0, 5);

        TextView t_s = (TextView) findViewById(R.id.today_date);
        TextView c_id = (TextView) findViewById(R.id.clinic_ID);
        TextView p_name = (TextView) findViewById(R.id.patientName);

        t_s.setText(timestamp);
        c_id.setText(Clinic_ID);
        p_name.setText(PatientName);

        //의사 ID 얻어오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        database_write_url = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Writing").child(taskNum.toString());

        //이미지 가져오기
        mGlideRequestManager = Glide.with(Personal_writing.this);
        final ImageView present_write = findViewById(R.id.writing_image_result);
        database_write_url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    downurl = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                    present_write.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .asBitmap()
                                    .load(downurl)
                                    .placeholder(R.drawable.image_loading_rotate)
                                    .apply(new RequestOptions().timeout(40000))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Matrix matrix = new Matrix();
                                            matrix.postRotate(90);
                                            int width = resource.getWidth();
                                            int height = resource.getHeight();

                                            resource = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);
                                            present_write.setImageBitmap(resource);

                                        }
                                    });
                        }
                    });
                }
                else
                {
                    present_write.post(new Runnable() {
                        @Override
                        public void run() {
                            mGlideRequestManager
                                    .load(R.drawable.image_err_rotate)//인터넷 너무 느릴 시 실행
                                    .into(present_write);

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button backButton = (Button) findViewById(R.id.gotohome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "WRITING TASK");
                startActivity(intent);
            }
        });


    }
}
