package com.vinay.emanager.views.fragments;

import static com.vinay.emanager.utils.Constants.SELECTED_STATS_TYPE;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.android.material.tabs.TabLayout;
import com.vinay.emanager.R;
import com.vinay.emanager.databinding.FragmentStatsBinding;
import com.vinay.emanager.models.Transactions;
import com.vinay.emanager.utils.Constants;
import com.vinay.emanager.utils.Helper;
import com.vinay.emanager.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class StatsFragment extends Fragment {



    public StatsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentStatsBinding binding;

    private Calendar calendar;


    public MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater);

        mainViewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updateDate();

        binding.incomeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeid.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseid.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseid.setTextColor(getContext().getColor(R.color.textcolor));
                binding.incomeid.setTextColor(getContext().getColor(R.color.green));

                SELECTED_STATS_TYPE=Constants.INCOME;
                updateDate();
            }
        });
        binding.expenseid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeid.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseid.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.expenseid.setTextColor(getContext().getColor(R.color.red));
                binding.incomeid.setTextColor(getContext().getColor(R.color.textcolor));

                SELECTED_STATS_TYPE=Constants.EXPENSE;
                updateDate();
            }
        });

        binding.nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.SELECTED_TAB_STATS==Constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                } else if (Constants.SELECTED_TAB_STATS==Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, 1);
                }
                updateDate();
            }
        });

        binding.previousdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.SELECTED_TAB_STATS==Constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                } else if (Constants.SELECTED_TAB_STATS==Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB_STATS = 1;
                    updateDate();
                }else if (tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB_STATS =0;
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

        Pie pie;
        pie = AnyChart.pie();


        mainViewModel.categoryTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transactions>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(RealmResults<Transactions> transactions) {

                if(transactions.size() > 0){
                    binding.empty.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);
                    List<DataEntry> data = new ArrayList<>();
                    Map<String,Double> categoryMap = new HashMap<>();
                    for (Transactions transactions1 : transactions){
                        String category = transactions1.getCategory();
                        double amount = transactions1.getAmount();

                        if (categoryMap.containsKey(category)){
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal+= Math.abs(amount);

                            categoryMap.put(category,currentTotal);

                        }else {
                            categoryMap.put(category,Math.abs(amount));
                        }

                    }
                    for (Map.Entry<String,Double> entry:categoryMap.entrySet()){
                        data.add(new ValueDataEntry(entry.getKey(),entry.getValue()));
                    }

                    pie.data(data);

                }else {
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);

                }

            }
        });

        mainViewModel.getTransactions(calendar,SELECTED_STATS_TYPE);



        binding.anyChart.setChart(pie);
        return binding.getRoot();
    }

    void updateDate(){
        if(Constants.SELECTED_TAB_STATS==Constants.DAILY) {
            binding.currentdate.setText(Helper.formatDate(calendar.getTime()));
        }
        else if(Constants.SELECTED_TAB_STATS==Constants.MONTHLY) {
            binding.currentdate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        mainViewModel.getTransactions(calendar,SELECTED_STATS_TYPE);
    }
}