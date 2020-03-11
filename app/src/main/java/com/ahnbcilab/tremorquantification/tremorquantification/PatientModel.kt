package com.ahnbcilab.tremorquantification.tremorquantification

import android.util.Log
import com.ahnbcilab.tremorquantification.data.CurrentUserData.Companion.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


object PatientModel: Observable() {

    val user = FirebaseAuth.getInstance().currentUser
    private var uid: String?= null
    private var mValueDataListener: ValueEventListener? = null
    private var mPatientList: ArrayList<Patient>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("PatientList")
    }

    init{


        if(mValueDataListener != null){
            getDatabaseRef()?.removeEventListener(mValueDataListener as ValueEventListener)
        }
        mValueDataListener = null
        Log.i("PatientModel", "data init line 26")

        mValueDataListener = object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try{
                    Log.i("PatientModel", "data updated line 29")
                    val data: ArrayList<Patient> = ArrayList()
                    if(dataSnapshot != null){
                        for (snapshot: DataSnapshot in dataSnapshot.children){
                            try{
                                data.add(Patient(snapshot))
                            } catch(e: Exception){
                                e.printStackTrace()
                            }
                        }
                        mPatientList = data
                        Log.i("PatientModel", "data updated, there are " + mPatientList!!.size + " entrees in the cache")
                        setChanged()
                        notifyObservers()
                    }
                } catch(e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                if(p0 != null){
                    Log.i("PatientModel", "line 51 data update canceled, err = ${p0.message}")
                }
            }
        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }


    fun getData(uid: String): ArrayList<Patient>?{
        var myPatientList: ArrayList<Patient>? = ArrayList()
        for (i in mPatientList!!.indices) {
            if(mPatientList!![i].uid == uid){
                myPatientList!!.add(mPatientList!![i])
            }
        }

        return myPatientList
    }

}