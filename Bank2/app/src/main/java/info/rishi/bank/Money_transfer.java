package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

public class Money_transfer extends AppCompatActivity {

    Validation validation;
    String u,disp,ab,bb,cc;
    int account,fr_acc,to_acc,amount,flag;
    EditText ed1,ed2,ed3;
    Button b;
    public boolean valid = true;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pswd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        Intent i=this.getIntent();
        Bundle bnd=i.getExtras();
        account=bnd.getInt("account");
        ed1= (EditText) findViewById(R.id.ed1);
        ed2=(EditText) findViewById(R.id.ed2);
        ed3=(EditText) findViewById(R.id.ed3);
        b=(Button) findViewById(R.id.b1);
         validation = new Validation();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean A,B,F;

                 ab = ed1.getText().toString();
                A=validation.isValidAccount(ab);
                if(A==false){
                    Log.e("key","A value");
                    ed1.setError("Enter valid Account NO.");
                }
                else {
                    Log.e("key","A else");
                    fr_acc = Integer.parseInt(ab);
                }
                bb = ed2.getText().toString();
                B = validation.isValidAccount(bb);
                if (B==false){
                    Log.e("key","B value");
                    ed2.setError("Enter Valid Destination Account No");
                }
                else {
                    Log.e("key","B else");
                    to_acc = Integer.parseInt(bb);
                }
                    cc = ed3.getText().toString();
                     F = validation.isValidAccount(cc);
                if (F==false){
                    Log.e("key","C value");
                    ed3.setError("Enter Amount");
                }
                else {
                    Log.e("key","C else");
                    amount = Integer.parseInt(cc);
                }
            if (A==true&&B==true&&F==true) {

                if (account == fr_acc) {
                    alertconfirm();
                } else {
                    disp = "Invalid Account No";
                    alertunsuccess();
                }
            }
            }
        });


    }

    private void alertunsuccess()
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setIcon(R.drawable.robot_error);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setTitle("TRANSACTION ERROR");
        b.setMessage(disp);
        b.create().show();
    }

    private void alertconfirm()
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Transfer t = new Transfer();
                t.execute();
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setTitle("Alert");
        b.setMessage("Are You Sure");
        b.create().show();
    }

    private class Transfer extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids)
        {
            try {
                int db_amt,dest_amt;
                flag=0;
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url, user, pswd);
                Statement s = con.createStatement();
                ResultSet res=s.executeQuery("select * from bankaccount where accountno = "+fr_acc);
                if(res.next())
                {
                    db_amt=res.getInt(8);
                    if(amount>db_amt)
                    {
                        throw new CustomException();
                    }
                    else
                    {
                        ResultSet r=s.executeQuery("select * from bankaccount where accountno = "+to_acc);

                        if(fr_acc==to_acc){
                      flag = 1;
                            disp = " Sorry, Account no can't be same";
                        }
                        else {
                            if (r.next()) {

                                db_amt = db_amt - amount;
                                PreparedStatement pr = con.prepareStatement("update bankaccount set amount=? where accountno=?");
                                pr.setInt(1, db_amt);
                                pr.setInt(2, fr_acc);
                                pr.executeUpdate();
                                dest_amt = r.getInt(8);
                                dest_amt = dest_amt + amount;
                                PreparedStatement p = con.prepareStatement("update bankaccount set amount=? where accountno=?");
                                p.setInt(1, dest_amt);
                                p.setInt(2, Integer.parseInt(bb));
                                p.executeUpdate();

                            } else {
                                flag = 1;
                                disp = "Invalid destination account no";

                            }
                        }
                    }


                }
                else
                {
                    flag=1;
                    disp="Invalid Account no";
                }


            }
            catch(Exception e)
            {
                disp=""+e;
                flag=1;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(flag==0)
                Toast.makeText(Money_transfer.this, "Amount Tranferred Successfully", Toast.LENGTH_LONG).show();
            else if(flag==1)
                alertunsuccess();
            super.onPostExecute(aVoid);
        }
    }
    public void back(View v)
    {
        Intent i=new Intent(Money_transfer.this,userInside.class);
        i.putExtra("accountno", account);
        startActivity(i);
    }
//    public void errr(){
//        if (ed1.length()==0){
//            Log.e("key ", "ran");
//            ed1.setError("Enter Source Account Number");
//            Log.e("key ", "rana");
//            valid = false;
//        }
//        else if (ed2.length()==0){
//            Log.e("key ", "ranads");
//            ed2.setError("Enter Destination Account No. ");
//            Log.e("key ", "ranaddddddd");
//            valid = false;
//        }
//        else if (ed3.length()==0){
//            ed3.setError("Enter Amount");
//            valid = false;
//        }
//        else {
//            valid = true;
//        }
//
//    }


}


