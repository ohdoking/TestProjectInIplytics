package com.ohdoking.manage;

import com.ohdoking.manage.dao.Project;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Management {

    List<Project> projectList;
    List<Employee> employeeList;

    public List<String[]> readData(String path) throws IOException {
        int count = 0;
        String file = path;
        List<String[]> content = new ArrayList<>();
        boolean checkFirstLine = true;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if(checkFirstLine){
                    checkFirstLine = !checkFirstLine;
                    continue;
                }
                content.add(line.replace("\"","").split(","));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }
        return content;
    }

    /**
     * Importing of existing projects based on the projects.csv (check for invalid rows and duplicates)
     */
    public void importProjects() {
        projectList = new ArrayList();
        String file = "src/main/resources/projects.csv";
        try {
            int index = 0;
            for(String[] data : readData(file)){
                Project project = new Project();
                for(int i = 0 ; i < data.length; i++){
                    project.setId(i);
                    if(i == 0){
                        project.setName(data[i]);
                    }
                    else if(i == 1){
                        project.setStartDate(stringToDate(data[i]));
                    }
                    else if(i == 2){
                        String temp = data[i];
                        if(NumberUtils.isDigits(temp)){
                            project.setBuffer(Integer.valueOf(temp));
                        }
                    }
                }
                //check for invalid rows and duplicates)
                if(project.getBuffer() != 0 && isContainProject(project)){
                    projectList.add(project);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Importing of existing employees based on the employees.csv (check for invalid rows and duplicates)
     */
    public void importEmployees() {

        employeeList = new ArrayList<>();
        String file = "src/main/resources/employees.csv";
        try {
            int index = 0;
            for(String[] data : readData(file)){
                Employee employee = new Employee();
                for(int i = 0 ; i < data.length; i++){
                    employee.setId(i);
                    if(i == 0){
                        employee.setFirstName(data[i]);
                    }
                    else if(i == 1){
                        employee.setLastName(data[i]);
                    }
                    else if(i == 2){
                        if(!data[i].equals(" ")){
                            for(Employee employee1 : employeeList){
                                if(employee1.getLastName().equals(data[i])){
                                    employee.setSupervisor(employee1);
                                }
                            }
                        }
                    }
                }
                employeeList.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * check contain project in project list
     * @param project
     * @return
     */
    private boolean isContainProject(Project project) {
        for(Project project1 : projectList){
            if(project1.getName().equals(project.getName())){
                return false;
            }
        }
        return true;
    }

    /**
     * get Project List
     *
     * @return Project List
     */
    public List<Project> getAllProjects(){
        return projectList;
    }

    /**
     * convert String to LocalDateTime.
     * @param sDate String Date
     * @return LocalDateTime
     */
    private LocalDateTime stringToDate(String sDate){

        Date date = new Date();
        DateFormat format;

        if(sDate.length() == 4){
            sDate = "01/01/".concat(sDate);
            format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        }
        else if(sDate.contains(".")){
            format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        }
        else{
            format = new SimpleDateFormat("yyyy-dd-MM", Locale.ENGLISH);
        }

        try {
            date = format.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * get Employee List
     *
     * @return List<Employee>
     */
    public List<Employee> getAllEmployees() {
        return employeeList;
    }
}
