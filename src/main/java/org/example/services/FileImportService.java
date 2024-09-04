package org.example.services;

import java.io.FileReader;
import java.io.IOException;

public class FileImportService {

    public FileImportService() throws IOException {
        FileReader fileReader = new FileReader("test.csv");
        System.out.println("Reading char by char :\n");
        int i;
        while ((i = fileReader.read()) != -1){
            System.out.print((char)i);
        }
        System.out.println("Read using array: \n");
        char[] charArray = new char [10];

        fileReader.read(charArray);
        System.out.println(charArray);

        fileReader.close();
        System.out.println("FileReader closed!");

    }
}
