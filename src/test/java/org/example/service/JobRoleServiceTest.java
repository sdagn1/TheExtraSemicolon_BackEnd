package org.example.service;

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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JobRoleServiceTest {

    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    JobRoleValidator jobRoleValidator = Mockito.mock(JobRoleValidator.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    JobRoleService jobRoleService = new JobRoleService(jobRoleDao, jobRoleValidator);

    Connection conn;

    @Test
    void getJobRole_shouldThrowSqlException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(jobRoleDao.getAllJobRoles()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> jobRoleService.getAllJobRoles());
    }

    @Test
    void getJobRoleById_shouldThrowSqlException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(jobRoleDao.getJobRoleById(5))
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> jobRoleService.getJobRoleById(5));
    }

    @Test
    void getJobRoleId_shouldReturnJobRoleWhenDaoReturnsJobRole()
            throws SQLException, DoesNotExistException {
        Date date = new Date();
        JobRole jobRole = new JobRole(
                999,
                "Technical Architect",
                "Test description for the technical architect role.",
                "Responsibility 1, Responsibility 2, Responsibility 3",
                "examplelink.co.uk",
                "Manager",
                date
        );
        jobRole.setCapability("Engineering");
        jobRole.setStatus(true);
        jobRole.setPositionsAvailable(1);
        jobRole.setLocations("Birmingham");

        Mockito.when(jobRoleDao.getJobRoleById(555)).thenReturn(jobRole);

        assertEquals(jobRole, jobRoleService.getJobRoleById(555));
    }

    @Test
    void getJobRoleById_shouldThrowDoesNotExistExceptionWhenDaoReturnsNull()
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException {
        Mockito.when(jobRoleDao.getJobRoleById(1)).thenReturn(null);

        assertThrows(DoesNotExistException.class,
                () -> jobRoleService.getJobRoleById(1));
    }


}
