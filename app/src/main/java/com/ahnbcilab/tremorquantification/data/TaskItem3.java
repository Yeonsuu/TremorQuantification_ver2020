package com.ahnbcilab.tremorquantification.data;

public class TaskItem3 {

    String taskNum ;
    String taskDate;
    String taskTime;
    String taskCircle;
    String taskName;

    public TaskItem3(String taskNum, String taskDate, String taskTime, String taskCircle, String taskName) {
        this.taskNum = taskNum;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskCircle = taskCircle;
        this.taskName = taskName;

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

    public String getTaskCircle() { return taskCircle; }

    public String getTaskName() {
        return taskName;
    }

}