package com.ahnbcilab.tremorquantification.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.TaskItem3;
import com.ahnbcilab.tremorquantification.tremorquantification.PersonalPatient;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskListViewAdapter3 extends RecyclerView.Adapter<TaskListViewAdapter3.MyViewHolder>  {
    public ArrayList<TaskItem3> taskList = new ArrayList<>();
    public ArrayList<TaskItem3> selected_taskList = new ArrayList<>() ;
    public PersonalPatient personalPatient = new PersonalPatient() ;
    Menu context_menu;
    Context mContext;

    public TaskListViewAdapter3(Context context, ArrayList<TaskItem3> taskList, ArrayList<TaskItem3> selected_taskList) {
        this.mContext = context;
        this.taskList = taskList;
        this.selected_taskList = selected_taskList ;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskNum;
        TextView taskDate;
        TextView taskTime;
        TextView taskCircle;
        TextView taskName;
        ConstraintLayout ta_listitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskNum = (TextView) itemView.findViewById(R.id.taskNum);
            taskDate = (TextView) itemView.findViewById(R.id.taskDate);
            taskTime = (TextView) itemView.findViewById(R.id.taskTime);
            taskCircle = (TextView) itemView.findViewById(R.id.task_circle);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            ta_listitem = (ConstraintLayout) itemView.findViewById(R.id.ta_listitem3);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.task_list_item3, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(itemView) ;
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskItem3 data = taskList.get(position);
        holder.taskNum.setText(data.getTaskNum());
        holder.taskDate.setText(data.getTaskDate());
        holder.taskTime.setText(data.getTaskTime());
        holder.taskCircle.setText(data.getTaskCircle());
        holder.taskName.setText(data.getTaskName());

        if(data.getTaskCircle().equals("CRTS")){
            holder.taskCircle.setText("");
            holder.taskCircle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.crts_circle));

        }
        else{
            holder.taskCircle.setText("");
            holder.taskCircle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.spiral_circle));
        }

        if (selected_taskList.contains(taskList.get(position)))
            holder.ta_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.ta_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void clear() {
        int size = taskList.size() ;
        taskList.clear() ;
        notifyItemRangeRemoved(0, size);
    }

    //어댑터 정비
    public void refreshAdapter() {
        this.selected_taskList = selected_taskList;
        this.taskList = taskList;
        this.notifyDataSetChanged();
    }


    public void removeList(int position) {
        String count = "" ;
        if(personalPatient.taskType.equals("CRTS List")) {
            count = "CRTS_count" ;
        }
        else if(personalPatient.taskType.equals("UPDRS List")) {
            count = "UPDRS_count" ;
        }
        else if(personalPatient.taskType.equals("Spiral List")) {
            count = "SPIRAL_count" ;
        }
        DatabaseReference ref ;
        ref = FirebaseDatabase.getInstance().getReference() ;
        final Query deleteQuery = ref.child("PatientList").child(personalPatient.Clinic_ID).child(personalPatient.taskType).orderByChild(count).equalTo(position);

        final int finalPosition1 = position;
        final DatabaseReference finalRef1 = ref;
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();
                    TaskNo(finalPosition1) ;
                }
            }
            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ListTaskNo(final int position, DataSnapshot deleteSnapshot, final DatabaseReference finalRef1) {
        String count = "" ;
        if(personalPatient.taskType.equals("CRTS List")) {
            count = "CRTS_count" ;
        }
        else if(personalPatient.taskType.equals("UPDRS List")) {
            count = "UPDRS_count" ;
        }
        else if(personalPatient.taskType.equals("Spiral List")) {
            count = "SPIRAL_count" ;
        }
        String Num = String.valueOf(deleteSnapshot.child(personalPatient.taskType).getChildrenCount()) ;
        int nextPosition = position+1 ;
        if(position<Integer.parseInt(Num)) {
            for(int i = nextPosition ; i<=Integer.parseInt(Num) ; i++){
                final int ii = i -1 ;
                Query ref = finalRef1.child("PatientList").child(personalPatient.Clinic_ID).child(personalPatient.taskType).orderByChild(count).equalTo(i) ;
                final String finalCount = count;
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                            String key = deleteSnapshot.getKey() ;
                            finalRef1.child("PatientList").child(personalPatient.Clinic_ID).child(personalPatient.taskType).child(key).child(finalCount).setValue(ii) ;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
        else {

        }
    }

    public void TaskNo(final int position) {
        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        final Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(personalPatient.Clinic_ID);
        final DatabaseReference finalRef1 = ref;

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                    String CRTSNum = String.valueOf(deleteSnapshot.child("CRTS List").getChildrenCount());
                    String LineNum = String.valueOf(deleteSnapshot.child("Line List").getChildrenCount());
                    String SpiralNum = String.valueOf(deleteSnapshot.child("Spiral List").getChildrenCount());
                    String UPDRSNum = String.valueOf(deleteSnapshot.child("UPDRS List").getChildrenCount());

                    int num = Integer.parseInt(CRTSNum) + Integer.parseInt(LineNum) + Integer.parseInt(SpiralNum) + Integer.parseInt(UPDRSNum);
                    //Toast.makeText(mContext, personalPatient.taskType+num, Toast.LENGTH_SHORT).show();
                    finalRef1.child("PatientList").child(personalPatient.Clinic_ID).child("TaskNo").setValue(num);

                    ListTaskNo(position, deleteSnapshot, ref);
                }
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}