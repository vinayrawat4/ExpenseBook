package com.vinay.emanager.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.vinay.emanager.models.Transactions;
import com.vinay.emanager.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

    Realm realm;
    Calendar calendar;

   public MutableLiveData<RealmResults<Transactions>> transactions = new MutableLiveData<>();
   public MutableLiveData<RealmResults<Transactions>> categoryTransactions = new MutableLiveData<>();

   public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
   public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
   public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setupDatabase();
    }

   public void getTransactions(Calendar calendar){
        this.calendar=calendar;

        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

       double income=0;
       double expense=0;
       double total=0;

       RealmResults<Transactions> newtransactions = null;


       if(Constants.SELECTED_TAB==Constants.DAILY) {

             newtransactions = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .findAll();


             income = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.INCOME)
                    .sum("amount")
                    .doubleValue();

             expense = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.EXPENSE)
                    .sum("amount")
                    .doubleValue();


             total = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .sum("amount")
                    .doubleValue();





        } else if (Constants.SELECTED_TAB==Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();

            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();

            newtransactions = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .findAll();


           income = realm.where(Transactions.class).
                   greaterThanOrEqualTo("date", startTime)
                   .lessThan("date", endTime)
                   .equalTo("type", Constants.INCOME)
                   .sum("amount")
                   .doubleValue();

           expense = realm.where(Transactions.class).
                   greaterThanOrEqualTo("date", startTime)
                   .lessThan("date", endTime)
                   .equalTo("type", Constants.EXPENSE)
                   .sum("amount")
                   .doubleValue();


           total = realm.where(Transactions.class).
                   greaterThanOrEqualTo("date", startTime)
                   .lessThan("date",endTime)
                   .sum("amount")
                   .doubleValue();
        }

       totalIncome.setValue(income);
       totalExpense.setValue(expense);
       totalAmount.setValue(total);
       transactions.setValue(newtransactions);

   }

   public void getTransactions(Calendar calendar,String type){
        this.calendar=calendar;

        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);


        RealmResults<Transactions> newtransactions = null;


        if(Constants.SELECTED_TAB_STATS==Constants.DAILY) {

            newtransactions = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type",type)
                    .findAll();

        } else if (Constants.SELECTED_TAB_STATS==Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();

            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();

            newtransactions = realm.where(Transactions.class).
                    greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .equalTo("type",type)
                    .findAll();


        }

        categoryTransactions.setValue(newtransactions);

    }

   public void addTransactions(Transactions transactions){

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transactions);
        realm.commitTransaction();
    }

    public void deleteTransactions(Transactions transactions){

        realm.beginTransaction();
        transactions.deleteFromRealm();
        realm.commitTransaction();
        getTransactions(calendar);
    }

    void setupDatabase(){
        realm = Realm.getDefaultInstance();
    }
}
