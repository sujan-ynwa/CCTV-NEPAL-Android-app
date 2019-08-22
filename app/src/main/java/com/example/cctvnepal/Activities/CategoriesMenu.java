package com.example.cctvnepal.Activities;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.adapterClass.CategoriesRecyclerViewAdapter;
import com.example.cctvnepal.model.Categories;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMenu extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;

    List<Categories> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_menu);

        // initializing the list
        categoriesList = new ArrayList<>();

        // calling the asynctask class to do the task in background
       new LoadFromServer().execute();

       // initialization
        recyclerView = findViewById(R.id.rvCategories);




    }

    // loading the data into the recylerView
    public void populateRecylerView(List<Categories> categories){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        myAdapter = new CategoriesRecyclerViewAdapter(this,categories);
        recyclerView.setAdapter(myAdapter);
    }


    // Asynctask to load the data in the background so that the UI thread won't be interrupted
    public class LoadFromServer extends AsyncTask<Nullable,Nullable,Nullable>{

        @Override
        protected Nullable doInBackground(Nullable... nullables) {
            loadListApi();
            return null;
        }
    }

    private void loadListApi() {
        final String BASE_URL = BaseUrl.BASE_URL_CATEGORIES;
        // final String BASE_URL ="http://10.0.2.2:8080/spring-crm-rest/api/customers";

        // creating a request ques for HTTP request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Setting HTTP GET request to retrieve the data from the SERVER
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,BASE_URL
                ,null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // creating gson object to convert JSON file
                Gson gson = new Gson();

                //gets the array response
                // now parsing the array into the value

                    try {
                        JSONObject embedded = response.getJSONObject("_embedded");
                        JSONArray jsonArray = embedded.getJSONArray("categorieses");
                        for(int i=0;i<jsonArray.length();i++) {
                            String json = jsonArray.getString(i);
                            Categories categories = gson.fromJson(json, Categories.class);
                            categoriesList.add(categories);

                        }

                    } catch (Exception e) {
                        Log.d(e.getMessage(), "onResponse: ------------JSON Prasing exception-----------");
                    }


                // setting up recycler view after the list is populated
                populateRecylerView(categoriesList);
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REST error",error.toString() );
            }
        }

        );

        requestQueue.add(objectRequest);

    }

}
