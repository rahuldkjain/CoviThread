package fileHandler;



import collection.MaxSizeException;
import model.Employee;

import java.text.ParseException;
import java.util.List;

public interface MyFileHandler{
    public void read(String filePath) throws ParseException;
    public void write(String filePath) throws MaxSizeException;
}