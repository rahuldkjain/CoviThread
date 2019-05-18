package collection;

import java.util.ArrayList;

public class MyCollection<T> {

    private static final int MAX_SIZE=300;

    ArrayList<T> objArrayList ;

    private static int writeCounter ;
    private static int readCounter ;
    private int lastElement;

    public MyCollection() {
        objArrayList = new ArrayList<T>();
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