package com.vinay.emanager.utils;

import com.vinay.emanager.R;
import com.vinay.emanager.models.Category;

import java.util.ArrayList;

public class Constants {

    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static ArrayList<Category> category;

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;
    public static int DAILY = 0;
    public static int MONTHLY = 1;


    public static void setCategories(){
        category = new ArrayList<>();

        category.add(new Category(R.drawable.baseline_currency,"Salary",R.color.green));
        category.add(new Category(R.drawable.baseline_business,"Business",R.color.orange));
        category.add(new Category(R.drawable.baseline_bar_chart,"Investment",R.color.pink));
        category.add(new Category(R.drawable.baseline_handshake,"Loan",R.color.blue));
        category.add(new Category(R.drawable.baseline_key,"Rent",R.color.purple));
        category.add(new Category(R.drawable.baseline_account_balance_wallet,"Others",R.color.red));
    }


    public static Category getCategoryDetails(String categoryName){
        for (Category cat:category){
            if (cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }
}
