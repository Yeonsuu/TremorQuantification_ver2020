package com.ahnbcilab.tremorquantification.tremorquantification;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerViewAdapter;
import com.ahnbcilab.tremorquantification.data.DoctorData;
import com.ahnbcilab.tremorquantification.data.PatientData;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.text.RuleBasedCollator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import lecho.lib.hellocharts.model.ComboLineColumnChartData;


public class PatientListActivity extends AppCompatActivity implements Observer, GoogleApiClient.OnConnectionFailedListener {
    //
    private static final int RC_SIGN_IN = 10;
    private static final int PERMISSION_REQUEST_CODE=1232;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    String name, email, uid, r_uid;
    DatabaseReference databaseDoctor;
    DatabaseReference databaseUPDRS;
    DatabaseReference databaseCRTS;
    DatabaseReference databaseSpiral;
    DatabaseReference databaseLine;
    ArrayList<String> uid_list = new ArrayList<String>();
    ArrayList<String> path_list = new ArrayList<>();
    //
    private long lastTimeBackPressed;

    int year, month, year2, month2;
    PatientData pd;
    DatabaseReference databasePatient;
    DatabaseReference databasePatientList;
    DatabaseReference databasePath;
    int taskNo;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference path_ref = storage.getReference();
    StorageReference temp_ref = storage.getReference();

