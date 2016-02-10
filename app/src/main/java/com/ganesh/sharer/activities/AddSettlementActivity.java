package com.ganesh.sharer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ganesh.sharer.DatabaseContext;
import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.dialogFragments.UserSelectionDialogFragment;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.Settlement;
import com.ganesh.sharer.models.User;

public class AddSettlementActivity extends AppCompatActivity implements UserSelectionDialogFragment.OnFragmentInteractionListener {

    public static final String ARG_USER_SELECTION_DIALOG = "user_selection_dialog";

    private User mSelectedFriend;
    private EditText mEditTextAmount;
    private EditText mEditTextFriend;
    private Settlement mSettlement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_settlement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Add settlement");

        mEditTextAmount = (EditText) findViewById(R.id.editTextAmount);
        mEditTextFriend = (EditText) findViewById(R.id.editTextFriend);
        mSettlement = new Settlement();

        mEditTextFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSelectionDialogFragment dialog = UserSelectionDialogFragment.newInstance(Repository.getAllUsers());
                dialog.setFromActivity(AddSettlementActivity.class.getSimpleName());
                dialog.show(getSupportFragmentManager(), ARG_USER_SELECTION_DIALOG);
            }
        });

        TextView textViewCurrencySign = (TextView) findViewById(R.id.textviewCurrencySign);
        textViewCurrencySign.setText(Repository.getCurrency().getSymbol());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_settlement:
                if (saveSettlement(mSelectedFriend, mEditTextAmount.getText().toString())){
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean saveSettlement(User friend, String amount) {
        boolean isError = false;
        if (friend == null){
            mEditTextFriend.setError("A friend is required");
            isError = true;
        }



        if (amount == null || amount.isEmpty()){
            mEditTextAmount.setError("Email is required");
            isError = true;
        }

        if (!isError){
            Double a = Double.valueOf(amount);
            mSettlement.setGiver(Repository.getLoginedUser());
            mSettlement.setTaker(friend);
            Repository.createSettlement(mSettlement);
        }

        return !isError;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_settlement, menu);
        return true;
    }

    @Override
    public void onFinishingUserSelection(User selectedFriend) {
        mSettlement.setTaker(selectedFriend);
        mSelectedFriend = selectedFriend;
        mEditTextFriend.setText(selectedFriend.getName());
    }

}
