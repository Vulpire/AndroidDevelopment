package com.example.wofford_hw06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class MainActivity extends AppCompatActivity implements LoginFragment.loginListener,
        CreateAccountFragment.registerListener, ForumsFragment.forumsListener,
        NewForumFragment.newForumListener{
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoginFragment(), "LOGIN")
                .commit();
    }

    @Override
    public void loginSuccess() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rootView, new ForumsFragment(), "FORUMS")
                .commit();
    }

    @Override
    public void loginNewAcct() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rootView, new CreateAccountFragment(), "CREATEACCT")
                .commit();
    }

    @Override
    public void registerCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void registerSuccess() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rootView, new ForumsFragment(), "FORUMS")
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoginFragment(), "LOGIN")
                .commit();
    }

    @Override
    public void newForum() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rootView, new NewForumFragment(), "NEWF")
                .commit();
    }

    @Override
    public void selectForum(Forum clicked) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rootView, ForumFragment.newInstance(clicked), "DETAIL")
                .commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void newForumSuccess() {
        getSupportFragmentManager().popBackStack();
        ForumsFragment fragment = (ForumsFragment) getSupportFragmentManager().findFragmentByTag("FORUMS");
        fragment.updateRecycler();


    }
}