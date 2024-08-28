package org.example.service;

import org.example.controllers.JobRoleController;
import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.models.JobRole;
import org.example.services.JobRoleService;
import org.example.validators.JobRoleValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JobRoleServiceTest {

    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    JobRoleValidator jobRoleValidator = Mockito.mock(JobRoleValidator.class);
    JobRoleController jobRolesController = Mockito.mock(JobRoleController.class);

    JobRoleService jobRoleService = new JobRoleService(jobRoleDao, jobRoleValidator);

    Connection conn;

    @Test
    void getJobRoles_shouldThrowSqlException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(jobRoleDao.getAllJobRoles()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> jobRoleService.getAllJobRoles());
    }

//    @Test
//    void getEmployees_shouldReturnListOfEmployees_whenDaoReturnsListOfEmployees() throws SQLException, DatabaseConnectionException, DoesNotExistException {
//        List<JobRole> jobRoleList = new ArrayList<>();
//        jobRoleList.add(jobRole);
//        Mockito.when(jobRoleDao.getAllJobRoles()).thenReturn(jobRoleList);
//
//        assertEquals(jobRoleList,
//                jobRoleService.getAllJobRoles());
//    }


}
