package com.projectmega.menu;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    //Creating a List of accountinfo
    private List<AccountList> listAccounts;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;

    //Volley Request Queue
    private RequestQueue requestQueue;

    //student id to use in ?phpid=
    private String studentNumber;

    //SharedPreferences
    public SharedPreferences sharedPreferences;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipe_containerAccount);
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
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewAccount);
        recyclerView.setHasFixedSize(true);
        itemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL_LIST);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);

        //Initializing the subject list
        listAccounts = new ArrayList<>();

        requestQueue = VolleySingleton.getInstance().getRequestQueue();

        //Getting the Student number
        sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        studentNumber = sharedPreferences.getString(Config.STUDENT_NUMBER, "Not Available");

        //calling method to get data
        getData();

        //initializing our adapter
        adapter = new AccountAdapter(listAccounts, getActivity().getApplicationContext());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

     return v;
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.ACCOUNT_URL + studentNumber,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        listAccounts.clear();
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
            Toast.makeText(getActivity(), R.string.error_timeout, Toast.LENGTH_LONG).show();

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
            //Creating the account object
            AccountList account = new AccountList();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                //Adding data to the account object
                account.setUser(json.getString(Config.TAG_USER));
                account.setID(json.getString(Config.TAG_ID));
                account.setFirst(json.getString(Config.TAG_FIRSTNAME));
                account.setMiddle(json.getString(Config.TAG_MIDDLENAME));
                account.setLast(json.getString(Config.TAG_LASTNAME));
                account.setAge(json.getString(Config.TAG_AGE));
                account.setSection(json.getString(Config.TAG_SECTION));
                account.setEmailadd(json.getString(Config.TAG_EMAIL));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the account object to the list
            listAccounts.add(account);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();

    }
}
