package com.ahnbcilab.tremorquantification.data;

public class TaskItem {

    String taskNum ;
    String taskDate;
    String taskTime;
    String taskScore;
    String totalScore;

    public TaskItem(String taskNum, String taskDate, String taskTime, String taskScore, String totalScore) {
        this.taskNum = taskNum;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskScore = taskScore;
        this.totalScore = totalScore;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getTaskScore() {
        return taskScore;
    }

    public String getTotalScore() {
        return totalScore;
    }
}