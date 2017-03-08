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
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class user_edit_new extends AppCompatActivity {

    EditText name,mobile,dob,email,pas;
    ListView l;
    String list[] = {"edit name", "edit email", "edit mobile", "date of birth","password"};
    String aa,max;
    String na,mo,db,eml,pas1;
    String nam,mob,emal,db1;
    String e_name="",e_mobile="",e_pass="",e_dob="",e_email="";
    int ab;
    String disp;

    private static final String url = "jdbc:mysql://10.0.2.2:3306/bankproject";
    private static final String user = "root";
    private static final String pass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_new);



        l = (ListView) findViewById(R.id.listView);
        ArrayAdapter ar = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        l.setAdapter(ar);
        l.setOnItemClickListener(new listv());

        Intent f = this.getIntent();
        Bundle bn = f.getExtras();
        ab = bn.getInt("edit");
       // ab= Integer.parseInt(aa);

    }

    private class listv implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0){
                dailg();
                Toast.makeText(user_edit_new.this,list[i],Toast.LENGTH_LONG).show();
            }
            else if(i==1){
                dailog1();
                Toast.makeText(user_edit_new.this,list[i],Toast.LENGTH_LONG).show();
            }
            else if(i==2){
                dai();
            }
            else if(i==3){
               dai1();

            }
            else if(i==4){
                dai2();
            }

        }
    }

    public void dailg(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Name");
        name = new EditText(this);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        b.setView(name);
        b.setPositiveButton("send request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e_name= name.getText().toString();
                Newedit ne = new Newedit();
                ne.execute();
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setCancelable(true);
        b.create().show();


    }

    public void dai(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("mobile");
        mobile = new EditText(this);
        mobile.setInputType(InputType.TYPE_CLASS_PHONE);
        b.setView(mobile);
        b.setPositiveButton("send request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e_mobile = mobile.getText().toString();
                Newedit ne = new Newedit();
                ne.execute();
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setCancelable(true);
        b.create().show();


    }

    public void dailog1(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("email");
        email = new EditText(this);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        b.setView(email);
        b.setPositiveButton("send request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e_email = email.getText().toString();
                Newedit ne = new Newedit();
                ne.execute();
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setCancelable(true);
        b.create().show();


    }

    public void dai1(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("dob");
        dob = new EditText(this);
        dob.setInputType(InputType.TYPE_CLASS_TEXT);
        b.setView(dob);
        b.setPositiveButton("send request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e_dob = dob.getText().toString();
                Newedit ne = new Newedit();
                ne.execute();
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setCancelable(true);
        b.create().show();


    }

    public void dai2(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("password");
        pas = new EditText(this);
        pas.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        b.setView(pas);
        b.setPositiveButton("send request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e_pass = pas.getText().toString();
                Newedit ne = new Newedit();
                ne.execute();
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setCancelable(true);
        b.create().show();


    }

    private class Newedit  extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement s = con.createStatement();
                ResultSet res=s.executeQuery("select * from bankaccount where accountno = "+ab);
                if(res.next())
                {
                    ResultSet res1=s.executeQuery("select * from editaccount  where accountno = "+ab);
                    if(res1.next())
                    {
                        if(!e_name.equals(""))
                        {
                            PreparedStatement pr1=con.prepareStatement("update editaccount set name=? where accountno=?") ;
                            pr1.setString(1,e_name);
                            pr1.setInt(2, ab);
                            pr1.executeUpdate();
                        }
                        if(!e_email.equals(""))
                        {
                            PreparedStatement pr2=con.prepareStatement("update editaccount set email=? where accountno=?") ;
                            pr2.setString(1,e_email);
                            pr2.setInt(2, ab);
                            pr2.executeUpdate();
                        }
                        if(!e_dob.equals(""))
                        {
                            PreparedStatement pr3=con.prepareStatement("update editaccount set dob=? where accountno=?") ;
                            pr3.setString(1,e_dob);
                            pr3.setInt(2, ab);
                            pr3.executeUpdate();
                        }
                        if(!e_mobile.equals(""))
                        {
                            PreparedStatement pr4=con.prepareStatement("update editaccount set mobile=? where accountno=?") ;
                            pr4.setString(1,e_mobile);
                            pr4.setInt(2, ab);
                            pr4.executeUpdate();
                        }
                        if(!e_pass.equals(""))
                        {
                            PreparedStatement pr5=con.prepareStatement("update editaccount set password=? where accountno=?") ;
                            pr5.setString(1,e_pass);
                            pr5.setInt(2,ab);
                            pr5.executeUpdate();
                        }
                    }

                    else
                    {
                        PreparedStatement pr = con.prepareStatement("insert into editaccount values(?,?,?,?,?,?)");
                        pr.setInt(1, ab);
                        pr.setString(2, e_name);
                        pr.setString(3, e_email);
                        pr.setString(4, e_pass);
                        pr.setString(5, e_mobile);
                        pr.setString(6, e_dob);
                        pr.executeUpdate();
                    }
                    disp="Request Sent Successfully";
                    e_name="";
                    e_email="";
                    e_dob="";
                    e_pass="";
                    e_mobile="";
                }
                else {

                    disp="Account does not exist";
                }



            }catch (Exception e){
            max = ""+e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(user_edit_new.this,max,Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }

    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//
//
//        MenuInflater inflater = getMenuInflater();
//
//        inflater.inflate(R.menu.menu_home, menu);
//
//        return true;
//
//    }
//
//
//
//
//
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                showMsg("Ok");
//                break;
//            case R.id.edit:
//                showMsg("edit");
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showMsg(String msg) {
//        Toast toast = Toast.makeText(user_edit_new.this, msg, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
//        toast.show();
//    }
//
//
//

}