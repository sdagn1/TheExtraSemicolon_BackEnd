package org.example.mappers;

import org.example.enums.Location;
import org.example.models.JobRole;
import org.example.models.JobRoleInfoResponse;
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
    public static JobRoleInfoResponse mapJobRoleToJobRoleInfo(
            final JobRole jobRole) {
        if (jobRole == null) {
            return null;
        }
        JobRoleInfoResponse jobRoleInfoResponse = new JobRoleInfoResponse(
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
        jobRoleInfoResponse.setBand(jobRole.getBand().getJobBand());
        jobRoleInfoResponse.setClosingDate(jobRole.getClosingDate());
        jobRoleInfoResponse.setStatus(jobRole.isStatus());
        jobRoleInfoResponse.setPositionsAvailable(
                jobRole.getPositionsAvailable());
        return jobRoleInfoResponse;
    }
}
