package com.ohdoking.manage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    /**
     * Importing of existing tasks based on the tasks.csv (check for invalid rows and duplicates)
     */

    /**
     * Assign a task to a project:
     * By this the attributes „assigned tasks" and „end date" will be calculated/update automatically
     */

    /**
     * Assign a project to an employee (an employee can only work on two projects at the same time).
     */

    /**
     * Delete a task (don‘t forget to update the underlying references)
     */

    /**
     * Delete a project (don‘t forget to update the underlying references)
     */

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
