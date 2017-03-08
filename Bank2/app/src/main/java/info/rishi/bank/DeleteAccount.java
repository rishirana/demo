package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeleteAccount extends AppCompatActivity {

    EditText e;
    Button b;
    String acnt;
    int a;
    String max,acc,dis;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        e = (EditText) findViewById(R.id.acc);
        b = (Button) findViewById(R.id.delete);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acnt = e.getText().toString();

                if (acnt.length() != 0) {
                    alert();
                } else {
                      e.setError("Enter Account Number");
                    max = "Enter the account no";
                    dis = "enter the account no for detail";

                }

            }
        });
    }

    private class Dlt extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement();
                ResultSet ps = statement.executeQuery("select *  from bankaccount where accountno = " + acnt);
                if(ps.next()){
                    dis="Name :"+ps.getString(1)+"\n"+"Email Id :"+ps.getString(2)+"\n"+"Password :"+ps.getString(3)+"\n"+"Account No :"+ps.getInt(4)+"\n"+"Gender :"+ps.getString(5)+"\n"+"Date Of Birth :"+ps.getString(6)+"\n"+"Mobile No :"+ps.getString(7);
                    PreparedStatement pr = connection.prepareStatement("delete from bankaccount where accountno =?");
                    pr.setInt(1, Integer.parseInt(acnt));
                    pr.executeUpdate();

                    max = "account deleted successfully";
                    connection.close();
                }
                else {
                    max = "no such user found";
                    dis = "no information found";
                }



            }catch (Exception e){
             max = ""+e;
            }


                return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Toast.makeText(DeleteAccount.this,max,Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }

    private void alert() {
        AlertDialog.Builder di = new AlertDialog.Builder(this);

        di.setMessage("Do you really want to to delete account");
        di.setTitle("Delete Account");
        di.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    Dlt k = new Dlt();
                    k.execute();


            }

        });
        di.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        di.setCancelable(true);
        di.create().show();
    }


}
