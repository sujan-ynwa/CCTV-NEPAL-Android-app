package com.example.cctvnepal.adapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cctvnepal.R;
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
        productViewHolder.tvProductName.setText(products.get(i).getProductName());
        productViewHolder.tvPrice.setText(products.get(i).getProductPrice());
        productViewHolder.tvSpecs.setText(products.get(i).getSpecs());
        productViewHolder.ivProductImage.setImageResource(R.drawable.movie_app_sign_up_two);

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



        }
    }

}
