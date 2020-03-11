package com.ahnbcilab.tremorquantification.tremorquantification

import com.google.firebase.database.DataSnapshot

class Patient(snapshot: DataSnapshot) {
    lateinit var clinicID: String
    lateinit var name: String
    lateinit var uid: String


    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            clinicID = data["clinicID"] as String
            name = data["name"] as String
            uid = data["uid"] as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}