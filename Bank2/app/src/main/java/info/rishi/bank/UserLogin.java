package info.rishi.bank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserLogin extends AppCompatActivity {

    Validation vali;
    EditText e1, e2, e3;
    TextView t1;
    Button b1;
    String m, p;
    String em, pp;
    int aaa, ne = 2;
    CheckBox logincheck;
    String username, password;
    String send_pass, ai,send_name,max;
    private SharedPreferences shr;
    private SharedPreferences.Editor edt;
    public boolean valid = true;

    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //check box
//        logincheck = (CheckBox)findViewById(R.id.check);
//        shr = getSharedPreferences("admin", MODE_PRIVATE);
//        edt = shr.edit();
//        save = shr.getBoolean("savelog",false);
//        if(save==true){
//            e1.setText(shr.getString("username",""));
//            e2.setText(shr.getString("password",""));
//            logincheck.setChecked(true);
//        }

        vali = new Validation();
        //text view id
        t1 = (TextView) findViewById(R.id.reset);
        //edit text id
        e1 = (EditText) findViewById(R.id.me);
        e2 = (EditText) findViewById(R.id.pas);
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                m = e1.getText().toString();
                p = e2.getText().toString();
                err();
//                    if (logincheck.isChecked()) {
//                        edt.putBoolean("savelog", true);
//                        edt.putString("username", m);
//                        edt.putString("password", p);
//                    } else {
//                        edt.clear();
//                        edt.commit();
//                    }
                if (valid == true) {
                    Sam k = new Sam();
                    k.execute();
                }

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset();
            }
        });

    }

    public void newaccount(View v) {
        Intent i = new Intent(UserLogin.this, UserApplication.class);
        startActivity(i);
    }

    private class Sam extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();
                ResultSet ps = s.executeQuery("select * from bankaccount where emailid = '" + m + "'");
                if (ps.next()) {
                    em = ps.getString(2);
                    pp = ps.getString(3);
                    aaa = ps.getInt(4);

                }
                else{
                    em="";
                    pp="";

                }


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Log.e("get", "" + em);
            Log.e("get", "" + pp);

            if (m.equals(em) && pp.equals(p)) {
                successfull();
            } else if (em==""&&pp=="") {
               String lg = "Incorrect UserName And Password";
                unscss(lg);
            }
            super.onPostExecute(aVoid);
        }


    }


    private void unscss(String display) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(display);
        builder.setIcon(R.drawable.robot_error);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    private void successfull() {
        //Toast.makeText(this,"login successfull",Toast.LENGTH_LONG).show();
        Intent i = new Intent(UserLogin.this, userInside.class);
        i.putExtra("account", aaa);
        i.putExtra("email", em);
        startActivity(i);

    }

    public void Reset() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Enter Register Email id");
        e3 = new EditText(this);
        e3.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        b.setView(e3);
        b.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ai = e3.getText().toString();
                Forgot s = new Forgot();
                s.execute();
            }
        });
        b.setCancelable(false);
        b.create().show();
    }

    private void err() {
        if (!vali.isValidEmail(m) || e1.length() == 0) {
            e1.setError("Enter Valid Email Id");
            valid = false;
        } else if (!vali.isValidPassword(p)) {
            e2.setError("Enter Correct Password");
            valid = false;
        } else {
            valid = true;
        }
    }


    private class Forgot extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement p = con.prepareStatement("select * from bankaccount where emailid =?");
                p.setString(1, ai);
                ResultSet res = p.executeQuery();
                if (res.next()) {
                    send_pass = res.getString(6);
                    send_name = res.getString(1);
                } else {
                    send_pass = "";
                }
                max = "Password Sent to your Registered Email id";

            } catch (Exception e) {
                max = "" + e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!send_pass.equals("")) {
                MailSend mailSend = new MailSend();
                mailSend.send(ai, 0, send_pass,send_name,0);
                Toast.makeText(UserLogin.this, max, Toast.LENGTH_LONG).show();

            } else if (send_pass.equals("")) {
                String ss = "Not a Registered Email Id . Please Enter Register Email Id";
                unscss(ss);
            }

            super.onPostExecute(aVoid);
        }
    }
}