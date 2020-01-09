package com.example.cctvnepal.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.Activities.SignIn;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.adapterClass.PurcahseHistoryApapterClass;
import com.example.cctvnepal.model.Orders;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PurchaseHistory extends Fragment {

    List<Orders> orderList;
    RecyclerView rvPurchaseHistory;
    RecyclerView.Adapter puchaseAdapter;

    LinearLayout emptyList;


    public PurchaseHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_purchase_history, container, false);


        orderList = new ArrayList<>();

        new LoadFromServer().execute();

        rvPurchaseHistory = v.findViewById(R.id.rvPurchseHistory);

        emptyList = v.findViewById(R.id.layout_emptyList);

        return v;
    }

    // loading the data into the recylerView
    public void populateRecylerView(List<Orders> orders){
        rvPurchaseHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPurchaseHistory.setHasFixedSize(true);
        puchaseAdapter =  new PurcahseHistoryApapterClass(orders,getContext());
        rvPurchaseHistory.setAdapter(puchaseAdapter);

    }



    // Asynctask to load the data in the background so that the UI thread won't be interrupted
    public class LoadFromServer extends AsyncTask<Nullable,Nullable,Nullable> {

        @Override
        protected Nullable doInBackground(Nullable... nullable) {
            loadListApi();
            return null;
        }
    }


    private void loadListApi() {


        String BASE_URL = BaseUrl.BASE_URL_ORDERS+"/search/findByCustomerEmail?email="+ SignIn.tempEmail;

        // creating a request queue for HTTP request
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                    JSONArray jsonArray = embedded.getJSONArray("orderses");
                    for(int i=0;i<jsonArray.length();i++) {
                        String json = jsonArray.getString(i);
                        Orders orders = gson.fromJson(json, Orders.class);
                        orderList.add(orders);
                    }

                } catch (Exception e) {
                    Log.d("Error: ", "onResponse: ------------JSON Prasing exception-----------"+e.getMessage());
                }


                // setting up recycler view after the list is populated
                if(orderList.isEmpty()){
                    showEmptyList();
                }else{
                    populateRecylerView(orderList);
                }
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


    private void showEmptyList(){
        emptyList.setVisibility(View.VISIBLE);
    }


}
