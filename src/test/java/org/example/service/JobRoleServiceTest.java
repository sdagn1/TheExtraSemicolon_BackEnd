package org.example.service;

import org.example.controllers.JobRoleController;
import org.example.daos.JobRoleDao;
import org.example.mappers.JobRoleMapper;
import org.example.enums.Capability;
import org.example.enums.JobBand;
import org.example.enums.Location;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidPageLimitException;
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
            throws SQLException, DatabaseConnectionException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getAllJobRoles(1,10)).thenThrow(SQLException.class);


        assertThrows(SQLException.class,
                () -> jobRoleService.getAllJobRoles(1,10));
    }

    @Test
    void getJobRoleById_shouldThrowSqlException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException, InvalidPageLimitException {
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

        assertEquals(jobRole, jobRoleDao.getJobRoleById(555));
    }

    @Test
    void getJobRoleById_shouldThrowDoesNotExistExceptionWhenDaoReturnsNull()
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getJobRoleById(1)).thenReturn(null);

        assertThrows(DoesNotExistException.class,
                () -> jobRoleService.getJobRoleById(1));
    }

    @Test
    void getJobRoles_shouldReturnListOfJobRoles_whenDaoReturnsListOfJobRoles()
            throws SQLException, DatabaseConnectionException, DoesNotExistException,
            InvalidPageLimitException{
        List<Location> locationList = new ArrayList<>();
        locationList.add(Location.BIRMINGHAM);
        jobRole.setCapability(Capability.ENGINEERING);
        jobRole.setStatus(true);
        jobRole.setLocations(locationList);
        jobRole.setPositionsAvailable(1);

        List<JobRole> jobRoleList = new ArrayList<>();
        jobRoleList.add(jobRole);
        Mockito.when(jobRoleDao.getAllJobRoles(1,10)).thenReturn(jobRoleList);

        List<JobRoleResponse> test1 = JobRoleMapper.mapJobRoleListToResponseList(jobRoleList);
        List<JobRoleResponse> test2 = jobRoleService.getAllJobRoles(1,10);

        assertEquals(test1.get(0).getRoleName(),
                test2.get(0).getRoleName());
    }
    @Test
    void getJobRoles_shouldThrowDoesNotExistException_whenDaoThrowsDoesNotExistException()
            throws SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getAllJobRoles(1,10)).thenThrow(DoesNotExistException.class);

        assertThrows(DoesNotExistException.class,
                () -> jobRoleService.getAllJobRoles(1,10));
    }

    @Test
    void getJobRoles_shouldThrowInvalidPageLimitException_whenDaoThrowsInvalidLimitException()
            throws SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getAllJobRoles(2,11)).thenThrow(InvalidPageLimitException.class);

        assertThrows(InvalidPageLimitException.class,
                () -> jobRoleService.getAllJobRoles(2,11));
    }

    @Test
    void getJobRoles_shouldThrowInvalidPageLimitException_whenDaoThrowsInvalidPageException()
            throws SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getAllJobRoles(100,10)).thenThrow(InvalidPageLimitException.class);

        assertThrows(InvalidPageLimitException.class,
                () -> jobRoleService.getAllJobRoles(100,10));
    }

    @Test
    void getFullJobRoles_shouldThrowDoesNotExistException_whenDaoThrowsDoesNotExistException()
            throws SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getFullJobRoles()).thenThrow(DoesNotExistException.class);

        assertThrows(DoesNotExistException.class,
                () -> jobRoleService.getFullJobRoles());
    }
    @Test
    void getFullJobRoles_shouldThrowSQLException_whenDaoThrowsSQLException()
            throws SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleDao.getFullJobRoles()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> jobRoleService.getFullJobRoles());
    }
    @Test
    void getFullJobRole_shouldReturnJobRoleListWhenDaoReturnsJobRoleList()
            throws SQLException, DoesNotExistException, InvalidPageLimitException {
        Date date = new Date();
        JobRole jobRole1 = new JobRole(
                999,
                "Technical Architect",
                "Test description for the technical architect role.",
                "Responsibility 1, Responsibility 2, Responsibility 3",
                "examplelink.co.uk",
                JobBand.MANAGER,
                date
        );
        jobRole1.setCapability(Capability.ENGINEERING);
        jobRole1.setStatus(true);
        jobRole1.setPositionsAvailable(1);
        jobRole1.setLocations(List.of(Location.BIRMINGHAM));

        JobRole jobRole2 = new JobRole(
                999,
                "Technical Architect",
                "Test description for the technical architect role.",
                "Responsibility 1, Responsibility 2, Responsibility 3",
                "examplelink.co.uk",
                JobBand.MANAGER,
                date
        );
        jobRole2.setCapability(Capability.ENGINEERING);
        jobRole2.setStatus(true);
        jobRole2.setPositionsAvailable(1);
        jobRole2.setLocations(List.of(Location.BIRMINGHAM));
        List<JobRole> jobRoleList = new ArrayList<>();
        jobRoleList.add(jobRole1);
        jobRoleList.add(jobRole2);

        Mockito.when(jobRoleDao.getFullJobRoles()).thenReturn(jobRoleList);

        assertEquals(jobRoleList, jobRoleDao.getFullJobRoles());
    }

}
