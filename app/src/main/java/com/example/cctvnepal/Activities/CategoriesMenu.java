package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import android.content.DialogInterface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.adapterClass.CategoriesRecyclerViewAdapter;
import com.example.cctvnepal.fragments.AddToCart;
import com.example.cctvnepal.fragments.Feedbacks;
import com.example.cctvnepal.fragments.PurchaseHistory;
import com.example.cctvnepal.model.Categories;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;

    List<Categories> categoriesList;

    // for the navigation bar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout fragmentContainer;


    private boolean exitApp;


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

        // for back button
        exitApp = true;

        /* ----------------- Everything below this comment is for Navigation Menu----------------  */

        // check style.xml
        // also see string.xml file
        //adding the toolbar as the action bar
        // required to navigation bar/drawer
        fragmentContainer = findViewById(R.id.fragment_container);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);

        // this is required to the navigation bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        // we passed "this" as the argument,now we need to implement the "NavigationView.OnNavigationItemSelectedListener" Interface
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {

        Log.d("back Pressed", "onBackPressed: ------------"+exitApp);

        if(!exitApp){
            finish();
            startActivity(getIntent());
            Log.d("back Pressed 2", "onBackPressed: ------------"+exitApp);
        }else {

            // Create the object of
            // AlertDialog Builder class
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(CategoriesMenu.this);

            // Set the message show for the Alert time
            builder.setMessage("Do you want to exit ?");

            /*// Set Alert Title
            builder.setTitle("Alert !");*/

            // Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.

            builder
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    // When the user click yes button
                                    // then app will close
                                    SignIn.signInObj.finish();
                                    Log.d("Exit the app", "onClick: finishing the both activity");
                                    finish();

                                }
                            });

            // Set the Negative button with No name
            // OnClickListener method is use
            // of DialogInterface interface.
            builder
                    .setNegativeButton(
                            "No",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    // If user click no
                                    // then dialog box is canceled.
                                    dialog.cancel();
                                }
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }

    }


    // for the making the menu items clickable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_categories:
                fragmentContainer.setVisibility(View.GONE);
                break;

            case R.id.nav_cart:
                Log.d("Menu item pressed", "onNavigationItemSelected:  Add to cart clicked");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddToCart()).commit();
                fragmentContainer.setVisibility(View.VISIBLE);
                exitApp= false;
                break;

            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PurchaseHistory()).commit();
                fragmentContainer.setVisibility(View.VISIBLE);
                exitApp= false;
                break;

            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Feedbacks()).commit();
                fragmentContainer.setVisibility(View.VISIBLE);
                exitApp= false;
                break;

            case R.id.nav_logout:
                Intent intent = new Intent(this,SignIn.class);
                startActivity(intent);

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
