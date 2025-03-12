package com.vinay.emanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinay.emanager.R;
import com.vinay.emanager.databinding.SampleCategoryBinding;
import com.vinay.emanager.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    Context context;
    ArrayList<Category> list;

    public interface CategoryClickListener{
        void onCategoryClick(Category category);

    }

    CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context, ArrayList<Category> list,CategoryClickListener categoryClickListener ) {
        this.context = context;
        this.list = list;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
        Category category = list.get(position);
        holder.binding.categorytext.setText(category.getCategoryName());
        holder.binding.categoryicon.setImageResource(category.getCategoryImage());
        holder.binding.categoryicon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickListener.onCategoryClick(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {

        SampleCategoryBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SampleCategoryBinding.bind(itemView);
        }
    }
}
