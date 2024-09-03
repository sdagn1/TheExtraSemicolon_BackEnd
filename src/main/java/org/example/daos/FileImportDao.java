package org.example.daos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FileImportDao {

    public void importRoles() throws SQLException {
      try(Connection connection = DatabaseConnector.getConnection()){
          String insertStatement = "INSERT INTO Job_Roles (roleName, description, responsibilities, " +
                  "linkToJobSpec, capability, band, closingDate, `status`, " +
                  "positionsAvailable, locations)" +
                  "VALUES" +
                  "(?,?,?,?,?,?,?,?,?,?;) ";

          PreparedStatement statement = connection.prepareStatement(insertStatement,
                  PreparedStatement.RETURN_GENERATED_KEYS);
          BufferedReader br = new BufferedReader(new FileReader("/Users/jemima.orakwue/Documents/test.csv"));
          String csvHeadersLine = br.readLine();
          String line = null;
          String splitBy = ",";
          Scanner scanner = null;
          while ((line = br.readLine()) != null)
          {
              scanner = new Scanner (line);
              scanner.useDelimiter(",");

              while (scanner.hasNext()){
                  String

              }

              String[] words = line.split(splitBy);
              System.out.println("column="+words[1]+ " column="+words[2]+" column="+words[3]+" column="+words[4]+" column="+words[5]+" column="+words[6]+" column="+words[7]+" column="+words[8]);
          }


      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
    }



}
