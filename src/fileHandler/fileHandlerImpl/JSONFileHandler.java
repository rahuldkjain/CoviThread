package fileHandler.fileHandlerImpl;


import collection.MaxSizeException;
import collection.MyCollection;
import fileHandler.MyFileHandler;
import model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONFileHandler implements MyFileHandler,Runnable {
    private String fileName;
    private MyCollection myCollection;

    public JSONFileHandler(String fileName, MyCollection myCollection) {
        this.fileName = fileName;
        this.myCollection = myCollection;
    }

    @Override
    public MyCollection read(String filePath) throws java.text.ParseException {
        JSONParser jsonParser = new JSONParser();
        List<Employee> employeeJSON = new ArrayList<>();
        try (FileReader reader = new FileReader(fileName)) {
            Object object = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) object;


            for (Object emp : employeeList) {
                Employee employee = new Employee();
                JSONObject jsonObject = (JSONObject) emp;
                String firstName = (String) jsonObject.get("firstName");
                String lastName = (String) jsonObject.get("lastName");
                String date = (String) jsonObject.get("dateOfBirth");
                long experience = (long) jsonObject.get("experience");
                Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(date);

                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setExperience((double) experience);
                employee.setDateOfBirth(dateOfBirth);

                myCollection.add(employee);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }

        return myCollection;
    }

    @Override
    public void write(String filePath) throws MaxSizeException {
        JSONObject employeeDetails = new JSONObject();

        Employee employee = (Employee) myCollection.get();


        employeeDetails.put("firstName", employee.getFirstName());
        employeeDetails.put("lastName", employee.getLastName());
        employeeDetails.put("experience",employee.getExperience());
        employeeDetails.put("dateOfBirth",employee.getDateOfBirth());

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeDetails);

        //Write JSON file
        try (FileWriter file = new FileWriter(filePath, true)) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    @Override
    public void run () {
        String name = Thread.currentThread().getName();
        int jsoncounter = 0;
        if (name == "jsonRead") {
            try {
                this.myCollection = this.read(fileName);

            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } else if (name == "jsonWrite") {
            while(jsoncounter<100) {
                try {
                    this.write("/Users/muditjoshi/output.json");
                } catch (MaxSizeException e) {
                    e.printStackTrace();
                }
                jsoncounter++;
            }
        }

        System.out.println("JSON: "+ jsoncounter);
    }

}
