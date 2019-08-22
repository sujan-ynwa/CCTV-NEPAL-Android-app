package com.example.cctvnepal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cctvnepal.R;
import com.example.cctvnepal.adapterClass.ProductRecyclerViewAdapter;
import com.example.cctvnepal.model.Product;

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

        productsList = new ArrayList<>();
        productsList.add(new Product("Camera","1300","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("Router","1400","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("HardDrive","8000","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("Dell- Laptop","80,000","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("Windows-Geniune","7000","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("SATA-Cable","300","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("Switch","5000","This is a excellent camera and it does what it should do so thank you "));
        productsList.add(new Product("Ip-configuration Device","4500","This is a excellent camera and it does what it should do so thank you "));

        rvProduct = findViewById(R.id.rvProductMenu);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setHasFixedSize(true);
        productAdapter =  new ProductRecyclerViewAdapter(productsList,this);
        rvProduct.setAdapter(productAdapter);



    }
}
