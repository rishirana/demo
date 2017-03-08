package info.rishi.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    EditText e3;
    String aid;
    Button c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }
    public void admin1(View v){
        Intent g = new Intent(HomeActivity.this, Adminlogin.class);
                   startActivity(g);
    }
    public void userlogin1(View v){
        Intent i = new Intent(HomeActivity.this,UserLogin.class);
        startActivity(i);
    }
//    public void logins(){
//        AlertDialog.Builder b = new AlertDialog.Builder(this);
//        b.setTitle(" secure password");
//        e3 = new EditText(this);
//        e3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        b.setView(e3);
//        b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                aid = e3.getText().toString();
//                if (aid.equals("rana@123")) {
//                    Intent g = new Intent(HomeActivity.this, Adminlogin.class);
//                    startActivity(g);
//                }
//                else
//                    {
//                        loginunseccess();
//                    }
//            }
//        });
//    }
//    private void loginunseccess() {
//        AlertDialog.Builder di = new AlertDialog.Builder(this);
//
//        di.setMessage("incorrect secure password ");
//        di.setTitle("Error");
//        di.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//            }
//
//        });
//        di.setCancelable(true);

 //        di.create().show();
//    }

    //on Back Pressed


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


}
