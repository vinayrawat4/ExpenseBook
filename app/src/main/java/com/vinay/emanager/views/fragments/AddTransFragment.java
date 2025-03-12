package com.vinay.emanager.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vinay.emanager.R;
import com.vinay.emanager.adapters.AccountsAdapter;
import com.vinay.emanager.adapters.CategoryAdapter;
import com.vinay.emanager.databinding.FragmentAddTransBinding;
import com.vinay.emanager.databinding.ListDialogBinding;
import com.vinay.emanager.models.Account;
import com.vinay.emanager.models.Category;
import com.vinay.emanager.models.Transactions;
import com.vinay.emanager.utils.Constants;
import com.vinay.emanager.views.activites.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransFragment extends BottomSheetDialogFragment {



    public AddTransFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransBinding binding;
    Transactions transactions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransBinding.inflate(inflater);

        transactions = new Transactions();


        binding.incomeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeid.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseid.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseid.setTextColor(getContext().getColor(R.color.textcolor));
                binding.incomeid.setTextColor(getContext().getColor(R.color.green));

                transactions.setType(Constants.INCOME);
            }
        });
        binding.expenseid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeid.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseid.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.expenseid.setTextColor(getContext().getColor(R.color.red));
                binding.incomeid.setTextColor(getContext().getColor(R.color.textcolor));

                transactions.setType(Constants.EXPENSE);
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH,datePicker.getMonth());
                    calendar.set(Calendar.YEAR,datePicker.getYear());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy ");
                    String date = dateFormat.format(calendar.getTime());

                    binding.date.setText(date);

                    transactions.setDate(calendar.getTime());
                    transactions.setId(calendar.getTime().getTime());
                });
                datePickerDialog.show();
            }
        });


        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
                AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.setView(dialogBinding.getRoot());



                CategoryAdapter adapter = new CategoryAdapter(getContext(), Constants.category, new CategoryAdapter.CategoryClickListener() {
                    @Override
                    public void onCategoryClick(Category category) {
                        binding.category.setText(category.getCategoryName());
                        transactions.setCategory(category.getCategoryName());
                        dialog.dismiss();
                    }
                });
                dialogBinding.recylerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                dialogBinding.recylerview.setAdapter(adapter);

                dialog.show();

            }
        });


        binding.accounts1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
                AlertDialog accountsdialog = new AlertDialog.Builder(getContext()).create();
                accountsdialog.setView(dialogBinding.getRoot());

                ArrayList<Account> accounts = new ArrayList<>();
                accounts.add(new Account(0,"Cash"));
                accounts.add(new Account(0,"Bank"));
                accounts.add(new Account(0,"PayTm"));
                accounts.add(new Account(0,"PhonePe"));
                accounts.add(new Account(0,"GPay"));
                accounts.add(new Account(0,"others"));

                AccountsAdapter adapter2 = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                    @Override
                    public void onAccountclick(Account account) {
                        binding.accounts1.setText(account.getAccountName());
                        transactions.setAccount(account.getAccountName());
                        accountsdialog.dismiss();
                    }
                });
                dialogBinding.recylerview.setLayoutManager(new LinearLayoutManager(getContext()));
                dialogBinding.recylerview.setAdapter(adapter2);

                accountsdialog.show();

            }
        });


        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = Double.parseDouble(binding.amount1.getText().toString());

                if (transactions.getType().equals(Constants.EXPENSE)){
                    transactions.setAmount(amount*-1);
                }else {
                    transactions.setAmount(amount);
                }


                ((MainActivity)getActivity()).mainViewModel.addTransactions(transactions);
                ((MainActivity)getActivity()).getTransactions();
                dismiss();
            }
        });



        return binding.getRoot();
    }
}