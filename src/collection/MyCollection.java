package collection;

import java.util.ArrayList;

public class MyCollection<T> {

    private static final int MAX_SIZE=300;

    //don't use the arraylist .. create a reference for list
    ArrayList<T> objArrayList ;

    private static int writeCounter ;
    private static int readCounter ;
    private int lastElement;

    public MyCollection() {
        objArrayList = new ArrayList<T>();
        writeCounter=0;
        readCounter=0;
    }
    public int getWriteCounter() {
        return writeCounter;
    }
    public void setWriteCounter(int writeCounter) {
        MyCollection.writeCounter = writeCounter;
    }
    public int getReadCounter() {
        return readCounter;
    }
    public void setReadCounter(int readCounter) {
        MyCollection.readCounter = readCounter;
    }
    synchronized public void add(T obj) throws MaxSizeException
    {
        if(this.writeCounter>=MAX_SIZE){
            throw new MaxSizeException("List limit exceeded, try removing some elements from the list");
        }
        objArrayList.add(obj);
        writeCounter++;
    }
    synchronized public  Object get() throws MaxSizeException{
        if(this.readCounter>=MAX_SIZE){
            throw new MaxSizeException("All objects already fetched from list");
        }
        Object result=new Object();
        result=objArrayList.get(readCounter);
        readCounter++;
        return result;
    }
}