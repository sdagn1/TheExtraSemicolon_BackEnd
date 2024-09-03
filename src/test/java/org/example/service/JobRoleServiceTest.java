package org.example.service;

import org.example.controllers.JobRoleController;
import org.example.daos.JobRoleDao;
import org.example.enums.Capability;
import org.example.enums.JobBand;
import org.example.enums.Location;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.mappers.JobRoleMapper;
import org.example.models.JobRole;
import org.example.models.JobRoleResponse;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JobRoleServiceTest {
    Date date = new Date();

    JobRole jobRole = new JobRole(
            999,
            "Technical Architect",
            "Test description for the technical architect role.",
            "Responsibility 1, Responsibility 2, Responsibility 3",
            "examplelink.co.uk",
            JobBand.MANAGER,
            date
    );

    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    JobRoleController jobRolesController = Mockito.mock(JobRoleController.class);

    JobRoleService jobRoleService = new JobRoleService(jobRoleDao);

    Connection conn;

    @Test
    void getJobRoles_shouldThrowSqlException_whenDaoThrowsSqlException()
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
                JobBand.MANAGER,
                date
        );
        jobRole.setCapability(Capability.ENGINEERING);
        jobRole.setStatus(true);
        jobRole.setPositionsAvailable(1);
        jobRole.setLocations(List.of(Location.BIRMINGHAM));

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

    @Test
    void getJobRoles_shouldReturnListOfJobRoles_whenDaoReturnsListOfJobRoles()
            throws SQLException, DatabaseConnectionException, DoesNotExistException {
        List<Location> locationList = new ArrayList<>();
        locationList.add(Location.BIRMINGHAM);
        jobRole.setCapability(Capability.ENGINEERING);
        jobRole.setStatus(true);
        jobRole.setLocations(locationList);
        jobRole.setPositionsAvailable(1);

        List<JobRole> jobRoleList = new ArrayList<>();
        jobRoleList.add(jobRole);
        Mockito.when(jobRoleDao.getAllJobRoles()).thenReturn(jobRoleList);

        List<JobRoleResponse> test1 = JobRoleMapper.mapJobRoleListToResponseList(jobRoleList);
        List<JobRoleResponse> test2 = jobRoleService.getAllJobRoles();

        assertEquals(test1.get(0).getRoleName(),
                test2.get(0).getRoleName());
    }
}
