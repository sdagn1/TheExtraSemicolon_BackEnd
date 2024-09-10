package org.example.mappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.opencsv.CSVWriter;
import org.example.models.JobRoleInfoResponse;

public final class JobRoleToCSV {
    private JobRoleToCSV() {
        throw new UnsupportedOperationException("This is a utility class "
                + "and cannot be instantiated");
    }

    public static FileInputStream writeJobRoleForPipeSeparatorCSV(
            final List<JobRoleInfoResponse> jobRoleInfoResponse,
            final FileInputStream fileName) throws IOException {
        try (FileWriter outputfile = new FileWriter(String.valueOf(fileName));
             CSVWriter writer = new CSVWriter(outputfile, ',',
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            for (JobRoleInfoResponse jobRole : jobRoleInfoResponse) {
                String[] jobRoleData = {jobRole.getRoleName(),
                        jobRole.getDescription()
                                .replace(",", "|")
                                .replace("\n", "")
                                .replace(",Äô", "'"),
                        jobRole.getResponsibilities()
                                .replace(",", "|")
                                .replace("\n", "")
                                .replace("‚Ä¢", "-"),
                        jobRole.getLinkToJobSpec(),
                        jobRole.getCapability(),
                        jobRole.getBand(),
                        jobRole.getLocations().replace(",", "|"),
                        String.valueOf(jobRole.getClosingDate()),
                        String.valueOf(jobRole.getPositionsAvailable())};

                writer.writeNext(jobRoleData);
                String[] newLine = {"---"};
                writer.writeNext(newLine);
            }

            System.out.println(
                    "CSV file written successfully with pipe delimiters.");
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
