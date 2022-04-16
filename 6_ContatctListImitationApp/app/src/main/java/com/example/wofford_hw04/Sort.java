package com.example.wofford_hw04;

import java.util.Comparator;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class Sort {
    public static class SortAgeAsc implements Comparator<DataServices.User> {
        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return user.age-t1.age;
        }
    }

    public static class SortAgeDes implements Comparator<DataServices.User> {
        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return t1.age-user.age;
        }
    }

    public static class SortNameAsc implements Comparator<DataServices.User> {
        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return user.name.compareTo(t1.name);
        }
    }

    public static class SortNameDes implements Comparator<DataServices.User> {
        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return t1.name.compareTo(user.name);
        }
    }

    public static class SortStateAsc implements Comparator<DataServices.User>{
        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return user.state.compareTo(t1.state);
        }
    }

    public static class SortStateDes implements Comparator<DataServices.User>{
        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return t1.state.compareTo(user.state);
        }
    }

}
