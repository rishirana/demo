package info.rishi.bank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ritesh Rana on 7/16/2016.
 */
public class Validation {

    boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    boolean isValidPassword(String pass) {
        if (!pass.isEmpty() && pass.length() >= 6) {
            return true;
        }
        return false;
    }
    boolean isValidNumber(String num)
    {
        boolean n;
        n= check(num);
        if (n==false&&!num.isEmpty() && num.length()==10) {
            return true;
        }
        return false;
    }
    boolean isValidName(String name)
    {
        boolean a;
        a= check(name);
        if ((a==false)&&!name.isEmpty() && name.length()<30) {
            return true;
        }
        return false;
    }
    boolean isValidDob(String dob){
        String DOB_PATTERN =   "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        Pattern pattern = Pattern.compile(DOB_PATTERN);
        Matcher matcher = pattern.matcher(dob);
        return matcher.matches();
    }
    boolean isValidSpin(String sp){
      if(sp.equals("please wait")){
          return true;
      }
        return false;
    }
    boolean isValidAccount(String n){
        boolean a;
        a =check(n);
        if ((a==false)&&(!n.isEmpty())){
            return true;
        }
        return false;
    }
    boolean check(String aa){
        int count = 0;
        char c[] = aa.toCharArray();
        for (int i=0;i<c.length;i++){
            if (c[i]==' ')
                count++;
        }
        if (count==aa.length()) {
            return true;
        }
        return false;
    }
}
