package org.example.validators;

import org.example.exceptions.InvalidException;

import java.util.Set;

public class JobRoleValidator {
    private static final Set<String> VALID_COLUMNS = Set.of(
            "roleId", "roleName", "description", "responsibilities",
            "linkToJobSpec", "capability", "band", "closingDate",
            "status", "positionsAvailable", "locations"
    );


    public JobRoleValidator() {
    }
    public String validateOrderColumn(final String orderColumn)
            throws InvalidException {
        if (VALID_COLUMNS.contains(orderColumn)) {
            return orderColumn;
        } else {
            throw new InvalidException("Invalid order column");
        }
    }
    public String validateOrderStatement(final String orderStatement)
            throws InvalidException {
        if (orderStatement.equalsIgnoreCase("ASC")
        || orderStatement.equalsIgnoreCase("DESC")) {
            return orderStatement.toUpperCase();
        } else {
            throw new InvalidException("Invalid order statement");
        }
    }
}
