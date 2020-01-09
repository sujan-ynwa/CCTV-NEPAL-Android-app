package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.model.Cart;

import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity {

    private TextView tvProductName;
    private ImageView ivProductImage;
    private TextView tvPrice;
    private TextView tvCompanyName;

    // to store the values
    String name;
    double price;
    String description;
    String companyName;
    String warranty ;
    String image_url;


    // for description
    private TextView tvDescriptionContent;
    private ImageView ivDropDownDescription;

    // for warranty
    private TextView tvWarrantyContent;
    private ImageView ivDropDownWarranty;

    // add to cart button
    private Button btnAddtoCart;


    /* for animation */
    private Animation animationUp;
    private Animation animationDown;

    // for price, if nothing is given
    double defaultValue;

    // for add to cart
    Cart cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        defaultValue = 1;

        // getting the data sent from another activity through intent
        Intent intent = getIntent();
         name = intent.getStringExtra("Product name");
        price = intent.getDoubleExtra("price",defaultValue);
        description = intent.getStringExtra("details");
         companyName = intent.getStringExtra("companyName");
         warranty = intent.getStringExtra("warranty");
         image_url = intent.getStringExtra("image");


        //initializing the views
        tvProductName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        btnAddtoCart = findViewById(R.id.btnAddToCart);

        //for description
        tvDescriptionContent = findViewById(R.id.tvDescriptionContent);
        ivDropDownDescription = findViewById(R.id.ivdescriptionDropDown);

        //for warranty
        tvWarrantyContent = findViewById(R.id.tvWarrantyContent);
        ivDropDownWarranty = findViewById(R.id.ivWarrantyDropDown);

        // for animation
        animationUp  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);


        // setting up the view with data
        String tempPrice = "Rs: "+String.valueOf(price);
        tvPrice.setText(tempPrice);
        tvProductName.setText(name);
        tvDescriptionContent.setText(description);
        tvWarrantyContent.setText(warranty);
        tvCompanyName.setText(companyName);
        Glide.with(getApplicationContext()).load(BaseUrl.IMAGE_BASE_URL+image_url).into(ivProductImage);


        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean itemExists = false;
                if (CategoriesMenu.cartItems.size() != 0) {
                    for (int i = 0; i < CategoriesMenu.cartItems.size(); i++) {
                        if (CategoriesMenu.cartItems.get(i).equals(name)) {
                            itemExists = true;
                            Toast.makeText(getApplicationContext(), "Already added to cart", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else {
                    itemExists = true;
                    CategoriesMenu.cartItems.add(name);
                    addTOCart();
                    Toast.makeText(getApplicationContext(),"Product added to cart",Toast.LENGTH_SHORT).show();

                }
                if (!itemExists && CategoriesMenu.cartItems.size()!=0) {
                    Toast.makeText(getApplicationContext(),"Product added to cart",Toast.LENGTH_SHORT).show();
                    CategoriesMenu.cartItems.add(name);
                    addTOCart();
                }


            }
        });

    }

    public void showDescription(View view) {
        if(tvDescriptionContent.isShown()){
            // to stop warranty content from displaying
            tvWarrantyContent.clearAnimation();

            tvDescriptionContent.startAnimation(animationUp);
            tvDescriptionContent.setVisibility(View.GONE);
            ivDropDownDescription.setImageResource(R.drawable.drop_down_arrow_icon);
        }else{
            // to stop warranty content from displaying
            tvWarrantyContent.clearAnimation();

            tvDescriptionContent.setVisibility(View.VISIBLE);
            tvDescriptionContent.startAnimation(animationDown);
            ivDropDownDescription.setImageResource(R.drawable.ic_arrow_drop_up);
        }
    }

    public void showWarranty(View view) {
        if(tvWarrantyContent.isShown()){
            // to stop description content from displaying
            tvDescriptionContent.clearAnimation();

            tvWarrantyContent.startAnimation(animationUp);
            tvWarrantyContent.setVisibility(View.GONE);
            ivDropDownWarranty.setImageResource(R.drawable.drop_down_arrow_icon);
        }else{
            // to stop description content from displaying
            tvDescriptionContent.clearAnimation();
            tvWarrantyContent.setVisibility(View.VISIBLE);
            tvWarrantyContent.startAnimation(animationDown);
            ivDropDownWarranty.setImageResource(R.drawable.ic_arrow_drop_up);
        }
    }



    public void addTOCart(){
        String email = SignIn.tempEmail;
        cart = new Cart(email,companyName,name,price,1,image_url);
        final String BASE_URL = BaseUrl.BASE_URL_CART;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // sending the json file to the server
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("productName", cart.getProductName());
            postParams.put("email",cart.getEmail());
            postParams.put("price",cart.getPrice());
            postParams.put("companyName",cart.getCompanyName());
            postParams.put("imagePath",cart.getImagePath());
            postParams.put("quantity",cart.getQuantity());
            Log.e("testing", "adding to cart: "+postParams.toString());
        }catch (Exception e){
            Log.d(e.getMessage(), "addingToCart: error while adding to cart");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, postParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(response.toString(), "onResponse: " );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String correct = " com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of";
                if((error.toString().length()) == (correct.length())){
               /*     Toast.makeText(getApplicationContext(),"Product added to cart",Toast.LENGTH_SHORT).show();*/
                }else {
                    Toast.makeText(getApplicationContext(), "Error:Can't add to cart!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
