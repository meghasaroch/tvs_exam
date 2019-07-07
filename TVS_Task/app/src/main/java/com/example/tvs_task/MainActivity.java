package com.example.tvs_task;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = (EditText) (findViewById(R.id.et_username));
        et_password = (EditText) (findViewById(R.id.et_password));
    }


    public void loginAction(View view)
    {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if(username.equals("") || password.equals(""))
        {
            Snackbar.make(view,"All Fields are required",Snackbar.LENGTH_SHORT).show();
        }
        else if(username.equals("test") && password.equals("123456"))
        {
            Snackbar.make(view,"Login Successfully",Snackbar.LENGTH_SHORT).show();
            startActivity(new Intent(this,ListActivity.class));
        }
        else
        {
            Snackbar.make(view,"Username/Password is not correct",Snackbar.LENGTH_SHORT).show();
        }

    }
}
