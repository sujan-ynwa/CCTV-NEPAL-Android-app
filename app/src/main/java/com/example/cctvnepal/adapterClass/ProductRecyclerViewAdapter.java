package com.example.cctvnepal.adapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cctvnepal.Activities.ProductDetails;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.model.Product;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {


    List<Product> products;
    //context is required for onClickListener
    Context context;

    public ProductRecyclerViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // inflating the view
        // creating a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_view,viewGroup,false);
        return new ProductViewHolder(v,context);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {

        //image url
        String image_url = BaseUrl.IMAGE_BASE_URL+products.get(i).getImagepath();

        // setting up the value for the view
        productViewHolder.tvProductName.setText(products.get(i).getProductName());
        productViewHolder.tvPrice.setText(String.valueOf(products.get(i).getPrice()));
        productViewHolder.tvSpecs.setText(products.get(i).getSpecs());

        // setting up the image view
        Glide.with(context).load(image_url).into(productViewHolder.ivProductImage);



    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        Context context;
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvPrice;
        TextView tvSpecs;

        public ProductViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;

            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSpecs = itemView.findViewById(R.id.tvSpecs);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos= getAdapterPosition();
                    Product getClickedInfo = products.get(pos);

                    Intent intent = new Intent(v.getContext(), ProductDetails.class);
                    intent.putExtra("Product name",getClickedInfo.getProductName());
                    intent.putExtra("details",getClickedInfo.getSpecs());
                    intent.putExtra("price",getClickedInfo.getPrice());
                    intent.putExtra("image",getClickedInfo.getImagepath());

                    // added afterwards for test purpose if passes then applly changes
                    intent.putExtra("companyName",getClickedInfo.getCompanyName());
                    intent.putExtra("warranty",getClickedInfo.getWarranty());

                    v.getContext().startActivity(intent);
                }
            });



        }
    }

}
