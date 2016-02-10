package com.ganesh.sharer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ganesh.sharer.DatabaseContext;
import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.adapters.ShareArrayAdapter;
import com.ganesh.sharer.dialogFragments.GroupSelectionDialogFragment;
import com.ganesh.sharer.dialogFragments.UsersSelectionDialogFragment;
import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.Share;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity implements GroupSelectionDialogFragment.OnFragmentInteractionListener,
        UsersSelectionDialogFragment.OnFragmentInteractionListener{
    public static final String ARG_GROUP_SELECTION_DIALOG= "group_selection_dialog";

    public static final String ARG_USER_SELECTION_DIALOG= "user_selection_dialog";
    
    private Event mEvent;
    private EditText mEditTextGroup;
    private EditText mEditTextEventName;
    private EditText mEditTextDescription;
    private EditText mEditTextAmount;

    private ArrayList<Share> mShares;

    private ListView mListViewShares;
    private ShareArrayAdapter mShareArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextEventName = (EditText) findViewById(R.id.editEventName);
        mEditTextDescription = (EditText) findViewById(R.id.editTextDescription);
        mEditTextAmount = (EditText) findViewById(R.id.editTextAmount);

        mEvent = new Event();
        mShares = new ArrayList<>();
        mShares.add(new Share(Repository.getUserById(1), 100));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mEditTextGroup = (EditText) findViewById(R.id.editTextGroup);
        mEditTextGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupSelectionDialogFragment dialog = GroupSelectionDialogFragment.newInstance(Repository.getAllGroups());
                dialog.setFromActivity(AddEventActivity.class.getSimpleName());
                dialog.show(getSupportFragmentManager(), ARG_GROUP_SELECTION_DIALOG);
            }
        });

        Button buttonAddSharers = (Button) findViewById(R.id.buttonAddSharers);
        buttonAddSharers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<User> friends = null;
                if (mEvent.getGroup() != null) {
                    friends = mEvent.getGroup().getGroupMembers();
                }
                else
                {
                    friends = Repository.getAllUsers();
                }

                ArrayList<User> selectedFriends = new ArrayList<User>();

                    for (Share share: mShares ) {
                        selectedFriends.add(share.getSharer());
                    }


                UsersSelectionDialogFragment dialog = UsersSelectionDialogFragment.newInstance(friends, selectedFriends);
                dialog.setFromActivity(AddEventActivity.class.getSimpleName());
                dialog.show(getSupportFragmentManager(), ARG_USER_SELECTION_DIALOG);
            }
        });

        mListViewShares = (ListView) findViewById(R.id.listViewShares);
        mShareArrayAdapter = new ShareArrayAdapter(this, R.layout.row_layout_share, mShares);
        mListViewShares.setAdapter(mShareArrayAdapter);
        mShareArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_event:
                if (saveEvent(mEditTextEventName.getText().toString(),
                        mEditTextDescription.getText().toString(),
                        mEditTextAmount.getText().toString())){
                    DatabaseContext context = new DatabaseContext();
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean saveEvent(String title, String description, String amount) {
        boolean isError = false;
        if (title == null || title.isEmpty()){
            mEditTextEventName.setError("Firstname is required");
            isError = true;
        }



        if (amount == null || amount.isEmpty()){
            mEditTextAmount.setError("Email is required");
            isError = true;
        }

        if (!isError){
            Double a = Double.valueOf(amount);
            mEvent.setTotalAmount(a);
            mEvent.setSharers(mShares);
            mEvent.setTitle(title);
            mEvent.setDescription(description);
            Repository.createEvent(mEvent);
        }

        return !isError;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_event, menu);
        return true;
    }

    @Override
    public void onFinishingGroupSelection(Group selectedGroup) {
        mEvent.setGroup(selectedGroup);
        mEditTextGroup.setText(selectedGroup.getTitle());
    }

    @Override
    public void onFinishingUsersSelection(ArrayList<User> selectedUsers) {
        ArrayList<Share> shares = new ArrayList<>();
        for (User user: selectedUsers) {
            Share s = null;
            for (Share share: mShares){
                if (share.getSharer() == user) {
                    s = share;
                    break;
                }
            }
            if (s != null){
                shares.add(s);
            }
            else {
                shares.add(new Share(user, 0));
            }
        }
        mShares = shares;
        mShareArrayAdapter = new ShareArrayAdapter(this, R.layout.row_layout_share, mShares);
        mListViewShares.setAdapter(mShareArrayAdapter);
        mShareArrayAdapter.notifyDataSetChanged();
    }
}
