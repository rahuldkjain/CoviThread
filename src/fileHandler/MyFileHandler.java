package fileHandler;



import collection.MaxSizeException;
import collection.MyCollection;
import model.Employee;

import java.text.ParseException;
import java.util.List;

public interface MyFileHandler{
    public MyCollection read(String filePath) throws ParseException;
    public void write(String filePath) throws MaxSizeException;
}