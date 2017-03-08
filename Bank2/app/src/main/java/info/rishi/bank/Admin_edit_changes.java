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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Admin_edit_changes extends AppCompatActivity {

    Button b1,b2;
    String disp;
    StringBuffer show;
    int ac;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pswd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_changes);
        b1= (Button) findViewById(R.id.b1);
        b2= (Button) findViewById(R.id.b2);
        show=new StringBuffer();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Apply a=new Apply();
                a.execute();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert();
            }
        });

    }

    private class Apply extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {

                String name,email,mobile,dob,pass;
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url, user, pswd);
                Statement s=con.createStatement();
                ResultSet res=s.executeQuery("select * from editaccount");
                PreparedStatement pr=con.prepareStatement("delete from editaccount where accountno=?");
                if(res.next())
                {
                    res.previous();
                    while(res.next())
                    {
                        ac= res.getInt(1);
                        show.append("Account no :"+ac+"\n");
                        if(!res.getString(2).equals(""))
                        {
                            PreparedStatement pr1=con.prepareStatement("update bankaccount set name=? where accountno=?");
                            pr1.setString(1,res.getString(2));
                            pr1.setInt(2,ac);
                            show.append("New Name :" + res.getString(2) + "\n");
                            pr1.executeUpdate();
                        }
                        if(!res.getString(3).equals(""))
                        {
                            PreparedStatement pr1=con.prepareStatement("update bankaccount set mobileno=? where accountno=?");
                            pr1.setString(1,res.getString(3));
                            pr1.setInt(2, ac);
                            show.append("New mobile no :" + res.getString(3) + "\n");
                            pr1.executeUpdate();
                        }
                        if(!res.getString(4).equals(""))
                        {
                            PreparedStatement pr1=con.prepareStatement("update bankaccount set dob=? where accountno=?");
                            pr1.setString(1,res.getString(4));
                            show.append("New Date of birth :" + res.getString(4) + "\n");
                            pr1.setInt(2, ac);
                            pr1.executeUpdate();
                        }
                        if(!res.getString(5).equals(""))
                        {
                            PreparedStatement pr1=con.prepareStatement("update bankaccount set emailid=? where accountno=?");
                            pr1.setString(1,res.getString(5));
                            show.append("New Emaiil id :" + res.getString(5) + "\n");
                            pr1.setInt(2, ac);
                            pr1.executeUpdate();
                        }
                        if(!res.getString(6).equals(""))
                        {
                            PreparedStatement pr1=con.prepareStatement("update bankaccount set password=? where accountno=?");
                            pr1.setString(1,res.getString(6));
                            show.append("New password :" + res.getString(6) + "\n\n\n");
                            pr1.setInt(2,ac);
                            pr1.executeUpdate();
                        }


                    }

                    disp="Changes applied successfully";
                    pr.setInt(1,ac);
                    pr.executeUpdate();
                    Log.e("keu",""+ac);
                }

                else {
                    show.append("No new Changes ");
                    disp = "No applications for changes ";
                }

            }
            catch(Exception e)
            {
              disp = ""+e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(Admin_edit_changes.this,disp,Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }

    public void back(View v) {
        Intent i = new Intent(Admin_edit_changes.this, AdminInside.class);
        startActivity(i);
    }

    public void alert()
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setTitle("INFORMATION");
        b.setMessage(show);
        b.create().show();
    }

}
