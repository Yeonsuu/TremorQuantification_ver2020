package com.ahnbcilab.tremorquantification.data;

public class CRTS_Result_Data {
    private String title;
    private String totalScore;
    double hz_score ;
    double magnitude_score ;
    double distance_score ;
    double time_score ;
    double speed_score ;
    int image ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public double getHz_score() {
        return hz_score;
    }

    public void setHz_score( double hz_score) {
        this.hz_score = hz_score;
    }

    public double getMagnitude_score() {
        return magnitude_score;
    }

    public void setMagnitude_score( double magnitude_score) {
        this.magnitude_score = magnitude_score;
    }

    public double getDistance_score() {
        return distance_score;
    }

    public void setDistance_score( double distance_score) {
        this.distance_score = distance_score;
    }

    public double getTime_score() {
        return time_score;
    }

    public void setTime_score( double time_score) {
        this.time_score = time_score;
    }

    public double getSpeed_score() {
        return speed_score;
    }

    public void setSpeed_score( double speed_score) {
        this.speed_score = speed_score;
    }

    public int getImage() {
        return image;
    }

    public CRTS_Result_Data(String title, String totalScore, double hz_score, double magnitude_score, double distance_score, double time_score, double speed_score, int image) {
        this.title = title;
        this.totalScore = totalScore;
        this.hz_score = hz_score;
        this.magnitude_score = magnitude_score;
        this.distance_score = distance_score;
        this.time_score = time_score;
        this.speed_score = speed_score;
        this.image = image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
