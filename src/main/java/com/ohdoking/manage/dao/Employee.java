package com.ohdoking.manage.dao;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Employee supervisor;
    private List<Project> projectList;

    public Employee(){
        projectList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProject(Project project) {
        if(getProjectList().size() < 2){
            this.projectList.add(project);
        }
        //TODO notification you can't input more project.
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
