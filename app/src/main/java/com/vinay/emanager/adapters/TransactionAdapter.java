package com.vinay.emanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinay.emanager.R;
import com.vinay.emanager.databinding.RowTrasBinding;
import com.vinay.emanager.models.Category;
import com.vinay.emanager.models.Transactions;
import com.vinay.emanager.utils.Constants;
import com.vinay.emanager.utils.Helper;
import com.vinay.emanager.views.activites.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.viewHolder> {

    Context context;
    RealmResults<Transactions> transactionsArrayList;

    public TransactionAdapter(Context context, RealmResults<Transactions> transactionsArrayList) {
        this.context = context;
        this.transactionsArrayList = transactionsArrayList;
    }

    @NonNull
    @Override
    public TransactionAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.row_tras,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.viewHolder holder, int position) {
        Transactions transactions = transactionsArrayList.get(position);

        holder.binding.transamount.setText(String.valueOf(transactions.getAmount()));
        holder.binding.account.setText(transactions.getAccount());
        holder.binding.date.setText(Helper.formatDate(transactions.getDate()));
        holder.binding.category.setText(transactions.getCategory());

        Category transCategory = Constants.getCategoryDetails(transactions.getCategory());
        holder.binding.imageView.setImageResource(transCategory.getCategoryImage());
        holder.binding.imageView.setBackgroundTintList(context.getColorStateList(transCategory.getCategoryColor()));

        if (transactions.getType().equals(Constants.INCOME)){
            holder.binding.transamount.setTextColor(context.getColor(R.color.green));
        } else if (transactions.getType().equals(Constants.EXPENSE)) {
            holder.binding.transamount.setTextColor(context.getColor(R.color.red));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deletedilog = new AlertDialog.Builder(context).create();
                deletedilog.setTitle("Delete Transactions");
                deletedilog.setMessage("Are you Sure to delete this transactions?");
                deletedilog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity)context).mainViewModel.deleteTransactions(transactions);
                    }
                });
                deletedilog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletedilog.dismiss();
                    }
                });
                deletedilog.show();

                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return transactionsArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        RowTrasBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTrasBinding.bind(itemView);
        }
    }
}
