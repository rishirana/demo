package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminShow extends AppCompatActivity {

    EditText ed;
    Button b,b1;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    String nam,emai,pas,gen,db,mob;
    int aco,par;
    int amt,acc;
    StringBuffer str ,strp;
    String dis,as;
    int flag = 3;
    public boolean val = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show);
        ed = (EditText)findViewById(R.id.acnt);
        b = (Button) findViewById(R.id.s1);
        b1 = (Button) findViewById(R.id.s2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               as = ed.getText().toString();
                flag = 1;
                Log.e("keyyyyy",""+ed.length());
                if (ed.length()==0){
                    ed.setError("Enter Account No");
                   val = false;
                    Log.e("key",""+ed.length());
                }
                else {
                    Show s = new Show();
                    s.execute();
                }
                val= true;
//                Intent i = new Intent(AdminShow.this, Admin_create_new.class);
//                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;

                Show s = new Show();
                s.execute();
            }
        });

    }

    private class Show extends AsyncTask<Void,Void,Void> {
        @Override

        protected Void doInBackground(Void... voids) {
            str = new StringBuffer();
            strp = new StringBuffer();
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();

                ResultSet ps1 = s.executeQuery("select * from bankaccount where accountno = "+as);
                if (ps1.next()){
                  strp.append("name= " + ps1.getString(1) + "\n" + "emailid= " + ps1.getString(2) + "\n" + "Account no= " + ps1.getInt(4) + "\n" + "Gender= " + ps1.getString(5) + "\n" + "Dob = " + ps1.getString(6) + "\n" + "Mobile No = " + ps1.getString(7) + "\n" + "Amount= " + ps1.getInt(8) + "\n\n\n");
                }
                else {
                    strp.append("No user info");
                }
                ResultSet ps = s.executeQuery("select * from bankaccount ");
                if (ps.next()){
                    ps.previous();
                    while (ps.next()){


                        str.append("name = " + ps.getString(1) + "\n" + "emailid = " + ps.getString(2)  + "\n" + "Account no= " + ps.getInt(4) + "\n" + "Gender= " + ps.getString(5) + "\n" + "Dob = " + ps.getString(6) + "\n" + "Mobile No = " + ps.getString(7) + "\n" + "Amount= " + ps.getInt(8) + "\n\n\n");
                        Log.e("key ",""+str);
                    }
                }
                else {
                    str.append("No user information");
                    dis = "No user ";
                }
            }catch (Exception e){
                dis = ""+e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(flag==1){
              specific();
            }
            if(flag==0) {
                all();
            }


            Toast.makeText(AdminShow.this, dis, Toast.LENGTH_LONG).show();

            super.onPostExecute(aVoid);
        }

    }
    public void all(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminShow.this);
        builder.setTitle("Information");
        builder.setMessage(str);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }
    public void specific(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminShow.this);
        builder.setTitle("Information");
        builder.setMessage(strp);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }


}
