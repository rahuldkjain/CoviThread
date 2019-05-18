package controller;

import collection.MyCollection;
import fileHandler.fileHandlerImpl.CSVFileHandler;
import fileHandler.fileHandlerImpl.JSONFileHandler;
import fileHandler.fileHandlerImpl.XMLFileHandler;

public class MyController {

    public static void main(String[] args) throws InterruptedException {

        MyCollection myCollection = new MyCollection();

        CSVFileHandler csvFileHandler = new CSVFileHandler("/Users/muditjoshi/employee.csv", myCollection);
        JSONFileHandler jsonFileHandler = new JSONFileHandler("/Users/muditjoshi/employee.json", myCollection);
        XMLFileHandler xmlFileHandler = new XMLFileHandler("/Users/muditjoshi/employee.xml", myCollection);

        Thread csvRead = new Thread(csvFileHandler,"csvRead");
        Thread jsonRead = new Thread(jsonFileHandler,"jsonRead");
        Thread xmlRead = new Thread(xmlFileHandler,"xmlRead");


        // need to handle exceptions here
        csvRead.start();
        jsonRead.start();
        xmlRead.start();

        csvRead.join();
        jsonRead.join();
        xmlRead.join();

        System.out.println("Reading Is Done");
        System.out.println(myCollection.getWriteCounter());
        System.out.println("Starting Write");

        Thread csvWrite = new Thread(csvFileHandler,"csvWrite");
        Thread jsonWrite = new Thread(jsonFileHandler,"jsonWrite");
        Thread xmlWrite = new Thread(xmlFileHandler, "xmlWrite");

        csvWrite.start();
        jsonWrite.start();
        xmlWrite.start();


        csvWrite.join();
        jsonWrite.join();
        xmlWrite.join();
    }
}


