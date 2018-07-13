package com.example.nandini.mini;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yuva on 18-02-2018.
 */

public class RegisterActivity extends AppCompatActivity
{
    EditText rname,rmail,rpass,rrpass;
    TextView tv1;
    Button register;
    int flag=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        tv1=(TextView)findViewById(R.id.tv1);
        rname=(EditText)findViewById(R.id.rname);
        rmail=(EditText)findViewById(R.id.rmail);
        rpass=(EditText)findViewById(R.id.rpass);
        rrpass=(EditText)findViewById(R.id.rrpass);
        String name=rname.getText().toString().trim();
        register=(Button)findViewById(R.id.register);
        rrpass.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String pwd=rpass.getText().toString();
                String rpwd=rrpass.getText().toString();
                if(pwd.equals(rpwd))
                {
                    flag=1;
                    tv1.setText("password matched");
                    tv1.setTextColor(Color.parseColor("#75e900"));
                }
                else
                {
                    flag=0;
                    tv1.setText("password not matched");
                    tv1.setTextColor(Color.RED);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        rpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String pwd=rpass.getText().toString();
                String rpwd=rrpass.getText().toString();
                if(pwd.equals(rpwd))
                {
                    flag=1;
                    tv1.setText("password matched");
                    tv1.setTextColor(Color.parseColor("#75e900"));
                }
                else
                {
                    flag=0;
                    tv1.setText("password not matched");
                    tv1.setTextColor(Color.RED);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name=rname.getText().toString();
                String mail=rmail.getText().toString();
                String pass=rpass.getText().toString();
                DataBaseHandler dbh=new DataBaseHandler(RegisterActivity.this);
                SQLiteDatabase db=dbh.getWritableDatabase();
                String emailPattern = "^[A-Za-z][A-Za-z0-9]*([._-]?[A-Za-z0-9]+)@[A-Za-z].[A-Za-z.]{2,9}?.[A-Za-z]{2,5}$";
                String namePattern="^[a-z0-9_]{4,25}$";
                String qry="select * from user";
                Cursor c=db.rawQuery(qry,null);
                int count=c.getCount();
                c.close();//execSQL(qry);
                if (count==1)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Sorry, Only one USER per DEVICE");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else if(!name.matches(namePattern))
                {
                    rname.setError("name must be in between 4-25 charecters. No spaces allowed");
                    rname.requestFocus();
                }
                else if(!mail.matches(emailPattern))
                {
                    rmail.setError("enter valid mail");
                    rmail.requestFocus();
                }
                else if(pass.length()<8)
                {
                    rpass.setError("password must be atleast 8 charecters");
                    rpass.requestFocus();
                }
                else if(flag==0)
                {
                    rpass.setError("password not matched");
                    rpass.requestFocus();
                }
                else
                {
                    String insert="insert into user(name,email,pwd) values('"+name+"','"+mail+"','"+pass+"')";
                    db.execSQL(insert);
                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    Intent onregister=new Intent(RegisterActivity.this,ImagesActivity.class);
                    startActivity(onregister);
                }
            }
        });
    }
}
