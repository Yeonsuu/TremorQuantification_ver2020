package com.ahnbcilab.tremorquantification.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Line {
    public String timestamp;
    public int LINE_count;

    public Line(String timestamp, int SPIRAL_count) {
        this.timestamp = timestamp;
        this.LINE_count = SPIRAL_count;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timestamp", timestamp);
        result.put("count", LINE_count);
        return result;
    }
}
