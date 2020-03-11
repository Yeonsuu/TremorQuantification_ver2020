package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Personal_UPDRS extends AppCompatActivity {

    String Clinic_ID;
    String PatientName;
    String taskDate;
    String taskTime;
    String taskScore;

    TextView u1score, u2score, u3score, u4score, u5score, u6score, u7score, u8score, u9score, u10score;
    TextView u11score, u12score, u13score, u14score, u15score, u16score, u17score, u18score, u19score, u20score;
    TextView u21score, u22score, u23score, u24score, u25score, u26score, u27score;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_updrs;
    DatabaseReference database_patient;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__updrs);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        taskDate = intent.getExtras().getString("taskDate");
        taskTime = intent.getExtras().getString("taskTime");
        taskScore = intent.getExtras().getString("taskScore");

        graph();

        database_patient = firebaseDatabase.getReference("PatientList");
        database_updrs = database_patient.child(Clinic_ID).child("UPDRS List");

        u1score = (TextView) findViewById(R.id.u1score);
        u2score = (TextView) findViewById(R.id.u2score);
        u3score = (TextView) findViewById(R.id.u3score);
        u4score = (TextView) findViewById(R.id.u4score);
        u5score = (TextView) findViewById(R.id.u5score);
        u6score = (TextView) findViewById(R.id.u6score);
        u7score = (TextView) findViewById(R.id.u7score);
        u8score = (TextView) findViewById(R.id.u8score);
        u9score = (TextView) findViewById(R.id.u9score);
        u10score = (TextView) findViewById(R.id.u10score);
        u11score = (TextView) findViewById(R.id.u11score);
        u12score = (TextView) findViewById(R.id.u12score);
        u13score = (TextView) findViewById(R.id.u13score);
        u14score = (TextView) findViewById(R.id.u14score);
        u15score = (TextView) findViewById(R.id.u15score);
        u16score = (TextView) findViewById(R.id.u16score);
        u17score = (TextView) findViewById(R.id.u17score);
        u18score = (TextView) findViewById(R.id.u18score);
        u19score = (TextView) findViewById(R.id.u19score);
        u20score = (TextView) findViewById(R.id.u20score);
        u21score = (TextView) findViewById(R.id.u21score);
        u22score = (TextView) findViewById(R.id.u22score);
        u23score = (TextView) findViewById(R.id.u23score);
        u24score = (TextView) findViewById(R.id.u24score);
        u25score = (TextView) findViewById(R.id.u25score);
        u26score = (TextView) findViewById(R.id.u26score);
        u27score = (TextView) findViewById(R.id.u27score);

        String timestamp = taskDate + " " + taskTime;
        String title = taskDate + "     " + taskTime.substring(0, 5);

        TextView t_s = (TextView) findViewById(R.id.timestamp);
        TextView c_id = (TextView) findViewById(R.id.clinicalID);
        TextView p_name = (TextView) findViewById(R.id.patientName);
        Button backButton = (Button) findViewById(R.id.backButton);
        constraintLayout = (ConstraintLayout) findViewById(R.id.personal_updrs);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "UPDRS");
                startActivity(intent);
            }
        });

        TextView share = (TextView) findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkExternalStorage();
                share();
            }
        });
        t_s.setText(title);
        c_id.setText(Clinic_ID);
        p_name.setText(PatientName);

        database_updrs.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String u1 = mData.child("UPDRS task").child("말하기").getValue() + "점";
                    String u2 = mData.child("UPDRS task").child("얼굴표정").getValue() + "점";
                    String u3 = mData.child("UPDRS task").child("안정시진정_얼굴과턱").getValue() + "점";
                    String u4 = mData.child("UPDRS task").child("안정시진정_오른쪽팔").getValue() + "점";
                    String u5 = mData.child("UPDRS task").child("안정시진정_왼쪽팔").getValue() + "점";
                    String u6 = mData.child("UPDRS task").child("안정시진정_오른쪽다리").getValue() + "점";
                    String u7 = mData.child("UPDRS task").child("안정시진정_왼쪽다리").getValue() + "점";
                    String u8 = mData.child("UPDRS task").child("운동또는자세성진정_오른쪽팔").getValue() + "점";
                    String u9 = mData.child("UPDRS task").child("운동또는자세성진정_왼쪽팔").getValue() + "점";
                    String u10 = mData.child("UPDRS task").child("경직_목").getValue() + "점";
                    String u11 = mData.child("UPDRS task").child("경직_오른쪽팔").getValue() + "점";
                    String u12 = mData.child("UPDRS task").child("경직_왼쪽팔").getValue() + "점";
                    String u13 = mData.child("UPDRS task").child("경직_오른쪽다리").getValue() + "점";
                    String u14 = mData.child("UPDRS task").child("경직_왼쪽다리").getValue() + "점";
                    String u15 = mData.child("UPDRS task").child("손가락벌렸다오므리기_오른쪽손").getValue() + "점";
                    String u16 = mData.child("UPDRS task").child("손가락벌렸다오므리기_왼쪽손").getValue() + "점";
                    String u17 = mData.child("UPDRS task").child("손운동_오른쪽손").getValue() + "점";
                    String u18 = mData.child("UPDRS task").child("손운동_왼쪽손").getValue() + "점";
                    String u19 = mData.child("UPDRS task").child("빠른손놀림_오른쪽손").getValue() + "점";
                    String u20 = mData.child("UPDRS task").child("빠른손놀림_왼쪽손").getValue() + "점";
                    String u21 = mData.child("UPDRS task").child("다리의민첩성_오른쪽다리").getValue() + "점";
                    String u22 = mData.child("UPDRS task").child("다리의민첩성_왼쪽다리").getValue() + "점";
                    String u23 = mData.child("UPDRS task").child("의자에서일어서기").getValue() + "점";
                    String u24 = mData.child("UPDRS task").child("서있는자세").getValue() + "점";
                    String u25 = mData.child("UPDRS task").child("걸음걸이").getValue() + "점";
                    String u26 = mData.child("UPDRS task").child("자세안정").getValue() + "점";
                    String u27 = mData.child("UPDRS task").child("느린행동").getValue() + "점";

                    u1score.setText(u1);
                    u2score.setText(u2);
                    u3score.setText(u3);
                    u4score.setText(u4);
                    u5score.setText(u5);
                    u6score.setText(u6);
                    u7score.setText(u7);
                    u8score.setText(u8);
                    u9score.setText(u9);
                    u10score.setText(u10);
                    u11score.setText(u11);
                    u12score.setText(u12);
                    u13score.setText(u13);
                    u14score.setText(u14);
                    u15score.setText(u15);
                    u16score.setText(u16);
                    u17score.setText(u17);
                    u18score.setText(u18);
                    u19score.setText(u19);
                    u20score.setText(u20);
                    u21score.setText(u21);
                    u22score.setText(u22);
                    u23score.setText(u23);
                    u24score.setText(u24);
                    u25score.setText(u25);
                    u26score.setText(u26);
                    u27score.setText(u27);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void share() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("공유")
                .setMessage("저장 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bitmap bm = Bitmap.createBitmap(constraintLayout.getWidth(), constraintLayout.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bm);
                        Drawable bgDrawable = constraintLayout.getBackground();
                        if (bgDrawable != null) {
                            bgDrawable.draw(canvas);
                        } else {
                            canvas.drawColor(Color.WHITE);
                        }
                        Log.d("share", "share1");
                        constraintLayout.draw(canvas);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        Log.d("share", "share2");
                        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                        try {
                            f.createNewFile();
                            FileOutputStream fo = new FileOutputStream(f);
                            fo.write(bytes.toByteArray());

                            Document document = new Document();
                            String dirpath = Environment.getExternalStorageDirectory().toString();

                            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/newPDF.pdf"));
                            document.open();
                            Log.d("share", "share5");
                            Image image = Image.getInstance(f.toString());
                            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                    - document.rightMargin() - 0) / image.getWidth()) * 100;
                            image.scalePercent(scaler);
                            image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                            Log.d("share", "share6");
                            document.add(image);
                            document.close();
                            Toast.makeText(Personal_UPDRS.this, "PDF 파일 저장성공", Toast.LENGTH_SHORT).show();

                            f.delete();

                        } catch (Exception e) {

                            e.printStackTrace();
                            Log.d("share", "share7" + e.getMessage());
                        }
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();


    }

    private void graph() {
        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.updrs_barchart);
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e2 = new BarEntry(1, new float[]{30f, 30f, 20f});
        int[] colorClassArray = new int[]{Color.GREEN, Color.YELLOW, Color.RED};
        valueSet1.add(v1e2);
        BarDataSet set1 = new BarDataSet(valueSet1, "today_score");
        set1.setColors(colorClassArray);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(set1);
//        YAxis left = chart.getAxisLeft();
//        left.setDrawLabels(false);
//        left.setDrawAxisLine(false);
//        left.setDrawGridLines(false);
//        chart.setDrawGridBackground(false);
//        chart.getLegend().setEnabled(false);
//        chart.setTouchEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawAxisLine(false);
        right.setDrawGridLines(false);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("");
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        chart.setData(data);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        // hide legend
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setAxisMaximum(108);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.animateY(1000);
        chart.invalidate();
    }

    public boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();
        // 외부메모리 상태
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 읽기 쓰기 모두 가능
            Log.d("test", "외부메모리 읽기 쓰기 모두 가능");
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            //읽기전용
            Log.d("test", "외부메모리 읽기만 가능");
            return false;
        } else {
            // 읽기쓰기 모두 안됨
            Log.d("test", "외부메모리 읽기쓰기 모두 안됨 : " + state);
            return false;
        }
    }

}
