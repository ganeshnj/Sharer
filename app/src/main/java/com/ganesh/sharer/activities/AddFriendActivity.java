package com.ganesh.sharer.activities;

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

import com.ganesh.sharer.DatabaseContext;
import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
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
        setTitle(getString(R.string.AddFriend));

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
            mEditTextFirstname.setError(getString(R.string.FirstnameRequired));
            isError = true;
        }

        if (lastname == null || lastname.isEmpty()){
            mEditTextLastname.setError(getString(R.string.LastnameRequired));
            isError = true;
        }

        if (email == null || email.isEmpty()){
            mEditTextEmail.setError(getString(R.string.EmailRequired));
            isError = true;
        }

        if (!isError){
            User user = new User(firstname, lastname, email);
            return Repository.createUser(user);
        }

        return !isError;
    }

}
