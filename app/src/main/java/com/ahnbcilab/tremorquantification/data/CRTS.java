package com.ahnbcilab.tremorquantification.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CRTS {
    public String timestamp;
    public int CRTS_count;

    public CRTS(String timestamp, int CRTS_count) {
        this.timestamp = timestamp;
        this.CRTS_count = CRTS_count;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timestamp", timestamp);
        result.put("count", CRTS_count);
        return result;
    }
}
