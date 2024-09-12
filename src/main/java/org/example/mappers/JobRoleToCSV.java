package org.example.mappers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.opencsv.CSVWriter;
import org.example.models.JobRoleInfoResponse;

public final class JobRoleToCSV {
    private JobRoleToCSV() {
        throw new UnsupportedOperationException("This is a utility class "
                + "and cannot be instantiated");
    }

    public static void writeJobRoleForCsvFormatting(
            final List<JobRoleInfoResponse> jobRoleInfoResponse,
            final ByteArrayOutputStream outputStream) throws IOException {
        try (OutputStreamWriter outputWriter =
                     new OutputStreamWriter(
                             outputStream, StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(outputWriter, ',',
                     CSVWriter.DEFAULT_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            for (JobRoleInfoResponse jobRole : jobRoleInfoResponse) {
                String[] jobRoleData = {
                        (jobRole.getRoleName()),
                        (jobRole.getDescription()),
                        (jobRole.getResponsibilities().replace("•", "*")),
                        (jobRole.getLinkToJobSpec()),
                        (jobRole.getCapability()),
                        (jobRole.getBand()),
                        (jobRole.getLocations()),
                        (String.valueOf(jobRole.getClosingDate())),
                        (String.valueOf(jobRole.getPositionsAvailable()))
                };

                writer.writeNext(jobRoleData);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
