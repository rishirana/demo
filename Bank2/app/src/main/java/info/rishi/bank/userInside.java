package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class userInside extends AppCompatActivity {

    Button b;
    int flag=2;
    String sd,emm;
    StringBuffer display,dis;
    int a;
    String cl;
    int acnt,wid,dpo;
    String dptm,wdtm;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inside);
        b = (Button) findViewById(R.id.show);
      Intent i = this.getIntent();
        Bundle b1 = i.getExtras();
        a = b1.getInt("account");
        cl = b1.getString("email");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show s = new Show();

                s.execute();
                flag = 1;
            }
        });

    }

    public void deposit(View view) {
        Intent i = new Intent(userInside.this, User_deposit.class);
        i.putExtra("mail",cl);
        startActivity(i);
    }

    public void withdraw(View v) {
        Intent i = new Intent(userInside.this, Withdrawal.class);
        i.putExtra("mail",cl);
        startActivity(i);
    }
    public void edit(View v){
        Intent g = new Intent(userInside.this, user_edit_new.class);
        g.putExtra("edit",a);
       // g.putExtra("mail",cl);
        startActivity(g);
    }
    //back button
    public void bck(View view){
        startActivity(new Intent(userInside.this,UserLogin.class));
    }
    public void mini(View v){
        flag = 0;
        Show m = new Show();
        m.execute();
    }
    public void transfer(View view){
        Intent g = new Intent(userInside.this, Money_transfer.class);
        g.putExtra("account",a);
        startActivity(g);
    }



    private class Show extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            display = new StringBuffer();
            dis = new StringBuffer();
            int count=0;
            try {

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();
                ResultSet ps1 = s.executeQuery("select * from minitransaction where accountno = "+a);
                dis.append("Accountno:" +a+"\n\n");
                if(ps1.next()) {

                    ps1.last();
                     ps1.next();
                    while (ps1.previous()) {
                        acnt = ps1.getInt(1);
                        wid = ps1.getInt(2);
                        dpo = ps1.getInt(3);
                        dptm = ps1.getString(4);
                        wdtm = ps1.getString(5);
                        if (wid == 0) {

                            dis.append("Deposit:" + dpo + "on"  +dptm+ "\n\n");
                        }
                        else if (dpo==0){
                            dis.append("withdraw:" +wid+ "on" +wdtm+ "\n\n");
                        }
                        count++;
                        if(count>=5)
                            break;

                    }

                }
                ResultSet ps = s.executeQuery("select * from bankaccount where accountno = " + a);
                if (ps.next()) {

                    display.append("name= " + ps.getString(1) + "\n" + "emailid= " + ps.getString(2) + "\n" + "password = " + ps.getString(3) + "\n" + "Account no= " + ps.getInt(4) + "\n" + "Gender= " + ps.getString(5) + "\n" + "Dob = " + ps.getString(6) + "\n" + "Mobile No = " + ps.getString(7) + "\n" + "Amount= " + ps.getInt(8) + "\n");
                }


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
             if(flag==1) {
                 alert();
                 flag = 2;
             }
            if(flag==0) {
                mn();
                flag=2;
            }

            super.onPostExecute(aVoid);
        }

    }
    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(userInside.this);
        builder.setTitle("Information");
        builder.setMessage(display);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    public void mn(){
        AlertDialog.Builder builder = new AlertDialog.Builder(userInside.this);
        builder.setTitle("Transaction");
        builder.setMessage(dis);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    }
