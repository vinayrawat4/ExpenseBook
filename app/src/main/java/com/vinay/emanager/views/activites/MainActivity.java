package com.vinay.emanager.views.activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.vinay.emanager.adapters.TransactionAdapter;
import com.vinay.emanager.models.Transactions;
import com.vinay.emanager.utils.Constants;
import com.vinay.emanager.utils.Helper;
import com.vinay.emanager.viewmodels.MainViewModel;
import com.vinay.emanager.views.fragments.AddTransFragment;
import com.vinay.emanager.R;
import com.vinay.emanager.databinding.ActivityMainBinding;
import com.vinay.emanager.views.fragments.StatsFragment;
import com.vinay.emanager.views.fragments.TranscationFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private Calendar calendar;


    public MainViewModel mainViewModel;



    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel= new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("ExpenseBook");


        Constants.setCategories();

        calendar = Calendar.getInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new TranscationFragment());
        transaction.commit();

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId()== R.id.trans){
                    getSupportFragmentManager().popBackStack();

                } else if (item.getItemId()==R.id.stats) {
                    transaction.replace(R.id.content,new StatsFragment());
                    transaction.addToBackStack(null);
                }
                transaction.commit();

                return true;
            }
        });


    }


    public void getTransactions(){
        mainViewModel.getTransactions(calendar);
    }


}