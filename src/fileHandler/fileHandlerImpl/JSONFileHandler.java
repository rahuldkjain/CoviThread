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
    /*
        private static void parseEmployeeObject(JSONObject employee)
        {
            //Get employee object within list
            JSONObject employeeObject = (JSONObject) employee.get("employee");

            //Get employee first name
            String firstName = (String) employeeObject.get("firstName");
            System.out.println(firstName);

            //Get employee last name
            String lastName = (String) employeeObject.get("lastName");
            System.out.println(lastName);

            //Get employee website name
            String website = (String) employeeObject.get("website");
            System.out.println(website);
        }
    */
    private String fileName;
    private static Employee employee;
    private static MyCollection myCollection;

    public JSONFileHandler(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void read(String filePath) throws java.text.ParseException {
        JSONParser jsonParser = new JSONParser();
        List<Employee> employeeJSON = new ArrayList<>();
        try (FileReader reader = new FileReader(fileName)) {
            //Read JSON file
            Object object = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) object;


            //Reading the array
            for (Object emp : employeeList) {
                JSONObject jsonObject = (JSONObject) emp;
                //Reading the String
                String firstName = (String) jsonObject.get("firstName");
                String lastName = (String) jsonObject.get("lastName");
                String date = (String) jsonObject.get("dateOfBirth");
                long experience = (long) jsonObject.get("experience");
                Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                System.out.println("Date: " + date);

                //Printing all the values
                System.out.println("Name: " + firstName);
                System.out.println("Age: " + lastName);
                System.out.println("dateOfBirth:" + dateOfBirth);
                System.out.println("Experience:" + experience);

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
        //return employeeJSON;
    }

    @Override
    public void write(String filePath) throws MaxSizeException {
        JSONObject employeeDetails = new JSONObject();

        employee = (Employee) myCollection.get();


        employeeDetails.put("firstName", employee.getFirstName());
        employeeDetails.put("lastName", employee.getLastName());
        employeeDetails.put("experience",employee.getExperience());
        employeeDetails.put("dateOfBirth",employee.getDateOfBirth());

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeDetails);

        //Write JSON file
        try (FileWriter file = new FileWriter(filePath)) {

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
        if (jsoncounter < 100)
            if (name == "jsonRead") {
                try {
                    this.read(fileName);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            } else if (name == "jsonWrite") {
                try {
                    this.write("/Users/muditjoshi/output.json");
                } catch (MaxSizeException e) {
                    e.printStackTrace();
                }
                jsoncounter++;
            }
    }
    /*public static void main(String[] args) throws java.text.ParseException, NullPointerException {
        JSONFileHandler f = new JSONFileHandler("/Users/rahuljain/Documents/CoviamThread/src/com/coviam/covithread/employee.json");
        System.out.println(f.read("/Users/rahuljain/Documents/CoviamThread/src/com/coviam/covithread/employee.json"));    }
    */

}
