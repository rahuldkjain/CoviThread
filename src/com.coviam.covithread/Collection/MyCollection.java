package com.coviam.covithread.Collection;

import com.coviam.covithread.model.Employee;

import java.util.ArrayList;

public class MyCollection<T> {

    private static final int MAX_SIZE=300;
    ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    private static int writeCounter ;
    private static int readCounter ;
    private int lastElement;

    public MyCollection() {
        writeCounter=0;
        readCounter=0;
    }

    public static int getWriteCounter() {
        return writeCounter;
    }

    public static void setWriteCounter(int writeCounter) {
        MyCollection.writeCounter = writeCounter;
    }

    public static int getReadCounter() {
        return readCounter;
    }

    public static void setReadCounter(int readCounter) {
        MyCollection.readCounter = readCounter;
    }

    public void add(Employee employee)
    {
        employeeArrayList.add(employee);
        writeCounter++;
    }

    public Employee get(){
        Employee emp=new Employee();
        emp=employeeArrayList.get(readCounter);
        readCounter++;
        return emp;
    }
}
