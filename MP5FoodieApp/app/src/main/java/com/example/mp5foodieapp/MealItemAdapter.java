package com.example.mp5foodieapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.MealItemHolder> {

    private static final String TAG = "MealItemAdapter";
    private ArrayList<MealItem> mealData;
    private Context context;

    protected static final String KEY_EXTRA_TITLE = "com.example.mp5foodieapp.extra.TITLE";
    protected static final String KEY_EXTRA_DESCRIPTION = "com.example.mp5foodieapp.extra.DESCRIPTION";
    protected static final String KEY_EXTRA_INGREDIENTS = "com.example.mp5foodieapp.extra.INGREDIENTS";
    protected static final String KEY_EXTRA_CALORIES = "com.example.mp5foodieapp.extra.CALORIES";
    protected static final String KEY_EXTRA_LINK = "com.example.mp5foodieapp.extra.LINK";
    protected static final String KEY_EXTRA_IMAGE = "com.example.mp5foodieapp.extra.IMAGE";

    public MealItemAdapter(Context context, ArrayList<MealItem> mealItemArrayList){
        this.context = context;
        mealData = mealItemArrayList;
    }

    @NonNull
    @Override
    public MealItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealItemHolder((LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull MealItemHolder holder, int position) {
        MealItem currentMeal = mealData.get(position);
        holder.bindItem(currentMeal);
    }

    @Override
    public int getItemCount() {
        return mealData.size();
    }

    protected class MealItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textTitle, textDescriptionTitle, textDescription;
        private ImageView imageViewFood;

        public MealItemHolder(View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textDescriptionTitle = itemView.findViewById(R.id.mealDescriptionTitle);
            textDescription = itemView.findViewById(R.id.mealDescription);
            imageViewFood = itemView.findViewById(R.id.imageViewMeal);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindItem(MealItem currentMeal){
            textTitle.setText(currentMeal.getTitle());
            textDescriptionTitle.setText(currentMeal.getTitle() + " Description:");
            textDescription.setText(currentMeal.getDescription());
            Glide.with(context).load(currentMeal.getImageId()).into(imageViewFood);
        }

        @Override
        public void onClick(View v) {
            MealItem currentMeal = mealData.get(getAdapterPosition());
            Intent intent = new Intent(context, MealItemActivity.class);

            intent.putExtra(KEY_EXTRA_TITLE, currentMeal.getTitle());
            intent.putExtra(KEY_EXTRA_DESCRIPTION, currentMeal.getDescription());
            intent.putExtra(KEY_EXTRA_INGREDIENTS, currentMeal.getIngredients());
            intent.putExtra(KEY_EXTRA_CALORIES, currentMeal.getCalories());
            intent.putExtra(KEY_EXTRA_LINK, currentMeal.getLink());
            intent.putExtra(KEY_EXTRA_IMAGE, currentMeal.getImageId());

            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            int currentMealPosition = getLayoutPosition();
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(v.getContext());

            confirmDialog.setMessage("Remove?");

            confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mealData.remove(currentMealPosition);
                    notifyItemRemoved(currentMealPosition);
                }
            });

            confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "Don't remove meal");
                }
            });

            confirmDialog.create().show();
            return false;
        }
    }

}
