package org.example.daos;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class FileImportDao {

    public int importRoles(final List<String> listOfLines) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String insertStatement = "INSERT INTO Job_Roles"
                    + "(roleName, description, responsibilities, "
                    + "linkToJobSpec, capability, band, closingDate, `status`, "
                    + "positionsAvailable, locations)"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement statement = connection.prepareStatement(
                    insertStatement,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            String[] lineContents = new String[listOfLines.size()];
            for (String line : listOfLines) {
                lineContents = line.split(",");
            }
            System.out.println("Number 0" + lineContents[0]);
            System.out.println("Number 1" + lineContents[1]);
            System.out.println("Number 2" + lineContents[2]);
            System.out.println("Number 3" + lineContents[3]);
            System.out.println("Number 4" + lineContents[4]);
            System.out.println("Number 5" + lineContents[5]);
            System.out.println("Number 6" +lineContents[6]);
            System.out.println("Number 7" +lineContents[7]);
            System.out.println("Number 8" +lineContents[8]);
            System.out.println("Number 9" +lineContents[9]);
            System.out.println("Number 10" +lineContents[10]);
            System.out.println("Number 11" +lineContents[11]);


            statement.setString(1, lineContents[0]);
            statement.setString(2, lineContents[1]);
            statement.setString(3, lineContents[2]);
            statement.setString(4, lineContents[5]);
            statement.setString(5, lineContents[6]);
            statement.setInt(6, Integer.parseInt(lineContents[7]));
            statement.setString(7, lineContents[8]);
            statement.setString(8, lineContents[9]);
            statement.setInt(9, Integer.parseInt(lineContents[10]));
            statement.setString(10, lineContents[11]);

            int resultSet = statement.executeUpdate();

            System.out.println(resultSet);



//          InputStreamReader sr = new InputStreamReader((new FileInputStream(
//                  "/Users/jemima.orakwue/Documents/test.csv")));
//          BufferedReader br = new BufferedReader(new
//                  FileReader(
//                  "/Users/jemima.orakwue/Documents/test.csv"));
//
//            FileInputStream fileInputStream = new FileInputStream(
//                    "/Users/jemima.orakwue/Documents/test.csv");
//            InputStreamReader inputStreamReader = new InputStreamReader(
//                    fileInputStream);
//            BufferedReader bufferedReader =
//                    new BufferedReader(inputStreamReader);
//
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                // Process the line
//                System.out.println(line);
//            }
//
//            bufferedReader.close();
//            inputStreamReader.close();
//            fileInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            return 0;
        }
    }
}

