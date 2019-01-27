package com.ohdoking.manage;

import com.ohdoking.manage.dao.Employee;
import com.ohdoking.manage.dao.Project;
import com.ohdoking.manage.dao.Task;
import com.ohdoking.manage.utils.CsvUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Management {

    List<Project> projectList;
    List<Employee> employeeList;
    List<Task> taskList;
    CsvUtil csvUtil;

    static final String PROJECT_CSV_PATH = "src/main/resources/projects.csv";
    static final String EMPLOYEE_CSV_PATH = "src/main/resources/employees.csv";
    static final String TASK_CSV_PATH = "src/main/resources/tasks.csv";

    public Management(){
        csvUtil = new CsvUtil();
    }

    public void imports(){
        importProjects();
        importEmployees();
        importTasks();
    }

    /**
     * Importing of existing projects based on the projects.csv (check for invalid rows and duplicates)
     */
    public void importProjects() {
        projectList = new ArrayList();
        csvUtil.readData(PROJECT_CSV_PATH).forEach(data -> {
            Project project = new Project();
            for(int i = 0 ; i < data.length; i++){
                project.setId(i);
                if(i == 0){
                    project.setName(data[i]);
                }
                else if(i == 1){
                    LocalDateTime tempTime = stringToDate(data[i]);
                    project.setStartDate(tempTime);
                    project.setEndDate(tempTime);
                }
                else if(i == 2){
                    String temp = data[i];
                    if(NumberUtils.isDigits(temp)){
                        project.setBuffer(Integer.valueOf(temp));
                        project.setEndDate(project.getEndDate().plusHours(Integer.valueOf(temp)));
                    }
                }
            }
            //check for invalid rows and duplicates)
            if(project.getBuffer() != 0 && isContainProject(project)){
                projectList.add(project);
            }
        });
    }

    /**
     * Importing of existing employees based on the employees.csv (check for invalid rows and duplicates)
     */
    public void importEmployees() {

        employeeList = new ArrayList<>();
        csvUtil.readData(EMPLOYEE_CSV_PATH).forEach(data -> {
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
            //check for invalid rows and duplicates)
            if(isContainEmployee(employee)) {
                employeeList.add(employee);
            }
        });
    }



    /**
     * Importing of existing tasks based on the tasks.csv (check for invalid rows and duplicates)
     */
    public void importTasks() {
        taskList = new ArrayList<>();
        csvUtil.readData(TASK_CSV_PATH).forEach(data -> {
            Task task = new Task();
            for(int i = 0 ; i < data.length; i++){
                task.setId(i);
                if(i == 0){
                    task.setName(data[i]);
                }
                else if(i == 1){
                    task.setDescription(data[i]);
                }
                else if(i == 2){
                    String temp = data[i];
                    if(NumberUtils.isDigits(temp)){
                        task.setEstimatedHours(Integer.valueOf(temp));
                    }
                }

            }
            //check for invalid rows and duplicates)
            if(isContainTask(task)){
                taskList.add(task);
            }
        });
    }

    /**
     * check contain task in task list
     * @param task
     * @return
     */
    private boolean isContainTask(Task task) {
        for(Task task1 : taskList){
            if(task1.getName().equals(task.getName())){
                return false;
            }
        }
        return true;
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
     * check contain employee in employee list
     * @param employee
     * @return
     */
    private boolean isContainEmployee(Employee employee) {
        for(Employee employee1 : employeeList){
            if(employee1.getFirstName().equals(employee.getFirstName()) && employee1.getLastName().equals(employee.getLastName())){
                return false;
            }
        }
        return true;
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
     * get Project List
     *
     * @return Project List
     */
    public List<Project> getAllProjects(){
        return projectList;
    }

    /**
     * get Employee List
     *
     * @return List<Employee>
     */
    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    /**
     * get Task List
     * @return List<Task>
     */
    public List<Task> getAllTasks() {
        return taskList;
    }

    /**
     * delete task
     * @param task
     */
    public void deleteTask(Task task) {
        for(Task task1 : taskList){
            if(task1.getName().equals(task.getName())){
                taskList.remove(task);
                break;
            }
        }
        //update the underlying references
        csvUtil.deleteRowInCSV(TASK_CSV_PATH, task.getName(), task.getHeader());
    }

    /**
     * delete project
     * @param project
     */
    public void deleteProject(Project project) {
        //delete project in projectList
        for(Project project1 : projectList){
            if(project1.getName().equals(project.getName())){
                projectList.remove(project1);
                break;
            }
        }
        //delete project that assign to employee
        for (Employee employee : employeeList){
            for(Project project1 : employee.getProjectList()){
                if(project1.getName().equals(project.getName())){
                    projectList.remove(project1);
                    break;
                }
            }
        }
        //update the underlying references
        csvUtil.deleteRowInCSV(PROJECT_CSV_PATH, project.getName(), project.getHeader());
    }

    /**
     * get Task by Project
     * @param project
     * @return
     */
    public List<Task> getTasksByProject(Project project) {
        for(Project project1: projectList){
            if(project1.getName().equals(project.getName())){
                return project1.getTaskList();
            }
        }
        //TODO deal with exception.
        return new ArrayList<Task>();
    }

    /**
     * get total days project that i need for finish
     * @param allProjects
     * @return
     */
    public long getTotalDaysProject(List<Project> allProjects) {
        long totalMillis = 0;
        for(Project project : allProjects){
            totalMillis += Duration.between(project.getStartDate(), project.getEndDate()).toMillis();
        }
        return TimeUnit.MILLISECONDS.toDays(totalMillis);
    }
}
