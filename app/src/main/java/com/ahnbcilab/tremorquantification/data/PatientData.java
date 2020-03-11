package com.ahnbcilab.tremorquantification.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PatientData {
        String ClinicID;
        String ClinicName;
        String UserID;
        int TaskNo;
        String DiseaseType;

        public PatientData(String clinicID, String clinicName, String userID, int taskNo, String diseaseType) {
                ClinicID = clinicID;
                ClinicName = clinicName;
                UserID = userID;
                TaskNo = taskNo;
                DiseaseType = diseaseType;
        }

        @Exclude
        public Map<String, Object> toMap() {
                HashMap<String, Object> result = new HashMap<>();
                result.put("ClinicID", ClinicID);
                result.put("ClinicName", ClinicName);
                result.put("UserID", UserID);
                result.put("TaskNo", TaskNo);
                result.put("DiseaseType", DiseaseType);
                return result;
        }
}