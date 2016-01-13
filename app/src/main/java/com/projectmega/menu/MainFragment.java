package com.projectmega.menu;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    //Creating a List of superheroes
    private List<SubjectList> listSubjects;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;

    //Volley Request Queue
    private RequestQueue requestQueue;

    //student id to use in ?phpid=
    private String studentNumber;

    public String stringSubject;

    //textview
    public TextView subjectName;

    //SharedPreferences
    public SharedPreferences sharedPreferences;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
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
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        itemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL_LIST);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        subjectName = (TextView) v.findViewById(R.id.textViewSubject);


        //Initializing the subject list
        listSubjects = new ArrayList<>();

        requestQueue = VolleySingleton.getInstance().getRequestQueue();

        //Getting the Student number
        sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        studentNumber = sharedPreferences.getString(Config.STUDENT_NUMBER, "Not Available");

        //calling method to get data
        getData();

        //initializing our adapter
        adapter = new CardAdapter(listSubjects, getActivity().getApplicationContext());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //Determines kung anu ung naclick sa subjects

        final GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());


                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int itemposition = rv.getChildAdapterPosition(child);
                    //stringSubject = subjectName.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Config.SUBJECT, itemposition);
                    editor.apply();
                    Intent intent = new Intent(getActivity().getApplicationContext(), Exam.class);
                    startActivity(intent);
                    //Toast.makeText(getActivity().getApplicationContext(), "The Item Clicked is: " + rv.getChildAdapterPosition(child), Toast.LENGTH_SHORT).show();

                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    return  v;
    }


    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());
    }

    private JsonArrayRequest getDataFromServer() {
        //Initializing progressbar
        //final ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);

        //Displaying Progressbar
        //progressBar.setVisibility(View.VISIBLE);
        //setProgressBarIndeterminateVisibility(true);


        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL + studentNumber,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        listSubjects.clear();
                        parseData(response);
                        //Hiding the progressbar
                        //progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        //Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();
                        handleVolleyError(error);
                    }
                });
        //Returning the request
        return jsonArrayRequest;

    }
    //handles Error
    private void handleVolleyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(getActivity(),R.string.error_timeout,Toast.LENGTH_LONG).show();

        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getActivity(),R.string.error_auth_failure,Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(getActivity(),R.string.error_auth_failure,Toast.LENGTH_LONG).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(getActivity(),R.string.error_network,Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(getActivity(),R.string.error_parser,Toast.LENGTH_LONG).show();
        }
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the subject object
            SubjectList subject = new SubjectList();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                //Adding data to the subjectlist object
                subject.setSubjectName(json.getString(Config.TAG_SUBJECT));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the subject object to the list
            listSubjects.add(subject);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();

    }


}