    boolean isMultiSelect = false;
    boolean deleteMode = false;
    Toolbar toolbar;
    TextView patientNum;
    Button bottom_cancel;
    Button bottom_delete;
    EditText searchPatient;
    CheckBox all_checkBox;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    ArrayList<Patient> data = new ArrayList<>();
    ArrayList<PatientItem> patientList = new ArrayList<>();
    ArrayList<PatientItem> selected_patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        //permission check
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkVerify();
        }


        //
        databaseDoctor = firebaseDatabase.getReference("UserList");
        databaseDoctor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    r_uid = fileSnapshot.child("uid").getValue(String.class);
                    if (r_uid != null) {

                    } else {
                    }
                    uid_list.add(r_uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
//
        final TextView userAccount = (TextView) findViewById(R.id.userAccount);
        userAccount.setText(user.getDisplayName());

        final TextView item_count = (TextView) findViewById(R.id.item_count);

        recyclerView = findViewById(R.id.patientList);

        databasePatient = firebaseDatabase.getReference();
        uid = user.getUid();

        databasePatientList = firebaseDatabase.getReference("PatientList");

        databasePatientList.orderByChild("UserID").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int list_count = (int) dataSnapshot.getChildrenCount();
                item_count.setText("Total " + list_count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        patient_get();

        final TextView t = (TextView) findViewById(R.id.typeViewItem);
        Button button = (Button) findViewById(R.id.patientAdd);
        final TextView typeView = (TextView) findViewById(R.id.typeView);
        final TextView clinicIDView = (TextView) findViewById(R.id.clinicIDView);
        final TextView patientNameView = (TextView) findViewById(R.id.patientNameView);
        final TextView dateView = (TextView) findViewById(R.id.dateView);
        patientNum = (TextView) findViewById(R.id.patient_number);
        bottom_cancel = (Button) findViewById(R.id.button_cancel);
        bottom_delete = (Button) findViewById(R.id.button_delete);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        all_checkBox = (CheckBox) findViewById(R.id.all_checkBox);
        all_checkBox.setVisibility(View.GONE);

        all_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all_checkBox.isChecked()) {
                    for (int i = 0; i < patientList.size(); i++) {
                        if (!patientList.get(i).isDeleteBox()) multi_select(i);
                    }
                } else {
                    for (int i = 0; i < patientList.size(); i++) {
                        if (patientList.get(i).isDeleteBox()) multi_select(i);
                    }
                }
            }
        });


        bottom_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteMode == true) {
                    delete_exit();
                }

            }

        });

        bottom_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_patientList.size() > 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientListActivity.this);
                    alertDialog.setTitle("");
                    alertDialog.setMessage("삭제 하시겠습니까?");
                    alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (selected_patientList.size() > 0) {
                                for (int i = 0; i < selected_patientList.size(); i++) {
                                    patientList.remove(selected_patientList.get(i));
                                    removeList(selected_patientList.get(i).getClinicID());
//                                    Toast.makeText(mContext, personalPatient.Clinic_ID+personalPatient.taskType+String.valueOf(selected_taskList.get(i).getTaskNum()), Toast.LENGTH_SHORT).show();
                                }
                                recyclerViewAdapter.notifyDataSetChanged();
                                if (deleteMode == true) {
                                    delete_exit();
                                }

                            }
                        }
                    });
                    alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();

                }
            }
        });
        //타입에 따라 정렬
        typeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>();
                Context wrapper = new ContextThemeWrapper(PatientListActivity.this, R.style.YOURSTYLE);
                PopupMenu popupMenu = new PopupMenu(wrapper, typeView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_type, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_all:
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;

                            case R.id.item_et:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientType() == "ET")
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;

                            case R.id.item_p:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientType() == "P")
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;

                            case R.id.item_no:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientType() == null)
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        //id에 따라 정렬
        clinicIDView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>();
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, clinicIDView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_clinicid, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_내림차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o2.getClinicID().compareTo(o1.getClinicID());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;

                            case R.id.id_오름차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o1.getClinicID().compareTo(o2.getClinicID());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        //이름에 따라 정렬
        patientNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>();
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, patientNameView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_patientname, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.name_내림차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o2.getPatientName().compareTo(o1.getPatientName());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;

                            case R.id.name_오름차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o1.getPatientName().compareTo(o2.getPatientName());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>();
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, dateView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_date, popupMenu.getMenu());
                SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                Date day = new Date();
                String today = date.format(day);
                final String[] array = today.split("/");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_전체:
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;
                            case R.id.id_1개월:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getDateFinal().equals("")) {

                                    } else {
                                        String[] array2 = patientList.get(i).getDateFinal().split("[.]");
                                        if (array[1].equals("10") || array[1].equals("11") || array[1].equals("12")) {
                                            month = Integer.parseInt(array[1]);
                                        } else {
                                            month = Integer.parseInt(array[1].substring(1, 2));
                                        }
                                        if (array2[1].equals("10") || array2[1].equals("11") || array2[1].equals("12")) {
                                            month2 = Integer.parseInt(array2[1]);
                                        } else {
                                            month2 = Integer.parseInt(array2[1].substring(1, 2));
                                        }
                                        if (month == 1) {
                                            year = Integer.parseInt(array[0].substring(2, 4)) - 1;
                                            month = 12;
                                        } else {
                                            year = Integer.parseInt(array[0].substring(2, 4));
                                            month--;
                                        }
                                        year2 = Integer.parseInt(array2[0]);
                                        if (Integer.parseInt(array2[0]) > year) {
                                            filteredList.add(patientList.get(i));
                                        } else if (Integer.parseInt(array2[0]) == year) {
                                            if (month2 >= month) {
                                                filteredList.add(patientList.get(i));
                                            }
                                        }
                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                    recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                    recyclerView.setAdapter(recyclerViewAdapter);
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }
                                return true;

                            case R.id.id_3개월:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getDateFinal().equals("")) {

                                    } else {
                                        String[] array2 = patientList.get(i).getDateFinal().split("[.]");
                                        if (array[1].equals("10") || array[1].equals("11") || array[1].equals("12")) {
                                            month = Integer.parseInt(array[1]);
                                        } else {
                                            month = Integer.parseInt(array[1].substring(1, 2));
                                        }
                                        if (array2[1].equals("10") || array2[1].equals("11") || array2[1].equals("12")) {
                                            month2 = Integer.parseInt(array2[1]);
                                        } else {
                                            month2 = Integer.parseInt(array2[1].substring(1, 2));
                                        }
                                        if (month <= 3) {
                                            year = Integer.parseInt(array[0].substring(2, 4)) - 1;
                                            month = 12 + (month - 3);
                                        } else {
                                            year = Integer.parseInt(array[0].substring(2, 4));
                                            month = month - 3;
                                        }
                                        year2 = Integer.parseInt(array2[0]);
                                        if (Integer.parseInt(array2[0]) > year) {
                                            filteredList.add(patientList.get(i));
                                        } else if (Integer.parseInt(array2[0]) == year) {
                                            if (month2 >= month) {
                                                filteredList.add(patientList.get(i));
                                            }
                                        }
                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                    recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                    recyclerView.setAdapter(recyclerViewAdapter);
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }
                                return true;

                            case R.id.id_6개월:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getDateFinal().equals("")) {

                                    } else {
                                        String[] array2 = patientList.get(i).getDateFinal().split("[.]");
                                        if (array[1].equals("10") || array[1].equals("11") || array[1].equals("12")) {
                                            month = Integer.parseInt(array[1]);
                                        } else {
                                            month = Integer.parseInt(array[1].substring(1, 2));
                                        }
                                        if (array2[1].equals("10") || array2[1].equals("11") || array2[1].equals("12")) {
                                            month2 = Integer.parseInt(array2[1]);
                                        } else {
                                            month2 = Integer.parseInt(array2[1].substring(1, 2));
                                        }
                                        if (month <= 6) {
                                            year = Integer.parseInt(array[0].substring(2, 4)) - 1;
                                            month = 12 + (month - 6);
                                        } else {
                                            year = Integer.parseInt(array[0].substring(2, 4));
                                            month = month - 6;
                                        }
                                        year2 = Integer.parseInt(array2[0]);
                                        if (Integer.parseInt(array2[0]) > year) {
                                            filteredList.add(patientList.get(i));
                                        } else if (Integer.parseInt(array2[0]) == year) {
                                            if (month2 >= month) {
                                                filteredList.add(patientList.get(i));
                                            }
                                        }
                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                    recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                    recyclerView.setAdapter(recyclerViewAdapter);
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        //환자 등록 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
                //getStatusBarSize() ;
            }
        });

        //계정 버튼
        userAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, userAccount);
                popupMenu.getMenuInflater().inflate(R.menu.menu_account, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_accountChange:
                                Log.v("알림", "구글 LOGOUT");
                                AlertDialog.Builder alt_bld = new AlertDialog.Builder(v.getContext());
                                alt_bld.setMessage("다른 계정으로 로그인 하시겠습니까?").setCancelable(false)
                                        .setPositiveButton("네",

                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int id) {

                                                        signOut();

                                                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                                                        startActivityForResult(signInIntent, RC_SIGN_IN);
                                                    }

                                                }).setNegativeButton("아니오",

                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int id) {

                                                // 아니오 클릭. dialog 닫기.

                                                dialog.cancel();

                                            }

                                        });

                                AlertDialog alert = alt_bld.create();
                                alert.setTitle("계정 변경");
                                alert.setIcon(R.drawable.assignment);
                                alert.show();
                                return true;

                            case R.id.id_logout:
                                FirebaseAuth.getInstance().signOut();
                                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent2);
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAdapter = new RecyclerViewAdapter(this, patientList, selected_patientList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);


        //리스트 클릭했을 때 이벤트
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) multi_select(position);
                else {

                    //LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    Intent intent = new Intent(PatientListActivity.this, PersonalPatient.class);
                    intent.putExtra("ClinicID", recyclerViewAdapter.patientList.get(position).getClinicID());//수정
                    intent.putExtra("PatientName", recyclerViewAdapter.patientList.get(position).getPatientName());
                    intent.putExtra("doc_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("task", "UPDRS");
                    startActivity(intent);

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    all_checkBox.setVisibility(View.VISIBLE);
                    selected_patientList = new ArrayList<PatientItem>();
                    isMultiSelect = true;
                    toolbar.setVisibility(View.VISIBLE);
                    recyclerViewAdapter.visible();
                    deleteMode = true;
                }
                multi_select(position);
            }
        }));



        searchPatient = (EditText) findViewById(R.id.searchPatient);

