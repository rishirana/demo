package info.rishi.bank;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
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

import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User_deposit extends AppCompatActivity {

    EditText edt1,edt2;
    Button btn;
    String act;
    int db_aamt;
    String  max,mal,eml,a;
    int add,amount,acn;
    public boolean valid = true;

    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deposit);
        Intent i = this.getIntent();
        Bundle bn = i.getExtras();
        eml = bn.getString("mail");

        edt1 = (EditText)findViewById(R.id.editacc);
        edt2 = (EditText) findViewById(R.id.editamt);
        btn = (Button) findViewById(R.id.deposit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act = edt1.getText().toString();
                 a = edt2.getText().toString();

                     errr();

                    Deposit dp = new Deposit();
                    dp.execute();



            }
        });
    }

    private class Deposit extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try{

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();
                ResultSet ps= s.executeQuery("select * from bankaccount where emailid = '"+eml+"'");
                if(ps.next()) {

                     db_aamt= ps.getInt(8);
                    acn = ps.getInt(4);
                    mal = ps.getString(2);


                    if ( Integer.parseInt(act)== acn) {
                        db_aamt=db_aamt+Integer.parseInt(a);


                        PreparedStatement pr = connection.prepareStatement("update bankaccount set amount = ? where accountno = ?");
                        pr.setInt(1, db_aamt);
                        pr.setInt(2, Integer.parseInt(act));
                        pr.executeUpdate();
                        max = "amount deposit";

                        PreparedStatement pr1 = connection.prepareStatement("insert into minitransaction values(?,?,?,?,?)");
                        pr1.setInt(1, Integer.parseInt(act));
                        pr1.setInt(2, 0);
                        pr1.setInt(3, Integer.parseInt(a));
                        pr1.setString(4, mydate);
                        pr1.setString(5, "");
                        pr1.executeUpdate();
                    }
                    else {
                        max = "invalid account ";
                    }
                    }


            }catch (Exception e){
              max = ""+e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("amount",""+add);
            Toast.makeText(User_deposit.this,max,Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

//    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//    SimpleDateFormat dat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
//    String currentDateandTime =dat.format(new Date());

   // cal.getTime().toLocaleString();

    public void errr(){
        if (edt1.length()==0){
            Log.e("key ", "ran");
            edt1.setError("Enter Account Number");
            Log.e("key ", "rana");
            valid = false;
        }
        else if (edt2.length()==0){
            Log.e("key ", "ranads");
            edt2.setError("Enter Amount ");
            edt2.setTextColor(Color.green(3));
            Log.e("key ", "ranaddddddd");
            valid = false;
        }
        else {
            valid = true;
        }
    }
}
