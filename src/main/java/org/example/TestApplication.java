package org.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.example.controllers.TestController;
import org.example.daos.TestDao;
import org.example.services.TestService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestApplication extends Application<TestConfiguration> {
    public static void main(final String[] args) throws Exception {
//        Scanner sc = new Scanner(new File("/Users/jemima.orakwue/Documents/test.csv"));
//        sc.useDelimiter(",");
//        while (sc.hasNext())  //returns a boolean value
//        {
//            System.out.print(sc.next());  //find and returns the next complete token from this scanner
//        }
//        sc.close();  //closes the scanner
//
//

        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("/Users/jemima.orakwue/Documents/test.csv"));
            String csvHeadersLine = br.readLine();
            System.out.println(csvHeadersLine);
            List<String> headers = Arrays.asList(csvHeadersLine.split(splitBy));
            for (String head: headers){
                System.out.println("Header: " + head);
            }
            String line = "";
            while ((line = br.readLine()) != null)
            {
                String[] words = line.split(splitBy);
                System.out.println("column="+words[1]+ " column="+words[2]+" column="+words[3]+" column="+words[4]+" column="+words[5]+" column="+words[6]+" column="+words[7]+" column="+words[8]);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        new TestApplication().run(args);
    }
    @Override
    public String getName() {
        return "Test";
    }
    @Override
    public void initialize(final Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    final TestConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }
    @Override
    public void run(final TestConfiguration configuration,
                    final Environment environment) {
        environment.jersey()
                .register(new TestController(new TestService(new TestDao())));
    }

}
