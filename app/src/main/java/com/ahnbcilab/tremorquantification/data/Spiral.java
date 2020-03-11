package com.ahnbcilab.tremorquantification.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Spiral {
    public String timestamp;
    public int SPIRAL_count;

    public Spiral(String timestamp, int SPIRAL_count) {
        this.timestamp = timestamp;
        this.SPIRAL_count = SPIRAL_count;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timestamp", timestamp);
        result.put("count", SPIRAL_count);
        return result;
    }
}
