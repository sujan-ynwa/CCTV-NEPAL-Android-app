package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;

public class ProductDetails extends AppCompatActivity {

    private TextView tvProductName;
    private ImageView ivProductImage;
    private TextView tvPrice;
    private TextView tvCompanyName;

    // for description
    private TextView tvDescriptionContent;
    private ImageView ivDropDownDescription;

    // for warranty
    private TextView tvWarrantyContent;
    private ImageView ivDropDownWarranty;


    /* for animation */
    private Animation animationUp;
    private Animation animationDown;

    // for price, if nothing is given
    double defaultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        defaultValue = 0;

        // getting the data sent from another activity through intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("Product name");
       double price = intent.getDoubleExtra("price",defaultValue);
        String description = intent.getStringExtra("details");
        String companyName = intent.getStringExtra("companyName");
        String warranty = intent.getStringExtra("warranty");
        String image_url = BaseUrl.IMAGE_BASE_URL+intent.getStringExtra("image");


        //initializing the views
        tvProductName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        tvCompanyName = findViewById(R.id.tvCompanyName);

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
        tvPrice.setText(String.valueOf(price));
        tvProductName.setText(name);
        tvDescriptionContent.setText(description);
        tvWarrantyContent.setText(warranty);
        tvCompanyName.setText(companyName);
        Glide.with(getApplicationContext()).load(image_url).into(ivProductImage);



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
}
