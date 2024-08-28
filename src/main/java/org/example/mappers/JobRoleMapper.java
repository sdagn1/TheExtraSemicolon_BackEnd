package org.example.mappers;

import org.example.models.JobRole;
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
                        jobRole.getRoleName(), jobRole.getLocations(),
                        jobRole.getCapability(), jobRole.getBand(),
                        jobRole.getClosingDate()))
                .collect(Collectors.toList());
    }

}
