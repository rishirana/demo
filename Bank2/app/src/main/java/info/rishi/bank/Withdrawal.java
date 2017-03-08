package info.rishi.bank;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
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

public class Withdrawal extends AppCompatActivity {

    EditText e1,e2;
    Button b;
    String ml,a,act;
    int amount,db_aamt;
    int acn,d;
    String max;
    public boolean valid = true;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        Intent i = this.getIntent();
        Bundle bn = i.getExtras();
        ml = bn.getString("mail");


        e1 = (EditText)findViewById(R.id.acont);
        e2 = (EditText) findViewById(R.id.withdraw);
        b = (Button) findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation validation = new Validation();
                boolean A,C;
                act = e1.getText().toString();
                A=validation.isValidAccount(act);
                if (A==false){
                    e1.setError("Enter Account Number");
                }else
                    d = Integer.parseInt(act);
                 a = e2.getText().toString();
                C = validation.isValidAccount(a);
                if (C==false){
                    e2.setError("Enter Amount");
                }else
                amount = Integer.parseInt(a);
                //errr();
                if (A==true&&C==true) {
                   // if (valid == true) {
                        Withdraw dp = new Withdraw();
                        dp.execute();
                   // }
                }
            }
        });
    }

    private class Withdraw extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();
                ResultSet ps= s.executeQuery("select * from bankaccount where emailid = '"+ml+"'");
                if(ps.next()) {
                    db_aamt = ps.getInt(8);
                    acn = ps.getInt(4);
                    if(amount>db_aamt)
                    {
                        throw new CustomException();

                        }

                    else {
                        if(acn==d) {

                            db_aamt = db_aamt - amount;
                            PreparedStatement pr = connection.prepareStatement("update bankaccount set amount = ? where accountno = ?");
                            pr.setInt(1, db_aamt);
                            pr.setInt(2,d);
                            pr.executeUpdate();
                            max = "amount successfully withdraw";

                            PreparedStatement pr1 = connection.prepareStatement("insert into minitransaction values(?,?,?,?,?)");
                            pr1.setInt(1, d);
                            pr1.setInt(2, amount);
                            pr1.setInt(3, 0);
                            pr1.setString(4, "");
                            pr1.setString(5, mydate);
                            pr1.executeUpdate();
                        }
                        else{
                            max = "invalid account ";
                        }
                    }
                }
                else
                    max="Invalid account no";


            }catch (Exception e){
                max = ""+e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(Withdrawal.this,max,Toast.LENGTH_LONG).show();

            super.onPostExecute(aVoid);
        }
    }
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

//    public void errr(){
//        if (e1.length()==0){
//            Log.e("key ", "ran");
//            e1.setError("Enter Account Number");
//            Log.e("key ", "rana");
//            valid = false;
//        }
//        else if (e2.length()==0){
//            Log.e("key ", "ranads");
//            e2.setError("Enter Amount ");
//            e2.setTextColor(Color.green(1));
//            Log.e("key ", "ranaddddddd");
//            valid = false;
//        }
//        else {
//            valid = true;
//        }
//    }
}
