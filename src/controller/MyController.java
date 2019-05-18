package controller;

import fileHandler.fileHandlerImpl.CSVFileHandler;
import fileHandler.fileHandlerImpl.JSONFileHandler;
import fileHandler.fileHandlerImpl.XMLFileHandler;

public class MyController {

    public static void main(String[] args) throws InterruptedException {

        CSVFileHandler csvFileHandler = new CSVFileHandler("/Users/muditjoshi/employee.csv");
        JSONFileHandler jsonFileHandler = new JSONFileHandler("/Users/muditjoshi/employee.json");
        XMLFileHandler xmlFileHandler = new XMLFileHandler("/Users/muditjoshi/employee.xml");

        System.out.println("Yaaha");

        Thread csvRead = new Thread(csvFileHandler,"csvRead");
        Thread jsonRead = new Thread(jsonFileHandler,"jsonRead");
        Thread xmlRead = new Thread(xmlFileHandler,"xmlRead");

        System.out.println("Aur");

        csvRead.start();
        jsonRead.start();
        xmlRead.start();

        System.out.println("Bhi");
        csvRead.join();
        jsonRead.join();
        xmlRead.join();

        System.out.println("Ho gaya");
        Thread csvWrite = new Thread(csvFileHandler,"csvWrite");
        Thread jsonWrite = new Thread(jsonFileHandler,"jsonWrite");
        Thread xmlWrite = new Thread(xmlFileHandler, "xmlWrite");

        csvWrite.start();
        jsonWrite.start();
        xmlWrite.start();

    }
}


