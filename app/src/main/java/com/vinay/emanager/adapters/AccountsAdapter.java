package com.vinay.emanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinay.emanager.R;
import com.vinay.emanager.databinding.AccountsrowBinding;
import com.vinay.emanager.models.Account;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.viewHolder> {

    Context context;
    ArrayList<Account> accountslist;


    public interface AccountsClickListener{

        void onAccountclick(Account account);
    }

    AccountsClickListener accountsClickListener;

    public AccountsAdapter(Context context, ArrayList<Account> accountslist,AccountsClickListener accountsClickListener) {
        this.context = context;
        this.accountslist = accountslist;
        this.accountsClickListener = accountsClickListener;
    }

    @NonNull
    @Override
    public AccountsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.accountsrow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsAdapter.viewHolder holder, int position) {
        Account account = accountslist.get(position);
        holder.binding.accountname.setText(account.getAccountName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountsClickListener.onAccountclick(account);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accountslist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        AccountsrowBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AccountsrowBinding.bind(itemView);
        }
    }
}
