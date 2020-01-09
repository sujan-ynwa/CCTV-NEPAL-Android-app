package com.example.cctvnepal.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.Activities.CategoriesMenu;
import com.example.cctvnepal.Activities.SignIn;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.adapterClass.AddToCartRecyclerViewAdapter;
import com.example.cctvnepal.model.Cart;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddToCart extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter addTocartAdapter;

    // to show the empty list
    LinearLayout emptyList;

    // for grand total
    Button btnCheckOut;
    public static TextView tvTotal;

    List<Cart> cartList;

    public AddToCart(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cartList = new ArrayList<>();

        // calling the asynctask class to do the task in background
        new LoadFromServer().execute();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        recyclerView = view.findViewById(R.id.rvAddToCart);
        // referencing to the empty list layout
        emptyList = view.findViewById(R.id.layout_emptyList);



        //referring to the total
        tvTotal = view.findViewById(R.id.tvTotal);
        btnCheckOut = view.findViewById(R.id.btnCheckout);


        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendOrderData();
                    emptyCart();
                    refreshFragment();
               // Toast.makeText(getContext(),"checking out",Toast.LENGTH_SHORT).show();


            }
        });

        return view;
    }





    public void getData(){
        final String BASE_URL = BaseUrl.BASE_URL_CART+"/search/findByEmail?email="+SignIn.tempEmail;

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
                    JSONArray jsonArray = embedded.getJSONArray("carts");
                    for(int i=0;i<jsonArray.length();i++) {
                        String json = jsonArray.getString(i);
                        Cart cart = gson.fromJson(json, Cart.class);
                        cartList.add(cart);
                    }

                } catch (Exception e) {
                    Log.d("Error: ", "onResponse: ------------JSON Prasing exception-----------"+e.getMessage());
                }

                // setting up recycler view after the list is populated
                if(cartList.isEmpty()){
                    showEmptyList();
                }else {
                    populateRecylerView(cartList);
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

    private void populateRecylerView(List<Cart> cartList) {
        Log.e("Getting data", "populating the recyclerView" );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addTocartAdapter = new AddToCartRecyclerViewAdapter(getContext(),cartList);
        recyclerView.setAdapter(addTocartAdapter);
        addTocartAdapter.notifyDataSetChanged();
    }

    private void showEmptyList(){
        emptyList.setVisibility(View.VISIBLE);
    }
    // Asynctask to load the data in the background so that the UI thread won't be interrupted
    public class LoadFromServer extends AsyncTask<Nullable,Nullable,Nullable> {

        @Override
        protected Nullable doInBackground(Nullable... nullables) {
            getData();
            return null;
        }
    }


    public void sendOrderData(){
        String customerName[] = new String[AddToCartRecyclerViewAdapter.cartList.size()];
        String productName[]=new String[AddToCartRecyclerViewAdapter.cartList.size()];
        String companyName[]=new String[AddToCartRecyclerViewAdapter.cartList.size()];
        double price[]=new double[AddToCartRecyclerViewAdapter.cartList.size()];
        int quantity[]=new int[AddToCartRecyclerViewAdapter.cartList.size()];

        String contactPhone = SignIn.tempPhone;
        String contactName =SignIn.tempCustomerName;

        for (int i = 0; i < AddToCartRecyclerViewAdapter.cartList.size(); i++) {
            customerName[i] = AddToCartRecyclerViewAdapter.cartList.get(i).getEmail();
            productName[i] = AddToCartRecyclerViewAdapter.cartList.get(i).getProductName();
            companyName[i] = AddToCartRecyclerViewAdapter.cartList.get(i).getCompanyName();
            price[i] = AddToCartRecyclerViewAdapter.cartList.get(i).getPrice();
            quantity[i] = AddToCartRecyclerViewAdapter.cartList.get(i).getQuantity();

            Log.e("Quantity", "sendOrderData: "+quantity[i]);

        }

        for(int j=0;j<AddToCartRecyclerViewAdapter.cartList.size();j++) {
            final String BASE_URL = BaseUrl.BASE_URL_ORDERS;
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            // sending the json file to the server
            JSONObject postParams = new JSONObject();
            Gson gson = new Gson();
            try {
                postParams.put("customerEmail", customerName[j]);
                postParams.put("productName", productName[j]);
                postParams.put("companyName", companyName[j]);
                Log.e("Company Name", "sendOrderData: "+companyName[j]);
                postParams.put("price", price[j]);
                postParams.put("quantity", quantity[j]);
                postParams.put("purchaseDate", getTodayDate());
                postParams.put("contactNumber",contactPhone);
                postParams.put("contactName",contactName);

                Log.e("prices", "sendOrderData: " + Arrays.toString(price));
            } catch (Exception e) {
                Log.d(e.getMessage(), "checkout: error while checking out");
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(response.toString(), "onResponse: ");
                    Toast.makeText(getContext(), "Creating new user", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String correct = " com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of";
                    if ((error.toString().length()) == (correct.length())) {
                        Toast.makeText(getContext(), "checking out", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error:Can't  create new user", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    // for sending the date to the server
    public String getTodayDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }

    // to empty the cart after customer checks out
    public void emptyCart(){
        cartList.clear();
        AddToCartRecyclerViewAdapter.cartList.clear();
        String BASE_URL = BaseUrl.BASE_URL_CART+"/search/deleteByEmail?email="+ SignIn.tempEmail;

        // creating a request queue for HTTP request
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest  = new StringRequest(Request.Method.GET, BASE_URL, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(error.getMessage(), "onErrorResponse: "+error );
                    }
                });

        requestQueue.add(stringRequest);
    }


    // to refresh fragment after
    public void refreshFragment(){
        Intent intent = new Intent(getContext(), CategoriesMenu.class);
        startActivity(intent);
    }

}



