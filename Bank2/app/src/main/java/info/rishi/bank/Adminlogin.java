package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Adminlogin extends AppCompatActivity {

    Validation validation;
    TextView t;
    EditText e1,e2,e3,e4;
    Button b ,bt;
    String email,password,aid,ai;
    String pass2,mail;
    String send_pass,disp,max,send_name;
    int nex;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    public boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
         validation = new Validation();
        e1 = (EditText)findViewById(R.id.email1);
        e2 = (EditText)findViewById(R.id.password1);
        b = (Button) findViewById(R.id.b2);
        bt = (Button)findViewById(R.id.reset);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = e1.getText().toString();
                password = e2.getText().toString();
                //error
                err();

                if(valid==true) {
                    Rana r = new Rana();
                    r.execute();
                }

            }
        });

        //password reset
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rst();
                Log.e("keee","reset");
            }
        });

    }



    public void register(View v){
        nex = 0;
        alert();
    }



    //error
    private void err(){
        if(!validation.isValidEmail(email)||e1.length()==0){
            e1.setError("Enter Valid Email Id");
            valid = false;
        }
        else if(!validation.isValidPassword(password)){
            e2.setError("Enter Correct Password");
            valid = false;
        }
        else {
            valid = true;
        }
    }

    private class  Rana extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url,user,pass);
                Statement s = connection.createStatement();
          ResultSet ps= s.executeQuery("select * from bank where emailid = '"+email+"'");
             if(ps.next()){
                 mail = ps.getString(5);
                 pass2 = ps.getString(6);
             }
//                ResultSet ps1 = s.executeQuery("select * from  bank where emailid ='"+ai+"'");
//                if (ps1.next()){
//                    send_pass = ps1.getString(6);
//                }
//                else{
//                    send_pass = "";
//                }


               // max = "login successfully";
            }catch (Exception e){
               // max = ""+e;
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            if(pass2.equals(password)&&mail.equals(email)){
                loginsuccess();
            }
            else {
                disp = "Invalid User Name or Password";
                loginunseccess(disp);
            }


            super.onPostExecute(aVoid);
        }
    }

    private void loginunseccess( String dis) {
        AlertDialog.Builder di = new AlertDialog.Builder(this);

        di.setMessage(dis);
        di.setTitle("Error");
        di.setIcon(R.drawable.add);
        di.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        });
        di.setCancelable(true);
        di.create().show();
    }

    private void loginsuccess() {
         nex = 1;
        alert();

    }
public void alert() {
    AlertDialog.Builder b = new AlertDialog.Builder(this);
    b.setTitle(" Admin Verification : Enter Pin");
    e3 = new EditText(this);
    e3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    b.setView(e3);
    b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
           aid  = e3.getText().toString();
            if(aid.equals("rana@123")){
                if(nex==1){
                    Intent g = new Intent(Adminlogin.this,AdminInside.class);
                    startActivity(g);
                }
                else if(nex==0){
                    Intent h = new Intent(Adminlogin.this,NewAdmin.class);
                    startActivity(h);
                }
            }
            else{
                String dd = "Varification Failed";
                loginunseccess(dd);
            }

        }
    });
    b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    });
    b.setCancelable(true);
    b.create().show();

}

    //reset dailog box
    public void rst() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(" Enter Register Email id");
        e4 = new EditText(this);
        e4.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        b.setView(e4);
        b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ai = e4.getText().toString();
                Forgot r = new Forgot();
                r.execute();
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setCancelable(false);
    b.create().show();
    }

    private class Forgot extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement p = con.prepareStatement("select * from bank where emailid =?");
                p.setString(1,ai);
                ResultSet res = p.executeQuery();
                if(res.next())
                {
                    send_pass=res.getString(6);
                    send_name = res.getString(1);
                }
                else
                {
                    send_pass="";
                }
                max = "Password Sent to your Registered Email id";
                MailSend mailSend = new MailSend();
                mailSend.send(ai,0,send_pass,send_name,2);
            }
            catch(Exception e)
            {
              max = ""+e;
            }

         return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!send_pass.equals("")){

                Toast.makeText(Adminlogin.this,max,Toast.LENGTH_LONG).show();

            }
            else if (send_pass.equals("")){
                String ss = "Not a Registered Email Id . Please Enter Register Email Id";
                loginunseccess(ss);
            }

            super.onPostExecute(aVoid);
        }
    }}