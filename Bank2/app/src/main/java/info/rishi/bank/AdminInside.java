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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminInside extends AppCompatActivity {

    Button b,b1;
    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";
    String nam,emai,pas,gen,db,mob;
    int amt,acc;
    StringBuffer str;
    String dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inside);
        b = (Button) findViewById(R.id.b1);
        b1 = (Button) findViewById(R.id.b5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminInside.this, Admin_create_new.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminInside.this,AdminShow.class);
                startActivity(i);
//                Show s = new Show();
//                s.execute();
            }
        });
    }

   public void editacou(View view){
       Intent i = new Intent(AdminInside.this,Admin_edit_changes.class);
       startActivity(i);
   }
    public void delete(View view){
        Intent i = new Intent(AdminInside.this,DeleteAccount.class);
        startActivity(i);
    }

//    private class Show extends AsyncTask<Void,Void,Void> {
//        @Override
//
//        protected Void doInBackground(Void... voids) {
//            str = new StringBuffer();
//            try {
//                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                Connection connection = DriverManager.getConnection(url, user, pass);
//                Statement s = connection.createStatement();
//                ResultSet ps = s.executeQuery("select * from bankaccount ");
//                if (ps.next()){
//                    ps.previous();
//                    while (ps.next()){
//
//                     str.append("name= " + ps.getString(1) + "\n" + "emailid= " + ps.getString(2) + "\n" + "password = " + ps.getString(3) + "\n" + "Account no= " + ps.getInt(4) + "\n" + "Gender= " + ps.getString(5) + "\n" + "Dob = " + ps.getString(6) + "\n" + "Mobile No = " + ps.getString(7) + "\n" + "Amount= " + ps.getInt(8) + "\n\n\n");
//                    }
//                }
//                else {
//
//                  dis = "No user ";
//                }
//            }catch (Exception e){
//               dis = ""+e;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            alert();
//            Toast.makeText(AdminInside.this,dis,Toast.LENGTH_LONG).show();
//
//            super.onPostExecute(aVoid);
//        }
//
//    }
//    public void alert(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(AdminInside.this);
//        builder.setTitle("Information");
//        builder.setMessage(str);
//        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.setCancelable(true);
//        builder.create().show();
//    }

}
