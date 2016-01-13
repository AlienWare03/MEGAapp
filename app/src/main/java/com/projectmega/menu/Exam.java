package com.projectmega.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Exam extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    //Creating a List of superheroes
    private List<ExamsList> listExams;
    
    //Crating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;

    //Volley Request Queue
    private RequestQueue requestQueue;

    //declaring pos as private
    private int pos;
    private String posKey = "&pos=";

    //declaring studentnum
    private String studentNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        SharedPreferences sharedPreferences = Exam.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        pos = sharedPreferences.getInt(Config.SUBJECT, 0);

        //Getting the Student number
        studentNumber = sharedPreferences.getString(Config.STUDENT_NUMBER, "Not Available");
        
        //examSubject.setText(String.valueOf(pos));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_containerExam);
        swipeContainer.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getData();
                swipeContainer.setRefreshing(false);
            }


        });

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewExam);
        recyclerView.setHasFixedSize(true);
        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);


        //Initializing the Exams list
        listExams = new ArrayList<>();

        requestQueue = VolleySingleton.getInstance().getRequestQueue();

        //calling method to get data
        getData();

        //initializing our adapter
        adapter = new ExamAdapter(listExams, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

    //This method will get data from the web api
    private void getData() {

        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(pos));

    }

    private JsonArrayRequest getDataFromServer(int pos) {
        //Initializing progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);


        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.SCORE_URL + studentNumber + posKey + String.valueOf(pos),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        listExams.clear();
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        //Toast.makeText(Exam.this, error.toString().trim(), Toast.LENGTH_LONG).show();
                        handleVolleyError(error);
                    }
                });
        //Returning the request
        return jsonArrayRequest;

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

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the exam object
            ExamsList exam = new ExamsList();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                //Adding data to the exam object
                exam.setSubjectTitle(json.getString(Config.TAG_SUBJECT_TITLE));
                exam.setExamName(json.getString(Config.TAG_EXAM_NAME));
                exam.setExamScore(json.getString(Config.TAG_SCORE));
                exam.setExamItems(json.getString(Config.TAG_ITEMS));
                exam.setExamPercent(json.getString(Config.TAG_PERCENT));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the exam object to the list
            listExams.add(exam);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();

    }

    //logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();
                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                        //Putting blank value to email
                        editor.putString(Config.USER_SHARED_PREF, "");
                        //Saving the sharedpreferences
                        editor.commit();
                        //Starting login activity
                        Intent intent = new Intent(Exam.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }



}
