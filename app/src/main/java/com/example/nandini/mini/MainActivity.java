package com.example.nandini.mini;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.*;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    EditText lmail,lpass;
    Button lsubmit;
    TextView lregister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lsubmit=(Button)findViewById(R.id.lsubmit);
        lregister=(TextView)findViewById(R.id.lregister);
        lmail=(EditText)findViewById(R.id.lmail);
        lpass=(EditText)findViewById(R.id.lpass);
        lsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email=lmail.getText().toString();
                String pass=lpass.getText().toString();
                String emailPattern = "^[A-Za-z][A-Za-z0-9]*([._-]?[A-Za-z0-9]+)@[A-Za-z].[A-Za-z.]{0,9}?.[A-Za-z]{0,5}$";
                if(!email.matches(emailPattern) && email.length()<4)
                {
                    lmail.setError("enter valid email");
                    lmail.requestFocus();
                }
                else if (pass.length() < 8)
                {
                    lpass.setError("password must have atleast 8 charecters");
                    lpass.requestFocus();
                }
                else
                {
                    DataBaseHandler dbh=new DataBaseHandler(MainActivity.this);
                    SQLiteDatabase db=dbh.getReadableDatabase();
                    //to find whether user is registered or not
                    String query="select * from user where email='"+email+"' and pwd='"+pass+"'" ;
                    Cursor c=db.rawQuery(query,null);
                    int count=c.getCount();
                    c.close();

                    if(count==1)
                    {
                        Intent onsubmit=new Intent(MainActivity.this,ImagesActivity.class);
                        startActivity(onsubmit);
                    }
                    else
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("invalid UserName or Password");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        lmail.setText("");
                                        lpass.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });
        lregister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    int doubleBackToExitPressed = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }
}
