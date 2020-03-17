package com.ahnbcilab.tremorquantification.data;

public class TaskItem2 {

    String taskDate;
    String taskTime ;
    String taskName;
    String taskNum;
    String taskImage;
    String taskType;
    String taskHandside;

    public TaskItem2(String taskDate, String taskTime, String taskName, String taskNum, String taskImage, String taskType, String taskHandside) {
        this.taskDate = taskDate;
        this.taskTime = taskTime ;
        this.taskName = taskName;
        this.taskNum = taskNum;
        this.taskImage = taskImage;
        this.taskType = taskType;
        this.taskHandside = taskHandside ;
    }

    public String getTaskDate() {
        return taskDate;
    }
    public String getTaskTime() {
        return taskTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public String getTaskImage() { return taskImage ; }

    public String getTaskType() { return taskType; }
    public String getTaskHandside() { return taskHandside; }
}