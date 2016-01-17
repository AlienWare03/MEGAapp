package com.projectmega.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateAccount extends AppCompatActivity implements View.OnClickListener{

    private EditText Username;
    private EditText Password;
    private EditText Confirm;
    private EditText First;
    private EditText Middle;
    private EditText Last;
    private EditText Age;
    private EditText Email;
    private Button update;

    private String userData;
    private String passwordData;
    private String firstData;
    private String middleData;
    private String lastData;
    private String ageData;
    private String emailAdd;
    private String confirmData;


    //SharedPreferences
    public SharedPreferences sharedPreferences;

    public String studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        Username = (EditText) findViewById(R.id.editTextUserAcct);
        Password = (EditText) findViewById(R.id.editTextPasswordAcct);
        Confirm = (EditText) findViewById(R.id.editTextConfirmPassAcct);
        First = (EditText) findViewById(R.id.editTextFirstAcct);
        Middle = (EditText) findViewById(R.id.editTextMiddleAcct);
        Last = (EditText)findViewById(R.id.editTextLastAcct);
        Age = (EditText)findViewById(R.id.editTextAgeAcct);
        Email = (EditText) findViewById(R.id.editTextEmailAcct);
        update = (Button) findViewById(R.id.buttonUpdate);


        //Getting the Student number
        sharedPreferences = UpdateAccount.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        studentNumber = sharedPreferences.getString(Config.STUDENT_NUMBER, "Not Available");

        update.setOnClickListener(this);

    }

    private void Update() throws JSONException{
       userData = Username.getText().toString().trim();
        passwordData = Password.getText().toString().trim();
        firstData = First.getText().toString().trim();
        middleData = Middle.getText().toString().trim();
        lastData = Last.getText().toString().trim();
        ageData = Age.getText().toString().trim();
        confirmData = Confirm.getText().toString().trim();
        emailAdd = Email.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_URL + studentNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(Config.UPDATE_SUCCESS)){
                            Toast.makeText(UpdateAccount.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UpdateAccount.this,Navigation.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(UpdateAccount.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateAccount.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                //Adding parameter to request
                //params.put(Config.KEY_STUDENT,studentnum);
                params.put(Config.KEY_USER1,userData);
                params.put(Config.KEY_PASSWORD1,passwordData);
                params.put(Config.KEY_CONFIRM,confirmData);
                params.put(Config.KEY_FIRSTNAME, firstData);
                params.put(Config.KEY_LASTNAME,lastData);
                params.put(Config.KEY_MIDDLENAME,middleData);
                params.put(Config.KEY_EMAIL,emailAdd);
                params.put(Config.KEY_AGE,ageData);

                return params;
            }

        };
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        requestQueue.add(stringRequest);

    }


    @Override
    public void onClick(View v) {
        if (v== update){
            try{
                Update();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

    }
}
