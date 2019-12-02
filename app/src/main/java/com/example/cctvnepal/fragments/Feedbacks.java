package com.example.cctvnepal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cctvnepal.Activities.SignIn;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.model.Feedback;

import org.json.JSONObject;


public class Feedbacks extends Fragment {




    private TextView tvCustomerEmail;
    private EditText etFeedbackTitle;
    private EditText etFeedbackDescription;
    private ImageButton imgBtnSend;

    // for sending the object to the server
   private Feedback feedback;

   private String customerEmail;


    public Feedbacks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        tvCustomerEmail = view.findViewById(R.id.tvCustomerEmail);
        etFeedbackTitle = view.findViewById(R.id.etFeedbackTitle);
        etFeedbackDescription = view.findViewById(R.id.etFeedbackDescription);
        imgBtnSend = view.findViewById(R.id.imgBtnSend);






        // setting up the email id
        // getting the customer email from the SignIn activity
        customerEmail = "From: "+SignIn.tempEmail;
        tvCustomerEmail.setText(customerEmail);



        imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etFeedbackTitle.getText().toString();
                String description = etFeedbackDescription.getText().toString();
                String tempEmail = SignIn.tempEmail;

                if(title.isEmpty() || description.isEmpty()){
                    Toast.makeText(getContext(), "Don't leave any field empty", Toast.LENGTH_SHORT).show();
                }else{
                    feedback = new Feedback(tempEmail,title,description);
                    //sending the data to the server
                    sendFeedbacks();
                    Toast.makeText(getContext(), "Thank you for your feedback!!", Toast.LENGTH_SHORT).show();
                    etFeedbackTitle.setText("");
                    etFeedbackDescription.setText("");
                }
            }
        });


        // Inflate the layout for this fragment
        return view;

    }



    public void sendFeedbacks(){
        final String BASE_URL = BaseUrl.BASE_URL_FEEDBACK;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // sending the json file to the server
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("customerEmail", feedback.getCustomerEmail());
            postParams.put("title",feedback.getTitle());
            postParams.put("feedback",feedback.getFeedback());
            Log.e("testing", "Sending feedback: "+postParams.toString());
        }catch (Exception e){
            Log.d(e.getMessage(), "Error connecting to the server");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, postParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(response.toString(), "onResponse: " );
                Toast.makeText(getContext(),"Sending Feedback",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String correct = " com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of";
                if((error.toString().length()) == (correct.length())){
                   // do nothing
                }else {
                    Toast.makeText(getContext(), "Error:Can't  create new user", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);


    }







}
