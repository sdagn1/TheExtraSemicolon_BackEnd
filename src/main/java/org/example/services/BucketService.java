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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BucketService {
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
//        ListObjectsV2Result result = s3client.listObjectsV2(System.getenv().
//        get("S3_BUCKET"));
//        List<S3ObjectSummary> objects = result.getObjectSummaries();
//        for (S3ObjectSummary os : objects) {
//            System.out.println("* " + os.getKey());
//        }

        System.out.format("Downloading %s from S3 bucket %s...\n",
                filename, System.getenv().get("S3_BUCKET"));

        try {
            S3Object o = s3client.getObject(System.getenv().get(
                    "S3_BUCKET"), filename);
            S3ObjectInputStream s3is = o.getObjectContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(s3is));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

//            FileOutputStream fos = new FileOutputStream(new File(filename));
//            byte[] read_buf = new byte[1024];
//            int read_len = 0;
//            while ((read_len = s3is.read(read_buf)) > 0) {
//                fos.write(read_buf, 0, read_len);
//            }
            s3is.close();
            reader.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }
}
