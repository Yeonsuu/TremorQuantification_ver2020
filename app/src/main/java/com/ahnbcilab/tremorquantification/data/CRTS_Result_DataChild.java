package com.ahnbcilab.tremorquantification.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CRTS_Result_DataChild implements Parcelable {
    public double hz_score;
    public double magnitude_score;
    public double distance_score;
    public double time_score;
    public double speed_score;
    public int image;

    public CRTS_Result_DataChild(double hz_score, double magnitude_score, double distance_score, double time_score, double speed_score, int image) {
        this.hz_score = hz_score;
        this.magnitude_score = magnitude_score;
        this.distance_score = distance_score;
        this.time_score = time_score;
        this.speed_score = speed_score;
        this.image = image;
    }

    protected CRTS_Result_DataChild(Parcel in) {
        hz_score = in.readDouble();
        magnitude_score = in.readDouble();
        distance_score = in.readDouble();
        time_score = in.readDouble();
        speed_score = in.readDouble();
        image = in.readInt();
    }

    public static final Creator<CRTS_Result_DataChild> CREATOR = new Creator<CRTS_Result_DataChild>() {
        @Override
        public CRTS_Result_DataChild createFromParcel(Parcel in) {
            return new CRTS_Result_DataChild(in);
        }

        @Override
        public CRTS_Result_DataChild[] newArray(int size) {
            return new CRTS_Result_DataChild[size];
        }
    };

    public double getHz_score() {
        return hz_score;
    }

    public void setHz_score(double hz_score) {
        this.hz_score = hz_score;
    }

    public double getMagnitude_score() {
        return magnitude_score;
    }

    public void setMagnitude_score(double magnitude_score) {
        this.magnitude_score = magnitude_score;
    }

    public double getDistance_score() {
        return distance_score;
    }

    public void setDistance_score(double distance_score) {
        this.distance_score = distance_score;
    }

    public double getTime_score() {
        return time_score;
    }

    public void setTime_score(double time_score) {
        this.time_score = time_score;
    }

    public double getSpeed_score() {
        return speed_score;
    }

    public void setSpeed_score(double speed_score) {
        this.speed_score = speed_score;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(hz_score);
        dest.writeDouble(magnitude_score);
        dest.writeDouble(distance_score);
        dest.writeDouble(time_score);
        dest.writeDouble(speed_score);
        dest.writeInt(image);
    }
}
