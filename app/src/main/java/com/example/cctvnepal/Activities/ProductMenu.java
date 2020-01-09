package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.R;
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

    String code;

    // to show the empty list
    LinearLayout emptyList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);



        productsList = new ArrayList<>();

        // calling the asynctask class to do the task in background
        new LoadFromServer().execute();

        // getting the data from another categories Activity
        Intent intent = getIntent();
        String title= intent.getStringExtra("Category Name");
        code = intent.getStringExtra("Category code");
       // productListUrl = intent.getStringExtra("products Link");


        // setting up the Category titile of the products
        setTitle(title);

        // recycler view
        rvProduct = findViewById(R.id.rvProductMenu);

        // referencing to the empty list layout
        emptyList =findViewById(R.id.layout_emptyList);

    }



    // Asynctask to load the data in the background so that the UI thread won't be interrupted
    public class LoadFromServer extends AsyncTask<Nullable,Nullable,Nullable> {

        @Override
        protected Nullable doInBackground(Nullable... nullable) {
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


    private void showEmptyList(){
        emptyList.setVisibility(View.VISIBLE);
    }


    private void loadListApi() {
        Intent intent = getIntent();

         String BASE_URL =intent.getStringExtra("products Link");;

        // creating a request queue for HTTP request
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
                    Log.d("Error: ", "onResponse: ------------JSON Prasing exception-----------"+e.getMessage());
                }


                // setting up recycler view after the list is populated
                if(productsList.isEmpty()){
                    showEmptyList();
                }else{
                    populateRecylerView(productsList);
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

}
