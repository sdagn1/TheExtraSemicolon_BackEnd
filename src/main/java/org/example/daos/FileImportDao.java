package org.example.daos;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FileImportDao {

    public int importRoles() throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String insertStatement = "INSERT INTO Job_Roles"
                    + "(roleName, description, responsibilities, "
                    + "linkToJobSpec, capability, band, closingDate, `status`, "
                    + "positionsAvailable, locations)"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?;) ";

            PreparedStatement statement = connection.prepareStatement(
                    insertStatement,
                    PreparedStatement.RETURN_GENERATED_KEYS);

//          InputStreamReader sr = new InputStreamReader((new FileInputStream(
//                  "/Users/jemima.orakwue/Documents/test.csv")));
//          BufferedReader br = new BufferedReader(new
//                  FileReader(
//                  "/Users/jemima.orakwue/Documents/test.csv"));

            FileInputStream fileInputStream = new FileInputStream(
                    "/Users/jemima.orakwue/Documents/test.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream);
            BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Process the line
                System.out.println(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
