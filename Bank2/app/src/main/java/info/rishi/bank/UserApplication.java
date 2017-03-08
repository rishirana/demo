package info.rishi.bank;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserApplication extends AppCompatActivity {


    Validation validation;


    Spinner spin;
    String str_spinner;
    String min[] = {"please select", "male", "female", "other"};

    EditText ed1, ed2, ed5, ed6, ed7, ed8, ed9,ed11;
    // RadioButton ed3,ed4;
    Button b1;
    String name, mobile, adminid, emailid, pass1, confpass, dob,amt;
    String gender, max,emll;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    public boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_application);

        //link section


        ed1 = (EditText) findViewById(R.id.n1);
        ed2 = (EditText) findViewById(R.id.m1);
        ed6 = (EditText) findViewById(R.id.email1);
        ed7 = (EditText) findViewById(R.id.password1);
        ed8 = (EditText) findViewById(R.id.cnfrmpswd1);
        ed9 = (EditText) findViewById(R.id.dob1);
        ed11 = (EditText) findViewById(R.id.amount);
        b1 = (Button) findViewById(R.id.add);


        //spinner
        spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter ar = new ArrayAdapter(this, android.R.layout.simple_list_item_1, min);
        spin.setAdapter(ar);
        spin.setOnItemSelectedListener(new pa());
        validation = new Validation();

        b1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                name = ed1.getText().toString();
                mobile = ed2.getText().toString();
                str_spinner = spin.getSelectedItem().toString();
                //female = ed4.getText().toString();
                //adminid = ed5.getText().toString();
                emailid = ed6.getText().toString();
                pass1 = ed7.getText().toString();
                confpass = ed8.getText().toString();
                dob = ed9.getText().toString();
                amt = ed11.getText().toString();
                //error check

                rana();
                if (valid == true) {
                    // boolean d = error();
                    Log.e("tag..", "rana");
                    boolean c = check(pass1, confpass);
                    if (c == true) {
                        Rishi k = new Rishi();
                        k.execute();
//                        Intent i = new Intent(UserApplication.this, UserLogin.class);
//                        startActivity(i);
                        Log.e("tag..", "raaaaa");


                    } else {
                        ed8.setError("Password not match");
                       // alert();

                    }


                }
            }
        });


    }

    private void rana() {


        //error message
        if (!validation.isValidName(name)) {
            ed1.setError("Name can't be empty");
            valid = false;
        }

      else  if (!validation.isValidNumber(mobile)) {
            ed2.setError("Mobile No.  can't be empty");
            valid = false;
        }

      else  if (!validation.isValidEmail(emailid)) {
            ed6.setError("Enter correct email id");
            valid = false;
        }

       else if (!validation.isValidPassword(pass1)) {
            ed7.setError(" Password Minimum Length Should be 6 ");
            valid = false;
        }

      else  if (!validation.isValidDob(dob)) {
            ed9.setError("Date of birth can't be empty");
            valid = false;
        }
        else {
            valid = true;
        }
        //||mobile.length()==0||emailid.length()==0||pass1.length()==0||dob.length()==0)
        // {
        // Toast.makeText(this,"Field cant be empty",Toast.LENGTH_LONG).show();
        //  }
        // else{
        //   valid = true;
    }


//    public boolean error(){
//        if(!validation.isValidName(name))
//            ed1.setError("please enter valid name");
//        else
//            ed1.setError(null);
//        if(!validation.isValidEmail(emailid))
//            ed6.setError("please enter valid emailid");
//        else
//            ed6.setError(null);
//        if(!validation.isValidPassword(pass1))
//            ed7.setError("minimum password length should be 6");
//        else
//            ed7.setError(null);
//        if(!validation.isValidNumber(mobile))
//            ed2.setError("please enter valid mobile no");
//        else
//            ed2.setError(null);
//
//
//        return false;
//    }


//    private void alert() {
//        AlertDialog.Builder di = new AlertDialog.Builder(this);
//
//       di.setMessage("confirm password");
//       di.setTitle("password");
//        di.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//            }
//
//        });
//        di.setCancelable(true);
//        di.create().show();
//    }

    private boolean check(String a, String b) {
        if(a.equals(b))
            return true;
        else
            return false;
    }

    private class Rishi extends AsyncTask<Void,Void,Void> {

        //dailog box
        private ProgressDialog Dialog = new ProgressDialog(UserApplication.this);

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url,user,pass);
                Statement s = con.createStatement();
           ResultSet rs =  s.executeQuery("select * from bankaccount where emailid ='"+emailid+"'");
                if(rs.next()){
                  emll = rs.getString(2);
                }
                else {
                    emll = "";
                }

                Log.e("email", emll);



                 if (emll==""){
                    PreparedStatement ps = con.prepareStatement("insert into userapplication values(?,?,?,?,?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, mobile);
                    ps.setString(3, str_spinner);
                    ps.setString(4, dob);
                    ps.setString(5, emailid);
                    ps.setString(6, pass1);
                    ps.setInt(7, Integer.parseInt(amt));
                    ps.executeUpdate();
                    max = "Application Submitted";
                     MailSend m = new MailSend();
                     m.send("ritesh.rana53@gmail.com",0,pass1,name,3);
                }
                else  if (emll.equals(emailid)){
                     max = "Email id already exist .\n Enter different email id";
                 }


                Log.e("em",emailid);

            }catch (Exception e){
                 max = ""+e;
            }



            return null;
        }

        @Override
        protected void onPreExecute() {

           // Dialog.setMessage("Please wait...");
            //Dialog.show();
            //Dialog.setCancelable(true);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(UserApplication.this, max, Toast.LENGTH_LONG).show();

            super.onPostExecute(aVoid);
            Toast.makeText(UserApplication.this,max,Toast.LENGTH_LONG).show();

        }
    }



    //spinner class
    private class pa implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    //link section
    public void login(View view){
        Intent i = new Intent(UserApplication.this,UserLogin.class);
        startActivity(i);
    }
}
