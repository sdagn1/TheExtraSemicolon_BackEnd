package org.example.validators;

import org.example.exceptions.Entity;
import org.example.exceptions.InvalidImportFileException;

import java.util.ArrayList;
import java.util.List;

public class FileImportValidator {
    public static boolean isNotNumber(final String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public void validateFileImportLine(final String row, final int counter)
            throws InvalidImportFileException {
        List<String> rowItems = List.of(row.split(","));
        List<String> rowErrors = new ArrayList<>();

        System.out.println("Now validating file line");
        if (rowItems.get(0) == null || rowItems.get(0).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", roleName is required");
        } else if (rowItems.get(1) == null || rowItems.get(1).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", description is required");
        } else if (rowItems.get(2) == null || rowItems.get(2).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", responsibilities is required");
        } else if (rowItems.get(3) == null || rowItems.get(3).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", link to job role is required");
        } else if (rowItems.get(4) == null || rowItems.get(4).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", capability is required");
        } else if (rowItems.get(5) == null || rowItems.get(5).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", band is required");
        } else if (isNotNumber(rowItems.get(5))) {
            rowErrors.add("Row " + counter + ", band is not a number");
        } else if (rowItems.get(6) == null || rowItems.get(6).trim().isEmpty()) {
            rowErrors.add("Row " + counter + ", closing date is required");
        }

        if (!rowErrors.isEmpty()) {
            throw new InvalidImportFileException(Entity.FILE, "Invalid File. Please check your file");
        }
    }
}
