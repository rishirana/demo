package info.rishi.bank;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class option_menu extends AppCompatActivity {

    EditText e1, e2, e3, e4, e5;
    Button b;
    int counter = 2;
    String a1, a2, a3;
    String ema;
    String c1, c2, c3, c4, c5, c6;
    int c7, c8;
    String max;
    String name, mob, dd, ee, pa;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);

        Rqst r = new Rqst();
        Log.e("execute", "do exexute");
        r.execute();
        counter = 1;
        Log.e("execute", "do");

        Intent i = this.getIntent();
        Bundle b1 = i.getExtras();
        ema = b1.getString("mail");
        e1 = (EditText) findViewById(R.id.n1);
        e2 = (EditText) findViewById(R.id.m1);
        e3 = (EditText) findViewById(R.id.dob1);
        e4 = (EditText) findViewById(R.id.email1);
        e5 = (EditText) findViewById(R.id.password1);
        b = (Button) findViewById(R.id.update);
        b.setEnabled(false);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = e1.getText().toString();
                mob = e2.getText().toString();
                dd = e3.getText().toString();
                ee = e4.getText().toString();
                pa = e5.getText().toString();


                Rqstd rq = new Rqstd();
                rq.execute();


            }
        });
    }

    //option Menu creator
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok_menu_item:
                b.setEnabled(true);
                e1.setEnabled(true);
                e2.setEnabled(true);
                e3.setEnabled(true);
                e4.setEnabled(true);
                e5.setEnabled(true);
                return true;
            case R.id.action_settings:
                showMsg("edit");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMsg(String msg) {
        Toast toast = Toast.makeText(option_menu.this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
        toast.show();
    }


    private class Rqst extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Log.e("doinback", "do in back");
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();


                ResultSet ps1 = s.executeQuery("select * from bankaccount where emailid = '" + ema + "'");
                if (ps1.next()) {
                    c1 = ps1.getString(1);
                    c2 = ps1.getString(2);
                    c3 = ps1.getString(3);
                    c7 = ps1.getInt(4);
                    c4 = ps1.getString(5);
                    c5 = ps1.getString(6);
                    c6 = ps1.getString(7);
                    c8 = ps1.getInt(8);


                }


            } catch (Exception e) {


            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            e1.setText(c1);
            e2.setText(c6);
            e3.setText(c5);
            e4.setText(c2);
            e5.setText(c3);
            e1.setEnabled(false);
            e2.setEnabled(false);
            e3.setEnabled(false);
            e4.setEnabled(false);
            e5.setEnabled(false);
            counter = 2;
            Log.e("execute", "dopost" + c1);
            super.onPostExecute(aVoid);
        }
    }

    private class Rqstd extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Log.e("doinback", "do in back");
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement pe = connection.prepareStatement("insert into  editaccount values(?,?,?,?,?)");
                pe.setString(1, name);
                pe.setString(2, mob);
                pe.setString(3, dd);
                pe.setString(4, ee);
                pe.setString(5, pa);
                max = "iserted";


            } catch (Exception e) {
                  max = ""+e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(option_menu.this,max,Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }
}