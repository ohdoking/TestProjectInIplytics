package com.ohdoking.manage.dao;

import java.time.LocalDateTime;

public class Project {
    private int id;
    private String Name;
    private LocalDateTime startDate;
    private int Buffer;

    public Project(){

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

}
