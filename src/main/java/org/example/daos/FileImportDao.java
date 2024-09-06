package org.example.daos;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileInputStream;
import org.example.enums.Location;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileImportDao {

    public int importRoles(final List<String> listOfLines) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {

            String insertStatement = "INSERT INTO Job_Roles"
                    + "(roleName, description, responsibilities, "
                    + "linkToJobSpec, capability, band, closingDate, `status`, "
                    + "positionsAvailable) "
                    + "VALUES "
                    + "(?,?,?,?,?,?,?,?,?);";

            String getLastInsertId = "SET @lastRoleId = LAST_INSERT_ID();";

            String insertLocationStatement = "INSERT INTO Job_Location_Connector"
                    + "(roleId, roleLocationId)"
                    + "VALUES"
                    + "(@lastRoleId, ?);";


            PreparedStatement statement = connection.prepareStatement(
                    insertStatement,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement locationStatement = connection.prepareStatement(insertLocationStatement);

            System.out.println("Number of lines " + listOfLines.size());

            Map<String, Object>[] arrayOfMaps = new HashMap[listOfLines.size()];

            for (int i = 0; i < arrayOfMaps.length; i++) {
                arrayOfMaps[i] = new HashMap<>();
            }

            SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[] lineContents;

            for (String line : listOfLines) {
                lineContents = line.split(",");

                statement.setString(1, lineContents[0]);
                statement.setString(2, lineContents[1]);
                statement.setString(3, lineContents[2]);
                statement.setString(4, lineContents[3]);
                statement.setString(5, lineContents[4]);
                statement.setInt(6, Integer.parseInt(lineContents[5]));

                try {
                    java.util.Date parsedDate;
                    String dateStr = lineContents[6].trim();
                    if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        // Append default time if missing
                        dateStr += " 00:00:00";
                    }
                    parsedDate = sqlDateFormat.parse(dateStr);
                    System.out.println("Appended date: " + dateStr);
                    statement.setTimestamp(7, new Timestamp(parsedDate.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new SQLException("Error parsing date: " + lineContents[6]);
                }

//                statement.setTimestamp(7, Timestamp.valueOf(lineContents[6]));
                statement.setString(8, lineContents[7]);
                statement.setInt(9, Integer.parseInt(lineContents[8]));

                int resultSet = statement.executeUpdate();

                if (resultSet > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        String[] locations = lineContents[9].split(","); // Locations also separated by commas

                        for (String location : locations) {
                            try {
                                Location locEnum = Location.fromString(location.trim());
                                int locationId = locEnum.ordinal() + 1; // Assuming ordinal matches the ID
                                locationStatement.setInt(1, locationId);
                                locationStatement.executeUpdate();
                            } catch (IllegalArgumentException e) {
                                System.err.println("Unknown location: " + location);
                            }
                        }
                    }
                }
            }



//            int i = 0;
//                for (String line : listOfLines) {
//                    lineContents = line.split("|");
//                    arrayOfMaps[i].put("roleName", lineContents[0]);
//                    arrayOfMaps[i].put("description", lineContents[1]);
//                    arrayOfMaps[i].put("responsibilities", lineContents[2]);
//                    arrayOfMaps[i].put("linkToJobSpec", lineContents[3]);
//                    arrayOfMaps[i].put("capability", lineContents[4]);
//                    arrayOfMaps[i].put("band", lineContents[5]);
//                    arrayOfMaps[i].put("closingDate", lineContents[6]);
//                    arrayOfMaps[i].put("status", lineContents[7]);
//                    arrayOfMaps[i].put("positionsAvailable", lineContents[8]);
//                    arrayOfMaps[i].put("locations", lineContents[9]);
//                }
//
//                statement.setString(1, (String) arrayOfMaps[i].get("roleName"));
//                statement.setString(2, (String) arrayOfMaps[i].get("description"));
//                statement.setString(3,  (String) arrayOfMaps[i].get("responsibilities"));
//                statement.setString(4,  (String) arrayOfMaps[i].get("linkToJobSpec"));
//                statement.setString(5,  (String) arrayOfMaps[i].get("capability"));
//                statement.setInt(6, Integer.parseInt(
//                        (String) arrayOfMaps[i].get("band")));
//                statement.setString(7, (String) arrayOfMaps[i].get("closingDate"));
//                statement.setString(8, (String) arrayOfMaps[i].get("status"));
//                statement.setInt(9, Integer.parseInt((String) arrayOfMaps[i].get("positionsAvailable")));
//                statement.setString(10, (String) arrayOfMaps[i].get("locations"));
//
//                int resultSet = statement.executeUpdate();
//
//                System.out.println(resultSet);




//            for (String line : listOfLines) {
//                lineContents = line.split(",");
//                arrayOfMaps[0].put("roleName", lineContents[0]);
//                arrayOfMaps[0].put("description", lineContents[1]);
//                arrayOfMaps[0].put("responsibilities", lineContents[2]);
//                arrayOfMaps[0].put("linkToJobSpec", lineContents[3]);
//                arrayOfMaps[0].put("capability", lineContents[4]);
//                arrayOfMaps[0].put("band", lineContents[5]);
//                arrayOfMaps[0].put("closingDate", lineContents[6]);
//                arrayOfMaps[0].put("status", lineContents[7]);
//                arrayOfMaps[0].put("positionsAvailable", lineContents[8]);
//                arrayOfMaps[0].put("locations", lineContents[9]);
//            }
//            System.out.println("Number 0" + lineContents[0]);
//            System.out.println("Number 1" + lineContents[1]);
//            System.out.println("Number 2" + lineContents[2]);
//            System.out.println("Number 3" + lineContents[3]);
//            System.out.println("Number 4" + lineContents[4]);
//            System.out.println("Number 5" + lineContents[5]);
//            System.out.println("Number 6" +lineContents[6]);
//            System.out.println("Number 7" +lineContents[7]);
//            System.out.println("Number 8" +lineContents[8]);
//            System.out.println("Number 9" +lineContents[9]);
//            System.out.println("Number 10" +lineContents[10]);
////            System.out.println("Number 11" +lineContents[11]);
//
//
//            statement.setString(1, lineContents[0]);
//            statement.setString(2, lineContents[1]);
//            statement.setString(3, lineContents[2]);
//            statement.setString(4, lineContents[5]);
//            statement.setString(5, lineContents[6]);
//            statement.setInt(6, Integer.parseInt(lineContents[7]));
//            statement.setString(7, lineContents[8]);
//            statement.setString(8, lineContents[9]);
//            statement.setInt(9, Integer.parseInt(lineContents[10]));
//            statement.setString(10, lineContents[11]);

//            statement.setString(1, (String) arrayOfMaps[0].get("roleName"));
//            statement.setString(2, (String) arrayOfMaps[0].get("description"));
//            statement.setString(3,  (String) arrayOfMaps[0].get("responsibilities"));
//            statement.setString(4,  (String) arrayOfMaps[0].get("linkToJobSpec"));
//            statement.setString(5,  (String) arrayOfMaps[0].get("capability"));
//            statement.setInt(6, Integer.parseInt(
//                    (String) arrayOfMaps[0].get("band")));
//            statement.setString(7, (String) arrayOfMaps[0].get("closingDate"));
//            statement.setString(8, (String) arrayOfMaps[0].get("status"));
//            statement.setInt(9, Integer.parseInt((String) arrayOfMaps[0].get("positionsAvailable")));
//            statement.setString(10, (String) arrayOfMaps[0].get("locations"));
//
//            int resultSet = statement.executeUpdate();
//
//            System.out.println(resultSet);



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

