package com.vinay.emanager.views.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.vinay.emanager.R;
import com.vinay.emanager.adapters.TransactionAdapter;
import com.vinay.emanager.databinding.FragmentTranscationBinding;
import com.vinay.emanager.models.Transactions;
import com.vinay.emanager.utils.Constants;
import com.vinay.emanager.utils.Helper;
import com.vinay.emanager.viewmodels.MainViewModel;
import com.vinay.emanager.views.activites.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.RealmResults;


public class TranscationFragment extends Fragment {


    public TranscationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTranscationBinding binding;

    private Calendar calendar;
    


    public MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTranscationBinding.inflate(inflater);

        mainViewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        
        calendar = Calendar.getInstance();
        updateDate();



        binding.transrecylerview.setLayoutManager(new LinearLayoutManager(getContext()));

        mainViewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transactions>>() {
            @Override
            public void onChanged(RealmResults<Transactions> transactions) {
                TransactionAdapter adapter = new TransactionAdapter(getActivity(),transactions);
                binding.transrecylerview.setAdapter(adapter);
            }
        });



        mainViewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.income1.setText(String.valueOf(aDouble));
            }
        });

        mainViewModel.totalExpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expense1.setText(String.valueOf(aDouble));
            }
        });

        mainViewModel.totalAmount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.total1.setText(String.valueOf(aDouble));
            }
        });

        mainViewModel.getTransactions(calendar);

        Constants.setCategories();


        binding.floating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddTransFragment().show(getParentFragmentManager(),null);
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB = 1;
                    updateDate();
                }else if (tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB =0;
                    updateDate();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.SELECTED_TAB==Constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                } else if (Constants.SELECTED_TAB==Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, 1);
                }
                updateDate();
            }
        });

        binding.previousdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.SELECTED_TAB==Constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                } else if (Constants.SELECTED_TAB==Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();
            }
        });

        return binding.getRoot();
    }

    void updateDate(){
        if(Constants.SELECTED_TAB==Constants.DAILY) {
            binding.currentdate.setText(Helper.formatDate(calendar.getTime()));
        }
        else if(Constants.SELECTED_TAB==Constants.MONTHLY) {
            binding.currentdate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        mainViewModel.getTransactions(calendar);
    }
}