package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.adapterClass.ProductRecyclerViewAdapter;
import com.example.cctvnepal.model.Product;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductMenu extends AppCompatActivity {

    List<Product> productsList;
    RecyclerView rvProduct;
    RecyclerView.Adapter productAdapter;

    TextView tvProductTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        tvProductTitle = findViewById(R.id.tvProductTitle);

        // getting the data from another categories Activity
        Intent intent = getIntent();
        String title= intent.getStringExtra("Product Category");
        tvProductTitle.setText(title);
        rvProduct = findViewById(R.id.rvProductMenu);




    }


    // Asynctask to load the data in the background so that the UI thread won't be interrupted
    public class LoadFromServer extends AsyncTask<Nullable,Nullable,Nullable> {

        @Override
        protected Nullable doInBackground(Nullable... nullables) {
            loadListApi();
            return null;
        }
    }


    // loading the data into the recylerView
    public void populateRecylerView(List<Product> products){


        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setHasFixedSize(true);
        productAdapter =  new ProductRecyclerViewAdapter(products,this);
        rvProduct.setAdapter(productAdapter);

    }


    private void loadListApi() {
        final String BASE_URL = BaseUrl.BASE_URL_PRODUCTS;
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
                    JSONArray jsonArray = embedded.getJSONArray("productses");
                    for(int i=0;i<jsonArray.length();i++) {
                        String json = jsonArray.getString(i);
                        Product product = gson.fromJson(json, Product.class);
                        productsList.add(product);

                    }

                } catch (Exception e) {
                    Log.d(e.getMessage(), "onResponse: ------------JSON Prasing exception-----------");
                }


                // setting up recycler view after the list is populated
                populateRecylerView(productsList);
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
