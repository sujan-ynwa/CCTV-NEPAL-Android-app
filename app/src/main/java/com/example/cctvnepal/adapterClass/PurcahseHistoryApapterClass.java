package com.example.cctvnepal.adapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cctvnepal.R;
import com.example.cctvnepal.model.Orders;

import java.util.List;

public class PurcahseHistoryApapterClass extends RecyclerView.Adapter<PurcahseHistoryApapterClass.PurchaseHistoryViewHolder>{


    List<Orders> ordersList;

    Context context;

    public PurcahseHistoryApapterClass(List<Orders> ordersList, Context context){
        this.ordersList = ordersList;
        this.context = context;
    }



    @NonNull
    @Override
    public PurchaseHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_purchase_history,viewGroup,false);
        return  new PurchaseHistoryViewHolder(v,context);

    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseHistoryViewHolder purchaseHistoryViewHolder, int i) {


        purchaseHistoryViewHolder.tvdate.setText(ordersList.get(i).getPurchaseDate());
        purchaseHistoryViewHolder.tvProductName.setText("Product: "+ordersList.get(i).getProductName());
        purchaseHistoryViewHolder.tvPrice.setText("Price per unit: "+String.valueOf(ordersList.get(i).getPrice()));
        purchaseHistoryViewHolder.tvQuantity.setText("Quantity: "+String.valueOf(ordersList.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class PurchaseHistoryViewHolder extends RecyclerView.ViewHolder{

        Context context;

        TextView tvProductName;
        TextView tvPrice;
        TextView tvQuantity;
        TextView tvdate;

        public PurchaseHistoryViewHolder(@NonNull View itemView,final Context context) {
            super(itemView);
            this.context = context;

            tvProductName = itemView.findViewById(R.id.tvPurchaseHistoryProductName);
            tvPrice = itemView.findViewById(R.id.tvPurchaseHistoryPrice);
            tvQuantity = itemView.findViewById(R.id.tvPurchaseHistoryQuantity);
            tvdate = itemView.findViewById(R.id.tvPurchaseDate);
        }
    }


}
