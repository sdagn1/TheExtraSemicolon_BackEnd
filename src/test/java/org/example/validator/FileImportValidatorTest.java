package org.example.validator;

import org.example.exceptions.InvalidImportFileException;
import org.example.validators.FileImportValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FileImportValidatorTest {
    FileImportValidator fileImportValidator = new FileImportValidator();

    @Test
    public void validateFileImportLine_shouldNotThrowError_whenValidLine(){
        String line = "roleName, description, responsibilities, linkToJobSpec, capability, 1, 2024-12-31";
// Don't forget to add positions, locations to validation
        Assertions.assertDoesNotThrow(() -> fileImportValidator.validateFileImportLine(line, 1));
    }

    @ParameterizedTest
    @CsvSource(value = {
            " '' ",
            " ,description,responsibilities,urlink,capability,1,2024-12-31,1,3,Birmingham",
            "roleName,,responsibilities,urlink,capability,1,2024-12-31,1,3,Birmingham",
            "roleName,description,,urlink,capability,1,2024-12-31,1,3,Birmingham",
            "roleName,description,responsibilities,,capability,1,2024-12-31,1,3,Birmingham ",
            "roleName,description,responsibilities,urlink,,1,2024-12-31,1,3,Birmingham ",
            "roleName,description,responsibilities,urlink,capability,,2024-12-31,1,3,Birmingham ",
            "roleName,description,responsibilities,urlink,capability,one,2024-12-31,1,3,Birmingham",
            "roleName,description,responsibilities,urlink,capability,1, ,1,3,Birmingham",
            "roleName,description,responsibilities,urlink,capability,1,2024-12-31, ,3,Birmingham",
            "roleName,description,responsibilities,urlink,capability,1,2024-12-31,one,3,Birmingham",
            "roleName,description,responsibilities,urlink,capability,1,2024-12-31,1,,Birmingham",
            "roleName,description,responsibilities,urlink,capability,1,2024-12-31,1,three,Birmingham",
            "roleName,description,responsibilities,urlink,capability,1,2024-12-31,1,3,",

    }, delimiter = '|')
    public void validateFileImportLine_shouldThrowError_whenNotValidLine(String row){

        Assertions.assertThrows(InvalidImportFileException.class, ()-> fileImportValidator.validateFileImportLine(row, 1));
    }
}
