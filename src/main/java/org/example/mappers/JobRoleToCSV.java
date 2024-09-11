package org.example.mappers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.opencsv.CSVWriter;
import org.example.models.JobRoleInfoResponse;

public final class JobRoleToCSV {
    private JobRoleToCSV() {
        throw new UnsupportedOperationException("This is a utility class "
                + "and cannot be instantiated");
    }

    public static void writeJobRoleForPipeSeparatorCSV(
            final List<JobRoleInfoResponse> jobRoleInfoResponse,
            final ByteArrayOutputStream outputStream) throws IOException {
        try (OutputStreamWriter outputWriter =
                     new OutputStreamWriter(
                             outputStream, StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(outputWriter, ',',
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            for (JobRoleInfoResponse jobRole : jobRoleInfoResponse) {
                String[] jobRoleData = {jobRole.getRoleName(),
                        jobRole.getDescription()
                                .replace(",", "|")
                                .replace("\n", ""),
                        jobRole.getResponsibilities()
                                .replace(",", "|")
                                .replace("\n", ""),
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

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
