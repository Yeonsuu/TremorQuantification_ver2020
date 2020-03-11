package com.ahnbcilab.tremorquantification.data;

public class DoctorData{
    String name ;
    String email ;
    String uid ;

    DoctorData(){

    }

    public DoctorData(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}