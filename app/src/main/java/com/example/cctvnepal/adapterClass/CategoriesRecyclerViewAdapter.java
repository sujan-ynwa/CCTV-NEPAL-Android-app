package com.example.cctvnepal.adapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cctvnepal.Activities.CategoriesMenu;
import com.example.cctvnepal.Activities.ProductMenu;
import com.example.cctvnepal.R;
import com.example.cctvnepal.URL.BaseUrl;
import com.example.cctvnepal.model.Categories;


import java.util.List;



public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CategoryViewHolder> {


    private List<Categories> categoriesList;
    private Context context;

    public CategoriesRecyclerViewAdapter(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // creating a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_cardview,viewGroup,false);

        return new CategoryViewHolder(v,context);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder categoryViewHolder, int i) {

        // setting up the category name
        categoryViewHolder.tvCategoryName.setText(categoriesList.get(i).getcategoryName());

        // getting the url for the image
        String image_url = BaseUrl.IMAGE_BASE_URL+categoriesList.get(i).getimagePath();



        // loading up the image for image view
       Glide.with(context).load(image_url).into(categoryViewHolder.ivIcon);


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }




    public class CategoryViewHolder extends  RecyclerView.ViewHolder {

        Context context;
        ImageView ivIcon;
        TextView tvCategoryName;
        CardView cardviewCategories;
        public CategoryViewHolder(View itemView, final Context context) {
            super(itemView);

           this.context = context;

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            cardviewCategories = itemView.findViewById(R.id.cardviewCategoryMenu);


            // to recognize the item clicked on the recyclerView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos= getAdapterPosition();

                    Categories getClickedDataInfo = categoriesList.get(pos);


                   Intent intent = new Intent(v.getContext(),ProductMenu.class);
                   // sending the data to another activity
                   intent.putExtra("Product Category",getClickedDataInfo.getcategoryName());
                   v.getContext().startActivity(intent);
                }
            });

        }





    }

}
