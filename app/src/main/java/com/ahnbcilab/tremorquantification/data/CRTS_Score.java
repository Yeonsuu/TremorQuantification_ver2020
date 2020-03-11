package com.ahnbcilab.tremorquantification.data;

public class CRTS_Score {
    int partA_score;
    int partB_score;
    int partC_score;

    public CRTS_Score(int partA_score, int partB_score, int partC_score) {
        this.partA_score = partA_score;
        this.partB_score = partB_score;
        this.partC_score = partC_score;
    }

    public int getPartA_score() {
        return partA_score;
    }

    public int getPartB_score() {
        return partB_score;
    }

    public int getPartC_score() {
        return partC_score;
    }
}
