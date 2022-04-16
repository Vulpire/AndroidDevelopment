package com.example.wofford_midterm;

import java.util.Comparator;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class Sort {
    public static class SortDateDes implements Comparator<Expense> {
        @Override
        public int compare(Expense expense, Expense t1) {
            return t1.date.compareTo(expense.date);
        }
    }

    public static class SortMonthYear implements Comparator<Groups>{

        @Override
        public int compare(Groups groups, Groups t1) {
            if(groups.yearValue == t1.yearValue){
                return groups.monthValue - t1.monthValue;
            } else {
                return groups.yearValue - t1.yearValue;
            }
        }
    }


}
