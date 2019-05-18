package fileHandler.fileHandlerImpl;

import collection.MaxSizeException;
import collection.MyCollection;
import fileHandler.MyFileHandler;
import model.Employee;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;


import java.io.*;

public class CSVFileHandler implements MyFileHandler, Runnable {
    private static final char DEFAULT_SEPARATOR = ',';


    private String fileName;
    private MyCollection myCollection;

    public CSVFileHandler(String fileName, MyCollection myCollection) {
        this.fileName = fileName;
        this.myCollection = myCollection;
    }

    @Override
    public MyCollection read(String fileName) {

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            //depend on a library not as parsing strings
            while(line!=null) {
                String[] attr = line.split(",");
                Employee employee = new Employee();
                //System.out.println(attr[0]+"  "+attr[1]+" "+attr[2]+" " +attr[3]);
                employee.setFirstName(attr[0]);
                employee.setLastName(attr[1]);
                Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(attr[2]);
                employee.setDateOfBirth(dateOfBirth);
                employee.setExperience(Double.parseDouble(attr[3]));
                myCollection.add(employee);
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MaxSizeException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return myCollection;
    }

    @Override
    public void write(String filePath) {
        Employee employee = new Employee();

        try{
            FileWriter writer=new FileWriter(filePath, true);
            StringBuilder sb=new StringBuilder();
            employee = (Employee) myCollection.get();
            sb.append(employee.getFirstName());
            sb.append(",");
            sb.append(employee.getLastName());
            sb.append(",");
            sb.append(employee.getDateOfBirth());
            sb.append(",");
            sb.append(employee.getExperience());
            sb.append("\n");
            writer.append(sb.toString());

            writer.close();
        }
        catch (IOException io){
            io.printStackTrace();
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run () {
        String name = Thread.currentThread().getName();
        int csvcounter = 0;
        if (name == "csvRead") {
            this.read(fileName);
        } else if (name == "csvWrite") {
            while(csvcounter<100) {
                this.write("/Users/muditjoshi/output.csv");
                csvcounter++;
            }
        }

        System.out.println("CSV: "+ csvcounter);
    }



}

