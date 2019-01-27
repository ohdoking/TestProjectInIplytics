package com.ohdoking.manage;

import com.ohdoking.manage.dao.Project;
import com.ohdoking.manage.dao.Task;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue(management.getAllTasks().get(0).getName().equals(task.getName()));
    }

    /**
     * Delete a project (don‘t forget to update the underlying references)
     */
    @Test
    public void testDeleteProject(){
        management.imports();
        Project project = management.getAllProjects().get(0);
        management.deleteProject(project);
        assertTrue(management.getAllProjects().get(0).getName().equals(project.getName()));
    }

    /**
     * Display/View all employees
     */

    /**
     * Display/View all tasks for a given project
     */

    /**
     * Getting the total days needed for a given list of projects (assuming that projects can‘t be worked on parallel)
     */
}
