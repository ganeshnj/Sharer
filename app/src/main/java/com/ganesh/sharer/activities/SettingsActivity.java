package com.ganesh.sharer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.adapters.CurrencySpinnerAdapter;
import com.ganesh.sharer.models.Currency;
import com.ganesh.sharer.models.User;

public class SettingsActivity extends AppCompatActivity {


    private EditText mEditTextFirstname;
    private EditText mEditTextLastname;
    private EditText mEditTextEmail;

    Currency[] mCurrencyAll ={
            new Currency("$", "Dollar"),
            new Currency("£", "Pound"),
            new Currency("¥", "Yen"),
            new Currency("€", "EURO"),
            new Currency("₹", "INDIAN RUPEE SIGN"),
    };

    Spinner mSpinnerCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mEditTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        this.mEditTextLastname = (EditText) findViewById(R.id.editTextLastname);
        this.mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);

        User user = Repository.getLoginedUser();
        this.mEditTextFirstname.setText(user.getFirstname());
        this.mEditTextLastname.setText(user.getLastname());
        this.mEditTextEmail.setText(user.getEmail());

        mSpinnerCurrency = (Spinner)findViewById(R.id.spinnerCurrency);

        CurrencySpinnerAdapter currencySpinnerAdapter =
                new CurrencySpinnerAdapter(SettingsActivity.this,
                        R.layout.row_layout_currency,
                        mCurrencyAll);
        mSpinnerCurrency.setAdapter(currencySpinnerAdapter);
        mSpinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_settings:
                Currency currency = (Currency) mSpinnerCurrency.getSelectedItem();
                if (saveSettings(mEditTextFirstname.getText().toString(),
                        mEditTextLastname.getText().toString(),
                        mEditTextEmail.getText().toString(), currency)){
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean saveSettings(String firstname, String lastname, String email, Currency currency) {
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
            Repository.updateCurrency(currency);

            return Repository.updateLoginedUser(user);
        }

        return !isError;
    }

}
