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
import com.ganesh.sharer.dialogFragments.UsersSelectionDialogFragment;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;

public class AddGroupActivity extends AppCompatActivity implements UsersSelectionDialogFragment.OnFragmentInteractionListener {


    public static final String ARG_USER_SELECTION_DIALOG= "user_selection_dialog";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private TextView mTextViewGroupMembers;
    private ArrayList<User> mGroupMembers;
    private DatabaseContext mDbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDbContext = new DatabaseContext();

        mEditTextTitle = (EditText) findViewById(R.id.editTextTitle);
        mEditTextDescription = (EditText) findViewById(R.id.editTextDescription);
        mTextViewGroupMembers = (TextView) findViewById(R.id.textViewGroupMembers);
        mTextViewGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersSelectionDialogFragment dialog = UsersSelectionDialogFragment.newInstance(mDbContext.getUsers(), mGroupMembers);
                dialog.setFromActivity(AddGroupActivity.class.getSimpleName());
                dialog.show(getSupportFragmentManager(), ARG_USER_SELECTION_DIALOG);
            }
        });
        mGroupMembers = new ArrayList<>();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_group:
                saveGroup(mEditTextTitle.getText().toString(), mEditTextDescription.getText().toString(), mGroupMembers);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_group, menu);
        return true;
    }

    private boolean saveGroup(String title, String description, ArrayList<User> userIds) {
        boolean isError = false;
        if (title == null || title.isEmpty()){
            mEditTextTitle.setError("Firstname is required");
            isError = true;
        }

        if (userIds.size()==0){
            mTextViewGroupMembers.setError("There must be atleast one member in this group");
            isError = true;
        }

        if (isError == false) {
            if (mDbContext.addGroup(title, description, mGroupMembers)) {
                finish();
            }
        }

        return !isError;
    }

    @Override
    public void onFinishingUsersSelection(ArrayList<User> selectedUsers) {
        mGroupMembers = selectedUsers;

        StringBuilder builder = new StringBuilder();
        for (User user: selectedUsers) {
            builder.append(user.getFormatted());
            builder.append('\n');
        }

        mTextViewGroupMembers.setText(builder);
    }

    public void updateSelectedUsers(ArrayList<User> users) {

    }
}
