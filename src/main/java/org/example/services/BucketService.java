package org.example.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.example.daos.DatabaseConnector;
import org.example.daos.FileImportDao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BucketService {
    FileImportDao fileImportDao = new FileImportDao();
    DatabaseConnector databaseConnector;

    public BucketService() {
        AWSCredentials credentials = new BasicAWSCredentials(
                System.getenv().get("AWS_ACCESS_KEY_ID"),
                System.getenv().get("AWS_SECRET_ACCESS_KEY")

        );
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();

        String filename = "test.csv";

        System.out.format("Downloading %s from S3 bucket %s...\n",
                filename, System.getenv().get("S3_BUCKET"));

        try {
            S3Object o = s3client.getObject(System.getenv().get(
                    "S3_BUCKET"), filename);
            S3ObjectInputStream s3is = o.getObjectContent();

            FileInputStream fileInputStream = new FileInputStream(
                    "/Users/jemima.orakwue/Documents/test.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream);

//            InputStreamReader inputStreamReader = new InputStreamReader(
//                    s3is);
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            System.out.println("NOT AWS FILE HERE");
            String line;
            List<String> listOfLines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                // Process the line
                System.out.println(line);
                listOfLines.add(line);
            }
//            System.out.println(listOfLines);
            fileImportDao.importRoles(listOfLines);


            bufferedReader.close();
            inputStreamReader.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
