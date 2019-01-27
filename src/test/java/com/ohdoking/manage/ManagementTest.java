package com.ohdoking.manage;

import com.ohdoking.manage.dao.Project;
import com.ohdoking.manage.dao.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ManagementTest {

    Management management;

    @Before
    public void setUp() throws Exception {
        management = new Management();
    }

    /**
     * Importing of existing projects based on the projects.csv (check for invalid rows and duplicates)
     */
    @Test
    public void testImportProject(){
        management.importProjects();
        assertEquals("Mission Impossible",management.getAllProjects().get(0).getName());
    }

    /**
     * Importing of existing employees based on the employees.csv (check for invalid rows and duplicates)
     */
    @Test
    public void testImportEmployees(){
        management.importEmployees();
        assertEquals("Hanne",management.getAllEmployees().get(0).getLastName());
    }

    /**
     * Importing of existing tasks based on the tasks.csv (check for invalid rows and duplicates)
     */
    @Test
    public void testImportTasks(){
        management.importTasks();
        assertEquals("Data Cleaning",management.getAllTasks().get(0).getName());
    }

    /**
     * Assign a task to a project:
     * By this the attributes „assigned tasks" and „end date" will be calculated/update automatically
     */
    @Test
    public void testAssignTaskToProject(){
        management.imports();
        for(Task task : management.getAllTasks()){
            management.getAllProjects().get(0).setTask(task);
        }

        assertTrue(management.getAllProjects().get(0).getTaskList().get(0).getName().equals(management.getAllTasks().get(0).getName()));
        assertEquals(LocalDateTime.of(2017, 1, 22, 14, 00,00),management.getAllProjects().get(0).getEndDate());
    }

    /**
     * Assign a project to an employee (an employee can only work on two projects at the same time).
     */
    @Test
    public void testNotAssingMore2Project(){
        management.imports();
        for(Project project : management.getAllProjects()){
            management.getAllEmployees().get(0).setProject(project);
        }
        assertTrue(management.getAllEmployees().get(0).getProjectList().size() == 2);
    }

    /**
     * Delete a task (don‘t forget to update the underlying references)
     */
    @Test
    public void testDeleteTask(){
        management.imports();
        Task task = management.getAllTasks().get(0);
        management.deleteTask(task);
        assertFalse(management.getAllTasks().get(0).getName().equals(task.getName()));
    }

    /**
     * Delete a project (don‘t forget to update the underlying references)
     */
    @Test
    public void testDeleteProject(){
        management.imports();
        Project project = management.getAllProjects().get(0);
        management.deleteProject(project);
        assertFalse(management.getAllProjects().get(0).getName().equals(project.getName()));
    }

    /**
     * Display/View all employees
     */
    @Test
    public void testGetAllEmployees(){
        management.imports();
        assertEquals(10,management.getAllEmployees().size());
    }

    /**
     * Display/View all tasks for a given project
     */
    @Test
    public void testGetAllTasksByProject(){
        management.imports();
        for(Task task : management.getAllTasks()){
            management.getAllProjects().get(0).setTask(task);
        }
        assertEquals(9,management.getTasksByProject(management.getAllProjects().get(0)).size());
    }


    /**
     * Getting the total days needed for a given list of projects (assuming that projects can‘t be worked on parallel)
     */
    @Test
    public void testGetTotalDaysProjectThatINeedForFinish(){
        management.imports();
        assertEquals(2, management.getTotalDaysProject(management.getAllProjects()));
    }
}
