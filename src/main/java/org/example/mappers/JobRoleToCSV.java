package org.example.mappers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.opencsv.CSVWriter;
import org.example.models.JobRoleInfoResponse;

public final class JobRoleToCSV {
    private JobRoleToCSV() {
        throw new UnsupportedOperationException("This is a utility class "
                + "and cannot be instantiated");
    }

    public static File writeJobRoleForPipeSeparatorCSV(
            final List<JobRoleInfoResponse> jobRoleInfoResponse,
            final File fileName) throws IOException {
        try (FileWriter outputfile = new FileWriter(fileName);
             CSVWriter writer = new CSVWriter(outputfile, ',',
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            for (JobRoleInfoResponse jobRole : jobRoleInfoResponse) {
                String[] jobRoleData = {jobRole.getRoleName(),
                        jobRole.getDescription().replace(",", "|").replace("\n", ""),
                        jobRole.getResponsibilities().replace(",", "|").replace("\n", ""),
                        jobRole.getLinkToJobSpec(),
                        jobRole.getCapability(),
                        jobRole.getBand(),
                        jobRole.getLocations().replace(",", "|"),
                        String.valueOf(jobRole.getClosingDate()),
                        String.valueOf(jobRole.getPositionsAvailable())};

                writer.writeNext(jobRoleData);

                String[] separator = {"---"};
                writer.writeNext(separator);
            }

            System.out.println("CSV file written successfully with pipe delimiters.");
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
