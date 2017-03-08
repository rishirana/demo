package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Admin_create_new extends AppCompatActivity {

    Button b1,b2;
    StringBuffer dis;
    int res = 0;
    String max;


    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new);

        b1 = (Button) findViewById(R.id.create_new);
        b2= (Button) findViewById(R.id.display);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add a = new add();
                a.execute();
            }
        });
    }

    private class add extends AsyncTask<Void,Void,Void>{

        String f,g,h,i,j,l,m,n;
        long acc_no;
        long def_acc = 30676170;
        @Override
        protected Void doInBackground(Void... voids) {
            dis = new StringBuffer();
            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement s = connection.createStatement();
                ResultSet ps= s.executeQuery("select * from bankaccount");
               if( ps.last()){

              acc_no=   ps.getInt(4);
                   Log.e("result",""+acc_no);
               }

                else {
                   acc_no = def_acc;
               }

                ResultSet ps1 = s.executeQuery("select * from userapplication");



                PreparedStatement pre = connection.prepareStatement("insert into bankaccount values(?,?,?,?,?,?,?,?)");
                PreparedStatement pre1 = connection.prepareStatement("delete from userapplication where emailid= ?");
                if(ps1.next()){

                    ps1.previous();
                    while (ps1.next()){
                       f = ps1.getString(1);
                        g = ps1.getString(2);
                        h = ps1.getString(3);
                        i = ps1.getString(4);
                        j = ps1.getString(5);
                        l = ps1.getString(6);
                        m = ps1.getString(7);
                        acc_no = acc_no+1;
                        pre.setString(1,f);
                        pre.setString(2,j);
                        pre.setString(3, l);
                        pre.setInt(4, (int) acc_no);
                        pre.setString(5, h);
                        pre.setString(6, i);
                        pre.setString(7,g);
                        pre.setString(8, m);
                       // pre.setString(9,mydate);
                        pre.executeUpdate();
                        pre1.setString(1, j);
                        pre1.executeUpdate();

                         dis.append("Name =" + f + "\n Mobile No =" + g + "\n" + "Gender =" + h + "\n" + "DOB =" + i + "\n" + "Email Id =" + j + "\n" + "");
                      res++;
                        MailSend m = new MailSend();
                        m.send(j,(int) acc_no,l,f,1);
                    }
                 max = " No of new application = "+res;
                    connection.close();
                }
                else {
                    dis.append("No New User Application");
                    max = "No New User";
                }




            }catch (Exception e ){
           max = ""+e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(Admin_create_new.this,max,Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }

    public void showinfo(View view){
        AlertDialog.Builder aa = new AlertDialog.Builder(this);
        aa.setTitle("Information");
        aa.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        aa.setMessage(dis);
        aa.setCancelable(true);
        aa.create().show();
    }

    //Date and time
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


}
