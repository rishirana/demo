package info.rishi.bank;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class NewAdmin extends AppCompatActivity {

  Validation val;
    Spinner spin;
    String str_spinner;
    String min[] = {"please select","male","female","other"};

    EditText ed1,ed2,ed5,ed6,ed7,ed8,ed9;
   // RadioButton ed3,ed4;
    Button b1;
    String name,mobile,adminid,emailid,pass1,confpass,dob;
    String gender,max;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    public boolean valid = true;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);
        //spinner
        spin = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter ar = new ArrayAdapter(this,android.R.layout.simple_list_item_1,min);
           spin.setAdapter(ar);
        spin.setOnItemSelectedListener(new panku());
        val = new Validation();

        ed1 = (EditText) findViewById(R.id.n1);
        ed2 = (EditText) findViewById(R.id.mobile);
        //radio button
       //ed3 = (RadioGroup) findViewById(R.id.radioGroupbutton);
        //ed4 = (RadioButton) findViewById(R.id.radioButton);


        ed5 = (EditText) findViewById(R.id.adminid);
        ed6 = (EditText) findViewById(R.id.email1);
        ed7 = (EditText) findViewById(R.id.password1);
        ed8 = (EditText) findViewById(R.id.cnfrmpswd1);
        ed9 = (EditText) findViewById(R.id.dob1);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = ed1.getText().toString();
                mobile = ed2.getText().toString();
                //male = ed3.getText().toString();
                str_spinner = spin.getSelectedItem().toString();
                //female = ed4.getText().toString();
                adminid = ed5.getText().toString();
                emailid = ed6.getText().toString();
                pass1 = ed7.getText().toString();
                confpass = ed8.getText().toString();
                dob = ed9.getText().toString();

                errs();
                boolean c= check(pass1,confpass);
                if (valid==true) {
                    if (c == true) {
                        Rishi k = new Rishi();
                        k.execute();
                        Intent i = new Intent(NewAdmin.this, Adminlogin.class);
                        startActivity(i);

                    }
                    else {
                        ed8.setError("password not match");
                        //alert();
                    }
                }
            }
        });



    }

    public void errs(){

        if(!val.isValidName(name)){
            ed1.setError("Enter Name");
            valid = false;
        }
        else if (!val.isValidNumber(mobile)){
            ed2.setError("Enter Mobile Number");
            valid = false;
        }
        else if (!val.isValidDob(dob)){
            ed9.setError("Enter dob in dd/mm/yyyy format");
            valid = false;
        }
        else if (adminid.length()==0){
            ed5.setError("Enter adminid");
            valid = false;
        }
        else if (!val.isValidEmail(emailid)){
            ed6.setError("Enter valid Email id");
            valid = false;
        }
        else if (!val.isValidPassword(pass1)){
            ed7.setError("Password minimum length should be 6");
            valid = false;
        }
        else {
            valid = true;
        }
    }


    private void alert() {
        AlertDialog.Builder di = new AlertDialog.Builder(this);
        di.setMessage("cofirm password");
        di.setTitle("password");
        di.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        });
        di.setCancelable(true);
        di.create().show();
    }

    private boolean check(String a, String b) {
        if(a.equals(b))
            return true;
        else
            return false;
    }

    private class Rishi extends AsyncTask<Void,Void,Void> {

        //dailog box
        private ProgressDialog Dialog = new ProgressDialog(NewAdmin.this);

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url,user,pass);
                PreparedStatement ps = con.prepareStatement("insert into bank values(?,?,?,?,?,?,?)");
                ps.setString(1,name);
                ps.setString(2,mobile);
                ps.setString(3,str_spinner);
                ps.setInt(4, Integer.parseInt(adminid));
                ps.setString(5,emailid);
                ps.setString(6,pass1);
                ps.setString(7,dob);
                ps.executeUpdate();
                max= "New Admin Created";


            }catch (Exception e){
                max = ""+e;
            }



            return null;
        }

        @Override
        protected void onPreExecute() {

            Dialog.setMessage("Please wait...");
            Dialog.show();
            Dialog.setCancelable(true);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(NewAdmin.this,max,Toast.LENGTH_LONG).show();

                       super.onPostExecute(aVoid);
            Toast.makeText(NewAdmin.this,max,Toast.LENGTH_LONG).show();

        }
    }

    //spinner
    private class panku implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    //forgot password
}
