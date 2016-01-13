package com.projectmega.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextStudentid;
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister1;
    private TextView linkSignup;
    public String studentnumber;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing views
        editTextStudentid = (EditText) findViewById(R.id.editTextStudentid);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister1 = (Button)findViewById(R.id.buttonRegister1);
        linkSignup = (TextView) findViewById(R.id.linkSignup);

        //linkSignup.setPaintFlags(linkSignup.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        //Adding click listener
        buttonLogin.setOnClickListener(this);
        buttonRegister1.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences= getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value from sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF,false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(MainActivity.this, Navigation.class);
            startActivity(intent);
        }
    }

    private void login(){

        //Getting values from edit texts
        final String studentid = editTextStudentid.getText().toString().trim();
        final String username = editTextUser.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(Config.LOGIN_SUCCESS)) {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USER_SHARED_PREF, username);
                            editor.putString(Config.STUDENT_NUMBER, studentid);
                            studentnumber = studentid;
                            //Saving values to editor
                            editor.commit();

                            //Starting Navigation activity
                            Intent intent = new Intent(MainActivity.this, Navigation.class);
                            startActivity(intent);
                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to Request
                params.put(Config.KEY_STUDENT1, studentid);
                params.put(Config.KEY_USER,username);
                params.put(Config.KEY_PASSWORD,password);


                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        requestQueue.add(stringRequest);
    }
    @Override
    public  void onBackPressed(){
        moveTaskToBack(true);
    }
    @Override
    public void onClick(View v) {
        //Calling the login function
       if (v == buttonLogin){
           login();
       }
       if (v == buttonRegister1){
           Intent intent = new Intent(MainActivity.this, Register.class);
           startActivity(intent);
       }
    }

}
