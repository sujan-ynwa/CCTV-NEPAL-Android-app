package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {


    // links for the UI component
   private TextView tvRegister;
   private EditText etEmail;
   private EditText etPassword;
   private Button btnLogin;

   // to Store user Typed values
   private String email;
   private String password;

   //to store jason Values
   private List<String> emailList;
   private List<String> passwordList;
   private List<String> firstnameList;
   private List<String> lastnameList;
   private List<String> phoneList;

   // to check validation of the Sign in form
   boolean validationComplete;


   // creating a static string variable so that it can be accessed by other class
   // this is for sending the data to the feedback section
    // see line no. 136
   public static String tempEmail="";
   public static String tempCustomerName="";
   public static String tempPhone="";

   // for finishing this activity on exit
    public static SignIn signInObj;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme); //this line is for selectin the theme for splash screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvRegister = findViewById(R.id.tvRegisterText);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnSignIn);

        signInObj = this;

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        // goes to Registration Form
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });


       // call doInBackground method of the AsyncTask which do all the task related to api
      new StartConnection().execute(); // call AsyncTask class


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                // checking if any fields are left empty
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all fields...",Toast.LENGTH_LONG).show();
                }else {
                    // validating the form
                    validateLogin(email,password);
                    if (validationComplete){
                        Toast.makeText(getApplicationContext(),"Sign in Success...",Toast.LENGTH_LONG).show();

                        // going to the menu after validating the sign in
                        Intent intent  = new Intent(getApplicationContext(), CategoriesMenu.class);
                       startActivity(intent);

                        etEmail.setText("");
                        etPassword.setText("");

                    } else{
                        Toast.makeText(getApplicationContext(),"Either Email or Password is incorrect :(",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // The form validation that will check if user typed Email and password is matching to the database or not;
    public void validateLogin(String email, String password) {
        for(int i=0;i<emailList.size();i++){
            if(emailList.get(i).equals(email) && passwordList.get(i).equals(password)){
                validationComplete = true;
                // storing the user's email id  in static variable so it can be accessed by other class
                tempEmail = email;
                tempCustomerName = firstnameList.get(i)+" "+lastnameList.get(i);
                tempPhone = phoneList.get(i);
                break;
            }else{
                validationComplete = false;
            }
        }

    }


    // connects to the api and stores the retrived JSON values in the respective list
    public void connectToApi(){
        // initialize lists
        emailList = new ArrayList<>();
        passwordList = new ArrayList<>();
        firstnameList = new ArrayList<>();
        lastnameList = new ArrayList<>();
        phoneList = new ArrayList<>();

        final String BASE_URL = BaseUrl.BASE_URL_CUSTOMER;


        // creating a request ques for HTTP request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Setting HTTP GET request to retrieve the data from the SERVER
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,BASE_URL
                ,null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // the customer object us inside "_embedded"
                    JSONObject embedded = response.getJSONObject("_embedded");
                    JSONArray jsonArray = embedded.getJSONArray("customers");
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject customer = jsonArray.getJSONObject(i);
                        emailList.add(customer.getString("email"));
                        passwordList.add(customer.getString("password"));
                       firstnameList.add(customer.getString("firstName"));
                       lastnameList.add(customer.getString("lastName"));
                       phoneList.add(customer.getString("contactNumber"));
                    }
                } catch (Exception e) {
                       e.printStackTrace();
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



    // AsyncTask to do the heavy stuff on the background....
   public class StartConnection extends AsyncTask<Nullable,Nullable,Nullable>{

       @Override
       protected Nullable doInBackground(Nullable... nullable) {
           // calls the function which will connect to the api and get the JSON data and Store them in a list.
          connectToApi();
          return null;
       }
   }

}
