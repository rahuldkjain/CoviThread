package fileHandler.fileHandlerImpl;

import collection.MaxSizeException;
import collection.MyCollection;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import fileHandler.MyFileHandler;
import model.Employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVFileHandler implements MyFileHandler, Runnable {
    private static final char DEFAULT_SEPARATOR = ',';


    private String fileName;
    private static Employee employee;
    private static MyCollection myCollection;

    public CSVFileHandler(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void read(String line) {

        try{
            String[] attr = line.split(",");

            //System.out.println(attr[0]+"  "+attr[1]+" "+attr[2]+" " +attr[3]);
            employee.setFirstName(attr[0]);
            employee.setLastName(attr[1]);
            Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(attr[2]);
            employee.setDateOfBirth(dateOfBirth);
            employee.setExperience(Double.parseDouble(attr[3]));
            String input = "Aug 31 09:53:19 2011";

            myCollection.add(employee);
        }
        catch (ParseException p){
            p.printStackTrace();
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }


        //return employee1;
    }

    @Override
    public void write(String filePath) {

        try{
            FileWriter writer=new FileWriter(filePath);
            StringBuilder sb=new StringBuilder();

            sb.append(employee.getFirstName());
            sb.append(",");
            sb.append(employee.getLastName());
            sb.append(",");
            sb.append(employee.getDateOfBirth());
            sb.append(",");
            sb.append(employee.getExperience());
            sb.append("\n");
            //System.out.println(sb.toString());
            writer.append(sb.toString());
            //System.out.println(writer.toString());

            writer.close();
        }
        catch (IOException io){
            io.printStackTrace();
        }


    }

    @Override
    public void run () {
        String name = Thread.currentThread().getName();
        int csvcounter = 0;
        if (csvcounter < 100)
            if (name == "csvRead") {
                this.read(fileName);
            } else if (name == "csvWrite") {
                this.write("/Users/muditjoshi/output.csv");
                csvcounter++;
            }
    }


    /*public static void main(String[] args) {
        //reading
        CSVFileHandler csvFileHandler=new CSVFileHandler();
        String fileName="/Users/gorantlameghana/Downloads/sampleCSV.csv";
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br= new BufferedReader(new FileReader(fileName))){
            String line=br.readLine();
            while (line!=null)
            {
                String[] attr = line.split(",");
                Employee employees=csvFileHandler.read(line);
                System.out.println(employees.toString());
                line=br.readLine();
            }
        }
        catch (IOException io){
            io.printStackTrace();
        }


        //writing
        Employee employee=new Employee();
        employee.setFirstName("meghana");
        employee.setLastName("hi");

        try{
            Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse("1999/28/21");
            employee.setDateOfBirth(dateOfBirth);
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        employee.setExperience(Double.parseDouble("8"));
        //System.out.println(employee.toString());
        csvFileHandler.write(employee,"/Users/gorantlameghana/Downloads/sampleCSV1.csv");

    }*/
}

