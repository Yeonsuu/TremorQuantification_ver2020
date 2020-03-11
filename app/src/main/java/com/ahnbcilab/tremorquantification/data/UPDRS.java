package com.ahnbcilab.tremorquantification.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UPDRS {
    public String timestamp;
    public int UPDRS_count;
    public int UPDRS_score;

    public UPDRS(String timestamp, int UPDRS_count, int UPDRS_score) {
        this.timestamp = timestamp;
        this.UPDRS_count = UPDRS_count;
        this.UPDRS_score = UPDRS_score;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timestamp", timestamp);
        result.put("count", UPDRS_count);
        result.put("updrs_score", UPDRS_score);
        return result;
    }
}
