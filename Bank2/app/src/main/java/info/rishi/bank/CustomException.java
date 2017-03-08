package info.rishi.bank;

/**
 * Created by Ritesh Rana on 7/18/2016.
 */
public class CustomException extends Exception {
    public String toString()
    {
        return "Sorry Insufficient amount".toString();
    }
}
