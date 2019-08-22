package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.model.Customer;
import com.example.cctvnepal.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private Button btnRegister;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    // for sending the api back to server
    private Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etConfirmPassword);


        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();

                // validating the data
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Don't leave any field empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (validateEmail(email) && password.length()>7 && password.equals(passwordConfirm)) {
                        // saving file to the customer class
                        customer = new Customer(firstName, lastName, password, email);
                        // doing the database entry in the server
                        createNewUser();

                        // go back to the sign in activity
                        Intent intent = new Intent(getApplicationContext(), SignIn.class);
                        startActivity(intent);

                    }else{
                        if(!validateEmail(email)){
                            Toast.makeText(getApplicationContext(), "Please put a valid email", Toast.LENGTH_SHORT).show();
                        }
                        if(password.length()<=7){
                            Toast.makeText(getApplicationContext(), "Password must be at least 8 character long", Toast.LENGTH_SHORT).show();
                        }
                        if(password.length()>7 && !password.equals(passwordConfirm)){
                            Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }


        });


    }


    // sends data to the server
    public void createNewUser(){

        final String BASE_URL = BaseUrl.BASE_URL_CUSTOMER;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // sending the json file to the server
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("firstName", customer.getFirstName());
            postParams.put("lastName",customer.getLastName());
            postParams.put("email",customer.getEmail());
            postParams.put("password",customer.getPassword());
            Log.e("testing", "createNewUser: "+postParams.toString());
        }catch (Exception e){
            Log.d(e.getMessage(), "createNewUser: error while creating a new user");
        }

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, postParams, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               Log.e(response.toString(), "onResponse: " );
               Toast.makeText(getApplicationContext(),"Creating new user",Toast.LENGTH_SHORT).show();
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               String correct = " com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of";
               if((error.toString().length()) == (correct.length())){
                   Toast.makeText(getApplicationContext(),"Creating new user",Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getApplicationContext(), "Error:Can't  create new user", Toast.LENGTH_SHORT).show();
               }
           }
       });
        requestQueue.add(jsonObjectRequest);

    }






    boolean validateEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