//검색 기능
        searchPatient.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();
                final ArrayList<PatientItem> filteredList = new ArrayList<>();
                for (int i = 0; i < patientList.size(); i++) {
                    final String text = patientList.get(i).getClinicID();
                    final String text2 = patientList.get(i).getPatientName();
                    if (text.contains(query) || text2.contains(query)) {
                        filteredList.add(patientList.get(i));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
            }
        });

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchPatient.getWindowToken(), 0);

        searchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.showSoftInput(searchPatient, 0);
            }
        });

    }

    private void patient_get() {
        databasePatientList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerViewAdapter.clear();
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String msg = mData.getValue().toString();
                    String id = null, name = null, updrs_task, crts_task, spiral_task, line_task, task, diseaseType, firstDate, finalDate = "";
                    if (msg.contains(user.getUid())) {
                        String result = msg.substring(1, msg.length());
                        String[] array = result.split(", |\\}");
                        for (int i = 0; i < array.length; i++) {
                            if (array[i].contains("ClinicID")) {
                                id = array[i].substring(array[i].indexOf("=") + 1);
                            }
                            if (array[i].contains("ClinicName")) {
                                name = array[i].substring(array[i].indexOf("=") + 1);
                            }
                        }
                        updrs_task = String.valueOf(mData.child("UPDRS List").getChildrenCount());
                        crts_task = String.valueOf(mData.child("CRTS List").getChildrenCount());
                        spiral_task = String.valueOf(mData.child("Spiral List").getChildrenCount());
                        line_task = String.valueOf(mData.child("Line List").getChildrenCount());
                        task = String.valueOf(Integer.parseInt(updrs_task) + Integer.parseInt(crts_task));
                        diseaseType = String.valueOf(mData.child("DiseaseType").getValue());
                        firstDate = String.valueOf(mData.child("FirstDate").getValue());
                        finalDate = String.valueOf(mData.child("FinalDate").getValue());
                        if (firstDate.equals("null")) {
                            firstDate = "";
                        } else {
                            firstDate = date(firstDate);
                        }
                        if (finalDate.equals("null")) {
                            finalDate = "";
                        } else {
                            finalDate = date(finalDate);
                        }
                        if (task.equals("1") && updrs_task.equals("1")) {
                            patientList.add(new PatientItem("P", id, name, firstDate, finalDate, false));
                            databasePatientList.child(id).child("DiseaseType").setValue("P");
                        } else if (task.equals("1") && crts_task.equals("1")) {
                            patientList.add(new PatientItem("ET", id, name, firstDate, finalDate, false));
                            databasePatientList.child(id).child("DiseaseType").setValue("ET");
                        } else {
                            if (diseaseType.equals("P")) {
                                patientList.add(new PatientItem("P", id, name, firstDate, finalDate, false));
                            } else if (diseaseType.equals("ET")) {
                                patientList.add(new PatientItem("ET", id, name, firstDate, finalDate, false));
                            } else {
                                patientList.add(new PatientItem(null, id, name, firstDate, finalDate, false));
                            }
                        }
                    }
                }

                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, patientList, selected_patientList);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private String date(String date) {
        String[] array = date.split("/");
        return array[0].substring(2, 4) + "." + array[1] + "." + array[2];
    }

    private void delete_exit() {
        deleteMode = false;
        isMultiSelect = false;
        recyclerViewAdapter.novisible();
        toolbar.setVisibility(View.GONE);
        all_checkBox.setVisibility(View.GONE);
        for (int i = 0; i < patientList.size(); i++) {
            patientList.get(i).setDeleteBox(false);
        }
        all_checkBox.setChecked(false);

        selected_patientList = new ArrayList<PatientItem>();
        refreshAdapter();
    }

    //환자 등록
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_patient_dialog, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.patienAddButton);
        final Button cancel = (Button) view.findViewById(R.id.patienAddCancel);
        final EditText clinicID = (EditText) view.findViewById(R.id.addClinicID);
        final EditText patientName = (EditText) view.findViewById(R.id.addPatientName);

        final AlertDialog dialog = builder.create();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clinic_ID = clinicID.getText().toString();
                String patient_Name = patientName.getText().toString();

                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> postValues = null;

                pd = new PatientData(clinic_ID, patient_Name, uid, taskNo, "null");
                postValues = pd.toMap();

                if (TextUtils.isEmpty(clinicID.getText().toString()) || TextUtils.isEmpty(patientName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "빈칸을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    childUpdates.put("/PatientList/" + clinic_ID, postValues);
                    databasePatient.updateChildren(childUpdates);
                    recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, patientList, selected_patientList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Toast.makeText(getApplicationContext(), "환자 추가", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //delete
    public void multi_select(int position) {
        if (deleteMode == true) {
            if (selected_patientList.contains(patientList.get(position))) {
                selected_patientList.remove(patientList.get(position));
                patientList.get(position).setDeleteBox(false);

            } else {
                selected_patientList.add(patientList.get(position));
                patientList.get(position).setDeleteBox(true);
            }

            if (selected_patientList.size() > 0)
                patientNum.setText("총 " + selected_patientList.size() + " 명의 환자 선택");
            else
                patientNum.setText("총 " + 0 + " 명의 환자 선택");
            refreshAdapter();


        }
    }

    //어댑터 정비
    public void refreshAdapter() {
        recyclerViewAdapter.selected_patientList = selected_patientList;
        recyclerViewAdapter.patientList = patientList;
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(Observable observable, Object o) {
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            ActivityCompat.finishAffinity(this);
            return;
        }
        if (deleteMode == true) {
            delete_exit();
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.v("알림", "google sign 성공, FireBase Auth.");
                Toast.makeText(PatientListActivity.this, "계정 변경되었습니다.", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.v("알림", result.isSuccess() + " Google Sign In failed. Because : " + result.getStatus().toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("알림", "ONCOMPLETE");
                        if (!task.isSuccessful()) {
                            Log.v("알림", "!task.isSuccessful()");
                            Toast.makeText(PatientListActivity.this, "Authenticataion failed!", Toast.LENGTH_LONG).show();
                        } else {
                            Log.v("알림", "task.isSuccessful()");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (uid_list.size() == 0) {
                                alertDisplay();
                            } else {
                                for (int i = 0; i < uid_list.size(); i++) {
                                    if (uid_list.get(i).equals(user.getUid())) {
                                        getInfo();
                                        DoctorData doc = new DoctorData(name, email, uid);
                                        databaseDoctor.child(uid).setValue(doc);
                                    } else {
                                        alertDisplay();
                                    }
                                }
                            }

                        }

                    }
                });
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("알림", "onConnectionFailed");
    }

    public void signOut() {

        mGoogleApiClient.connect();

        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override

            public void onConnected(@Nullable Bundle bundle) {

                mAuth.signOut();

                if (mGoogleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {

                        @Override

                        public void onResult(@NonNull Status status) {

                            if (status.isSuccess()) {

                                Log.v("알림", "로그아웃 성공");

                                setResult(1);

                            } else {

                                setResult(0);

                            }

                        }

                    });

                }

            }

            @Override

            public void onConnectionSuspended(int i) {

                Log.v("알림", "Google API Client Connection Suspended");

                setResult(-1);

                finish();

            }

        });

    }

    public void removeList(final String Clinicid) {
        DatabaseReference ref;
        Boolean start = false;
        ref = FirebaseDatabase.getInstance().getReference();
        final Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(Clinicid);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();
                }
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //스토리지 삭제
        databasePath = firebaseDatabase.getReference("URL List").child(uid).child(Clinicid).child("Path");
        databasePath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String key = dataSnapshot1.getKey();
                    databasePath.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            path_ref = temp_ref.child(String.valueOf(dataSnapshot.getValue()));
                            path_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.v("삭제 로그 storage", "실패 :"+e.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                removeURL(Clinicid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}

    private void removeURL(String Clinicid){
        //스토리지를 다 삭제해야 url을 삭제할 수 있다.
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference deleteurlref = ref.child("URL List").child(uid).child(Clinicid);
        deleteurlref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                 }
        });
    }

    public void alertDisplay() {

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("프로필 가져오기")
                .setMessage("google 프로필을 가져오는 것에 동의하십니까?")
                .setPositiveButton("동의", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getInfo();
                        DoctorData doc = new DoctorData(name, email, uid);
                        databaseDoctor.child(uid).setValue(doc);
                        Toast.makeText(PatientListActivity.this, email, Toast.LENGTH_SHORT).show();


                    }
                })
                .setNegativeButton("동의안함", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }


    public void getInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        email = user.getEmail();
        uid = user.getUid();
    }

    private void getStatusBarSize() {
        Rect rectgle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int StatusBarHeight = rectgle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int TitleBarHeight = contentViewTop - StatusBarHeight;
        Log.i("StatusBarTest", "StatusBar Height= " + StatusBarHeight +
                " TitleBar Height = " + TitleBarHeight);
    }

    public void checkVerify() {
        if (
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // ...
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // 하나라도 거부한다면.
                        new AlertDialog.Builder(this).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        ActivityCompat.finishAffinity(PatientListActivity.this);
                                    }
                                }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                getApplicationContext().startActivity(intent);
                            }
                        }).setCancelable(false).show();

                        return;
                    }

                }
            }
        }
    }


}
