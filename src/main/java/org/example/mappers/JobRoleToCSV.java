package org.example.mappers;

import java.io.File;
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

    public static File writeJobRoleForPipeSeparatorCSV(
            final List<JobRoleInfoResponse> jobRoleInfoResponse,
            final File fileName)
            throws IOException {

        // first create file object for file placed at location
        // specified by filepath


        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(fileName);

            // create CSVWriter with '|' as separator
            CSVWriter writer = new CSVWriter(outputfile, '|',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            for (JobRoleInfoResponse jobRole : jobRoleInfoResponse) {
                String[] jobRoleData = {jobRole.getRoleName(),
                        jobRole.getDescription(),
                        jobRole.getResponsibilities(),
                        jobRole.getLinkToJobSpec(),
                        jobRole.getCapability(),
                        jobRole.getBand(),
                        String.valueOf(jobRole.getClosingDate()),
                        String.valueOf(jobRole.getPositionsAvailable())};
                writer.writeNext(jobRoleData);

                String[] separator = {"---"};
                writer.writeNext(separator);
            }

            writer.close();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
