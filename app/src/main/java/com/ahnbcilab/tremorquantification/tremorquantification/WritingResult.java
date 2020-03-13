package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public class WritingResult extends AppCompatActivity {

    RadioButton crts11_1_0, crts11_1_1, crts11_1_2, crts11_1_3, crts11_1_4;
    int crts11, crts12, crts13, crts14;
    boolean bool = true;
    int check;
    static int count = 0;
    RadioGroup r_group_crts;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databasepatient;
    DatabaseReference databasewriting;
    private DatabaseReference firebase_write_url = firebaseDatabase.getReference("URL List");
    String downurl = null;
    int total_writing_count;
    RadioButton r_crt_arr[] = new RadioButton[5];
    Button next;

    public RequestManager mGlideRequestManager;
    ImageView present_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_result);


        final Intent intent = getIntent();
        final double[] spiral_result = intent.getDoubleArrayExtra("spiral_result");
        final double[] left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result");
        final double[] line_result = intent.getDoubleArrayExtra("line_result");
        final String path = intent.getStringExtra("path1");
        final String edit = intent.getStringExtra("edit");
        final String PatientName = intent.getStringExtra("PatientName");
        final String Clinic_ID = intent.getStringExtra("Clinic_ID");
        final String crts_num = intent.getStringExtra("crts_num");
        final int left = intent.getIntExtra("left", -1);
        if (edit.equals("yes")) {
            crts11 = intent.getIntExtra("crts11", -1);
            crts12 = intent.getIntExtra("crts12", -1);
            crts13 = intent.getIntExtra("crts13", -1);
            crts14 = intent.getIntExtra("crts14", -1);
            Log.v("WritingResult", "CRTS_Activity_intent " + crts11);
        }
        check = intent.getIntExtra("crts11", -1);

        next = (Button) findViewById(R.id.writing_next);
        final Button quit = (Button) findViewById(R.id.writing_result_quit_button);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        final String timestamp = sdf.format(d);

        databasepatient = firebaseDatabase.getReference("PatientList");
        databasewriting = databasepatient.child(Clinic_ID).child("Writing List");

        databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    total_writing_count = (int) mData.child("Writing List").getChildrenCount();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //의사 ID 얻어오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        //글쓰기 이미지 불러오기
        firebase_write_url = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Writing");
        mGlideRequestManager = Glide.with(WritingResult.this);
        present_write = findViewById(R.id.writing_image);
        firebase_write_url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    count = (int) dataSnapshot.getChildrenCount();
                    /* ******************************** download image from firebase *************************************/
                    int count_now = count - 1;
                    firebase_write_url.child(String.valueOf(count_now)).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                .placeholder(R.drawable.spiral_underline)
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
                                                .load(R.drawable.image_err)//인터넷 사용 느릴 시 사용자에게 알려줘야 함
                                                .into(present_write);

                                    }
                                });
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });


        /* ******************************** end of download image from firebase *************************************/
        crts11_1_0 = (RadioButton) findViewById(R.id.crts11_1_0);
        crts11_1_1 = (RadioButton) findViewById(R.id.crts11_1_1);
        crts11_1_2 = (RadioButton) findViewById(R.id.crts11_1_2);
        crts11_1_3 = (RadioButton) findViewById(R.id.crts11_1_3);
        crts11_1_4 = (RadioButton) findViewById(R.id.crts11_1_4);

        next.setVisibility(View.GONE);
        r_group_crts = (RadioGroup) findViewById(R.id.crg11_1);
        r_crt_arr[0] = crts11_1_0;
        r_crt_arr[1] = crts11_1_1;
        r_crt_arr[2] = crts11_1_2;
        r_crt_arr[3] = crts11_1_3;
        r_crt_arr[4] = crts11_1_4;

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



        if (path.equals("CRTS_detail")) {
            quit.setText("돌아가기");
            if (check == 0) {
                crts11_1_0.setChecked(true);
            } else if (check == 1) {
                crts11_1_1.setChecked(true);
            } else if (check == 2) {
                crts11_1_2.setChecked(true);
            } else if (check == 3) {
                crts11_1_3.setChecked(true);
            } else if (check == 4) {
                crts11_1_4.setChecked(true);
            }
        } else if (edit.equals("yes")) {
            if (crts11 == 0) {
                crts11_1_0.setChecked(true);
            } else if (crts11 == 1) {
                crts11_1_1.setChecked(true);
            } else if (crts11 == 2) {
                crts11_1_2.setChecked(true);
            } else if (crts11 == 3) {
                crts11_1_3.setChecked(true);
            } else if (crts11 == 4) {
                crts11_1_4.setChecked(true);
            }

        }
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quit.getText().toString().equals("돌아가기")) {
                    onBackPressed();
                    finish();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (crts11_1_0.isChecked()) {
                    crts11 = 0;
                } else if (crts11_1_1.isChecked()) {
                    crts11 = 1;
                } else if (crts11_1_2.isChecked()) {
                    crts11 = 2;
                } else if (crts11_1_3.isChecked()) {
                    crts11 = 3;
                } else if (crts11_1_4.isChecked()) {
                    crts11 = 4;
                } else {
                    bool = false;
                }
                if (bool) {
                    if (edit.equals("yes")) {
                        Intent intent = new Intent(getApplicationContext(), CRTSActivity.class);
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
                        intent.putExtra("line_result", line_result);
                        intent.putExtra("left_spiral_result", left_spiral_result);
                        intent.putExtra("spiral_result", spiral_result);
                        startActivity(intent);
                        finish();
                    } else if (edit.equals("no")) {
                        final String key = databasewriting.push().getKey().toString();
                        databasewriting.child(key).child("timestamp").setValue(timestamp);
                        databasewriting.child(key).child("writing_count").setValue(total_writing_count);

                        Intent intent = new Intent(getApplicationContext(), CRTS_SpiralResult.class);
                        intent.putExtra("spiral_result", spiral_result);
                        intent.putExtra("left_spiral_result", left_spiral_result);
                        intent.putExtra("line_result", line_result);
                        intent.putExtra("path", path);
                        intent.putExtra("path1", path);
                        intent.putExtra("edit", edit);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("Clinic_ID", Clinic_ID);
                        intent.putExtra("crts_num", crts_num);
                        intent.putExtra("crts11", crts11);
                        intent.putExtra("left", left); //left : 0, right : 1
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "그림이 평가되지 않았습니다.", Toast.LENGTH_LONG);
                }
            }
        });

    }
    @Override
    protected  void onDestroy()
    {
        Drawable d = present_write.getDrawable();
        if(d instanceof BitmapDrawable)
        {
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            bitmap.recycle();
            bitmap = null;
        }
        if(d != null)
        {
            d.setCallback(null);
        }


        super.onDestroy();
    }
}
