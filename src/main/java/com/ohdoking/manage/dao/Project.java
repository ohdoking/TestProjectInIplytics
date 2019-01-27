package com.ohdoking.manage.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String Name;
    private LocalDateTime startDate;
    private int Buffer;
    private LocalDateTime endDate;
    private String[] header = new String[]{"Name","Start Date","Buffer"};

    private List<Task> taskList;

    public Project(){
        taskList = new ArrayList<Task>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBuffer() {
        return Buffer;
    }

    public void setBuffer(int buffer) {
        Buffer = buffer;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void setTask(Task task){
        setEndDate(getEndDate().plusHours(task.getEstimatedHours()));
        this.taskList.add(task);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }
}
