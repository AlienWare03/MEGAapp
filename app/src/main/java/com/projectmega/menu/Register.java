package com.projectmega.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //Defining Views
    private EditText editTextStudent;
    private EditText editTextUser1;
    private EditText editTextPassword1;
    private EditText editTextConfirm;
    private EditText editTextFirst;
    private EditText editTextLast;
    private EditText editTextMiddle;
    private EditText editTextEmail;
    private EditText editTextAge;
    private EditText editTextSection;

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initializing views
        editTextStudent = (EditText)findViewById(R.id.editTextStudent);
        editTextUser1 = (EditText)findViewById(R.id.editTextUser1);
        editTextFirst = (EditText)findViewById(R.id.editTextFirst);
        editTextLast = (EditText)findViewById(R.id.editTextLast);
        editTextMiddle = (EditText)findViewById(R.id.editTextMiddle);
        editTextPassword1 = (EditText)findViewById(R.id.editTextPassword1);
        editTextConfirm = (EditText)findViewById(R.id.editTextConfirm);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextSection = (EditText)findViewById(R.id.editTextSection);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }
    private void register() throws JSONException{
        //Getting values from editText
        final String studentnum = editTextStudent.getText().toString().trim();
        final String username = editTextUser1.getText().toString().trim();
        final String password = editTextPassword1.getText().toString().trim();
        final String confirmpass = editTextConfirm.getText().toString().trim();
        final String firstname = editTextFirst.getText().toString().trim();
        final String lastname = editTextLast.getText().toString().trim();
        final String middlename = editTextMiddle.getText().toString().trim();
        final String emailadd = editTextEmail.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String section = editTextSection.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(Config.REGISTER_SUCCESS)){
                            Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Register.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);
                        //Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
             @Override
            protected Map<String,String> getParams(){
                 Map<String, String> params = new HashMap<>();
                 //Adding parameter to request
                params.put(Config.KEY_STUDENT,studentnum);
                params.put(Config.KEY_USER1,username);
                params.put(Config.KEY_PASSWORD1,password);
                params.put(Config.KEY_CONFIRM,confirmpass);
                params.put(Config.KEY_FIRSTNAME, firstname);
                params.put(Config.KEY_LASTNAME,lastname);
                params.put(Config.KEY_MIDDLENAME,middlename);
                params.put(Config.KEY_EMAIL,emailadd);
                params.put(Config.KEY_AGE,age);
                params.put(Config.KEY_SECTION,section);
                return params;
             }

        };
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        requestQueue.add(stringRequest);
    }
    //handles Error
    private void handleVolleyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(this,R.string.error_timeout,Toast.LENGTH_LONG).show();

        } else if (error instanceof AuthFailureError) {
            Toast.makeText(this,R.string.error_auth_failure,Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(this,R.string.error_auth_failure,Toast.LENGTH_LONG).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(this,R.string.error_network,Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(this,R.string.error_parser,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v){
        if (v== buttonRegister){
            try{
                register();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
