package org.example.mappers;

import org.example.enums.Location;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import org.example.models.JobRoleResponse;

import java.util.List;
import java.util.stream.Collectors;

public final class JobRoleMapper {
    private JobRoleMapper() {
        throw new UnsupportedOperationException("This is a utility class "
                + "and cannot be instantiated");
    }

    public static List<JobRoleResponse> mapJobRoleListToResponseList(
            final List<JobRole> jobRoles) {
        return jobRoles
                .stream()
                .map(jobRole -> new JobRoleResponse(jobRole.getRoleId(),
                        jobRole.getRoleName(),
                        jobRole.getLocations().stream()
                                .map(Location::getLocation)
                                .collect(Collectors.toList()),
                        jobRole.getCapability().getCapability(),
                        jobRole.getBand().getJobBand(),
                        jobRole.getClosingDate()))
                .collect(Collectors.toList());
    }
    public static JobRoleInfo mapJobRoleToJobRoleInfo(final JobRole jobRole) {
        if (jobRole == null) {
            return null;
        }
        JobRoleInfo jobRoleInfo = new JobRoleInfo(
                jobRole.getRoleId(),
                jobRole.getRoleName(),
                jobRole.getDescription(),
                jobRole.getResponsibilities(),
                jobRole.getLocations().stream()
                        .map(Location::getLocation)
                        .collect(Collectors.joining(", ")),
                jobRole.getLinkToJobSpec(),
                jobRole.getCapability().getCapability()
        );
        jobRoleInfo.setBand(jobRole.getBand().getJobBand());
        jobRoleInfo.setClosingDate(jobRole.getClosingDate());
        jobRoleInfo.setStatus(jobRole.isStatus());
        jobRoleInfo.setPositionsAvailable(jobRole.getPositionsAvailable());
        return jobRoleInfo;
    }
}
