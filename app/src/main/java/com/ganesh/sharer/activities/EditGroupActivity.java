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
import com.ganesh.sharer.dialogFragments.UsersSelectionDialogFragment;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;

public class EditGroupActivity extends AppCompatActivity implements UsersSelectionDialogFragment.OnFragmentInteractionListener {

    public static final String ARG_GROUP_ID= "group_id";

    public static final String ARG_USER_SELECTION_DIALOG= "user_selection_dialog";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private TextView mTextViewGroupMembers;
    private ArrayList<User> mGroupMembers;
    private int mGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.EditGroup));


        mEditTextTitle = (EditText) findViewById(R.id.editTextTitle);
        mEditTextDescription = (EditText) findViewById(R.id.editTextDescription);
        mTextViewGroupMembers = (TextView) findViewById(R.id.textViewGroupMembers);
        mTextViewGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersSelectionDialogFragment dialog = UsersSelectionDialogFragment.newInstance(Repository.getAllUsers(), mGroupMembers);
                dialog.setFromActivity(EditGroupActivity.class.getSimpleName());
                dialog.show(getSupportFragmentManager(), ARG_USER_SELECTION_DIALOG);
            }
        });
        mGroupMembers = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mGroupId = extras.getInt(ARG_GROUP_ID);

            Group group = Repository.getGroupById(mGroupId);
            if (group != null){
                this.mEditTextTitle.setText(group.getTitle());
                this.mEditTextDescription.setText(group.getDescription());
                this.mGroupMembers = group.getGroupMembers();

                updateGroupMembers(group.getGroupMembers());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_group:
                saveGroup(mGroupId, mEditTextTitle.getText().toString(), mEditTextDescription.getText().toString(), mGroupMembers);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_group, menu);
        return true;
    }

    private boolean saveGroup(int groupId, String title, String description, ArrayList<User> groupMembers) {
        boolean isError = false;
        if (title == null || title.isEmpty()){
            mEditTextTitle.setError(getString(R.string.FirstnameRequired));
            isError = true;
        }

        if (groupMembers.size()==0){
            mTextViewGroupMembers.setError(getString(R.string.ThereMustBeAGroup));
            isError = true;
        }

        if (isError == false) {
            Group group = new Group(groupId);
            group.setTitle(title);
            group.setDescription(description);
            group.setGroupMembers(groupMembers);

            if (Repository.updateGroup(group)) {
                finish();
            }
        }

        return !isError;
    }

    @Override
    public void onFinishingUsersSelection(ArrayList<User> selectedUsers) {
        mGroupMembers = selectedUsers;
        updateGroupMembers(selectedUsers);
    }

    public void updateGroupMembers(ArrayList<User> groupMembers){

        if (groupMembers != null) {
            StringBuilder builder = new StringBuilder();

            for (User user: groupMembers) {
                builder.append(user.getFormatted());
                builder.append('\n');
            }

            mTextViewGroupMembers.setText(builder);
        }

    }

}
