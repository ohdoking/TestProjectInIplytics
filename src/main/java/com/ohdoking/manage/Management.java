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
                content.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }
        return content;
    }

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
                        project.setName(data[i].replace("\"",""));
                    }
                    else if(i == 1){
                        project.setStartDate(stringToDate(data[i].replace("\"","")));
                    }
                    else if(i == 2){
                        String temp = data[i].replace("\"","");
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

    private boolean isContainProject(Project project) {
        for(Project project1 : projectList){
            if(project1.getName().equals(project.getName())){
                return false;
            }
        }
        return true;
    }

    public List<Project> getAllProjects(){
        return projectList;
    }

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
}
