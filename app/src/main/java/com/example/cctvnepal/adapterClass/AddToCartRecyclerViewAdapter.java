package com.example.cctvnepal.adapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.fragments.AddToCart;
import com.example.cctvnepal.model.Cart;

import java.util.List;

public class AddToCartRecyclerViewAdapter extends RecyclerView.Adapter<AddToCartRecyclerViewAdapter.AddToCartViewHolder>{


    public static List<Cart> cartList;
    private Context context;

    public AddToCartRecyclerViewAdapter(Context context, List<Cart> cartList){
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public AddToCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // setting up the layout for recyclerView
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_add_to_cart,viewGroup,false);

        return new AddToCartViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToCartViewHolder addToCartViewHolder, int i) {

        // for setting the views
        addToCartViewHolder.tvProductName.setText(cartList.get(i).getProductName());
        addToCartViewHolder.tvAddtoCartPrice.setText("Rs: "+cartList.get(i).getPrice());
        addToCartViewHolder.tvAddtoCartQuantity.setText(String.valueOf(cartList.get(i).getQuantity()));
        addToCartViewHolder.ivMinus.setImageResource(R.drawable.ic_minus);
        addToCartViewHolder.ivPlus.setImageResource(R.drawable.ic_add);

        displayTotalSum();



        // for displaying image
        String image_url = BaseUrl.IMAGE_BASE_URL+cartList.get(i).getImagePath();
        Glide.with(context).load(image_url).into(addToCartViewHolder.ivProductImage);
    }



    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class AddToCartViewHolder extends RecyclerView.ViewHolder{

        Context context;
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvAddtoCartPrice;
        TextView tvAddtoCartQuantity;
        ImageView ivPlus;
        ImageView ivMinus;


        //for increasing and decreasing the quantity
        int quantity=1;

        //total price per item based on number of quantity
        double itemInitialPrice;
        public boolean initialPriceExtracted= false;




        public AddToCartViewHolder(View itemView, final Context context){
            super(itemView);
            this.context = context;

            ivProductImage = itemView.findViewById(R.id.ivAddToCartProductImage);
            ivPlus = itemView.findViewById(R.id.ivPlus);
            ivMinus = itemView.findViewById(R.id.ivMinus);
            tvProductName = itemView.findViewById(R.id.tvAddToCartProductName);
            tvAddtoCartPrice = itemView.findViewById(R.id.tvAddToCartPrice);
            tvAddtoCartQuantity= itemView.findViewById(R.id.tvAddToCartQuantity);


            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    Log.e("long clicks", "onLongClick: long pressed");
                    return true;
                }
            });

            // when plus icon is pressed, quantity is increased
            // and price is updated accordingly
            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(!initialPriceExtracted){
                        itemInitialPrice = cartList.get(pos).getPrice();
                        initialPriceExtracted = true;
                    }
                    quantity++;
                    tvAddtoCartQuantity.setText(String.valueOf(quantity));
                    tvAddtoCartPrice.setText(String.valueOf(quantity*itemInitialPrice));
                    //cartList.get(pos).setPrice(quantity*itemInitialPrice);

                    cartList.get(pos).setQuantity(quantity);

                    displayTotalSum();


                }
            });

            ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // adapter position will help
                    int pos = getAdapterPosition();
                  if(quantity>1){
                      quantity--;
                      tvAddtoCartQuantity.setText(String.valueOf(quantity));
                      tvAddtoCartPrice.setText(String.valueOf(quantity*itemInitialPrice));
                     // cartList.get(pos).setPrice(quantity*itemInitialPrice);

                      cartList.get(pos).setQuantity(quantity);

                      displayTotalSum();

                  }
                }
            });

        }

    }


    // for displaying the total sum of all the item on the cart list
    private void displayTotalSum(){
        double sum = 0;
        for(int j=0;j<cartList.size();j++){
            sum = sum+cartList.get(j).getPrice();
        }
        AddToCart.tvTotal.setText(String.valueOf(sum));
    }
}
