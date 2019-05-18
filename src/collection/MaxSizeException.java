package collection;

public class MaxSizeException extends Throwable {
    private String message;
    public MaxSizeException(String message){
        this.message=message;
    }
    public String printMessage(){
        return this.message;
    }
}