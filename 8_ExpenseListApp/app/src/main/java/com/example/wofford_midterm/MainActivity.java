package com.example.wofford_midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class MainActivity extends AppCompatActivity implements
        ExpenseFragment.ExpenseFragmentListener, NewExpenseFragment.NewExpenseFragmentListener
        , PickCategotyFragment.PickCategoryFragmentListener {
    ArrayList<Expense> expenseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.expenses));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainLayout, ExpenseFragment.newInstance(expenseArrayList), "EXPENSE")
                .commit();
    }

    @Override
    public void delete(Expense expense) {
        expenseArrayList.remove(expense);
        Collections.sort(expenseArrayList, new Sort.SortDateDes());
        ExpenseFragment fragment = (ExpenseFragment) getSupportFragmentManager().findFragmentByTag("EXPENSE");
        fragment.updateExpenseFragment(expenseArrayList);
    }

    @Override
    public void addNew() {
        //getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.mainLayout, new NewExpenseFragment(), "NEW_EXPENSE")
                .commit();
    }

    @Override
    public void summary() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.mainLayout, SummaryFragment.newInstance(expenseArrayList), "SUMMARY")
                .commit();
    }

    @Override
    public void newExpensePickCat() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.mainLayout, new PickCategotyFragment(), "PICK")
                .commit();
    }

    @Override
    public void newExpenseCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void newExpenseSubmit(Expense expense) {
        expenseArrayList.add(expense);
        Collections.sort(expenseArrayList, new Sort.SortDateDes());
        ExpenseFragment fragment = (ExpenseFragment) getSupportFragmentManager().findFragmentByTag("EXPENSE");
        fragment.updateExpenseFragment(expenseArrayList);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void category(String cat) {
        NewExpenseFragment fragment = (NewExpenseFragment) getSupportFragmentManager()
                .findFragmentByTag("NEW_EXPENSE");
        fragment.updateFragment(cat);
        getSupportFragmentManager().popBackStack();

    }
}