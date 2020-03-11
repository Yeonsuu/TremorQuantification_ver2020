package com.ahnbcilab.tremorquantification.data;

public class SurveyData {
    String patient_Id;
    String timestamp;
    String uid;
    int count;


    public SurveyData(String patientId, String timestamp, String uid, int count) {
        this.patient_Id = patientId;
        this.timestamp = timestamp;
        this.uid = uid;
        this.count = count;
    }

    public String getPatient_Id() {
        return patient_Id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUid() {
        return uid;
    }

    public int getCount() {
        return count;
    }
}
