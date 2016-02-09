package com.ganesh.sharer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ganesh.sharer.models.User;

public class AddFriendActivity extends AppCompatActivity {

    private EditText mEditTextFirstname;
    private EditText mEditTextLastname;
    private EditText mEditTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_friend);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setTitle("Add a friend");

        this.mEditTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        this.mEditTextLastname = (EditText) findViewById(R.id.editTextLastname);
        this.mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_friend:
                if (saveFriend(mEditTextFirstname.getText().toString(), mEditTextLastname.getText().toString(), mEditTextEmail.getText().toString())){
                    DatabaseContext context = new DatabaseContext();
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_friend, menu);
        return true;
    }

    private boolean saveFriend(String firstname, String lastname, String email) {
        boolean isError = false;
        if (firstname == null || firstname.isEmpty()){
            mEditTextFirstname.setError("Firstname is required");
            isError = true;
        }

        if (lastname == null || lastname.isEmpty()){
            mEditTextLastname.setError("Lastname is required");
            isError = true;
        }

        if (email == null || email.isEmpty()){
            mEditTextEmail.setError("Email is required");
            isError = true;
        }

        if (!isError){
            User user = new User(firstname, lastname, email);
            DatabaseContext context = new DatabaseContext();
            context.addUser(user);
        }

        return !isError;
    }

}
